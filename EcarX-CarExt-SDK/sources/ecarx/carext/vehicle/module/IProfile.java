//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用车偏好
 */
public interface IProfile {
    /**
     * Save current preferences
     * 用车偏好：保存当前偏好
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-05 14:36:15
     */
    int PROFILE_MEM_SAVE = 0x00012b00;

    /**
     * Switch to other preferences
     * 用车偏好：切换到其他偏好
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-05 14:40:13
     */
    int PROFILE_SWITCH_PREFERENCE = 0x00012e00;

    /**
     * Pop ups with changed preferences
     * 用车偏好：偏好改变后的弹框，弹框选项如：保存、恢复、另存为...
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-09 16:05:50
     */
    int PROFILE_CHANGED_POP = 0x00015d00;

    @IntDef({PROFILE_MEM_SAVE,
            PROFILE_SWITCH_PREFERENCE,
            PROFILE_CHANGED_POP,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
