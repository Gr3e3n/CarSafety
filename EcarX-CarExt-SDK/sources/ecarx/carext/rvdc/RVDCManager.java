/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.rvdc;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * RVDC function entry.
 * Refer to {@link IRVDC} for more information.
 *
 * @since 1
 */
public final class RVDCManager {

    /**
     * A factory method that creates {@link IRVDC} instance.
     *
     * @param context App's Context.
     * @return IRVDC instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 1
     */
    @NonNull
    public static IRVDC get(@NonNull Context context) {
        throw new UnimplementedException();
    }

}
