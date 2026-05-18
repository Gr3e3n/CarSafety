//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 车身
 */
public interface IBcm {
    /**
     * Power off notification
     * 下电通知，关闭车通知
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-10 16:38:40
     */
    int POWER_OFF_NOTICE = 0x00017a00;

    /**
     * Enable engine operation feedback notification
     * 开启引擎操作反馈通知
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link PowerOnOffNotice} property type
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-10 16:46:22
     */
    int POWER_ONOFF_NOTICE = 0x00017b00;

    /**
     * Instrument View Mode
     * 仪表视图模式，原ID：SETTING_FUNC_DIM_DISPLAY_MODE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DisplayMode} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 10:37:05
     */
    int DIM_DISPLAY_MODE = 0x0001db00;

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
     * Car search mode, three modes: honking, flashing lights, flashing lights+honking
     * 寻车模式，三种模式：鸣笛，闪灯，闪灯+鸣笛
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link FindMode} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 13:53:15
     */
    int FIND_CAR_MODE = 0x0001e200;

    /**
     * Time setting for leaving the car without turning off the power, in minutes
     * 离车不下电时长设置，单位分钟，原ID：SETTING_FUNC_PCM_TIMER_TIME_STAMP 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 14:22:26
     */
    int PARKING_KEEP_POWER_TIME = 0x0001e500;

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
     * Notification of fuel tank pressure relief status
     * 油箱盖泄压状态通知
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link FuelDoorOpenCondition} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-30 17:21:05
     */
    int FUEL_DOOR_OPEN_CONDITION = 0x00026100;

    @IntDef({FuelDoorOpenCondition.READY,
            FuelDoorOpenCondition.NOT_READY,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface FuelDoorOpenCondition {
        /**
         * System is ready - Fuel can be filled
         */
        int READY = 0x01;

        /**
         * System is not ready
         */
        int NOT_READY = 0x02;
    }

    @IntDef({FindMode.HONKING,
            FindMode.FLASHING_LIGHTS,
            FindMode.HONKING_AND_FLASHING_LIGHTS,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface FindMode {
        /**
         * 鸣笛
         */
        int HONKING = 0x01;

        /**
         * 闪灯
         */
        int FLASHING_LIGHTS = 0x02;

        /**
         * 鸣笛+闪灯
         */
        int HONKING_AND_FLASHING_LIGHTS = 0x03;
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

    @IntDef({PowerOnOffNotice.POWER_ON,
            PowerOnOffNotice.POWER_OFF,
            PowerOnOffNotice.CHARGING,
            PowerOnOffNotice.ENGINE_HOOD,
            PowerOnOffNotice.LOW_OIL,
            PowerOnOffNotice.UN_GEAR,
            PowerOnOffNotice.LONG_TIME,
            PowerOnOffNotice.NOTFIND_VALID_KEY,
            PowerOnOffNotice.LOW_PRESSUR,
            PowerOnOffNotice.HIGH_PRESSURE,
            PowerOnOffNotice.MAX_TIMES,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface PowerOnOffNotice {
        /**
         * 启动发电已激活
         */
        int POWER_ON = 0x01;

        /**
         * 启动发电已关闭
         */
        int POWER_OFF = 0x02;

        /**
         * 无法启动发电，请取下充电枪
         */
        int CHARGING = 0x03;

        /**
         * 无法启动发电，请关闭机舱盖
         */
        int ENGINE_HOOD = 0x04;

        /**
         * 启动发电退出，油量低
         */
        int LOW_OIL = 0x05;

        /**
         * 启动发电退出，请挂P档
         */
        int UN_GEAR = 0x06;

        /**
         * 启动发电退出，已达最大时间
         */
        int LONG_TIME = 0x07;

        /**
         * 启动发电退出，未找到有效钥匙
         */
        int NOTFIND_VALID_KEY = 0x08;

        /**
         * 启动发电退出，低压系统故障
         */
        int LOW_PRESSUR = 0x09;

        /**
         * 启动发电退出，高压系统故障
         */
        int HIGH_PRESSURE = 0x0a;

        /**
         * 启动发电失败，已达最大次数
         */
        int MAX_TIMES = 0x0b;
    }

    @IntDef({POWER_OFF_NOTICE,
            POWER_ONOFF_NOTICE,
            DIM_DISPLAY_MODE,
            AUTO_REAR_WIPING,
            FIND_CAR_MODE,
            PARKING_KEEP_POWER_TIME,
            VEHICLE_FACTORY_RESET,
            VEHICLE_FACTORY_RESET_STATUS,
            FUEL_DOOR_OPEN_CONDITION,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({DisplayMode.SIMPLE,
            DisplayMode.MULTILANE,
            DisplayMode.SMART,
            DisplayMode.MAP,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DisplayMode {
        /**
         * 极简视图模式
         */
        int SIMPLE = 0x01;

        /**
         * 多车道视图模式
         */
        int MULTILANE = 0x02;

        /**
         * 智能视图模式
         */
        int SMART = 0x03;

        /**
         * 地图视图模式
         */
        int MAP = 0x04;
    }
}
