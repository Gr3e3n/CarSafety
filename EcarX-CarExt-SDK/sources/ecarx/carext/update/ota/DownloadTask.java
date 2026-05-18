/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Represents a download task.
 *
 * @since 5
 */
public interface DownloadTask {

    /**
     * Returns the uuid of the task.
     *
     * @see VersionInfo#getInstallationOrder()
     * @since 5
     */
    @NonNull
    String getInstallationOrder();

    /**
     * Returns a certificate containing a public key used to verify the download files.
     *
     * @since 5
     */
    @NonNull
    byte[] getCertificate();

    /**
     * Returns a list of the files that needs to be downloaded.
     *
     * @since 5
     */
    @NonNull
    List<DownloadFile> getDownloadFiles();

    /**
     * Returns the total size of download files.
     *
     * @return The size, in bytes.
     * @since 5
     */
    long getTotalSize();

    /**
     * Returns the directory where the files needs to be saved.
     *
     * @since 5
     */
    @NonNull
    String getSavedDirectory();

}
