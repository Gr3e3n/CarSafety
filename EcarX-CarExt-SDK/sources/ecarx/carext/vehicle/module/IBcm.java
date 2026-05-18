//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 车身
 */
public interface IBcm {
    /**
     * Power ON and Power OFF request to VMM
     * 启动/关闭车 (类 一键启动)，车辆下电，原ID为BCM_FUNC_POWER_ONOFF
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 14:56:22
     * @deprecated 使用 {@link #POWER_ON}和{@link #POWER_OFF}代替 --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int POWER_ONOFF = 0x00002f00;

    /**
     * Front fog light switch
     * 前雾灯开关，原ID为BCM_FUNC_LIGHT_FRONT_FOG_LAMPS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 14:57:43
     * @deprecated using {@link android.car.VehiclePropertyIds#FRONT_FOG_LIGHTS_STATE} instead on android-13 --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int LIGHT_FRONT_FOG_LAMPS = 0x00003000;

    /**
     * Request to adjust the left/right/both exterior rear mirrors&lt;br&gt;Left mirror use with VehicleAreaMirror#MIRROR_DRIVER_LEFT&lt;br&gt;Right mirror use with VehicleAreaMirror#MIRROR_DRIVER_RIGHT&lt;br&gt;Both mirror use with VehicleAreaMirror#MIRROR_DRIVER_LEFT | VehicleAreaMirror#MIRROR_DRIVER_RIGHT&lt;br&gt;
     * 激活后视镜调节，原ID为BCM_FUNC_REAR_MIRROR_ADJUST
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_MIRROR}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 15:10:51
     */
    int REAR_MIRROR_ADJUST = 0x00003b00;

    /**
     * setting to define if mirrors should fold automatically
     * 后视镜自动折叠，原ID为SETTING_FUNC_MIRROR_AUTO_FOLDING
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 15:55:41
     * @deprecated 使用原生ID: MIRROR_AUTO_FOLD_ENABLED --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int MIRROR_AUTO_FOLDING = 0x00005200;

    /**
     * Setting auto close window when lock
     * 锁车自动关窗设置,原ID为SETTING_FUNC_LOCK_AUTO_CLOSE_WINDOW
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 17:48:18
     */
    int LOCK_AUTO_CLOSE_WINDOW = 0x00005a00;

    /**
     * rain auto close windows
     * 下雨自动关窗，原ID为BCM_FUNC_AUTO_CLOSE_WINDOW_RAINY
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 17:49:20
     */
    int AUTO_CLOSE_WINDOW_RAINY = 0x00005b00;

    /**
     * to activate wiper service position for windscreen through a menu
     * 雨刮维修位置 / 挡风玻璃雨刮擦拭位置，原ID为SETTING_FUNC_WINDSCREEN_SERVICE_POSITION
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_WINDOW}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 17:56:21
     */
    int WINDSCREEN_SERVICE_POSITION = 0x00005e00;

    /**
     * custom key navigation，setting custom key service default
     * 自定义按键，原ID为BCM_FUNC_CUSTOM_KEY
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link CustomKeyType} property type
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-18 18:31:32
     */
    int CUSTOM_KEY = 0x00006d00;

    /**
     * child safety lock
     * 打开/关闭儿童锁
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-18 21:38:10
     * @deprecated using {@link VehiclePropertyIds#DOOR_CHILD_LOCK_ENABLED} instead on Android 14 --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int CHILD_SAFETY_LOCK = 0x00008a00;

    /**
     * open/close Atmosphere lamps
     * 氛围灯开关，原ID为BCM_FUNC_LIGHT_ATMOSPHERE_LAMPS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 09:49:27
     */
    int LIGHT_ATMOSPHERE_LAMPS = 0x00009a00;

    /**
     *  Electric Parking Brake(EPB) switch
     * EPB(电子手刹)开关,原ID为SETTING_FUNC_PBC_EPB_SWITCH
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 10:35:18
     * @deprecated using {@link android.car.VehiclePropertyIds#PARKING_BRAKE_ON} instead --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int PBC_EPB_SWITCH = 0x00009f00;

    /**
     * Automatically turn on the rear wiper
     * 自动开启后雨刮，开启后判断两个条件：1、倒车，2、前雨刮开启；两个都满足则开启后雨刮，原ID：SETTING_FUNC_AUTO_REAR_WIPING
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 11:27:36
     */
    int AUTO_REAR_WIPING = 0x0001e000;

    /**
     * Request car to factory reset
     * 整车恢复出厂设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 15:53:20
     */
    int VEHICLE_FACTORY_RESET = 0x0001f900;

    /**
     * Restore the factory result
     * 整车恢复出厂设置状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link FactoryResetStatus} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 15:56:48
     */
    int VEHICLE_FACTORY_RESET_STATUS = 0x0001fa00;

    /**
     * Passenger Airbag on off 1. Validity: can only be set in P gear, and cannot be set or adjusted in driving gears such as D and R. 2. The signal value is of boolean type, set to true to enable the passenger airbag, set to false to disable the passenger airbag.
     * 乘客安全气囊使能开关 1、有效性: 仅在P挡位可以进行设置,在D挡位和R挡位等行车挡位不能进行设置和调整。 2、信号值为boolean类型,设置为true,启用副驾安全气囊,设置false,禁用副驾安全气囊。
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-09-05 15:37:01
     */
    int PAB_SWITCH = 0x00023200;

    /**
     * [Same name as native interface]Indicates if there is a trailer present or not.&lt;br/&gt;&lt;p&gt;Returns the trailer state of the car.
     * 【与原生接口同名】指示是否有拖车&lt;br/&gt;&lt;p&gt;返回汽车的拖车状态。
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link TrailerState} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-11-27 09:57:13
     * @deprecated 在APILevel-33时，使用同名原生接口{@link android.car.VehiclePropertyIds#TRAILER_PRESENT} --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int TRAILER_PRESENT = 0x00028300;

    @IntDef({POWER_ONOFF,
            LIGHT_FRONT_FOG_LAMPS,
            REAR_MIRROR_ADJUST,
            MIRROR_AUTO_FOLDING,
            LOCK_AUTO_CLOSE_WINDOW,
            AUTO_CLOSE_WINDOW_RAINY,
            WINDSCREEN_SERVICE_POSITION,
            CUSTOM_KEY,
            CHILD_SAFETY_LOCK,
            LIGHT_ATMOSPHERE_LAMPS,
            PBC_EPB_SWITCH,
            AUTO_REAR_WIPING,
            VEHICLE_FACTORY_RESET,
            VEHICLE_FACTORY_RESET_STATUS,
            PAB_SWITCH,
            TRAILER_PRESENT,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({CustomKeyType.KEY_TYPE_AUTO_PARKING,
            CustomKeyType.KEY_TYPE_SOUND_SWITCH,
            CustomKeyType.KEY_TYPE_DVR,
            CustomKeyType.KEY_TYPE_DRIVE_MODE_SWITCH,
            CustomKeyType.KEY_TYPE_THEME_SWITCH,
            CustomKeyType.KEY_TYPE_GAMEMOD,
            CustomKeyType.KEY_TYPE_VOICE,
            CustomKeyType.KEY_TYPE_REAR_MIRROR,
            CustomKeyType.KEY_TYPE_360,
            CustomKeyType.KEY_TYPE_HUD,
            CustomKeyType.KEY_TYPE_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface CustomKeyType {
        /**
         * 激活泊车，泊车
         */
        int KEY_TYPE_AUTO_PARKING = 0x01;

        /**
         * 音源切换
         */
        int KEY_TYPE_SOUND_SWITCH = 0x02;

        /**
         * 拍照录像，DVR
         */
        int KEY_TYPE_DVR = 0x03;

        /**
         * 切换驾驶模式
         */
        int KEY_TYPE_DRIVE_MODE_SWITCH = 0x04;

        /**
         * 切换主题
         */
        int KEY_TYPE_THEME_SWITCH = 0x05;

        /**
         *  游戏模式
         */
        int KEY_TYPE_GAMEMOD = 0x06;

        /**
         * 激活语音
         */
        int KEY_TYPE_VOICE = 0x07;

        /**
         * 后视镜调节
         */
        int KEY_TYPE_REAR_MIRROR = 0x08;

        /**
         * 全景
         */
        int KEY_TYPE_360 = 0x09;

        /**
         * 切换HUD视图
         */
        int KEY_TYPE_HUD = 0x0a;

        int KEY_TYPE_OFF = 0xff;
    }

    @IntDef({FactoryResetStatus.PROGRESS,
            FactoryResetStatus.SUCCEED,
            FactoryResetStatus.FAILED,
            FactoryResetStatus.UNKNOW,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface FactoryResetStatus {
        /**
         * 恢复出厂中
         */
        int PROGRESS = 0x01;

        /**
         * 恢复出厂成功
         */
        int SUCCEED = 0x02;

        /**
         * 恢复出厂失败
         */
        int FAILED = 0x03;

        /**
         * 未知
         */
        int UNKNOW = 0xff;
    }

    @IntDef({TrailerState.STATE_NOT_PRESENT,
            TrailerState.STATE_PRESENT,
            TrailerState.STATE_ERROR,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface TrailerState {
        /**
         * A trailer is not attached to the vehicle.
         */
        int STATE_NOT_PRESENT = 0x01;

        /**
         * A trailer is attached to the vehicle.
         */
        int STATE_PRESENT = 0x02;

        /**
         * The state of the trailer is not available due to an error.
         */
        int STATE_ERROR = 0x03;
    }
}
