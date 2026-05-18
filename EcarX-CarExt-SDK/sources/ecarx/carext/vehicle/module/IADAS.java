//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 高级驾驶辅助系统
 */
public interface IADAS {
    /**
     * the setting to activate/deactivate Autonomous Emergency Braking(AEB) function&lt;br&gt;
     * 自动紧急制动，原值SETTING_FUNC_AUTONOMOUS_EMERGENCY_BRAKING
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type true/false 
     * </ul>
     * fei.hu@ecarxgroup.com created for project hongqi at 2024-06-18 17:57:06
     */
    int AUTONOMOUS_EMERGENCY_BRAKING = 0x00005f00;

    /**
     * Allows the user to change settings for Forward Collision Warning sensitivity&lt;br&gt;
     * 前碰撞预警灵敏等级,原值SETTING_FUNC_FORWARD_COLLISION_WARN_SNVTY
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link ForwardCollisionWarnSnvtyMode} property type
     * </ul>
     * fei.hu@ecarxgroup.com created for project hongqi at 2024-06-18 20:06:05
     */
    int FORWARD_COLLISION_WARN_SNVT = 0x00007200;

    /**
     * DOW, to activate and deactivate Door open warning&lt;br&gt;
     * 车门开启提醒(DOW)开关，原值：SETTING_FUNC_DOOR_OPEN_WARN_ACTIVE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * fei.hu@ecarxgroup.com created for project hongqi at 2024-06-18 20:09:24
     */
    int DOOR_OPEN_WARN_ACTIVE = 0x00007400;

    /**
     * RCW，the setting to activate/deactivate the Rear Collision Warning(RCW) function&lt;br&gt;
     * 后碰撞预警，原值：SETTING_FUNC_REAR_COLLISION_WARNING
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * fei.hu@ecarxgroup.com created for project hongqi at 2024-06-18 20:11:29
     */
    int REAR_COLLISION_WARNING = 0x00007600;

    /**
     * intelligent driving assistance mode selection, etc: ACC ISS(SACC) HNOA
     * 智能驾驶辅助模式选择 ACC ICC(SACC) HNOA 等
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link IntelligentDrivingMode} property type
     * </ul>
     * fei.hu@ecarxgroup.com created for project hongqi at 2024-06-18 20:19:25
     */
    int INTELLIGENT_DRIVING_ASSISTANCE_MODE = 0x00007a00;

    /**
     * Pilot driver assistance switch  
     * 领航驾驶辅助开关,原值：SETTING_FUNC_AUTO_LANE_CHANGE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * fei.hu@ecarxgroup.com created for project hongqi at 2024-06-18 20:31:55
     */
    int AUTO_LANE_CHANGE = 0x00007e00;

    /**
     * the setting to activate/deactivate Driver Performance Support(DPS) function&lt;br&gt;
     * 驾驶员状态监测，原值：SETTING_FUNC_DRIVER_PERFOR_SUPPORT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * fei.hu@ecarxgroup.com created for project hongqi at 2024-06-18 20:38:37
     */
    int DRIVER_PERFOR_SUPPORT = 0x00008300;

    /**
     * the user can change settings for Steering Assistance Level
     * 方向盘辅助力级别,原ID为SETTING_FUNC_STEERING_ASSISTANCE_LEVEL
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link SteeringAssistanceLevel} property type
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 11:03:43
     */
    int STEERING_ASSISTANCE_LEVEL = 0x0000a500;

    /**
     * lane departure waning
     * 车道偏离预警开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 11:23:54
     */
    int LANE_DEPARTURE_WARNING = 0x0000ab00;

    /**
     * Choose the fusion ACC {@link IVehicle#SPEED_CONTROL_MODE_ACC} with TSR(TSI: Traffic Sigh Info) or not
     * 自动识别限速(ACC + TSR)
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 14:49:58
     */
    int ACC_WITH_TSR = 0x0000ed00;

    /**
     * the setting to activate/deactivate Lane Keeping Aid function
     * 车道辅助保持开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 14:51:08
     */
    int LANE_KEEPING_AID = 0x0000ee00;

    /**
     * to change Lane Keeping Aid warning mode
     * 车道保持辅助警告类别设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link LaneKeepingAidWarningMode} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 15:54:20
     */
    int LANE_KEEPING_AID_WARNING = 0x0000ef00;

    /**
     * the setting to activate/deactivate  Rear Cross Traffic Alert(RCTA) function
     * 后方交叉路口危险警报(倒车侧向辅助)
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 15:55:34
     */
    int REAR_CROSS_TRAFFIC_ALERT = 0x0000f000;

    /**
     * the setting to choose Lane Change Waring(LCW) mode
     * 变道警示模式设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link LaneChangeWarningMode} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 15:58:20
     */
    int LANE_CHANGE_WARNING_MODE = 0x0000f100;

    /**
     * Emergency Lane Keeping Assist ELK function adjustment
     * 紧急车道保持辅助ELK功能开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-08-09 09:21:59
     */
    int EMERGENCY_LANE_KEEP_AID = 0x0001c200;

    /**
     * Intelligent speed limit and speeding reminder mode adjustment
     * 限速警告模式设置&lt;br&gt; 原id：SETTING_FUNC_SPEED_LIMIT_WARNING_MODE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link SpeedLimitationMode} property type
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-08-09 09:23:30
     */
    int SPEED_LIMIT_WARNING_MODE = 0x0001c300;

    /**
     * Speed Limit Warning Offset Value Setting
     * 限速警报偏差值设置,原 ID SETTING_FUNC_SPEED_LIMIT_WARNING_OFFSET
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 正负 int 值，具体值由业务约定 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 10:57:54
     */
    int SPEED_LIMIT_WARNING_OFFSET_VALUE = 0x0001dd00;

    @IntDef({SpeedLimitationMode.VISUAL,
            SpeedLimitationMode.SOUND,
            SpeedLimitationMode.VISUAL_SOUND,
            SpeedLimitationMode.CLOSE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface SpeedLimitationMode {
        /**
         * 视觉
         */
        int VISUAL = 0x01;

        /**
         * 声音
         */
        int SOUND = 0x02;

        /**
         * 声音+视觉
         */
        int VISUAL_SOUND = 0x03;

        /**
         * 关闭
         */
        int CLOSE = 0xff;
    }

    @IntDef({IntelligentDrivingMode.ACC_MODE,
            IntelligentDrivingMode.ICC_MODE,
            IntelligentDrivingMode.HNOA_MODE,
            IntelligentDrivingMode.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface IntelligentDrivingMode {
        /**
         * ACC模式
         */
        int ACC_MODE = 0x01;

        /**
         * ICC模式
         */
        int ICC_MODE = 0x02;

        /**
         * 领航辅助
         */
        int HNOA_MODE = 0x04;

        int OFF = 0xff;
    }

    @IntDef({ForwardCollisionWarnSnvtyMode.LOW,
            ForwardCollisionWarnSnvtyMode.NORMAL,
            ForwardCollisionWarnSnvtyMode.HIGH,
            ForwardCollisionWarnSnvtyMode.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface ForwardCollisionWarnSnvtyMode {
        /**
         * 低
         */
        int LOW = 0x01;

        /**
         * 中
         */
        int NORMAL = 0x02;

        /**
         * 高
         */
        int HIGH = 0x03;

        int OFF = 0xff;
    }

    @IntDef({LaneKeepingAidWarningMode.SOUND,
            LaneKeepingAidWarningMode.HAPTIC,
            LaneKeepingAidWarningMode.SOUND_HAPTIC,
            LaneKeepingAidWarningMode.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface LaneKeepingAidWarningMode {
        /**
         * Lane keeping aid mode: sound(声音)
         */
        int SOUND = 0x01;

        /**
         * Lane keeping aid mode: haptic(振动)
         */
        int HAPTIC = 0x02;

        /**
         * Lane keeping aid mode: sound(声音) & haptic(振动)
         */
        int SOUND_HAPTIC = 0x03;

        /**
         * Lane keeping aid mode: off(关)
         */
        int OFF = 0x04;
    }

    @IntDef({SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_HEAVY,
            SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_MEDIUM,
            SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_SO,
            SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_AUTO,
            SteeringAssistanceLevel.STEERING_ASSISTANCE_LEVEL_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface SteeringAssistanceLevel {
        /**
         * steering assistance level high
         */
        int STEERING_ASSISTANCE_LEVEL_HEAVY = 0x01;

        /**
         * steering assistance level medium
         */
        int STEERING_ASSISTANCE_LEVEL_MEDIUM = 0x02;

        /**
         * steering assistance level low
         */
        int STEERING_ASSISTANCE_LEVEL_SO = 0x03;

        /**
         * steering assistance level auto
         */
        int STEERING_ASSISTANCE_LEVEL_AUTO = 0x04;

        /**
         * steering assistance level off
         */
        int STEERING_ASSISTANCE_LEVEL_OFF = 0xff;
    }

    @IntDef({LaneChangeWarningMode.VISUAL,
            LaneChangeWarningMode.SOUND,
            LaneChangeWarningMode.VISUAL_SOUND,
            LaneChangeWarningMode.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface LaneChangeWarningMode {
        /**
         * lane change warning mode: visual
         */
        int VISUAL = 0x01;

        /**
         * lane change warning mode: sound
         */
        int SOUND = 0x02;

        /**
         * lane change warning mode: visual and sound
         */
        int VISUAL_SOUND = 0x03;

        /**
         * lane change warning mode: off
         */
        int OFF = 0xff;
    }

    @IntDef({AUTONOMOUS_EMERGENCY_BRAKING,
            FORWARD_COLLISION_WARN_SNVT,
            DOOR_OPEN_WARN_ACTIVE,
            REAR_COLLISION_WARNING,
            INTELLIGENT_DRIVING_ASSISTANCE_MODE,
            AUTO_LANE_CHANGE,
            DRIVER_PERFOR_SUPPORT,
            STEERING_ASSISTANCE_LEVEL,
            LANE_DEPARTURE_WARNING,
            ACC_WITH_TSR,
            LANE_KEEPING_AID,
            LANE_KEEPING_AID_WARNING,
            REAR_CROSS_TRAFFIC_ALERT,
            LANE_CHANGE_WARNING_MODE,
            EMERGENCY_LANE_KEEP_AID,
            SPEED_LIMIT_WARNING_MODE,
            SPEED_LIMIT_WARNING_OFFSET_VALUE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
