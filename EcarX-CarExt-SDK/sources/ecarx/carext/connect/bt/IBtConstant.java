/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.bt;

import androidx.annotation.IntDef;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Bluetooth interface constant value definition class
 *
 * @since 2
 */
public interface IBtConstant {

    /**
     * Screen area -- passenger side display
     * @since 2
     */
    int PSD = 1;

    /**
     * Screen area -- Row 2 display left
     * @since 2
     */
    int ROW_2_LEFT = 2;

    /**
     * Screen area -- Row 2 display right
     * @since 2
     */
    int ROW_2_RIGHT = 4;


    /**
     * Screen area <br>
     *
     * {@link #PSD}<br>
     * {@link #ROW_2_LEFT}<br>
     * {@link #ROW_2_RIGHT}<br>
     */
    @Documented
    @IntDef({PSD, ROW_2_LEFT, ROW_2_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface ScreenArea {
    }

    /**
     * Bluetooth switch status -- on
     * @since 2
     */
    int BT_STATUS_ON = 1;
    /**
     * Bluetooth switch status -- turning on
     * @since 2
     */
    int BT_STATUS_TURNING_ON = 2;
    /**
     * Bluetooth switch status -- turning off
     * @since 2
     */
    int BT_STATUS_TURNING_OFF = 3;
    /**
     * Bluetooth switch status -- off
     * @since 2
     */
    int BT_STATUS_OFF = 4;

    /**
     * Bluetooth switch status <br>
     * {@link #BT_STATUS_ON}<br>
     * {@link #BT_STATUS_TURNING_ON}<br>
     * {@link #BT_STATUS_TURNING_OFF}<br>
     * {@link #BT_STATUS_OFF}<br>
     */
    @Documented
    @IntDef({BT_STATUS_ON, BT_STATUS_TURNING_ON, BT_STATUS_TURNING_OFF ,BT_STATUS_OFF})
    @Retention(RetentionPolicy.SOURCE)
    @interface BtStatus {
    }

    /**
     * Bluetooth operation type -- Pairing Bluetooth devices
     * @since 2
     */
    int OPERATION_TYPE_PAIRING = 1;
    /**
     * Bluetooth operation type -- Cancel Bluetooth pairing
     * @since 2
     */
    int OPERATION_TYPE_CANCEL = 2;
    /**
     * Bluetooth operation type -- Delete Bluetooth device
     * @since 2
     */
    int OPERATION_TYPE_DELETE = 3;

    /**
     * Bluetooth operation type <br>
     * {@link #OPERATION_TYPE_PAIRING}<br>
     * {@link #OPERATION_TYPE_CANCEL}<br>
     * {@link #OPERATION_TYPE_DELETE}<br>
     *
     * @since 2
     */
    @Documented
    @IntDef({OPERATION_TYPE_PAIRING, OPERATION_TYPE_CANCEL, OPERATION_TYPE_DELETE})
    @Retention(RetentionPolicy.SOURCE)
    @interface OperationType {
    }

    /**
     * Notification media type -- playing
     * @since 2
     */
    int MEDIA_PLAYING = 1;
    /**
     * Notification media type --  paused
     * @since 2
     */
    int MEDIA_PAUSED = 2;
    /**
     * Notification media type -- stopped
     * @since 2
     */
    int MEDIA_STOPPED = 3;
    /**
     * Notification media type -- volume
     * @since 2
     */
    int MEDIA_VOLUME = 4;

    /**
     * Notification media type <br>
     * {@link #MEDIA_PLAYING}<br>
     * {@link #MEDIA_PAUSED}<br>
     * {@link #MEDIA_STOPPED}<br>
     * {@link #MEDIA_VOLUME}<br>
     *
     */
    @Documented
    @IntDef({MEDIA_PLAYING , MEDIA_PAUSED, MEDIA_STOPPED, MEDIA_VOLUME})
    @Retention(RetentionPolicy.SOURCE)
    @interface NoticeMediaType {
    }

    /**
     * Bluetooth operation media action -- play
     * @since 2
     */
    int BLUETOOTH_MEDIA_PLAY = 1;
    /**
     * Bluetooth operation media action -- pause
     * @since 2
     */
    int BLUETOOTH_MEDIA_PAUSE = 2;
    /**
     * Bluetooth operation media action -- next
     * @since 2
     */
    int BLUETOOTH_MEDIA_NEXT = 3;
    /**
     * Bluetooth operation media action -- previous
     * @since 2
     */
    int BLUETOOTH_MEDIA_PREVIOUS = 4;
    /**
     * Bluetooth operation media action -- volume
     * @since 2
     */
    int BLUETOOTH_MEDIA_VOLUME = 5;

    /**
     * Connected Bluetooth operation media action <br>
     * {@link #BLUETOOTH_MEDIA_PLAY}<br>
     * {@link #BLUETOOTH_MEDIA_PAUSE}<br>
     * {@link #BLUETOOTH_MEDIA_NEXT}<br>
     * {@link #BLUETOOTH_MEDIA_PREVIOUS}<br>
     * {@link #BLUETOOTH_MEDIA_VOLUME}<br>
     *
     */
    @Documented
    @IntDef({BLUETOOTH_MEDIA_PLAY , BLUETOOTH_MEDIA_PAUSE, BLUETOOTH_MEDIA_NEXT, BLUETOOTH_MEDIA_PREVIOUS,BLUETOOTH_MEDIA_VOLUME})
    @Retention(RetentionPolicy.SOURCE)
    @interface BtOptMediaType {
    }


    /**
     * Media notification status -- success
     * @since 2
     */
    int MEDIA_NOTIFICATION_SUCCESS = 0;
    /**
     * Media notification status -- failed unsupported
     * @since 2
     */
    int MEDIA_NOTIFICATION_FAILED = 1;
    /**
     * Media notification status -- failed other
     * @since 2
     */
    int MEDIA_NOTIFICATION_OTHER = 2;
    /**
     * Media notification status <br>
     * {@link #MEDIA_NOTIFICATION_SUCCESS}<br>
     * {@link #MEDIA_NOTIFICATION_FAILED}<br>
     * {@link #MEDIA_NOTIFICATION_OTHER}<br>
     *
     */
    @Documented
    @IntDef({MEDIA_NOTIFICATION_SUCCESS , MEDIA_NOTIFICATION_FAILED, MEDIA_NOTIFICATION_OTHER})
    @Retention(RetentionPolicy.SOURCE)
    @interface NoticeMediaStatus {
    }

    /**
     * Scan Bluetooth request status -- start
     * @since 2
     */
    int SCAN_START = 1;
    /**
     * Scan Bluetooth request status -- end
     * @since 2
     */
    int SCAN_END = 2;

    /**
     * Scan Bluetooth request status <br>
     * {@link #SCAN_START}<br>
     * {@link #SCAN_END}<br>
     *
     */
    @Documented
    @IntDef({SCAN_START , SCAN_END})
    @Retention(RetentionPolicy.SOURCE)
    @interface ScanBtStatus {
    }

    /**
     * Connection Bluetooth device status -- regular
     * @since 2
     */
    int BlUETOOTH_REGULAR= 0;
    /**
     * Connection Bluetooth device status -- error(Abnormal voltage)
     * @since 2
     */
    int BlUETOOTH_ERROR = 1;
    /**
     * Connection Bluetooth device status <br>
     * {@link #BlUETOOTH_REGULAR}<br>
     * {@link #BlUETOOTH_ERROR}<br>
     *
     */
    @Documented
    @IntDef({BlUETOOTH_REGULAR , BlUETOOTH_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    @interface BtDeviceStatus {
    }

    /**
     * ACL status -- paired success
     * @since 2
     */
    int PAIRED_SUCCESS = 1;
    /**
     * ACL status -- paired fail
     * @since 2
     */
    int PAIRED_FAILURE = 2;

    /**
     * ACL status -- ACL connected
     * @since 2
     */
    int ACL_CONNECTED = 3;
    /**
     * ACL status -- ACL disconnected
     * @since 2
     */
    int ACL_DISCONNECTED = 4;

    /**
     * ACL status -- ACL failure
     * @since 2
     */
    int ACL_FAILURE = 5;
    /**
     * ACL status -- remove paired success
     * @since 2
     */
    int REMOVE_PAIRED_SUCCESS = 6;

    /**
     * ACL status -- remove paired failure
     * @since 2
     */
    int REMOVE_PAIRED_FAILURE = 7;
    /**
     * ACL connection status <br>
     * {@link #PAIRED_SUCCESS}<br>
     * {@link #PAIRED_FAILURE}<br>
     * {@link #ACL_CONNECTED}<br>
     * {@link #ACL_DISCONNECTED}<br>
     * {@link #ACL_FAILURE}<br>
     * {@link #REMOVE_PAIRED_SUCCESS}<br>
     * {@link #REMOVE_PAIRED_FAILURE}<br>
     *
     */
    @Documented
    @IntDef({PAIRED_SUCCESS ,
            PAIRED_FAILURE,
            ACL_CONNECTED ,
            ACL_DISCONNECTED ,
            ACL_FAILURE ,
            REMOVE_PAIRED_SUCCESS ,
            REMOVE_PAIRED_FAILURE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ACLStatus {
    }

    /**
     * ACL reason --  success
     * @since 2
     */
    int ACL_CONNECTION_SUCCESS = 0X00;
    /**
     * ACL reason -- connection failed
     * @since 2
     */
    int ACL_CONNECTION_FAILURE = 0X02;

    /**
     * ACL reason -- earphone termination connection
     * @since 2
     */
    int EARPHONE_TERMINATION_CONNECTION = 0X13;
    /**
     * ACL reason -- local termination connection
     * @since 2
     */
    int LOCAL_TERMINATION_CONNECTION = 0X16;
    /**
     * ACL status fail reason <br>
     * {@link #ACL_CONNECTION_SUCCESS}<br>
     * {@link #ACL_CONNECTION_FAILURE}<br>
     * {@link #EARPHONE_TERMINATION_CONNECTION}<br>
     * {@link #LOCAL_TERMINATION_CONNECTION}<br>
     *
     */
    @Documented
    @IntDef({ACL_CONNECTION_SUCCESS ,
            ACL_CONNECTION_FAILURE,
            EARPHONE_TERMINATION_CONNECTION ,
            LOCAL_TERMINATION_CONNECTION})
    @Retention(RetentionPolicy.SOURCE)
    @interface ACLFailReason {
    }

    /**
     * Bluetooth connection status --  connecting
     * @since 2
     */
    int CONNECTING = 1;
    /**
     * Bluetooth connection status --  connected
     * @since 2
     */
    int CONNECTED = 2;

    /**
     * Bluetooth connection status --  disconnecting
     * @since 2
     */
    int DISCONNECTING = 3;
    /**
     * Bluetooth connection status --  disconnected
     * @since 2
     */
    int DISCONNECTED = 4;
    /**
     * Bluetooth connection status --  failure
     * @since 2
     */
    int FAILURE = 5;

    /**
     * Bluetooth connection status  <br>
     * {@link #CONNECTING}<br>
     * {@link #CONNECTED}<br>
     * {@link #DISCONNECTING}<br>
     * {@link #DISCONNECTED}<br>
     * {@link #FAILURE}<br>
     *
     */
    @Documented
    @IntDef({CONNECTING ,
            CONNECTED,
            DISCONNECTING ,
            DISCONNECTED,
            FAILURE})
    @Retention(RetentionPolicy.SOURCE)
    @interface BtConnectionStatus {
    }

}
