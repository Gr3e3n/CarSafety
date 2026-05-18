//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 传感器，虚拟传感器，传感器事件，都放这里吧
 */
public interface ISensor {
    /**
     * EV endurance mileage of the car, unit: Kilometer(km)
     * 当前电可续航里程，单位（km）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project hongqi at 2024-06-21 15:50:57
     */
    int ENDURANCE_MILEAGE_EV = 0x0000cc00;

    /**
     * Current battery level in percentage, unit: %
     * 当前剩余电量百分比
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project hongqi at 2024-06-21 15:53:19
     */
    int BATTERY_PERCENTAGE = 0x0000cd00;

    /**
     * Driving info: Average energy consumption of current trip, Unit: kwh/100km
     * 本次行程平均能耗(kwh) 小计里程(trip1)：MIRROR_DRIVER_LEFT 本次里程(trip2)：MIRROR_DRIVER_RIGHT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_MIRROR}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Float} property type  
     * </ul>
     * tao.zhan@ecarxgroup.com created for project hongqi at 2024-06-22 11:10:32
     */
    int AVG_ENERGY_CONSUMPTION = 0x0000e000;

    /**
     * Car Mode / Vehicle Mode
     * 车辆(整车)状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link CarMode} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-24 14:38:27
     */
    int CAR_MODE = 0x0000e800;

    /**
     * Vehicle usage mode
     * 车辆使用模式，原ID为ISensor#SENSOR_TYPE_USG_MODE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link UsageMode} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-28 17:13:48
     */
    int USAGE_MODE = 0x00010300;

    /**
     * power state, s mode
     * 电源状态，S Mode
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link PowerState} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-08-06 19:29:16
     */
    int POWER_STATE = 0x0001bb00;

    /**
     * indoor Air Quality Index(AQI)
     * 车内 空气质量指数AQI （原adaptApi接口SENSOR_TYPE_AQI_INDOOR）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Float} property type  
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-12-11 14:48:52
     */
    int AQI_INDOOR = 0x0002ac00;

    @IntDef({CarMode.NORMAL,
            CarMode.FACTORY,
            CarMode.TRANSPORT,
            CarMode.CRASH,
            CarMode.DYNO,
            CarMode.SHOW_ROOM,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface CarMode {
        /**
         * Car Mode state: Normal (正常使用模式, 客户使用的正常模式)
         */
        int NORMAL = 0x01;

        /**
         * Car Mode state: Factory (生产过程模式, 总装, 以离开工厂为结束)
         */
        int FACTORY = 0x02;

        /**
         * Car Mode state: Transport (运输模式, 运输和存储, 交付顾客前)
         */
        int TRANSPORT = 0x03;

        /**
         * Car Mode state: Crash (撞击模式, 监测到碰撞)
         */
        int CRASH = 0x04;

        /**
         * Car Mode state: Dynamo (测试模式, 测试特定驾驶情况或诊断使用情况)
         */
        int DYNO = 0x05;

        /**
         * Car Mode state: ShowRoom （展车，展厅）。已存在属性AUTO_SHOW_MODE，此枚举需要确认
         */
        int SHOW_ROOM = 0x06;
    }

    @IntDef({PowerState.SLEEP,
            PowerState.STARTUP_READY,
            PowerState.WELCOME,
            PowerState.STARTUP_INACTIVE,
            PowerState.CONVENIENCE,
            PowerState.ACTIVE,
            PowerState.DRIVING,
            PowerState.ERROR,
            PowerState.KEEP_TOUCH,
            PowerState.PREPARE_SHUTDOWN,
            PowerState.STR,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface PowerState {
        /**
         * 电源状态编号S0
         */
        int SLEEP = 0x01;

        /**
         * 电源状态编号S1
         */
        int STARTUP_READY = 0x02;

        /**
         * 电源状态编号S2
         */
        int WELCOME = 0x03;

        /**
         * 电源状态编号S3
         */
        int STARTUP_INACTIVE = 0x04;

        /**
         * 电源状态编号S4
         */
        int CONVENIENCE = 0x05;

        /**
         * 电源状态编号S5
         */
        int ACTIVE = 0x06;

        /**
         * 电源状态编号S6
         */
        int DRIVING = 0x07;

        /**
         * 电源状态编号S7
         */
        int ERROR = 0x08;

        /**
         * 电源状态编号S8
         */
        int KEEP_TOUCH = 0x09;

        /**
         * 电源状态编号S9
         */
        int PREPARE_SHUTDOWN = 0x0a;

        /**
         * 电源状态编号S10
         */
        int STR = 0x0b;
    }

    @IntDef({UsageMode.ABANDONED,
            UsageMode.INACTIVE,
            UsageMode.CONVENIENCE,
            UsageMode.ACTIVE,
            UsageMode.DRIVING,
            UsageMode.UNKNOWN,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface UsageMode {
        /**
         * 休眠(休眠，inactive一段时间后进入，关闭待机等待激活周期性检查输入数据的功能)
         */
        int ABANDONED = 0x01;

        /**
         * 待机(休息，功能有限，仅限部分低能耗功能处于待激活状态，但需客户激活才可能有效，以及后运行功能可用)
         */
        int INACTIVE = 0x02;

        /**
         * 便利(舒适性功能可用)
         */
        int CONVENIENCE = 0x03;

        /**
         * 能源消耗，激活(非正常用户使用模式，如拖车、售后。这个模式的进入需要满足一系列条件)
         */
        int ACTIVE = 0x04;

        /**
         * 驾驶(动力系统激活，准备输出扭矩。全功能有效。)
         */
        int DRIVING = 0x05;

        /**
         * 未知
         */
        int UNKNOWN = 0xff;
    }

    @IntDef({ENDURANCE_MILEAGE_EV,
            BATTERY_PERCENTAGE,
            AVG_ENERGY_CONSUMPTION,
            CAR_MODE,
            USAGE_MODE,
            POWER_STATE,
            AQI_INDOOR,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
