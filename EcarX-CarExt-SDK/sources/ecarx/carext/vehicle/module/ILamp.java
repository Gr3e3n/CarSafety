//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 车外灯
 */
public interface ILamp {
    /**
     * the user can change settings for home safe light
     * 伴我回家灯时间，原ID为SETTING_FUNC_LAMP_HOME_SAFE_LIGHT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HomeSafeLightValue} property type
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 09:37:41
     */
    int LAMP_HOME_SAFE_LIGHT = 0x00009500;

    /**
     * Setting light time Of The approaching lighting
     * 设置接近照明的时间,原ID为SETTING_FUNC_APPROACHING_LIGHTING_TIME
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link LightingTimeLevel} property type
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 09:41:09
     */
    int APPROACHING_LIGHTING_TIME = 0x00009600;

    /**
     * to change between off and on for automatic courtesy light setting
     * 自动礼貌灯设置(门控灯),开门点亮座舱，原ID为SETTING_FUNC_LAMP_AUTOMATIC_COURTESY_LIGHT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 09:42:51
     */
    int LAMP_AUTOMATIC_COURTESY_LIGHT = 0x00009700;

    /**
     * Exterior light control system indicates which light the driver has chosen
     * 外灯控制选择(设置)，原ID为SETTING_FUNC_LAMP_EXTERIOR_LIGHT_CONTROL
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link ExteriorLightControlValue} property type
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 09:55:24
     */
    int LAMP_EXTERIOR_LIGHT_CONTROL = 0x00009b00;

    /**
     * Front position light status, original ID: FRONT_POSITION_STATUS
     * 前位置灯，原 ID 为FRONT_POSTITION_STATUS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-16 15:56:57
     */
    int LAMP_FRONT_POSITION = 0x00018a00;

    /**
     * Rear position light status, original ID: AFTER_POSITION_STATUS
     * 后位置灯，原 ID 为 AFTER_POSTITION_STATUS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-16 15:58:38
     */
    int LAMP_REAR_POSITION = 0x00018b00;

    /**
     * Daytime running light status, original ID: DAYTIME_LIGHT_STATUS
     * 日间行车灯，原 ID 为 DAYTIME_LIGHT_STATUS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-16 15:59:45
     */
    int LAMP_DAYTIME_LIGHT = 0x00018c00;

    @IntDef({HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_10S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_20S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_30S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_40S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_50S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_60S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_70S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_80S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_90S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_100S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_110S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_120S,
            HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface HomeSafeLightValue {
        int HOME_SAFE_LIGHT_VALUE_10S = 0x01;

        int HOME_SAFE_LIGHT_VALUE_20S = 0x02;

        int HOME_SAFE_LIGHT_VALUE_30S = 0x03;

        int HOME_SAFE_LIGHT_VALUE_40S = 0x04;

        int HOME_SAFE_LIGHT_VALUE_50S = 0x05;

        int HOME_SAFE_LIGHT_VALUE_60S = 0x06;

        int HOME_SAFE_LIGHT_VALUE_70S = 0x07;

        int HOME_SAFE_LIGHT_VALUE_80S = 0x08;

        int HOME_SAFE_LIGHT_VALUE_90S = 0x09;

        int HOME_SAFE_LIGHT_VALUE_100S = 0x10;

        int HOME_SAFE_LIGHT_VALUE_110S = 0x11;

        int HOME_SAFE_LIGHT_VALUE_120S = 0x12;

        int HOME_SAFE_LIGHT_VALUE_OFF = 0xff;
    }

    @IntDef({LAMP_HOME_SAFE_LIGHT,
            APPROACHING_LIGHTING_TIME,
            LAMP_AUTOMATIC_COURTESY_LIGHT,
            LAMP_EXTERIOR_LIGHT_CONTROL,
            LAMP_FRONT_POSITION,
            LAMP_REAR_POSITION,
            LAMP_DAYTIME_LIGHT,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({LightingTimeLevel.LIGHTING_TIME_LEVEL1,
            LightingTimeLevel.LIGHTING_TIME_LEVEL2,
            LightingTimeLevel.LIGHTING_TIME_LEVEL3,
            LightingTimeLevel.LIGHTING_TIME_LEVEL4,
            LightingTimeLevel.LIGHTING_TIME_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface LightingTimeLevel {
        int LIGHTING_TIME_LEVEL1 = 0x01;

        int LIGHTING_TIME_LEVEL2 = 0x02;

        int LIGHTING_TIME_LEVEL3 = 0x03;

        int LIGHTING_TIME_LEVEL4 = 0x04;

        int LIGHTING_TIME_OFF = 0xff;
    }

    @IntDef({ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_POS_,
            ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_LOWB,
            ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_AUTO,
            ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_AHBC,
            ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface ExteriorLightControlValue {
        /**
         * Exterior light control: Position light(位置灯)
         */
        int LAMP_EXTERIOR_LIGHT_CONTROL_POS_ = 0x01;

        /**
         * Exterior light control: Low beam(近光灯)
         */
        int LAMP_EXTERIOR_LIGHT_CONTROL_LOWB = 0x02;

        /**
         * Exterior light control: Automatic(自动)
         */
        int LAMP_EXTERIOR_LIGHT_CONTROL_AUTO = 0x03;

        /**
         * Exterior light control: Adaptive High Beam(自动远光灯)
         */
        int LAMP_EXTERIOR_LIGHT_CONTROL_AHBC = 0x04;

        /**
         * Exterior light control: off
         */
        int LAMP_EXTERIOR_LIGHT_CONTROL_OFF = 0xff;
    }
}
