/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.rvdc;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 *
 * @since 11
 */
public final class LogManager {

    /**
     * A factory method that creates {@link ILogApi} instance.
     *
     * @param context App's Context.
     * @return ILogApi instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 11
     */
    @NonNull
    public static ILogApi get(@NonNull Context context) {
        throw new UnimplementedException();
    }

}
