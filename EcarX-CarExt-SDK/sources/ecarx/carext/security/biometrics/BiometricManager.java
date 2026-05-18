/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.security.biometrics;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * Biometric function entry.
 * Refer to {@link IBiometric} for more information.
 *
 * @since 6
 */
public class BiometricManager {
    /**
     * A factory method that creates {@link IBiometric} instance.
     *
     * @param context App's Context.
     * @return {@link IBiometric} instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 6
     */
    @NonNull
    public static IBiometric get(@NonNull Context context) {
        throw new UnimplementedException();
    }
}
