/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.oncall;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents a call.
 *
 * @since 4
 */
public interface Call {
    /**
     * Unknown type.
     *
     * @since 4
     */
    int TYPE_UNKNOWN = -1;
    /**
     * Information call.
     *
     * @since 4
     */
    int TYPE_ICALL = 2;
    /**
     * Brakedown call.
     *
     * @since 4
     */
    int TYPE_BCALL = 1;
    /**
     * Emergency call.
     *
     * @since 4
     */
    int TYPE_ECALL = 0;

    /**
     * Call type.
     *
     * @since 4
     */
    @IntDef({TYPE_UNKNOWN, TYPE_ICALL, TYPE_BCALL, TYPE_ECALL})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }

    /**
     * Get type of the call.
     *
     * @return call type.
     * @since 4
     */
    @Type
    int getType();

    /**
     * The state of an incoming call when ringing locally, but not yet connected.
     *
     * @since 4
     */
    int STATUS_STARTING = 0;
    /**
     * The state of an outgoing call when newly created.
     *
     * @since 4
     */
    int STATUS_INCOMING = 1;
    /**
     * The state of an outgoing call when dialing the call center, but not yet connected.
     *
     * @since 4
     */
    int STATUS_RECONNECITON = 2;
    /**
     * The state of a call when actively supporting conversation.
     *
     * @since 4
     */
    int STATUS_FAILED = 3;
    /**
     * The state of a call when the user has initiated a disconnection of the call, but the
     * call has not yet been disconnected.
     *
     * @since 4
     */
    int STATUS_CONNECTING = 4;
    /**
     * The state of a call when CONNECTED normally.
     *
     * @since 4
     */
    int STATUS_CONNECTED = 5;

    /**
     * The state of a call when data sending.
     *
     * @since 4
     */
    int STATUS_DATA_SENDING = 6;
    /**
     * The state of a call when ended abnormally.
     *
     * @since 4
     */
    int STATUS_ENDED = 7;

    /**
     * The state of a call when ended abnormally in callbackmode.
     *
     * @since 4
     */
    int STATUS_FAILED_CALLBACKMODE = 8;

    /** The state of a call when call out timeout. */
    int STATUS_CALL_OUT_TIMEOUT = 9;

    /** The state of a call when call reject timeout. */
    int STATUS_CALL_REJECT_TIMEOUT = 10;

    /** The state of a call when answer timeout. */
    int STATUS_CALL_ANSWER_TIMEOUT = 11;


    /**
     * Call status.
     *
     * @since 4
     */
    @IntDef({STATUS_STARTING, STATUS_INCOMING, STATUS_RECONNECITON, STATUS_FAILED, STATUS_CONNECTING,
            STATUS_CONNECTED, STATUS_DATA_SENDING, STATUS_ENDED, STATUS_FAILED_CALLBACKMODE,
            STATUS_CALL_OUT_TIMEOUT, STATUS_CALL_REJECT_TIMEOUT, STATUS_CALL_ANSWER_TIMEOUT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Status {
    }

    /**
     * Get current status of the call.
     *
     * @return call status.
     * @since 4
     */
    @Status
    int getStatus();

    /**
     * Returns the time of the call connected.
     *
     * @return The time the call connected in milliseconds since the epoch.
     * @since 4
     */
    long getConnectTimeMillis();

    /**
     * The call is in call back mode or not.
     *
     * @return true indicates in call back mode.
     * @since 4
     */
    boolean isCallBackMode();

}
