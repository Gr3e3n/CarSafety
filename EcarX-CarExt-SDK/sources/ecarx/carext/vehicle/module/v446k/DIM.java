//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Driver Information Module
 */
public interface DIM {
    /**
     * Set the instrument cluster brightness action, original ID: SETTING_FUNC_SCENE_MODE_DIM_BRIGHTNESS_STATE
     * 设置仪表亮度动作， 原 ID 为 SETTING_FUNC_SCENE_MODE_DIM_BRIGHTNESS_STATE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DimBrightnessAction} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-26 15:33:14
     */
    int DIM_BRIGHTNESS_ACTION = 0x00021700;

    /**
     * Current instrument projection state, original ID: VENDOR_FUNC_PROJECTION_EXCLUSION
     * 当前仪表投屏状态，原 ID 为 VENDOR_FUNC_PROJECTION_EXCLUSION
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DimProjectionState} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-26 15:39:08
     */
    int DIM_PROJECTION_STATE = 0x00021800;

    /**
     * Set the instrument cluster projection action , start / stop casting , original ID：VENDOR_FUNC_PROJECTION_EXCLUSION 
     * 仪表投屏动作，发起/结束投屏，原 ID 为 VENDOR_FUNC_PROJECTION_EXCLUSION
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DimProjectionAction} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-26 15:47:20
     */
    int DIM_PROJECTION_ACTION = 0x00021900;

    /**
     * Vehicle fault alarm in zone A of the car instrument
     * 仪表显示整车A区故障告警
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 0代表无告警 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-08-30 16:13:51
     */
    int FAULT_ALARM_ZONE_A = 0x00022600;

    @IntDef({DimProjectionState.NAVI,
            DimProjectionState.SCENE,
            DimProjectionState.VIDEO,
            DimProjectionState.NONE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DimProjectionState {
        /**
         *  导航投屏中
         */
        int NAVI = 0x01;

        /**
         *  情景模式投屏中
         */
        int SCENE = 0x02;

        /**
         * 视频投屏中
         */
        int VIDEO = 0x04;

        /**
         *  没有投屏
         */
        int NONE = 0xff;
    }

    @IntDef({DimBrightnessAction.RESTORE,
            DimBrightnessAction.SCREEN_OFF,
            DimBrightnessAction.START_RESET,
            DimBrightnessAction.NONE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DimBrightnessAction {
        /**
         * 恢复仪表亮度
         */
        int RESTORE = 0x01;

        /**
         * 仪表息屏
         */
        int SCREEN_OFF = 0x02;

        /**
         * 开始休息
         */
        int START_RESET = 0x03;

        /**
         * 无
         */
        int NONE = 0xff;
    }

    @IntDef({DimProjectionAction.NAVI_START,
            DimProjectionAction.NAVI_END,
            DimProjectionAction.SCENT_START,
            DimProjectionAction.SCENE_END,
            DimProjectionAction.VIDEO_START,
            DimProjectionAction.VIDEO_END,
            DimProjectionAction.NONE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DimProjectionAction {
        /**
         * 导航投屏开始
         */
        int NAVI_START = 0x01;

        /**
         * 导航投屏结束
         */
        int NAVI_END = 0x02;

        /**
         * 情景模式投屏开始
         */
        int SCENT_START = 0x03;

        /**
         * 情景模式投屏结束
         */
        int SCENE_END = 0x04;

        /**
         * video模式开始
         */
        int VIDEO_START = 0x05;

        /**
         * video模式结束
         */
        int VIDEO_END = 0x06;

        /**
         * 无
         */
        int NONE = 0xff;
    }

    @IntDef({DIM_BRIGHTNESS_ACTION,
            DIM_PROJECTION_STATE,
            DIM_PROJECTION_ACTION,
            FAULT_ALARM_ZONE_A,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
