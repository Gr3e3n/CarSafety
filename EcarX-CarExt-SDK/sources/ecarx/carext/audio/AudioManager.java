/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.audio;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * Audio mode function entry.
 * Refer to {@link IAudio} for more information.
 *
 * @since 11
 */
public final class AudioManager {
    /**
     * A factory method that creates {@link IAudio} instance.
     *
     * @param context App's Context.
     * @return {@link IAudio} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 11
     */
    @NonNull
    public static IAudio get(@NonNull Context context) {
        throw new UnimplementedException();
    }

}

