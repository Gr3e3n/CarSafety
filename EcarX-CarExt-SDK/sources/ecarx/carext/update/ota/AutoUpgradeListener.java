/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

/**
 * A listener of automatic upgrade for OTA.
 *
 * @since 8
 */
public interface AutoUpgradeListener {

    /**
     * Called when the automatic upgrade status changed.
     *
     * @param autoUpdate switch status.
     * @param timestamp  automatic upgrade time.
     * @since 8
     */
    void onAutoUpgradeChanged(boolean autoUpdate, long timestamp);
}
