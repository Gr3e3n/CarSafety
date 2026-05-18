/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Connected ap clients observer.
 *
 * @since 4
 */
public interface ApClientListener {

    /**
     * Called when the connected clients to soft AP changes.
     *
     * @param clients the currently connected clients
     * @since 4
     */
    void onConnectedClientsChanged(@NonNull List<ApClient> clients);
}
