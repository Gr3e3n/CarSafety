//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 充电管理
 */
public interface ICharging {
    /**
     * Parking charging function switch
     * 驻车充电功能开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 14:41:11
     */
    int CHARGE_FUNC_PARKING = 0x00015a00;

    /**
     * Discharge mileage protection value, unit km
     * 放电里程保护值，单位km
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 15:50:23
     */
    int DISTANCE_PROTECTION = 0x00015b00;

    /**
     * Discharge mileage protection switch
     * 放电里程保护功能开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 15:52:21
     */
    int DISTANCE_PROTECTION_SWITCH = 0x00015c00;

    /**
     * Total charge value: Float type Unit kwh
     * 充电总电量 value: Float type Unit kwh
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 16:43:50
     */
    int CHARGING_ENERGY = 0x00015e00;

    /**
     * The energy which the vehicle has already discharged
     * 放电能量释放值 value: Float type Unit kwh
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 16:45:39
     */
    int DISCHARGING_ENERGY = 0x00015f00;

    /**
     * The time which the vehicle single charged
     * 单次充电时长 value: long 单位分钟
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Long} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 16:56:10
     */
    int CHARGING_WORK_TIME = 0x00016100;

    /**
     * The time which the vehicle single discharged
     * 单次放电时长 value: Float 单位分钟
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 16:57:16
     */
    int DISCHARGING_WORK_TIME = 0x00016200;

    /**
     * Try to parking charging notice 
     * 驻车充电通知，原ID:HYBRID_FUNC_POWER_FLOW_NOTICE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link PowerFlowNotice} property type
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 17:50:40
     */
    int POWER_FLOW_NOTICE = 0x00016a00;

    /**
     * Set/get Adding battery mode HYBRID_FUNC_BATTERY_SAVE_MODE or HYBRID_FUNC_BATTERY_CHARGE_MODE SOC
     * 电池充电模式 SOC(目标电量),原ID：HYBRID_FUNC_BATTERY_SOC
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 17:54:22
     */
    int BATTERY_SOC = 0x00016b00;

    /**
     * Battery mode（normal, hold, charge）
     * 电池模式（normal, hold, charge具有单独有效性）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link BatteryMode} property type
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-09 19:17:52
     */
    int BATTERY_MODE = 0x00017100;

    /**
     * Make an appointment to charge the charge switch immediately
     * 预约充电立即充电开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-26 15:03:37
     */
    int BOOK_CHARGING_IMMEDIATELY = 0x0001af00;

    /**
     * activate/deactivate [Front position lamp] will flash when charging starts(External charging light)
     * 充电状态指示灯开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-07-30 14:42:27
     */
    int EXTERNAL_CHARGING_LIGHT = 0x0001b200;

    /**
     * The type of battery currently used in the vehicle
     * 当前车辆使用的电池类型
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     * <li>{@link BatteryType} property type
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-08-14 13:48:31
     */
    int BATTERY_TYPE = 0x0001cc00;

    /**
     * For hybrid vehicles, activating the extreme pure electric range can appropriately extend the pure electric range，original ID : CHARGE_FUNC_MAXEV_SWITCH
     * 极致纯电续航，对于混动车来说，激活极致纯电续航后可以适当延长纯电续航里程, 原 ID 为 CHARGE_FUNC_MAXEV_SWITCH
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project v446k at 2024-08-15 17:25:09
     */
    int MAX_EV_ENDURANCE = 0x0001cf00;

    /**
     * WPC(Wireless Phone Charging) Wireless charging panel switch
     * WPC(Wireless Phone Charging)无线充电面板开关，开启后手机放置可进行无线充电，原ID：WPC_FUNC_WORK_MODE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 13:43:19
     */
    int WPC_WORK_MODE = 0x0001e100;

    /**
     * Extreme Endurance Mode Block Notification. If the conditions for Extreme Endurance Mode are not met, the system will automatically deactivate Extreme Endurance Mode and notify the application layer through this interface. The application can distinguish whether the Extreme Endurance Mode was actively or passively turned off using this interface. The original ID is CHARGE_FUNC_MAXEV_BLOCK_NOTIFICATION.
     * 极致续航功能受限/block通知。如果当前不满足极致续航条件，系统层主动退出极致续航，通过该接口通知到应用层。应用通过该接口区分极致续航功能是用户主动关闭还是被动退出。原 ID 为 CHARGE_FUNC_MAXEV_BLOCK_NOTIFICATION
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type true - 极致续航功能受限/阻塞 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-28 17:02:14
     */
    int MAX_EV_ENDURANCE_BLOCKED = 0x00021f00;

    /**
     * WPC(Wireless Phone Charging) WPC charging states, read only
     * WPC(Wireless Phone Charging)WPC无线充电状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link ChargingStates} property type
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-08-30 15:33:33
     */
    int WPC_CHARGE_STATES = 0x00022200;

    /**
     * WPC(Wireless Phone Charging) WPC charging percentage
     * WPC(Wireless Phone Charging) WPC充电百分比
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 0~100 
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-08-30 15:50:39
     */
    int WPC_CHARGE_PERCENTAGE = 0x00022400;

    /**
     * Phone left behind reminder
     * 手机遗留时是否提示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type true:遗留时进行提醒 
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-08-30 15:55:04
     */
    int WPC_REMIND_PHONE_LEAVE_OVER = 0x00022500;

    /**
     * Start engine when low battery . When turned on, the engine is used to supply power to the battery.
     * 低电量启机。开启后，低电量时燃油发电启动发动机。（原AdaptAPI:CHARGE_FUNC_DISCHARGING_ENGINE_SWITCH）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-09-06 17:02:33
     */
    int START_ENGINE_WHEN_LOW_POWER = 0x00023900;

    @IntDef({BatteryType.TERNARY_LITHIUM_BATTERY,
            BatteryType.LITHIUM_IRON_PHOSPHATE_BATTERY,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface BatteryType {
        /**
         * 三元锂电池
         */
        int TERNARY_LITHIUM_BATTERY = 0x01;

        /**
         * 磷酸铁锂电池
         */
        int LITHIUM_IRON_PHOSPHATE_BATTERY = 0x02;
    }

    @IntDef({BatteryMode.BATTERY_MODE_NORMAL,
            BatteryMode.BATTERY_MODE_HOLD,
            BatteryMode.BATTERY_MODE_CHARGE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface BatteryMode {
        /**
         * parking charging notice cannot enter 开关显示off
         */
        int BATTERY_MODE_NORMAL = 0x01;

        /**
         * parking charging notice cannot enter 开关显示hold
         */
        int BATTERY_MODE_HOLD = 0x02;

        /**
         * parking charging notice cannot enter 开关显示charge
         */
        int BATTERY_MODE_CHARGE = 0x03;
    }

    @IntDef({ChargingStates.UNKNOWN,
            ChargingStates.NO_DEVICE,
            ChargingStates.STANDBY,
            ChargingStates.CHARGING,
            ChargingStates.CHARGED,
            ChargingStates.ERROR,
            ChargingStates.OVERHEAT,
            ChargingStates.OVERPOWER,
            ChargingStates.FOD,
            ChargingStates.PEPS_INTERRUPT,
            ChargingStates.OVERVOLTAGE,
            ChargingStates.CHARGING_ONE_FOURTH,
            ChargingStates.CHARGING_HALF,
            ChargingStates.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface ChargingStates {
        /**
         * 未知
         */
        int UNKNOWN = 0x01;

        /**
         * 无设备
         */
        int NO_DEVICE = 0x02;

        /**
         * 就绪
         */
        int STANDBY = 0x03;

        /**
         * 充电中
         */
        int CHARGING = 0x04;

        /**
         * 充满电
         */
        int CHARGED = 0x05;

        /**
         * 出错
         */
        int ERROR = 0x06;

        /**
         * 过热
         */
        int OVERHEAT = 0x07;

        /**
         * 电流电压
         */
        int OVERPOWER = 0x08;

        /**
         * 监测到异物
         */
        int FOD = 0x09;

        /**
         * 无钥匙启动打断
         */
        int PEPS_INTERRUPT = 0x0a;

        /**
         * 电压
         */
        int OVERVOLTAGE = 0x0b;

        /**
         * 充电中_15W
         */
        int CHARGING_ONE_FOURTH = 0x0c;

        /**
         * 充电中_50w
         */
        int CHARGING_HALF = 0x0d;

        /**
         * 关
         */
        int OFF = 0xff;
    }

    @IntDef({PowerFlowNotice.POWER_FLOW_NOTICE_NOREQUESET,
            PowerFlowNotice.POWER_FLOW_NOTICE_NOACTIVE,
            PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING,
            PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING_COMPL,
            PowerFlowNotice.POWER_FLOW_NOTICE_CHARGING_FAULT,
            PowerFlowNotice.POWER_FLOW_NOTICE_CANNOTENTER,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface PowerFlowNotice {
        /**
         * parking charging notice no requeset 驻车充电通知无请求
         */
        int POWER_FLOW_NOTICE_NOREQUESET = 0x01;

        /**
         * parking charging notice no active 驻车充电通知无活动
         */
        int POWER_FLOW_NOTICE_NOACTIVE = 0x02;

        /**
         * parking charging notice charging 驻车充电通知充电
         */
        int POWER_FLOW_NOTICE_CHARGING = 0x03;

        /**
         * parking charging notice charging complete 驻车充电通知充电完成
         */
        int POWER_FLOW_NOTICE_CHARGING_COMPL = 0x04;

        /**
         * parking charging notice charging fault 驻车充电故障
         */
        int POWER_FLOW_NOTICE_CHARGING_FAULT = 0x05;

        /**
         * parking charging notice cannot enter 驻车充电无输入
         */
        int POWER_FLOW_NOTICE_CANNOTENTER = 0x06;
    }

    @IntDef({CHARGE_FUNC_PARKING,
            DISTANCE_PROTECTION,
            DISTANCE_PROTECTION_SWITCH,
            CHARGING_ENERGY,
            DISCHARGING_ENERGY,
            CHARGING_WORK_TIME,
            DISCHARGING_WORK_TIME,
            POWER_FLOW_NOTICE,
            BATTERY_SOC,
            BATTERY_MODE,
            BOOK_CHARGING_IMMEDIATELY,
            EXTERNAL_CHARGING_LIGHT,
            BATTERY_TYPE,
            MAX_EV_ENDURANCE,
            WPC_WORK_MODE,
            MAX_EV_ENDURANCE_BLOCKED,
            WPC_CHARGE_STATES,
            WPC_CHARGE_PERCENTAGE,
            WPC_REMIND_PHONE_LEAVE_OVER,
            START_ENGINE_WHEN_LOW_POWER,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
