//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 全球导航卫星系统
 */
public interface GNSS {
    /**
     * Location upload cloud authorization days
     * 定位上传云端授权天数，单位：天，原ID：SETTING_FUNC_CAR_TCAM_LOCATOR_AUTH_CYCLE, 获取支持哪些天数用ICarConfig.getSupportValues()，返回示例： 90 180 360
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-22 10:59:25
     */
    int GNSS_LOCATOR_AUTH_CYCLE = 0x0001ff00;

    /**
     * Starting time of authorization for uploading to the cloud location
     * 定位上传云端的授权开始时间，示例：Integer[] startTime = {2024, 8, 21}，原ID：SETTING_FUNC_CAR_TCAM_LOCATOR_UPDATE_AUTH_TIME
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-22 11:03:06
     */
    int GNSS_LOCATOR_UPDATE_AUTH_TIME = 0x00020000;

    @IntDef({GNSS_LOCATOR_AUTH_CYCLE,
            GNSS_LOCATOR_UPDATE_AUTH_TIME,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
