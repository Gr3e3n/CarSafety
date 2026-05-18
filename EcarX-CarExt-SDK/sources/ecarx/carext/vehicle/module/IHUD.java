//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 抬头显示
 */
public interface IHUD {
    /**
     * to activate and deactivate head-up display
     * 抬头显示开关，HUD抬头显示，原ID为SETTING_FUNC_HUD_ACTIVE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 17:58:37
     */
    int HUD_ACTIVE = 0x00006000;

    /**
     * Request to adjust the HUD angle
     * 激活HUD角度调节,原ID为SETTING_FUNC_HUD_ANGLE_ADJUST
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 18:05:24
     */
    int HUD_ANGLE_ADJUST = 0x00006300;

    /**
     * Request to adjust the HUD brightness
     * 激活HUD亮度调节,原ID为SETTING_FUNC_HUD_BRIGHTNESS_ADJUST
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 18:08:09
     */
    int HUD_BRIGHTNESS_ADJUST = 0x00006400;

    /**
     * HUD display Navigation information
     *  HUD 显示导航信息设置，原ID为SETTING_FUNC_HUD_DISPLAY_NAVI
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 18:09:51
     */
    int HUD_DISPLAY_NAVI = 0x00006600;

    /**
     *  Open or close snow mode of HUD
     * HUD 雪地模式，原ID为SETTING_FUNC_HUD_SNOW_MODE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 18:11:32
     */
    int HUD_SNOW_MODE = 0x00006800;

    /**
     * Head-up display(HUD) display mode&lt;br&gt;
     * 抬头显(HUD)显示模式&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HudDisplayMode} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-18 21:43:01
     */
    int HUD_DISPLAY_MODE = 0x00008e00;

    /**
     * HUD angle adjustment, original ID: SETTING_FUNC_HUD_ANGLE_TRANSMIT
     * HUD 角度调节,原 ID 为 SETTING_FUNC_HUD_ANGLE_TRANSMIT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 取值范围可通过 ICarConfig  获取 
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-08-26 15:28:44
     */
    int HUD_ANGLE = 0x00021600;

    /**
     * HUD brightness adjustment, original ID: SETTING_FUNC_HUD_BRIGHTNESS_TRANSMIT.
     * HUD 亮度调节，原 ID 为 SETTING_FUNC_HUD_BRIGHTNESS_TRANSMIT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 取值范围可通过 ICarConfig 获取  
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-08-27 11:31:47
     */
    int HUD_BRIGHTNESS = 0x00021a00;

    /**
     * Notify HUD to enter/exit AR HUD adjustment page.
     * 通知 HUD 进入/退出 AR HUD 调节页面
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type ture - 进入 ， false - 退出 
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-08-28 11:34:26
     */
    int HUD_NOTIFY_AR_ADJUST = 0x00021d00;

    @IntDef({HudDisplayMode.SIMPLE,
            HudDisplayMode.MAP,
            HudDisplayMode.STANDARD,
            HudDisplayMode.AR,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface HudDisplayMode {
        /**
         * display mode: simple
         */
        int SIMPLE = 0x01;

        /**
         * display mode: map
         */
        int MAP = 0x02;

        /**
         * standard
         */
        int STANDARD = 0x03;

        /**
         * display mode: AR
         */
        int AR = 0x04;
    }

    @IntDef({HUD_ACTIVE,
            HUD_ANGLE_ADJUST,
            HUD_BRIGHTNESS_ADJUST,
            HUD_DISPLAY_NAVI,
            HUD_SNOW_MODE,
            HUD_DISPLAY_MODE,
            HUD_ANGLE,
            HUD_BRIGHTNESS,
            HUD_NOTIFY_AR_ADJUST,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
