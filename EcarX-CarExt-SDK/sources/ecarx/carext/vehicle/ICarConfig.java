//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;


import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public interface ICarConfig<T> {

    @IntDef({
            VEHICLE_PROPERTY_ACCESS_NONE,
            VEHICLE_PROPERTY_ACCESS_READ,
            VEHICLE_PROPERTY_ACCESS_WRITE,
            VEHICLE_PROPERTY_ACCESS_READ_WRITE
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface VehiclePropertyAccessType {
    }

    /**
     * Property Access Unknown
     */
    int VEHICLE_PROPERTY_ACCESS_NONE = 0;
    /**
     * The property is readable
     */
    int VEHICLE_PROPERTY_ACCESS_READ = 1;
    /**
     * The property is writable
     */
    int VEHICLE_PROPERTY_ACCESS_WRITE = 2;
    /**
     * The property is readable and writable
     */
    int VEHICLE_PROPERTY_ACCESS_READ_WRITE = 3;

    @IntDef({
            VEHICLE_PROPERTY_CHANGE_MODE_STATIC,
            VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE,
            VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS,
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface VehiclePropertyChangeModeType {
    }

    /**
     * Properties of this type must never be changed.
     */
    int VEHICLE_PROPERTY_CHANGE_MODE_STATIC = 0;
    /**
     * Properties of this type must report when there is a change.
     */
    int VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE = 1;
    /**
     * Properties of this type change continuously.
     */
    int VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS = 2;

    @VehiclePropertyAccessType
    int getAccess();

    @VehicleAreaType.VehicleAreaTypeValue
    int getAreaType();

    @VehiclePropertyChangeModeType
    int getChangeMode();

    int getPropertyId();

    @NonNull
    Class<T> getPropertyType();

    boolean isGlobalProperty();

    @NonNull
    int[] getAreaIds();

    @Nullable
    T getMinValue(int areaId);

    @Nullable
    T getMaxValue(int areaId);

    @Nullable
    T getMinValue();

    @Nullable
    T getMaxValue();

    /**
     * If not applicable, pls return an empty array
     * @return support values array
     */
    @Nullable
    default T[] getSupportValues() {
        return null;
    }
}
