//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 座椅调节
 */
public interface ISeat {
    /**
     * Seat occupancy status, determine if there is anyone on the seat
     * 座椅占位情况，判断座椅上是否有人，原ID：SETTING_SEAT_OCCUPANCY_STATUS、SETTING_FRONT_PASSENGER_SEAT_STATUS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 14:57:38
     * @deprecated using {@link android.car.VehiclePropertyIds#SEAT_OCCUPANCY} instead --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int SEAT_OCCUPANCY_STATUS = 0x0001ee00;

    /**
     * Seat adjustment: seat mode adjustment
     * 座椅调节：座椅模式调节
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link SeatMode} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-22 14:50:02
     */
    int SEAT_MODE_ADJUST = 0x00028200;

    /**
     * Seat adjustment physical button status
     * 座椅调节物理按键 硬按键 状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-02-28 17:55:35
     */
    int SEAT_ADJUST_HARD_SWITCH_STATUS = 0x00031f00;

    @IntDef({SeatMode.MODE1,
            SeatMode.MODE2,
            SeatMode.MODE3,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface SeatMode {
        /**
         * 模式1
         */
        int MODE1 = 0x01;

        /**
         * 模式2
         */
        int MODE2 = 0x02;

        /**
         * 模式3
         */
        int MODE3 = 0x03;
    }

    @IntDef({SEAT_OCCUPANCY_STATUS,
            SEAT_MODE_ADJUST,
            SEAT_ADJUST_HARD_SWITCH_STATUS,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
