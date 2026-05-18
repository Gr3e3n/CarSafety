//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Day mode brightness functions (CSD/PSD/RSD),  \nIf you want to change the theme of the application, try to use {@link android.app.UiModeManager}  \nand application resource with -night or -notnight
 */
public interface IDayMode {
    /**
     * Day mode setting&lt;br&gt;
     * 日夜模式设置&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DayModeSettings} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-18 21:00:23
     */
    int DAYMODE_SETTING = 0x00008700;

    /**
     * CSD brightness value
     * CSD当前亮度值，屏幕亮度，原ID为SETTING_FUNC_BRIGHTNESS,老版本是float，改成int
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 11:37:35
     * @deprecated using {@link android.car.VehiclePropertyIds#DISPLAY_BRIGHTNESS} instead. --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int FUNC_BRIGHTNESS = 0x0000ae00;

    /**
     * Max brightness of CCD, read only
     * CCD(CSD/PSD/RSD)最大亮度，原类型float，改成int,原ID为SETTING_FUNC_BRIGHTNESS_MAX
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 11:39:22
     */
    int FUNC_BRIGHTNESS_MAX = 0x0000af00;

    /**
     * Min brightness of CCD, read only
     * CCD(CSD/PSD/RSD)最小亮度，原ID为SETTING_FUNC_BRIGHTNESS_MIN
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 11:40:01
     */
    int FUNC_BRIGHTNESS_MIN = 0x0000b000;

    /**
     * diff mode light adjust
     * 选项：白天模式、夜间模式、自动模式，不同模式的亮度调节
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DayModeSettings} property type
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-07-03 09:14:08
     */
    int BRIGHTNESS_DAYMODE = 0x00010a00;

    /**
     * Sunrise and sunset mode time (24h)
     * 日出日落模式时间(24h)
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 比如设置10201830，代表了日出10:20，日落18:30 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-07-15 14:21:05
     */
    int SUN_TIME = 0x00018000;

    @IntDef({DAYMODE_SETTING,
            FUNC_BRIGHTNESS,
            FUNC_BRIGHTNESS_MAX,
            FUNC_BRIGHTNESS_MIN,
            BRIGHTNESS_DAYMODE,
            SUN_TIME,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({DayModeSettings.DAY,
            DayModeSettings.NIGHT,
            DayModeSettings.AUTO,
            DayModeSettings.CUSTOM,
            DayModeSettings.SUNRISE_AND_SUNSET,
            DayModeSettings.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DayModeSettings {
        int DAY = 0x01;

        int NIGHT = 0x02;

        /**
         * 自动
         */
        int AUTO = 0x03;

        int CUSTOM = 0x04;

        /**
         * 依据日出日落时间
         */
        int SUNRISE_AND_SUNSET = 0x05;

        /**
         * 关闭
         */
        int OFF = 0xff;
    }
}
