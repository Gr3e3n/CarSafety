from __future__ import annotations

import json
from pathlib import Path

import joblib

ROOT_DIR = Path(__file__).resolve().parents[2]
MODEL_PATH = Path(__file__).resolve().parent / "artifacts" / "collision_severity_pipeline.joblib"
OUTPUT_PATH = ROOT_DIR / "app" / "src" / "main" / "assets" / "collision_severity_model.json"

SELECTED_NUMERIC_COLUMNS = [
    "number_of_vehicles",
    "speed_limit",
    "collision_month",
    "collision_day",
    "collision_hour",
    "collision_minute",
    "is_weekend",
    "is_night",
    "vehicle_record_count",
]

SELECTED_CATEGORICAL_COLUMNS = [
    "day_of_week",
    "weather_conditions",
    "light_conditions",
    "road_surface_conditions",
    "urban_or_rural_area",
]


def export_runtime_model() -> None:
    pipeline = joblib.load(MODEL_PATH)
    preprocessor = pipeline.named_steps["preprocessor"]
    classifier = pipeline.named_steps["classifier"]

    num_columns = list(preprocessor.transformers_[0][2])
    num_pipeline = preprocessor.transformers_[0][1]
    cat_columns = list(preprocessor.transformers_[1][2])
    cat_pipeline = preprocessor.transformers_[1][1]

    numeric_defaults = dict(zip(num_columns, num_pipeline.named_steps["imputer"].statistics_))
    numeric_scales = dict(zip(num_columns, num_pipeline.named_steps["scaler"].scale_))
    categorical_defaults = {
        column: str(value)
        for column, value in zip(cat_columns, cat_pipeline.named_steps["imputer"].statistics_)
    }
    categorical_candidates = {
        column: [str(value) for value in cat_pipeline.named_steps["encoder"].categories_[cat_columns.index(column)]]
        for column in SELECTED_CATEGORICAL_COLUMNS
    }

    feature_names = list(preprocessor.get_feature_names_out())
    numeric_feature_index = {
        column: feature_names.index(f"num__{column}")
        for column in SELECTED_NUMERIC_COLUMNS
    }

    base_row = {**numeric_defaults, **categorical_defaults}
    base_frame = __import__("pandas").DataFrame([base_row])
    base_logits = classifier.decision_function(preprocessor.transform(base_frame))[0]

    export_payload = {
        "model_name": "collision_severity_pipeline.joblib-runtime",
        "source_joblib": str(MODEL_PATH.resolve()),
        "output_classes": [str(label) for label in classifier.classes_],
        "base_logits": [float(value) for value in base_logits],
        "numeric_features": {},
        "categorical_features": {},
    }

    for column in SELECTED_NUMERIC_COLUMNS:
        index = numeric_feature_index[column]
        export_payload["numeric_features"][column] = {
            "base_value": float(numeric_defaults[column]),
            "scale": float(numeric_scales[column]),
            "coefficients": [float(value) for value in classifier.coef_[:, index]],
        }

    for column in SELECTED_CATEGORICAL_COLUMNS:
        default_value = categorical_defaults[column]
        deltas: dict[str, list[float]] = {}
        for candidate in categorical_candidates[column]:
            row = dict(base_row)
            row[column] = candidate
            frame = __import__("pandas").DataFrame([row])
            logits = classifier.decision_function(preprocessor.transform(frame))[0]
            deltas[candidate] = [float(logits[i] - base_logits[i]) for i in range(len(base_logits))]

        export_payload["categorical_features"][column] = {
            "default_value": default_value,
            "deltas": deltas,
        }

    OUTPUT_PATH.parent.mkdir(parents=True, exist_ok=True)
    OUTPUT_PATH.write_text(json.dumps(export_payload, ensure_ascii=False, indent=2), encoding="utf-8")
    print(f"Runtime model exported to: {OUTPUT_PATH}")


if __name__ == "__main__":
    export_runtime_model()
