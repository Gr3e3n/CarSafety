//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * CC值以及一些静态信息
 */
public interface ICarInfo {
    /**
     * Time unit setting, original ID: FUNC_UNIT_TIME_INDICATION
     * 时间显示单位设置，原 ID 为 FUNC_UNIT_TIME_INDICATION
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link TimeUnit} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 14:35:16
     * @deprecated Settings.System.TIME_12_24 --- by hang.jiang@ecarxgroup.com
     */
    @Deprecated
    int UNIT_TIME_FORMAT = 0x0001e800;

    /**
     * Vehicle maintenance state
     * 整车保养状态  
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 具体值由业务约定 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 15:14:45
     */
    int VEHICLE_MAINTENANCE_STATE = 0x0001f000;

    /**
     * The remaining mileage before the next maintenance, positive numbers represent surplus, negative numbers represent excess, original ID TYPE_MAINTENANCE_MILEAGE
     * 整车保养剩余里程，正数代表剩余，负数代表超出，原 ID 为 TYPE_MAINTENANCE_MILEAGE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type 单位 km 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 15:18:27
     */
    int VEHICLE_MAINTENANCE_MILEAGE = 0x0001f100;

    /**
     * The remaining time before the next maintenance, positive numbers represent surplus, negative numbers represent excess, original ID is TYPE_MAINTENANCE_TIME
     * 整车保养剩余时间，正数代表剩余，负数代表超出，原 ID 为 TYPE_MAINTENANCE_TIME
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type 单位 day 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 15:20:17
     */
    int VEHICLE_MAINTENANCE_TIME = 0x0001f200;

    /**
     * Engine maintenance state
     * 发动机保养状态 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 具体值由业务自行约定 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 15:25:09
     */
    int ENGINE_MAINTENANCE_STATE = 0x0001f300;

    /**
     * The remaining mileage before the next maintenance，Positive numbers represent surplus, negative numbers represent excess, original ID TYPE_ADDITIONAL_ENDURANCE_ENGINE_MILEAGE
     * 发动机保养剩余里程，正数代表剩余，负数代表超出，原 ID 为 TYPE_ADDITIONAL_ENDURANCE_ENGINE_MILEAGE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type 单位 km 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 15:27:05
     */
    int ENGINE_MAINTENANCE_MILEAGE = 0x0001f400;

    /**
     * The remaining time before the next maintenance, positive numbers represent surplus, negative numbers represent excess, original ID TYPE_ADDITIONAL_ENDURANCE_ENGINE_TIME
     * 发动机保养剩余时间，正数代表剩余，负数代表超出，原 ID 为 TYPE_ADDITIONAL_ENDURANCE_ENGINE_TIME
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type 单位 day 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 15:28:11
     */
    int ENGINE_MAINTENANCE_TIME = 0x0001f500;

    /**
     * vehicle manitenance time cycle, one year(365 days) etc
     * 整车保养周期，比如一年(365天)
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     *  <li>{@code Integer} property type unit:day 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-11-11 11:32:25
     */
    int VEHICLE_MAINTENANCE_TIME_CYCLE = 0x00026d00;

    /**
     * vehicle maintenance mileage cycle, 10000km etc
     * 整车保养里程周期, 比如 一万公里一保养
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     *  <li>{@code Integer} property type unit:km 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-11-11 11:33:53
     */
    int VEHICLE_MAINTENANCE_MILEAGE_CYCLE = 0x00026e00;

    @IntDef({UNIT_TIME_FORMAT,
            VEHICLE_MAINTENANCE_STATE,
            VEHICLE_MAINTENANCE_MILEAGE,
            VEHICLE_MAINTENANCE_TIME,
            ENGINE_MAINTENANCE_STATE,
            ENGINE_MAINTENANCE_MILEAGE,
            ENGINE_MAINTENANCE_TIME,
            VEHICLE_MAINTENANCE_TIME_CYCLE,
            VEHICLE_MAINTENANCE_MILEAGE_CYCLE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({TimeUnit.UNIT_12,
            TimeUnit.UNIT_24,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface TimeUnit {
        /**
         * 12小时制
         */
        int UNIT_12 = 0x01;

        /**
         * 24小时制
         */
        int UNIT_24 = 0x02;
    }
}
