/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * OTA function entry.
 * Refer to {@link IOta} for more information.
 *
 * @since 5
 */
public final class OtaManager {
    /**
     * A factory method that creates {@link IOta} instance.
     *
     * @param context App's Context.
     * @return {@link IOta} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 5
     */
    @NonNull
    public static IOta get(@NonNull Context context) {
        throw new UnimplementedException();
    }
}
