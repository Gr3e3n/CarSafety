/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for managing ota download functionality.
 *
 * @since 5
 */
public interface IOtaDownload {
    /**
     * Certificate verified success.
     *
     * @since 5
     */
    int STATUS_CERTIFICATE_VERIFIED = 1;
    /**
     * Download started.
     *
     * @since 5
     */
    int STATUS_STARTED = 2;
    /**
     * Download paused.
     *
     * @since 5
     */
    int STATUS_PAUSED = 3;
    /**
     * Download resumed.
     *
     * @since 5
     */
    int STATUS_RESUMED = 4;
    /**
     * Download completed.
     *
     * @since 5
     */
    int STATUS_COMPLETED = 5;
    /**
     * Download failed.
     *
     * @since 5
     */
    int STATUS_FAILED = 6;
    /**
     * Download aborted.
     *
     * @since 5
     */
    int STATUS_ABORTED = 7;
    /**
     * Distribute download started.
     *
     * @since 5
     */
    int STATUS_DISTRIBUTE_STARTED = 8;
    /**
     * Distribute download paused.
     *
     * @since 5
     */
    int STATUS_DISTRIBUTE_PAUSED = 9;
    /**
     * Distribute download resumed.
     *
     * @since 5
     */
    int STATUS_DISTRIBUTE_RESUMED = 10;
    /**
     * Distribute download aborted.
     *
     * @since 5
     */
    int STATUS_DISTRIBUTE_ABORTED = 11;

    /**
     * Download status.
     *
     * @since 5
     */
    @IntDef({STATUS_CERTIFICATE_VERIFIED, STATUS_STARTED, STATUS_PAUSED, STATUS_RESUMED,
            STATUS_COMPLETED, STATUS_FAILED, STATUS_ABORTED, STATUS_DISTRIBUTE_STARTED,
            STATUS_DISTRIBUTE_PAUSED, STATUS_DISTRIBUTE_RESUMED, STATUS_DISTRIBUTE_ABORTED})
    @Retention(RetentionPolicy.SOURCE)
    @interface Status {
    }

    /**
     * Unknown reason.
     *
     * @since 5
     */
    int REASON_UNKNOWN = 0;
    /**
     * Url invalid.
     *
     * @since 5
     */
    int REASON_URL_INVALID = 1;
    /**
     * File verification failed.
     *
     * @since 5
     */
    int REASON_FILE_VERIFICATION_FAILED = 2;
    /**
     * Certificate verification failed.
     *
     * @since 5
     */
    int REASON_CERTIFICATE_VERIFICATION_FAILED = 3;

    /**
     * Failed reason.
     *
     * @since 5
     */
    @IntDef({REASON_UNKNOWN, REASON_URL_INVALID, REASON_FILE_VERIFICATION_FAILED,
            REASON_CERTIFICATE_VERIFICATION_FAILED})
    @Retention(RetentionPolicy.SOURCE)
    @interface Reason {
    }

    /**
     * Returns an download task. If there is no task, returns {@code null}.
     *
     * @since 5
     */
    @Nullable
    @RequiresPermission(Permissions.ACCESS_OTA_DOWNLOAD)
    default DownloadTask getDownloadTask() {
        throw new UnimplementedException();
    }

    /**
     * Consent download.
     *
     * @param installationOrder uuid of the task.
     * @see DownloadTask#getInstallationOrder()
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_OTA_DOWNLOAD)
    default boolean consentDownload(@NonNull String installationOrder) {
        throw new UnimplementedException();
    }

    /**
     * Report current download status and other info.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_OTA_DOWNLOAD)
    default boolean reportStatus(@Status int status, @Reason int reason, @NonNull String fileName) {
        throw new UnimplementedException();
    }

    /**
     * Report total size of downloaded files
     *
     * @param size The size, in bytes.
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_OTA_DOWNLOAD)
    default boolean reportDownloadedTotalSize(long size) {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain download event.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_OTA_DOWNLOAD)
    default boolean registerDownloadListener(@NonNull DownloadListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_OTA_DOWNLOAD)
    default void unregisterDownloadListener(@NonNull DownloadListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Check the connectivity status.
     *
     * @return whether this call was successful. The connectivity status will be notified later
     * through the callback {@link ConnectivityListener#onStatusChanged(boolean)}.
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_OTA_DOWNLOAD)
    default boolean checkConnectivityStatus() {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain connectivity change event.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_OTA_DOWNLOAD)
    default boolean registerConnectivityListener(@NonNull ConnectivityListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_OTA_DOWNLOAD)
    default void unregisterConnectivityListener(@NonNull ConnectivityListener listener) {
        throw new UnimplementedException();
    }

}
