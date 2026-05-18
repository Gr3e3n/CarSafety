//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 安全设置
 */
public interface ISafety {
    /**
     * Open the glove box password lock
     * 打开手套箱密码锁，原ID：SETTING FUNC_GLOVEBOX_LOCK
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 10:08:17
     * @deprecated use {@link android.car.VehiclePropertyIds#GLOVE_BOX_LOCKED} instead. --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int GLOVEBOX_LOCK = 0x0001d800;

    /**
     * Automatic parking unlock
     * 驻车自动解锁，原ID：SETTING_FUNC_P_GEAR_UNLOCK
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 10:45:30
     */
    int PARKING_AUTO_UNLOCK = 0x0001dc00;

    /**
     * Two step unlock switch, press the unlock button to unlock the driver's door first, and press the unlock button again to unlock the entire vehicle
     * 两步解锁开关，开启后按解锁键先解锁主驾车门，再次按解锁键才解锁全车，原ID：SETTING_FUNC_TWOSTEP_UNLOCKING
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 11:24:00
     */
    int TWOSTEP_UNLOCKING = 0x0001de00;

    /**
     * check the valid NFC card&lt;br&gt;Original AdaptAPI：KEY_FUNC_PRIVATE_LOCK_CHECK_NFC
     * 检测有效NFC卡&lt;br&gt;V216新增私密锁清除密码使用两张NFC卡方案，平移V446K。原AdaptAPI：KEY_FUNC_PRIVATE_LOCK_CHECK_NFC
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link NfcCarCheckState} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-09 14:03:05
     */
    int PRIVATE_LOCK_CHECK_NFC = 0x00029f00;

    /**
     * to activate/deactivate private locking functionality&lt;br&gt;Original AdaptAPI：SETTING_FUNC_PRIVATE_LOCK
     * 开启或关闭 私人锁&lt;br&gt;原AdaptAPI：SETTING_FUNC_PRIVATE_LOCK
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-09 15:49:51
     */
    int PRIVATE_LOCK = 0x0002a000;

    /**
     * [v446k private]private lock access password&lt;br&gt;Original AdaptAPI:BCM_FUNC_PRIVATE_LOCK_ACCESS_PASSWORD.
     * [v446k 私有]私密锁密码存取&lt;br&gt;密码应加密后存储（如：MD5 SHA256等），APP将密码明文加密后与存储的加密后的密码做比较。新项目方案应考虑使用TEE相关接口&lt;br&gt;Original AdaptAPI:BCM_FUNC_PRIVATE_LOCK_ACCESS_PASSWORD.
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code String} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-09 16:03:36
     */
    int PRIVATE_LOCK_ACCESS_PASSWORD = 0x0002a100;

    /**
     * a reminder when the user tries to open the glove box or trunk when the function {@link #PRIVATE_LOCK} is active&lt;br&gt;&lt;br&gt;Original AdaptAPI:SETTING_FUNC_PRIVATE_LOCK_REMINDER
     * 手套箱打开提醒&lt;br&gt;原 AdaptAPI:SETTING_FUNC_PRIVATE_LOCK_REMINDER
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link PrivateLockReminder} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-09 16:19:58
     */
    int PRIVATE_LOCK_REMINDER = 0x0002a200;

    @IntDef({PrivateLockReminder.ON,
            PrivateLockReminder.REMINDER,
            PrivateLockReminder.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface PrivateLockReminder {
        /**
         * private lock reminder: on (setting value on)
         */
        int ON = 0x01;

        /**
         * private lock reminder: reminder callback, only for user tries to open the glove box or trunk
         */
        int REMINDER = 0x02;

        /**
         * private lock reminder: off (setting value off)
         */
        int OFF = 0xff;
    }

    @IntDef({GLOVEBOX_LOCK,
            PARKING_AUTO_UNLOCK,
            TWOSTEP_UNLOCKING,
            PRIVATE_LOCK_CHECK_NFC,
            PRIVATE_LOCK,
            PRIVATE_LOCK_ACCESS_PASSWORD,
            PRIVATE_LOCK_REMINDER,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({NfcCarCheckState.CHECK_CARD1,
            NfcCarCheckState.CHECK_CARD2,
            NfcCarCheckState.CHECK_CARD1_OK,
            NfcCarCheckState.CHECK_CARD2_OK,
            NfcCarCheckState.CHECK_CARD1_NOK,
            NfcCarCheckState.CHECK_CARD2_NOK,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface NfcCarCheckState {
        /**
         * 开始识别卡1，支持下发
         */
        int CHECK_CARD1 = 0x01;

        /**
         * 开始识别卡2，支持下发
         */
        int CHECK_CARD2 = 0x02;

        /**
         * bin:0101 卡1检测成功
         */
        int CHECK_CARD1_OK = 0x05;

        /**
         * bin:0110 卡2检测成功
         */
        int CHECK_CARD2_OK = 0x06;

        /**
         * bin:1001 卡1检测失败
         */
        int CHECK_CARD1_NOK = 0x09;

        /**
         *  bin:1010 卡2检测失败
         */
        int CHECK_CARD2_NOK = 0x0a;
    }
}
