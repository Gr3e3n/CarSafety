/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import androidx.annotation.NonNull;

/**
 * A listener of download events.
 *
 * @since 5
 */
public interface DownloadListener {

    /**
     * Called when a download task received.
     *
     * @since 5
     */
    void onTaskReceived(@NonNull DownloadTask task);

    /**
     * Called when a download status changed.
     *
     * @since 5
     */
    void onStatusChanged(@IOtaDownload.Status int status, @IOtaDownload.Reason int reason);

    /**
     * Called periodically as the download progresses.
     *
     * @param progress the approximate percentage that has been downloaded,
     *                 ranging from 0 to 100 (inclusive).
     * @since 5
     */
    void onProgressUpdate(int progress);

}
