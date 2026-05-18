/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.bt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.util.List;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;
import ecarx.carext.connect.bt.model.BtDeviceInfo;
import ecarx.carext.connect.bt.model.LocalBluetooth;

/**
 * RSD BT（Rear side Display Bluetooth）function is to
 * control the Bluetooth function of the rear screen.
 *
 * @since 2
 */
public interface IBtExtension extends BaseAPI {

    /**
     * Get current Bluetooth switch status.
     *
     * @return {@link IBtConstant.BtStatus}
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    @IBtConstant.BtStatus
    default int getBtStatus() {
        throw new UnimplementedException();
    }

    /**
     * Set Bluetooth switch status.
     *
     * @param enable {true: open，false: close}
     * @return Whether the sending request was successful
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    default boolean setBtEnable(boolean enable) {
        throw new UnimplementedException();
    }

    /**
     * Request to discover Bluetooth device.
     *
     * @return Whether the sending request was successful
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    default boolean startDiscovery() {
        throw new UnimplementedException();
    }

    /**
     * Cancel request for Bluetooth device discovery.
     *
     * @return Whether the sending request was successful
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    default boolean cancelDiscovery() {
        throw new UnimplementedException();
    }

    /**
     * Obtain local Bluetooth device name.
     *
     * @return bt device name
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    @Nullable
    default String getBtName() {
        throw new UnimplementedException();
    }

    /**
     * Set local Bluetooth device name.
     *
     * @return Whether the sending request was successful
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    default boolean setBtName(@NonNull String btName) {
        throw new UnimplementedException();
    }

    /**
     * Request to obtain paired Bluetooth devices.
     *
     * @return Whether the sending request was successful
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    default boolean requestBondedDevices() {
        throw new UnimplementedException();
    }

    /**
     * Request operation on bound devices.
     * @param operationType {@link IBtConstant.OperationType}
     * @param macAddress Bind the MAC address of the device
     * @return Whether the sending request was successful
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    default boolean requestBoundedDeviceOperation(@IBtConstant.OperationType int operationType, @NonNull String macAddress) {
        throw new UnimplementedException();
    }

    /**
     * Notify Screen Media Current Status.
     *
     * @param operation Media Current Status
     * @param volume When the notification type is {@link IBtConstant#MEDIA_VOLUME} the range of values is
     *               between 0 and 39,otherwise it defaults to 0
     *
     * @return Whether the sending request was successful
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    default boolean notifyMediaCurrentStatus(@IBtConstant.NoticeMediaType int operation, int volume) {
        throw new UnimplementedException();
    }

    /**
     * Get local device properties.
     *
     * @return local Bluetooth device object <br>
     *     {@link LocalBluetooth}
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    @Nullable
    default LocalBluetooth getLocalDeviceProperties() {
        throw new UnimplementedException();
    }

    /**
     * Listening method for registering Bluetooth data callback.
     *
     * @param callback Register listening callback interface{@link IBtExtensionCallback}
     *
     * @return Whether registration successful
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    default boolean registerCallback(@NonNull IBtExtensionCallback callback) {
        throw new UnimplementedException();
    }

    /**
     * A listening method for unregistering Bluetooth data callback.
     *
     * @param callback Register listening callback interface{@link IBtExtensionCallback}
     *
     * @return Whether the cancellation of registration was successful
     *
     * @since 2
     */
    @RequiresPermission(Permissions.ACCESS_EXPENDED_BLUETOOTH)
    default boolean unregisterCallback(@NonNull IBtExtensionCallback callback) {
        throw new UnimplementedException();
    }

    /**
     * The interface definition of the callback to be called when receiving Bluetooth device messages.
     *
     *  @since 2
     */
    interface IBtExtensionCallback{

        /**
         *
         * When function {@link #setBtEnable(boolean)} is called, this callback
         * function returns the result
         *
         * @param btState {@link IBtConstant.BtStatus}
         * @since 2
         *
         */
        void onBtStateChanged(@IBtConstant.BtStatus int btState);

        /**
         *
         *When function {@link #startDiscovery()} or {@link #cancelDiscovery()} is called,
         * this callback function will be called
         *
         * @param scanValue {@link IBtConstant.BtStatus}
         *
         * @since 2
         */
        void onScanOver(@IBtConstant.ScanBtStatus int scanValue);

        /**
         * When function {@link #startDiscovery()} is called,
         * if a new Bluetooth device is found, this callback function will be called
         *
         * @param nearBtInfo Bluetooth device object
         *
         * @since 2
         */
        void onFoundNearBtDeviceInfo(@NonNull BtDeviceInfo nearBtInfo);

        /**
         * When a local Bluetooth device is found, this function will be called back
         *
         * @param localBtInfo local Bluetooth device object
         *
         * @since 2
         */
        void onLocalBtInfoCallback(@NonNull LocalBluetooth localBtInfo);

        /**
         * When function {@link #setBtName(String)} } is called, this callback
          function returns the result
         *
         * @param btName Bluetooth name
         *
         * @since 2
         */
        void onBtNameChanged(@NonNull String btName);

        /**
         * When function {@link #requestBondedDevices()} is called, if there are Bluetooth devices
         * that have already been paired, this callback function will be called
         * @param btInfoList Bluetooth devices list
         *
         * @since 2
         */
        void onPairedDevices(@NonNull List<BtDeviceInfo> btInfoList);

        /**
         *
         *When function {@link #requestBondedDevices()} function is called, if the ACL state changes,
         *  this callback function will be called
         *
         * @param device Bluetooth devices object
         * @param status ACL status {@link IBtConstant.ACLStatus}
         * @param reason ACL fail reason {@link IBtConstant.ACLFailReason}
         *
         * @since 2
         */
        void onBtBondedStatusChanged(@NonNull BtDeviceInfo device,@IBtConstant.ACLStatus int status, @IBtConstant.ACLFailReason int reason);

        /**
         * When function {@link #requestBoundedDeviceOperation(int, String)} is called
         * or the Bluetooth transmission protocol changes, this callback function will be called
         *
         * @param macAddress MAC Address
         * @param status connection status {@link IBtConstant.BtConnectionStatus}
         *
         * @since 2
         */
        void onProfileConnection(@NonNull String macAddress,@IBtConstant.BtConnectionStatus int status);

        /**
         * When the battery level of the Bluetooth earphone changes, this callback function will be called
         *
         * @param device Bluetooth device object
         * @param power Earphone battery level range 1~9
         *
         * @since 2
         */
        void onHeadsetPowerChanged(@NonNull BtDeviceInfo device, int power);

        /**
         *
         * When function {@link #notifyMediaCurrentStatus(int, int)} is called, this callback
         * function returns the result
         *
         * @param operate Media notification type{@link IBtConstant.NoticeMediaType}
         * @param status Media notification status {@link IBtConstant.NoticeMediaStatus}
         *
         * @since 2
         */
        void onMediaStatusUpdate(@IBtConstant.NoticeMediaType int operate, @IBtConstant.NoticeMediaStatus int status);

        /**
         * Bluetooth error detection for connection
         * @param status Connection Bluetooth device status {@link IBtConstant.BtDeviceStatus}
         *
         * @since 2
         */
        void onErrorDetect(int status);

        /**
         * When the Bluetooth device issues a command, this callback function will be called
         *
         * @param operate Bluetooth operation media action type {@link IBtConstant.BtOptMediaType}
         * @param volume Volume range from 1~39
         *
         * @since 2
         */
        void onBtOperateCallback(int operate, int volume);

    }


}
