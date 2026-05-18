/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import android.os.Bundle;

import androidx.annotation.NonNull;

/**
 * Represents a version.
 *
 * @since 5
 */
public interface VersionInfo {
    /**
     * Returns baseline software structure identifier.
     *
     * @since 5
     */
    @NonNull
    String getBSSID();

    /**
     * Returns the installation order(taskUuid).
     *
     * @see DownloadTask#getInstallationOrder()
     * @since 5
     */
    @NonNull
    String getInstallationOrder();

    /**
     * Returns the version name.
     *
     * @since 5
     */
    @NonNull
    String getVersionName();

    /**
     * Returns the description.
     *
     * @since 5
     */
    @NonNull
    String getDescription();

    /**
     * Returns the total size of this version.
     *
     * @return The size, in bytes.
     * @since 5
     */
    long getTotalSize();

    /**
     * Returns the time spent for installation.
     *
     * @return The time in seconds.
     * @since 5
     */
    int getInstallationSpentTime();

    /**
     * Returns extra info.
     *
     * @return extra info.
     * @since 12
     */
    default @NonNull Bundle getExtraInfo() {
        return new Bundle();
    }
}
