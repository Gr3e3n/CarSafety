/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

/**
 * A listener of connectivity events.
 *
 * @since 5
 */
public interface ConnectivityListener {

    /**
     * Called when the connected status changed.
     *
     * @since 5
     */
    void onStatusChanged(boolean isConnected);
}
