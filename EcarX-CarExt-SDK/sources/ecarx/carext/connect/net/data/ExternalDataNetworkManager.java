/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.data;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * External data network function entry.
 * Refer to {@link IExternalDataNetwork} for more information.
 *
 * @since 4
 */
public final class ExternalDataNetworkManager {

    /**
     * A factory method that creates {@link IExternalDataNetwork} instance.
     *
     * @param context App's Context.
     * @return {@link IExternalDataNetwork} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 4
     */
    @NonNull
    public static IExternalDataNetwork get(@NonNull Context context) {
        throw new UnimplementedException();
    }

}
