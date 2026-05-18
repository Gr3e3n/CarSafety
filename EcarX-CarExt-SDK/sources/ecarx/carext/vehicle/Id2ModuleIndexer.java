//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.String;

public final class Id2ModuleIndexer {
    @Nullable
    public static String findModuleNameById(int id) {
        switch(id) {
            case ecarx.carext.vehicle.module.ISeat.EASY_INGRESS_EGRESS:// 0x00005100;
            case ecarx.carext.vehicle.module.ISeat.BACKREST_ANGLE_ADJUST:// 0x00018300;
            case ecarx.carext.vehicle.module.ISeat.SEAT_VERTICAL_ADJUST:// 0x00018400;
            case ecarx.carext.vehicle.module.ISeat.SEAT_HORIZONTAL_ADJUST:// 0x00018500;
            case ecarx.carext.vehicle.module.ISeat.MASSAGE_SWITCH:// 0x0001a700;
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_OCCUPANCY_STATUS:// 0x0001ee00;
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_MODE_ADJUST:// 0x00028200;
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_MODE_ADJUST
                    |ecarx.carext.vehicle.module.v446k.ISeat.SeatMode.MODE1:// 0x00028200|0x01;
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_MODE_ADJUST
                    |ecarx.carext.vehicle.module.v446k.ISeat.SeatMode.MODE2:// 0x00028200|0x02;
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_MODE_ADJUST
                    |ecarx.carext.vehicle.module.v446k.ISeat.SeatMode.MODE3:// 0x00028200|0x03;
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_ADJUST_HARD_SWITCH_STATUS:// 0x00031f00;
                return ecarx.carext.vehicle.CarExtModule.ISeat;
            case ecarx.carext.vehicle.module.IHUD.HUD_ACTIVE:// 0x00006000;
            case ecarx.carext.vehicle.module.IHUD.HUD_ANGLE_ADJUST:// 0x00006300;
            case ecarx.carext.vehicle.module.IHUD.HUD_BRIGHTNESS_ADJUST:// 0x00006400;
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_NAVI:// 0x00006600;
            case ecarx.carext.vehicle.module.IHUD.HUD_SNOW_MODE:// 0x00006800;
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_MODE:// 0x00008e00;
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.IHUD.HudDisplayMode.SIMPLE:// 0x00008e00|0x01;
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.IHUD.HudDisplayMode.MAP:// 0x00008e00|0x02;
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.IHUD.HudDisplayMode.STANDARD:// 0x00008e00|0x03;
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.IHUD.HudDisplayMode.AR:// 0x00008e00|0x04;
            case ecarx.carext.vehicle.module.IHUD.HUD_ANGLE:// 0x00021600;
            case ecarx.carext.vehicle.module.IHUD.HUD_BRIGHTNESS:// 0x00021a00;
            case ecarx.carext.vehicle.module.IHUD.HUD_NOTIFY_AR_ADJUST:// 0x00021d00;
            case ecarx.carext.vehicle.module.v446k.IHUD.HUD_ANGLE_RESET:// 0x0001d900;
            case ecarx.carext.vehicle.module.v446k.IHUD.HUD_CAST_ACTIVE:// 0x00033a00;
                return ecarx.carext.vehicle.CarExtModule.IHUD;
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE:// 0x00007b00;
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_ALL:// 0x00007b00|0x01;
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_DRIVER:// 0x00007b00|0x02;
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_COPILOT:// 0x00007b00|0x03;
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_SURROUND:// 0x00007b00|0x04;
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_REAR:// 0x00007b00|0x05;
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_FREE:// 0x00007b00|0x06;
            case ecarx.carext.vehicle.module.IAudio.AUDIO_COMPENSATION_LEVEL:// 0x00008200;
            case ecarx.carext.vehicle.module.IAudio.AUDIO_COMPENSATION_LEVEL
                    |ecarx.carext.vehicle.module.IAudio.CompensationLevel.LOW:// 0x00008200|0x01;
            case ecarx.carext.vehicle.module.IAudio.AUDIO_COMPENSATION_LEVEL
                    |ecarx.carext.vehicle.module.IAudio.CompensationLevel.MEDIUM:// 0x00008200|0x02;
            case ecarx.carext.vehicle.module.IAudio.AUDIO_COMPENSATION_LEVEL
                    |ecarx.carext.vehicle.module.IAudio.CompensationLevel.HIGH:// 0x00008200|0x03;
            case ecarx.carext.vehicle.module.IAudio.AUDIO_COMPENSATION_LEVEL
                    |ecarx.carext.vehicle.module.IAudio.CompensationLevel.OFF:// 0x00008200|0xff;
            case ecarx.carext.vehicle.module.IAudio.SOUND_WARNING_VOLUME:// 0x00008500;
            case ecarx.carext.vehicle.module.IAudio.SOUND_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IAudio.SoundWarningVolume.LOW:// 0x00008500|0x01;
            case ecarx.carext.vehicle.module.IAudio.SOUND_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IAudio.SoundWarningVolume.MID:// 0x00008500|0x02;
            case ecarx.carext.vehicle.module.IAudio.SOUND_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IAudio.SoundWarningVolume.HIGH:// 0x00008500|0x03;
            case ecarx.carext.vehicle.module.IAudio.SOUND_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IAudio.SoundWarningVolume.OFF:// 0x00008500|0xff;
            case ecarx.carext.vehicle.module.IAudio.SOFT_BUTTON_SOUND_TYPE:// 0x00008600;
            case ecarx.carext.vehicle.module.IAudio.SOFT_BUTTON_SOUND_TYPE
                    |ecarx.carext.vehicle.module.IAudio.SoftButtonSoundType.TYPE_1:// 0x00008600|0x01;
            case ecarx.carext.vehicle.module.IAudio.SOFT_BUTTON_SOUND_TYPE
                    |ecarx.carext.vehicle.module.IAudio.SoftButtonSoundType.TYPE_2:// 0x00008600|0x02;
            case ecarx.carext.vehicle.module.IAudio.SOFT_BUTTON_SOUND_TYPE
                    |ecarx.carext.vehicle.module.IAudio.SoftButtonSoundType.TYPE_3:// 0x00008600|0x03;
            case ecarx.carext.vehicle.module.IAudio.SOFT_BUTTON_SOUND_TYPE
                    |ecarx.carext.vehicle.module.IAudio.SoftButtonSoundType.OFF:// 0x00008600|0xff;
            case ecarx.carext.vehicle.module.IAudio.AUDIO_FREQUENCY_LOW:// 0x0000fb00;
            case ecarx.carext.vehicle.module.IAudio.AUDIO_FREQUENCY_MID:// 0x0000fc00;
            case ecarx.carext.vehicle.module.IAudio.AUDIO_FREQUENCY_HIGH:// 0x0000fd00;
            case ecarx.carext.vehicle.module.IAudio.AUDIO_SOUND_OPTIMIZE_FADER:// 0x0001a400;
            case ecarx.carext.vehicle.module.IAudio.AUDIO_SOUND_OPTIMIZE_BALANCE:// 0x0001a500;
            case ecarx.carext.vehicle.module.IAudio.NAVIGATION_MUTE:// 0x00022b00;
            case ecarx.carext.vehicle.module.IAudio.MEDIA_MUTE:// 0x00022c00;
            case ecarx.carext.vehicle.module.IAudio.CURRENT_ACTIVE_USAGE:// 0x0002ae00;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_3D_SURROUND_SOUND_EFFECT_LEVEL:// 0x0001e900;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE:// 0x0001eb00;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE
                    |ecarx.carext.vehicle.module.v446k.IAudio.HeadPillowModeType.SHARE:// 0x0001eb00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE
                    |ecarx.carext.vehicle.module.v446k.IAudio.HeadPillowModeType.DRIVE:// 0x0001eb00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE
                    |ecarx.carext.vehicle.module.v446k.IAudio.HeadPillowModeType.PRIVATE:// 0x0001eb00|0x03;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_HEAD_PILLOW:// 0x0001ec00;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_NAVI_MIX_MODE:// 0x0001ed00;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_NAVI_MIX_MODE
                    |ecarx.carext.vehicle.module.v446k.IAudio.NaviVoiceMixMode.DUCKED:// 0x0001ed00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_NAVI_MIX_MODE
                    |ecarx.carext.vehicle.module.v446k.IAudio.NaviVoiceMixMode.REMAINS:// 0x0001ed00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_AUDIBLE_LOCKING_FEEDBACK:// 0x0001ef00;
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_TIMBRE_CUSTOM:// 0x00022000;
            case ecarx.carext.vehicle.module.v446k.IAudio.PLAY_PDC_WARNING:// 0x00026200;
            case ecarx.carext.vehicle.module.v446k.IAudio.KTV_MODE:// 0x00032000;
                return ecarx.carext.vehicle.CarExtModule.IAudio;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT:// 0x00026500;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.NORMAL:// 0x00026500|0x01;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.PRESS:// 0x00026500|0x02;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.RELEASE:// 0x00026500|0x03;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.CLICK:// 0x00026500|0x04;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.LONG_CLICK:// 0x00026500|0x05;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT:// 0x00026600;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.NORMAL:// 0x00026600|0x01;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.PRESS:// 0x00026600|0x02;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.RELEASE:// 0x00026600|0x03;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.CLICK:// 0x00026600|0x04;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.LONG_CLICK:// 0x00026600|0x05;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM:// 0x00026700;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.NORMAL:// 0x00026700|0x01;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.PRESS:// 0x00026700|0x02;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.RELEASE:// 0x00026700|0x03;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.CLICK:// 0x00026700|0x04;
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.LONG_CLICK:// 0x00026700|0x05;
                return ecarx.carext.vehicle.CarExtModule.IHardKey;
            case ecarx.carext.vehicle.module.IProfile.PROFILE_MEM_SAVE:// 0x00012b00;
            case ecarx.carext.vehicle.module.IProfile.PROFILE_SWITCH_PREFERENCE:// 0x00012e00;
            case ecarx.carext.vehicle.module.IProfile.PROFILE_CHANGED_POP:// 0x00015d00;
                return ecarx.carext.vehicle.CarExtModule.IProfile;
            case ecarx.carext.vehicle.module.Tcam.TCAM_RESET:// 0x0000f500;
            case ecarx.carext.vehicle.module.Tcam.HARDWARE_VERSION:// 0x00010000;
            case ecarx.carext.vehicle.module.Tcam.SOFTWARE_VERSION:// 0x00010100;
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_SAVE_STATUS:// 0x0001b100;
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE:// 0x0001d000;
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.OFF:// 0x0001d000|0x01;
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.SINGLE:// 0x0001d000|0x02;
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.CYCLE:// 0x0001d000|0x03;
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_TIME:// 0x0001d100;
            case ecarx.carext.vehicle.module.v446k.Tcam.TCAM_RESET_STATE:// 0x0001f600;
            case ecarx.carext.vehicle.module.v446k.Tcam.TCAM_RESET_STATE
                    |ecarx.carext.vehicle.module.v446k.Tcam.TcamResetState.FAILED:// 0x0001f600|0x01;
            case ecarx.carext.vehicle.module.v446k.Tcam.TCAM_RESET_STATE
                    |ecarx.carext.vehicle.module.v446k.Tcam.TcamResetState.SUCCESS:// 0x0001f600|0x02;
            case ecarx.carext.vehicle.module.v446k.Tcam.TCAM_RESET_STATE
                    |ecarx.carext.vehicle.module.v446k.Tcam.TcamResetState.UNKNOW:// 0x0001f600|0xff;
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE_LAST_TIME:// 0x00023700;
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE_LAST_TIME
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.OFF:// 0x00023700|0x01;
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE_LAST_TIME
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.SINGLE:// 0x00023700|0x02;
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE_LAST_TIME
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.CYCLE:// 0x00023700|0x03;
                return ecarx.carext.vehicle.CarExtModule.Tcam;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.CREATE_DIGITAL_KEY:// 0x00025700;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.CREATE_DIGITAL_KEY
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyType.DIGITAL_KEY_TYPE_OWNER:// 0x00025700|0x01;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.CREATE_DIGITAL_KEY
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyType.DIGITAL_KEY_TYPE_SHARED:// 0x00025700|0x02;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DELETE_DIGITAL_KEY:// 0x00025800;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION:// 0x00025900;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.SUCCESS:// 0x00025900|0x01;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.REJECT_USGMOD:// 0x00025900|0x02;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.REJECT_CARMOD:// 0x00025900|0x03;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.REJECT_KEY_NOT_EXIST:// 0x00025900|0x04;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.REJECT_NO_KEY:// 0x00025900|0x05;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.REJECT_BNCM:// 0x00025900|0x06;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.IDLE:// 0x00025900|0xff;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DELETE_ALL_DIGITAL_KEYS:// 0x00025a00;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.GET_DIGITAL_KEYS:// 0x00025b00;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS:// 0x00025c00;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.IDLE:// 0x00025c00|0x01;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.CREATING:// 0x00025c00|0x02;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.RESETTING:// 0x00025c00|0x03;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.DELETING:// 0x00025c00|0x04;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.SEARCHING_CARD:// 0x00025c00|0x05;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.SEARCHING_PHONE:// 0x00025c00|0x06;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.PREPARING_DATA:// 0x00025c00|0x07;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.CREATE_SUCCESS:// 0x00025c00|0x08;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.SMS_SENT:// 0x00025c00|0x09;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.PAIRING_CODE_VERIFIED:// 0x00025c00|0x0a;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS:// 0x00025d00;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.SUCCESS:// 0x00025d00|0x01;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.TIMEOUT:// 0x00025d00|0x02;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.BCNM_LACK_VIN:// 0x00025d00|0x08;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.VERIFIER_UPDATED:// 0x00025d00|0x09;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.UTC_TI_LOST:// 0x00025d00|0x0a;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.CERTIFICATE_INVALID:// 0x00025d00|0x0b;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.ERROR_MSG:// 0x00025d00|0x0c;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.SECURE_CREATFAL:// 0x00025d00|0x0d;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.WRITE_DATAFAL:// 0x00025d00|0x0e;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.GET_DATAFAL:// 0x00025d00|0x0f;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.PROTOCOL_MISMATCH:// 0x00025d00|0x10;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.AUTH_FAL:// 0x00025d00|0x11;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.PHONE_NOT_FOUND:// 0x00025d00|0x13;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.OWNER_KEY_NOT_FOUND:// 0x00025d00|0x14;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TRACKING_RESULT:// 0x00025e00;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR:// 0x00025f00;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.IDLE:// 0x00025f00|0x01;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.SUCCESS:// 0x00025f00|0x02;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.REJECT_USGMOD:// 0x00025f00|0x03;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.REJECT_CARMOD:// 0x00025f00|0x04;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.REJECT_NO_MOBILE:// 0x00025f00|0x05;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.REJECT_NO_KEY:// 0x00025f00|0x06;
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.REJECT_BNCM:// 0x00025f00|0x07;
                return ecarx.carext.vehicle.CarExtModule.IDigitalKey;
            case ecarx.carext.vehicle.module.IBcm.POWER_ONOFF:// 0x00002f00;
            case ecarx.carext.vehicle.module.IBcm.LIGHT_FRONT_FOG_LAMPS:// 0x00003000;
            case ecarx.carext.vehicle.module.IBcm.REAR_MIRROR_ADJUST:// 0x00003b00;
            case ecarx.carext.vehicle.module.IBcm.MIRROR_AUTO_FOLDING:// 0x00005200;
            case ecarx.carext.vehicle.module.IBcm.LOCK_AUTO_CLOSE_WINDOW:// 0x00005a00;
            case ecarx.carext.vehicle.module.IBcm.AUTO_CLOSE_WINDOW_RAINY:// 0x00005b00;
            case ecarx.carext.vehicle.module.IBcm.WINDSCREEN_SERVICE_POSITION:// 0x00005e00;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY:// 0x00006d00;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_AUTO_PARKING:// 0x00006d00|0x01;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_SOUND_SWITCH:// 0x00006d00|0x02;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_DVR:// 0x00006d00|0x03;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_DRIVE_MODE_SWITCH:// 0x00006d00|0x04;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_THEME_SWITCH:// 0x00006d00|0x05;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_GAMEMOD:// 0x00006d00|0x06;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_VOICE:// 0x00006d00|0x07;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_REAR_MIRROR:// 0x00006d00|0x08;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_360:// 0x00006d00|0x09;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_HUD:// 0x00006d00|0x0a;
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_OFF:// 0x00006d00|0xff;
            case ecarx.carext.vehicle.module.IBcm.CHILD_SAFETY_LOCK:// 0x00008a00;
            case ecarx.carext.vehicle.module.IBcm.LIGHT_ATMOSPHERE_LAMPS:// 0x00009a00;
            case ecarx.carext.vehicle.module.IBcm.PBC_EPB_SWITCH:// 0x00009f00;
            case ecarx.carext.vehicle.module.IBcm.PAB_SWITCH:// 0x00023200;
            case ecarx.carext.vehicle.module.IBcm.TRAILER_PRESENT:// 0x00028300;
            case ecarx.carext.vehicle.module.IBcm.TRAILER_PRESENT
                    |ecarx.carext.vehicle.module.IBcm.TrailerState.STATE_NOT_PRESENT:// 0x00028300|0x01;
            case ecarx.carext.vehicle.module.IBcm.TRAILER_PRESENT
                    |ecarx.carext.vehicle.module.IBcm.TrailerState.STATE_PRESENT:// 0x00028300|0x02;
            case ecarx.carext.vehicle.module.IBcm.TRAILER_PRESENT
                    |ecarx.carext.vehicle.module.IBcm.TrailerState.STATE_ERROR:// 0x00028300|0x03;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_OFF_NOTICE:// 0x00017a00;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE:// 0x00017b00;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.POWER_ON:// 0x00017b00|0x01;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.POWER_OFF:// 0x00017b00|0x02;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.CHARGING:// 0x00017b00|0x03;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.ENGINE_HOOD:// 0x00017b00|0x04;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.LOW_OIL:// 0x00017b00|0x05;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.UN_GEAR:// 0x00017b00|0x06;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.LONG_TIME:// 0x00017b00|0x07;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.NOTFIND_VALID_KEY:// 0x00017b00|0x08;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.LOW_PRESSUR:// 0x00017b00|0x09;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.HIGH_PRESSURE:// 0x00017b00|0x0a;
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.MAX_TIMES:// 0x00017b00|0x0b;
            case ecarx.carext.vehicle.module.v446k.IBcm.DIM_DISPLAY_MODE:// 0x0001db00;
            case ecarx.carext.vehicle.module.v446k.IBcm.DIM_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.DisplayMode.SIMPLE:// 0x0001db00|0x01;
            case ecarx.carext.vehicle.module.v446k.IBcm.DIM_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.DisplayMode.MULTILANE:// 0x0001db00|0x02;
            case ecarx.carext.vehicle.module.v446k.IBcm.DIM_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.DisplayMode.SMART:// 0x0001db00|0x03;
            case ecarx.carext.vehicle.module.v446k.IBcm.DIM_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.DisplayMode.MAP:// 0x0001db00|0x04;
            case ecarx.carext.vehicle.module.v446k.IBcm.AUTO_REAR_WIPING:// 0x0001e000;
            case ecarx.carext.vehicle.module.v446k.IBcm.FIND_CAR_MODE:// 0x0001e200;
            case ecarx.carext.vehicle.module.v446k.IBcm.FIND_CAR_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.FindMode.HONKING:// 0x0001e200|0x01;
            case ecarx.carext.vehicle.module.v446k.IBcm.FIND_CAR_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.FindMode.FLASHING_LIGHTS:// 0x0001e200|0x02;
            case ecarx.carext.vehicle.module.v446k.IBcm.FIND_CAR_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.FindMode.HONKING_AND_FLASHING_LIGHTS:// 0x0001e200|0x03;
            case ecarx.carext.vehicle.module.v446k.IBcm.PARKING_KEEP_POWER_TIME:// 0x0001e500;
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET:// 0x0001f900;
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET_STATUS:// 0x0001fa00;
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET_STATUS
                    |ecarx.carext.vehicle.module.v446k.IBcm.FactoryResetStatus.PROGRESS:// 0x0001fa00|0x01;
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET_STATUS
                    |ecarx.carext.vehicle.module.v446k.IBcm.FactoryResetStatus.SUCCEED:// 0x0001fa00|0x02;
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET_STATUS
                    |ecarx.carext.vehicle.module.v446k.IBcm.FactoryResetStatus.FAILED:// 0x0001fa00|0x03;
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET_STATUS
                    |ecarx.carext.vehicle.module.v446k.IBcm.FactoryResetStatus.UNKNOW:// 0x0001fa00|0xff;
            case ecarx.carext.vehicle.module.v446k.IBcm.FUEL_DOOR_OPEN_CONDITION:// 0x00026100;
            case ecarx.carext.vehicle.module.v446k.IBcm.FUEL_DOOR_OPEN_CONDITION
                    |ecarx.carext.vehicle.module.v446k.IBcm.FuelDoorOpenCondition.READY:// 0x00026100|0x01;
            case ecarx.carext.vehicle.module.v446k.IBcm.FUEL_DOOR_OPEN_CONDITION
                    |ecarx.carext.vehicle.module.v446k.IBcm.FuelDoorOpenCondition.NOT_READY:// 0x00026100|0x02;
                return ecarx.carext.vehicle.CarExtModule.IBcm;
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE:// 0x00010d00;
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.UNKNOW:// 0x00010d00|0x01;
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.MUTE:// 0x00010d00|0x02;
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.OK:// 0x00010d00|0x03;
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.CLENCH_FIST:// 0x00010d00|0x04;
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.V:// 0x00010d00|0x05;
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.THUMB_UP:// 0x00010d00|0x06;
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.FINGER_LEFT:// 0x00010d00|0x07;
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.FINGER_RIGHT:// 0x00010d00|0x08;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FATIGUE_STATUS:// 0x00016f00;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FATIGUE_STATUS
                    |ecarx.carext.vehicle.module.IBiometric.DriverFatigueStatus.UNKNOW:// 0x00016f00|0x01;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FATIGUE_STATUS
                    |ecarx.carext.vehicle.module.IBiometric.DriverFatigueStatus.NORMAL:// 0x00016f00|0x02;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FATIGUE_STATUS
                    |ecarx.carext.vehicle.module.IBiometric.DriverFatigueStatus.DISTRACTED:// 0x00016f00|0x03;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FATIGUE_STATUS
                    |ecarx.carext.vehicle.module.IBiometric.DriverFatigueStatus.TIRED:// 0x00016f00|0x04;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION:// 0x00017000;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.UNKNOW:// 0x00017000|0x01;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.HAPPY:// 0x00017000|0x02;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.ANGRY:// 0x00017000|0x03;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.SURPRISE:// 0x00017000|0x04;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.NEUTRAL:// 0x00017000|0x05;
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.SAD:// 0x00017000|0x06;
                return ecarx.carext.vehicle.CarExtModule.IBiometric;
            case ecarx.carext.vehicle.module.GNSS.GNSS_UPLOAD_LOCATION_SWITCH:// 0x00010200;
            case ecarx.carext.vehicle.module.v446k.GNSS.GNSS_LOCATOR_AUTH_CYCLE:// 0x0001ff00;
            case ecarx.carext.vehicle.module.v446k.GNSS.GNSS_LOCATOR_UPDATE_AUTH_TIME:// 0x00020000;
                return ecarx.carext.vehicle.CarExtModule.GNSS;
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT:// 0x00009d00;
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_ECO:// 0x00009d00|0x01;
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_NORMAL:// 0x00009d00|0x02;
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_DYNAMIC:// 0x00009d00|0x03;
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_CUSTOM:// 0x00009d00|0x04;
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_PURE:// 0x00009d00|0x06;
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_HYBRID:// 0x00009d00|0x07;
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_OFFROAD:// 0x00009d00|0x08;
                return ecarx.carext.vehicle.CarExtModule.IDriveMode;
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING:// 0x00008700;
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.DAY:// 0x00008700|0x01;
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.NIGHT:// 0x00008700|0x02;
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.AUTO:// 0x00008700|0x03;
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.CUSTOM:// 0x00008700|0x04;
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.SUNRISE_AND_SUNSET:// 0x00008700|0x05;
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.OFF:// 0x00008700|0xff;
            case ecarx.carext.vehicle.module.IDayMode.FUNC_BRIGHTNESS:// 0x0000ae00;
            case ecarx.carext.vehicle.module.IDayMode.FUNC_BRIGHTNESS_MAX:// 0x0000af00;
            case ecarx.carext.vehicle.module.IDayMode.FUNC_BRIGHTNESS_MIN:// 0x0000b000;
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE:// 0x00010a00;
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.DAY:// 0x00010a00|0x01;
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.NIGHT:// 0x00010a00|0x02;
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.AUTO:// 0x00010a00|0x03;
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.CUSTOM:// 0x00010a00|0x04;
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.SUNRISE_AND_SUNSET:// 0x00010a00|0x05;
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.OFF:// 0x00010a00|0xff;
            case ecarx.carext.vehicle.module.IDayMode.SUN_TIME:// 0x00018000;
            case ecarx.carext.vehicle.module.v446k.IDayMode.BRIGHTNESS_AUTO_ADJUST:// 0x0001e600;
                return ecarx.carext.vehicle.CarExtModule.IDayMode;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_BRIGHTNESS_ACTION:// 0x00021700;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_BRIGHTNESS_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimBrightnessAction.RESTORE:// 0x00021700|0x01;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_BRIGHTNESS_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimBrightnessAction.SCREEN_OFF:// 0x00021700|0x02;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_BRIGHTNESS_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimBrightnessAction.START_RESET:// 0x00021700|0x03;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_BRIGHTNESS_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimBrightnessAction.NONE:// 0x00021700|0xff;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_STATE:// 0x00021800;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_STATE
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionState.NAVI:// 0x00021800|0x01;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_STATE
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionState.SCENE:// 0x00021800|0x02;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_STATE
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionState.VIDEO:// 0x00021800|0x04;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_STATE
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionState.NONE:// 0x00021800|0xff;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION:// 0x00021900;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.NAVI_START:// 0x00021900|0x01;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.NAVI_END:// 0x00021900|0x02;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.SCENT_START:// 0x00021900|0x03;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.SCENE_END:// 0x00021900|0x04;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.VIDEO_START:// 0x00021900|0x05;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.VIDEO_END:// 0x00021900|0x06;
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.NONE:// 0x00021900|0xff;
            case ecarx.carext.vehicle.module.v446k.DIM.FAULT_ALARM_ZONE_A:// 0x00022600;
                return ecarx.carext.vehicle.CarExtModule.DIM;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT:// 0x00009500;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_10S:// 0x00009500|0x01;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_20S:// 0x00009500|0x02;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_30S:// 0x00009500|0x03;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_40S:// 0x00009500|0x04;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_50S:// 0x00009500|0x05;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_60S:// 0x00009500|0x06;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_70S:// 0x00009500|0x07;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_80S:// 0x00009500|0x08;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_90S:// 0x00009500|0x09;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_100S:// 0x00009500|0x10;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_110S:// 0x00009500|0x11;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_120S:// 0x00009500|0x12;
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_OFF:// 0x00009500|0xff;
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME:// 0x00009600;
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME
                    |ecarx.carext.vehicle.module.ILamp.LightingTimeLevel.LIGHTING_TIME_LEVEL1:// 0x00009600|0x01;
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME
                    |ecarx.carext.vehicle.module.ILamp.LightingTimeLevel.LIGHTING_TIME_LEVEL2:// 0x00009600|0x02;
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME
                    |ecarx.carext.vehicle.module.ILamp.LightingTimeLevel.LIGHTING_TIME_LEVEL3:// 0x00009600|0x03;
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME
                    |ecarx.carext.vehicle.module.ILamp.LightingTimeLevel.LIGHTING_TIME_LEVEL4:// 0x00009600|0x04;
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME
                    |ecarx.carext.vehicle.module.ILamp.LightingTimeLevel.LIGHTING_TIME_OFF:// 0x00009600|0xff;
            case ecarx.carext.vehicle.module.ILamp.LAMP_AUTOMATIC_COURTESY_LIGHT:// 0x00009700;
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL:// 0x00009b00;
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL
                    |ecarx.carext.vehicle.module.ILamp.ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_POS_:// 0x00009b00|0x01;
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL
                    |ecarx.carext.vehicle.module.ILamp.ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_LOWB:// 0x00009b00|0x02;
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL
                    |ecarx.carext.vehicle.module.ILamp.ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_AUTO:// 0x00009b00|0x03;
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL
                    |ecarx.carext.vehicle.module.ILamp.ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_AHBC:// 0x00009b00|0x04;
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL
                    |ecarx.carext.vehicle.module.ILamp.ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_OFF:// 0x00009b00|0xff;
            case ecarx.carext.vehicle.module.ILamp.LAMP_FRONT_POSITION:// 0x00018a00;
            case ecarx.carext.vehicle.module.ILamp.LAMP_REAR_POSITION:// 0x00018b00;
            case ecarx.carext.vehicle.module.ILamp.LAMP_DAYTIME_LIGHT:// 0x00018c00;
            case ecarx.carext.vehicle.module.v446k.ILamp.ADAPTIVE_FRONT_LIGHT:// 0x0001e700;
            case ecarx.carext.vehicle.module.v446k.ILamp.BENDING_LIGHT:// 0x0001ea00;
            case ecarx.carext.vehicle.module.v446k.ILamp.READING_LIGHTS_SWITCH_VOICE:// 0x00033500;
                return ecarx.carext.vehicle.CarExtModule.ILamp;
            case ecarx.carext.vehicle.module.v446k.IDisplay.SCREEN_ON:// 0x0002cb00;
                return ecarx.carext.vehicle.CarExtModule.IDisplay;
            case ecarx.carext.vehicle.module.IAPA.APA_INTELLIGENT_PARKING_SWITCH:// 0x00013e00;
            case ecarx.carext.vehicle.module.v446k.IAPA.IS_FUNC_APA_ACTIVE:// 0x00028600;
            case ecarx.carext.vehicle.module.v446k.IAPA.IS_FUNC_RPA_ACTIVE:// 0x00028700;
            case ecarx.carext.vehicle.module.v446k.IAPA.IS_FUNC_RPP_ACTIVE:// 0x00028800;
            case ecarx.carext.vehicle.module.v446k.IAPA.IS_FUNC_RSPA_ACTIVE:// 0x00028900;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_IS_DISPLAY:// 0x00028a00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_TOAST_NOTIFICATION:// 0x00028b00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_INTERRUPT_CONTROL:// 0x00028c00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_INTERRUPT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.ParkingInterruptStatus.CONTROL_ITEM1:// 0x00028c00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_INTERRUPT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.ParkingInterruptStatus.CONTROL_ITEM2:// 0x00028c00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_INTERRUPT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.ParkingInterruptStatus.CONTROL_ITEM3:// 0x00028c00|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_TO_AVM:// 0x00028d00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_TO_AVM
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00028d00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_TO_AVM
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00028d00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_TO_AVM
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00028d00|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_TO_AVM
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00028d00|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_PROGRESS_AND_STATUS:// 0x00028e00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_STATUS_MARKET:// 0x00028f00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_VOICE_BROADCAST:// 0x00029000;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_TRAVELED_DISTANCE:// 0x00029100;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_IN_CONTROL:// 0x00029200;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_IN_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029200|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_IN_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029200|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_IN_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029200|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_IN_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029200|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_CONTROL:// 0x00029300;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029300|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029300|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029300|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029300|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_DIRECTION:// 0x00029400;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_DIRECTION
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029400|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_DIRECTION
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029400|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_DIRECTION
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029400|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_DIRECTION
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029400|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SETTINGS_STATUS:// 0x00029500;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SETTINGS_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029500|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SETTINGS_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029500|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SETTINGS_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029500|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SETTINGS_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029500|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_CONTROL:// 0x00029600;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029600|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029600|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029600|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029600|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL:// 0x00029700;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAParkingDistance.ITME1:// 0x00029700|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAParkingDistance.ITEM2:// 0x00029700|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAParkingDistance.ITEM3:// 0x00029700|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_HELP_VOICE:// 0x00029800;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_HELP_VOICE
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAVoiceMode.ITEM1:// 0x00029800|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_HELP_VOICE
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAVoiceMode.ITEM2:// 0x00029800|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_AVM_SETTINGS:// 0x00029900;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_AVM_SETTINGS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029900|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_AVM_SETTINGS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029900|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_AVM_SETTINGS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029900|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_AVM_SETTINGS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029900|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_LOW_SPEED_EXPLORE:// 0x00029a00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SELECT_VIR_PARKING_SPACE:// 0x00029b00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_HMI_SHOW_PARKING_SPOT:// 0x00029c00;
            case ecarx.carext.vehicle.module.v446k.IAPA.PARKING_OUT_CONFIRMATION:// 0x0002b000;
            case ecarx.carext.vehicle.module.v446k.IAPA.SENSORLESS_PARKING:// 0x0002bf00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_EXIT:// 0x0002c000;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_EXIT_BUTTON_ENABLE:// 0x0002c100;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PDC_MUTE_SWITCH:// 0x0002c300;
            case ecarx.carext.vehicle.module.v446k.IAPA.IS_FUNC_HPA_ACTIVE:// 0x0002d500;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ACTION_CONTROL:// 0x0002d600;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_POP:// 0x0002d700;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_RECOVER_BTN:// 0x0002d800;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_RECOVER_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002d800|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_RECOVER_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002d800|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_RECOVER_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002d800|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_RECOVER_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002d800|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_EXIT_BACK_BTN:// 0x0002d900;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_EXIT_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002d900|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_EXIT_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002d900|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_EXIT_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002d900|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_EXIT_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002d900|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_NOTIFICATIONS:// 0x0002da00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_NOTIFICATIONS_CONTROL:// 0x0002db00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_IS_DISPLAY:// 0x0002dc00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_VIDEO_TYPE:// 0x0002dd00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_TTS_CALLBACK:// 0x0002de00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_TTS_CONTROL:// 0x0002df00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_DEXTINATION_BTN:// 0x0002e000;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002e000|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002e000|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002e000|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002e000|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_BTN:// 0x0002e100;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002e100|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002e100|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002e100|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002e100|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONTROL:// 0x0002e200;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG:// 0x0002e300;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM0:// 0x0002e300|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM1:// 0x0002e300|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM2:// 0x0002e300|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM3:// 0x0002e300|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM4:// 0x0002e300|0x05;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM5:// 0x0002e300|0x06;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_AUTO_ROUTES_SWITCH:// 0x0002e400;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PUSH_SWITCH:// 0x0002e500;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CAR_CONFIG:// 0x0002e600;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_HMI_STATUS:// 0x0002e700;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_BACK_BTN:// 0x0002e800;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002e800|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002e800|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002e800|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002e800|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_START_BACK_TRACE_BTN:// 0x0002e900;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_START_BACK_TRACE_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002e900|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_START_BACK_TRACE_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002e900|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_START_BACK_TRACE_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002e900|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_START_BACK_TRACE_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002e900|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_TEXT_PARK_BEFORE:// 0x0002ea00;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_TEXT_PARK_AFTER:// 0x0002eb00;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_TTS_CALLBACK:// 0x0002ec00;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_TTS_CONTROL:// 0x0002ed00;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CONFIRM_DEXTINATION_BTN:// 0x0002ee00;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002ee00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002ee00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002ee00|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002ee00|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_TTS_CALLBACK_MODE:// 0x0002ef00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_TTS_CALLBACK_MODE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAAoundMode.ITEM0:// 0x0002ef00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_TTS_CALLBACK_MODE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAAoundMode.ITEM1:// 0x0002ef00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_TTS_CALLBACK_MODE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAAoundMode.ITEM2:// 0x0002ef00|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_TTS_CALLBACK_MODE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAAoundMode.ITEM3:// 0x0002ef00|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_VIEW_TYPE:// 0x0002f000;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAViewType.ITEM0:// 0x0002f000|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAViewType.ITEM1:// 0x0002f000|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAViewType.ITEM2:// 0x0002f000|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_DST:// 0x0002f100;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_CRSSING:// 0x0002f200;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_BUM:// 0x0002f300;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_TO_END:// 0x0002f400;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_PED:// 0x0002f500;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_VEH:// 0x0002f600;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_RAMP:// 0x0002f700;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REMAINING_DST:// 0x0002f800;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_TIME_TYPE:// 0x0002f900;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_TUTORIAL:// 0x0002fa00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN:// 0x0002fb00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002fb00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002fb00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002fb00|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002fb00|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN:// 0x0002fc00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002fc00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002fc00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002fc00|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002fc00|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CREATE_PARK_IN:// 0x0002fd00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CREATE_PARK_OUT:// 0x0002fe00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP0:// 0x0002ff00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP1:// 0x00030000;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP2:// 0x00030100;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP3:// 0x00030200;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP4:// 0x00030300;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_LOCAL_MAP_SELECT:// 0x00030400;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_LOCAL_MAP_CLOSE:// 0x00030500;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_PARK_IN_COVER:// 0x00030600;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_PARK_OUT_COVER:// 0x00030700;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_PARK_IN_COVER_SET:// 0x00030800;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_PARK_OUT_COVER_SET:// 0x00030900;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_PARKING_PROGRESS:// 0x00030a00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CREATE_ROUTR_PROGRESS:// 0x00030b00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_BUILD_RESULT:// 0x00030c00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_BUILD_RESULT
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAMapBuildStatus.ITEM0:// 0x00030c00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_BUILD_RESULT
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAMapBuildStatus.ITEM1:// 0x00030c00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_BUILD_RESULT
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAMapBuildStatus.ITEM2:// 0x00030c00|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_BUILD_RESULT
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAMapBuildStatus.ITEM3:// 0x00030c00|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ROUTER_MANAGE_DISPLAY:// 0x00030d00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ROUTER_MANAGE_DISPLAY
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPARouteDisplay.ITEM0:// 0x00030d00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ROUTER_MANAGE_DISPLAY
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPARouteDisplay.ITEM1:// 0x00030d00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ROUTER_MANAGE_DISPLAY
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPARouteDisplay.ITEM2:// 0x00030d00|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_RENAME_NOTICE_CONTROL:// 0x00030e00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_PARK_IN_ROUTES_DELETE:// 0x00030f00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_PARK_OUT_ROUTES_DELETE:// 0x00031000;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_PARK_ROUTE_SYNC:// 0x00031100;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_VIEW_SWITCH:// 0x00031200;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_POP_VIEW_IS_REDUCE:// 0x00031300;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_OBSTACLE_TRIGGER_SWITCH:// 0x00031400;
            case ecarx.carext.vehicle.module.v446k.IAPA.PDC_FRONT_DISTANCE:// 0x00031a00;
            case ecarx.carext.vehicle.module.v446k.IAPA.PDC_REAR_DISTANCE:// 0x00031b00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_DOCK_DISABLE_REQUEST:// 0x00032500;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_PARKING_TEXT:// 0x00032c00;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS:// 0x00033200;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_RSPA:// 0x00033200|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_APA:// 0x00033200|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_RPA:// 0x00033200|0x03;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_RPAP:// 0x00033200|0x04;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_HPA:// 0x00033200|0x05;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_AVP:// 0x00033200|0x06;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_AVM:// 0x00033200|0x07;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_TBA:// 0x00033200|0x08;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_LSDA:// 0x00033200|0x09;
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_OFF:// 0x00033200|0xff;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_HIGH_FREQUENCY_STATUS:// 0x00033c00;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP5:// 0x00034100;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP6:// 0x00034200;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP7:// 0x00034300;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP8:// 0x00034400;
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP9:// 0x00034500;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_VIEW_TYPE:// 0x00034a00;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAViewType.TYPE_VIEW_DEFAULT:// 0x00034a00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAViewType.TYPE_VIEW_FRONT:// 0x00034a00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAViewType.TYPE_VIEW_REAR:// 0x00034a00|0x03;
                return ecarx.carext.vehicle.CarExtModule.IAPA;
            case ecarx.carext.vehicle.module.IAvm.AVM_DISPLAY_STATUS:// 0x00023e00;
            case ecarx.carext.vehicle.module.IAvm.AVM_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.IAvm.DisplayMode.FULL_SCREEN:// 0x00023e00|0x01;
            case ecarx.carext.vehicle.module.IAvm.AVM_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.IAvm.DisplayMode.MINI_WINDOW:// 0x00023e00|0x02;
            case ecarx.carext.vehicle.module.IAvm.AVM_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.IAvm.DisplayMode.DISPLAY_OFF:// 0x00023e00|0xff;
                return ecarx.carext.vehicle.CarExtModule.IAvm;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_SOC:// 0x00003500;
            case ecarx.carext.vehicle.module.ICharging.BATTERY_CHARGING_CURRENT_POWER:// 0x00003900;
            case ecarx.carext.vehicle.module.ICharging.DC_CHARGING_POWER:// 0x00003c00;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_ESTIMATED_TIME:// 0x00004000;
            case ecarx.carext.vehicle.module.ICharging.CHARGE_FUNC_CHARGING:// 0x00004900;
            case ecarx.carext.vehicle.module.ICharging.CHARGE_FUNC_CHARGING
                    |ecarx.carext.vehicle.module.ICharging.ChargeSwitchState.STOP:// 0x00004900|0x01;
            case ecarx.carext.vehicle.module.ICharging.CHARGE_FUNC_CHARGING
                    |ecarx.carext.vehicle.module.ICharging.ChargeSwitchState.RESTART:// 0x00004900|0x02;
            case ecarx.carext.vehicle.module.ICharging.CHARGE_FUNC_CHARGING
                    |ecarx.carext.vehicle.module.ICharging.ChargeSwitchState.START_NOW:// 0x00004900|0x03;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_GUN_LOCK:// 0x00004a00;
            case ecarx.carext.vehicle.module.ICharging.DISCHARGING_SOC:// 0x00004b00;
            case ecarx.carext.vehicle.module.ICharging.DISCHARGING_SWITCH_V2L:// 0x00004c00;
            case ecarx.carext.vehicle.module.ICharging.DISCHARGING_WORK_VOLTAGE:// 0x00004d00;
            case ecarx.carext.vehicle.module.ICharging.DISCHARGING_WORK_CURRENT:// 0x00004e00;
            case ecarx.carext.vehicle.module.ICharging.BATTERY_DISCHARGING_CURRENT_POWER:// 0x00004f00;
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION:// 0x0000a700;
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_LOW:// 0x0000a700|0x01;
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_MID:// 0x0000a700|0x02;
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_HIGH:// 0x0000a700|0x03;
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_AUTO:// 0x0000a700|0x04;
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_LOWER:// 0x0000a700|0x05;
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_HIGHER:// 0x0000a700|0x06;
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_OFF:// 0x0000a700|0xff;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE:// 0x0000dd00;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_UNKNOWN:// 0x0000dd00|0x01;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_PREPARED:// 0x0000dd00|0x02;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_CHARGING:// 0x0000dd00|0x03;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_COMPLETED:// 0x0000dd00|0x04;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_MULTI:// 0x0000dd00|0x05;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_ERROR:// 0x0000dd00|0x06;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING:// 0x0000dd00|0x07;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING_COMPLETED:// 0x0000dd00|0x08;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_HEATING:// 0x0000dd00|0x09;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_CONNECTED:// 0x0000dd00|0x0a;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCONNECTED:// 0x0000dd00|0x0b;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_CHARGING_PAUSE:// 0x0000dd00|0x0c;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING_PAUSE:// 0x0000dd00|0x0d;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_SLOW_CHARGING_CONNECTED:// 0x0000dd00|0x0e;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_QUICK_CHARGING_CONNECTED:// 0x0000dd00|0x0f;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING_CONNECTED:// 0x0000dd00|0x10;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_WAITING:// 0x0000dd00|0x11;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_MIS_MATCHED:// 0x0000dd00|0x12;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_NONE:// 0x0000dd00|0x13;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_RESERVE_WAITING:// 0x0000dd00|0x14;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_CHARGING_FAIL:// 0x0000dd00|0x15;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING_FAIL:// 0x0000dd00|0x16;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING_END:// 0x0000dd00|0x17;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE:// 0x0000de00;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DEFAULT:// 0x0000de00|0x01;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.NO_CHARGING:// 0x0000de00|0x02;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.AC_CHARGING:// 0x0000de00|0x03;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.CHARGING_END:// 0x0000de00|0x04;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.CHARGING_CMPL:// 0x0000de00|0x05;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.HEATING:// 0x0000de00|0x06;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.BOOKING:// 0x0000de00|0x07;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.NO_DISCHARING:// 0x0000de00|0x08;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DISCHARING:// 0x0000de00|0x09;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DISCHARING_END:// 0x0000de00|0x0a;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DISCHARING_CMPL:// 0x0000de00|0x0b;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.CHARING_FALUT:// 0x0000de00|0x0c;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DISCHARING_FALUT:// 0x0000de00|0x0d;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.AC_CHRGNFLTCHRGRSIDE:// 0x0000de00|0x0e;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHARGING:// 0x0000de00|0x0f;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTVEHSIDE:// 0x0000de00|0x10;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDETEMPFLT:// 0x0000de00|0x11;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDECONFLT:// 0x0000de00|0x12;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDEHWFLT:// 0x0000de00|0x13;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDEEMGYFLT:// 0x0000de00|0x14;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDECOMFLT:// 0x0000de00|0x15;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.SUPERCHARGING:// 0x0000de00|0x16;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.AC_CHARGINGSUSPEND:// 0x0000de00|0x17;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHARGINGEND:// 0x0000de00|0x18;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.ACCHRGNFLTVEHSIDE:// 0x0000de00|0x19;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.BOOSTCHARGING:// 0x0000de00|0x1a;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.BOOSTCHARGINGFLT:// 0x0000de00|0x1b;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.WIRELESSCHARGING:// 0x0000de00|0x1c;
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.NONE:// 0x0000de00|0x1d;
            case ecarx.carext.vehicle.module.v446k.ICharging.CHARGE_FUNC_PARKING:// 0x00015a00;
            case ecarx.carext.vehicle.module.v446k.ICharging.DISTANCE_PROTECTION:// 0x00015b00;
            case ecarx.carext.vehicle.module.v446k.ICharging.DISTANCE_PROTECTION_SWITCH:// 0x00015c00;
            case ecarx.carext.vehicle.module.v446k.ICharging.CHARGING_ENERGY:// 0x00015e00;
            case ecarx.carext.vehicle.module.v446k.ICharging.DISCHARGING_ENERGY:// 0x00015f00;
            case ecarx.carext.vehicle.module.v446k.ICharging.CHARGING_WORK_TIME:// 0x00016100;
            case ecarx.carext.vehicle.module.v446k.ICharging.DISCHARGING_WORK_TIME:// 0x00016200;
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE:// 0x00016a00;
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_NOREQUESET:// 0x00016a00|0x01;
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_NOACTIVE:// 0x00016a00|0x02;
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING:// 0x00016a00|0x03;
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING_COMPL:// 0x00016a00|0x04;
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING_FAULT:// 0x00016a00|0x05;
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_CANNOTENTER:// 0x00016a00|0x06;
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_SOC:// 0x00016b00;
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_MODE:// 0x00017100;
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_MODE
                    |ecarx.carext.vehicle.module.v446k.ICharging.BatteryMode.BATTERY_MODE_NORMAL:// 0x00017100|0x01;
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_MODE
                    |ecarx.carext.vehicle.module.v446k.ICharging.BatteryMode.BATTERY_MODE_HOLD:// 0x00017100|0x02;
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_MODE
                    |ecarx.carext.vehicle.module.v446k.ICharging.BatteryMode.BATTERY_MODE_CHARGE:// 0x00017100|0x03;
            case ecarx.carext.vehicle.module.v446k.ICharging.BOOK_CHARGING_IMMEDIATELY:// 0x0001af00;
            case ecarx.carext.vehicle.module.v446k.ICharging.EXTERNAL_CHARGING_LIGHT:// 0x0001b200;
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_TYPE:// 0x0001cc00;
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_TYPE
                    |ecarx.carext.vehicle.module.v446k.ICharging.BatteryType.TERNARY_LITHIUM_BATTERY:// 0x0001cc00|0x01;
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_TYPE
                    |ecarx.carext.vehicle.module.v446k.ICharging.BatteryType.LITHIUM_IRON_PHOSPHATE_BATTERY:// 0x0001cc00|0x02;
            case ecarx.carext.vehicle.module.v446k.ICharging.MAX_EV_ENDURANCE:// 0x0001cf00;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_WORK_MODE:// 0x0001e100;
            case ecarx.carext.vehicle.module.v446k.ICharging.MAX_EV_ENDURANCE_BLOCKED:// 0x00021f00;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES:// 0x00022200;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.UNKNOWN:// 0x00022200|0x01;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.NO_DEVICE:// 0x00022200|0x02;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.STANDBY:// 0x00022200|0x03;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.CHARGING:// 0x00022200|0x04;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.CHARGED:// 0x00022200|0x05;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.ERROR:// 0x00022200|0x06;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.OVERHEAT:// 0x00022200|0x07;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.OVERPOWER:// 0x00022200|0x08;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.FOD:// 0x00022200|0x09;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.PEPS_INTERRUPT:// 0x00022200|0x0a;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.OVERVOLTAGE:// 0x00022200|0x0b;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.CHARGING_ONE_FOURTH:// 0x00022200|0x0c;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.CHARGING_HALF:// 0x00022200|0x0d;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.OFF:// 0x00022200|0xff;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_PERCENTAGE:// 0x00022400;
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_REMIND_PHONE_LEAVE_OVER:// 0x00022500;
            case ecarx.carext.vehicle.module.v446k.ICharging.START_ENGINE_WHEN_LOW_POWER:// 0x00023900;
                return ecarx.carext.vehicle.CarExtModule.ICharging;
            case ecarx.carext.vehicle.module.ISensor.ENDURANCE_MILEAGE_EV:// 0x0000cc00;
            case ecarx.carext.vehicle.module.ISensor.BATTERY_PERCENTAGE:// 0x0000cd00;
            case ecarx.carext.vehicle.module.ISensor.AVG_ENERGY_CONSUMPTION:// 0x0000e000;
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE:// 0x0000e800;
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.NORMAL:// 0x0000e800|0x01;
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.FACTORY:// 0x0000e800|0x02;
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.TRANSPORT:// 0x0000e800|0x03;
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.CRASH:// 0x0000e800|0x04;
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.DYNO:// 0x0000e800|0x05;
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.SHOW_ROOM:// 0x0000e800|0x06;
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE:// 0x00010300;
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.ABANDONED:// 0x00010300|0x01;
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.INACTIVE:// 0x00010300|0x02;
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.CONVENIENCE:// 0x00010300|0x03;
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.ACTIVE:// 0x00010300|0x04;
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.DRIVING:// 0x00010300|0x05;
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.UNKNOWN:// 0x00010300|0xff;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE:// 0x0001bb00;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.SLEEP:// 0x0001bb00|0x01;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.STARTUP_READY:// 0x0001bb00|0x02;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.WELCOME:// 0x0001bb00|0x03;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.STARTUP_INACTIVE:// 0x0001bb00|0x04;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.CONVENIENCE:// 0x0001bb00|0x05;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.ACTIVE:// 0x0001bb00|0x06;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.DRIVING:// 0x0001bb00|0x07;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.ERROR:// 0x0001bb00|0x08;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.KEEP_TOUCH:// 0x0001bb00|0x09;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.PREPARE_SHUTDOWN:// 0x0001bb00|0x0a;
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.STR:// 0x0001bb00|0x0b;
            case ecarx.carext.vehicle.module.v446k.ISensor.CHARGING_ENDURANCE_MILEAGE:// 0x00016500;
            case ecarx.carext.vehicle.module.v446k.ISensor.DISCHARGING_ENDURANCE_MILEAGE:// 0x00016600;
            case ecarx.carext.vehicle.module.v446k.ISensor.FUEL_WARNING_COLOR:// 0x00016900;
            case ecarx.carext.vehicle.module.v446k.ISensor.FUEL_WARNING_COLOR
                    |ecarx.carext.vehicle.module.v446k.ISensor.FuelWarningColor.FUEL_WARNING_COLOR_NORMAL:// 0x00016900|0x01;
            case ecarx.carext.vehicle.module.v446k.ISensor.FUEL_WARNING_COLOR
                    |ecarx.carext.vehicle.module.v446k.ISensor.FuelWarningColor.FUEL_WARNING_COLOR_YELLOW:// 0x00016900|0x02;
            case ecarx.carext.vehicle.module.v446k.ISensor.AVG_FUEL_CONSUMPTION:// 0x00017800;
            case ecarx.carext.vehicle.module.v446k.ISensor.FUEL_PERCENTAGE:// 0x00017c00;
            case ecarx.carext.vehicle.module.v446k.ISensor.ENDURANCE_MILEAGE_FUEL:// 0x00017d00;
            case ecarx.carext.vehicle.module.v446k.ISensor.PM25_INDOOR:// 0x00029d00;
            case ecarx.carext.vehicle.module.v446k.ISensor.AQI_INDOOR:// 0x0002ac00;
                return ecarx.carext.vehicle.CarExtModule.ISensor;
            case ecarx.carext.vehicle.module.ISceneMode.AUTO_SHOW_MODE:// 0x0000bb00;
            case ecarx.carext.vehicle.module.ISceneMode.PARKING_COMFORT_MODE:// 0x00018100;
            case ecarx.carext.vehicle.module.ISceneMode.NAP_MODE:// 0x00018200;
            case ecarx.carext.vehicle.module.v446k.ISceneMode.SCENE_MODE_CAST_STATE:// 0x00020d00;
            case ecarx.carext.vehicle.module.v446k.ISceneMode.SCENE_MODE_CAST_STATE
                    |ecarx.carext.vehicle.module.v446k.ISceneMode.SceneModeCastState.CAST_ALLOW:// 0x00020d00|0x01;
            case ecarx.carext.vehicle.module.v446k.ISceneMode.SCENE_MODE_CAST_STATE
                    |ecarx.carext.vehicle.module.v446k.ISceneMode.SceneModeCastState.CAST_NOT_ALLOW:// 0x00020d00|0x02;
            case ecarx.carext.vehicle.module.v446k.ISceneMode.AUTO_SHOW_MODE_ENTER_CONDITIONS:// 0x00024e00;
            case ecarx.carext.vehicle.module.v446k.ISceneMode.AUTO_SHOW_MODE_ENTER_CONDITIONS
                    |ecarx.carext.vehicle.module.v446k.ISceneMode.AutoShowModeStatus.MEET:// 0x00024e00|0x01;
            case ecarx.carext.vehicle.module.v446k.ISceneMode.AUTO_SHOW_MODE_ENTER_CONDITIONS
                    |ecarx.carext.vehicle.module.v446k.ISceneMode.AutoShowModeStatus.NOT_MEET:// 0x00024e00|0x02;
                return ecarx.carext.vehicle.CarExtModule.ISceneMode;
            case ecarx.carext.vehicle.module.ISafety.APPROACH_UNLOCK:// 0x00005600;
            case ecarx.carext.vehicle.module.ISafety.AWAY_LOCK:// 0x00005700;
            case ecarx.carext.vehicle.module.ISafety.KEYLESS_UNLOCKING:// 0x00005900;
            case ecarx.carext.vehicle.module.ISafety.KEYLESS_UNLOCKING
                    |ecarx.carext.vehicle.module.ISafety.KeylessUnlockingValue.KEYLESS_UNLOCKING_ALL_DOORS:// 0x00005900|0x01;
            case ecarx.carext.vehicle.module.ISafety.KEYLESS_UNLOCKING
                    |ecarx.carext.vehicle.module.ISafety.KeylessUnlockingValue.KEYLESS_UNLOCKING_SINGLE_DOOR:// 0x00005900|0x02;
            case ecarx.carext.vehicle.module.ISafety.KEYLESS_UNLOCKING
                    |ecarx.carext.vehicle.module.ISafety.KeylessUnlockingValue.KEYLESS_UNLOCKING_OFF:// 0x00005900|0xff;
            case ecarx.carext.vehicle.module.ISafety.CENTRAL_LOCK:// 0x00018700;
            case ecarx.carext.vehicle.module.v446k.ISafety.GLOVEBOX_LOCK:// 0x0001d800;
            case ecarx.carext.vehicle.module.v446k.ISafety.PARKING_AUTO_UNLOCK:// 0x0001dc00;
            case ecarx.carext.vehicle.module.v446k.ISafety.TWOSTEP_UNLOCKING:// 0x0001de00;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC:// 0x00029f00;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD1:// 0x00029f00|0x01;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD2:// 0x00029f00|0x02;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD1_OK:// 0x00029f00|0x05;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD2_OK:// 0x00029f00|0x06;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD1_NOK:// 0x00029f00|0x09;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD2_NOK:// 0x00029f00|0x0a;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK:// 0x0002a000;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_ACCESS_PASSWORD:// 0x0002a100;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_REMINDER:// 0x0002a200;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_REMINDER
                    |ecarx.carext.vehicle.module.v446k.ISafety.PrivateLockReminder.ON:// 0x0002a200|0x01;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_REMINDER
                    |ecarx.carext.vehicle.module.v446k.ISafety.PrivateLockReminder.REMINDER:// 0x0002a200|0x02;
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_REMINDER
                    |ecarx.carext.vehicle.module.v446k.ISafety.PrivateLockReminder.OFF:// 0x0002a200|0xff;
                return ecarx.carext.vehicle.CarExtModule.ISafety;
            case ecarx.carext.vehicle.module.IAmbienceLight.AMBIENCE_LIGHT_VOICE:// 0x00016300;
            case ecarx.carext.vehicle.module.IAmbienceLight.AMBIENCE_LIGHT_INTENSITY:// 0x0001a800;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR:// 0x00026000;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM1:// 0x00026000|0x01;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM2:// 0x00026000|0x02;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM3:// 0x00026000|0x03;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM4:// 0x00026000|0x04;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM5:// 0x00026000|0x05;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM6:// 0x00026000|0x06;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM7:// 0x00026000|0x07;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_MUSIC_SHOWMODE:// 0x00026300;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED:// 0x00031e00;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_LEFT_SEAT:// 0x00031e00|0x01;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_RIGHT_SEAT:// 0x00031e00|0x02;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.IS_BROADCASTING:// 0x00031e00|0x03;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_FRONT_LEFT_SEAT:// 0x00031e00|0x04;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_FRONT_RIGHT_SEAT:// 0x00031e00|0x05;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_LEFT_REAR_SEAT:// 0x00031e00|0x06;
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_RIGHT_REAR_SEAT:// 0x00031e00|0x07;
                return ecarx.carext.vehicle.CarExtModule.IAmbienceLight;
            case ecarx.carext.vehicle.module.IPAS.RCTA_WARNING_VOLUME:// 0x0000f300;
            case ecarx.carext.vehicle.module.IPAS.RCTA_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IPAS.RCTAWarningVolumeMode.LOW:// 0x0000f300|0x01;
            case ecarx.carext.vehicle.module.IPAS.RCTA_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IPAS.RCTAWarningVolumeMode.MID:// 0x0000f300|0x02;
            case ecarx.carext.vehicle.module.IPAS.RCTA_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IPAS.RCTAWarningVolumeMode.HIGH:// 0x0000f300|0x03;
            case ecarx.carext.vehicle.module.IPAS.RCTA_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IPAS.RCTAWarningVolumeMode.OFF:// 0x0000f300|0xff;
            case ecarx.carext.vehicle.module.IPAS.PEB_SWITCH:// 0x0000f400;
            case ecarx.carext.vehicle.module.IPAS.HPA_CAR_CONFIG_HMI_CAR_STATE:// 0x00019200;
            case ecarx.carext.vehicle.module.v446k.IPAS.DLC_SWITCH:// 0x0001e300;
            case ecarx.carext.vehicle.module.v446k.IPAS.DLC_AUTHORIZED_TIME:// 0x0001e400;
            case ecarx.carext.vehicle.module.v446k.IPAS.PAS_MUTE:// 0x0002af00;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_INNER_LEFT:// 0x0002b100;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_INNER_RIGHT:// 0x0002b200;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_OUT_LEFT:// 0x0002b300;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_OUT_RIGHT:// 0x0002b400;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_LEFT_SIDE:// 0x0002b500;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_RIGHT_SIDE:// 0x0002b600;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_LEFT_SIDE:// 0x0002b700;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_RIGHT_SIDE:// 0x0002b800;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_OUT_LEFT:// 0x0002b900;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_OUT_RIGHT:// 0x0002ba00;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_INNER_LEFT:// 0x0002bb00;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_INNER_RIGHT:// 0x0002bc00;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_MAX_DISTANCE:// 0x0002bd00;
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_MIN_DISTANCE:// 0x0002be00;
                return ecarx.carext.vehicle.CarExtModule.IPAS;
            case ecarx.carext.vehicle.module.ITripData.TRIP_DI_AVG_SPEED:// 0x00008b00;
            case ecarx.carext.vehicle.module.ITripData.TRIP_DI_TOTAL_DISTANCE:// 0x00008c00;
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_RESET:// 0x00008d00;
            case ecarx.carext.vehicle.module.ITripData.TRIP_DI_AVG_ELC_CONSUMPTION_ARRAY:// 0x00008f00;
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_AUTO_RESET_OPTION:// 0x00009200;
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_AUTO_RESET_OPTION
                    |ecarx.carext.vehicle.module.ITripData.AutoResetTripOption.AUTO_RESET_OPTION_4_HOURS:// 0x00009200|0x01;
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_AUTO_RESET_OPTION
                    |ecarx.carext.vehicle.module.ITripData.AutoResetTripOption.AUTO_RESET_OPTION_CHARGING:// 0x00009200|0x02;
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_AUTO_RESET_OPTION
                    |ecarx.carext.vehicle.module.ITripData.AutoResetTripOption.AUTO_RESET_OPTION_PARKING_OIL:// 0x00009200|0x03;
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_AUTO_RESET_OPTION
                    |ecarx.carext.vehicle.module.ITripData.AutoResetTripOption.AUTO_RESET_OPTION_PARKING:// 0x00009200|0x04;
            case ecarx.carext.vehicle.module.ITripData.TRIP_DI_TOTAL_DURATION:// 0x0000e100;
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_DOT_INTERVAL:// 0x00017200;
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ED_DRIVING_ENERGY_FLOW:// 0x00017300;
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ED_CLIMATE_ENERGY_FLOW:// 0x00017400;
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ED_BATTERY_ENERGY_FLOW:// 0x00017500;
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ED_OTHER_ENERGY_FLOW:// 0x00017600;
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_DI_AVG_FUEL_CONSUMPTION_ARRAY:// 0x00017900;
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE:// 0x00032900;
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.ITripData.EnergyCurveDisplayMode.FUEL_CONSUMPTION:// 0x00032900|0x01;
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.ITripData.EnergyCurveDisplayMode.ELECTRICITY_CONSUMPTION:// 0x00032900|0x02;
                return ecarx.carext.vehicle.CarExtModule.ITripData;
            case ecarx.carext.vehicle.module.IADAS.AUTONOMOUS_EMERGENCY_BRAKING:// 0x00005f00;
            case ecarx.carext.vehicle.module.IADAS.FORWARD_COLLISION_WARN_SNVT:// 0x00007200;
            case ecarx.carext.vehicle.module.IADAS.FORWARD_COLLISION_WARN_SNVT
                    |ecarx.carext.vehicle.module.IADAS.ForwardCollisionWarnSnvtyMode.LOW:// 0x00007200|0x01;
            case ecarx.carext.vehicle.module.IADAS.FORWARD_COLLISION_WARN_SNVT
                    |ecarx.carext.vehicle.module.IADAS.ForwardCollisionWarnSnvtyMode.NORMAL:// 0x00007200|0x02;
            case ecarx.carext.vehicle.module.IADAS.FORWARD_COLLISION_WARN_SNVT
                    |ecarx.carext.vehicle.module.IADAS.ForwardCollisionWarnSnvtyMode.HIGH:// 0x00007200|0x03;
            case ecarx.carext.vehicle.module.IADAS.FORWARD_COLLISION_WARN_SNVT
                    |ecarx.carext.vehicle.module.IADAS.ForwardCollisionWarnSnvtyMode.OFF:// 0x00007200|0xff;
            case ecarx.carext.vehicle.module.IADAS.DOOR_OPEN_WARN_ACTIVE:// 0x00007400;
            case ecarx.carext.vehicle.module.IADAS.REAR_COLLISION_WARNING:// 0x00007600;
            case ecarx.carext.vehicle.module.IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE:// 0x00007a00;
            case ecarx.carext.vehicle.module.IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE
                    |ecarx.carext.vehicle.module.IADAS.IntelligentDrivingMode.ACC_MODE:// 0x00007a00|0x01;
            case ecarx.carext.vehicle.module.IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE
                    |ecarx.carext.vehicle.module.IADAS.IntelligentDrivingMode.ICC_MODE:// 0x00007a00|0x02;
            case ecarx.carext.vehicle.module.IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE
                    |ecarx.carext.vehicle.module.IADAS.IntelligentDrivingMode.HNOA_MODE:// 0x00007a00|0x04;
            case ecarx.carext.vehicle.module.IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE
                    |ecarx.carext.vehicle.module.IADAS.IntelligentDrivingMode.OFF:// 0x00007a00|0xff;
            case ecarx.carext.vehicle.module.IADAS.AUTO_LANE_CHANGE:// 0x00007e00;
            case ecarx.carext.vehicle.module.IADAS.DRIVER_PERFOR_SUPPORT:// 0x00008300;
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL:// 0x0000a500;
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL
                    |ecarx.carext.vehicle.module.IADAS.SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_HEAVY:// 0x0000a500|0x01;
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL
                    |ecarx.carext.vehicle.module.IADAS.SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_MEDIUM:// 0x0000a500|0x02;
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL
                    |ecarx.carext.vehicle.module.IADAS.SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_SO:// 0x0000a500|0x03;
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL
                    |ecarx.carext.vehicle.module.IADAS.SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_AUTO:// 0x0000a500|0x04;
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL
                    |ecarx.carext.vehicle.module.IADAS.SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_OFF:// 0x0000a500|0xff;
            case ecarx.carext.vehicle.module.IADAS.LANE_DEPARTURE_WARNING:// 0x0000ab00;
            case ecarx.carext.vehicle.module.IADAS.ACC_WITH_TSR:// 0x0000ed00;
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID:// 0x0000ee00;
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID_WARNING:// 0x0000ef00;
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID_WARNING
                    |ecarx.carext.vehicle.module.IADAS.LaneKeepingAidWarningMode.SOUND:// 0x0000ef00|0x01;
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID_WARNING
                    |ecarx.carext.vehicle.module.IADAS.LaneKeepingAidWarningMode.HAPTIC:// 0x0000ef00|0x02;
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID_WARNING
                    |ecarx.carext.vehicle.module.IADAS.LaneKeepingAidWarningMode.SOUND_HAPTIC:// 0x0000ef00|0x03;
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID_WARNING
                    |ecarx.carext.vehicle.module.IADAS.LaneKeepingAidWarningMode.OFF:// 0x0000ef00|0x04;
            case ecarx.carext.vehicle.module.IADAS.REAR_CROSS_TRAFFIC_ALERT:// 0x0000f000;
            case ecarx.carext.vehicle.module.IADAS.LANE_CHANGE_WARNING_MODE:// 0x0000f100;
            case ecarx.carext.vehicle.module.IADAS.LANE_CHANGE_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.LaneChangeWarningMode.VISUAL:// 0x0000f100|0x01;
            case ecarx.carext.vehicle.module.IADAS.LANE_CHANGE_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.LaneChangeWarningMode.SOUND:// 0x0000f100|0x02;
            case ecarx.carext.vehicle.module.IADAS.LANE_CHANGE_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.LaneChangeWarningMode.VISUAL_SOUND:// 0x0000f100|0x03;
            case ecarx.carext.vehicle.module.IADAS.LANE_CHANGE_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.LaneChangeWarningMode.OFF:// 0x0000f100|0xff;
            case ecarx.carext.vehicle.module.IADAS.EMERGENCY_LANE_KEEP_AID:// 0x0001c200;
            case ecarx.carext.vehicle.module.IADAS.SPEED_LIMIT_WARNING_MODE:// 0x0001c300;
            case ecarx.carext.vehicle.module.IADAS.SPEED_LIMIT_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.SpeedLimitationMode.VISUAL:// 0x0001c300|0x01;
            case ecarx.carext.vehicle.module.IADAS.SPEED_LIMIT_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.SpeedLimitationMode.SOUND:// 0x0001c300|0x02;
            case ecarx.carext.vehicle.module.IADAS.SPEED_LIMIT_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.SpeedLimitationMode.VISUAL_SOUND:// 0x0001c300|0x03;
            case ecarx.carext.vehicle.module.IADAS.SPEED_LIMIT_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.SpeedLimitationMode.CLOSE:// 0x0001c300|0xff;
            case ecarx.carext.vehicle.module.v446k.IADAS.LANE_CHANGE_STRATEGY:// 0x0001d700;
            case ecarx.carext.vehicle.module.v446k.IADAS.LANE_CHANGE_STRATEGY
                    |ecarx.carext.vehicle.module.v446k.IADAS.LaneChangeStrategy.GENTLE:// 0x0001d700|0x01;
            case ecarx.carext.vehicle.module.v446k.IADAS.LANE_CHANGE_STRATEGY
                    |ecarx.carext.vehicle.module.v446k.IADAS.LaneChangeStrategy.STANDARD:// 0x0001d700|0x02;
            case ecarx.carext.vehicle.module.v446k.IADAS.LANE_CHANGE_STRATEGY
                    |ecarx.carext.vehicle.module.v446k.IADAS.LaneChangeStrategy.RADICAL:// 0x0001d700|0x03;
            case ecarx.carext.vehicle.module.v446k.IADAS.LANE_CHANGE_STRATEGY
                    |ecarx.carext.vehicle.module.v446k.IADAS.LaneChangeStrategy.OFF:// 0x0001d700|0xff;
            case ecarx.carext.vehicle.module.v446k.IADAS.VOICE_BROADCAST_MODE:// 0x0001da00;
            case ecarx.carext.vehicle.module.v446k.IADAS.VOICE_BROADCAST_MODE
                    |ecarx.carext.vehicle.module.v446k.IADAS.VoiceBroadcastMode.DETAILED:// 0x0001da00|0x01;
            case ecarx.carext.vehicle.module.v446k.IADAS.VOICE_BROADCAST_MODE
                    |ecarx.carext.vehicle.module.v446k.IADAS.VoiceBroadcastMode.STREAMLINED:// 0x0001da00|0x02;
            case ecarx.carext.vehicle.module.v446k.IADAS.VOICE_BROADCAST_MODE
                    |ecarx.carext.vehicle.module.v446k.IADAS.VoiceBroadcastMode.OFF:// 0x0001da00|0xff;
            case ecarx.carext.vehicle.module.v446k.IADAS.SPEED_LIMIT_WARNING_OFFSET_VALUE:// 0x0001dd00;
            case ecarx.carext.vehicle.module.v446k.IADAS.DRIVER_PERFORMANCE_AUTHORIZED_TIME:// 0x0001df00;
            case ecarx.carext.vehicle.module.v446k.IADAS.DPS_UPDATE_AUTHORIZED_TIME:// 0x00022300;
            case ecarx.carext.vehicle.module.v446k.IADAS.DCL_UPDATE_AUTHORIZED_TIME:// 0x00022700;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_LANE_CHANGE_CALLBACK:// 0x00027200;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_CAN_ACTIVATE_CALLBACK:// 0x00027300;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_STATUS_CALLBACK:// 0x00027400;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_EXIT_ALERT_CALLBACK:// 0x00027500;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_FIRST_ACTIVATION_CALLBACK:// 0x00027600;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_ICC_CANNOT_ACTIVATE_CALLBACK:// 0x00027700;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_CAREFUL_DRIVE_CALLBACK:// 0x00027800;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_EYES_OFF_WARN_CALLBACK:// 0x00027900;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_ACC_CANNOT_ACTIVATE_CALLBACK:// 0x00027a00;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_ACC_STATUS_CALLBACK:// 0x00027b00;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_ICC_STATUS_CALLBACK:// 0x00027c00;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_CANNOT_ACTIVATE_CALLBACK:// 0x00027d00;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_TAKE_OVER_CALLBACK:// 0x00027e00;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_REGULAR_CALLBACK:// 0x00027f00;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_LANE_CHANGE_EXTEND_CALLBACK:// 0x00028000;
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_HAND_OFF_CALLBACK:// 0x00028100;
            case ecarx.carext.vehicle.module.v446k.IADAS.RCTA_WARNING_LEFT:// 0x00032700;
            case ecarx.carext.vehicle.module.v446k.IADAS.RCTA_WARNING_RIGHT:// 0x00032800;
                return ecarx.carext.vehicle.CarExtModule.IADAS;
            case ecarx.carext.vehicle.module.IHvac.TEMPERATURE_INCREMENTAL_ADJUST:// 0x00002c00;
            case ecarx.carext.vehicle.module.IHvac.FAN_SPEED_INCREMENTAL_ADJUST:// 0x00002d00;
            case ecarx.carext.vehicle.module.IHvac.CIRCULATION:// 0x00003600;
            case ecarx.carext.vehicle.module.IHvac.CIRCULATION
                    |ecarx.carext.vehicle.module.IHvac.CirculationMode.INTERNAL:// 0x00003600|0x01;
            case ecarx.carext.vehicle.module.IHvac.CIRCULATION
                    |ecarx.carext.vehicle.module.IHvac.CirculationMode.EXTERNAL:// 0x00003600|0x02;
            case ecarx.carext.vehicle.module.IHvac.CIRCULATION
                    |ecarx.carext.vehicle.module.IHvac.CirculationMode.AUTO:// 0x00003600|0x03;
            case ecarx.carext.vehicle.module.IHvac.CIRCULATION
                    |ecarx.carext.vehicle.module.IHvac.CirculationMode.OFF:// 0x00003600|0xff;
            case ecarx.carext.vehicle.module.IHvac.AUTO_STEERING_WHEEL_HEAT_SWITCH:// 0x00004400;
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE:// 0x0000c800;
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_FOCUS:// 0x0000c800|0x01;
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_AVOID:// 0x0000c800|0x02;
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_CUSTOM:// 0x0000c800|0x03;
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_SWING:// 0x0000c800|0x04;
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_ADAPTIVE:// 0x0000c800|0x05;
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_MANUAL:// 0x0000c800|0x06;
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_OFF:// 0x0000c800|0xff;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_CLEAN_ZONE:// 0x00020200;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_CLEAN_ZONE
                    |ecarx.carext.vehicle.module.v446k.IHvac.CleanZoneState.ON:// 0x00020200|0x01;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_CLEAN_ZONE
                    |ecarx.carext.vehicle.module.v446k.IHvac.CleanZoneState.OFF:// 0x00020200|0x02;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_CLEAN_ZONE
                    |ecarx.carext.vehicle.module.v446k.IHvac.CleanZoneState.NONE:// 0x00020200|0xff;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE:// 0x00020300;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT:// 0x00020400;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceSlot.AIR_FRAGRANCE_SLOT_1:// 0x00020400|0x01;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceSlot.AIR_FRAGRANCE_SLOT_2:// 0x00020400|0x02;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceSlot.AIR_FRAGRANCE_SLOT_3:// 0x00020400|0x04;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceSlot.AIR_FRAGRANCE_SLOT_4:// 0x00020400|0x10;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceSlot.AIR_FRAGRANCE_SLOT_5:// 0x00020400|0x20;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LEVEL:// 0x00020500;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LEVEL
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceLevel.AIR_FRAGRANCE_LEVEL_1:// 0x00020500|0x01;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LEVEL
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceLevel.AIR_FRAGRANCE_LEVEL_2:// 0x00020500|0x02;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LEVEL
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceLevel.AIR_FRAGRANCE_LEVEL_3:// 0x00020500|0x03;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LEVEL
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceLevel.AIR_FRAGRANCE_LEVEL_OFF:// 0x00020500|0xff;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FRAGRANCE_MARGIN:// 0x00020600;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AUTO_DRY:// 0x00020700;
            case ecarx.carext.vehicle.module.v446k.IHvac.AIR_FRAGRANCE_TYPE_ID:// 0x00022100;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LOW:// 0x00022a00;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY:// 0x00024800;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.SEAT_HEAT:// 0x00024800|0x01;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.SEAT_VENTILATION:// 0x00024800|0x02;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.WHEEL_HEAT:// 0x00024800|0x03;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.SEAT_HEAT_AUTO:// 0x00024800|0x04;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.SEAT_VENTILATION_AUTO:// 0x00024800|0x05;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.DEFROST_REAR:// 0x00024800|0x06;
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.DEFROST_REARVIEW_MIRROR:// 0x00024800|0x07;
            case ecarx.carext.vehicle.module.v446k.IHvac.ELECTRONIC_AIR_OUTLET_SWITCH:// 0x00024c00;
            case ecarx.carext.vehicle.module.v446k.IHvac.ELECTRONIC_AIR_OUTLET_DIRECTION_ADJUST:// 0x00024d00;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE:// 0x00026800;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_FACE:// 0x00026800|0x01;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_LEG:// 0x00026800|0x02;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_FACE_AND_LEG:// 0x00026800|0x03;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_FRONT_WINDOW:// 0x00026800|0x04;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_FACE_AND_FRONT_WIND:// 0x00026800|0x05;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_LEG_AND_FRONT_WINDO:// 0x00026800|0x06;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_ALL:// 0x00026800|0x07;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_AUTO_SWITCH:// 0x00026800|0x08;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_OFF:// 0x00026800|0xff;
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_AUTO_STATUS:// 0x00026900;
            case ecarx.carext.vehicle.module.v446k.IHvac.STEERING_WHEEL_HEATING_AUTO_START:// 0x0002ad00;
                return ecarx.carext.vehicle.CarExtModule.IHvac;
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_VER_MCU_FIRMWARE:// 0x0000e300;
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_VER_MCU_HARDWARE:// 0x0000e400;
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_VER_MCU_FIRMWARE_INT:// 0x0000e500;
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_XDSN:// 0x00010400;
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_IHUID:// 0x00010500;
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_VEHICLE_TYPE_CONFIG:// 0x00010600;
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_PROJECT_CODE:// 0x00010700;
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_SUPPLIER_CODE:// 0x00010800;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_MODEL_COLOR:// 0x00018d00;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_MOTION_STATUS:// 0x00018f00;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES:// 0x00019f00;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.FUEL:// 0x00019f00|0x01;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.HEV:// 0x00019f00|0x02;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.PHEV:// 0x00019f00|0x03;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.EREV:// 0x00019f00|0x04;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.FCEV:// 0x00019f00|0x05;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.FCV:// 0x00019f00|0x06;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.MHEV:// 0x00019f00|0x07;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.BEV:// 0x00019f00|0x08;
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.UNKNOWN:// 0x00019f00|0xff;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.UNIT_TIME_FORMAT:// 0x0001e800;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.UNIT_TIME_FORMAT
                    |ecarx.carext.vehicle.module.v446k.ICarInfo.TimeUnit.UNIT_12:// 0x0001e800|0x01;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.UNIT_TIME_FORMAT
                    |ecarx.carext.vehicle.module.v446k.ICarInfo.TimeUnit.UNIT_24:// 0x0001e800|0x02;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.VEHICLE_MAINTENANCE_STATE:// 0x0001f000;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.VEHICLE_MAINTENANCE_MILEAGE:// 0x0001f100;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.VEHICLE_MAINTENANCE_TIME:// 0x0001f200;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.ENGINE_MAINTENANCE_STATE:// 0x0001f300;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.ENGINE_MAINTENANCE_MILEAGE:// 0x0001f400;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.ENGINE_MAINTENANCE_TIME:// 0x0001f500;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.VEHICLE_MAINTENANCE_TIME_CYCLE:// 0x00026d00;
            case ecarx.carext.vehicle.module.v446k.ICarInfo.VEHICLE_MAINTENANCE_MILEAGE_CYCLE:// 0x00026e00;
                return ecarx.carext.vehicle.CarExtModule.ICarInfo;
        }
        if (id > 0x10000000) { return ecarx.carext.vehicle.CarExtModule.AAOS;};
        return null;
    }

    @NonNull
    public static String findIdNameById(int id) {
        // Find the name of the ID, if not found, return the hex string of the ID
        switch(id) {
            case ecarx.carext.vehicle.module.ISeat.EASY_INGRESS_EGRESS:// 0x00005100;
                return "ISeat.EASY_INGRESS_EGRESS";
            case ecarx.carext.vehicle.module.ISeat.BACKREST_ANGLE_ADJUST:// 0x00018300;
                return "ISeat.BACKREST_ANGLE_ADJUST";
            case ecarx.carext.vehicle.module.ISeat.SEAT_VERTICAL_ADJUST:// 0x00018400;
                return "ISeat.SEAT_VERTICAL_ADJUST";
            case ecarx.carext.vehicle.module.ISeat.SEAT_HORIZONTAL_ADJUST:// 0x00018500;
                return "ISeat.SEAT_HORIZONTAL_ADJUST";
            case ecarx.carext.vehicle.module.ISeat.MASSAGE_SWITCH:// 0x0001a700;
                return "ISeat.MASSAGE_SWITCH";
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_OCCUPANCY_STATUS:// 0x0001ee00;
                return "ISeat.SEAT_OCCUPANCY_STATUS";
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_MODE_ADJUST:// 0x00028200;
                return "ISeat.SEAT_MODE_ADJUST";
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_MODE_ADJUST
                    |ecarx.carext.vehicle.module.v446k.ISeat.SeatMode.MODE1:// 0x00028200|0x01;
                return "ISeat.SEAT_MODE_ADJUST|SeatMode.MODE1";
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_MODE_ADJUST
                    |ecarx.carext.vehicle.module.v446k.ISeat.SeatMode.MODE2:// 0x00028200|0x02;
                return "ISeat.SEAT_MODE_ADJUST|SeatMode.MODE2";
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_MODE_ADJUST
                    |ecarx.carext.vehicle.module.v446k.ISeat.SeatMode.MODE3:// 0x00028200|0x03;
                return "ISeat.SEAT_MODE_ADJUST|SeatMode.MODE3";
            case ecarx.carext.vehicle.module.v446k.ISeat.SEAT_ADJUST_HARD_SWITCH_STATUS:// 0x00031f00;
                return "ISeat.SEAT_ADJUST_HARD_SWITCH_STATUS";
            case ecarx.carext.vehicle.module.IHUD.HUD_ACTIVE:// 0x00006000;
                return "IHUD.HUD_ACTIVE";
            case ecarx.carext.vehicle.module.IHUD.HUD_ANGLE_ADJUST:// 0x00006300;
                return "IHUD.HUD_ANGLE_ADJUST";
            case ecarx.carext.vehicle.module.IHUD.HUD_BRIGHTNESS_ADJUST:// 0x00006400;
                return "IHUD.HUD_BRIGHTNESS_ADJUST";
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_NAVI:// 0x00006600;
                return "IHUD.HUD_DISPLAY_NAVI";
            case ecarx.carext.vehicle.module.IHUD.HUD_SNOW_MODE:// 0x00006800;
                return "IHUD.HUD_SNOW_MODE";
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_MODE:// 0x00008e00;
                return "IHUD.HUD_DISPLAY_MODE";
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.IHUD.HudDisplayMode.SIMPLE:// 0x00008e00|0x01;
                return "IHUD.HUD_DISPLAY_MODE|HudDisplayMode.SIMPLE";
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.IHUD.HudDisplayMode.MAP:// 0x00008e00|0x02;
                return "IHUD.HUD_DISPLAY_MODE|HudDisplayMode.MAP";
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.IHUD.HudDisplayMode.STANDARD:// 0x00008e00|0x03;
                return "IHUD.HUD_DISPLAY_MODE|HudDisplayMode.STANDARD";
            case ecarx.carext.vehicle.module.IHUD.HUD_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.IHUD.HudDisplayMode.AR:// 0x00008e00|0x04;
                return "IHUD.HUD_DISPLAY_MODE|HudDisplayMode.AR";
            case ecarx.carext.vehicle.module.IHUD.HUD_ANGLE:// 0x00021600;
                return "IHUD.HUD_ANGLE";
            case ecarx.carext.vehicle.module.IHUD.HUD_BRIGHTNESS:// 0x00021a00;
                return "IHUD.HUD_BRIGHTNESS";
            case ecarx.carext.vehicle.module.IHUD.HUD_NOTIFY_AR_ADJUST:// 0x00021d00;
                return "IHUD.HUD_NOTIFY_AR_ADJUST";
            case ecarx.carext.vehicle.module.v446k.IHUD.HUD_ANGLE_RESET:// 0x0001d900;
                return "IHUD.HUD_ANGLE_RESET";
            case ecarx.carext.vehicle.module.v446k.IHUD.HUD_CAST_ACTIVE:// 0x00033a00;
                return "IHUD.HUD_CAST_ACTIVE";
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE:// 0x00007b00;
                return "IAudio.SOUND_FIELD_MODE";
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_ALL:// 0x00007b00|0x01;
                return "IAudio.SOUND_FIELD_MODE|SoundFieldMode.SOUND_FIELD_MODE_ALL";
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_DRIVER:// 0x00007b00|0x02;
                return "IAudio.SOUND_FIELD_MODE|SoundFieldMode.SOUND_FIELD_MODE_DRIVER";
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_COPILOT:// 0x00007b00|0x03;
                return "IAudio.SOUND_FIELD_MODE|SoundFieldMode.SOUND_FIELD_MODE_COPILOT";
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_SURROUND:// 0x00007b00|0x04;
                return "IAudio.SOUND_FIELD_MODE|SoundFieldMode.SOUND_FIELD_MODE_SURROUND";
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_REAR:// 0x00007b00|0x05;
                return "IAudio.SOUND_FIELD_MODE|SoundFieldMode.SOUND_FIELD_MODE_REAR";
            case ecarx.carext.vehicle.module.IAudio.SOUND_FIELD_MODE
                    |ecarx.carext.vehicle.module.IAudio.SoundFieldMode.SOUND_FIELD_MODE_FREE:// 0x00007b00|0x06;
                return "IAudio.SOUND_FIELD_MODE|SoundFieldMode.SOUND_FIELD_MODE_FREE";
            case ecarx.carext.vehicle.module.IAudio.AUDIO_COMPENSATION_LEVEL:// 0x00008200;
                return "IAudio.AUDIO_COMPENSATION_LEVEL";
            case ecarx.carext.vehicle.module.IAudio.AUDIO_COMPENSATION_LEVEL
                    |ecarx.carext.vehicle.module.IAudio.CompensationLevel.LOW:// 0x00008200|0x01;
                return "IAudio.AUDIO_COMPENSATION_LEVEL|CompensationLevel.LOW";
            case ecarx.carext.vehicle.module.IAudio.AUDIO_COMPENSATION_LEVEL
                    |ecarx.carext.vehicle.module.IAudio.CompensationLevel.MEDIUM:// 0x00008200|0x02;
                return "IAudio.AUDIO_COMPENSATION_LEVEL|CompensationLevel.MEDIUM";
            case ecarx.carext.vehicle.module.IAudio.AUDIO_COMPENSATION_LEVEL
                    |ecarx.carext.vehicle.module.IAudio.CompensationLevel.HIGH:// 0x00008200|0x03;
                return "IAudio.AUDIO_COMPENSATION_LEVEL|CompensationLevel.HIGH";
            case ecarx.carext.vehicle.module.IAudio.AUDIO_COMPENSATION_LEVEL
                    |ecarx.carext.vehicle.module.IAudio.CompensationLevel.OFF:// 0x00008200|0xff;
                return "IAudio.AUDIO_COMPENSATION_LEVEL|CompensationLevel.OFF";
            case ecarx.carext.vehicle.module.IAudio.SOUND_WARNING_VOLUME:// 0x00008500;
                return "IAudio.SOUND_WARNING_VOLUME";
            case ecarx.carext.vehicle.module.IAudio.SOUND_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IAudio.SoundWarningVolume.LOW:// 0x00008500|0x01;
                return "IAudio.SOUND_WARNING_VOLUME|SoundWarningVolume.LOW";
            case ecarx.carext.vehicle.module.IAudio.SOUND_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IAudio.SoundWarningVolume.MID:// 0x00008500|0x02;
                return "IAudio.SOUND_WARNING_VOLUME|SoundWarningVolume.MID";
            case ecarx.carext.vehicle.module.IAudio.SOUND_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IAudio.SoundWarningVolume.HIGH:// 0x00008500|0x03;
                return "IAudio.SOUND_WARNING_VOLUME|SoundWarningVolume.HIGH";
            case ecarx.carext.vehicle.module.IAudio.SOUND_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IAudio.SoundWarningVolume.OFF:// 0x00008500|0xff;
                return "IAudio.SOUND_WARNING_VOLUME|SoundWarningVolume.OFF";
            case ecarx.carext.vehicle.module.IAudio.SOFT_BUTTON_SOUND_TYPE:// 0x00008600;
                return "IAudio.SOFT_BUTTON_SOUND_TYPE";
            case ecarx.carext.vehicle.module.IAudio.SOFT_BUTTON_SOUND_TYPE
                    |ecarx.carext.vehicle.module.IAudio.SoftButtonSoundType.TYPE_1:// 0x00008600|0x01;
                return "IAudio.SOFT_BUTTON_SOUND_TYPE|SoftButtonSoundType.TYPE_1";
            case ecarx.carext.vehicle.module.IAudio.SOFT_BUTTON_SOUND_TYPE
                    |ecarx.carext.vehicle.module.IAudio.SoftButtonSoundType.TYPE_2:// 0x00008600|0x02;
                return "IAudio.SOFT_BUTTON_SOUND_TYPE|SoftButtonSoundType.TYPE_2";
            case ecarx.carext.vehicle.module.IAudio.SOFT_BUTTON_SOUND_TYPE
                    |ecarx.carext.vehicle.module.IAudio.SoftButtonSoundType.TYPE_3:// 0x00008600|0x03;
                return "IAudio.SOFT_BUTTON_SOUND_TYPE|SoftButtonSoundType.TYPE_3";
            case ecarx.carext.vehicle.module.IAudio.SOFT_BUTTON_SOUND_TYPE
                    |ecarx.carext.vehicle.module.IAudio.SoftButtonSoundType.OFF:// 0x00008600|0xff;
                return "IAudio.SOFT_BUTTON_SOUND_TYPE|SoftButtonSoundType.OFF";
            case ecarx.carext.vehicle.module.IAudio.AUDIO_FREQUENCY_LOW:// 0x0000fb00;
                return "IAudio.AUDIO_FREQUENCY_LOW";
            case ecarx.carext.vehicle.module.IAudio.AUDIO_FREQUENCY_MID:// 0x0000fc00;
                return "IAudio.AUDIO_FREQUENCY_MID";
            case ecarx.carext.vehicle.module.IAudio.AUDIO_FREQUENCY_HIGH:// 0x0000fd00;
                return "IAudio.AUDIO_FREQUENCY_HIGH";
            case ecarx.carext.vehicle.module.IAudio.AUDIO_SOUND_OPTIMIZE_FADER:// 0x0001a400;
                return "IAudio.AUDIO_SOUND_OPTIMIZE_FADER";
            case ecarx.carext.vehicle.module.IAudio.AUDIO_SOUND_OPTIMIZE_BALANCE:// 0x0001a500;
                return "IAudio.AUDIO_SOUND_OPTIMIZE_BALANCE";
            case ecarx.carext.vehicle.module.IAudio.NAVIGATION_MUTE:// 0x00022b00;
                return "IAudio.NAVIGATION_MUTE";
            case ecarx.carext.vehicle.module.IAudio.MEDIA_MUTE:// 0x00022c00;
                return "IAudio.MEDIA_MUTE";
            case ecarx.carext.vehicle.module.IAudio.CURRENT_ACTIVE_USAGE:// 0x0002ae00;
                return "IAudio.CURRENT_ACTIVE_USAGE";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_3D_SURROUND_SOUND_EFFECT_LEVEL:// 0x0001e900;
                return "IAudio.AUDIO_3D_SURROUND_SOUND_EFFECT_LEVEL";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE:// 0x0001eb00;
                return "IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE
                    |ecarx.carext.vehicle.module.v446k.IAudio.HeadPillowModeType.SHARE:// 0x0001eb00|0x01;
                return "IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE|HeadPillowModeType.SHARE";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE
                    |ecarx.carext.vehicle.module.v446k.IAudio.HeadPillowModeType.DRIVE:// 0x0001eb00|0x02;
                return "IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE|HeadPillowModeType.DRIVE";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE
                    |ecarx.carext.vehicle.module.v446k.IAudio.HeadPillowModeType.PRIVATE:// 0x0001eb00|0x03;
                return "IAudio.AUDIO_HEAD_PILLOW_AUTO_MODE|HeadPillowModeType.PRIVATE";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_HEAD_PILLOW:// 0x0001ec00;
                return "IAudio.AUDIO_HEAD_PILLOW";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_NAVI_MIX_MODE:// 0x0001ed00;
                return "IAudio.AUDIO_NAVI_MIX_MODE";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_NAVI_MIX_MODE
                    |ecarx.carext.vehicle.module.v446k.IAudio.NaviVoiceMixMode.DUCKED:// 0x0001ed00|0x01;
                return "IAudio.AUDIO_NAVI_MIX_MODE|NaviVoiceMixMode.DUCKED";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_NAVI_MIX_MODE
                    |ecarx.carext.vehicle.module.v446k.IAudio.NaviVoiceMixMode.REMAINS:// 0x0001ed00|0x02;
                return "IAudio.AUDIO_NAVI_MIX_MODE|NaviVoiceMixMode.REMAINS";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_AUDIBLE_LOCKING_FEEDBACK:// 0x0001ef00;
                return "IAudio.AUDIO_AUDIBLE_LOCKING_FEEDBACK";
            case ecarx.carext.vehicle.module.v446k.IAudio.AUDIO_TIMBRE_CUSTOM:// 0x00022000;
                return "IAudio.AUDIO_TIMBRE_CUSTOM";
            case ecarx.carext.vehicle.module.v446k.IAudio.PLAY_PDC_WARNING:// 0x00026200;
                return "IAudio.PLAY_PDC_WARNING";
            case ecarx.carext.vehicle.module.v446k.IAudio.KTV_MODE:// 0x00032000;
                return "IAudio.KTV_MODE";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT:// 0x00026500;
                return "IHardKey.DMSM_HARD_KEY_LEFT";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.NORMAL:// 0x00026500|0x01;
                return "IHardKey.DMSM_HARD_KEY_LEFT|HardKeyState.NORMAL";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.PRESS:// 0x00026500|0x02;
                return "IHardKey.DMSM_HARD_KEY_LEFT|HardKeyState.PRESS";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.RELEASE:// 0x00026500|0x03;
                return "IHardKey.DMSM_HARD_KEY_LEFT|HardKeyState.RELEASE";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.CLICK:// 0x00026500|0x04;
                return "IHardKey.DMSM_HARD_KEY_LEFT|HardKeyState.CLICK";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_LEFT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.LONG_CLICK:// 0x00026500|0x05;
                return "IHardKey.DMSM_HARD_KEY_LEFT|HardKeyState.LONG_CLICK";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT:// 0x00026600;
                return "IHardKey.DMSM_HARD_KEY_RIGHT";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.NORMAL:// 0x00026600|0x01;
                return "IHardKey.DMSM_HARD_KEY_RIGHT|HardKeyState.NORMAL";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.PRESS:// 0x00026600|0x02;
                return "IHardKey.DMSM_HARD_KEY_RIGHT|HardKeyState.PRESS";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.RELEASE:// 0x00026600|0x03;
                return "IHardKey.DMSM_HARD_KEY_RIGHT|HardKeyState.RELEASE";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.CLICK:// 0x00026600|0x04;
                return "IHardKey.DMSM_HARD_KEY_RIGHT|HardKeyState.CLICK";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_RIGHT
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.LONG_CLICK:// 0x00026600|0x05;
                return "IHardKey.DMSM_HARD_KEY_RIGHT|HardKeyState.LONG_CLICK";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM:// 0x00026700;
                return "IHardKey.DMSM_HARD_KEY_CONFIRM";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.NORMAL:// 0x00026700|0x01;
                return "IHardKey.DMSM_HARD_KEY_CONFIRM|HardKeyState.NORMAL";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.PRESS:// 0x00026700|0x02;
                return "IHardKey.DMSM_HARD_KEY_CONFIRM|HardKeyState.PRESS";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.RELEASE:// 0x00026700|0x03;
                return "IHardKey.DMSM_HARD_KEY_CONFIRM|HardKeyState.RELEASE";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.CLICK:// 0x00026700|0x04;
                return "IHardKey.DMSM_HARD_KEY_CONFIRM|HardKeyState.CLICK";
            case ecarx.carext.vehicle.module.v446k.IHardKey.DMSM_HARD_KEY_CONFIRM
                    |ecarx.carext.vehicle.module.v446k.IHardKey.HardKeyState.LONG_CLICK:// 0x00026700|0x05;
                return "IHardKey.DMSM_HARD_KEY_CONFIRM|HardKeyState.LONG_CLICK";
            case ecarx.carext.vehicle.module.IProfile.PROFILE_MEM_SAVE:// 0x00012b00;
                return "IProfile.PROFILE_MEM_SAVE";
            case ecarx.carext.vehicle.module.IProfile.PROFILE_SWITCH_PREFERENCE:// 0x00012e00;
                return "IProfile.PROFILE_SWITCH_PREFERENCE";
            case ecarx.carext.vehicle.module.IProfile.PROFILE_CHANGED_POP:// 0x00015d00;
                return "IProfile.PROFILE_CHANGED_POP";
            case ecarx.carext.vehicle.module.Tcam.TCAM_RESET:// 0x0000f500;
                return "Tcam.TCAM_RESET";
            case ecarx.carext.vehicle.module.Tcam.HARDWARE_VERSION:// 0x00010000;
                return "Tcam.HARDWARE_VERSION";
            case ecarx.carext.vehicle.module.Tcam.SOFTWARE_VERSION:// 0x00010100;
                return "Tcam.SOFTWARE_VERSION";
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_SAVE_STATUS:// 0x0001b100;
                return "Tcam.BOOK_CHARGING_SAVE_STATUS";
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE:// 0x0001d000;
                return "Tcam.BOOK_CHARGING_MODE";
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.OFF:// 0x0001d000|0x01;
                return "Tcam.BOOK_CHARGING_MODE|BookChargingMode.OFF";
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.SINGLE:// 0x0001d000|0x02;
                return "Tcam.BOOK_CHARGING_MODE|BookChargingMode.SINGLE";
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.CYCLE:// 0x0001d000|0x03;
                return "Tcam.BOOK_CHARGING_MODE|BookChargingMode.CYCLE";
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_TIME:// 0x0001d100;
                return "Tcam.BOOK_CHARGING_TIME";
            case ecarx.carext.vehicle.module.v446k.Tcam.TCAM_RESET_STATE:// 0x0001f600;
                return "Tcam.TCAM_RESET_STATE";
            case ecarx.carext.vehicle.module.v446k.Tcam.TCAM_RESET_STATE
                    |ecarx.carext.vehicle.module.v446k.Tcam.TcamResetState.FAILED:// 0x0001f600|0x01;
                return "Tcam.TCAM_RESET_STATE|TcamResetState.FAILED";
            case ecarx.carext.vehicle.module.v446k.Tcam.TCAM_RESET_STATE
                    |ecarx.carext.vehicle.module.v446k.Tcam.TcamResetState.SUCCESS:// 0x0001f600|0x02;
                return "Tcam.TCAM_RESET_STATE|TcamResetState.SUCCESS";
            case ecarx.carext.vehicle.module.v446k.Tcam.TCAM_RESET_STATE
                    |ecarx.carext.vehicle.module.v446k.Tcam.TcamResetState.UNKNOW:// 0x0001f600|0xff;
                return "Tcam.TCAM_RESET_STATE|TcamResetState.UNKNOW";
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE_LAST_TIME:// 0x00023700;
                return "Tcam.BOOK_CHARGING_MODE_LAST_TIME";
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE_LAST_TIME
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.OFF:// 0x00023700|0x01;
                return "Tcam.BOOK_CHARGING_MODE_LAST_TIME|BookChargingMode.OFF";
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE_LAST_TIME
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.SINGLE:// 0x00023700|0x02;
                return "Tcam.BOOK_CHARGING_MODE_LAST_TIME|BookChargingMode.SINGLE";
            case ecarx.carext.vehicle.module.v446k.Tcam.BOOK_CHARGING_MODE_LAST_TIME
                    |ecarx.carext.vehicle.module.v446k.Tcam.BookChargingMode.CYCLE:// 0x00023700|0x03;
                return "Tcam.BOOK_CHARGING_MODE_LAST_TIME|BookChargingMode.CYCLE";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.CREATE_DIGITAL_KEY:// 0x00025700;
                return "IDigitalKey.CREATE_DIGITAL_KEY";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.CREATE_DIGITAL_KEY
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyType.DIGITAL_KEY_TYPE_OWNER:// 0x00025700|0x01;
                return "IDigitalKey.CREATE_DIGITAL_KEY|DigitalKeyType.DIGITAL_KEY_TYPE_OWNER";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.CREATE_DIGITAL_KEY
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyType.DIGITAL_KEY_TYPE_SHARED:// 0x00025700|0x02;
                return "IDigitalKey.CREATE_DIGITAL_KEY|DigitalKeyType.DIGITAL_KEY_TYPE_SHARED";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DELETE_DIGITAL_KEY:// 0x00025800;
                return "IDigitalKey.DELETE_DIGITAL_KEY";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION:// 0x00025900;
                return "IDigitalKey.DIGITAL_KEY_TERMINATION";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.SUCCESS:// 0x00025900|0x01;
                return "IDigitalKey.DIGITAL_KEY_TERMINATION|DigitalKeyTerminationStatus.SUCCESS";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.REJECT_USGMOD:// 0x00025900|0x02;
                return "IDigitalKey.DIGITAL_KEY_TERMINATION|DigitalKeyTerminationStatus.REJECT_USGMOD";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.REJECT_CARMOD:// 0x00025900|0x03;
                return "IDigitalKey.DIGITAL_KEY_TERMINATION|DigitalKeyTerminationStatus.REJECT_CARMOD";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.REJECT_KEY_NOT_EXIST:// 0x00025900|0x04;
                return "IDigitalKey.DIGITAL_KEY_TERMINATION|DigitalKeyTerminationStatus.REJECT_KEY_NOT_EXIST";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.REJECT_NO_KEY:// 0x00025900|0x05;
                return "IDigitalKey.DIGITAL_KEY_TERMINATION|DigitalKeyTerminationStatus.REJECT_NO_KEY";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.REJECT_BNCM:// 0x00025900|0x06;
                return "IDigitalKey.DIGITAL_KEY_TERMINATION|DigitalKeyTerminationStatus.REJECT_BNCM";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TERMINATION
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyTerminationStatus.IDLE:// 0x00025900|0xff;
                return "IDigitalKey.DIGITAL_KEY_TERMINATION|DigitalKeyTerminationStatus.IDLE";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DELETE_ALL_DIGITAL_KEYS:// 0x00025a00;
                return "IDigitalKey.DELETE_ALL_DIGITAL_KEYS";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.GET_DIGITAL_KEYS:// 0x00025b00;
                return "IDigitalKey.GET_DIGITAL_KEYS";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS:// 0x00025c00;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.IDLE:// 0x00025c00|0x01;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS|DigitalKeyReadyStatus.IDLE";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.CREATING:// 0x00025c00|0x02;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS|DigitalKeyReadyStatus.CREATING";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.RESETTING:// 0x00025c00|0x03;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS|DigitalKeyReadyStatus.RESETTING";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.DELETING:// 0x00025c00|0x04;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS|DigitalKeyReadyStatus.DELETING";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.SEARCHING_CARD:// 0x00025c00|0x05;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS|DigitalKeyReadyStatus.SEARCHING_CARD";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.SEARCHING_PHONE:// 0x00025c00|0x06;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS|DigitalKeyReadyStatus.SEARCHING_PHONE";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.PREPARING_DATA:// 0x00025c00|0x07;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS|DigitalKeyReadyStatus.PREPARING_DATA";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.CREATE_SUCCESS:// 0x00025c00|0x08;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS|DigitalKeyReadyStatus.CREATE_SUCCESS";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.SMS_SENT:// 0x00025c00|0x09;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS|DigitalKeyReadyStatus.SMS_SENT";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_READY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyReadyStatus.PAIRING_CODE_VERIFIED:// 0x00025c00|0x0a;
                return "IDigitalKey.DIGITAL_KEY_READY_STATUS|DigitalKeyReadyStatus.PAIRING_CODE_VERIFIED";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS:// 0x00025d00;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.SUCCESS:// 0x00025d00|0x01;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.SUCCESS";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.TIMEOUT:// 0x00025d00|0x02;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.TIMEOUT";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.BCNM_LACK_VIN:// 0x00025d00|0x08;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.BCNM_LACK_VIN";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.VERIFIER_UPDATED:// 0x00025d00|0x09;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.VERIFIER_UPDATED";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.UTC_TI_LOST:// 0x00025d00|0x0a;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.UTC_TI_LOST";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.CERTIFICATE_INVALID:// 0x00025d00|0x0b;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.CERTIFICATE_INVALID";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.ERROR_MSG:// 0x00025d00|0x0c;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.ERROR_MSG";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.SECURE_CREATFAL:// 0x00025d00|0x0d;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.SECURE_CREATFAL";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.WRITE_DATAFAL:// 0x00025d00|0x0e;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.WRITE_DATAFAL";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.GET_DATAFAL:// 0x00025d00|0x0f;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.GET_DATAFAL";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.PROTOCOL_MISMATCH:// 0x00025d00|0x10;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.PROTOCOL_MISMATCH";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.AUTH_FAL:// 0x00025d00|0x11;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.AUTH_FAL";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.PHONE_NOT_FOUND:// 0x00025d00|0x13;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.PHONE_NOT_FOUND";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_PAIRING_STATUS
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.PairingStatus.OWNER_KEY_NOT_FOUND:// 0x00025d00|0x14;
                return "IDigitalKey.DIGITAL_KEY_PAIRING_STATUS|PairingStatus.OWNER_KEY_NOT_FOUND";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_TRACKING_RESULT:// 0x00025e00;
                return "IDigitalKey.DIGITAL_KEY_TRACKING_RESULT";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR:// 0x00025f00;
                return "IDigitalKey.DIGITAL_KEY_UNPAIR";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.IDLE:// 0x00025f00|0x01;
                return "IDigitalKey.DIGITAL_KEY_UNPAIR|DigitalKeyUnpairStatus.IDLE";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.SUCCESS:// 0x00025f00|0x02;
                return "IDigitalKey.DIGITAL_KEY_UNPAIR|DigitalKeyUnpairStatus.SUCCESS";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.REJECT_USGMOD:// 0x00025f00|0x03;
                return "IDigitalKey.DIGITAL_KEY_UNPAIR|DigitalKeyUnpairStatus.REJECT_USGMOD";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.REJECT_CARMOD:// 0x00025f00|0x04;
                return "IDigitalKey.DIGITAL_KEY_UNPAIR|DigitalKeyUnpairStatus.REJECT_CARMOD";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.REJECT_NO_MOBILE:// 0x00025f00|0x05;
                return "IDigitalKey.DIGITAL_KEY_UNPAIR|DigitalKeyUnpairStatus.REJECT_NO_MOBILE";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.REJECT_NO_KEY:// 0x00025f00|0x06;
                return "IDigitalKey.DIGITAL_KEY_UNPAIR|DigitalKeyUnpairStatus.REJECT_NO_KEY";
            case ecarx.carext.vehicle.module.v446k.IDigitalKey.DIGITAL_KEY_UNPAIR
                    |ecarx.carext.vehicle.module.v446k.IDigitalKey.DigitalKeyUnpairStatus.REJECT_BNCM:// 0x00025f00|0x07;
                return "IDigitalKey.DIGITAL_KEY_UNPAIR|DigitalKeyUnpairStatus.REJECT_BNCM";
            case ecarx.carext.vehicle.module.IBcm.POWER_ONOFF:// 0x00002f00;
                return "IBcm.POWER_ONOFF";
            case ecarx.carext.vehicle.module.IBcm.LIGHT_FRONT_FOG_LAMPS:// 0x00003000;
                return "IBcm.LIGHT_FRONT_FOG_LAMPS";
            case ecarx.carext.vehicle.module.IBcm.REAR_MIRROR_ADJUST:// 0x00003b00;
                return "IBcm.REAR_MIRROR_ADJUST";
            case ecarx.carext.vehicle.module.IBcm.MIRROR_AUTO_FOLDING:// 0x00005200;
                return "IBcm.MIRROR_AUTO_FOLDING";
            case ecarx.carext.vehicle.module.IBcm.LOCK_AUTO_CLOSE_WINDOW:// 0x00005a00;
                return "IBcm.LOCK_AUTO_CLOSE_WINDOW";
            case ecarx.carext.vehicle.module.IBcm.AUTO_CLOSE_WINDOW_RAINY:// 0x00005b00;
                return "IBcm.AUTO_CLOSE_WINDOW_RAINY";
            case ecarx.carext.vehicle.module.IBcm.WINDSCREEN_SERVICE_POSITION:// 0x00005e00;
                return "IBcm.WINDSCREEN_SERVICE_POSITION";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY:// 0x00006d00;
                return "IBcm.CUSTOM_KEY";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_AUTO_PARKING:// 0x00006d00|0x01;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_AUTO_PARKING";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_SOUND_SWITCH:// 0x00006d00|0x02;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_SOUND_SWITCH";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_DVR:// 0x00006d00|0x03;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_DVR";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_DRIVE_MODE_SWITCH:// 0x00006d00|0x04;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_DRIVE_MODE_SWITCH";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_THEME_SWITCH:// 0x00006d00|0x05;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_THEME_SWITCH";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_GAMEMOD:// 0x00006d00|0x06;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_GAMEMOD";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_VOICE:// 0x00006d00|0x07;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_VOICE";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_REAR_MIRROR:// 0x00006d00|0x08;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_REAR_MIRROR";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_360:// 0x00006d00|0x09;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_360";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_HUD:// 0x00006d00|0x0a;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_HUD";
            case ecarx.carext.vehicle.module.IBcm.CUSTOM_KEY
                    |ecarx.carext.vehicle.module.IBcm.CustomKeyType.KEY_TYPE_OFF:// 0x00006d00|0xff;
                return "IBcm.CUSTOM_KEY|CustomKeyType.KEY_TYPE_OFF";
            case ecarx.carext.vehicle.module.IBcm.CHILD_SAFETY_LOCK:// 0x00008a00;
                return "IBcm.CHILD_SAFETY_LOCK";
            case ecarx.carext.vehicle.module.IBcm.LIGHT_ATMOSPHERE_LAMPS:// 0x00009a00;
                return "IBcm.LIGHT_ATMOSPHERE_LAMPS";
            case ecarx.carext.vehicle.module.IBcm.PBC_EPB_SWITCH:// 0x00009f00;
                return "IBcm.PBC_EPB_SWITCH";
            case ecarx.carext.vehicle.module.IBcm.PAB_SWITCH:// 0x00023200;
                return "IBcm.PAB_SWITCH";
            case ecarx.carext.vehicle.module.IBcm.TRAILER_PRESENT:// 0x00028300;
                return "IBcm.TRAILER_PRESENT";
            case ecarx.carext.vehicle.module.IBcm.TRAILER_PRESENT
                    |ecarx.carext.vehicle.module.IBcm.TrailerState.STATE_NOT_PRESENT:// 0x00028300|0x01;
                return "IBcm.TRAILER_PRESENT|TrailerState.STATE_NOT_PRESENT";
            case ecarx.carext.vehicle.module.IBcm.TRAILER_PRESENT
                    |ecarx.carext.vehicle.module.IBcm.TrailerState.STATE_PRESENT:// 0x00028300|0x02;
                return "IBcm.TRAILER_PRESENT|TrailerState.STATE_PRESENT";
            case ecarx.carext.vehicle.module.IBcm.TRAILER_PRESENT
                    |ecarx.carext.vehicle.module.IBcm.TrailerState.STATE_ERROR:// 0x00028300|0x03;
                return "IBcm.TRAILER_PRESENT|TrailerState.STATE_ERROR";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_OFF_NOTICE:// 0x00017a00;
                return "IBcm.POWER_OFF_NOTICE";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE:// 0x00017b00;
                return "IBcm.POWER_ONOFF_NOTICE";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.POWER_ON:// 0x00017b00|0x01;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.POWER_ON";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.POWER_OFF:// 0x00017b00|0x02;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.POWER_OFF";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.CHARGING:// 0x00017b00|0x03;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.CHARGING";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.ENGINE_HOOD:// 0x00017b00|0x04;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.ENGINE_HOOD";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.LOW_OIL:// 0x00017b00|0x05;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.LOW_OIL";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.UN_GEAR:// 0x00017b00|0x06;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.UN_GEAR";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.LONG_TIME:// 0x00017b00|0x07;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.LONG_TIME";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.NOTFIND_VALID_KEY:// 0x00017b00|0x08;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.NOTFIND_VALID_KEY";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.LOW_PRESSUR:// 0x00017b00|0x09;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.LOW_PRESSUR";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.HIGH_PRESSURE:// 0x00017b00|0x0a;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.HIGH_PRESSURE";
            case ecarx.carext.vehicle.module.v446k.IBcm.POWER_ONOFF_NOTICE
                    |ecarx.carext.vehicle.module.v446k.IBcm.PowerOnOffNotice.MAX_TIMES:// 0x00017b00|0x0b;
                return "IBcm.POWER_ONOFF_NOTICE|PowerOnOffNotice.MAX_TIMES";
            case ecarx.carext.vehicle.module.v446k.IBcm.DIM_DISPLAY_MODE:// 0x0001db00;
                return "IBcm.DIM_DISPLAY_MODE";
            case ecarx.carext.vehicle.module.v446k.IBcm.DIM_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.DisplayMode.SIMPLE:// 0x0001db00|0x01;
                return "IBcm.DIM_DISPLAY_MODE|DisplayMode.SIMPLE";
            case ecarx.carext.vehicle.module.v446k.IBcm.DIM_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.DisplayMode.MULTILANE:// 0x0001db00|0x02;
                return "IBcm.DIM_DISPLAY_MODE|DisplayMode.MULTILANE";
            case ecarx.carext.vehicle.module.v446k.IBcm.DIM_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.DisplayMode.SMART:// 0x0001db00|0x03;
                return "IBcm.DIM_DISPLAY_MODE|DisplayMode.SMART";
            case ecarx.carext.vehicle.module.v446k.IBcm.DIM_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.DisplayMode.MAP:// 0x0001db00|0x04;
                return "IBcm.DIM_DISPLAY_MODE|DisplayMode.MAP";
            case ecarx.carext.vehicle.module.v446k.IBcm.AUTO_REAR_WIPING:// 0x0001e000;
                return "IBcm.AUTO_REAR_WIPING";
            case ecarx.carext.vehicle.module.v446k.IBcm.FIND_CAR_MODE:// 0x0001e200;
                return "IBcm.FIND_CAR_MODE";
            case ecarx.carext.vehicle.module.v446k.IBcm.FIND_CAR_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.FindMode.HONKING:// 0x0001e200|0x01;
                return "IBcm.FIND_CAR_MODE|FindMode.HONKING";
            case ecarx.carext.vehicle.module.v446k.IBcm.FIND_CAR_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.FindMode.FLASHING_LIGHTS:// 0x0001e200|0x02;
                return "IBcm.FIND_CAR_MODE|FindMode.FLASHING_LIGHTS";
            case ecarx.carext.vehicle.module.v446k.IBcm.FIND_CAR_MODE
                    |ecarx.carext.vehicle.module.v446k.IBcm.FindMode.HONKING_AND_FLASHING_LIGHTS:// 0x0001e200|0x03;
                return "IBcm.FIND_CAR_MODE|FindMode.HONKING_AND_FLASHING_LIGHTS";
            case ecarx.carext.vehicle.module.v446k.IBcm.PARKING_KEEP_POWER_TIME:// 0x0001e500;
                return "IBcm.PARKING_KEEP_POWER_TIME";
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET:// 0x0001f900;
                return "IBcm.VEHICLE_FACTORY_RESET";
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET_STATUS:// 0x0001fa00;
                return "IBcm.VEHICLE_FACTORY_RESET_STATUS";
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET_STATUS
                    |ecarx.carext.vehicle.module.v446k.IBcm.FactoryResetStatus.PROGRESS:// 0x0001fa00|0x01;
                return "IBcm.VEHICLE_FACTORY_RESET_STATUS|FactoryResetStatus.PROGRESS";
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET_STATUS
                    |ecarx.carext.vehicle.module.v446k.IBcm.FactoryResetStatus.SUCCEED:// 0x0001fa00|0x02;
                return "IBcm.VEHICLE_FACTORY_RESET_STATUS|FactoryResetStatus.SUCCEED";
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET_STATUS
                    |ecarx.carext.vehicle.module.v446k.IBcm.FactoryResetStatus.FAILED:// 0x0001fa00|0x03;
                return "IBcm.VEHICLE_FACTORY_RESET_STATUS|FactoryResetStatus.FAILED";
            case ecarx.carext.vehicle.module.v446k.IBcm.VEHICLE_FACTORY_RESET_STATUS
                    |ecarx.carext.vehicle.module.v446k.IBcm.FactoryResetStatus.UNKNOW:// 0x0001fa00|0xff;
                return "IBcm.VEHICLE_FACTORY_RESET_STATUS|FactoryResetStatus.UNKNOW";
            case ecarx.carext.vehicle.module.v446k.IBcm.FUEL_DOOR_OPEN_CONDITION:// 0x00026100;
                return "IBcm.FUEL_DOOR_OPEN_CONDITION";
            case ecarx.carext.vehicle.module.v446k.IBcm.FUEL_DOOR_OPEN_CONDITION
                    |ecarx.carext.vehicle.module.v446k.IBcm.FuelDoorOpenCondition.READY:// 0x00026100|0x01;
                return "IBcm.FUEL_DOOR_OPEN_CONDITION|FuelDoorOpenCondition.READY";
            case ecarx.carext.vehicle.module.v446k.IBcm.FUEL_DOOR_OPEN_CONDITION
                    |ecarx.carext.vehicle.module.v446k.IBcm.FuelDoorOpenCondition.NOT_READY:// 0x00026100|0x02;
                return "IBcm.FUEL_DOOR_OPEN_CONDITION|FuelDoorOpenCondition.NOT_READY";
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE:// 0x00010d00;
                return "IBiometric.OMS_EVENT_GESTURE";
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.UNKNOW:// 0x00010d00|0x01;
                return "IBiometric.OMS_EVENT_GESTURE|Gesture.UNKNOW";
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.MUTE:// 0x00010d00|0x02;
                return "IBiometric.OMS_EVENT_GESTURE|Gesture.MUTE";
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.OK:// 0x00010d00|0x03;
                return "IBiometric.OMS_EVENT_GESTURE|Gesture.OK";
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.CLENCH_FIST:// 0x00010d00|0x04;
                return "IBiometric.OMS_EVENT_GESTURE|Gesture.CLENCH_FIST";
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.V:// 0x00010d00|0x05;
                return "IBiometric.OMS_EVENT_GESTURE|Gesture.V";
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.THUMB_UP:// 0x00010d00|0x06;
                return "IBiometric.OMS_EVENT_GESTURE|Gesture.THUMB_UP";
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.FINGER_LEFT:// 0x00010d00|0x07;
                return "IBiometric.OMS_EVENT_GESTURE|Gesture.FINGER_LEFT";
            case ecarx.carext.vehicle.module.IBiometric.OMS_EVENT_GESTURE
                    |ecarx.carext.vehicle.module.IBiometric.Gesture.FINGER_RIGHT:// 0x00010d00|0x08;
                return "IBiometric.OMS_EVENT_GESTURE|Gesture.FINGER_RIGHT";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FATIGUE_STATUS:// 0x00016f00;
                return "IBiometric.DMS_DRIVER_FATIGUE_STATUS";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FATIGUE_STATUS
                    |ecarx.carext.vehicle.module.IBiometric.DriverFatigueStatus.UNKNOW:// 0x00016f00|0x01;
                return "IBiometric.DMS_DRIVER_FATIGUE_STATUS|DriverFatigueStatus.UNKNOW";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FATIGUE_STATUS
                    |ecarx.carext.vehicle.module.IBiometric.DriverFatigueStatus.NORMAL:// 0x00016f00|0x02;
                return "IBiometric.DMS_DRIVER_FATIGUE_STATUS|DriverFatigueStatus.NORMAL";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FATIGUE_STATUS
                    |ecarx.carext.vehicle.module.IBiometric.DriverFatigueStatus.DISTRACTED:// 0x00016f00|0x03;
                return "IBiometric.DMS_DRIVER_FATIGUE_STATUS|DriverFatigueStatus.DISTRACTED";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FATIGUE_STATUS
                    |ecarx.carext.vehicle.module.IBiometric.DriverFatigueStatus.TIRED:// 0x00016f00|0x04;
                return "IBiometric.DMS_DRIVER_FATIGUE_STATUS|DriverFatigueStatus.TIRED";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION:// 0x00017000;
                return "IBiometric.DMS_DRIVER_FACIAL_EXPRESSION";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.UNKNOW:// 0x00017000|0x01;
                return "IBiometric.DMS_DRIVER_FACIAL_EXPRESSION|DriverFacialExpression.UNKNOW";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.HAPPY:// 0x00017000|0x02;
                return "IBiometric.DMS_DRIVER_FACIAL_EXPRESSION|DriverFacialExpression.HAPPY";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.ANGRY:// 0x00017000|0x03;
                return "IBiometric.DMS_DRIVER_FACIAL_EXPRESSION|DriverFacialExpression.ANGRY";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.SURPRISE:// 0x00017000|0x04;
                return "IBiometric.DMS_DRIVER_FACIAL_EXPRESSION|DriverFacialExpression.SURPRISE";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.NEUTRAL:// 0x00017000|0x05;
                return "IBiometric.DMS_DRIVER_FACIAL_EXPRESSION|DriverFacialExpression.NEUTRAL";
            case ecarx.carext.vehicle.module.IBiometric.DMS_DRIVER_FACIAL_EXPRESSION
                    |ecarx.carext.vehicle.module.IBiometric.DriverFacialExpression.SAD:// 0x00017000|0x06;
                return "IBiometric.DMS_DRIVER_FACIAL_EXPRESSION|DriverFacialExpression.SAD";
            case ecarx.carext.vehicle.module.GNSS.GNSS_UPLOAD_LOCATION_SWITCH:// 0x00010200;
                return "GNSS.GNSS_UPLOAD_LOCATION_SWITCH";
            case ecarx.carext.vehicle.module.v446k.GNSS.GNSS_LOCATOR_AUTH_CYCLE:// 0x0001ff00;
                return "GNSS.GNSS_LOCATOR_AUTH_CYCLE";
            case ecarx.carext.vehicle.module.v446k.GNSS.GNSS_LOCATOR_UPDATE_AUTH_TIME:// 0x00020000;
                return "GNSS.GNSS_LOCATOR_UPDATE_AUTH_TIME";
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT:// 0x00009d00;
                return "IDriveMode.DRIVE_MODE_SELECT";
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_ECO:// 0x00009d00|0x01;
                return "IDriveMode.DRIVE_MODE_SELECT|DriveModeSelect.DRIVE_MODE_SELECTION_ECO";
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_NORMAL:// 0x00009d00|0x02;
                return "IDriveMode.DRIVE_MODE_SELECT|DriveModeSelect.DRIVE_MODE_SELECTION_NORMAL";
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_DYNAMIC:// 0x00009d00|0x03;
                return "IDriveMode.DRIVE_MODE_SELECT|DriveModeSelect.DRIVE_MODE_SELECTION_DYNAMIC";
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_CUSTOM:// 0x00009d00|0x04;
                return "IDriveMode.DRIVE_MODE_SELECT|DriveModeSelect.DRIVE_MODE_SELECTION_CUSTOM";
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_PURE:// 0x00009d00|0x06;
                return "IDriveMode.DRIVE_MODE_SELECT|DriveModeSelect.DRIVE_MODE_SELECTION_PURE";
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_HYBRID:// 0x00009d00|0x07;
                return "IDriveMode.DRIVE_MODE_SELECT|DriveModeSelect.DRIVE_MODE_SELECTION_HYBRID";
            case ecarx.carext.vehicle.module.IDriveMode.DRIVE_MODE_SELECT
                    |ecarx.carext.vehicle.module.IDriveMode.DriveModeSelect.DRIVE_MODE_SELECTION_OFFROAD:// 0x00009d00|0x08;
                return "IDriveMode.DRIVE_MODE_SELECT|DriveModeSelect.DRIVE_MODE_SELECTION_OFFROAD";
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING:// 0x00008700;
                return "IDayMode.DAYMODE_SETTING";
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.DAY:// 0x00008700|0x01;
                return "IDayMode.DAYMODE_SETTING|DayModeSettings.DAY";
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.NIGHT:// 0x00008700|0x02;
                return "IDayMode.DAYMODE_SETTING|DayModeSettings.NIGHT";
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.AUTO:// 0x00008700|0x03;
                return "IDayMode.DAYMODE_SETTING|DayModeSettings.AUTO";
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.CUSTOM:// 0x00008700|0x04;
                return "IDayMode.DAYMODE_SETTING|DayModeSettings.CUSTOM";
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.SUNRISE_AND_SUNSET:// 0x00008700|0x05;
                return "IDayMode.DAYMODE_SETTING|DayModeSettings.SUNRISE_AND_SUNSET";
            case ecarx.carext.vehicle.module.IDayMode.DAYMODE_SETTING
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.OFF:// 0x00008700|0xff;
                return "IDayMode.DAYMODE_SETTING|DayModeSettings.OFF";
            case ecarx.carext.vehicle.module.IDayMode.FUNC_BRIGHTNESS:// 0x0000ae00;
                return "IDayMode.FUNC_BRIGHTNESS";
            case ecarx.carext.vehicle.module.IDayMode.FUNC_BRIGHTNESS_MAX:// 0x0000af00;
                return "IDayMode.FUNC_BRIGHTNESS_MAX";
            case ecarx.carext.vehicle.module.IDayMode.FUNC_BRIGHTNESS_MIN:// 0x0000b000;
                return "IDayMode.FUNC_BRIGHTNESS_MIN";
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE:// 0x00010a00;
                return "IDayMode.BRIGHTNESS_DAYMODE";
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.DAY:// 0x00010a00|0x01;
                return "IDayMode.BRIGHTNESS_DAYMODE|DayModeSettings.DAY";
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.NIGHT:// 0x00010a00|0x02;
                return "IDayMode.BRIGHTNESS_DAYMODE|DayModeSettings.NIGHT";
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.AUTO:// 0x00010a00|0x03;
                return "IDayMode.BRIGHTNESS_DAYMODE|DayModeSettings.AUTO";
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.CUSTOM:// 0x00010a00|0x04;
                return "IDayMode.BRIGHTNESS_DAYMODE|DayModeSettings.CUSTOM";
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.SUNRISE_AND_SUNSET:// 0x00010a00|0x05;
                return "IDayMode.BRIGHTNESS_DAYMODE|DayModeSettings.SUNRISE_AND_SUNSET";
            case ecarx.carext.vehicle.module.IDayMode.BRIGHTNESS_DAYMODE
                    |ecarx.carext.vehicle.module.IDayMode.DayModeSettings.OFF:// 0x00010a00|0xff;
                return "IDayMode.BRIGHTNESS_DAYMODE|DayModeSettings.OFF";
            case ecarx.carext.vehicle.module.IDayMode.SUN_TIME:// 0x00018000;
                return "IDayMode.SUN_TIME";
            case ecarx.carext.vehicle.module.v446k.IDayMode.BRIGHTNESS_AUTO_ADJUST:// 0x0001e600;
                return "IDayMode.BRIGHTNESS_AUTO_ADJUST";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_BRIGHTNESS_ACTION:// 0x00021700;
                return "DIM.DIM_BRIGHTNESS_ACTION";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_BRIGHTNESS_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimBrightnessAction.RESTORE:// 0x00021700|0x01;
                return "DIM.DIM_BRIGHTNESS_ACTION|DimBrightnessAction.RESTORE";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_BRIGHTNESS_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimBrightnessAction.SCREEN_OFF:// 0x00021700|0x02;
                return "DIM.DIM_BRIGHTNESS_ACTION|DimBrightnessAction.SCREEN_OFF";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_BRIGHTNESS_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimBrightnessAction.START_RESET:// 0x00021700|0x03;
                return "DIM.DIM_BRIGHTNESS_ACTION|DimBrightnessAction.START_RESET";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_BRIGHTNESS_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimBrightnessAction.NONE:// 0x00021700|0xff;
                return "DIM.DIM_BRIGHTNESS_ACTION|DimBrightnessAction.NONE";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_STATE:// 0x00021800;
                return "DIM.DIM_PROJECTION_STATE";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_STATE
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionState.NAVI:// 0x00021800|0x01;
                return "DIM.DIM_PROJECTION_STATE|DimProjectionState.NAVI";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_STATE
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionState.SCENE:// 0x00021800|0x02;
                return "DIM.DIM_PROJECTION_STATE|DimProjectionState.SCENE";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_STATE
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionState.VIDEO:// 0x00021800|0x04;
                return "DIM.DIM_PROJECTION_STATE|DimProjectionState.VIDEO";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_STATE
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionState.NONE:// 0x00021800|0xff;
                return "DIM.DIM_PROJECTION_STATE|DimProjectionState.NONE";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION:// 0x00021900;
                return "DIM.DIM_PROJECTION_ACTION";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.NAVI_START:// 0x00021900|0x01;
                return "DIM.DIM_PROJECTION_ACTION|DimProjectionAction.NAVI_START";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.NAVI_END:// 0x00021900|0x02;
                return "DIM.DIM_PROJECTION_ACTION|DimProjectionAction.NAVI_END";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.SCENT_START:// 0x00021900|0x03;
                return "DIM.DIM_PROJECTION_ACTION|DimProjectionAction.SCENT_START";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.SCENE_END:// 0x00021900|0x04;
                return "DIM.DIM_PROJECTION_ACTION|DimProjectionAction.SCENE_END";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.VIDEO_START:// 0x00021900|0x05;
                return "DIM.DIM_PROJECTION_ACTION|DimProjectionAction.VIDEO_START";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.VIDEO_END:// 0x00021900|0x06;
                return "DIM.DIM_PROJECTION_ACTION|DimProjectionAction.VIDEO_END";
            case ecarx.carext.vehicle.module.v446k.DIM.DIM_PROJECTION_ACTION
                    |ecarx.carext.vehicle.module.v446k.DIM.DimProjectionAction.NONE:// 0x00021900|0xff;
                return "DIM.DIM_PROJECTION_ACTION|DimProjectionAction.NONE";
            case ecarx.carext.vehicle.module.v446k.DIM.FAULT_ALARM_ZONE_A:// 0x00022600;
                return "DIM.FAULT_ALARM_ZONE_A";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT:// 0x00009500;
                return "ILamp.LAMP_HOME_SAFE_LIGHT";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_10S:// 0x00009500|0x01;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_10S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_20S:// 0x00009500|0x02;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_20S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_30S:// 0x00009500|0x03;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_30S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_40S:// 0x00009500|0x04;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_40S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_50S:// 0x00009500|0x05;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_50S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_60S:// 0x00009500|0x06;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_60S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_70S:// 0x00009500|0x07;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_70S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_80S:// 0x00009500|0x08;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_80S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_90S:// 0x00009500|0x09;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_90S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_100S:// 0x00009500|0x10;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_100S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_110S:// 0x00009500|0x11;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_110S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_120S:// 0x00009500|0x12;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_120S";
            case ecarx.carext.vehicle.module.ILamp.LAMP_HOME_SAFE_LIGHT
                    |ecarx.carext.vehicle.module.ILamp.HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_OFF:// 0x00009500|0xff;
                return "ILamp.LAMP_HOME_SAFE_LIGHT|HomeSafeLightValue.HOME_SAFE_LIGHT_VALUE_OFF";
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME:// 0x00009600;
                return "ILamp.APPROACHING_LIGHTING_TIME";
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME
                    |ecarx.carext.vehicle.module.ILamp.LightingTimeLevel.LIGHTING_TIME_LEVEL1:// 0x00009600|0x01;
                return "ILamp.APPROACHING_LIGHTING_TIME|LightingTimeLevel.LIGHTING_TIME_LEVEL1";
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME
                    |ecarx.carext.vehicle.module.ILamp.LightingTimeLevel.LIGHTING_TIME_LEVEL2:// 0x00009600|0x02;
                return "ILamp.APPROACHING_LIGHTING_TIME|LightingTimeLevel.LIGHTING_TIME_LEVEL2";
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME
                    |ecarx.carext.vehicle.module.ILamp.LightingTimeLevel.LIGHTING_TIME_LEVEL3:// 0x00009600|0x03;
                return "ILamp.APPROACHING_LIGHTING_TIME|LightingTimeLevel.LIGHTING_TIME_LEVEL3";
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME
                    |ecarx.carext.vehicle.module.ILamp.LightingTimeLevel.LIGHTING_TIME_LEVEL4:// 0x00009600|0x04;
                return "ILamp.APPROACHING_LIGHTING_TIME|LightingTimeLevel.LIGHTING_TIME_LEVEL4";
            case ecarx.carext.vehicle.module.ILamp.APPROACHING_LIGHTING_TIME
                    |ecarx.carext.vehicle.module.ILamp.LightingTimeLevel.LIGHTING_TIME_OFF:// 0x00009600|0xff;
                return "ILamp.APPROACHING_LIGHTING_TIME|LightingTimeLevel.LIGHTING_TIME_OFF";
            case ecarx.carext.vehicle.module.ILamp.LAMP_AUTOMATIC_COURTESY_LIGHT:// 0x00009700;
                return "ILamp.LAMP_AUTOMATIC_COURTESY_LIGHT";
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL:// 0x00009b00;
                return "ILamp.LAMP_EXTERIOR_LIGHT_CONTROL";
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL
                    |ecarx.carext.vehicle.module.ILamp.ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_POS_:// 0x00009b00|0x01;
                return "ILamp.LAMP_EXTERIOR_LIGHT_CONTROL|ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_POS_";
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL
                    |ecarx.carext.vehicle.module.ILamp.ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_LOWB:// 0x00009b00|0x02;
                return "ILamp.LAMP_EXTERIOR_LIGHT_CONTROL|ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_LOWB";
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL
                    |ecarx.carext.vehicle.module.ILamp.ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_AUTO:// 0x00009b00|0x03;
                return "ILamp.LAMP_EXTERIOR_LIGHT_CONTROL|ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_AUTO";
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL
                    |ecarx.carext.vehicle.module.ILamp.ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_AHBC:// 0x00009b00|0x04;
                return "ILamp.LAMP_EXTERIOR_LIGHT_CONTROL|ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_AHBC";
            case ecarx.carext.vehicle.module.ILamp.LAMP_EXTERIOR_LIGHT_CONTROL
                    |ecarx.carext.vehicle.module.ILamp.ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_OFF:// 0x00009b00|0xff;
                return "ILamp.LAMP_EXTERIOR_LIGHT_CONTROL|ExteriorLightControlValue.LAMP_EXTERIOR_LIGHT_CONTROL_OFF";
            case ecarx.carext.vehicle.module.ILamp.LAMP_FRONT_POSITION:// 0x00018a00;
                return "ILamp.LAMP_FRONT_POSITION";
            case ecarx.carext.vehicle.module.ILamp.LAMP_REAR_POSITION:// 0x00018b00;
                return "ILamp.LAMP_REAR_POSITION";
            case ecarx.carext.vehicle.module.ILamp.LAMP_DAYTIME_LIGHT:// 0x00018c00;
                return "ILamp.LAMP_DAYTIME_LIGHT";
            case ecarx.carext.vehicle.module.v446k.ILamp.ADAPTIVE_FRONT_LIGHT:// 0x0001e700;
                return "ILamp.ADAPTIVE_FRONT_LIGHT";
            case ecarx.carext.vehicle.module.v446k.ILamp.BENDING_LIGHT:// 0x0001ea00;
                return "ILamp.BENDING_LIGHT";
            case ecarx.carext.vehicle.module.v446k.ILamp.READING_LIGHTS_SWITCH_VOICE:// 0x00033500;
                return "ILamp.READING_LIGHTS_SWITCH_VOICE";
            case ecarx.carext.vehicle.module.v446k.IDisplay.SCREEN_ON:// 0x0002cb00;
                return "IDisplay.SCREEN_ON";
            case ecarx.carext.vehicle.module.IAPA.APA_INTELLIGENT_PARKING_SWITCH:// 0x00013e00;
                return "IAPA.APA_INTELLIGENT_PARKING_SWITCH";
            case ecarx.carext.vehicle.module.v446k.IAPA.IS_FUNC_APA_ACTIVE:// 0x00028600;
                return "IAPA.IS_FUNC_APA_ACTIVE";
            case ecarx.carext.vehicle.module.v446k.IAPA.IS_FUNC_RPA_ACTIVE:// 0x00028700;
                return "IAPA.IS_FUNC_RPA_ACTIVE";
            case ecarx.carext.vehicle.module.v446k.IAPA.IS_FUNC_RPP_ACTIVE:// 0x00028800;
                return "IAPA.IS_FUNC_RPP_ACTIVE";
            case ecarx.carext.vehicle.module.v446k.IAPA.IS_FUNC_RSPA_ACTIVE:// 0x00028900;
                return "IAPA.IS_FUNC_RSPA_ACTIVE";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_IS_DISPLAY:// 0x00028a00;
                return "IAPA.APA_IS_DISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_TOAST_NOTIFICATION:// 0x00028b00;
                return "IAPA.APA_TOAST_NOTIFICATION";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_INTERRUPT_CONTROL:// 0x00028c00;
                return "IAPA.APA_PARKING_INTERRUPT_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_INTERRUPT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.ParkingInterruptStatus.CONTROL_ITEM1:// 0x00028c00|0x01;
                return "IAPA.APA_PARKING_INTERRUPT_CONTROL|ParkingInterruptStatus.CONTROL_ITEM1";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_INTERRUPT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.ParkingInterruptStatus.CONTROL_ITEM2:// 0x00028c00|0x02;
                return "IAPA.APA_PARKING_INTERRUPT_CONTROL|ParkingInterruptStatus.CONTROL_ITEM2";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_INTERRUPT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.ParkingInterruptStatus.CONTROL_ITEM3:// 0x00028c00|0x03;
                return "IAPA.APA_PARKING_INTERRUPT_CONTROL|ParkingInterruptStatus.CONTROL_ITEM3";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_TO_AVM:// 0x00028d00;
                return "IAPA.APA_SWITCH_TO_AVM";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_TO_AVM
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00028d00|0x01;
                return "IAPA.APA_SWITCH_TO_AVM|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_TO_AVM
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00028d00|0x02;
                return "IAPA.APA_SWITCH_TO_AVM|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_TO_AVM
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00028d00|0x03;
                return "IAPA.APA_SWITCH_TO_AVM|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_TO_AVM
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00028d00|0x04;
                return "IAPA.APA_SWITCH_TO_AVM|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_PROGRESS_AND_STATUS:// 0x00028e00;
                return "IAPA.APA_PARKING_PROGRESS_AND_STATUS";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_STATUS_MARKET:// 0x00028f00;
                return "IAPA.APA_STATUS_MARKET";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_VOICE_BROADCAST:// 0x00029000;
                return "IAPA.APA_VOICE_BROADCAST";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_TRAVELED_DISTANCE:// 0x00029100;
                return "IAPA.APA_PARKING_TRAVELED_DISTANCE";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_IN_CONTROL:// 0x00029200;
                return "IAPA.APA_PARKING_IN_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_IN_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029200|0x01;
                return "IAPA.APA_PARKING_IN_CONTROL|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_IN_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029200|0x02;
                return "IAPA.APA_PARKING_IN_CONTROL|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_IN_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029200|0x03;
                return "IAPA.APA_PARKING_IN_CONTROL|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_IN_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029200|0x04;
                return "IAPA.APA_PARKING_IN_CONTROL|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_CONTROL:// 0x00029300;
                return "IAPA.APA_PARKING_OUT_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029300|0x01;
                return "IAPA.APA_PARKING_OUT_CONTROL|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029300|0x02;
                return "IAPA.APA_PARKING_OUT_CONTROL|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029300|0x03;
                return "IAPA.APA_PARKING_OUT_CONTROL|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029300|0x04;
                return "IAPA.APA_PARKING_OUT_CONTROL|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_DIRECTION:// 0x00029400;
                return "IAPA.APA_PARKING_OUT_DIRECTION";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_DIRECTION
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029400|0x01;
                return "IAPA.APA_PARKING_OUT_DIRECTION|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_DIRECTION
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029400|0x02;
                return "IAPA.APA_PARKING_OUT_DIRECTION|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_DIRECTION
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029400|0x03;
                return "IAPA.APA_PARKING_OUT_DIRECTION|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_OUT_DIRECTION
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029400|0x04;
                return "IAPA.APA_PARKING_OUT_DIRECTION|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SETTINGS_STATUS:// 0x00029500;
                return "IAPA.APA_SETTINGS_STATUS";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SETTINGS_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029500|0x01;
                return "IAPA.APA_SETTINGS_STATUS|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SETTINGS_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029500|0x02;
                return "IAPA.APA_SETTINGS_STATUS|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SETTINGS_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029500|0x03;
                return "IAPA.APA_SETTINGS_STATUS|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SETTINGS_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029500|0x04;
                return "IAPA.APA_SETTINGS_STATUS|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_CONTROL:// 0x00029600;
                return "IAPA.APA_PREMOTE_PARKING_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029600|0x01;
                return "IAPA.APA_PREMOTE_PARKING_CONTROL|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029600|0x02;
                return "IAPA.APA_PREMOTE_PARKING_CONTROL|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029600|0x03;
                return "IAPA.APA_PREMOTE_PARKING_CONTROL|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029600|0x04;
                return "IAPA.APA_PREMOTE_PARKING_CONTROL|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL:// 0x00029700;
                return "IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAParkingDistance.ITME1:// 0x00029700|0x01;
                return "IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL|APAParkingDistance.ITME1";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAParkingDistance.ITEM2:// 0x00029700|0x02;
                return "IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL|APAParkingDistance.ITEM2";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAParkingDistance.ITEM3:// 0x00029700|0x03;
                return "IAPA.APA_PREMOTE_PARKING_DISTANCE_CONTROL|APAParkingDistance.ITEM3";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_HELP_VOICE:// 0x00029800;
                return "IAPA.APA_SWITCH_HELP_VOICE";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_HELP_VOICE
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAVoiceMode.ITEM1:// 0x00029800|0x01;
                return "IAPA.APA_SWITCH_HELP_VOICE|APAVoiceMode.ITEM1";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SWITCH_HELP_VOICE
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAVoiceMode.ITEM2:// 0x00029800|0x02;
                return "IAPA.APA_SWITCH_HELP_VOICE|APAVoiceMode.ITEM2";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_AVM_SETTINGS:// 0x00029900;
                return "IAPA.APA_AVM_SETTINGS";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_AVM_SETTINGS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x00029900|0x01;
                return "IAPA.APA_AVM_SETTINGS|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_AVM_SETTINGS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x00029900|0x02;
                return "IAPA.APA_AVM_SETTINGS|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_AVM_SETTINGS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x00029900|0x03;
                return "IAPA.APA_AVM_SETTINGS|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_AVM_SETTINGS
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x00029900|0x04;
                return "IAPA.APA_AVM_SETTINGS|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_LOW_SPEED_EXPLORE:// 0x00029a00;
                return "IAPA.APA_LOW_SPEED_EXPLORE";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_SELECT_VIR_PARKING_SPACE:// 0x00029b00;
                return "IAPA.APA_SELECT_VIR_PARKING_SPACE";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_HMI_SHOW_PARKING_SPOT:// 0x00029c00;
                return "IAPA.APA_HMI_SHOW_PARKING_SPOT";
            case ecarx.carext.vehicle.module.v446k.IAPA.PARKING_OUT_CONFIRMATION:// 0x0002b000;
                return "IAPA.PARKING_OUT_CONFIRMATION";
            case ecarx.carext.vehicle.module.v446k.IAPA.SENSORLESS_PARKING:// 0x0002bf00;
                return "IAPA.SENSORLESS_PARKING";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_EXIT:// 0x0002c000;
                return "IAPA.APA_EXIT";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_EXIT_BUTTON_ENABLE:// 0x0002c100;
                return "IAPA.APA_EXIT_BUTTON_ENABLE";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PDC_MUTE_SWITCH:// 0x0002c300;
                return "IAPA.APA_PDC_MUTE_SWITCH";
            case ecarx.carext.vehicle.module.v446k.IAPA.IS_FUNC_HPA_ACTIVE:// 0x0002d500;
                return "IAPA.IS_FUNC_HPA_ACTIVE";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ACTION_CONTROL:// 0x0002d600;
                return "IAPA.HPA_ACTION_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_POP:// 0x0002d700;
                return "IAPA.HPA_CONFIRM_POP";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_RECOVER_BTN:// 0x0002d800;
                return "IAPA.HPA_RECOVER_BTN";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_RECOVER_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002d800|0x01;
                return "IAPA.HPA_RECOVER_BTN|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_RECOVER_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002d800|0x02;
                return "IAPA.HPA_RECOVER_BTN|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_RECOVER_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002d800|0x03;
                return "IAPA.HPA_RECOVER_BTN|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_RECOVER_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002d800|0x04;
                return "IAPA.HPA_RECOVER_BTN|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_EXIT_BACK_BTN:// 0x0002d900;
                return "IAPA.HPA_EXIT_BACK_BTN";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_EXIT_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002d900|0x01;
                return "IAPA.HPA_EXIT_BACK_BTN|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_EXIT_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002d900|0x02;
                return "IAPA.HPA_EXIT_BACK_BTN|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_EXIT_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002d900|0x03;
                return "IAPA.HPA_EXIT_BACK_BTN|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_EXIT_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002d900|0x04;
                return "IAPA.HPA_EXIT_BACK_BTN|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_NOTIFICATIONS:// 0x0002da00;
                return "IAPA.HPA_NOTIFICATIONS";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_NOTIFICATIONS_CONTROL:// 0x0002db00;
                return "IAPA.HPA_NOTIFICATIONS_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_IS_DISPLAY:// 0x0002dc00;
                return "IAPA.HPA_IS_DISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_VIDEO_TYPE:// 0x0002dd00;
                return "IAPA.HPA_VIDEO_TYPE";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_TTS_CALLBACK:// 0x0002de00;
                return "IAPA.HPA_TTS_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_TTS_CONTROL:// 0x0002df00;
                return "IAPA.HPA_TTS_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_DEXTINATION_BTN:// 0x0002e000;
                return "IAPA.HPA_CONFIRM_DEXTINATION_BTN";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002e000|0x01;
                return "IAPA.HPA_CONFIRM_DEXTINATION_BTN|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002e000|0x02;
                return "IAPA.HPA_CONFIRM_DEXTINATION_BTN|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002e000|0x03;
                return "IAPA.HPA_CONFIRM_DEXTINATION_BTN|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002e000|0x04;
                return "IAPA.HPA_CONFIRM_DEXTINATION_BTN|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_BTN:// 0x0002e100;
                return "IAPA.HPA_SETTING_BTN";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002e100|0x01;
                return "IAPA.HPA_SETTING_BTN|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002e100|0x02;
                return "IAPA.HPA_SETTING_BTN|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002e100|0x03;
                return "IAPA.HPA_SETTING_BTN|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002e100|0x04;
                return "IAPA.HPA_SETTING_BTN|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONTROL:// 0x0002e200;
                return "IAPA.HPA_SETTING_PAGE_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG:// 0x0002e300;
                return "IAPA.HPA_SETTING_PAGE_CONFIG";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM0:// 0x0002e300|0x01;
                return "IAPA.HPA_SETTING_PAGE_CONFIG|HPASettingPageType.ITEM0";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM1:// 0x0002e300|0x02;
                return "IAPA.HPA_SETTING_PAGE_CONFIG|HPASettingPageType.ITEM1";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM2:// 0x0002e300|0x03;
                return "IAPA.HPA_SETTING_PAGE_CONFIG|HPASettingPageType.ITEM2";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM3:// 0x0002e300|0x04;
                return "IAPA.HPA_SETTING_PAGE_CONFIG|HPASettingPageType.ITEM3";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM4:// 0x0002e300|0x05;
                return "IAPA.HPA_SETTING_PAGE_CONFIG|HPASettingPageType.ITEM4";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PAGE_CONFIG
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPASettingPageType.ITEM5:// 0x0002e300|0x06;
                return "IAPA.HPA_SETTING_PAGE_CONFIG|HPASettingPageType.ITEM5";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_AUTO_ROUTES_SWITCH:// 0x0002e400;
                return "IAPA.HPA_SETTING_AUTO_ROUTES_SWITCH";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_PUSH_SWITCH:// 0x0002e500;
                return "IAPA.HPA_SETTING_PUSH_SWITCH";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CAR_CONFIG:// 0x0002e600;
                return "IAPA.TBA_CAR_CONFIG";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_HMI_STATUS:// 0x0002e700;
                return "IAPA.TBA_HMI_STATUS";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_BACK_BTN:// 0x0002e800;
                return "IAPA.TBA_BACK_BTN";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002e800|0x01;
                return "IAPA.TBA_BACK_BTN|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002e800|0x02;
                return "IAPA.TBA_BACK_BTN|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002e800|0x03;
                return "IAPA.TBA_BACK_BTN|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_BACK_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002e800|0x04;
                return "IAPA.TBA_BACK_BTN|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_START_BACK_TRACE_BTN:// 0x0002e900;
                return "IAPA.TBA_START_BACK_TRACE_BTN";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_START_BACK_TRACE_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002e900|0x01;
                return "IAPA.TBA_START_BACK_TRACE_BTN|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_START_BACK_TRACE_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002e900|0x02;
                return "IAPA.TBA_START_BACK_TRACE_BTN|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_START_BACK_TRACE_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002e900|0x03;
                return "IAPA.TBA_START_BACK_TRACE_BTN|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_START_BACK_TRACE_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002e900|0x04;
                return "IAPA.TBA_START_BACK_TRACE_BTN|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_TEXT_PARK_BEFORE:// 0x0002ea00;
                return "IAPA.TBA_TEXT_PARK_BEFORE";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_TEXT_PARK_AFTER:// 0x0002eb00;
                return "IAPA.TBA_TEXT_PARK_AFTER";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_TTS_CALLBACK:// 0x0002ec00;
                return "IAPA.TBA_TTS_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_TTS_CONTROL:// 0x0002ed00;
                return "IAPA.TBA_TTS_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CONFIRM_DEXTINATION_BTN:// 0x0002ee00;
                return "IAPA.TBA_CONFIRM_DEXTINATION_BTN";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002ee00|0x01;
                return "IAPA.TBA_CONFIRM_DEXTINATION_BTN|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002ee00|0x02;
                return "IAPA.TBA_CONFIRM_DEXTINATION_BTN|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002ee00|0x03;
                return "IAPA.TBA_CONFIRM_DEXTINATION_BTN|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.TBA_CONFIRM_DEXTINATION_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002ee00|0x04;
                return "IAPA.TBA_CONFIRM_DEXTINATION_BTN|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_TTS_CALLBACK_MODE:// 0x0002ef00;
                return "IAPA.HPA_SETTING_TTS_CALLBACK_MODE";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_TTS_CALLBACK_MODE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAAoundMode.ITEM0:// 0x0002ef00|0x01;
                return "IAPA.HPA_SETTING_TTS_CALLBACK_MODE|HPAAoundMode.ITEM0";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_TTS_CALLBACK_MODE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAAoundMode.ITEM1:// 0x0002ef00|0x02;
                return "IAPA.HPA_SETTING_TTS_CALLBACK_MODE|HPAAoundMode.ITEM1";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_TTS_CALLBACK_MODE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAAoundMode.ITEM2:// 0x0002ef00|0x03;
                return "IAPA.HPA_SETTING_TTS_CALLBACK_MODE|HPAAoundMode.ITEM2";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_SETTING_TTS_CALLBACK_MODE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAAoundMode.ITEM3:// 0x0002ef00|0x04;
                return "IAPA.HPA_SETTING_TTS_CALLBACK_MODE|HPAAoundMode.ITEM3";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_VIEW_TYPE:// 0x0002f000;
                return "IAPA.HPA_VIEW_TYPE";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAViewType.ITEM0:// 0x0002f000|0x01;
                return "IAPA.HPA_VIEW_TYPE|HPAViewType.ITEM0";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAViewType.ITEM1:// 0x0002f000|0x02;
                return "IAPA.HPA_VIEW_TYPE|HPAViewType.ITEM1";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAViewType.ITEM2:// 0x0002f000|0x03;
                return "IAPA.HPA_VIEW_TYPE|HPAViewType.ITEM2";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_DST:// 0x0002f100;
                return "IAPA.HPA_DISPLAY_ELEMENT_REC_DST";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_CRSSING:// 0x0002f200;
                return "IAPA.HPA_DISPLAY_ELEMENT_REC_CRSSING";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_BUM:// 0x0002f300;
                return "IAPA.HPA_DISPLAY_ELEMENT_REC_BUM";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_TO_END:// 0x0002f400;
                return "IAPA.HPA_DISPLAY_ELEMENT_REC_TO_END";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_PED:// 0x0002f500;
                return "IAPA.HPA_DISPLAY_ELEMENT_REC_PED";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_VEH:// 0x0002f600;
                return "IAPA.HPA_DISPLAY_ELEMENT_REC_VEH";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REC_RAMP:// 0x0002f700;
                return "IAPA.HPA_DISPLAY_ELEMENT_REC_RAMP";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_REMAINING_DST:// 0x0002f800;
                return "IAPA.HPA_DISPLAY_ELEMENT_REMAINING_DST";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_TIME_TYPE:// 0x0002f900;
                return "IAPA.HPA_DISPLAY_ELEMENT_TIME_TYPE";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_TUTORIAL:// 0x0002fa00;
                return "IAPA.HPA_DISPLAY_ELEMENT_TUTORIAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN:// 0x0002fb00;
                return "IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002fb00|0x01;
                return "IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002fb00|0x02;
                return "IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002fb00|0x03;
                return "IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002fb00|0x04;
                return "IAPA.HPA_ELEMENT_CREATE_PARK_IN_BTN|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN:// 0x0002fc00;
                return "IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NOTDISPLAY:// 0x0002fc00|0x01;
                return "IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN|APABtnStatus.APA_BTN_NOTDISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_NORMAL:// 0x0002fc00|0x02;
                return "IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN|APABtnStatus.APA_BTN_NORMAL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_GRAY:// 0x0002fc00|0x03;
                return "IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN|APABtnStatus.APA_BTN_GRAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN
                    |ecarx.carext.vehicle.module.v446k.IAPA.APABtnStatus.APA_BTN_HIGHLIGHT:// 0x0002fc00|0x04;
                return "IAPA.HPA_ELEMENT_CREATE_PARK_OUT_BTN|APABtnStatus.APA_BTN_HIGHLIGHT";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CREATE_PARK_IN:// 0x0002fd00;
                return "IAPA.HPA_CREATE_PARK_IN";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CREATE_PARK_OUT:// 0x0002fe00;
                return "IAPA.HPA_CREATE_PARK_OUT";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP0:// 0x0002ff00;
                return "IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP0";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP1:// 0x00030000;
                return "IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP1";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP2:// 0x00030100;
                return "IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP2";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP3:// 0x00030200;
                return "IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP3";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP4:// 0x00030300;
                return "IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP4";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_LOCAL_MAP_SELECT:// 0x00030400;
                return "IAPA.HPA_DISPLAY_ELEMENT_LOCAL_MAP_SELECT";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_LOCAL_MAP_CLOSE:// 0x00030500;
                return "IAPA.HPA_DISPLAY_ELEMENT_LOCAL_MAP_CLOSE";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_PARK_IN_COVER:// 0x00030600;
                return "IAPA.HPA_DISPLAY_ELEMENT_PARK_IN_COVER";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_PARK_OUT_COVER:// 0x00030700;
                return "IAPA.HPA_DISPLAY_ELEMENT_PARK_OUT_COVER";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_PARK_IN_COVER_SET:// 0x00030800;
                return "IAPA.HPA_DISPLAY_ELEMENT_PARK_IN_COVER_SET";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_PARK_OUT_COVER_SET:// 0x00030900;
                return "IAPA.HPA_DISPLAY_ELEMENT_PARK_OUT_COVER_SET";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_PARKING_PROGRESS:// 0x00030a00;
                return "IAPA.HPA_PARKING_PROGRESS";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_CREATE_ROUTR_PROGRESS:// 0x00030b00;
                return "IAPA.HPA_CREATE_ROUTR_PROGRESS";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_BUILD_RESULT:// 0x00030c00;
                return "IAPA.HPA_MAP_BUILD_RESULT";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_BUILD_RESULT
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAMapBuildStatus.ITEM0:// 0x00030c00|0x01;
                return "IAPA.HPA_MAP_BUILD_RESULT|HPAMapBuildStatus.ITEM0";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_BUILD_RESULT
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAMapBuildStatus.ITEM1:// 0x00030c00|0x02;
                return "IAPA.HPA_MAP_BUILD_RESULT|HPAMapBuildStatus.ITEM1";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_BUILD_RESULT
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAMapBuildStatus.ITEM2:// 0x00030c00|0x03;
                return "IAPA.HPA_MAP_BUILD_RESULT|HPAMapBuildStatus.ITEM2";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_BUILD_RESULT
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPAMapBuildStatus.ITEM3:// 0x00030c00|0x04;
                return "IAPA.HPA_MAP_BUILD_RESULT|HPAMapBuildStatus.ITEM3";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ROUTER_MANAGE_DISPLAY:// 0x00030d00;
                return "IAPA.HPA_ROUTER_MANAGE_DISPLAY";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ROUTER_MANAGE_DISPLAY
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPARouteDisplay.ITEM0:// 0x00030d00|0x01;
                return "IAPA.HPA_ROUTER_MANAGE_DISPLAY|HPARouteDisplay.ITEM0";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ROUTER_MANAGE_DISPLAY
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPARouteDisplay.ITEM1:// 0x00030d00|0x02;
                return "IAPA.HPA_ROUTER_MANAGE_DISPLAY|HPARouteDisplay.ITEM1";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_ROUTER_MANAGE_DISPLAY
                    |ecarx.carext.vehicle.module.v446k.IAPA.HPARouteDisplay.ITEM2:// 0x00030d00|0x03;
                return "IAPA.HPA_ROUTER_MANAGE_DISPLAY|HPARouteDisplay.ITEM2";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_MAP_RENAME_NOTICE_CONTROL:// 0x00030e00;
                return "IAPA.HPA_MAP_RENAME_NOTICE_CONTROL";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_PARK_IN_ROUTES_DELETE:// 0x00030f00;
                return "IAPA.HPA_PARK_IN_ROUTES_DELETE";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_PARK_OUT_ROUTES_DELETE:// 0x00031000;
                return "IAPA.HPA_PARK_OUT_ROUTES_DELETE";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_PARK_ROUTE_SYNC:// 0x00031100;
                return "IAPA.HPA_PARK_ROUTE_SYNC";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_VIEW_SWITCH:// 0x00031200;
                return "IAPA.HPA_DISPLAY_ELEMENT_VIEW_SWITCH";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_POP_VIEW_IS_REDUCE:// 0x00031300;
                return "IAPA.HPA_POP_VIEW_IS_REDUCE";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_OBSTACLE_TRIGGER_SWITCH:// 0x00031400;
                return "IAPA.APA_OBSTACLE_TRIGGER_SWITCH";
            case ecarx.carext.vehicle.module.v446k.IAPA.PDC_FRONT_DISTANCE:// 0x00031a00;
                return "IAPA.PDC_FRONT_DISTANCE";
            case ecarx.carext.vehicle.module.v446k.IAPA.PDC_REAR_DISTANCE:// 0x00031b00;
                return "IAPA.PDC_REAR_DISTANCE";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_PARKING_DOCK_DISABLE_REQUEST:// 0x00032500;
                return "IAPA.APA_PARKING_DOCK_DISABLE_REQUEST";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_PARKING_TEXT:// 0x00032c00;
                return "IAPA.HPA_PARKING_TEXT";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS:// 0x00033200;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_RSPA:// 0x00033200|0x01;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS|DisplayStatus.DISPLAY_RSPA";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_APA:// 0x00033200|0x02;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS|DisplayStatus.DISPLAY_APA";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_RPA:// 0x00033200|0x03;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS|DisplayStatus.DISPLAY_RPA";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_RPAP:// 0x00033200|0x04;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS|DisplayStatus.DISPLAY_RPAP";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_HPA:// 0x00033200|0x05;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS|DisplayStatus.DISPLAY_HPA";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_AVP:// 0x00033200|0x06;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS|DisplayStatus.DISPLAY_AVP";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_AVM:// 0x00033200|0x07;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS|DisplayStatus.DISPLAY_AVM";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_TBA:// 0x00033200|0x08;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS|DisplayStatus.DISPLAY_TBA";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_LSDA:// 0x00033200|0x09;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS|DisplayStatus.DISPLAY_LSDA";
            case ecarx.carext.vehicle.module.v446k.IAPA.PRKG_IMG_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.v446k.IAPA.DisplayStatus.DISPLAY_OFF:// 0x00033200|0xff;
                return "IAPA.PRKG_IMG_DISPLAY_STATUS|DisplayStatus.DISPLAY_OFF";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_HIGH_FREQUENCY_STATUS:// 0x00033c00;
                return "IAPA.APA_HIGH_FREQUENCY_STATUS";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP5:// 0x00034100;
                return "IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP5";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP6:// 0x00034200;
                return "IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP6";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP7:// 0x00034300;
                return "IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP7";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP8:// 0x00034400;
                return "IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP8";
            case ecarx.carext.vehicle.module.v446k.IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP9:// 0x00034500;
                return "IAPA.HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP9";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_VIEW_TYPE:// 0x00034a00;
                return "IAPA.APA_VIEW_TYPE";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAViewType.TYPE_VIEW_DEFAULT:// 0x00034a00|0x01;
                return "IAPA.APA_VIEW_TYPE|APAViewType.TYPE_VIEW_DEFAULT";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAViewType.TYPE_VIEW_FRONT:// 0x00034a00|0x02;
                return "IAPA.APA_VIEW_TYPE|APAViewType.TYPE_VIEW_FRONT";
            case ecarx.carext.vehicle.module.v446k.IAPA.APA_VIEW_TYPE
                    |ecarx.carext.vehicle.module.v446k.IAPA.APAViewType.TYPE_VIEW_REAR:// 0x00034a00|0x03;
                return "IAPA.APA_VIEW_TYPE|APAViewType.TYPE_VIEW_REAR";
            case ecarx.carext.vehicle.module.IAvm.AVM_DISPLAY_STATUS:// 0x00023e00;
                return "IAvm.AVM_DISPLAY_STATUS";
            case ecarx.carext.vehicle.module.IAvm.AVM_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.IAvm.DisplayMode.FULL_SCREEN:// 0x00023e00|0x01;
                return "IAvm.AVM_DISPLAY_STATUS|DisplayMode.FULL_SCREEN";
            case ecarx.carext.vehicle.module.IAvm.AVM_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.IAvm.DisplayMode.MINI_WINDOW:// 0x00023e00|0x02;
                return "IAvm.AVM_DISPLAY_STATUS|DisplayMode.MINI_WINDOW";
            case ecarx.carext.vehicle.module.IAvm.AVM_DISPLAY_STATUS
                    |ecarx.carext.vehicle.module.IAvm.DisplayMode.DISPLAY_OFF:// 0x00023e00|0xff;
                return "IAvm.AVM_DISPLAY_STATUS|DisplayMode.DISPLAY_OFF";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_SOC:// 0x00003500;
                return "ICharging.CHARGING_SOC";
            case ecarx.carext.vehicle.module.ICharging.BATTERY_CHARGING_CURRENT_POWER:// 0x00003900;
                return "ICharging.BATTERY_CHARGING_CURRENT_POWER";
            case ecarx.carext.vehicle.module.ICharging.DC_CHARGING_POWER:// 0x00003c00;
                return "ICharging.DC_CHARGING_POWER";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_ESTIMATED_TIME:// 0x00004000;
                return "ICharging.CHARGING_ESTIMATED_TIME";
            case ecarx.carext.vehicle.module.ICharging.CHARGE_FUNC_CHARGING:// 0x00004900;
                return "ICharging.CHARGE_FUNC_CHARGING";
            case ecarx.carext.vehicle.module.ICharging.CHARGE_FUNC_CHARGING
                    |ecarx.carext.vehicle.module.ICharging.ChargeSwitchState.STOP:// 0x00004900|0x01;
                return "ICharging.CHARGE_FUNC_CHARGING|ChargeSwitchState.STOP";
            case ecarx.carext.vehicle.module.ICharging.CHARGE_FUNC_CHARGING
                    |ecarx.carext.vehicle.module.ICharging.ChargeSwitchState.RESTART:// 0x00004900|0x02;
                return "ICharging.CHARGE_FUNC_CHARGING|ChargeSwitchState.RESTART";
            case ecarx.carext.vehicle.module.ICharging.CHARGE_FUNC_CHARGING
                    |ecarx.carext.vehicle.module.ICharging.ChargeSwitchState.START_NOW:// 0x00004900|0x03;
                return "ICharging.CHARGE_FUNC_CHARGING|ChargeSwitchState.START_NOW";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_GUN_LOCK:// 0x00004a00;
                return "ICharging.CHARGING_GUN_LOCK";
            case ecarx.carext.vehicle.module.ICharging.DISCHARGING_SOC:// 0x00004b00;
                return "ICharging.DISCHARGING_SOC";
            case ecarx.carext.vehicle.module.ICharging.DISCHARGING_SWITCH_V2L:// 0x00004c00;
                return "ICharging.DISCHARGING_SWITCH_V2L";
            case ecarx.carext.vehicle.module.ICharging.DISCHARGING_WORK_VOLTAGE:// 0x00004d00;
                return "ICharging.DISCHARGING_WORK_VOLTAGE";
            case ecarx.carext.vehicle.module.ICharging.DISCHARGING_WORK_CURRENT:// 0x00004e00;
                return "ICharging.DISCHARGING_WORK_CURRENT";
            case ecarx.carext.vehicle.module.ICharging.BATTERY_DISCHARGING_CURRENT_POWER:// 0x00004f00;
                return "ICharging.BATTERY_DISCHARGING_CURRENT_POWER";
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION:// 0x0000a700;
                return "ICharging.ENERGY_REGENERATION";
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_LOW:// 0x0000a700|0x01;
                return "ICharging.ENERGY_REGENERATION|EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_LOW";
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_MID:// 0x0000a700|0x02;
                return "ICharging.ENERGY_REGENERATION|EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_MID";
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_HIGH:// 0x0000a700|0x03;
                return "ICharging.ENERGY_REGENERATION|EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_HIGH";
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_AUTO:// 0x0000a700|0x04;
                return "ICharging.ENERGY_REGENERATION|EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_AUTO";
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_LOWER:// 0x0000a700|0x05;
                return "ICharging.ENERGY_REGENERATION|EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_LOWER";
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_HIGHER:// 0x0000a700|0x06;
                return "ICharging.ENERGY_REGENERATION|EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_HIGHER";
            case ecarx.carext.vehicle.module.ICharging.ENERGY_REGENERATION
                    |ecarx.carext.vehicle.module.ICharging.EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_OFF:// 0x0000a700|0xff;
                return "ICharging.ENERGY_REGENERATION|EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_OFF";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE:// 0x0000dd00;
                return "ICharging.CHARGING_PLUG_STATE";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_UNKNOWN:// 0x0000dd00|0x01;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_UNKNOWN";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_PREPARED:// 0x0000dd00|0x02;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_PREPARED";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_CHARGING:// 0x0000dd00|0x03;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_CHARGING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_COMPLETED:// 0x0000dd00|0x04;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_COMPLETED";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_MULTI:// 0x0000dd00|0x05;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_MULTI";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_ERROR:// 0x0000dd00|0x06;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_ERROR";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING:// 0x0000dd00|0x07;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_DISCHARGING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING_COMPLETED:// 0x0000dd00|0x08;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_DISCHARGING_COMPLETED";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_HEATING:// 0x0000dd00|0x09;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_HEATING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_CONNECTED:// 0x0000dd00|0x0a;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_CONNECTED";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCONNECTED:// 0x0000dd00|0x0b;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_DISCONNECTED";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_CHARGING_PAUSE:// 0x0000dd00|0x0c;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_CHARGING_PAUSE";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING_PAUSE:// 0x0000dd00|0x0d;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_DISCHARGING_PAUSE";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_SLOW_CHARGING_CONNECTED:// 0x0000dd00|0x0e;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_SLOW_CHARGING_CONNECTED";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_QUICK_CHARGING_CONNECTED:// 0x0000dd00|0x0f;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_QUICK_CHARGING_CONNECTED";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING_CONNECTED:// 0x0000dd00|0x10;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_DISCHARGING_CONNECTED";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_WAITING:// 0x0000dd00|0x11;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_WAITING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_MIS_MATCHED:// 0x0000dd00|0x12;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_MIS_MATCHED";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_NONE:// 0x0000dd00|0x13;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_NONE";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_RESERVE_WAITING:// 0x0000dd00|0x14;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_RESERVE_WAITING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_CHARGING_FAIL:// 0x0000dd00|0x15;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_CHARGING_FAIL";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING_FAIL:// 0x0000dd00|0x16;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_DISCHARGING_FAIL";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_PLUG_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingPlugState.STATE_DISCHARGING_END:// 0x0000dd00|0x17;
                return "ICharging.CHARGING_PLUG_STATE|ChargingPlugState.STATE_DISCHARGING_END";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE:// 0x0000de00;
                return "ICharging.CHARGING_DISCHARGING_STATE";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DEFAULT:// 0x0000de00|0x01;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DEFAULT";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.NO_CHARGING:// 0x0000de00|0x02;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.NO_CHARGING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.AC_CHARGING:// 0x0000de00|0x03;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.AC_CHARGING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.CHARGING_END:// 0x0000de00|0x04;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.CHARGING_END";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.CHARGING_CMPL:// 0x0000de00|0x05;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.CHARGING_CMPL";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.HEATING:// 0x0000de00|0x06;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.HEATING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.BOOKING:// 0x0000de00|0x07;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.BOOKING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.NO_DISCHARING:// 0x0000de00|0x08;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.NO_DISCHARING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DISCHARING:// 0x0000de00|0x09;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DISCHARING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DISCHARING_END:// 0x0000de00|0x0a;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DISCHARING_END";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DISCHARING_CMPL:// 0x0000de00|0x0b;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DISCHARING_CMPL";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.CHARING_FALUT:// 0x0000de00|0x0c;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.CHARING_FALUT";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DISCHARING_FALUT:// 0x0000de00|0x0d;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DISCHARING_FALUT";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.AC_CHRGNFLTCHRGRSIDE:// 0x0000de00|0x0e;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.AC_CHRGNFLTCHRGRSIDE";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHARGING:// 0x0000de00|0x0f;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DC_CHARGING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTVEHSIDE:// 0x0000de00|0x10;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DC_CHRGNFLTVEHSIDE";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDETEMPFLT:// 0x0000de00|0x11;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDETEMPFLT";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDECONFLT:// 0x0000de00|0x12;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDECONFLT";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDEHWFLT:// 0x0000de00|0x13;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDEHWFLT";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDEEMGYFLT:// 0x0000de00|0x14;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDEEMGYFLT";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDECOMFLT:// 0x0000de00|0x15;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDECOMFLT";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.SUPERCHARGING:// 0x0000de00|0x16;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.SUPERCHARGING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.AC_CHARGINGSUSPEND:// 0x0000de00|0x17;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.AC_CHARGINGSUSPEND";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.DC_CHARGINGEND:// 0x0000de00|0x18;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.DC_CHARGINGEND";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.ACCHRGNFLTVEHSIDE:// 0x0000de00|0x19;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.ACCHRGNFLTVEHSIDE";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.BOOSTCHARGING:// 0x0000de00|0x1a;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.BOOSTCHARGING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.BOOSTCHARGINGFLT:// 0x0000de00|0x1b;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.BOOSTCHARGINGFLT";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.WIRELESSCHARGING:// 0x0000de00|0x1c;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.WIRELESSCHARGING";
            case ecarx.carext.vehicle.module.ICharging.CHARGING_DISCHARGING_STATE
                    |ecarx.carext.vehicle.module.ICharging.ChargingDisChargingState.NONE:// 0x0000de00|0x1d;
                return "ICharging.CHARGING_DISCHARGING_STATE|ChargingDisChargingState.NONE";
            case ecarx.carext.vehicle.module.v446k.ICharging.CHARGE_FUNC_PARKING:// 0x00015a00;
                return "ICharging.CHARGE_FUNC_PARKING";
            case ecarx.carext.vehicle.module.v446k.ICharging.DISTANCE_PROTECTION:// 0x00015b00;
                return "ICharging.DISTANCE_PROTECTION";
            case ecarx.carext.vehicle.module.v446k.ICharging.DISTANCE_PROTECTION_SWITCH:// 0x00015c00;
                return "ICharging.DISTANCE_PROTECTION_SWITCH";
            case ecarx.carext.vehicle.module.v446k.ICharging.CHARGING_ENERGY:// 0x00015e00;
                return "ICharging.CHARGING_ENERGY";
            case ecarx.carext.vehicle.module.v446k.ICharging.DISCHARGING_ENERGY:// 0x00015f00;
                return "ICharging.DISCHARGING_ENERGY";
            case ecarx.carext.vehicle.module.v446k.ICharging.CHARGING_WORK_TIME:// 0x00016100;
                return "ICharging.CHARGING_WORK_TIME";
            case ecarx.carext.vehicle.module.v446k.ICharging.DISCHARGING_WORK_TIME:// 0x00016200;
                return "ICharging.DISCHARGING_WORK_TIME";
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE:// 0x00016a00;
                return "ICharging.POWER_FLOW_NOTICE";
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_NOREQUESET:// 0x00016a00|0x01;
                return "ICharging.POWER_FLOW_NOTICE|PowerFlowNotice.POWER_FLOW_NOTICE_NOREQUESET";
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_NOACTIVE:// 0x00016a00|0x02;
                return "ICharging.POWER_FLOW_NOTICE|PowerFlowNotice.POWER_FLOW_NOTICE_NOACTIVE";
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING:// 0x00016a00|0x03;
                return "ICharging.POWER_FLOW_NOTICE|PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING";
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING_COMPL:// 0x00016a00|0x04;
                return "ICharging.POWER_FLOW_NOTICE|PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING_COMPL";
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING_FAULT:// 0x00016a00|0x05;
                return "ICharging.POWER_FLOW_NOTICE|PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING_FAULT";
            case ecarx.carext.vehicle.module.v446k.ICharging.POWER_FLOW_NOTICE
                    |ecarx.carext.vehicle.module.v446k.ICharging.PowerFlowNotice.POWER_FLOW_NOTICE_CANNOTENTER:// 0x00016a00|0x06;
                return "ICharging.POWER_FLOW_NOTICE|PowerFlowNotice.POWER_FLOW_NOTICE_CANNOTENTER";
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_SOC:// 0x00016b00;
                return "ICharging.BATTERY_SOC";
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_MODE:// 0x00017100;
                return "ICharging.BATTERY_MODE";
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_MODE
                    |ecarx.carext.vehicle.module.v446k.ICharging.BatteryMode.BATTERY_MODE_NORMAL:// 0x00017100|0x01;
                return "ICharging.BATTERY_MODE|BatteryMode.BATTERY_MODE_NORMAL";
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_MODE
                    |ecarx.carext.vehicle.module.v446k.ICharging.BatteryMode.BATTERY_MODE_HOLD:// 0x00017100|0x02;
                return "ICharging.BATTERY_MODE|BatteryMode.BATTERY_MODE_HOLD";
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_MODE
                    |ecarx.carext.vehicle.module.v446k.ICharging.BatteryMode.BATTERY_MODE_CHARGE:// 0x00017100|0x03;
                return "ICharging.BATTERY_MODE|BatteryMode.BATTERY_MODE_CHARGE";
            case ecarx.carext.vehicle.module.v446k.ICharging.BOOK_CHARGING_IMMEDIATELY:// 0x0001af00;
                return "ICharging.BOOK_CHARGING_IMMEDIATELY";
            case ecarx.carext.vehicle.module.v446k.ICharging.EXTERNAL_CHARGING_LIGHT:// 0x0001b200;
                return "ICharging.EXTERNAL_CHARGING_LIGHT";
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_TYPE:// 0x0001cc00;
                return "ICharging.BATTERY_TYPE";
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_TYPE
                    |ecarx.carext.vehicle.module.v446k.ICharging.BatteryType.TERNARY_LITHIUM_BATTERY:// 0x0001cc00|0x01;
                return "ICharging.BATTERY_TYPE|BatteryType.TERNARY_LITHIUM_BATTERY";
            case ecarx.carext.vehicle.module.v446k.ICharging.BATTERY_TYPE
                    |ecarx.carext.vehicle.module.v446k.ICharging.BatteryType.LITHIUM_IRON_PHOSPHATE_BATTERY:// 0x0001cc00|0x02;
                return "ICharging.BATTERY_TYPE|BatteryType.LITHIUM_IRON_PHOSPHATE_BATTERY";
            case ecarx.carext.vehicle.module.v446k.ICharging.MAX_EV_ENDURANCE:// 0x0001cf00;
                return "ICharging.MAX_EV_ENDURANCE";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_WORK_MODE:// 0x0001e100;
                return "ICharging.WPC_WORK_MODE";
            case ecarx.carext.vehicle.module.v446k.ICharging.MAX_EV_ENDURANCE_BLOCKED:// 0x00021f00;
                return "ICharging.MAX_EV_ENDURANCE_BLOCKED";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES:// 0x00022200;
                return "ICharging.WPC_CHARGE_STATES";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.UNKNOWN:// 0x00022200|0x01;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.UNKNOWN";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.NO_DEVICE:// 0x00022200|0x02;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.NO_DEVICE";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.STANDBY:// 0x00022200|0x03;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.STANDBY";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.CHARGING:// 0x00022200|0x04;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.CHARGING";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.CHARGED:// 0x00022200|0x05;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.CHARGED";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.ERROR:// 0x00022200|0x06;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.ERROR";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.OVERHEAT:// 0x00022200|0x07;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.OVERHEAT";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.OVERPOWER:// 0x00022200|0x08;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.OVERPOWER";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.FOD:// 0x00022200|0x09;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.FOD";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.PEPS_INTERRUPT:// 0x00022200|0x0a;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.PEPS_INTERRUPT";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.OVERVOLTAGE:// 0x00022200|0x0b;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.OVERVOLTAGE";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.CHARGING_ONE_FOURTH:// 0x00022200|0x0c;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.CHARGING_ONE_FOURTH";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.CHARGING_HALF:// 0x00022200|0x0d;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.CHARGING_HALF";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_STATES
                    |ecarx.carext.vehicle.module.v446k.ICharging.ChargingStates.OFF:// 0x00022200|0xff;
                return "ICharging.WPC_CHARGE_STATES|ChargingStates.OFF";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_CHARGE_PERCENTAGE:// 0x00022400;
                return "ICharging.WPC_CHARGE_PERCENTAGE";
            case ecarx.carext.vehicle.module.v446k.ICharging.WPC_REMIND_PHONE_LEAVE_OVER:// 0x00022500;
                return "ICharging.WPC_REMIND_PHONE_LEAVE_OVER";
            case ecarx.carext.vehicle.module.v446k.ICharging.START_ENGINE_WHEN_LOW_POWER:// 0x00023900;
                return "ICharging.START_ENGINE_WHEN_LOW_POWER";
            case ecarx.carext.vehicle.module.ISensor.ENDURANCE_MILEAGE_EV:// 0x0000cc00;
                return "ISensor.ENDURANCE_MILEAGE_EV";
            case ecarx.carext.vehicle.module.ISensor.BATTERY_PERCENTAGE:// 0x0000cd00;
                return "ISensor.BATTERY_PERCENTAGE";
            case ecarx.carext.vehicle.module.ISensor.AVG_ENERGY_CONSUMPTION:// 0x0000e000;
                return "ISensor.AVG_ENERGY_CONSUMPTION";
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE:// 0x0000e800;
                return "ISensor.CAR_MODE";
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.NORMAL:// 0x0000e800|0x01;
                return "ISensor.CAR_MODE|CarMode.NORMAL";
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.FACTORY:// 0x0000e800|0x02;
                return "ISensor.CAR_MODE|CarMode.FACTORY";
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.TRANSPORT:// 0x0000e800|0x03;
                return "ISensor.CAR_MODE|CarMode.TRANSPORT";
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.CRASH:// 0x0000e800|0x04;
                return "ISensor.CAR_MODE|CarMode.CRASH";
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.DYNO:// 0x0000e800|0x05;
                return "ISensor.CAR_MODE|CarMode.DYNO";
            case ecarx.carext.vehicle.module.ISensor.CAR_MODE
                    |ecarx.carext.vehicle.module.ISensor.CarMode.SHOW_ROOM:// 0x0000e800|0x06;
                return "ISensor.CAR_MODE|CarMode.SHOW_ROOM";
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE:// 0x00010300;
                return "ISensor.USAGE_MODE";
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.ABANDONED:// 0x00010300|0x01;
                return "ISensor.USAGE_MODE|UsageMode.ABANDONED";
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.INACTIVE:// 0x00010300|0x02;
                return "ISensor.USAGE_MODE|UsageMode.INACTIVE";
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.CONVENIENCE:// 0x00010300|0x03;
                return "ISensor.USAGE_MODE|UsageMode.CONVENIENCE";
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.ACTIVE:// 0x00010300|0x04;
                return "ISensor.USAGE_MODE|UsageMode.ACTIVE";
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.DRIVING:// 0x00010300|0x05;
                return "ISensor.USAGE_MODE|UsageMode.DRIVING";
            case ecarx.carext.vehicle.module.ISensor.USAGE_MODE
                    |ecarx.carext.vehicle.module.ISensor.UsageMode.UNKNOWN:// 0x00010300|0xff;
                return "ISensor.USAGE_MODE|UsageMode.UNKNOWN";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE:// 0x0001bb00;
                return "ISensor.POWER_STATE";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.SLEEP:// 0x0001bb00|0x01;
                return "ISensor.POWER_STATE|PowerState.SLEEP";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.STARTUP_READY:// 0x0001bb00|0x02;
                return "ISensor.POWER_STATE|PowerState.STARTUP_READY";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.WELCOME:// 0x0001bb00|0x03;
                return "ISensor.POWER_STATE|PowerState.WELCOME";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.STARTUP_INACTIVE:// 0x0001bb00|0x04;
                return "ISensor.POWER_STATE|PowerState.STARTUP_INACTIVE";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.CONVENIENCE:// 0x0001bb00|0x05;
                return "ISensor.POWER_STATE|PowerState.CONVENIENCE";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.ACTIVE:// 0x0001bb00|0x06;
                return "ISensor.POWER_STATE|PowerState.ACTIVE";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.DRIVING:// 0x0001bb00|0x07;
                return "ISensor.POWER_STATE|PowerState.DRIVING";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.ERROR:// 0x0001bb00|0x08;
                return "ISensor.POWER_STATE|PowerState.ERROR";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.KEEP_TOUCH:// 0x0001bb00|0x09;
                return "ISensor.POWER_STATE|PowerState.KEEP_TOUCH";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.PREPARE_SHUTDOWN:// 0x0001bb00|0x0a;
                return "ISensor.POWER_STATE|PowerState.PREPARE_SHUTDOWN";
            case ecarx.carext.vehicle.module.ISensor.POWER_STATE
                    |ecarx.carext.vehicle.module.ISensor.PowerState.STR:// 0x0001bb00|0x0b;
                return "ISensor.POWER_STATE|PowerState.STR";
            case ecarx.carext.vehicle.module.v446k.ISensor.CHARGING_ENDURANCE_MILEAGE:// 0x00016500;
                return "ISensor.CHARGING_ENDURANCE_MILEAGE";
            case ecarx.carext.vehicle.module.v446k.ISensor.DISCHARGING_ENDURANCE_MILEAGE:// 0x00016600;
                return "ISensor.DISCHARGING_ENDURANCE_MILEAGE";
            case ecarx.carext.vehicle.module.v446k.ISensor.FUEL_WARNING_COLOR:// 0x00016900;
                return "ISensor.FUEL_WARNING_COLOR";
            case ecarx.carext.vehicle.module.v446k.ISensor.FUEL_WARNING_COLOR
                    |ecarx.carext.vehicle.module.v446k.ISensor.FuelWarningColor.FUEL_WARNING_COLOR_NORMAL:// 0x00016900|0x01;
                return "ISensor.FUEL_WARNING_COLOR|FuelWarningColor.FUEL_WARNING_COLOR_NORMAL";
            case ecarx.carext.vehicle.module.v446k.ISensor.FUEL_WARNING_COLOR
                    |ecarx.carext.vehicle.module.v446k.ISensor.FuelWarningColor.FUEL_WARNING_COLOR_YELLOW:// 0x00016900|0x02;
                return "ISensor.FUEL_WARNING_COLOR|FuelWarningColor.FUEL_WARNING_COLOR_YELLOW";
            case ecarx.carext.vehicle.module.v446k.ISensor.AVG_FUEL_CONSUMPTION:// 0x00017800;
                return "ISensor.AVG_FUEL_CONSUMPTION";
            case ecarx.carext.vehicle.module.v446k.ISensor.FUEL_PERCENTAGE:// 0x00017c00;
                return "ISensor.FUEL_PERCENTAGE";
            case ecarx.carext.vehicle.module.v446k.ISensor.ENDURANCE_MILEAGE_FUEL:// 0x00017d00;
                return "ISensor.ENDURANCE_MILEAGE_FUEL";
            case ecarx.carext.vehicle.module.v446k.ISensor.PM25_INDOOR:// 0x00029d00;
                return "ISensor.PM25_INDOOR";
            case ecarx.carext.vehicle.module.v446k.ISensor.AQI_INDOOR:// 0x0002ac00;
                return "ISensor.AQI_INDOOR";
            case ecarx.carext.vehicle.module.ISceneMode.AUTO_SHOW_MODE:// 0x0000bb00;
                return "ISceneMode.AUTO_SHOW_MODE";
            case ecarx.carext.vehicle.module.ISceneMode.PARKING_COMFORT_MODE:// 0x00018100;
                return "ISceneMode.PARKING_COMFORT_MODE";
            case ecarx.carext.vehicle.module.ISceneMode.NAP_MODE:// 0x00018200;
                return "ISceneMode.NAP_MODE";
            case ecarx.carext.vehicle.module.v446k.ISceneMode.SCENE_MODE_CAST_STATE:// 0x00020d00;
                return "ISceneMode.SCENE_MODE_CAST_STATE";
            case ecarx.carext.vehicle.module.v446k.ISceneMode.SCENE_MODE_CAST_STATE
                    |ecarx.carext.vehicle.module.v446k.ISceneMode.SceneModeCastState.CAST_ALLOW:// 0x00020d00|0x01;
                return "ISceneMode.SCENE_MODE_CAST_STATE|SceneModeCastState.CAST_ALLOW";
            case ecarx.carext.vehicle.module.v446k.ISceneMode.SCENE_MODE_CAST_STATE
                    |ecarx.carext.vehicle.module.v446k.ISceneMode.SceneModeCastState.CAST_NOT_ALLOW:// 0x00020d00|0x02;
                return "ISceneMode.SCENE_MODE_CAST_STATE|SceneModeCastState.CAST_NOT_ALLOW";
            case ecarx.carext.vehicle.module.v446k.ISceneMode.AUTO_SHOW_MODE_ENTER_CONDITIONS:// 0x00024e00;
                return "ISceneMode.AUTO_SHOW_MODE_ENTER_CONDITIONS";
            case ecarx.carext.vehicle.module.v446k.ISceneMode.AUTO_SHOW_MODE_ENTER_CONDITIONS
                    |ecarx.carext.vehicle.module.v446k.ISceneMode.AutoShowModeStatus.MEET:// 0x00024e00|0x01;
                return "ISceneMode.AUTO_SHOW_MODE_ENTER_CONDITIONS|AutoShowModeStatus.MEET";
            case ecarx.carext.vehicle.module.v446k.ISceneMode.AUTO_SHOW_MODE_ENTER_CONDITIONS
                    |ecarx.carext.vehicle.module.v446k.ISceneMode.AutoShowModeStatus.NOT_MEET:// 0x00024e00|0x02;
                return "ISceneMode.AUTO_SHOW_MODE_ENTER_CONDITIONS|AutoShowModeStatus.NOT_MEET";
            case ecarx.carext.vehicle.module.ISafety.APPROACH_UNLOCK:// 0x00005600;
                return "ISafety.APPROACH_UNLOCK";
            case ecarx.carext.vehicle.module.ISafety.AWAY_LOCK:// 0x00005700;
                return "ISafety.AWAY_LOCK";
            case ecarx.carext.vehicle.module.ISafety.KEYLESS_UNLOCKING:// 0x00005900;
                return "ISafety.KEYLESS_UNLOCKING";
            case ecarx.carext.vehicle.module.ISafety.KEYLESS_UNLOCKING
                    |ecarx.carext.vehicle.module.ISafety.KeylessUnlockingValue.KEYLESS_UNLOCKING_ALL_DOORS:// 0x00005900|0x01;
                return "ISafety.KEYLESS_UNLOCKING|KeylessUnlockingValue.KEYLESS_UNLOCKING_ALL_DOORS";
            case ecarx.carext.vehicle.module.ISafety.KEYLESS_UNLOCKING
                    |ecarx.carext.vehicle.module.ISafety.KeylessUnlockingValue.KEYLESS_UNLOCKING_SINGLE_DOOR:// 0x00005900|0x02;
                return "ISafety.KEYLESS_UNLOCKING|KeylessUnlockingValue.KEYLESS_UNLOCKING_SINGLE_DOOR";
            case ecarx.carext.vehicle.module.ISafety.KEYLESS_UNLOCKING
                    |ecarx.carext.vehicle.module.ISafety.KeylessUnlockingValue.KEYLESS_UNLOCKING_OFF:// 0x00005900|0xff;
                return "ISafety.KEYLESS_UNLOCKING|KeylessUnlockingValue.KEYLESS_UNLOCKING_OFF";
            case ecarx.carext.vehicle.module.ISafety.CENTRAL_LOCK:// 0x00018700;
                return "ISafety.CENTRAL_LOCK";
            case ecarx.carext.vehicle.module.v446k.ISafety.GLOVEBOX_LOCK:// 0x0001d800;
                return "ISafety.GLOVEBOX_LOCK";
            case ecarx.carext.vehicle.module.v446k.ISafety.PARKING_AUTO_UNLOCK:// 0x0001dc00;
                return "ISafety.PARKING_AUTO_UNLOCK";
            case ecarx.carext.vehicle.module.v446k.ISafety.TWOSTEP_UNLOCKING:// 0x0001de00;
                return "ISafety.TWOSTEP_UNLOCKING";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC:// 0x00029f00;
                return "ISafety.PRIVATE_LOCK_CHECK_NFC";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD1:// 0x00029f00|0x01;
                return "ISafety.PRIVATE_LOCK_CHECK_NFC|NfcCarCheckState.CHECK_CARD1";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD2:// 0x00029f00|0x02;
                return "ISafety.PRIVATE_LOCK_CHECK_NFC|NfcCarCheckState.CHECK_CARD2";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD1_OK:// 0x00029f00|0x05;
                return "ISafety.PRIVATE_LOCK_CHECK_NFC|NfcCarCheckState.CHECK_CARD1_OK";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD2_OK:// 0x00029f00|0x06;
                return "ISafety.PRIVATE_LOCK_CHECK_NFC|NfcCarCheckState.CHECK_CARD2_OK";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD1_NOK:// 0x00029f00|0x09;
                return "ISafety.PRIVATE_LOCK_CHECK_NFC|NfcCarCheckState.CHECK_CARD1_NOK";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_CHECK_NFC
                    |ecarx.carext.vehicle.module.v446k.ISafety.NfcCarCheckState.CHECK_CARD2_NOK:// 0x00029f00|0x0a;
                return "ISafety.PRIVATE_LOCK_CHECK_NFC|NfcCarCheckState.CHECK_CARD2_NOK";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK:// 0x0002a000;
                return "ISafety.PRIVATE_LOCK";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_ACCESS_PASSWORD:// 0x0002a100;
                return "ISafety.PRIVATE_LOCK_ACCESS_PASSWORD";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_REMINDER:// 0x0002a200;
                return "ISafety.PRIVATE_LOCK_REMINDER";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_REMINDER
                    |ecarx.carext.vehicle.module.v446k.ISafety.PrivateLockReminder.ON:// 0x0002a200|0x01;
                return "ISafety.PRIVATE_LOCK_REMINDER|PrivateLockReminder.ON";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_REMINDER
                    |ecarx.carext.vehicle.module.v446k.ISafety.PrivateLockReminder.REMINDER:// 0x0002a200|0x02;
                return "ISafety.PRIVATE_LOCK_REMINDER|PrivateLockReminder.REMINDER";
            case ecarx.carext.vehicle.module.v446k.ISafety.PRIVATE_LOCK_REMINDER
                    |ecarx.carext.vehicle.module.v446k.ISafety.PrivateLockReminder.OFF:// 0x0002a200|0xff;
                return "ISafety.PRIVATE_LOCK_REMINDER|PrivateLockReminder.OFF";
            case ecarx.carext.vehicle.module.IAmbienceLight.AMBIENCE_LIGHT_VOICE:// 0x00016300;
                return "IAmbienceLight.AMBIENCE_LIGHT_VOICE";
            case ecarx.carext.vehicle.module.IAmbienceLight.AMBIENCE_LIGHT_INTENSITY:// 0x0001a800;
                return "IAmbienceLight.AMBIENCE_LIGHT_INTENSITY";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR:// 0x00026000;
                return "IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM1:// 0x00026000|0x01;
                return "IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR|AmbienceLightThemeColor.ITEM1";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM2:// 0x00026000|0x02;
                return "IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR|AmbienceLightThemeColor.ITEM2";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM3:// 0x00026000|0x03;
                return "IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR|AmbienceLightThemeColor.ITEM3";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM4:// 0x00026000|0x04;
                return "IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR|AmbienceLightThemeColor.ITEM4";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM5:// 0x00026000|0x05;
                return "IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR|AmbienceLightThemeColor.ITEM5";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM6:// 0x00026000|0x06;
                return "IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR|AmbienceLightThemeColor.ITEM6";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.AmbienceLightThemeColor.ITEM7:// 0x00026000|0x07;
                return "IAmbienceLight.AMBIENCE_LIGHT_THEME_COLOR|AmbienceLightThemeColor.ITEM7";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.AMBIENCE_LIGHT_MUSIC_SHOWMODE:// 0x00026300;
                return "IAmbienceLight.AMBIENCE_LIGHT_MUSIC_SHOWMODE";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED:// 0x00031e00;
                return "IAmbienceLight.VR_ACTIVATED";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_LEFT_SEAT:// 0x00031e00|0x01;
                return "IAmbienceLight.VR_ACTIVATED|VRActivated.ACTIVATED_ON_LEFT_SEAT";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_RIGHT_SEAT:// 0x00031e00|0x02;
                return "IAmbienceLight.VR_ACTIVATED|VRActivated.ACTIVATED_ON_RIGHT_SEAT";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.IS_BROADCASTING:// 0x00031e00|0x03;
                return "IAmbienceLight.VR_ACTIVATED|VRActivated.IS_BROADCASTING";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_FRONT_LEFT_SEAT:// 0x00031e00|0x04;
                return "IAmbienceLight.VR_ACTIVATED|VRActivated.ACTIVATED_ON_FRONT_LEFT_SEAT";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_FRONT_RIGHT_SEAT:// 0x00031e00|0x05;
                return "IAmbienceLight.VR_ACTIVATED|VRActivated.ACTIVATED_ON_FRONT_RIGHT_SEAT";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_LEFT_REAR_SEAT:// 0x00031e00|0x06;
                return "IAmbienceLight.VR_ACTIVATED|VRActivated.ACTIVATED_ON_LEFT_REAR_SEAT";
            case ecarx.carext.vehicle.module.v446k.IAmbienceLight.VR_ACTIVATED
                    |ecarx.carext.vehicle.module.v446k.IAmbienceLight.VRActivated.ACTIVATED_ON_RIGHT_REAR_SEAT:// 0x00031e00|0x07;
                return "IAmbienceLight.VR_ACTIVATED|VRActivated.ACTIVATED_ON_RIGHT_REAR_SEAT";
            case ecarx.carext.vehicle.module.IPAS.RCTA_WARNING_VOLUME:// 0x0000f300;
                return "IPAS.RCTA_WARNING_VOLUME";
            case ecarx.carext.vehicle.module.IPAS.RCTA_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IPAS.RCTAWarningVolumeMode.LOW:// 0x0000f300|0x01;
                return "IPAS.RCTA_WARNING_VOLUME|RCTAWarningVolumeMode.LOW";
            case ecarx.carext.vehicle.module.IPAS.RCTA_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IPAS.RCTAWarningVolumeMode.MID:// 0x0000f300|0x02;
                return "IPAS.RCTA_WARNING_VOLUME|RCTAWarningVolumeMode.MID";
            case ecarx.carext.vehicle.module.IPAS.RCTA_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IPAS.RCTAWarningVolumeMode.HIGH:// 0x0000f300|0x03;
                return "IPAS.RCTA_WARNING_VOLUME|RCTAWarningVolumeMode.HIGH";
            case ecarx.carext.vehicle.module.IPAS.RCTA_WARNING_VOLUME
                    |ecarx.carext.vehicle.module.IPAS.RCTAWarningVolumeMode.OFF:// 0x0000f300|0xff;
                return "IPAS.RCTA_WARNING_VOLUME|RCTAWarningVolumeMode.OFF";
            case ecarx.carext.vehicle.module.IPAS.PEB_SWITCH:// 0x0000f400;
                return "IPAS.PEB_SWITCH";
            case ecarx.carext.vehicle.module.IPAS.HPA_CAR_CONFIG_HMI_CAR_STATE:// 0x00019200;
                return "IPAS.HPA_CAR_CONFIG_HMI_CAR_STATE";
            case ecarx.carext.vehicle.module.v446k.IPAS.DLC_SWITCH:// 0x0001e300;
                return "IPAS.DLC_SWITCH";
            case ecarx.carext.vehicle.module.v446k.IPAS.DLC_AUTHORIZED_TIME:// 0x0001e400;
                return "IPAS.DLC_AUTHORIZED_TIME";
            case ecarx.carext.vehicle.module.v446k.IPAS.PAS_MUTE:// 0x0002af00;
                return "IPAS.PAS_MUTE";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_INNER_LEFT:// 0x0002b100;
                return "IPAS.RADAR_FRONT_INNER_LEFT";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_INNER_RIGHT:// 0x0002b200;
                return "IPAS.RADAR_FRONT_INNER_RIGHT";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_OUT_LEFT:// 0x0002b300;
                return "IPAS.RADAR_FRONT_OUT_LEFT";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_OUT_RIGHT:// 0x0002b400;
                return "IPAS.RADAR_FRONT_OUT_RIGHT";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_LEFT_SIDE:// 0x0002b500;
                return "IPAS.RADAR_FRONT_LEFT_SIDE";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_FRONT_RIGHT_SIDE:// 0x0002b600;
                return "IPAS.RADAR_FRONT_RIGHT_SIDE";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_LEFT_SIDE:// 0x0002b700;
                return "IPAS.RADAR_REAR_LEFT_SIDE";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_RIGHT_SIDE:// 0x0002b800;
                return "IPAS.RADAR_REAR_RIGHT_SIDE";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_OUT_LEFT:// 0x0002b900;
                return "IPAS.RADAR_REAR_OUT_LEFT";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_OUT_RIGHT:// 0x0002ba00;
                return "IPAS.RADAR_REAR_OUT_RIGHT";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_INNER_LEFT:// 0x0002bb00;
                return "IPAS.RADAR_REAR_INNER_LEFT";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_REAR_INNER_RIGHT:// 0x0002bc00;
                return "IPAS.RADAR_REAR_INNER_RIGHT";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_MAX_DISTANCE:// 0x0002bd00;
                return "IPAS.RADAR_MAX_DISTANCE";
            case ecarx.carext.vehicle.module.v446k.IPAS.RADAR_MIN_DISTANCE:// 0x0002be00;
                return "IPAS.RADAR_MIN_DISTANCE";
            case ecarx.carext.vehicle.module.ITripData.TRIP_DI_AVG_SPEED:// 0x00008b00;
                return "ITripData.TRIP_DI_AVG_SPEED";
            case ecarx.carext.vehicle.module.ITripData.TRIP_DI_TOTAL_DISTANCE:// 0x00008c00;
                return "ITripData.TRIP_DI_TOTAL_DISTANCE";
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_RESET:// 0x00008d00;
                return "ITripData.TRIP_FUNC_RESET";
            case ecarx.carext.vehicle.module.ITripData.TRIP_DI_AVG_ELC_CONSUMPTION_ARRAY:// 0x00008f00;
                return "ITripData.TRIP_DI_AVG_ELC_CONSUMPTION_ARRAY";
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_AUTO_RESET_OPTION:// 0x00009200;
                return "ITripData.TRIP_FUNC_AUTO_RESET_OPTION";
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_AUTO_RESET_OPTION
                    |ecarx.carext.vehicle.module.ITripData.AutoResetTripOption.AUTO_RESET_OPTION_4_HOURS:// 0x00009200|0x01;
                return "ITripData.TRIP_FUNC_AUTO_RESET_OPTION|AutoResetTripOption.AUTO_RESET_OPTION_4_HOURS";
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_AUTO_RESET_OPTION
                    |ecarx.carext.vehicle.module.ITripData.AutoResetTripOption.AUTO_RESET_OPTION_CHARGING:// 0x00009200|0x02;
                return "ITripData.TRIP_FUNC_AUTO_RESET_OPTION|AutoResetTripOption.AUTO_RESET_OPTION_CHARGING";
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_AUTO_RESET_OPTION
                    |ecarx.carext.vehicle.module.ITripData.AutoResetTripOption.AUTO_RESET_OPTION_PARKING_OIL:// 0x00009200|0x03;
                return "ITripData.TRIP_FUNC_AUTO_RESET_OPTION|AutoResetTripOption.AUTO_RESET_OPTION_PARKING_OIL";
            case ecarx.carext.vehicle.module.ITripData.TRIP_FUNC_AUTO_RESET_OPTION
                    |ecarx.carext.vehicle.module.ITripData.AutoResetTripOption.AUTO_RESET_OPTION_PARKING:// 0x00009200|0x04;
                return "ITripData.TRIP_FUNC_AUTO_RESET_OPTION|AutoResetTripOption.AUTO_RESET_OPTION_PARKING";
            case ecarx.carext.vehicle.module.ITripData.TRIP_DI_TOTAL_DURATION:// 0x0000e100;
                return "ITripData.TRIP_DI_TOTAL_DURATION";
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_DOT_INTERVAL:// 0x00017200;
                return "ITripData.TRIP_DOT_INTERVAL";
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ED_DRIVING_ENERGY_FLOW:// 0x00017300;
                return "ITripData.TRIP_ED_DRIVING_ENERGY_FLOW";
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ED_CLIMATE_ENERGY_FLOW:// 0x00017400;
                return "ITripData.TRIP_ED_CLIMATE_ENERGY_FLOW";
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ED_BATTERY_ENERGY_FLOW:// 0x00017500;
                return "ITripData.TRIP_ED_BATTERY_ENERGY_FLOW";
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ED_OTHER_ENERGY_FLOW:// 0x00017600;
                return "ITripData.TRIP_ED_OTHER_ENERGY_FLOW";
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_DI_AVG_FUEL_CONSUMPTION_ARRAY:// 0x00017900;
                return "ITripData.TRIP_DI_AVG_FUEL_CONSUMPTION_ARRAY";
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE:// 0x00032900;
                return "ITripData.TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE";
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.ITripData.EnergyCurveDisplayMode.FUEL_CONSUMPTION:// 0x00032900|0x01;
                return "ITripData.TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE|EnergyCurveDisplayMode.FUEL_CONSUMPTION";
            case ecarx.carext.vehicle.module.v446k.ITripData.TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE
                    |ecarx.carext.vehicle.module.v446k.ITripData.EnergyCurveDisplayMode.ELECTRICITY_CONSUMPTION:// 0x00032900|0x02;
                return "ITripData.TRIP_ENERGY_CONSUMPTION_CURVE_DISPLAY_MODE|EnergyCurveDisplayMode.ELECTRICITY_CONSUMPTION";
            case ecarx.carext.vehicle.module.IADAS.AUTONOMOUS_EMERGENCY_BRAKING:// 0x00005f00;
                return "IADAS.AUTONOMOUS_EMERGENCY_BRAKING";
            case ecarx.carext.vehicle.module.IADAS.FORWARD_COLLISION_WARN_SNVT:// 0x00007200;
                return "IADAS.FORWARD_COLLISION_WARN_SNVT";
            case ecarx.carext.vehicle.module.IADAS.FORWARD_COLLISION_WARN_SNVT
                    |ecarx.carext.vehicle.module.IADAS.ForwardCollisionWarnSnvtyMode.LOW:// 0x00007200|0x01;
                return "IADAS.FORWARD_COLLISION_WARN_SNVT|ForwardCollisionWarnSnvtyMode.LOW";
            case ecarx.carext.vehicle.module.IADAS.FORWARD_COLLISION_WARN_SNVT
                    |ecarx.carext.vehicle.module.IADAS.ForwardCollisionWarnSnvtyMode.NORMAL:// 0x00007200|0x02;
                return "IADAS.FORWARD_COLLISION_WARN_SNVT|ForwardCollisionWarnSnvtyMode.NORMAL";
            case ecarx.carext.vehicle.module.IADAS.FORWARD_COLLISION_WARN_SNVT
                    |ecarx.carext.vehicle.module.IADAS.ForwardCollisionWarnSnvtyMode.HIGH:// 0x00007200|0x03;
                return "IADAS.FORWARD_COLLISION_WARN_SNVT|ForwardCollisionWarnSnvtyMode.HIGH";
            case ecarx.carext.vehicle.module.IADAS.FORWARD_COLLISION_WARN_SNVT
                    |ecarx.carext.vehicle.module.IADAS.ForwardCollisionWarnSnvtyMode.OFF:// 0x00007200|0xff;
                return "IADAS.FORWARD_COLLISION_WARN_SNVT|ForwardCollisionWarnSnvtyMode.OFF";
            case ecarx.carext.vehicle.module.IADAS.DOOR_OPEN_WARN_ACTIVE:// 0x00007400;
                return "IADAS.DOOR_OPEN_WARN_ACTIVE";
            case ecarx.carext.vehicle.module.IADAS.REAR_COLLISION_WARNING:// 0x00007600;
                return "IADAS.REAR_COLLISION_WARNING";
            case ecarx.carext.vehicle.module.IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE:// 0x00007a00;
                return "IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE";
            case ecarx.carext.vehicle.module.IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE
                    |ecarx.carext.vehicle.module.IADAS.IntelligentDrivingMode.ACC_MODE:// 0x00007a00|0x01;
                return "IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE|IntelligentDrivingMode.ACC_MODE";
            case ecarx.carext.vehicle.module.IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE
                    |ecarx.carext.vehicle.module.IADAS.IntelligentDrivingMode.ICC_MODE:// 0x00007a00|0x02;
                return "IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE|IntelligentDrivingMode.ICC_MODE";
            case ecarx.carext.vehicle.module.IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE
                    |ecarx.carext.vehicle.module.IADAS.IntelligentDrivingMode.HNOA_MODE:// 0x00007a00|0x04;
                return "IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE|IntelligentDrivingMode.HNOA_MODE";
            case ecarx.carext.vehicle.module.IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE
                    |ecarx.carext.vehicle.module.IADAS.IntelligentDrivingMode.OFF:// 0x00007a00|0xff;
                return "IADAS.INTELLIGENT_DRIVING_ASSISTANCE_MODE|IntelligentDrivingMode.OFF";
            case ecarx.carext.vehicle.module.IADAS.AUTO_LANE_CHANGE:// 0x00007e00;
                return "IADAS.AUTO_LANE_CHANGE";
            case ecarx.carext.vehicle.module.IADAS.DRIVER_PERFOR_SUPPORT:// 0x00008300;
                return "IADAS.DRIVER_PERFOR_SUPPORT";
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL:// 0x0000a500;
                return "IADAS.STEERING_ASSISTANCE_LEVEL";
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL
                    |ecarx.carext.vehicle.module.IADAS.SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_HEAVY:// 0x0000a500|0x01;
                return "IADAS.STEERING_ASSISTANCE_LEVEL|SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_HEAVY";
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL
                    |ecarx.carext.vehicle.module.IADAS.SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_MEDIUM:// 0x0000a500|0x02;
                return "IADAS.STEERING_ASSISTANCE_LEVEL|SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_MEDIUM";
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL
                    |ecarx.carext.vehicle.module.IADAS.SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_SO:// 0x0000a500|0x03;
                return "IADAS.STEERING_ASSISTANCE_LEVEL|SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_SO";
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL
                    |ecarx.carext.vehicle.module.IADAS.SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_AUTO:// 0x0000a500|0x04;
                return "IADAS.STEERING_ASSISTANCE_LEVEL|SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_AUTO";
            case ecarx.carext.vehicle.module.IADAS.STEERING_ASSISTANCE_LEVEL
                    |ecarx.carext.vehicle.module.IADAS.SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_OFF:// 0x0000a500|0xff;
                return "IADAS.STEERING_ASSISTANCE_LEVEL|SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_OFF";
            case ecarx.carext.vehicle.module.IADAS.LANE_DEPARTURE_WARNING:// 0x0000ab00;
                return "IADAS.LANE_DEPARTURE_WARNING";
            case ecarx.carext.vehicle.module.IADAS.ACC_WITH_TSR:// 0x0000ed00;
                return "IADAS.ACC_WITH_TSR";
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID:// 0x0000ee00;
                return "IADAS.LANE_KEEPING_AID";
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID_WARNING:// 0x0000ef00;
                return "IADAS.LANE_KEEPING_AID_WARNING";
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID_WARNING
                    |ecarx.carext.vehicle.module.IADAS.LaneKeepingAidWarningMode.SOUND:// 0x0000ef00|0x01;
                return "IADAS.LANE_KEEPING_AID_WARNING|LaneKeepingAidWarningMode.SOUND";
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID_WARNING
                    |ecarx.carext.vehicle.module.IADAS.LaneKeepingAidWarningMode.HAPTIC:// 0x0000ef00|0x02;
                return "IADAS.LANE_KEEPING_AID_WARNING|LaneKeepingAidWarningMode.HAPTIC";
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID_WARNING
                    |ecarx.carext.vehicle.module.IADAS.LaneKeepingAidWarningMode.SOUND_HAPTIC:// 0x0000ef00|0x03;
                return "IADAS.LANE_KEEPING_AID_WARNING|LaneKeepingAidWarningMode.SOUND_HAPTIC";
            case ecarx.carext.vehicle.module.IADAS.LANE_KEEPING_AID_WARNING
                    |ecarx.carext.vehicle.module.IADAS.LaneKeepingAidWarningMode.OFF:// 0x0000ef00|0x04;
                return "IADAS.LANE_KEEPING_AID_WARNING|LaneKeepingAidWarningMode.OFF";
            case ecarx.carext.vehicle.module.IADAS.REAR_CROSS_TRAFFIC_ALERT:// 0x0000f000;
                return "IADAS.REAR_CROSS_TRAFFIC_ALERT";
            case ecarx.carext.vehicle.module.IADAS.LANE_CHANGE_WARNING_MODE:// 0x0000f100;
                return "IADAS.LANE_CHANGE_WARNING_MODE";
            case ecarx.carext.vehicle.module.IADAS.LANE_CHANGE_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.LaneChangeWarningMode.VISUAL:// 0x0000f100|0x01;
                return "IADAS.LANE_CHANGE_WARNING_MODE|LaneChangeWarningMode.VISUAL";
            case ecarx.carext.vehicle.module.IADAS.LANE_CHANGE_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.LaneChangeWarningMode.SOUND:// 0x0000f100|0x02;
                return "IADAS.LANE_CHANGE_WARNING_MODE|LaneChangeWarningMode.SOUND";
            case ecarx.carext.vehicle.module.IADAS.LANE_CHANGE_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.LaneChangeWarningMode.VISUAL_SOUND:// 0x0000f100|0x03;
                return "IADAS.LANE_CHANGE_WARNING_MODE|LaneChangeWarningMode.VISUAL_SOUND";
            case ecarx.carext.vehicle.module.IADAS.LANE_CHANGE_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.LaneChangeWarningMode.OFF:// 0x0000f100|0xff;
                return "IADAS.LANE_CHANGE_WARNING_MODE|LaneChangeWarningMode.OFF";
            case ecarx.carext.vehicle.module.IADAS.EMERGENCY_LANE_KEEP_AID:// 0x0001c200;
                return "IADAS.EMERGENCY_LANE_KEEP_AID";
            case ecarx.carext.vehicle.module.IADAS.SPEED_LIMIT_WARNING_MODE:// 0x0001c300;
                return "IADAS.SPEED_LIMIT_WARNING_MODE";
            case ecarx.carext.vehicle.module.IADAS.SPEED_LIMIT_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.SpeedLimitationMode.VISUAL:// 0x0001c300|0x01;
                return "IADAS.SPEED_LIMIT_WARNING_MODE|SpeedLimitationMode.VISUAL";
            case ecarx.carext.vehicle.module.IADAS.SPEED_LIMIT_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.SpeedLimitationMode.SOUND:// 0x0001c300|0x02;
                return "IADAS.SPEED_LIMIT_WARNING_MODE|SpeedLimitationMode.SOUND";
            case ecarx.carext.vehicle.module.IADAS.SPEED_LIMIT_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.SpeedLimitationMode.VISUAL_SOUND:// 0x0001c300|0x03;
                return "IADAS.SPEED_LIMIT_WARNING_MODE|SpeedLimitationMode.VISUAL_SOUND";
            case ecarx.carext.vehicle.module.IADAS.SPEED_LIMIT_WARNING_MODE
                    |ecarx.carext.vehicle.module.IADAS.SpeedLimitationMode.CLOSE:// 0x0001c300|0xff;
                return "IADAS.SPEED_LIMIT_WARNING_MODE|SpeedLimitationMode.CLOSE";
            case ecarx.carext.vehicle.module.v446k.IADAS.LANE_CHANGE_STRATEGY:// 0x0001d700;
                return "IADAS.LANE_CHANGE_STRATEGY";
            case ecarx.carext.vehicle.module.v446k.IADAS.LANE_CHANGE_STRATEGY
                    |ecarx.carext.vehicle.module.v446k.IADAS.LaneChangeStrategy.GENTLE:// 0x0001d700|0x01;
                return "IADAS.LANE_CHANGE_STRATEGY|LaneChangeStrategy.GENTLE";
            case ecarx.carext.vehicle.module.v446k.IADAS.LANE_CHANGE_STRATEGY
                    |ecarx.carext.vehicle.module.v446k.IADAS.LaneChangeStrategy.STANDARD:// 0x0001d700|0x02;
                return "IADAS.LANE_CHANGE_STRATEGY|LaneChangeStrategy.STANDARD";
            case ecarx.carext.vehicle.module.v446k.IADAS.LANE_CHANGE_STRATEGY
                    |ecarx.carext.vehicle.module.v446k.IADAS.LaneChangeStrategy.RADICAL:// 0x0001d700|0x03;
                return "IADAS.LANE_CHANGE_STRATEGY|LaneChangeStrategy.RADICAL";
            case ecarx.carext.vehicle.module.v446k.IADAS.LANE_CHANGE_STRATEGY
                    |ecarx.carext.vehicle.module.v446k.IADAS.LaneChangeStrategy.OFF:// 0x0001d700|0xff;
                return "IADAS.LANE_CHANGE_STRATEGY|LaneChangeStrategy.OFF";
            case ecarx.carext.vehicle.module.v446k.IADAS.VOICE_BROADCAST_MODE:// 0x0001da00;
                return "IADAS.VOICE_BROADCAST_MODE";
            case ecarx.carext.vehicle.module.v446k.IADAS.VOICE_BROADCAST_MODE
                    |ecarx.carext.vehicle.module.v446k.IADAS.VoiceBroadcastMode.DETAILED:// 0x0001da00|0x01;
                return "IADAS.VOICE_BROADCAST_MODE|VoiceBroadcastMode.DETAILED";
            case ecarx.carext.vehicle.module.v446k.IADAS.VOICE_BROADCAST_MODE
                    |ecarx.carext.vehicle.module.v446k.IADAS.VoiceBroadcastMode.STREAMLINED:// 0x0001da00|0x02;
                return "IADAS.VOICE_BROADCAST_MODE|VoiceBroadcastMode.STREAMLINED";
            case ecarx.carext.vehicle.module.v446k.IADAS.VOICE_BROADCAST_MODE
                    |ecarx.carext.vehicle.module.v446k.IADAS.VoiceBroadcastMode.OFF:// 0x0001da00|0xff;
                return "IADAS.VOICE_BROADCAST_MODE|VoiceBroadcastMode.OFF";
            case ecarx.carext.vehicle.module.v446k.IADAS.SPEED_LIMIT_WARNING_OFFSET_VALUE:// 0x0001dd00;
                return "IADAS.SPEED_LIMIT_WARNING_OFFSET_VALUE";
            case ecarx.carext.vehicle.module.v446k.IADAS.DRIVER_PERFORMANCE_AUTHORIZED_TIME:// 0x0001df00;
                return "IADAS.DRIVER_PERFORMANCE_AUTHORIZED_TIME";
            case ecarx.carext.vehicle.module.v446k.IADAS.DPS_UPDATE_AUTHORIZED_TIME:// 0x00022300;
                return "IADAS.DPS_UPDATE_AUTHORIZED_TIME";
            case ecarx.carext.vehicle.module.v446k.IADAS.DCL_UPDATE_AUTHORIZED_TIME:// 0x00022700;
                return "IADAS.DCL_UPDATE_AUTHORIZED_TIME";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_LANE_CHANGE_CALLBACK:// 0x00027200;
                return "IADAS.SOUND_LANE_CHANGE_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_CAN_ACTIVATE_CALLBACK:// 0x00027300;
                return "IADAS.SOUND_NOA_CAN_ACTIVATE_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_STATUS_CALLBACK:// 0x00027400;
                return "IADAS.SOUND_NOA_STATUS_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_EXIT_ALERT_CALLBACK:// 0x00027500;
                return "IADAS.SOUND_NOA_EXIT_ALERT_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_FIRST_ACTIVATION_CALLBACK:// 0x00027600;
                return "IADAS.SOUND_FIRST_ACTIVATION_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_ICC_CANNOT_ACTIVATE_CALLBACK:// 0x00027700;
                return "IADAS.SOUND_ICC_CANNOT_ACTIVATE_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_CAREFUL_DRIVE_CALLBACK:// 0x00027800;
                return "IADAS.SOUND_CAREFUL_DRIVE_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_EYES_OFF_WARN_CALLBACK:// 0x00027900;
                return "IADAS.SOUND_EYES_OFF_WARN_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_ACC_CANNOT_ACTIVATE_CALLBACK:// 0x00027a00;
                return "IADAS.SOUND_ACC_CANNOT_ACTIVATE_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_ACC_STATUS_CALLBACK:// 0x00027b00;
                return "IADAS.SOUND_ACC_STATUS_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_ICC_STATUS_CALLBACK:// 0x00027c00;
                return "IADAS.SOUND_ICC_STATUS_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_CANNOT_ACTIVATE_CALLBACK:// 0x00027d00;
                return "IADAS.SOUND_NOA_CANNOT_ACTIVATE_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_TAKE_OVER_CALLBACK:// 0x00027e00;
                return "IADAS.SOUND_NOA_TAKE_OVER_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_NOA_REGULAR_CALLBACK:// 0x00027f00;
                return "IADAS.SOUND_NOA_REGULAR_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_LANE_CHANGE_EXTEND_CALLBACK:// 0x00028000;
                return "IADAS.SOUND_LANE_CHANGE_EXTEND_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.SOUND_HAND_OFF_CALLBACK:// 0x00028100;
                return "IADAS.SOUND_HAND_OFF_CALLBACK";
            case ecarx.carext.vehicle.module.v446k.IADAS.RCTA_WARNING_LEFT:// 0x00032700;
                return "IADAS.RCTA_WARNING_LEFT";
            case ecarx.carext.vehicle.module.v446k.IADAS.RCTA_WARNING_RIGHT:// 0x00032800;
                return "IADAS.RCTA_WARNING_RIGHT";
            case ecarx.carext.vehicle.module.IHvac.TEMPERATURE_INCREMENTAL_ADJUST:// 0x00002c00;
                return "IHvac.TEMPERATURE_INCREMENTAL_ADJUST";
            case ecarx.carext.vehicle.module.IHvac.FAN_SPEED_INCREMENTAL_ADJUST:// 0x00002d00;
                return "IHvac.FAN_SPEED_INCREMENTAL_ADJUST";
            case ecarx.carext.vehicle.module.IHvac.CIRCULATION:// 0x00003600;
                return "IHvac.CIRCULATION";
            case ecarx.carext.vehicle.module.IHvac.CIRCULATION
                    |ecarx.carext.vehicle.module.IHvac.CirculationMode.INTERNAL:// 0x00003600|0x01;
                return "IHvac.CIRCULATION|CirculationMode.INTERNAL";
            case ecarx.carext.vehicle.module.IHvac.CIRCULATION
                    |ecarx.carext.vehicle.module.IHvac.CirculationMode.EXTERNAL:// 0x00003600|0x02;
                return "IHvac.CIRCULATION|CirculationMode.EXTERNAL";
            case ecarx.carext.vehicle.module.IHvac.CIRCULATION
                    |ecarx.carext.vehicle.module.IHvac.CirculationMode.AUTO:// 0x00003600|0x03;
                return "IHvac.CIRCULATION|CirculationMode.AUTO";
            case ecarx.carext.vehicle.module.IHvac.CIRCULATION
                    |ecarx.carext.vehicle.module.IHvac.CirculationMode.OFF:// 0x00003600|0xff;
                return "IHvac.CIRCULATION|CirculationMode.OFF";
            case ecarx.carext.vehicle.module.IHvac.AUTO_STEERING_WHEEL_HEAT_SWITCH:// 0x00004400;
                return "IHvac.AUTO_STEERING_WHEEL_HEAT_SWITCH";
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE:// 0x0000c800;
                return "IHvac.DIRECTION_MODE";
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_FOCUS:// 0x0000c800|0x01;
                return "IHvac.DIRECTION_MODE|DirectionMode.DIRECTION_MODE_FOCUS";
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_AVOID:// 0x0000c800|0x02;
                return "IHvac.DIRECTION_MODE|DirectionMode.DIRECTION_MODE_AVOID";
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_CUSTOM:// 0x0000c800|0x03;
                return "IHvac.DIRECTION_MODE|DirectionMode.DIRECTION_MODE_CUSTOM";
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_SWING:// 0x0000c800|0x04;
                return "IHvac.DIRECTION_MODE|DirectionMode.DIRECTION_MODE_SWING";
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_ADAPTIVE:// 0x0000c800|0x05;
                return "IHvac.DIRECTION_MODE|DirectionMode.DIRECTION_MODE_ADAPTIVE";
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_MANUAL:// 0x0000c800|0x06;
                return "IHvac.DIRECTION_MODE|DirectionMode.DIRECTION_MODE_MANUAL";
            case ecarx.carext.vehicle.module.IHvac.DIRECTION_MODE
                    |ecarx.carext.vehicle.module.IHvac.DirectionMode.DIRECTION_MODE_OFF:// 0x0000c800|0xff;
                return "IHvac.DIRECTION_MODE|DirectionMode.DIRECTION_MODE_OFF";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_CLEAN_ZONE:// 0x00020200;
                return "IHvac.HVAC_CLEAN_ZONE";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_CLEAN_ZONE
                    |ecarx.carext.vehicle.module.v446k.IHvac.CleanZoneState.ON:// 0x00020200|0x01;
                return "IHvac.HVAC_CLEAN_ZONE|CleanZoneState.ON";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_CLEAN_ZONE
                    |ecarx.carext.vehicle.module.v446k.IHvac.CleanZoneState.OFF:// 0x00020200|0x02;
                return "IHvac.HVAC_CLEAN_ZONE|CleanZoneState.OFF";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_CLEAN_ZONE
                    |ecarx.carext.vehicle.module.v446k.IHvac.CleanZoneState.NONE:// 0x00020200|0xff;
                return "IHvac.HVAC_CLEAN_ZONE|CleanZoneState.NONE";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE:// 0x00020300;
                return "IHvac.HVAC_AIR_FRAGRANCE";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT:// 0x00020400;
                return "IHvac.HVAC_AIR_FRAGRANCE_SLOT";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceSlot.AIR_FRAGRANCE_SLOT_1:// 0x00020400|0x01;
                return "IHvac.HVAC_AIR_FRAGRANCE_SLOT|AirFragranceSlot.AIR_FRAGRANCE_SLOT_1";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceSlot.AIR_FRAGRANCE_SLOT_2:// 0x00020400|0x02;
                return "IHvac.HVAC_AIR_FRAGRANCE_SLOT|AirFragranceSlot.AIR_FRAGRANCE_SLOT_2";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceSlot.AIR_FRAGRANCE_SLOT_3:// 0x00020400|0x04;
                return "IHvac.HVAC_AIR_FRAGRANCE_SLOT|AirFragranceSlot.AIR_FRAGRANCE_SLOT_3";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceSlot.AIR_FRAGRANCE_SLOT_4:// 0x00020400|0x10;
                return "IHvac.HVAC_AIR_FRAGRANCE_SLOT|AirFragranceSlot.AIR_FRAGRANCE_SLOT_4";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_SLOT
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceSlot.AIR_FRAGRANCE_SLOT_5:// 0x00020400|0x20;
                return "IHvac.HVAC_AIR_FRAGRANCE_SLOT|AirFragranceSlot.AIR_FRAGRANCE_SLOT_5";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LEVEL:// 0x00020500;
                return "IHvac.HVAC_AIR_FRAGRANCE_LEVEL";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LEVEL
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceLevel.AIR_FRAGRANCE_LEVEL_1:// 0x00020500|0x01;
                return "IHvac.HVAC_AIR_FRAGRANCE_LEVEL|AirFragranceLevel.AIR_FRAGRANCE_LEVEL_1";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LEVEL
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceLevel.AIR_FRAGRANCE_LEVEL_2:// 0x00020500|0x02;
                return "IHvac.HVAC_AIR_FRAGRANCE_LEVEL|AirFragranceLevel.AIR_FRAGRANCE_LEVEL_2";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LEVEL
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceLevel.AIR_FRAGRANCE_LEVEL_3:// 0x00020500|0x03;
                return "IHvac.HVAC_AIR_FRAGRANCE_LEVEL|AirFragranceLevel.AIR_FRAGRANCE_LEVEL_3";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LEVEL
                    |ecarx.carext.vehicle.module.v446k.IHvac.AirFragranceLevel.AIR_FRAGRANCE_LEVEL_OFF:// 0x00020500|0xff;
                return "IHvac.HVAC_AIR_FRAGRANCE_LEVEL|AirFragranceLevel.AIR_FRAGRANCE_LEVEL_OFF";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FRAGRANCE_MARGIN:// 0x00020600;
                return "IHvac.HVAC_FRAGRANCE_MARGIN";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AUTO_DRY:// 0x00020700;
                return "IHvac.HVAC_AUTO_DRY";
            case ecarx.carext.vehicle.module.v446k.IHvac.AIR_FRAGRANCE_TYPE_ID:// 0x00022100;
                return "IHvac.AIR_FRAGRANCE_TYPE_ID";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_AIR_FRAGRANCE_LOW:// 0x00022a00;
                return "IHvac.HVAC_AIR_FRAGRANCE_LOW";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY:// 0x00024800;
                return "IHvac.HVAC_FAULT_NOTIFY";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.SEAT_HEAT:// 0x00024800|0x01;
                return "IHvac.HVAC_FAULT_NOTIFY|HvacFaultNotify.SEAT_HEAT";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.SEAT_VENTILATION:// 0x00024800|0x02;
                return "IHvac.HVAC_FAULT_NOTIFY|HvacFaultNotify.SEAT_VENTILATION";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.WHEEL_HEAT:// 0x00024800|0x03;
                return "IHvac.HVAC_FAULT_NOTIFY|HvacFaultNotify.WHEEL_HEAT";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.SEAT_HEAT_AUTO:// 0x00024800|0x04;
                return "IHvac.HVAC_FAULT_NOTIFY|HvacFaultNotify.SEAT_HEAT_AUTO";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.SEAT_VENTILATION_AUTO:// 0x00024800|0x05;
                return "IHvac.HVAC_FAULT_NOTIFY|HvacFaultNotify.SEAT_VENTILATION_AUTO";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.DEFROST_REAR:// 0x00024800|0x06;
                return "IHvac.HVAC_FAULT_NOTIFY|HvacFaultNotify.DEFROST_REAR";
            case ecarx.carext.vehicle.module.v446k.IHvac.HVAC_FAULT_NOTIFY
                    |ecarx.carext.vehicle.module.v446k.IHvac.HvacFaultNotify.DEFROST_REARVIEW_MIRROR:// 0x00024800|0x07;
                return "IHvac.HVAC_FAULT_NOTIFY|HvacFaultNotify.DEFROST_REARVIEW_MIRROR";
            case ecarx.carext.vehicle.module.v446k.IHvac.ELECTRONIC_AIR_OUTLET_SWITCH:// 0x00024c00;
                return "IHvac.ELECTRONIC_AIR_OUTLET_SWITCH";
            case ecarx.carext.vehicle.module.v446k.IHvac.ELECTRONIC_AIR_OUTLET_DIRECTION_ADJUST:// 0x00024d00;
                return "IHvac.ELECTRONIC_AIR_OUTLET_DIRECTION_ADJUST";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE:// 0x00026800;
                return "IHvac.FAN_DIRECTION_AUTO_STATE";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_FACE:// 0x00026800|0x01;
                return "IHvac.FAN_DIRECTION_AUTO_STATE|FanDirectionAuto.BLOWING_MODE_FACE";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_LEG:// 0x00026800|0x02;
                return "IHvac.FAN_DIRECTION_AUTO_STATE|FanDirectionAuto.BLOWING_MODE_LEG";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_FACE_AND_LEG:// 0x00026800|0x03;
                return "IHvac.FAN_DIRECTION_AUTO_STATE|FanDirectionAuto.BLOWING_MODE_FACE_AND_LEG";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_FRONT_WINDOW:// 0x00026800|0x04;
                return "IHvac.FAN_DIRECTION_AUTO_STATE|FanDirectionAuto.BLOWING_MODE_FRONT_WINDOW";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_FACE_AND_FRONT_WIND:// 0x00026800|0x05;
                return "IHvac.FAN_DIRECTION_AUTO_STATE|FanDirectionAuto.BLOWING_MODE_FACE_AND_FRONT_WIND";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_LEG_AND_FRONT_WINDO:// 0x00026800|0x06;
                return "IHvac.FAN_DIRECTION_AUTO_STATE|FanDirectionAuto.BLOWING_MODE_LEG_AND_FRONT_WINDO";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_ALL:// 0x00026800|0x07;
                return "IHvac.FAN_DIRECTION_AUTO_STATE|FanDirectionAuto.BLOWING_ALL";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_AUTO_SWITCH:// 0x00026800|0x08;
                return "IHvac.FAN_DIRECTION_AUTO_STATE|FanDirectionAuto.BLOWING_MODE_AUTO_SWITCH";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_DIRECTION_AUTO_STATE
                    |ecarx.carext.vehicle.module.v446k.IHvac.FanDirectionAuto.BLOWING_MODE_OFF:// 0x00026800|0xff;
                return "IHvac.FAN_DIRECTION_AUTO_STATE|FanDirectionAuto.BLOWING_MODE_OFF";
            case ecarx.carext.vehicle.module.v446k.IHvac.FAN_AUTO_STATUS:// 0x00026900;
                return "IHvac.FAN_AUTO_STATUS";
            case ecarx.carext.vehicle.module.v446k.IHvac.STEERING_WHEEL_HEATING_AUTO_START:// 0x0002ad00;
                return "IHvac.STEERING_WHEEL_HEATING_AUTO_START";
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_VER_MCU_FIRMWARE:// 0x0000e300;
                return "ICarInfo.DEVICE_VER_MCU_FIRMWARE";
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_VER_MCU_HARDWARE:// 0x0000e400;
                return "ICarInfo.DEVICE_VER_MCU_HARDWARE";
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_VER_MCU_FIRMWARE_INT:// 0x0000e500;
                return "ICarInfo.DEVICE_VER_MCU_FIRMWARE_INT";
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_XDSN:// 0x00010400;
                return "ICarInfo.DEVICE_XDSN";
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_IHUID:// 0x00010500;
                return "ICarInfo.DEVICE_IHUID";
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_VEHICLE_TYPE_CONFIG:// 0x00010600;
                return "ICarInfo.DEVICE_VEHICLE_TYPE_CONFIG";
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_PROJECT_CODE:// 0x00010700;
                return "ICarInfo.DEVICE_PROJECT_CODE";
            case ecarx.carext.vehicle.module.ICarInfo.DEVICE_SUPPLIER_CODE:// 0x00010800;
                return "ICarInfo.DEVICE_SUPPLIER_CODE";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_MODEL_COLOR:// 0x00018d00;
                return "ICarInfo.VEHICLE_MODEL_COLOR";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_MOTION_STATUS:// 0x00018f00;
                return "ICarInfo.VEHICLE_MOTION_STATUS";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES:// 0x00019f00;
                return "ICarInfo.VEHICLE_TYPES";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.FUEL:// 0x00019f00|0x01;
                return "ICarInfo.VEHICLE_TYPES|VehicleTypes.FUEL";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.HEV:// 0x00019f00|0x02;
                return "ICarInfo.VEHICLE_TYPES|VehicleTypes.HEV";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.PHEV:// 0x00019f00|0x03;
                return "ICarInfo.VEHICLE_TYPES|VehicleTypes.PHEV";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.EREV:// 0x00019f00|0x04;
                return "ICarInfo.VEHICLE_TYPES|VehicleTypes.EREV";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.FCEV:// 0x00019f00|0x05;
                return "ICarInfo.VEHICLE_TYPES|VehicleTypes.FCEV";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.FCV:// 0x00019f00|0x06;
                return "ICarInfo.VEHICLE_TYPES|VehicleTypes.FCV";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.MHEV:// 0x00019f00|0x07;
                return "ICarInfo.VEHICLE_TYPES|VehicleTypes.MHEV";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.BEV:// 0x00019f00|0x08;
                return "ICarInfo.VEHICLE_TYPES|VehicleTypes.BEV";
            case ecarx.carext.vehicle.module.ICarInfo.VEHICLE_TYPES
                    |ecarx.carext.vehicle.module.ICarInfo.VehicleTypes.UNKNOWN:// 0x00019f00|0xff;
                return "ICarInfo.VEHICLE_TYPES|VehicleTypes.UNKNOWN";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.UNIT_TIME_FORMAT:// 0x0001e800;
                return "ICarInfo.UNIT_TIME_FORMAT";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.UNIT_TIME_FORMAT
                    |ecarx.carext.vehicle.module.v446k.ICarInfo.TimeUnit.UNIT_12:// 0x0001e800|0x01;
                return "ICarInfo.UNIT_TIME_FORMAT|TimeUnit.UNIT_12";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.UNIT_TIME_FORMAT
                    |ecarx.carext.vehicle.module.v446k.ICarInfo.TimeUnit.UNIT_24:// 0x0001e800|0x02;
                return "ICarInfo.UNIT_TIME_FORMAT|TimeUnit.UNIT_24";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.VEHICLE_MAINTENANCE_STATE:// 0x0001f000;
                return "ICarInfo.VEHICLE_MAINTENANCE_STATE";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.VEHICLE_MAINTENANCE_MILEAGE:// 0x0001f100;
                return "ICarInfo.VEHICLE_MAINTENANCE_MILEAGE";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.VEHICLE_MAINTENANCE_TIME:// 0x0001f200;
                return "ICarInfo.VEHICLE_MAINTENANCE_TIME";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.ENGINE_MAINTENANCE_STATE:// 0x0001f300;
                return "ICarInfo.ENGINE_MAINTENANCE_STATE";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.ENGINE_MAINTENANCE_MILEAGE:// 0x0001f400;
                return "ICarInfo.ENGINE_MAINTENANCE_MILEAGE";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.ENGINE_MAINTENANCE_TIME:// 0x0001f500;
                return "ICarInfo.ENGINE_MAINTENANCE_TIME";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.VEHICLE_MAINTENANCE_TIME_CYCLE:// 0x00026d00;
                return "ICarInfo.VEHICLE_MAINTENANCE_TIME_CYCLE";
            case ecarx.carext.vehicle.module.v446k.ICarInfo.VEHICLE_MAINTENANCE_MILEAGE_CYCLE:// 0x00026e00;
                return "ICarInfo.VEHICLE_MAINTENANCE_MILEAGE_CYCLE";
        }
        if (id > 0x10000000) {
                try {
                    return android.car.VehiclePropertyIds.toString(id);
                } catch (Throwable t) {
                }
        }
                ;
        return "0x" + java.lang.Integer.toHexString(id);
    }
}
