/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.net.wifi;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Interface for callback invocation on an application action
 *
 * @since 4
 */
public interface ActionListener {
    /**
     * Passed with {@link ActionListener#onFailure}.
     * Indicates that the operation failed due to an internal error.
     *
     * @since 4
     */
    int ERROR = 0;

    /**
     * Passed with {@link ActionListener#onFailure}.
     * Indicates that the operation is already in progress
     *
     * @since 4
     */
    int IN_PROGRESS = 1;

    /**
     * Passed with {@link ActionListener#onFailure}.
     * Indicates that the operation failed because the framework is busy and
     * unable to service the request
     *
     * @since 4
     */
    int BUSY = 2;

    /**
     * Failure reason.
     *
     * @since 4
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ERROR, IN_PROGRESS, BUSY})
    @interface ActionListenerFailureReason {
    }

    /**
     * The operation succeeded.
     *
     * @since 4
     */
    void onSuccess();

    /**
     * The operation failed.
     *
     * @param reason The reason for failure depends on the operation.
     * @since 4
     */
    void onFailure(@ActionListenerFailureReason int reason);
}
