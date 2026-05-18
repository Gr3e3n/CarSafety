/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.bt;

import android.content.Context;

import androidx.annotation.NonNull;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * RSD Bluetooth function entry.
 * Refer to {@link IBtExtension} for more information.
 *
 * @since 2
 */
public final class BtManager {

    /**
     * A factory method that creates {@link IBtExtension} instance.
     *
     * @param context App's Context.
     * @param screenArea Screen area of Bluetooth device to operate.
     * @return IBtExtension instance.
     * @throws UnimplementedException If this function is not implemented.
     * @since 2
     */
    @NonNull
    public static IBtExtension get(@NonNull Context context,@IBtConstant.ScreenArea int screenArea) {
        throw new UnimplementedException();
    }

}
