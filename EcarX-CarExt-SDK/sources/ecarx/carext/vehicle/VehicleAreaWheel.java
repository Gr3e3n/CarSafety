//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;

import androidx.annotation.IntDef;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Object used to indicate area value for car properties which have area type
 * {@link VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}.
 * <p>
 * The constants defined by {@link VehicleAreaWheel} indicate the position for area type
 * {@link VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}. A property can have a single or a combination of
 * positions. Developers can query the position using
 * {@link ICarManager#getAreaId(int, int)}.
 * </p><p>
 * Refer to {@link ICarConfig#getAreaIds()} for more information about areaId.
 * </p>
 */

// This class is only designed to provide constants for VehicleAreaWheel. The constants should
// exactly be same as VehicleAreaWheel in /hardware/interfaces/automotive/vehicle/2.0/types.hal.
public final class VehicleAreaWheel {
    /** Unknown wheel*/
    public static final int WHEEL_UNKNOWN = 0x00;
    /** Constant for left front wheel.*/
    public static final int WHEEL_LEFT_FRONT = 0x01;
    /** Constant for right front wheel.*/
    public static final int WHEEL_RIGHT_FRONT = 0x02;
    /** Constant for left rear wheel.*/
    public static final int WHEEL_LEFT_REAR = 0x04;
    /** Constant for right rear wheel.*/
    public static final int WHEEL_RIGHT_REAR = 0x08;

    @IntDef({
            WHEEL_UNKNOWN,
            WHEEL_LEFT_FRONT,
            WHEEL_RIGHT_FRONT,
            WHEEL_LEFT_REAR,
            WHEEL_RIGHT_REAR
    })
    @Retention(RetentionPolicy.SOURCE)

    public  @interface Enum {}
    private VehicleAreaWheel() {}
}
