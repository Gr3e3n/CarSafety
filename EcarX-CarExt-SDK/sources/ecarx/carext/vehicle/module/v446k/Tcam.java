//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Tcam Tbox
 */
public interface Tcam {
    /**
     * Save the preset charging status and check whether the preset status is successful based on the callback
     * 保存预约充电状态，并根据回调判断是否设置成功
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-26 17:37:32
     */
    int BOOK_CHARGING_SAVE_STATUS = 0x0001b100;

    /**
     * Reserve charging switch type, off, single time, daily
     * 预约充电开关类型，关闭、单次、重复（每天）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link BookChargingMode} property type
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-08-16 13:56:09
     */
    int BOOK_CHARGING_MODE = 0x0001d000;

    /**
     * Pre-charge time setting, timestamp array, start time long[0], end time long[1]
     * 预约充电时间设置，时间戳数组，开始时间long[0]，结束时间long[1]
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Long[]} property type 时间戳，开始时间long[0]，结束时间long[1] 
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-08-16 14:17:37
     */
    int BOOK_CHARGING_TIME = 0x0001d100;

    /**
     *  TCAM/TBOX reset state
     * TCAM/TBOX 重置状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link TcamResetState} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 15:35:00
     */
    int TCAM_RESET_STATE = 0x0001f600;

    /**
     * Reserve charging switch type used last time , off, single time, daily
     * 上一次预约充电开关类型，关闭、单次、重复（每天）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link BookChargingMode} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-09-06 13:40:00
     */
    int BOOK_CHARGING_MODE_LAST_TIME = 0x00023700;

    @IntDef({TcamResetState.FAILED,
            TcamResetState.SUCCESS,
            TcamResetState.UNKNOW,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface TcamResetState {
        /**
         * 重置失败
         */
        int FAILED = 0x01;

        /**
         *  重置成功
         */
        int SUCCESS = 0x02;

        /**
         * 未知
         */
        int UNKNOW = 0xff;
    }

    @IntDef({BookChargingMode.OFF,
            BookChargingMode.SINGLE,
            BookChargingMode.CYCLE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface BookChargingMode {
        /**
         * 预约充电开关模式——关闭
         */
        int OFF = 0x01;

        /**
         * 预约充电开关模式——单次
         */
        int SINGLE = 0x02;

        /**
         * 预约充电开关模式——重复（每天）
         */
        int CYCLE = 0x03;
    }

    @IntDef({BOOK_CHARGING_SAVE_STATUS,
            BOOK_CHARGING_MODE,
            BOOK_CHARGING_TIME,
            TCAM_RESET_STATE,
            BOOK_CHARGING_MODE_LAST_TIME,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
