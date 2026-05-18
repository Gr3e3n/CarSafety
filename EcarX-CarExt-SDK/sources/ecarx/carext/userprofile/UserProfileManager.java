/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.userprofile;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * UserProfile function entry.
 * Refer to {@link IUserProfile} for more information.
 *
 * @since 12
 */
public final class UserProfileManager {
    /**
     * A factory method that creates {@link IUserProfile} instance.
     *
     * @param context App's Context.
     * @return {@link IUserProfile} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 12
     */
    @NonNull
    public static IUserProfile get(@NonNull Context context) {
        throw new UnimplementedException();
    }
}