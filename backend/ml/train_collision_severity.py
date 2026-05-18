from __future__ import annotations

import argparse
import json
from pathlib import Path

import joblib
import pandas as pd
from sklearn.compose import ColumnTransformer
from sklearn.dummy import DummyClassifier
from sklearn.impute import SimpleImputer
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix, f1_score
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import OneHotEncoder, StandardScaler

ROOT_DIR = Path(__file__).resolve().parents[2]
DEFAULT_COLLISION_CSV = ROOT_DIR / "data/uk-dft-2024/dft-road-casualty-statistics-collision-2024.csv"
DEFAULT_VEHICLE_CSV = ROOT_DIR / "data/uk-dft-2024/dft-road-casualty-statistics-vehicle-2024.csv"
DEFAULT_OUTPUT_DIR = Path(__file__).resolve().parent / "artifacts"

TARGET_MAPPING = {1: "Fatal", 2: "Serious", 3: "Slight"}
LEAKAGE_COLUMNS = {
    "collision_index",
    "collision_year",
    "collision_ref_no",
    "collision_severity",
    "enhanced_severity_collision",
    "collision_injury_based",
    "collision_adjusted_severity_serious",
    "collision_adjusted_severity_slight",
    "number_of_casualties",
    "did_police_officer_attend_scene_of_accident",
}
NUMERIC_COLUMNS = {
    "location_easting_osgr",
    "location_northing_osgr",
    "longitude",
    "latitude",
    "first_road_number",
    "second_road_number",
    "speed_limit",
    "number_of_vehicles",
    "collision_month",
    "collision_day",
    "collision_hour",
    "collision_minute",
    "is_weekend",
    "is_night",
    "vehicle_record_count",
    "vehicle_type_nunique",
    "vehicle_manoeuvre_nunique",
    "age_of_driver_mean",
    "age_of_driver_min",
    "age_of_driver_max",
    "engine_capacity_cc_mean",
    "age_of_vehicle_mean",
    "escooter_count",
    "skidding_vehicle_count",
    "left_carriageway_count",
    "hit_object_carriageway_count",
    "hit_object_off_carriageway_count",
}


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Train a collision severity baseline model")
    parser.add_argument("--collision-csv", type=Path, default=DEFAULT_COLLISION_CSV)
    parser.add_argument("--vehicle-csv", type=Path, default=DEFAULT_VEHICLE_CSV)
    parser.add_argument("--output-dir", type=Path, default=DEFAULT_OUTPUT_DIR)
    parser.add_argument("--test-size", type=float, default=0.2)
    parser.add_argument("--random-state", type=int, default=42)
    parser.add_argument("--sample-size", type=int, default=0, help="Optional row cap for fast debugging; 0 uses all data")
    return parser.parse_args()


def safe_mode(series: pd.Series):
    modes = series.dropna().mode()
    if modes.empty:
        return pd.NA
    return modes.iloc[0]


def count_positive(series: pd.Series) -> int:
    numeric = pd.to_numeric(series, errors="coerce").fillna(0)
    return int((numeric > 0).sum())


def load_collision_frame(path: Path) -> pd.DataFrame:
    frame = pd.read_csv(path, low_memory=False)
    frame["target"] = frame["collision_severity"].map(TARGET_MAPPING)

    date_ts = pd.to_datetime(frame["date"], format="%d/%m/%Y", errors="coerce")
    time_ts = pd.to_datetime(frame["time"], format="%H:%M", errors="coerce")
    frame["collision_month"] = date_ts.dt.month
    frame["collision_day"] = date_ts.dt.day
    frame["collision_hour"] = time_ts.dt.hour
    frame["collision_minute"] = time_ts.dt.minute
    frame["is_weekend"] = frame["day_of_week"].isin([1, 7]).astype(int)
    frame["is_night"] = frame["collision_hour"].isin([0, 1, 2, 3, 4, 5, 22, 23]).astype(int)

    return frame


def load_vehicle_frame(path: Path) -> pd.DataFrame:
    frame = pd.read_csv(path, low_memory=False)
    for col in ["age_of_driver", "engine_capacity_cc", "age_of_vehicle"]:
        frame[col] = pd.to_numeric(frame[col], errors="coerce")
        frame.loc[frame[col] < 0, col] = pd.NA
    return frame


def aggregate_vehicle_features(vehicle: pd.DataFrame) -> pd.DataFrame:
    grouped = vehicle.groupby("collision_index", sort=False)
    aggregated = grouped.agg(
        vehicle_record_count=("vehicle_reference", "count"),
        vehicle_type_mode=("vehicle_type", safe_mode),
        vehicle_type_nunique=("vehicle_type", "nunique"),
        vehicle_manoeuvre_mode=("vehicle_manoeuvre", safe_mode),
        vehicle_manoeuvre_nunique=("vehicle_manoeuvre", "nunique"),
        first_point_of_impact_mode=("first_point_of_impact", safe_mode),
        age_of_driver_mean=("age_of_driver", "mean"),
        age_of_driver_min=("age_of_driver", "min"),
        age_of_driver_max=("age_of_driver", "max"),
        age_band_of_driver_mode=("age_band_of_driver", safe_mode),
        sex_of_driver_mode=("sex_of_driver", safe_mode),
        engine_capacity_cc_mean=("engine_capacity_cc", "mean"),
        age_of_vehicle_mean=("age_of_vehicle", "mean"),
        propulsion_code_mode=("propulsion_code", safe_mode),
        journey_purpose_mode=("journey_purpose_of_driver", safe_mode),
        driver_distance_mode=("driver_distance_banding", safe_mode),
        escooter_count=("escooter_flag", "sum"),
        skidding_vehicle_count=("skidding_and_overturning", count_positive),
        left_carriageway_count=("vehicle_leaving_carriageway", count_positive),
        hit_object_carriageway_count=("hit_object_in_carriageway", count_positive),
        hit_object_off_carriageway_count=("hit_object_off_carriageway", count_positive),
    )
    return aggregated.reset_index()


def build_model_input(collision: pd.DataFrame, vehicle_agg: pd.DataFrame) -> tuple[pd.DataFrame, pd.Series]:
    dataset = collision.merge(vehicle_agg, on="collision_index", how="left")
    y = dataset["target"].copy()
    X = dataset.drop(columns=list(LEAKAGE_COLUMNS | {"target", "date", "time"}), errors="ignore")

    numeric_columns = [column for column in X.columns if column in NUMERIC_COLUMNS]
    categorical_columns = [column for column in X.columns if column not in numeric_columns]

    for column in categorical_columns:
        X[column] = X[column].astype("string")

    return X, y


def build_training_pipeline(X: pd.DataFrame) -> Pipeline:
    numeric_columns = [column for column in X.columns if column in NUMERIC_COLUMNS]
    categorical_columns = [column for column in X.columns if column not in numeric_columns]

    preprocessor = ColumnTransformer(
        transformers=[
            (
                "num",
                Pipeline(
                    steps=[
                        ("imputer", SimpleImputer(strategy="median")),
                        ("scaler", StandardScaler()),
                    ]
                ),
                numeric_columns,
            ),
            (
                "cat",
                Pipeline(
                    steps=[
                        ("imputer", SimpleImputer(strategy="most_frequent")),
                        (
                            "encoder",
                            OneHotEncoder(
                                handle_unknown="infrequent_if_exist",
                                min_frequency=20,
                            ),
                        ),
                    ]
                ),
                categorical_columns,
            ),
        ],
        remainder="drop",
    )

    classifier = LogisticRegression(
        solver="saga",
        class_weight="balanced",
        max_iter=800,
        random_state=42,
    )


    return Pipeline(
        steps=[
            ("preprocessor", preprocessor),
            ("classifier", classifier),
        ]
    )


def evaluate_model(model: Pipeline, X_test: pd.DataFrame, y_test: pd.Series) -> dict:
    predictions = model.predict(X_test)
    labels = ["Fatal", "Serious", "Slight"]
    report = classification_report(y_test, predictions, labels=labels, output_dict=True, zero_division=0)
    confusion = confusion_matrix(y_test, predictions, labels=labels)
    return {
        "accuracy": round(float(accuracy_score(y_test, predictions)), 6),
        "macro_f1": round(float(f1_score(y_test, predictions, average="macro")), 6),
        "weighted_f1": round(float(f1_score(y_test, predictions, average="weighted")), 6),
        "classification_report": report,
        "confusion_matrix": {
            "labels": labels,
            "matrix": confusion.tolist(),
        },
    }


def run_dummy_baseline(X_train: pd.DataFrame, y_train: pd.Series, X_test: pd.DataFrame, y_test: pd.Series) -> dict:
    baseline = DummyClassifier(strategy="prior")
    baseline.fit(X_train, y_train)
    predictions = baseline.predict(X_test)
    return {
        "accuracy": round(float(accuracy_score(y_test, predictions)), 6),
        "macro_f1": round(float(f1_score(y_test, predictions, average="macro")), 6),
        "weighted_f1": round(float(f1_score(y_test, predictions, average="weighted")), 6),
    }


def maybe_sample(X: pd.DataFrame, y: pd.Series, sample_size: int, random_state: int) -> tuple[pd.DataFrame, pd.Series]:
    if sample_size <= 0 or sample_size >= len(X):
        return X, y

    sampled = X.copy()
    sampled["__target__"] = y.values
    per_class = max(1, sample_size // max(1, y.nunique()))
    sampled = sampled.groupby("__target__", group_keys=False).apply(
        lambda part: part.sample(min(len(part), per_class), random_state=random_state)
    )
    y_sampled = sampled.pop("__target__")
    return sampled.reset_index(drop=True), y_sampled.reset_index(drop=True)


def main() -> None:
    args = parse_args()
    args.output_dir.mkdir(parents=True, exist_ok=True)

    collision = load_collision_frame(args.collision_csv)
    vehicle = load_vehicle_frame(args.vehicle_csv)
    vehicle_agg = aggregate_vehicle_features(vehicle)
    X, y = build_model_input(collision, vehicle_agg)
    X, y = maybe_sample(X, y, args.sample_size, args.random_state)

    X_train, X_test, y_train, y_test = train_test_split(
        X,
        y,
        test_size=args.test_size,
        random_state=args.random_state,
        stratify=y,
    )

    model = build_training_pipeline(X_train)
    model.fit(X_train, y_train)

    model_metrics = evaluate_model(model, X_test, y_test)
    dummy_metrics = run_dummy_baseline(X_train, y_train, X_test, y_test)

    metrics = {
        "dataset": {
            "collision_rows": int(len(collision)),
            "vehicle_rows": int(len(vehicle)),
            "vehicle_agg_rows": int(len(vehicle_agg)),
            "train_rows": int(len(X_train)),
            "test_rows": int(len(X_test)),
            "feature_count": int(X.shape[1]),
            "target_distribution": y.value_counts().to_dict(),
        },
        "baseline": dummy_metrics,
        "model": model_metrics,
        "artifacts": {
            "model_path": str((args.output_dir / "collision_severity_pipeline.joblib").resolve()),
            "metrics_path": str((args.output_dir / "collision_severity_metrics.json").resolve()),
        },
    }

    joblib.dump(model, args.output_dir / "collision_severity_pipeline.joblib")
    (args.output_dir / "collision_severity_metrics.json").write_text(
        json.dumps(metrics, ensure_ascii=False, indent=2),
        encoding="utf-8",
    )

    print(json.dumps(metrics, ensure_ascii=False, indent=2))


if __name__ == "__main__":
    main()
