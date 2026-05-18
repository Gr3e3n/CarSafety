/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

import android.net.wifi.WifiInfo;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Wi-Fi sta connection info observer.
 *
 * @since 4
 */
public interface StaConnectionListener {

    /**
     * The Wi-Fi is in disconnected state
     *
     * @since 4
     */
    int STATE_DISCONNECTED = 0;
    /**
     * The Wi-Fi is in connecting state
     *
     * @since 4
     */
    int STATE_CONNECTING = 1;
    /**
     * The Wi-Fi is in connected state
     *
     * @since 4
     */
    int STATE_CONNECTED = 2;
    /**
     * The Wi-Fi is in disconnecting state
     *
     * @since 4
     */
    int STATE_DISCONNECTING = 3;

    /**
     * Connection state.
     *
     * @since 4
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_DISCONNECTED, STATE_CONNECTING, STATE_CONNECTED, STATE_DISCONNECTING})
    @interface State {
    }

    /**
     * Unknown reason
     *
     * @since 4
     */
    int ERROR_UNKNOWN = 0;
    /**
     * Ssid not found
     *
     * @since 4
     */
    int ERROR_SSID_NOT_FOUND = 1;
    /**
     * Password incorrect
     *
     * @since 4
     */
    int ERROR_PASSWORD_INCORRECT = 2;
    /**
     * Unsafe network
     *
     * @since 4
     */
    int ERROR_UNSAFE_NETWORK = 3;
    /**
     * Connection timeout
     *
     * @since 4
     */
    int ERROR_CONNECTION_TIMEOUT = 4;

    /**
     * Error code.
     *
     * @since 4
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ERROR_UNKNOWN, ERROR_SSID_NOT_FOUND, ERROR_PASSWORD_INCORRECT, ERROR_UNSAFE_NETWORK, ERROR_CONNECTION_TIMEOUT})
    @interface ErrorCode {
    }

    /**
     * Called when the state changes.
     *
     * @param state    current state.
     * @param wifiInfo The Wi-Fi info corresponding to state.
     * @since 4
     */
    void onStateChanged(@State int state, @NonNull WifiInfo wifiInfo);

    /**
     * Called when the operation finished with an error.
     *
     * @since 4
     */
    void onError(@ErrorCode int code);
}