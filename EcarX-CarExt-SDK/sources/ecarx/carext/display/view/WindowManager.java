/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.display.view;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * Window function entry.
 * Refer to {@link IWindow} for more information.
 *
 * @since 5
 */
public final class WindowManager {
    /**
     * A factory method that creates {@link IWindow} instance.
     *
     * @param context App's Context.
     * @return {@link IWindow} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 5
     */
    @NonNull
    public static IWindow get(@NonNull Context context) {
        throw new UnimplementedException();
    }
}