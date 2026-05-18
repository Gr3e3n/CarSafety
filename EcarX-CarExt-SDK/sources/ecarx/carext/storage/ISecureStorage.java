/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.storage;

import java.io.IOException;

import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the primary API for accessing TEE secure storage.
 *
 * @since 12
 */
public interface ISecureStorage {

    /**
     * @param name The name of the secure storage to open.
     * @param flag The mode in which to open the secure storage. This should be one
     *             of the following constants:
     *             - SS_RD: Read-only mode.
     *             - SS_WR: Write-only mode.
     *             - SS_RDWR: Read-write mode.
     *             And can optionally include one or more of the following
     *             constants:
     *             - SS_SHARE: If you need to globally share a file, you need to add
     *             the SS_SHARE flag when creating and reading/writing the file.
     *             - SS_CREATE: If the specified file does not exist, create it.
     *             - SS_APPEND: Each write operation is appended to the end of the
     *             file.
     *             - SS_PERMANENT: The file is permanently stored and will not be
     *             deleted. This flag needs to be used with SS_CREATE.
     * @return On success, returns a non-negative integer representing the secure
     * storage handle. On failure, returns a negative error code.
     * @brief Opens a secure storage.
     * <p>
     * This function opens a secure storage with the given name. The flag
     * parameter determines the mode in which the storage is opened.
     */
    default int openSecureStorage(String name, int flag) throws IOException {
        throw new UnimplementedException();
    }

    /**
     * @param fd The file descriptor of the secure storage to close. This should be
     *           a value that was returned by secure_storage_open or
     *           secure_storage_open2.
     * @return On success, returns 0. On failure, returns a negative error code.
     * @brief Closes a secure storage.
     * <p>
     * This function closes a secure storage that was previously opened with
     * secure_storage_open or secure_storage_open2.
     */
    default int closeSecureStorage(int fd) throws IOException {
        throw new UnimplementedException();
    }

    /**
     * @param fd    The file descriptor of the secure storage to read from. This
     *              should be a value that was returned by secure_storage_open or
     *              secure_storage_open2.
     * @param buf   The read data buffer
     * @param count The maximum number of bytes to be read.
     * @return On success, the number of bytes read is returned (zero indicates end of file).
     * On failure, a negative error code is returned.
     * @brief Reads data from a secure storage.
     * <p>
     * This function reads up to 'count' bytes from the secure storage
     * referred to by the file descriptor 'fd' into the buffer starting at
     * 'buf'.
     */
    default int readSecureStorage(int fd, int count, byte[] buf) throws IOException {
        throw new UnimplementedException();
    }

    /**
     * @param fd    The file descriptor of the secure storage to write to. This
     *              should be a value that was returned by secure_storage_open or
     *              secure_storage_open2.
     * @param buf   The buffer containing the data to be written.
     * @param count The maximum number of bytes to be written.
     * @return On success, the number of bytes written is returned (zero indicates
     * nothing was written). On failure, a negative error code is returned.
     * @brief Writes data to a secure storage.
     * <p>
     * This function writes up to 'count' bytes from the buffer starting at
     * 'buf' to the secure storage referred to by the file descriptor 'fd'.
     */
    default int writeSecureStorage(int fd, byte[] buf, int count) throws IOException {
        throw new UnimplementedException();
    }

    /**
     * @param fd     The file descriptor of the secure storage. This should be a
     *               value that was returned by secure_storage_open or
     *               secure_storage_open2.
     * @param offset The number of bytes to offset from 'whence'.
     * @param whence The position from where to offset. This should be one of the
     *               following constants:
     *               - SEEK_SET: The offset is set to 'offset' bytes.
     *               - SEEK_CUR: The offset is set to its current location plus
     *               'offset' bytes.
     *               - SEEK_END: The offset is set to the size of the secure storage
     *               plus 'offset' bytes.
     * @return On success, the resulting offset location as measured in bytes from
     * the beginning of the secure storage is returned. On failure, a
     * negative error code is returned.
     * @brief Changes the file position of a secure storage.
     * <p>
     * This function sets the file position indicator for the secure storage
     * referred to by the file descriptor 'fd'. The new position, measured in
     * bytes, is obtained by adding 'offset' bytes to the position specified
     * by 'whence'.
     */
    default int seekSecureStorage(int fd, long offset, int whence) throws IOException {
        throw new UnimplementedException();
    }
    /**
     * @param name The name of the secure storage to delete.
     * @return On success, returns 0. On failure, returns a negative error code.
     * @brief Deletes a secure storage.
     * <p>
     * This function deletes a secure storage with the given name.
     */
    default int deleteSecureStorage(String name) throws IOException {
        throw new UnimplementedException();
    }

    /**
     * @return On success, returns 0. On failure, returns a negative error code.
     * @brief Resets all normal secure storages.
     * <p>
     * This function deletes all normal (non-permanent) secure storages.
     */
    default int resetSecureStorage(int flag) throws IOException {
        throw new UnimplementedException();
    }

}
