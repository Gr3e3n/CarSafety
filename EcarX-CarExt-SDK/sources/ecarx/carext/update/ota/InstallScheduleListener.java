/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A listener of install schedule events.
 *
 * @since 5
 */
public interface InstallScheduleListener {
    /**
     * Represents the event comes from local.
     *
     * @since 5
     */
    int FROM_LOCAL = 0;
    /**
     * Represents the event comes from remote, such as the user's mobile.
     *
     * @since 5
     */
    int FROM_REMOTE = 1;

    /**
     * Represents where the event comes from.
     *
     * @since 5
     */
    @IntDef({FROM_LOCAL, FROM_REMOTE})
    @Retention(RetentionPolicy.SOURCE)
    @interface EventFrom {
    }

    /**
     * Called when the scheduled time changed.
     *
     * @param timestamp when the installation needs to perform in milliseconds since the epoch.
     * @since 5
     */
    void onTimeChanged(long timestamp, @EventFrom int from);

    /**
     * Called when the schedule canceled.
     *
     * @since 5
     */
    void onCanceled(@EventFrom int from);

    /**
     * Called when the scheduled time is up.
     *
     * @since 5
     */
    void onTimeUp();
}
