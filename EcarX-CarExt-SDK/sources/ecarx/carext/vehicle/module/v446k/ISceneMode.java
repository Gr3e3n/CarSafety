//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 场景模式
 */
public interface ISceneMode {
    /**
     * Check whether the current scene mode can be projected to the instrument(Original AdaptAPI: IVehicle#SETTING_FUNC_SCENE_MODE_CAST_STATE)
     * 查询当前情景模式是否可以投屏到仪表（原AdaptAPI：IVehicle#SETTING_FUNC_SCENE_MODE_CAST_STATE）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link SceneModeCastState} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-08-23 10:07:42
     */
    int SCENE_MODE_CAST_STATE = 0x00020d00;

    /**
     * auto show mode enter conditions
     * 展车模式进入条件
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link AutoShowModeStatus} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-09 18:05:16
     */
    int AUTO_SHOW_MODE_ENTER_CONDITIONS = 0x00024e00;

    @IntDef({AutoShowModeStatus.MEET,
            AutoShowModeStatus.NOT_MEET,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface AutoShowModeStatus {
        /**
         * 进入展车模式条件满足，FWK通知APP显示展车模式授权弹窗
         */
        int MEET = 0x01;

        /**
         * 进入展车模式条件不满足，FWK通知APP展车展车模式无法进入弹窗，或APP展示展车模式授权弹窗时，进入展车模式条件变为不满足，FWK通知APP取消授权弹窗
         */
        int NOT_MEET = 0x02;
    }

    @IntDef({SCENE_MODE_CAST_STATE,
            AUTO_SHOW_MODE_ENTER_CONDITIONS,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({SceneModeCastState.CAST_ALLOW,
            SceneModeCastState.CAST_NOT_ALLOW,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface SceneModeCastState {
        /**
         * 允许投屏
         */
        int CAST_ALLOW = 0x01;

        /**
         * 不允许投屏
         */
        int CAST_NOT_ALLOW = 0x02;
    }
}
