//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 空调
 */
public interface IHvac {
    /**
     * When CLEANZONE is unavailable, the icon displays in a grayed out state; Only when all of the following conditions are met, will the opening work be displayed: 1. All car doors and tailgate are closed; 2. All car windows are closed; 3. The air conditioning fan has started; 4. The internal and external circulation is in AUTO mode; Otherwise, it will display as not working.
     * CLEANZONE 不可用时，图标显示为置灰状态;只有满足所有以下条件时，显示开启工作：1.所有车门和尾门关闭；2.所有车窗关闭；3.空调风扇已启动；4.内外循环为AUTO模式；否则显示未开启工作。
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link CleanZoneState} property type
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-08-22 15:11:03
     */
    int HVAC_CLEAN_ZONE = 0x00020200;

    /**
     * to turn On or Off air fragrance
     * 香氛功能开启和关闭。（和香氛浓度等级重合，处于废弃状态）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-08-22 15:12:15
     */
    int HVAC_AIR_FRAGRANCE = 0x00020300;

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
     * to set air fragrance level.
     * 香氛浓度等级设置.香氛槽与AREA对应关系参考 {@link AirFragranceSlot}.不区分则传VehicleAreaSeat#SEAT_ROW_1_LEFT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link AirFragranceLevel} property type
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-08-22 15:20:12
     */
    int HVAC_AIR_FRAGRANCE_LEVEL = 0x00020500;

    /**
     * fragrance residue,fragrance remaining percentage, rang from 0 to 100
     * 香氛剩余量，取值范围0~100。香氛槽域AREA对应关系参考 {@link AirFragranceSlot}
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 0～100 
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-08-22 15:21:40
     */
    int HVAC_FRAGRANCE_MARGIN = 0x00020600;

    /**
     * The air conditioner dries automatically when the vehicle is locked. After the vehicle is electrically locked while driving, the air conditioner may automatically run the blower for a certain period of time to blow out the condensed water in the evaporator and air duct to prevent water accumulation and moldy odor from contaminating the cabin.
     * 锁车空调自干燥。车辆行驶下电锁车后，空调可能会自主运行鼓风机一定时间，吹出蒸发器和风道中的冷凝水，防止水积聚产生霉味污染座舱。
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-08-22 15:23:03
     */
    int HVAC_AUTO_DRY = 0x00020700;

    /**
     * Get the fragrance type ID installed in the fragrance slot。 The fragrance ID is used to match the fragrance information configured in the cloud.         * 0: indicates no fragrance&lt;br&gt;* Use with zone {@link AirFragranceSlot}&lt;br&gt;
     * 获取香氛槽安装的香氛类型ID。香氛ID用来和云端配置的香氛信息进行匹配。* Use with zone {@link AirFragranceSlot}&lt;br&gt;areaId{SEAT_ROW_1_LEFT,SEAT_ROW_1_CENTER,SEAT_ROW_1_RIGHT,SEAT_ROW_2_LEFT,SEAT_ROW_2_CENTER}
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-08-29 16:46:51
     */
    int AIR_FRAGRANCE_TYPE_ID = 0x00022100;

    /**
     * The low status of air fragrance for a special slot {@link AirFragranceSlot}&lt;br&gt;       * Use with zone {@link AirFragranceSlot}&lt;br&gt;
     * 香氛的剩余量是否不足。香氛槽域AREA对应关系参考 {@link AirFragranceSlot}
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type true:余量不足 false：余量正常 
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-09-02 13:03:35
     */
    int HVAC_AIR_FRAGRANCE_LOW = 0x00022a00;

    /**
     * When the air conditioning related functions are not available, a pop-up window will be displayed when the user clicks. For example, seat ventilation, heating, steering wheel heating, etc. Original DX interface HVAC_FUNC_HEAT_VENTILATION_POP
     * 空调相关功能不可用情况下，用户点击时进行弹窗提醒。例如座椅通风、加热、方向盘加热等。原DX接口HVAC_FUNC_HEAT_VENTILATION_POP
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HvacFaultNotify} property type
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-09-26 12:56:35
     */
    int HVAC_FAULT_NOTIFY = 0x00024800;

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

    /**
     * Realistic selection status of blowing mode in Auto mode, Original AdaptApi:HVAC_FUNC_FAN_DIRECTION_AUTO_STATE
     * Auto模式下吹风模式真实选中状态,原AdaptAPI:HVAC_FUNC_FAN_DIRECTION_AUTO_STATE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link FanDirectionAuto} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-11-08 15:57:05
     */
    int FAN_DIRECTION_AUTO_STATE = 0x00026800;

    /**
     * fan speed auto state, Original AdaptAPI: HVAC_FUNC_AUTO_STATUS
     * 自动风量状态, 原AdaptAPI：HVAC_FUNC_AUTO_STATUS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-11-08 16:35:44
     */
    int FAN_AUTO_STATUS = 0x00026900;

    /**
     * Steering Wheel Heating Automatic Start Indication
     * 方向盘加热自动启动指示。（原adaptApi接口HVAC_FUNC_AUTO_STEERSWITCH_ICON）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type True:已启动。  false：未启动 
     * </ul>
     * jian2.min@ecarxgroup.com created for project v446k at 2024-12-16 13:47:46
     */
    int STEERING_WHEEL_HEATING_AUTO_START = 0x0002ad00;

    @IntDef({CleanZoneState.ON,
            CleanZoneState.OFF,
            CleanZoneState.NONE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface CleanZoneState {
        /**
         * 激活
         */
        int ON = 0x01;

        /**
         * 未激活
         */
        int OFF = 0x02;

        /**
         * 未知,不显示
         */
        int NONE = 0xff;
    }

    @IntDef({HvacFaultNotify.SEAT_HEAT,
            HvacFaultNotify.SEAT_VENTILATION,
            HvacFaultNotify.WHEEL_HEAT,
            HvacFaultNotify.SEAT_HEAT_AUTO,
            HvacFaultNotify.SEAT_VENTILATION_AUTO,
            HvacFaultNotify.DEFROST_REAR,
            HvacFaultNotify.DEFROST_REARVIEW_MIRROR,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface HvacFaultNotify {
        /**
         * 座椅加热
         */
        int SEAT_HEAT = 0x01;

        /**
         * 座椅通风
         */
        int SEAT_VENTILATION = 0x02;

        /**
         * 方向盘加热
         */
        int WHEEL_HEAT = 0x03;

        /**
         * 座椅自动加热
         */
        int SEAT_HEAT_AUTO = 0x04;

        /**
         * 座椅自动通风
         */
        int SEAT_VENTILATION_AUTO = 0x05;

        /**
         * 后除霜
         */
        int DEFROST_REAR = 0x06;

        /**
         * 后视镜除霜
         */
        int DEFROST_REARVIEW_MIRROR = 0x07;
    }

    @IntDef({FanDirectionAuto.BLOWING_MODE_FACE,
            FanDirectionAuto.BLOWING_MODE_LEG,
            FanDirectionAuto.BLOWING_MODE_FACE_AND_LEG,
            FanDirectionAuto.BLOWING_MODE_FRONT_WINDOW,
            FanDirectionAuto.BLOWING_MODE_FACE_AND_FRONT_WIND,
            FanDirectionAuto.BLOWING_MODE_LEG_AND_FRONT_WINDO,
            FanDirectionAuto.BLOWING_ALL,
            FanDirectionAuto.BLOWING_MODE_AUTO_SWITCH,
            FanDirectionAuto.BLOWING_MODE_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface FanDirectionAuto {
        /**
         * blowing face
         */
        int BLOWING_MODE_FACE = 0x01;

        /**
         * blowing leg
         */
        int BLOWING_MODE_LEG = 0x02;

        /**
         * blowing face & leg
         */
        int BLOWING_MODE_FACE_AND_LEG = 0x03;

        /**
         * blowing front window
         */
        int BLOWING_MODE_FRONT_WINDOW = 0x04;

        /**
         * blowing face & front window
         */
        int BLOWING_MODE_FACE_AND_FRONT_WIND = 0x05;

        /**
         * blowing leg & front window
         */
        int BLOWING_MODE_LEG_AND_FRONT_WINDO = 0x06;

        /**
         * blowing all mode == 0x01|0x02|0x04
         */
        int BLOWING_ALL = 0x07;

        /**
         * blowing auto mode
         */
        int BLOWING_MODE_AUTO_SWITCH = 0x08;

        /**
         * blowing off
         */
        int BLOWING_MODE_OFF = 0xff;
    }

    @IntDef({HVAC_CLEAN_ZONE,
            HVAC_AIR_FRAGRANCE,
            HVAC_AIR_FRAGRANCE_SLOT,
            HVAC_AIR_FRAGRANCE_LEVEL,
            HVAC_FRAGRANCE_MARGIN,
            HVAC_AUTO_DRY,
            AIR_FRAGRANCE_TYPE_ID,
            HVAC_AIR_FRAGRANCE_LOW,
            HVAC_FAULT_NOTIFY,
            ELECTRONIC_AIR_OUTLET_SWITCH,
            ELECTRONIC_AIR_OUTLET_DIRECTION_ADJUST,
            FAN_DIRECTION_AUTO_STATE,
            FAN_AUTO_STATUS,
            STEERING_WHEEL_HEATING_AUTO_START,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
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

    @IntDef({AirFragranceLevel.AIR_FRAGRANCE_LEVEL_1,
            AirFragranceLevel.AIR_FRAGRANCE_LEVEL_2,
            AirFragranceLevel.AIR_FRAGRANCE_LEVEL_3,
            AirFragranceLevel.AIR_FRAGRANCE_LEVEL_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface AirFragranceLevel {
        /**
         * Air fragrance level 1
         */
        int AIR_FRAGRANCE_LEVEL_1 = 0x01;

        /**
         * Air fragrance level 2
         */
        int AIR_FRAGRANCE_LEVEL_2 = 0x02;

        /**
         * Air fragrance level 3
         */
        int AIR_FRAGRANCE_LEVEL_3 = 0x03;

        /**
         * Air fragrance level off
         */
        int AIR_FRAGRANCE_LEVEL_OFF = 0xff;
    }
}
