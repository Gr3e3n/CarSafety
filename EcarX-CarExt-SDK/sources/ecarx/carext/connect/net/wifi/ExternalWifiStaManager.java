/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * External Wi-Fi sta function entry.
 * Refer to {@link IExternalWifiSta} for more information.
 *
 * @since 4
 */
public final class ExternalWifiStaManager {

    /**
     * A factory method that creates {@link IExternalWifiSta} instance.
     *
     * @param context App's Context.
     * @return {@link IExternalWifiSta} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 4
     */
    @NonNull
    public static IExternalWifiSta get(@NonNull Context context) {
        throw new UnimplementedException();
    }

}
