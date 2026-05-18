//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;

/**
 * VehicleAreaMirror is an abstraction for a mirror in a car. Some car APIs may provide control per
 * mirror and values defined here should be used to distinguish different mirrors.
 */
public final class VehicleAreaMirror {
    public static final int MIRROR_DRIVER_LEFT   = 0x00000001;
    public static final int MIRROR_DRIVER_RIGHT  = 0x00000002;
    public static final int MIRROR_DRIVER_CENTER = 0x00000004;

    private VehicleAreaMirror() {}
}
