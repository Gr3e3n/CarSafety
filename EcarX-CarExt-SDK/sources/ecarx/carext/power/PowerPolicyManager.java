package ecarx.carext.power;

import androidx.annotation.NonNull;

import android.content.Context;

import ecarx.carext.base.exception.UnimplementedException;


public final class PowerPolicyManager {
    /**
     * A factory method that creates {@link IPowerPolicy} instance.
     *
     * @return {@link IPowerPolicy} instance.
     * @throws UnimplementedException If this function is not implemented.
     */
    public static IPowerPolicy get(@NonNull Context context) {
        throw new UnimplementedException();
    }

    private PowerPolicyManager() {
        // Prevent instantiation
    }
}
