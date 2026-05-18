//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 音频设置
 */
public interface IAudio {
    /**
     * Sound field mode settings&lt;br/&gt;
     * 音场模式设置&lt;br/&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link SoundFieldMode} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-18 20:24:54
     */
    int SOUND_FIELD_MODE = 0x00007b00;

    /**
     * Sound effects Compensation Level&lt;br&gt;
     * 音效补偿&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link CompensationLevel} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-18 20:37:04
     */
    int AUDIO_COMPENSATION_LEVEL = 0x00008200;

    /**
     * Sound warning Volume level setting (Chime module Volume level)
     * 报警音量级别设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link SoundWarningVolume} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-18 20:45:32
     */
    int SOUND_WARNING_VOLUME = 0x00008500;

    /**
     * UI setting, users can choose different sound types for soft button press, when users choose one of them, audio system shall play it one time
     * 软按键(触屏)音效类型设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link SoftButtonSoundType} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-18 20:47:55
     */
    int SOFT_BUTTON_SOUND_TYPE = 0x00008600;

    /**
     * Customize sound effects (low frequency)
     * 自定义音效（低频）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 17:14:10
     */
    int AUDIO_FREQUENCY_LOW = 0x0000fb00;

    /**
     * Customize sound effects (middle frequency)
     * 自定义音效（中频）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 17:15:11
     */
    int AUDIO_FREQUENCY_MID = 0x0000fc00;

    /**
     * Customize sound effects (high frequency)
     * 自定义音效（高频）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 17:15:51
     */
    int AUDIO_FREQUENCY_HIGH = 0x0000fd00;

    /**
     * Sound Field Mode - Custom Mode ，original ID is SETTING_FUNC_AUDIO_SOUND_OPTIMIZE_FADER
     * 声场模式-衰减，原 ID 为 SETTING_FUNC_AUDIO_SOUND_OPTIMIZE_FADER
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-24 10:39:47
     */
    int AUDIO_SOUND_OPTIMIZE_FADER = 0x0001a400;

    /**
     * Sound Field Mode - Custom Mode，original ID is SETTING_FUNC_AUDIO_SOUND_OPTIMIZE_BALANCE
     * 声场模式-平衡，原 ID 为 SETTING_FUNC_AUDIO_SOUND_OPTIMIZE_BALANCE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-24 10:41:07
     */
    int AUDIO_SOUND_OPTIMIZE_BALANCE = 0x0001a500;

    /**
     * Set the navigation volume to mute, get the navigation mute status, and the navigation mute status callback
     * 设置导航音量静音，获取导航静音状态，导航静音状态回调
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type true means mute, otherwise false 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-09-02 19:53:03
     */
    int NAVIGATION_MUTE = 0x00022b00;

    /**
     * Set the media volume to mute, get the media mute status, media mute status callback
     * 设置媒体音量静音，获取媒体静音状态，媒体静音状态回调
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type true means mute, otherwise false 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-09-02 19:54:41
     */
    int MEDIA_MUTE = 0x00022c00;

    /**
     * Returns the current playing audio source, which has the highest priority for adjusting the volume. The priority strategy is determined according to project requirements. The default is media. For example, when media and navigation are mixed, the navigation should be returned.
     * 返回当前播放音源中，针对调节音量而言优先级最高usage,优先级策略是按照项目需求决定，默认调节的是媒体。 比如当媒体和导航混音时应该返回的是导航。
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type see {@link android.media.AudioAttributes.AttributeUsage}. For extended audio usage, please refer to {@link ecarx.carext.audio.IAudioAttributes} 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-12-16 17:17:22
     */
    int CURRENT_ACTIVE_USAGE = 0x0002ae00;

    @IntDef({SoftButtonSoundType.TYPE_1,
            SoftButtonSoundType.TYPE_2,
            SoftButtonSoundType.TYPE_3,
            SoftButtonSoundType.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface SoftButtonSoundType {
        int TYPE_1 = 0x01;

        int TYPE_2 = 0x02;

        int TYPE_3 = 0x03;

        /**
         * 关闭/off
         */
        int OFF = 0xff;
    }

    @IntDef({CompensationLevel.LOW,
            CompensationLevel.MEDIUM,
            CompensationLevel.HIGH,
            CompensationLevel.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface CompensationLevel {
        /**
         * 补偿等级: 低
         */
        int LOW = 0x01;

        /**
         * 补偿等级: 中
         */
        int MEDIUM = 0x02;

        /**
         * 补偿等级: 高
         */
        int HIGH = 0x03;

        int OFF = 0xff;
    }

    @IntDef({SOUND_FIELD_MODE,
            AUDIO_COMPENSATION_LEVEL,
            SOUND_WARNING_VOLUME,
            SOFT_BUTTON_SOUND_TYPE,
            AUDIO_FREQUENCY_LOW,
            AUDIO_FREQUENCY_MID,
            AUDIO_FREQUENCY_HIGH,
            AUDIO_SOUND_OPTIMIZE_FADER,
            AUDIO_SOUND_OPTIMIZE_BALANCE,
            NAVIGATION_MUTE,
            MEDIA_MUTE,
            CURRENT_ACTIVE_USAGE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({SoundWarningVolume.LOW,
            SoundWarningVolume.MID,
            SoundWarningVolume.HIGH,
            SoundWarningVolume.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface SoundWarningVolume {
        /**
         * Sound warning Volume level: low
         */
        int LOW = 0x01;

        /**
         * Sound warning Volume level: middle
         */
        int MID = 0x02;

        /**
         * Sound warning Volume level: high
         */
        int HIGH = 0x03;

        int OFF = 0xff;
    }

    @IntDef({SoundFieldMode.SOUND_FIELD_MODE_ALL,
            SoundFieldMode.SOUND_FIELD_MODE_DRIVER,
            SoundFieldMode.SOUND_FIELD_MODE_COPILOT,
            SoundFieldMode.SOUND_FIELD_MODE_SURROUND,
            SoundFieldMode.SOUND_FIELD_MODE_REAR,
            SoundFieldMode.SOUND_FIELD_MODE_FREE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface SoundFieldMode {
        /**
         * 全车模式
         */
        int SOUND_FIELD_MODE_ALL = 0x01;

        /**
         * 主驾模式
         */
        int SOUND_FIELD_MODE_DRIVER = 0x02;

        /**
         * 副驾模式
         */
        int SOUND_FIELD_MODE_COPILOT = 0x03;

        /**
         * 环绕模式
         */
        int SOUND_FIELD_MODE_SURROUND = 0x04;

        /**
         * 后排模式
         */
        int SOUND_FIELD_MODE_REAR = 0x05;

        /**
         * 自由模式
         */
        int SOUND_FIELD_MODE_FREE = 0x06;
    }
}
