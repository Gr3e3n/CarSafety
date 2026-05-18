//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 安全设置
 */
public interface ISafety {
    /**
     *  Open or close approach to unlock
     * 接近解锁，原ID为SETTING_FUNC_APPROACH_UNLOCK
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 16:14:05
     */
    int APPROACH_UNLOCK = 0x00005600;

    /**
     * Open or close walk away to lock
     * 离开锁车，原ID为SETTING_FUNC_AWAY_LOCK
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 16:14:48
     */
    int AWAY_LOCK = 0x00005700;

    /**
     * a setting for keyless unlocking
     * 无钥匙解锁设置,原ID为SETTING_FUNC_KEYLESS_UNLOCKING
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link KeylessUnlockingValue} property type
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 17:47:00
     */
    int KEYLESS_UNLOCKING = 0x00005900;

    /**
     * Central locking switch setting
     * 车辆设置：中控锁开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-16 10:32:31
     */
    int CENTRAL_LOCK = 0x00018700;

    @IntDef({APPROACH_UNLOCK,
            AWAY_LOCK,
            KEYLESS_UNLOCKING,
            CENTRAL_LOCK,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({KeylessUnlockingValue.KEYLESS_UNLOCKING_ALL_DOORS,
            KeylessUnlockingValue.KEYLESS_UNLOCKING_SINGLE_DOOR,
            KeylessUnlockingValue.KEYLESS_UNLOCKING_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface KeylessUnlockingValue {
        /**
         * keyless unlocking: all doors (central locking)
         */
        int KEYLESS_UNLOCKING_ALL_DOORS = 0x01;

        /**
         * keyless unlocking: single door (driver door)
         */
        int KEYLESS_UNLOCKING_SINGLE_DOOR = 0x02;

        /**
         * keyless unlocking: off
         */
        int KEYLESS_UNLOCKING_OFF = 0xff;
    }
}
