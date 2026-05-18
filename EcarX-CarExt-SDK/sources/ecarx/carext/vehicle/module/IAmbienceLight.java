//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 氛围灯
 */
public interface IAmbienceLight {
    /**
     * Voice and ambient lighting linkage
     * 语音和氛围灯联动开关，（原AdaptAPI的ID为SETTING_FUNC_AMBIENCE_LIGHT_VOICE）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-07-09 17:26:26
     */
    int AMBIENCE_LIGHT_VOICE = 0x00016300;

    /**
     * Custom Ambient Light Intensity, original ID is SETTING_FUNC_AMBIENCE_LIGHT_INTENSITY_SET
     * 自定义氛围灯亮度，原 ID 为 SETTING_FUNC_AMBIENCE_LIGHT_INTENSITY_SET
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float} property type value : 0 ~ 100 
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-25 10:35:14
     */
    int AMBIENCE_LIGHT_INTENSITY = 0x0001a800;

    @IntDef({AMBIENCE_LIGHT_VOICE,
            AMBIENCE_LIGHT_INTENSITY,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
