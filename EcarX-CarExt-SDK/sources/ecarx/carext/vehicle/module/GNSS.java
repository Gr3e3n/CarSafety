//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 全球导航卫星系统
 */
public interface GNSS {
    /**
     * Vehicle Location Information Upload Switch: When the switch is turned on, the vehicle location information will be uploaded to the cloud for vehicle tracking purposes.
     * 车辆位置信息上传开关，当开关打开时，车辆位置信息将被上传至云端，供查找车辆使用
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-06-26 16:54:17
     */
    int GNSS_UPLOAD_LOCATION_SWITCH = 0x00010200;

    @IntDef({GNSS_UPLOAD_LOCATION_SWITCH,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
