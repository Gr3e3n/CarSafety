/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.storage;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * TEE secure storage function entry.
 * Refer to {@link ISecureStorage} for more information.
 *
 * @since 12
 */
public final class SecureStorageManager {

    /**
     * A factory method that creates {@link ISecureStorage} instance.
     *
     * @param context App's Context.
     * @return {@link ISecureStorage} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 12
     */
    @NonNull
    public static ISecureStorage get(@NonNull Context context) {
        throw new UnimplementedException();
    }

}
