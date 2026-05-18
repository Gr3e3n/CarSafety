/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

import androidx.annotation.NonNull;

/**
 * Represents a client connected to the ap.
 *
 * @since 4
 */
public interface ApClient {

    /**
     * Returns the device name.
     *
     * @since 4
     */
    @NonNull
    String getDeviceName();

    /**
     * Returns the MAC address.
     *
     * @since 4
     */
    @NonNull
    String getMacAddress();

    /**
     * Returns the ip address.
     *
     * @since 4
     */
    @NonNull
    String getIpAddress();

}
