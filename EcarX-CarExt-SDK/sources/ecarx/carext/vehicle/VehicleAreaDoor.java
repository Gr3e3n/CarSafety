//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;

/**
 * VehicleAreaDoor is an abstraction for a door in a car. Some car APIs may provide control per
 * door and values defined here should be used to distinguish different doors.
 */
public final class VehicleAreaDoor {
    public static final int DOOR_ROW_1_LEFT = 0x00000001;
    public static final int DOOR_ROW_1_RIGHT = 0x00000004;
    public static final int DOOR_ROW_2_LEFT = 0x00000010;
    public static final int DOOR_ROW_2_RIGHT = 0x00000040;
    public static final int DOOR_ROW_3_LEFT = 0x00000100;
    public static final int DOOR_ROW_3_RIGHT = 0x00000400;
    public static final int DOOR_HOOD = 0x10000000;
    public static final int DOOR_REAR = 0x20000000;

    private VehicleAreaDoor() {}
}
