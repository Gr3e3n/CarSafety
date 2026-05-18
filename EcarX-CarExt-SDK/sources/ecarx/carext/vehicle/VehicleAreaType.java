//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// This class is only designed to provide constants for VehicleAreaType. The constants should
// exactly be same as VehicleAreaType in /hardware/interfaces/automotive/vehicle/2.0/types.hal.
public final class VehicleAreaType {
    /** Used for global properties */
    public static final int VEHICLE_AREA_TYPE_GLOBAL = 0;
    /** Area type is Window */
    public static final int VEHICLE_AREA_TYPE_WINDOW = 2;
    /** Area type is Seat */
    public static final int VEHICLE_AREA_TYPE_SEAT = 3;
    /** Area type is Door */
    public static final int VEHICLE_AREA_TYPE_DOOR = 4;
    /** Area type is Mirror */
    public static final int VEHICLE_AREA_TYPE_MIRROR = 5;
    /** Area type is Wheel */
    public static final int VEHICLE_AREA_TYPE_WHEEL = 6;
    private VehicleAreaType() {}

    /** @hide */
    @IntDef({
            VEHICLE_AREA_TYPE_GLOBAL,
            VEHICLE_AREA_TYPE_WINDOW,
            VEHICLE_AREA_TYPE_SEAT,
            VEHICLE_AREA_TYPE_DOOR,
            VEHICLE_AREA_TYPE_MIRROR,
            VEHICLE_AREA_TYPE_WHEEL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface VehicleAreaTypeValue {}
}
