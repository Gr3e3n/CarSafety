//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 空调
 */
public interface IHvac {
    /**
     * temperature incremental adjust
     * 温度相对调节/增量调节
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 0 减；1 加 
     * </ul>
     * wangyang.xin@ecarxgroup.com created for project hongqi at 2024-06-18 14:38:10
     */
    int TEMPERATURE_INCREMENTAL_ADJUST = 0x00002c00;

    /**
     * Fan speed incremental adjustment
     * 空调风扇速度风量相对调节/增量调节
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 0 减；1 加 
     * </ul>
     * wangyang.xin@ecarxgroup.com created for project hongqi at 2024-06-18 14:40:22
     */
    int FAN_SPEED_INCREMENTAL_ADJUST = 0x00002d00;

    /**
     * air circulation mode
     * 空调空气循环模式
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link CirculationMode} property type
     * </ul>
     * wangyang.xin@ecarxgroup.com created for project hongqi at 2024-06-18 15:07:02
     */
    int CIRCULATION = 0x00003600;

    /**
     * auto start steering wheel heating on/off setting
     * 方向盘自动加热开关设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type true 开 false 关 
     * </ul>
     * wangyang.xin@ecarxgroup.com created for project hongqi at 2024-06-18 15:20:17
     */
    int AUTO_STEERING_WHEEL_HEAT_SWITCH = 0x00004400;

    /**
     * climate blowing direction mode
     * 电子出风口风向模式风口模式
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DirectionMode} property type
     * </ul>
     * wangyang.xin@ecarxgroup.com created for project hongqi at 2024-06-21 14:57:03
     */
    int DIRECTION_MODE = 0x0000c800;

    /**
     * The setting to select(active) fragrance slot
     * 选择并启动具体的香氛槽
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link AirFragranceSlot} property type
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-08-22 15:15:41
     */
    int HVAC_AIR_FRAGRANCE_SLOT = 0x00020400;

    /**
     * Electronic air outlet switch
     * 空调电动出风口开关(原adaptApi接口HVAC_FUNC_ELECTRICAL_AIR_VENT)（FlymeAuto实现指用户主动的开关操作，并非指实车出风口是否出风）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-09-30 17:36:18
     */
    int ELECTRONIC_AIR_OUTLET_SWITCH = 0x00024c00;

    /**
     * Electric air outlet direction adjustment for air conditioner
     * 空调电动出风口方向调节(原adaptApi接口HVAC_FUNC_ELECTRICAL_AIR_VENT_POS)
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 三个值。第一个代表x轴横向位置坐标；第二个代表y轴纵向位置坐标；第三个代表z轴位置坐标； 
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-09-30 17:40:57
     */
    int ELECTRONIC_AIR_OUTLET_DIRECTION_ADJUST = 0x00024d00;

    @IntDef({CirculationMode.INTERNAL,
            CirculationMode.EXTERNAL,
            CirculationMode.AUTO,
            CirculationMode.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface CirculationMode {
        /**
         * 内循环
         */
        int INTERNAL = 0x01;

        /**
         * 外循环
         */
        int EXTERNAL = 0x02;

        /**
         * 自动
         */
        int AUTO = 0x03;

        /**
         * 关闭（似乎不需要）
         */
        int OFF = 0xff;
    }

    @IntDef({DirectionMode.DIRECTION_MODE_FOCUS,
            DirectionMode.DIRECTION_MODE_AVOID,
            DirectionMode.DIRECTION_MODE_CUSTOM,
            DirectionMode.DIRECTION_MODE_SWING,
            DirectionMode.DIRECTION_MODE_ADAPTIVE,
            DirectionMode.DIRECTION_MODE_MANUAL,
            DirectionMode.DIRECTION_MODE_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DirectionMode {
        /**
         * blowing direction mode: focus to driver and passenger聚焦对人吹
         */
        int DIRECTION_MODE_FOCUS = 0x01;

        /**
         * blowing direction mode: avoid from driver and passenger避开模式避人
         */
        int DIRECTION_MODE_AVOID = 0x02;

        /**
         * blowing direction mode: custom自定义
         */
        int DIRECTION_MODE_CUSTOM = 0x03;

        /**
         * 自动扫风
         */
        int DIRECTION_MODE_SWING = 0x04;

        /**
         * 自适应
         */
        int DIRECTION_MODE_ADAPTIVE = 0x05;

        /**
         * 手动
         */
        int DIRECTION_MODE_MANUAL = 0x06;

        /**
         * 关闭
         */
        int DIRECTION_MODE_OFF = 0xff;
    }

    @IntDef({AirFragranceSlot.AIR_FRAGRANCE_SLOT_1,
            AirFragranceSlot.AIR_FRAGRANCE_SLOT_2,
            AirFragranceSlot.AIR_FRAGRANCE_SLOT_3,
            AirFragranceSlot.AIR_FRAGRANCE_SLOT_4,
            AirFragranceSlot.AIR_FRAGRANCE_SLOT_5,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface AirFragranceSlot {
        /**
         * 值同 ecarx.carext.vehicle.VehicleAreaSeat#SEAT_ROW_1_LEFT
         */
        int AIR_FRAGRANCE_SLOT_1 = 0x01;

        /**
         * 值同 ecarx.carext.vehicle.VehicleAreaSeat#SEAT_ROW_1_CENTER
         */
        int AIR_FRAGRANCE_SLOT_2 = 0x02;

        /**
         * 值同 ecarx.carext.vehicle.VehicleAreaSeat#SEAT_ROW_1_RIGHT
         */
        int AIR_FRAGRANCE_SLOT_3 = 0x04;

        /**
         * 值同 ecarx.carext.vehicle.VehicleAreaSeat#SEAT_ROW_2_LEFT
         */
        int AIR_FRAGRANCE_SLOT_4 = 0x10;

        /**
         * 值同 ecarx.carext.vehicle.VehicleAreaSeat#SEAT_ROW_2_CENTER
         */
        int AIR_FRAGRANCE_SLOT_5 = 0x20;
    }

    @IntDef({TEMPERATURE_INCREMENTAL_ADJUST,
            FAN_SPEED_INCREMENTAL_ADJUST,
            CIRCULATION,
            AUTO_STEERING_WHEEL_HEAT_SWITCH,
            DIRECTION_MODE,
            HVAC_AIR_FRAGRANCE_SLOT,
            ELECTRONIC_AIR_OUTLET_SWITCH,
            ELECTRONIC_AIR_OUTLET_DIRECTION_ADJUST,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
