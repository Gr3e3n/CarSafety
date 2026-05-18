//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Tcam Tbox
 */
public interface Tcam {
    /**
     * TCAM Reset control switch function
     * TCAM/TBOX复位开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 16:09:00
     */
    int TCAM_RESET = 0x0000f500;

    /**
     * Obtain TBOX/TCAM hardware version number
     * 获取TBOX/TCAM的硬件版本号
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code String} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-06-26 10:37:45
     */
    int HARDWARE_VERSION = 0x00010000;

    /**
     * Obtain TBOX/TCAM software version number
     * 获取TBOX/TCAM软件版本号
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code String} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-06-26 10:42:26
     */
    int SOFTWARE_VERSION = 0x00010100;

    @IntDef({TCAM_RESET,
            HARDWARE_VERSION,
            SOFTWARE_VERSION,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
