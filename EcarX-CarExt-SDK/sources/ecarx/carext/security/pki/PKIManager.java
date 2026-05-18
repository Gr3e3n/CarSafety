/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.security.pki;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * PKI function entry.
 * Refer to {@link IPKI} for more information.
 *
 * @since 6
 */
public final class PKIManager {
    /**
     * A factory method that creates {@link IPKI} instance.
     *
     * @param context App's Context.
     * @return {@link IPKI} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 6
     */
    @NonNull
    public static IPKI get(@NonNull Context context) {
        throw new UnimplementedException();
    }
}
