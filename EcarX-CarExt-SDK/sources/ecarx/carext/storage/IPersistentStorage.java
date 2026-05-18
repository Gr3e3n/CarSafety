/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.storage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import java.io.FileNotFoundException;
import java.io.IOException;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the primary API for accessing persistent storage.
 *
 * @since 5
 */
public interface IPersistentStorage extends BaseAPI {

    /**
     * Reads bytes from the file denoted by the fileName.
     *
     * @param fileName the name of the file to read.
     * @return the data in the file.
     * @throws FileNotFoundException if the file does not exist.
     * @throws IOException           if an I/O error occurs.
     * @since 5
     */
    @NonNull
    @RequiresPermission(Permissions.READ_PERSISTENT_STORAGE)
    default byte[] readFromFile(@NonNull String fileName) throws IOException {
        throw new UnimplementedException();
    }

    /**
     * Writes bytes from the data byte array to a file denoted by the fileName.
     *
     * @param fileName the name of the file to write.
     * @param data     the data to be written.
     * @return true if the data is successfully written; false otherwise
     * @throws IOException if an I/O error occurs.
     * @since 5
     */
    @RequiresPermission(Permissions.WRITE_PERSISTENT_STORAGE)
    default boolean writeToFile(@NonNull String fileName, @NonNull byte[] data) throws IOException {
        throw new UnimplementedException();
    }

    /**
     * Deletes the file denoted by the fileName.
     *
     * @param fileName the name of the file to delete.
     * @return true if the file is successfully deleted; false otherwise
     * @throws FileNotFoundException if the file does not exist.
     * @throws IOException           if an I/O error occurs.
     * @since 5
     */
    @RequiresPermission(Permissions.WRITE_PERSISTENT_STORAGE)
    default boolean deleteFile(@NonNull String fileName) throws IOException {
        throw new UnimplementedException();
    }

}
