/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.oncall;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * OnCall center function entry.
 * Refer to {@link IOnCallCenter} for more information.
 *
 * @since 4
 */
public final class OnCallCenterManager {

    /**
     * A factory method that creates {@link IOnCallCenter} instance.
     *
     * @param context App's Context.
     * @return {@link IOnCallCenter} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 4
     */
    @NonNull
    public static IOnCallCenter get(@NonNull Context context) {
        throw new UnimplementedException();
    }

}
