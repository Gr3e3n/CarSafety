/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

/**
 * Wi-Fi sta scan result observer.
 *
 * @since 4
 */
public interface StaScanResultListener {
    /**
     * Called when the scan finished.
     *
     * @param isSuccess {@code true} if the operation succeeded, {@code false} otherwise.
     * @since 4
     */
    void onScanResulted(boolean isSuccess);
}
