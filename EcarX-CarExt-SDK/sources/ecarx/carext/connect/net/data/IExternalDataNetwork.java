/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.data;

import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.FunctionStatus;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for managing data network connectivity provided by external hardware.
 *
 * @since 4
 */
public interface IExternalDataNetwork extends BaseAPI {

    /**
     * Get current function status.
     *
     * @since 4
     */
    @NonNull
    @RequiresPermission(Permissions.ACCESS_DATA_NETWORK)
    default FunctionStatus getFunctionStatus() {
        throw new UnimplementedException();
    }

    /**
     * Get supported network types.
     *
     * @return array of the supported network type defined in {@link TelephonyManager},
     * starts with NETWORK_TYPE_. such as {@link TelephonyManager#NETWORK_TYPE_NR}
     * or {@link TelephonyManager#NETWORK_TYPE_LTE}.
     * @since 4
     */
    @NonNull
    @RequiresPermission(Permissions.ACCESS_DATA_NETWORK)
    default int[] getSupportedNetworkTypes() {
        throw new UnimplementedException();
    }

    /**
     * Returns a constant indicating the network type currently in use for data transmission.
     *
     * @return one of the network type returned by {@link #getSupportedNetworkTypes} method.
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_DATA_NETWORK)
    default int getDataNetworkType() {
        throw new UnimplementedException();
    }

    /**
     * Modify the network type for data transmission.
     *
     * @param networkType one of the network type returned by {@link #getSupportedNetworkTypes} method.
     * @return whether this call was successful. Whether it is finally successful or not
     * will be notified later through the callback {@link NetworkTypeListener#onTypeChanged(int)}.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_DATA_NETWORK)
    default boolean setDataNetworkType(int networkType) {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain network type change event.
     *
     * @param listener The callback that will run.
     * @return {@code true} if the operation succeeded, {@code false} otherwise.
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_DATA_NETWORK)
    default boolean registerNetworkTypeListener(@NonNull NetworkTypeListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @param listener The callback that don't want to receive event.
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_DATA_NETWORK)
    default void unregisterNetworkTypeListener(@NonNull NetworkTypeListener listener) {
        throw new UnimplementedException();
    }
}
