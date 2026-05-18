/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.util.List;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.FunctionStatus;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for managing Wi-Fi sta connectivity provided by external hardware.
 *
 * @since 4
 */
public interface IExternalWifiSta extends BaseAPI {

    /**
     * Get current function status.
     *
     * @since 4
     */
    @NonNull
    @RequiresPermission(Permissions.ACCESS_WIFI_STA)
    default FunctionStatus getFunctionStatus() {
        throw new UnimplementedException();
    }

    /**
     * Enable or disable Wi-Fi.
     *
     * @param enabled {@code true} to enable, {@code false} to disable.
     * @return {@code false} if the request cannot be satisfied; {@code true} indicates that wifi is
     * either already in the requested state, or in progress toward the requested state.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default boolean setWifiEnabled(boolean enabled) {
        throw new UnimplementedException();
    }

    /**
     * Gets the Wi-Fi enabled state.
     *
     * @return One of {@link WifiManager#WIFI_STATE_DISABLED},
     * {@link WifiManager#WIFI_STATE_DISABLING}, {@link WifiManager#WIFI_STATE_ENABLED},
     * {@link WifiManager#WIFI_STATE_ENABLING}, {@link WifiManager#WIFI_STATE_UNKNOWN}
     * @see #isWifiEnabled()
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_WIFI_STA)
    default int getWifiState() {
        throw new UnimplementedException();
    }

    /**
     * Return whether Wi-Fi is enabled or disabled.
     *
     * @return {@code true} if Wi-Fi is enabled
     * @see #getWifiState()
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_WIFI_STA)
    default boolean isWifiEnabled() {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain state change event.
     *
     * @param listener The callback that will run.
     * @return {@code true} if the operation succeeded, {@code false} otherwise.
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_WIFI_STA)
    default boolean registerWifiStateListener(@NonNull StaStateListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @param listener The callback that don't want to receive event.
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_WIFI_STA)
    default void unregisterWifiStateListener(@NonNull StaStateListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Return a list of all the networks configured for the current foreground user.
     *
     * @return a list of network configurations in the form of a list
     * of {@link WifiConfiguration} objects.
     * @since 4
     */
    @NonNull
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default List<WifiConfiguration> getConfiguredNetworks() {
        throw new UnimplementedException();
    }

    /**
     * Add a new network description to the set of configured networks.
     * The {@code networkId} field of the supplied configuration object
     * is ignored.
     * <p/>
     * The new network will be marked DISABLED by default. To enable it,
     * called {@link #enableNetwork}.
     *
     * @param config the set of variables that describe the configuration,
     *               contained in a {@link WifiConfiguration} object.
     * @return the ID of the newly created network description. This is used in
     * other operations to specified the network to be acted upon.
     * Returns {@code -1} on failure.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default int addNetwork(@NonNull WifiConfiguration config) {
        throw new UnimplementedException();
    }

    /**
     * Remove the specified network from the list of configured networks.
     * This may result in the asynchronous delivery of state change
     * events.
     *
     * @param netId the ID of the network as returned by {@link #addNetwork} or {@link
     *              #getConfiguredNetworks}.
     * @return {@code true} if the operation succeeded
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default boolean removeNetwork(int netId) {
        throw new UnimplementedException();
    }

    /**
     * Allow a previously configured network to be associated with. If
     * <code>attemptConnect</code> is true, an attempt to connect to the selected
     * network is initiated. This may result in the asynchronous delivery
     * of state change events.
     *
     * @param netId          the ID of the network as returned by {@link #addNetwork} or {@link
     *                       #getConfiguredNetworks}.
     * @param attemptConnect The way to select a particular network to connect to is specify
     *                       {@code true} for this parameter.
     * @return {@code true} if the operation succeeded
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default boolean enableNetwork(int netId, boolean attemptConnect) {
        throw new UnimplementedException();
    }

    /**
     * Request a scan for access points. Returns immediately. The availability
     * of the results is made known later by means of an asynchronous event sent
     * on completion of the scan.
     *
     * @return {@code true} if the operation succeeded, i.e., the scan was initiated.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default boolean startScan() {
        throw new UnimplementedException();
    }

    /**
     * Return the results of the latest access point scan.
     *
     * @return the list of access points found in the most recent scan.
     * @since 4
     */
    @NonNull
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default List<ScanResult> getScanResults() {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain scan result event.
     *
     * @param listener The callback that will run.
     * @return {@code true} if the operation succeeded, {@code false} otherwise.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default boolean registerScanResultListener(@NonNull StaScanResultListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @param listener The callback that don't want to receive event.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default void unregisterScanResultListener(@NonNull StaScanResultListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Connect to a network with the given configuration. The network also
     * gets added to the list of configured networks for the foreground user.
     * <p>
     * For a new network, this function is used instead of a
     * sequence of addNetwork(), enableNetwork(), and reconnect()
     *
     * @param config   the set of variables that describe the configuration,
     *                 contained in a {@link WifiConfiguration} object.
     * @param listener for callbacks on success or failure. Can be null.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default void connect(@NonNull WifiConfiguration config, @Nullable ActionListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Disassociate from the currently active access point. This may result
     * in the asynchronous delivery of state change events.
     *
     * @return {@code true} if the operation succeeded
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default boolean disconnect() {
        throw new UnimplementedException();
    }

    /**
     * Return dynamic information about the current Wi-Fi connection, if any is active.
     *
     * @return the Wi-Fi information, contained in {@link WifiInfo}.
     * @since 4
     */
    @Nullable
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default WifiInfo getConnectionInfo() {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain connection info change event.
     *
     * @param listener The callback that will run.
     * @return {@code true} if the operation succeeded, {@code false} otherwise.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default boolean registerConnectionListener(@NonNull StaConnectionListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @param listener The callback that don't want to receive event.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_WIFI_STA)
    default void unregisterConnectionListener(@NonNull StaConnectionListener listener) {
        throw new UnimplementedException();
    }

}
