/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.interaction.charging;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * Charging function entry.
 * Refer to {@link ICharging} for more information.
 *
 * @since 7
 */
public final class ChargingManager {

    /**
     * A factory method that creates {@link ICharging} instance.
     *
     * @param context App's Context.
     * @return {@link ICharging} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 7
     */
    @NonNull
    public static ICharging get(@NonNull Context context) {
        throw new UnimplementedException();
    }
}
