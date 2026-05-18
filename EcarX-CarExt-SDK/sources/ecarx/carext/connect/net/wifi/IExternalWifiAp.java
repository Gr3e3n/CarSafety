/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

import android.net.wifi.SoftApConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.util.List;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.FunctionStatus;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for managing Wi-Fi ap connectivity provided by external hardware.
 *
 * @since 4
 */
public interface IExternalWifiAp extends BaseAPI {

    /**
     * Wi-Fi AP is currently being disabled. The state will change to
     * {@link #WIFI_AP_STATE_DISABLED} if it finishes successfully.
     *
     * @see #getWifiApState()
     * @since 4
     */
    int WIFI_AP_STATE_DISABLING = 10;

    /**
     * Wi-Fi AP is disabled.
     *
     * @see #getWifiApState()
     * @since 4
     */
    int WIFI_AP_STATE_DISABLED = 11;

    /**
     * Wi-Fi AP is currently being enabled. The state will change to
     * {@link #WIFI_AP_STATE_ENABLED} if it finishes successfully.
     *
     * @see #getWifiApState()
     * @since 4
     */
    int WIFI_AP_STATE_ENABLING = 12;

    /**
     * Wi-Fi AP is enabled.
     *
     * @see #getWifiApState()
     * @since 4
     */
    int WIFI_AP_STATE_ENABLED = 13;

    /**
     * Wi-Fi AP is in a failed state. This state will occur when an error occurs during
     * enabling or disabling
     *
     * @see #getWifiApState()
     * @since 4
     */
    int WIFI_AP_STATE_FAILED = 14;

    /**
     * Get current function status.
     *
     * @since 4
     */
    @NonNull
    @RequiresPermission(Permissions.ACCESS_WIFI_AP)
    default FunctionStatus getFunctionStatus() {
        throw new UnimplementedException();
    }

    /**
     * Gets the tethered Wi-Fi hotspot enabled state.
     *
     * @return One of {@link #WIFI_AP_STATE_DISABLED},
     * {@link #WIFI_AP_STATE_DISABLING}, {@link #WIFI_AP_STATE_ENABLED},
     * {@link #WIFI_AP_STATE_ENABLING}, {@link #WIFI_AP_STATE_FAILED}
     * @see #isWifiApEnabled()
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_WIFI_AP)
    default int getWifiApState() {
        throw new UnimplementedException();
    }

    /**
     * Return whether tethered Wi-Fi AP is enabled or disabled.
     *
     * @return {@code true} if tethered Wi-Fi AP is enabled
     * @see #getWifiApState()
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_WIFI_AP)
    default boolean isWifiApEnabled() {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain state change event.
     *
     * @param listener The callback that will run.
     * @return {@code true} if the operation succeeded, {@code false} otherwise.
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_WIFI_AP)
    default boolean registerApStateListener(@NonNull ApStateListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @param listener The callback that don't want to receive event.
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_WIFI_AP)
    default void unregisterApStateListener(@NonNull ApStateListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Start Soft AP (hotspot) mode for tethering purposes with the specified configuration.
     * Note that starting Soft AP mode may disable station mode operation if the device does not
     * support concurrency.
     *
     * @param softApConfig A valid SoftApConfiguration specifying the configuration of the SAP,
     *                     or null to use the persisted Soft AP configuration that was previously
     *                     set using {@link #setSoftApConfiguration(SoftApConfiguration)}.
     * @return {@code true} if the operation succeeded, {@code false} otherwise
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default boolean startTetheredHotspot(@Nullable SoftApConfiguration softApConfig) {
        throw new UnimplementedException();
    }

    /**
     * Stop SoftAp mode.
     * Note that stopping softap mode will restore the previous wifi mode.
     *
     * @return {@code true} if the operation succeeds, {@code false} otherwise
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default boolean stopSoftAp() {
        throw new UnimplementedException();
    }

    /**
     * Gets the Wi-Fi tethered AP Configuration.
     *
     * @return AP details in {@link SoftApConfiguration}
     * @since 4
     */
    @NonNull
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default SoftApConfiguration getSoftApConfiguration() {
        throw new UnimplementedException();
    }

    /**
     * Sets the tethered Wi-Fi AP Configuration.
     *
     * @param softApConfig A valid SoftApConfiguration specifying the configuration of the SAP.
     * @return {@code true} if the operation succeeded, {@code false} otherwise
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default boolean setSoftApConfiguration(@NonNull SoftApConfiguration softApConfig) {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain configuration change event.
     *
     * @param listener The callback that will run.
     * @return {@code true} if the operation succeeded, {@code false} otherwise.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default boolean registerApConfigurationListener(@NonNull ApConfigurationListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @param listener The callback that don't want to receive event.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default void unregisterApConfigurationListener(@NonNull ApConfigurationListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Get the currently connected clients.
     *
     * @return the currently connected clients.
     * @since 4
     */
    @NonNull
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default List<ApClient> getConnectedClients() {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain connected clients change event.
     *
     * @param listener The callback that will run.
     * @return {@code true} if the operation succeeded, {@code false} otherwise.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default boolean registerApClientListener(@NonNull ApClientListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @param listener The callback that don't want to receive event.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default void unregisterApClientListener(@NonNull ApClientListener listener) {
        throw new UnimplementedException();
    }

    /**
     * 5G mode switch status.
     *
     * @return true--5G on, false--5G off.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default boolean query5GMode() {
        throw new UnimplementedException();
    }

    /**
     * Set 5G mode switch status.
     *
     * @param enable true--5G on, false--5G off.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_AP)
    default boolean set5GMode(boolean enable) {
        throw new UnimplementedException();
    }

}
