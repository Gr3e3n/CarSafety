//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Day mode brightness functions (CSD/PSD/RSD),  \nIf you want to change the theme of the application, try to use {@link android.app.UiModeManager}  \nand application resource with -night or -notnight
 */
public interface IDayMode {
    /**
     * Central control brightness auto-adjustment switch, original ID: SETTING_FUNC_BRIGHTNESS_AUTO_ADJUST
     * 中控亮度自动调节开关，原 ID 为 SETTING_FUNC_BRIGHTNESS_AUTO_ADJUST
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 14:28:59
     */
    int BRIGHTNESS_AUTO_ADJUST = 0x0001e600;

    @IntDef({BRIGHTNESS_AUTO_ADJUST,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
