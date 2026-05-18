//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 传感器，虚拟传感器，传感器事件，都放这里吧
 */
public interface ISensor {
    /**
     * Endurance mileage base on Charging SOC, unit: km 
     * 充电限制电量(SOC)可行驶距离，单位 km
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 17:36:04
     */
    int CHARGING_ENDURANCE_MILEAGE = 0x00016500;

    /**
     * Endurance mileage base on Discharging SOC, unit: km
     * 放电限制电量(SOC)可行驶距离，单位 km
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 17:37:13
     */
    int DISCHARGING_ENDURANCE_MILEAGE = 0x00016600;

    /**
     * Oil level icon warning color
     * 油量图标警告颜色
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link FuelWarningColor} property type
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 17:43:36
     */
    int FUEL_WARNING_COLOR = 0x00016900;

    /**
     * average fuel consumption, unit: L/100km 
     * 平均油耗，单位：L/100km，小计里程VehicleAreaMirror.MIRROR_DRIVER_LEFT，本次里程VehicleAreaMirror.MIRROR_DRIVER_RIGHT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_MIRROR}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-10 15:31:26
     */
    int AVG_FUEL_CONSUMPTION = 0x00017800;

    /**
     * Current fuel level in percentage, unit: % 
     * 当前剩余油量百分比
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-11 14:51:46
     */
    int FUEL_PERCENTAGE = 0x00017c00;

    /**
     * Fuel endurance mileage of the car, unit: Kilometer(km) 
     * 当前油可续航里程
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-11 14:52:36
     */
    int ENDURANCE_MILEAGE_FUEL = 0x00017d00;

    /**
     * Indoor PM2.5, unit: &mu;g/m3
     * 车内PM2.5质量数值，单位&mu;g/m3
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Float} property type  
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-12-05 15:43:34
     */
    int PM25_INDOOR = 0x00029d00;

    /**
     * indoor Air Quality Index(AQI)
     * 车内 空气质量指数AQI （原adaptApi接口SENSOR_TYPE_AQI_INDOOR）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Float} property type  
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-12-11 14:48:52
     */
    int AQI_INDOOR = 0x0002ac00;

    @IntDef({CHARGING_ENDURANCE_MILEAGE,
            DISCHARGING_ENDURANCE_MILEAGE,
            FUEL_WARNING_COLOR,
            AVG_FUEL_CONSUMPTION,
            FUEL_PERCENTAGE,
            ENDURANCE_MILEAGE_FUEL,
            PM25_INDOOR,
            AQI_INDOOR,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({FuelWarningColor.FUEL_WARNING_COLOR_NORMAL,
            FuelWarningColor.FUEL_WARNING_COLOR_YELLOW,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface FuelWarningColor {
        /**
         * 油量图标显示为默认色
         */
        int FUEL_WARNING_COLOR_NORMAL = 0x01;

        /**
         * 油量图标显示为黄色
         */
        int FUEL_WARNING_COLOR_YELLOW = 0x02;
    }
}
