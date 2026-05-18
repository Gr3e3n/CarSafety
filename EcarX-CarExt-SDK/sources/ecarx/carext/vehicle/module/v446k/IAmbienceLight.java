//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 氛围灯
 */
public interface IAmbienceLight {
    /**
     * Atmosphere light theme color, original ID: SETTING-FUNC_AMBIENCE_LIGHT_THEME-COLOR
     * 氛围灯主题颜色，原ID：SETTING_FUNC_AMBIENCE_LIGHT_THEME_COLOR
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link AmbienceLightThemeColor} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-10-30 16:26:07
     */
    int AMBIENCE_LIGHT_THEME_COLOR = 0x00026000;

    /**
     * Atmosphere light linkage light effect, original ID: SETTING-FUNC_AMBIENCE_LIGHT_MUSIC_SHOWMOD
     * 氛围灯联动灯效，原ID：SETTING_FUNC_AMBIENCE_LIGHT_MUSIC_SHOWMODE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-04 10:10:23
     */
    int AMBIENCE_LIGHT_MUSIC_SHOWMODE = 0x00026300;

    /**
     * Voice status activation broadcast, etc. (inactive status is 0, not defined in enumeration)
     * 语音状态, 激活 广播等，(未激活状态为0，未定义在枚举中)
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link VRActivated} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-02-28 14:39:58
     */
    int VR_ACTIVATED = 0x00031e00;

    @IntDef({AmbienceLightThemeColor.ITEM1,
            AmbienceLightThemeColor.ITEM2,
            AmbienceLightThemeColor.ITEM3,
            AmbienceLightThemeColor.ITEM4,
            AmbienceLightThemeColor.ITEM5,
            AmbienceLightThemeColor.ITEM6,
            AmbienceLightThemeColor.ITEM7,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface AmbienceLightThemeColor {
        /**
         * 白光
         */
        int ITEM1 = 0x01;

        /**
         * 北欧森林
         */
        int ITEM2 = 0x02;

        /**
         * 群岛霞光
         */
        int ITEM3 = 0x03;

        /**
         * 盛夏光年
         */
        int ITEM4 = 0x04;

        /**
         * 仲夏之夜
         */
        int ITEM5 = 0x05;

        /**
         * 北欧极光
         */
        int ITEM6 = 0x06;

        /**
         * 月幕银光
         */
        int ITEM7 = 0x07;
    }

    @IntDef({VRActivated.ACTIVATED_ON_LEFT_SEAT,
            VRActivated.ACTIVATED_ON_RIGHT_SEAT,
            VRActivated.IS_BROADCASTING,
            VRActivated.ACTIVATED_ON_FRONT_LEFT_SEAT,
            VRActivated.ACTIVATED_ON_FRONT_RIGHT_SEAT,
            VRActivated.ACTIVATED_ON_LEFT_REAR_SEAT,
            VRActivated.ACTIVATED_ON_RIGHT_REAR_SEAT,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface VRActivated {
        int ACTIVATED_ON_LEFT_SEAT = 0x01;

        int ACTIVATED_ON_RIGHT_SEAT = 0x02;

        int IS_BROADCASTING = 0x03;

        int ACTIVATED_ON_FRONT_LEFT_SEAT = 0x04;

        int ACTIVATED_ON_FRONT_RIGHT_SEAT = 0x05;

        int ACTIVATED_ON_LEFT_REAR_SEAT = 0x06;

        int ACTIVATED_ON_RIGHT_REAR_SEAT = 0x07;
    }

    @IntDef({AMBIENCE_LIGHT_THEME_COLOR,
            AMBIENCE_LIGHT_MUSIC_SHOWMODE,
            VR_ACTIVATED,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
