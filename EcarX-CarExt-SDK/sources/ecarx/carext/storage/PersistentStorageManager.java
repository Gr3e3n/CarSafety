/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.storage;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * Persistent storage function entry.
 * Refer to {@link IPersistentStorage} for more information.
 *
 * @since 5
 */
public final class PersistentStorageManager {

    /**
     * A factory method that creates {@link IPersistentStorage} instance.
     *
     * @param context App's Context.
     * @return {@link IPersistentStorage} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 5
     */
    @NonNull
    public static IPersistentStorage get(@NonNull Context context) {
        throw new UnimplementedException();
    }

}
