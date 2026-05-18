//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Object used to indicate the area value for car properties which have area type
 * {@link VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}.
 * <p>
 * The constants defined by {@link VehicleAreaSeat} indicate the position for area type
 * {@link VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}. A property can have a single or a combination of
 * positions. Developers can query the position using
 * {@link ICarManager#getAreaId(int, int)}.
 * </p><p>
 * Refer to {@link ICarConfig#getAreaIds()} for more information about
 * areaId.
 * </p>
 */

// This class is only designed to provide constants for VehicleAreaSeat. The constants should
// be same as VehicleAreaSeat in /hardware/interfaces/automotive/vehicle/2.0/types.hal.
public final class VehicleAreaSeat {
    /** List of vehicle's seats. */
    public static final int SEAT_UNKNOWN = 0;
    /** Row 1 left side seat*/
    public static final int SEAT_ROW_1_LEFT = 0x0001;
    /** Row 1 center seat*/
    public static final int SEAT_ROW_1_CENTER = 0x0002;
    /** Row 1 right side seat*/
    public static final int SEAT_ROW_1_RIGHT = 0x0004;
    /** Row 2 left side seat*/
    public static final int SEAT_ROW_2_LEFT = 0x0010;
    /** Row 2 center seat*/
    public static final int SEAT_ROW_2_CENTER = 0x0020;
    /** Row 2 right side seat*/
    public static final int SEAT_ROW_2_RIGHT = 0x0040;
    /** Row 3 left side seat*/
    public static final int SEAT_ROW_3_LEFT = 0x0100;
    /** Row 3 center seat*/
    public static final int SEAT_ROW_3_CENTER = 0x0200;
    /** Row 3 right side seat*/
    public static final int SEAT_ROW_3_RIGHT = 0x0400;

    
    @IntDef({
            SEAT_UNKNOWN,
            SEAT_ROW_1_LEFT,
            SEAT_ROW_1_CENTER,
            SEAT_ROW_1_RIGHT,
            SEAT_ROW_2_LEFT,
            SEAT_ROW_2_CENTER,
            SEAT_ROW_2_RIGHT,
            SEAT_ROW_3_LEFT,
            SEAT_ROW_3_CENTER,
            SEAT_ROW_3_RIGHT
    })
    @Retention(RetentionPolicy.SOURCE)

    public @interface Enum {}
    private VehicleAreaSeat() {}

    
    public static final int SIDE_LEFT = -1;
    
    public static final int SIDE_CENTER = 0;
    
    public static final int SIDE_RIGHT = 1;
    /**
     * Convert row number and side into {@link Enum}.
     *
     * @param rowNumber should be 1, 2 or 3
     * @param side {@link #SIDE_LEFT}. {@link #SIDE_CENTER}, {@link #SIDE_RIGHT}.
     *
     **/
    @Enum
    public static int fromRowAndSide(int rowNumber, int side) {
        if (rowNumber < 1 || rowNumber > 3) {
            return SEAT_UNKNOWN;
        }
        if (side < -1 || side > 1) {
            return SEAT_UNKNOWN;
        }
        int seat = 0x1;
        seat = seat << ((rowNumber - 1) * 4);
        seat = seat << (side + 1);
        return seat;
    }

}
