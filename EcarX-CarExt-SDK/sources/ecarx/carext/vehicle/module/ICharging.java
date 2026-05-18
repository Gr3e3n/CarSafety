//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 充电管理
 */
public interface ICharging {
    /**
     * Try to set charging SOC level, {@link Float} type in percentage
     * 设置充电SOC(目标充电量)
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type 0~100 
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:04:46
     */
    int CHARGING_SOC = 0x00003500;

    /**
     * Current charging power
     * 当前充电功率,单位KW
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:10:22
     * @deprecated using {@link android.car.VehiclePropertyIds#EV_BATTERY_INSTANTANEOUS_CHARGE_RATE} instead --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int BATTERY_CHARGING_CURRENT_POWER = 0x00003900;

    /**
     * Dc charging power
     * 充电功率，直流充电功率
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type Unit: kW 
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:13:12
     */
    int DC_CHARGING_POWER = 0x00003c00;

    /**
     * The Estimated time when is charging
     * 充电预计时间，单位分钟
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:15:52
     * @deprecated using {@link android.car.VehiclePropertyIds#EV_CHARGE_TIME_REMAINING} instead on android-13 --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int CHARGING_ESTIMATED_TIME = 0x00004000;

    /**
     * Try to open/cancel charging
     * 开始充电或取消充电
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link ChargeSwitchState} property type
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:29:54
     */
    int CHARGE_FUNC_CHARGING = 0x00004900;

    /**
     * charging gun electronic lock switch
     * 充电枪电子锁开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:32:35
     */
    int CHARGING_GUN_LOCK = 0x00004a00;

    /**
     * try to set dis-charging SOC level, {@link Float} type in percentage
     * 设置放电SOC(目标放电量),百分比
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type percentage 0~100 
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:33:56
     */
    int DISCHARGING_SOC = 0x00004b00;

    /**
     * Try to open/cancel V2L(Vehicle to Load) dis-charging&lt;br&gt;
     * 开始或取消V2L(将动力电池的电给其他负载进行充电)放电&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:34:36
     */
    int DISCHARGING_SWITCH_V2L = 0x00004c00;

    /**
     * The actual work voltage when is dis-charging
     * 放电实际工作电压
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:35:18
     */
    int DISCHARGING_WORK_VOLTAGE = 0x00004d00;

    /**
     * The actual work current when is dis-charging
     * 放电实际工作电流
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:35:53
     */
    int DISCHARGING_WORK_CURRENT = 0x00004e00;

    /**
     * DISCHARGING POWER
     * 当前放电功率,单位KW
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type  
     * </ul>
     * huaqiao.lu@ecarxgroup.com created for project hongqi at 2024-06-18 15:36:48
     */
    int BATTERY_DISCHARGING_CURRENT_POWER = 0x00004f00;

    /**
     * to set the Energy regeneration level
     * 能量回收等级设置，原ID为SETTING_FUNC_ENERGY_REGENERATION
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link EnergyRegenerationLevel} property type
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 11:07:12
     */
    int ENERGY_REGENERATION = 0x0000a700;

    /**
     * Charging gun states of ChargingPlugState
     * 充电枪状态 ChargingPlugState
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link ChargingPlugState} property type
     * </ul>
     * tao.zhan@ecarxgroup.com created for project hongqi at 2024-06-21 18:36:39
     */
    int CHARGING_PLUG_STATE = 0x0000dd00;

    /**
     * Charge and discharge prompt Chinese display
     * 充放电提示文言显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link ChargingDisChargingState} property type
     * </ul>
     * tao.zhan@ecarxgroup.com created for project hongqi at 2024-06-21 18:45:47
     */
    int CHARGING_DISCHARGING_STATE = 0x0000de00;

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

    @IntDef({CHARGING_SOC,
            BATTERY_CHARGING_CURRENT_POWER,
            DC_CHARGING_POWER,
            CHARGING_ESTIMATED_TIME,
            CHARGE_FUNC_CHARGING,
            CHARGING_GUN_LOCK,
            DISCHARGING_SOC,
            DISCHARGING_SWITCH_V2L,
            DISCHARGING_WORK_VOLTAGE,
            DISCHARGING_WORK_CURRENT,
            BATTERY_DISCHARGING_CURRENT_POWER,
            ENERGY_REGENERATION,
            CHARGING_PLUG_STATE,
            CHARGING_DISCHARGING_STATE,
            CHARGING_ENERGY,
            DISCHARGING_ENERGY,
            BATTERY_TYPE,
            WPC_WORK_MODE,
            WPC_CHARGE_STATES,
            WPC_CHARGE_PERCENTAGE,
            WPC_REMIND_PHONE_LEAVE_OVER,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({ChargingDisChargingState.DEFAULT,
            ChargingDisChargingState.NO_CHARGING,
            ChargingDisChargingState.AC_CHARGING,
            ChargingDisChargingState.CHARGING_END,
            ChargingDisChargingState.CHARGING_CMPL,
            ChargingDisChargingState.HEATING,
            ChargingDisChargingState.BOOKING,
            ChargingDisChargingState.NO_DISCHARING,
            ChargingDisChargingState.DISCHARING,
            ChargingDisChargingState.DISCHARING_END,
            ChargingDisChargingState.DISCHARING_CMPL,
            ChargingDisChargingState.CHARING_FALUT,
            ChargingDisChargingState.DISCHARING_FALUT,
            ChargingDisChargingState.AC_CHRGNFLTCHRGRSIDE,
            ChargingDisChargingState.DC_CHARGING,
            ChargingDisChargingState.DC_CHRGNFLTVEHSIDE,
            ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDETEMPFLT,
            ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDECONFLT,
            ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDEHWFLT,
            ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDEEMGYFLT,
            ChargingDisChargingState.DC_CHRGNFLTCHRGRSIDECOMFLT,
            ChargingDisChargingState.SUPERCHARGING,
            ChargingDisChargingState.AC_CHARGINGSUSPEND,
            ChargingDisChargingState.DC_CHARGINGEND,
            ChargingDisChargingState.ACCHRGNFLTVEHSIDE,
            ChargingDisChargingState.BOOSTCHARGING,
            ChargingDisChargingState.BOOSTCHARGINGFLT,
            ChargingDisChargingState.WIRELESSCHARGING,
            ChargingDisChargingState.NONE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface ChargingDisChargingState {
        /**
         * 默认状态
         */
        int DEFAULT = 0x01;

        /**
         * 未充电
         */
        int NO_CHARGING = 0x02;

        /**
         * 交流充电中
         */
        int AC_CHARGING = 0x03;

        /**
         * 交流充电结束
         */
        int CHARGING_END = 0x04;

        /**
         * 交流充电完成
         */
        int CHARGING_CMPL = 0x05;

        /**
         * 充电预热中
         */
        int HEATING = 0x06;

        /**
         * 预约中
         */
        int BOOKING = 0x07;

        /**
         * 未放电
         */
        int NO_DISCHARING = 0x08;

        /**
         * 放电中
         */
        int DISCHARING = 0x09;

        /**
         * 放电结束
         */
        int DISCHARING_END = 0x0a;

        /**
         * 放电结束
         */
        int DISCHARING_CMPL = 0x0b;

        /**
         * 交流充电异常
         */
        int CHARING_FALUT = 0x0c;

        /**
         * 放电异常
         */
        int DISCHARING_FALUT = 0x0d;

        /**
         * 交流充电异常
         */
        int AC_CHRGNFLTCHRGRSIDE = 0x0e;

        /**
         * 直流充电中
         */
        int DC_CHARGING = 0x0f;

        /**
         * 直流充电异常
         */
        int DC_CHRGNFLTVEHSIDE = 0x10;

        /**
         * 直流充电异常
         */
        int DC_CHRGNFLTCHRGRSIDETEMPFLT = 0x11;

        /**
         * 直流充电异常
         */
        int DC_CHRGNFLTCHRGRSIDECONFLT = 0x12;

        /**
         * 直流充电异常
         */
        int DC_CHRGNFLTCHRGRSIDEHWFLT = 0x13;

        /**
         * 直流充电异常
         */
        int DC_CHRGNFLTCHRGRSIDEEMGYFLT = 0x14;

        /**
         * 直流充电异常
         */
        int DC_CHRGNFLTCHRGRSIDECOMFLT = 0x15;

        /**
         * 超级充电中
         */
        int SUPERCHARGING = 0x16;

        /**
         * 交流充电结束
         */
        int AC_CHARGINGSUSPEND = 0x17;

        /**
         * 直流充电结束
         */
        int DC_CHARGINGEND = 0x18;

        /**
         * 直流充电结束
         */
        int ACCHRGNFLTVEHSIDE = 0x19;

        /**
         * 升压充电中
         */
        int BOOSTCHARGING = 0x1a;

        /**
         * 升压充电异常
         */
        int BOOSTCHARGINGFLT = 0x1b;

        /**
         * 无线充电中
         */
        int WIRELESSCHARGING = 0x1c;

        /**
         * 不显示
         */
        int NONE = 0x1d;
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

    @IntDef({EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_LOW,
            EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_MID,
            EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_HIGH,
            EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_AUTO,
            EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_LOWER,
            EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_HIGHER,
            EnergyRegenerationLevel.ENERGY_REGENERATION_LEVEL_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface EnergyRegenerationLevel {
        /**
         * Energy regeneration level: low
         */
        int ENERGY_REGENERATION_LEVEL_LOW = 0x01;

        int ENERGY_REGENERATION_LEVEL_MID = 0x02;

        int ENERGY_REGENERATION_LEVEL_HIGH = 0x03;

        int ENERGY_REGENERATION_LEVEL_AUTO = 0x04;

        int ENERGY_REGENERATION_LEVEL_LOWER = 0x05;

        int ENERGY_REGENERATION_LEVEL_HIGHER = 0x06;

        /**
         * Energy regeneration level: off
         */
        int ENERGY_REGENERATION_LEVEL_OFF = 0xff;
    }

    @IntDef({ChargeSwitchState.STOP,
            ChargeSwitchState.RESTART,
            ChargeSwitchState.START_NOW,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface ChargeSwitchState {
        int STOP = 0x01;

        int RESTART = 0x02;

        int START_NOW = 0x03;
    }

    @IntDef({ChargingPlugState.STATE_UNKNOWN,
            ChargingPlugState.STATE_PREPARED,
            ChargingPlugState.STATE_CHARGING,
            ChargingPlugState.STATE_COMPLETED,
            ChargingPlugState.STATE_MULTI,
            ChargingPlugState.STATE_ERROR,
            ChargingPlugState.STATE_DISCHARGING,
            ChargingPlugState.STATE_DISCHARGING_COMPLETED,
            ChargingPlugState.STATE_HEATING,
            ChargingPlugState.STATE_CONNECTED,
            ChargingPlugState.STATE_DISCONNECTED,
            ChargingPlugState.STATE_CHARGING_PAUSE,
            ChargingPlugState.STATE_DISCHARGING_PAUSE,
            ChargingPlugState.STATE_SLOW_CHARGING_CONNECTED,
            ChargingPlugState.STATE_QUICK_CHARGING_CONNECTED,
            ChargingPlugState.STATE_DISCHARGING_CONNECTED,
            ChargingPlugState.STATE_WAITING,
            ChargingPlugState.STATE_MIS_MATCHED,
            ChargingPlugState.STATE_NONE,
            ChargingPlugState.STATE_RESERVE_WAITING,
            ChargingPlugState.STATE_CHARGING_FAIL,
            ChargingPlugState.STATE_DISCHARGING_FAIL,
            ChargingPlugState.STATE_DISCHARGING_END,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface ChargingPlugState {
        /**
         * 充电枪/放电枪状态未知
         */
        int STATE_UNKNOWN = 0x01;

        /**
         * 充电枪已准备就绪
         */
        int STATE_PREPARED = 0x02;

        /**
         * 正在充电中
         */
        int STATE_CHARGING = 0x03;

        /**
         * 充电枪已充电完成
         */
        int STATE_COMPLETED = 0x04;

        /**
         * 多充电枪连接 (DX11:充电即将停止\n请勿链接两把充电枪)
         */
        int STATE_MULTI = 0x05;

        /**
         * 充电枪异常
         */
        int STATE_ERROR = 0x06;

        /**
         * 放电枪正在放电中
         */
        int STATE_DISCHARGING = 0x07;

        /**
         * 放电枪已放电完成
         */
        int STATE_DISCHARGING_COMPLETED = 0x08;

        /**
         * 加热中
         */
        int STATE_HEATING = 0x09;

        /**
         * 充电枪/放电枪 已连接
         */
        int STATE_CONNECTED = 0x0a;

        /**
         * 充电枪/放电枪 未连接
         */
        int STATE_DISCONNECTED = 0x0b;

        /**
         * 充电暂停
         */
        int STATE_CHARGING_PAUSE = 0x0c;

        /**
         * 放电暂停
         */
        int STATE_DISCHARGING_PAUSE = 0x0d;

        /**
         * 交流充电枪已连接
         */
        int STATE_SLOW_CHARGING_CONNECTED = 0x0e;

        /**
         * 直流充电枪已连接
         */
        int STATE_QUICK_CHARGING_CONNECTED = 0x0f;

        /**
         * 放电枪已连接
         */
        int STATE_DISCHARGING_CONNECTED = 0x10;

        /**
         * 这种状态是交流充电线或放电线插入，但没有完全连接，所以不能判断当前插入的电缆是充电还是放电。 对于HMI接口，可以用来显示有电缆插入，如电缆指示灯
         */
        int STATE_WAITING = 0x11;

        /**
         * 抢线不匹配，请尝试更换枪线
         */
        int STATE_MIS_MATCHED = 0x12;

        /**
         * EgyChrgnCableConn.Status = None; Not display
         */
        int STATE_NONE = 0x13;

        /**
         * 预约等待
         */
        int STATE_RESERVE_WAITING = 0x14;

        /**
         * 充电失败
         */
        int STATE_CHARGING_FAIL = 0x15;

        /**
         * 放电失败
         */
        int STATE_DISCHARGING_FAIL = 0x16;

        /**
         * 已放电至结束值，放电结束
         */
        int STATE_DISCHARGING_END = 0x17;
    }
}
