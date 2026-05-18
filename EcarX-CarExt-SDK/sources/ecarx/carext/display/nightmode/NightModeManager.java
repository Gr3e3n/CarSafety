/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.display.nightmode;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * Night mode function entry.
 * Refer to {@link INightMode} for more information.
 *
 * @since 5
 */
public final class NightModeManager {
    /**
     * A factory method that creates {@link INightMode} instance.
     *
     * @param context App's Context.
     * @return {@link INightMode} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 5
     */
    @NonNull
    public static INightMode get(@NonNull Context context) {
        throw new UnimplementedException();
    }

}

