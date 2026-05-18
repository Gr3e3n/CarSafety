//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;
/**
 * VehicleAreaWindow is an abstraction for a window in a car. Some car APIs may provide control per
 * window and values defined here should be used to distinguish different windows.
 */
public final class VehicleAreaWindow {
    public static final int WINDOW_FRONT_WINDSHIELD = 0x0001;
    public static final int WINDOW_REAR_WINDSHIELD = 0x0002;
    public static final int WINDOW_ROW_1_LEFT = 0x0010;
    public static final int WINDOW_ROW_1_RIGHT = 0x0040;
    public static final int WINDOW_ROW_2_LEFT = 0x0100;
    public static final int WINDOW_ROW_2_RIGHT = 0x0400;
    public static final int WINDOW_ROW_3_LEFT = 0x1000;
    public static final int WINDOW_ROW_3_RIGHT = 0x4000;
    public static final int WINDOW_ROOF_TOP_1 = 0x10000;
    public static final int WINDOW_ROOF_TOP_2 = 0x20000;

    private VehicleAreaWindow() {}
}
