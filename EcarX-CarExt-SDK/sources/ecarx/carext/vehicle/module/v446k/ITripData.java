//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 行程数据
 */
public interface ITripData {
    /**
     * x axis sampling interval value: Float
     * 曲线图x轴采样点间隔 value: Float
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 19:18:59
     */
    int TRIP_DOT_INTERVAL = 0x00017200;

    /**
     * Energy flow during driving and energy recovery (unit: %)
     * 行驶及能量回收时的能量流（单位：%）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-10 15:12:48
     */
    int TRIP_ED_DRIVING_ENERGY_FLOW = 0x00017300;

    /**
     * Energy flow of air conditioner during operation (unit: %)
     * 空调工作时的能量流（单位：%）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-10 15:14:21
     */
    int TRIP_ED_CLIMATE_ENERGY_FLOW = 0x00017400;

    /**
     * Energy flow during battery thermal management (unit: %)
     * 电池热管理时的能量流（单位：%）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-10 15:15:06
     */
    int TRIP_ED_BATTERY_ENERGY_FLOW = 0x00017500;

    /**
     * Energy flows from other systems (%)
     * 其他系统的能量流（单位：%）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-10 15:20:06
     */
    int TRIP_ED_OTHER_ENERGY_FLOW = 0x00017600;

    /**
     * Average fuel consumption array
     * 平均油耗数组，小计里程VehicleAreaMirror.MIRROR_DRIVER_LEFT，本次里程VehicleAreaMirror.MIRROR_DRIVER_RIGHT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_MIRROR}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float[]} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-10 15:34:08
     */
    int TRIP_DI_AVG_FUEL_CONSUMPTION_ARRAY = 0x00017900;

    /**
     * Energy consumption curve display type value (fuel consumption/electricity consumption)
     * 能耗曲线显示类型值(油耗/电耗)
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link EnergyCurveDisplayMode} property type
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2025-03-31 15:47:04
     */
    int TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE = 0x00032900;

    @IntDef({EnergyCurveDisplayMode.FUEL_CONSUMPTION,
            EnergyCurveDisplayMode.ELECTRICITY_CONSUMPTION,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface EnergyCurveDisplayMode {
        /**
         * 油耗
         */
        int FUEL_CONSUMPTION = 0x01;

        /**
         * 电耗
         */
        int ELECTRICITY_CONSUMPTION = 0x02;
    }

    @IntDef({TRIP_DOT_INTERVAL,
            TRIP_ED_DRIVING_ENERGY_FLOW,
            TRIP_ED_CLIMATE_ENERGY_FLOW,
            TRIP_ED_BATTERY_ENERGY_FLOW,
            TRIP_ED_OTHER_ENERGY_FLOW,
            TRIP_DI_AVG_FUEL_CONSUMPTION_ARRAY,
            TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
