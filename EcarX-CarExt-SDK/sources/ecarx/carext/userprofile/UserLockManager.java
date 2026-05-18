/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.userprofile;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 *
 * @since 10
 */
public final class UserLockManager {

    @NonNull
    public static IUserLock get(@NonNull Context context) {
        throw new UnimplementedException();
    }
}