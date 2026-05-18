//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 座椅调节
 */
public interface ISeat {
    /**
     * the setting to activate/deactivate the Easy ingress and egress for Driver
     * 主驾方便进出/座椅舒适性下车，原ID为SETTING_FUNC_EASY_INGRESS_EGRESS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 15:46:30
     */
    int EASY_INGRESS_EGRESS = 0x00005100;

    /**
     * Adjust the backrest angle of the seat
     * 座椅靠背角度调节
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-15 18:08:28
     * @deprecated using {@link android.car.VehiclePropertyIds#SEAT_BACKREST_ANGLE_2_POS} instead --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int BACKREST_ANGLE_ADJUST = 0x00018300;

    /**
     * Seat longitudinal (up and down) adjustment
     * 座椅位置纵向（上下）调节
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-15 18:12:20
     * @deprecated using {@link android.car.VehiclePropertyIds#SEAT_HEIGHT_POS} instead. --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int SEAT_VERTICAL_ADJUST = 0x00018400;

    /**
     * Horizontal (front and rear) adjustment of seat position
     * 座椅位置横向（前后）调节
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-15 18:14:05
     * @deprecated using {@link android.car.VehiclePropertyIds#SEAT_FORE_AFT_POS} instead --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int SEAT_HORIZONTAL_ADJUST = 0x00018500;

    /**
     * Seat massage switch
     * 座椅按摩开关，原ID：HVAC_FUNC_SEAT_MASSAGE_SWITCH
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-24 14:46:16
     */
    int MASSAGE_SWITCH = 0x0001a700;

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

    @IntDef({EASY_INGRESS_EGRESS,
            BACKREST_ANGLE_ADJUST,
            SEAT_VERTICAL_ADJUST,
            SEAT_HORIZONTAL_ADJUST,
            MASSAGE_SWITCH,
            SEAT_OCCUPANCY_STATUS,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
