/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

import android.net.wifi.SoftApConfiguration;

import androidx.annotation.NonNull;

/**
 * Ap configuration observer.
 *
 * @since 4
 */
public interface ApConfigurationListener {
    /**
     * Called when the configuration changes.
     *
     * @param configuration AP details in {@link SoftApConfiguration}
     * @since 4
     */
    void onConfigurationChanged(@NonNull SoftApConfiguration configuration);
}
