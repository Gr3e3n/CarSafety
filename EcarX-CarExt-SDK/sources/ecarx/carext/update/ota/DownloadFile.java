/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import androidx.annotation.NonNull;

/**
 * Represents a file that needs to be downloaded.
 *
 * @since 5
 */
public interface DownloadFile {

    /**
     * Returns the name of the file.
     *
     * @since 5
     */
    @NonNull
    String getName();

    /**
     * Returns the signature of the file.
     *
     * @since 5
     */
    @NonNull
    byte[] getSignature();

    /**
     * Returns the download url of the file.
     *
     * @since 5
     */
    @NonNull
    String getDownloadUrl();

}
