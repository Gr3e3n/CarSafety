//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 音频设置
 */
public interface IAudio {
    /**
     * 3D surround sound effect level setting, original ID: SETTING_FUNC_AUDIO_3D_SURROUND_SHOUND_EFFECT_LEVEL
     * 3D 环绕音效等级设置， 原 ID 为SETTING_FUNC_AUDIO_3D_SURROUND_SHOUND_EFFECT_LEVEL
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 具体值由业务约定 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 14:40:29
     */
    int AUDIO_3D_SURROUND_SOUND_EFFECT_LEVEL = 0x0001e900;

    /**
     * Head pillow speaker mode setting, original ID: SETTING_FUNC_AUDIO_HEAD_PILLOW_AUTO_MODE
     * 头枕音响模式设置，原 ID 为 SETTING_FUNC_AUDIO_HEAD_PILLOW_AUTO_MODE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HeadPillowModeType} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 14:45:33
     */
    int AUDIO_HEAD_PILLOW_AUTO_MODE = 0x0001eb00;

    /**
     * The setting for  head pillow switch on/off, original ID SETTING_FUNC_AUDIO_HEAD_PILLOW
     * 头枕模式智能切换开关，原 ID 为 SETTING_FUNC_AUDIO_HEAD_PILLOW
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 14:50:04
     */
    int AUDIO_HEAD_PILLOW = 0x0001ec00;

    /**
     * Audio setting for navigation mix mode , original ID SETTING_FUNC_AUDIO_NAVI_MIX_MODE
     * 导航混音模式，原 ID 为 SETTING_FUNC_AUDIO_NAVI_MIX_MODE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link NaviVoiceMixMode} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 14:57:31
     */
    int AUDIO_NAVI_MIX_MODE = 0x0001ed00;

    /**
     * Audible locking feedback/lock prompt sound switch, original ID: `SETTING_FUNC_AUDIBLE_LOCKING_FEEDBACK`.
     * 可听落锁反馈/锁车提示音开关，原 ID 为 SETTING_FUNC_AUDIBLE_LOCKING_FEEDBACK
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 15:04:02
     */
    int AUDIO_AUDIBLE_LOCKING_FEEDBACK = 0x0001ef00;

    /**
     * Customize sound effects settings
     * 自定义音效/音色设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-29 15:12:29
     */
    int AUDIO_TIMBRE_CUSTOM = 0x00022000;

    /**
     * Play the preview sound of the selected PDC level
     * 播放选择的PDC等级预览音
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-10-31 10:19:10
     */
    int PLAY_PDC_WARNING = 0x00026200;

    /**
     * KTV mode（original AdaptAPI：IAudio.SETTING_FUNC_AUDIO_KTV_MODE）
     * KTV模式（原AdaptAPI：IAudio.SETTING_FUNC_AUDIO_KTV_MODE）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-03-06 14:40:47
     */
    int KTV_MODE = 0x00032000;

    @IntDef({HeadPillowModeType.SHARE,
            HeadPillowModeType.DRIVE,
            HeadPillowModeType.PRIVATE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface HeadPillowModeType {
        /**
         * 共享模式
         */
        int SHARE = 0x01;

        /**
         * 驾驶模式
         */
        int DRIVE = 0x02;

        /**
         * 私享模式
         */
        int PRIVATE = 0x03;
    }

    @IntDef({AUDIO_3D_SURROUND_SOUND_EFFECT_LEVEL,
            AUDIO_HEAD_PILLOW_AUTO_MODE,
            AUDIO_HEAD_PILLOW,
            AUDIO_NAVI_MIX_MODE,
            AUDIO_AUDIBLE_LOCKING_FEEDBACK,
            AUDIO_TIMBRE_CUSTOM,
            PLAY_PDC_WARNING,
            KTV_MODE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({NaviVoiceMixMode.DUCKED,
            NaviVoiceMixMode.REMAINS,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface NaviVoiceMixMode {
        /**
         * 导航播报时，多媒体音量自动降低
         */
        int DUCKED = 0x01;

        /**
         * 导航播报时，媒体音量保持不变
         */
        int REMAINS = 0x02;
    }
}
