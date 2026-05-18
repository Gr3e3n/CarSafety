/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.security.pki;

/**
 * This class provides the Listener for PKI prepared status.
 *
 * @since 6
 */
public interface PKIPreparedListener {

    /**
     * Call this method on prepared
     *
     * @param prepared PKI certificate status
     * @since 6
     */
    void onPrepared(boolean prepared);
}