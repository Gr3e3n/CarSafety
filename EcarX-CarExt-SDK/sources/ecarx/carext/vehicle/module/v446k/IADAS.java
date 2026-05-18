//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 高级驾驶辅助系统
 */
public interface IADAS {
    /**
     * Driving Assistance: Lane Change Strategy, original ID IADAS.SETTING_FUNC_AI_ASSIST_LANE_CHANGE_STRATEGY
     * 驾驶辅助：变道策略/风格,原 ID IADAS.SETTING_FUNC_AI_ASSIST_LANE_CHANGE_STRATEGY
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link LaneChangeStrategy} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 10:03:03
     */
    int LANE_CHANGE_STRATEGY = 0x0001d700;

    /**
     *  Driving Assistance: Voice Broadcast Mode, original ID SETTING_FUNC_VOICE_BROADCAST_MODE
     * 驾驶辅助：语音播报模式，原 ID 为 SETTING_FUNC_VOICE_BROADCAST_MODE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link VoiceBroadcastMode} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 10:30:02
     */
    int VOICE_BROADCAST_MODE = 0x0001da00;

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

    /**
     * Driver Performance Support（DPS）Authorized Time
     * DPS(驾驶疲劳检测)授权时间，原 ID 为 SETTING_FUNC_DPS_AUTHORIZED_TIME
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Long} property type 具体值由业务约定 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 11:26:18
     */
    int DRIVER_PERFORMANCE_AUTHORIZED_TIME = 0x0001df00;

    /**
     * DPS start time setting (Driver Performance Support)
     * DPS设置开始时间 (驾驶员状态监测)，原AdaptAPI为IADAS#SETTING_FUNC_DPS_UPDATE_AUTHORIZED_TIME
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type [0]代表年(四位，如2024)  [1]代表月（最多两位，不补0） [2]代表日（最多两位，不补0） 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-08-30 15:41:25
     */
    int DPS_UPDATE_AUTHORIZED_TIME = 0x00022300;

    /**
     * DCL(Data Closed-Loop) Vehicle data collection time update 
     * DCL(Data Closed-Loop)数据闭环，车辆数据采集更新时间
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type [0]代表年(四位，如2024)  [1]代表月（最多两位，不补0） [2]代表日（最多两位，不补0） 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-08-30 16:19:47
     */
    int DCL_UPDATE_AUTHORIZED_TIME = 0x00022700;

    /**
     * NOA voice command feedback:Lane change reminder, original ID:IVehicle.AsyLanChgSod
     * NOA语音指令反馈：变道提醒，原ID：IVehicle.AsyLanChgSod
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 17:24:40
     */
    int SOUND_LANE_CHANGE_CALLBACK = 0x00027200;

    /**
     * NOA can activate reminder, original ID:IVehicle.AsyNoaActvSuggestSound
     * NOA语音提醒：智能巡航可激活，原ID：IVehicle.AsyNoaActvSuggestSound
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 17:27:15
     */
    int SOUND_NOA_CAN_ACTIVATE_CALLBACK = 0x00027300;

    /**
     * NOA driving assistance on/off reminder, original ID:IVehicle.AsyNoaDistanMsg
     * NOA语音提醒：驾驶辅助开启/退出，原ID：IVehicle.AsyNoaDistanMsg
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 17:39:01
     */
    int SOUND_NOA_STATUS_CALLBACK = 0x00027400;

    /**
     * NOA driving assistance exit reminder, exit navigation driving assistance 2 kilometers/500m ahead, original ID:IVehicle.AsyNoaDistanMsgSound
     * NOA驾驶辅助退出提醒，前方2公里/500m退出领航驾驶辅助，原ID：IVehicle.AsyNoaDistanMsgSound
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 17:42:28
     */
    int SOUND_NOA_EXIT_ALERT_CALLBACK = 0x00027500;

    /**
     * First activation reminder, such as ICC/ACC, original ID:IVehicle.AsyFirstTiActvn
     * 首次激活提醒，例如ICC/ACC，原ID：IVehicle.AsyFirstTiActvn
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 17:46:00
     */
    int SOUND_FIRST_ACTIVATION_CALLBACK = 0x00027600;

    /**
     * Reason reminder for inability to activate ICC function, original ID:IVehicle.AsyAutDrvgAvlSound
     * ICC功能无法激活的原因提醒，原ID：IVehicle.AsyAutDrvgAvlSound
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 17:49:58
     */
    int SOUND_ICC_CANNOT_ACTIVATE_CALLBACK = 0x00027700;

    /**
     * ICC voice prompt: Drive with caution, original ID:IVehicle.AsyInformForDrvrSound
     * ICC语音提示：小心驾驶，原ID：IVehicle.AsyInformForDrvrSound
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 17:51:57
     */
    int SOUND_CAREFUL_DRIVE_CALLBACK = 0x00027800;

    /**
     * DPS eye detachment detection alarm, original ID:IVehicle.AsyEyesOffWarnRqrdSound
     * DPS脱眼检测报警，原ID：IVehicle.AsyEyesOffWarnRqrdSound
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 17:54:32
     */
    int SOUND_EYES_OFF_WARN_CALLBACK = 0x00027900;

    /**
     * ACC voice prompt: Reason for inability to activate function, original ID:IVehicle.AvlStsForLongAutDrvSound
     * ACC语音提示：功能无法激活的原因，原ID：IVehicle.AvlStsForLongAutDrvSound
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 17:58:11
     */
    int SOUND_ACC_CANNOT_ACTIVATE_CALLBACK = 0x00027a00;

    /**
     * Voice reminder: ACC function has been enter/exited, original ID:IVehicle.CnclWarnLgtForAutDrvSound
     * 语音提醒：ACC功能进入/退出，原ID：IVehicle.CnclWarnLgtForAutDrvSound
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 18:02:13
     */
    int SOUND_ACC_STATUS_CALLBACK = 0x00027b00;

    /**
     * ICC intelligent cruise exit, original ID:IVehicle.CnclWarnForAutDrvSound
     * ICC智能巡航退出，原ID：IVehicle.CnclWarnForAutDrvSound
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-18 18:11:34
     */
    int SOUND_ICC_STATUS_CALLBACK = 0x00027c00;

    /**
     * Voice reminder: Reasons why NOA cannot be activated
     * 语音提醒：NOA无法激活的原因
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-20 18:16:10
     */
    int SOUND_NOA_CANNOT_ACTIVATE_CALLBACK = 0x00027d00;

    /**
     * Voice reminder: NOA takeover reminder
     * 语音提醒：NOA接管提醒
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-20 19:00:40
     */
    int SOUND_NOA_TAKE_OVER_CALLBACK = 0x00027e00;

    /**
     * NOA voice reminder: regular reminder
     * NOA语音提醒：常规提醒
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-20 19:03:56
     */
    int SOUND_NOA_REGULAR_CALLBACK = 0x00027f00;

    /**
     * ICC voice reminder: lane change reminder 2
     * ICC语音提醒：变道提醒2
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-20 19:05:56
     */
    int SOUND_LANE_CHANGE_EXTEND_CALLBACK = 0x00028000;

    /**
     * Voice reminder: Hand off alarm
     * 语音提醒：脱手报警
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-11-21 14:10:08
     */
    int SOUND_HAND_OFF_CALLBACK = 0x00028100;

    /**
     * Rear Cross Traffic Alert(RCTA) occur on the left
     * RCTA 左报警
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-03-31 10:04:35
     */
    int RCTA_WARNING_LEFT = 0x00032700;

    /**
     * Rear Cross Traffic Alert(RCTA) occur on the right
     * RCTA 右报警
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-03-31 10:04:43
     */
    int RCTA_WARNING_RIGHT = 0x00032800;

    @IntDef({VoiceBroadcastMode.DETAILED,
            VoiceBroadcastMode.STREAMLINED,
            VoiceBroadcastMode.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface VoiceBroadcastMode {
        /**
         * 详细播报
         */
        int DETAILED = 0x01;

        /**
         * 精简播报
         */
        int STREAMLINED = 0x02;

        /**
         * 关
         */
        int OFF = 0xff;
    }

    @IntDef({LANE_CHANGE_STRATEGY,
            VOICE_BROADCAST_MODE,
            SPEED_LIMIT_WARNING_OFFSET_VALUE,
            DRIVER_PERFORMANCE_AUTHORIZED_TIME,
            DPS_UPDATE_AUTHORIZED_TIME,
            DCL_UPDATE_AUTHORIZED_TIME,
            SOUND_LANE_CHANGE_CALLBACK,
            SOUND_NOA_CAN_ACTIVATE_CALLBACK,
            SOUND_NOA_STATUS_CALLBACK,
            SOUND_NOA_EXIT_ALERT_CALLBACK,
            SOUND_FIRST_ACTIVATION_CALLBACK,
            SOUND_ICC_CANNOT_ACTIVATE_CALLBACK,
            SOUND_CAREFUL_DRIVE_CALLBACK,
            SOUND_EYES_OFF_WARN_CALLBACK,
            SOUND_ACC_CANNOT_ACTIVATE_CALLBACK,
            SOUND_ACC_STATUS_CALLBACK,
            SOUND_ICC_STATUS_CALLBACK,
            SOUND_NOA_CANNOT_ACTIVATE_CALLBACK,
            SOUND_NOA_TAKE_OVER_CALLBACK,
            SOUND_NOA_REGULAR_CALLBACK,
            SOUND_LANE_CHANGE_EXTEND_CALLBACK,
            SOUND_HAND_OFF_CALLBACK,
            RCTA_WARNING_LEFT,
            RCTA_WARNING_RIGHT,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({LaneChangeStrategy.GENTLE,
            LaneChangeStrategy.STANDARD,
            LaneChangeStrategy.RADICAL,
            LaneChangeStrategy.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface LaneChangeStrategy {
        /**
         * 平缓
         */
        int GENTLE = 0x01;

        /**
         * 标准
         */
        int STANDARD = 0x02;

        /**
         * 快速
         */
        int RADICAL = 0x03;

        /**
         * 关闭
         */
        int OFF = 0xff;
    }
}
