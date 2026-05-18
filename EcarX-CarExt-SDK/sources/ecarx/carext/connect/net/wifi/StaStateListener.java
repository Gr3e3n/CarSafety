/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

import android.net.wifi.WifiManager;

/**
 * Wi-Fi sta state observer.
 *
 * @since 4
 */
public interface StaStateListener {
    /**
     * Called when the state changes.
     *
     * @param state One of {@link WifiManager#WIFI_STATE_DISABLED},
     *              {@link WifiManager#WIFI_STATE_DISABLING}, {@link WifiManager#WIFI_STATE_ENABLED},
     *              {@link WifiManager#WIFI_STATE_ENABLING}, {@link WifiManager#WIFI_STATE_UNKNOWN}
     * @since 4
     */
    void onStateChanged(int state);
}