/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.data;

/**
 * Network type observer.
 *
 * @since 4
 */
public interface NetworkTypeListener {
    /**
     * Called when the network type changed.
     *
     * @param type the network type current in use.
     * @since 4
     */
    void onTypeChanged(int type);
}