//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 抬头显示
 */
public interface IHUD {
    /**
     * HUD angle reset.
     * HUD角度复位，原ID：SETTING_FUNC_HUD_ANGLE_RESET
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 10:11:48
     */
    int HUD_ANGLE_RESET = 0x0001d900;

    /**
     * hud cast active 
     * 投屏服务是否投屏中
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-08-04 15:31:08
     */
    int HUD_CAST_ACTIVE = 0x00033a00;

    @IntDef({HUD_ANGLE_RESET,
            HUD_CAST_ACTIVE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
