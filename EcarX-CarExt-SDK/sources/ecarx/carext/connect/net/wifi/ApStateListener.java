/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

/**
 * Wi-Fi ap state observer.
 *
 * @since 4
 */
public interface ApStateListener {
    /**
     * Called when the state changes.
     *
     * @param state One of {@link IExternalWifiAp#WIFI_AP_STATE_DISABLED},
     *              {@link IExternalWifiAp#WIFI_AP_STATE_DISABLING}, {@link IExternalWifiAp#WIFI_AP_STATE_ENABLED},
     *              {@link IExternalWifiAp#WIFI_AP_STATE_ENABLING}, {@link IExternalWifiAp#WIFI_AP_STATE_FAILED}
     * @since 4
     */
    void onStateChanged(int state);
}