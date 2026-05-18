//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Auto Parking Assist and History Parking Assist (APA and HPA)
 */
public interface IAPA {
    /**
     * Intelligent parking switch
     * 智能泊车开关  
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-08 10:22:09
     */
    int APA_INTELLIGENT_PARKING_SWITCH = 0x00013e00;

    @IntDef({APA_INTELLIGENT_PARKING_SWITCH,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
