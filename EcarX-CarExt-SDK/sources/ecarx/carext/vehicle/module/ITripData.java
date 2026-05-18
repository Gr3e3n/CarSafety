//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 行程数据
 */
public interface ITripData {
    /**
     * Driving info: Average speed of current trip, Unit: km/h&lt;br&gt;
     * 本次行程平均车速(km/h)
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_MIRROR}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 21:38:19
     */
    int TRIP_DI_AVG_SPEED = 0x00008b00;

    /**
     * riving info: Distance of current trip, Unit: km&lt;br&gt;
     * 本次行程距离(km)&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_MIRROR}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 21:39:16
     */
    int TRIP_DI_TOTAL_DISTANCE = 0x00008c00;

    /**
     * Request to reset the special trip data 
     * 重置Trip数据&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 21:42:58
     */
    int TRIP_FUNC_RESET = 0x00008d00;

    /**
     * Average power consumption array, one use case is to display energy consumption curve
     * 平均电耗数组,（一个使用场景是显示能耗曲线）&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_MIRROR}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float[]} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 21:44:43
     */
    int TRIP_DI_AVG_ELC_CONSUMPTION_ARRAY = 0x00008f00;

    /**
     * The setting option of Auto reset {@link #RESET_OPTION_AUTO} the special trip data with {@link TripType}&lt;br&gt;
     * 自动重置Trip数据设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link AutoResetTripOption} property type
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 21:52:14
     */
    int TRIP_FUNC_AUTO_RESET_OPTION = 0x00009200;

    /**
     * Driving info: Duration of current trip, Unit: s
     * 本次行程时间 小计里程(trip1)：MIRROR_DRIVER_LEFT 本次里程(trip2)：MIRROR_DRIVER_RIGHT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_MIRROR}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project hongqi at 2024-06-22 14:06:15
     */
    int TRIP_DI_TOTAL_DURATION = 0x0000e100;

    @IntDef({TRIP_DI_AVG_SPEED,
            TRIP_DI_TOTAL_DISTANCE,
            TRIP_FUNC_RESET,
            TRIP_DI_AVG_ELC_CONSUMPTION_ARRAY,
            TRIP_FUNC_AUTO_RESET_OPTION,
            TRIP_DI_TOTAL_DURATION,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({AutoResetTripOption.AUTO_RESET_OPTION_4_HOURS,
            AutoResetTripOption.AUTO_RESET_OPTION_CHARGING,
            AutoResetTripOption.AUTO_RESET_OPTION_PARKING_OIL,
            AutoResetTripOption.AUTO_RESET_OPTION_PARKING,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface AutoResetTripOption {
        int AUTO_RESET_OPTION_4_HOURS = 0x01;

        int AUTO_RESET_OPTION_CHARGING = 0x02;

        int AUTO_RESET_OPTION_PARKING_OIL = 0x03;

        int AUTO_RESET_OPTION_PARKING = 0x04;
    }
}
