/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import android.os.Bundle;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A listener of install events.
 *
 * @since 5
 */
public interface InstallListener {


    /**
     * Installation status.
     *
     * @since 5
     */
    @IntDef({Status.PENDING, Status.DEFERRED, Status.COMPLETED, Status.POST_STARTED,
            Status.POST_COMPLETED, Status.FINISHED, Status.FAILED, Status.FAILED_CRITICAL,
            Status.ABORTED, Status.RESET_SYSTEM, Status.PRE_CHECKING})
    @Retention(RetentionPolicy.SOURCE)
    @interface Status {
        /**
         * Before installation.
         *
         * @since 5
         */
        int PENDING = 1;
        /**
         * Installation deferred.
         *
         * @since 5
         */
        int DEFERRED = 2;
        /**
         * Installation completed, need reboot.
         *
         * @since 5
         */
        int COMPLETED = 4;
        /**
         * Post check started.
         *
         * @since 5
         */
        int POST_STARTED = 5;
        /**
         * Post check completed.
         *
         * @since 5
         */
        int POST_COMPLETED = 6;
        /**
         * Installation finished but post check failed.
         *
         * @since 5
         */
        int FINISHED = 7;
        /**
         * Installation failed.
         *
         * @since 5
         */
        int FAILED = 8;
        /**
         * Installation failed critical.
         *
         * @since 5
         */
        int FAILED_CRITICAL = 9;
        /**
         * Installation aborted.
         *
         * @since 5
         */
        int ABORTED = 10;
        /**
         * Reset system.
         *
         * @since 5
         */
        int RESET_SYSTEM = 11;

        /**
         * Pre installation check
         *
         * @since 11
         */
        int PRE_CHECKING = 12;
    }


    /**
     * Called when the status changed.
     *
     * @since 5
     */
    void onStatusChanged(@Status int status, int reason);

    /**
     * Called when the status changed.
     *
     * @since 11
     */
    void onStatusChanged(@Status int status, int reasonType, String reasonContent);

    /**
     * Called when the installation should be performed immediately.
     *
     * @since 5
     */
    void onInstallNow();

    /**
     * Called periodically as the installed progresses.
     *
     * @param progress the approximate percentage that has been installed,
     *                 ranging from 0 to 100 (inclusive).
     * @param time Installation time remaining
     * @param extra extra information
     * @since 11
     */
    void onProgressUpdate(int progress, int time, Bundle extra);

    /**
     * Called when the installed remind.
     *
     * @param type 0:remind by window 1:remind by mini
     * @param content remind content
     *
     * @since 13
     */
    void onInstallationRemindCallback(int type, String content, Bundle bundle);
}

