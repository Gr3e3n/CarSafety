/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.util.List;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.BaseCallBack;
import ecarx.carext.base.FunctionStatus;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for accessing ota functionality.
 *
 * @since 5
 */
public interface IOta extends BaseAPI {

    /**
     * Get current function status.
     *
     * @since 5
     */
    @NonNull
    @RequiresPermission(Permissions.ACCESS_OTA)
    default FunctionStatus getFunctionStatus() {
        throw new UnimplementedException();
    }

    /**
     * Returns current version.
     *
     * @since 5
     */
    @NonNull
    @RequiresPermission(Permissions.ACCESS_OTA)
    default VersionInfo getCurrentVersion() {
        throw new UnimplementedException();
    }

    /**
     * Returns an updated version. If there is no new version, returns {@code null}.
     *
     * @since 5
     */
    @Nullable
    @RequiresPermission(Permissions.ACCESS_OTA)
    default VersionInfo getTargetVersion() {
        throw new UnimplementedException();
    }

    /**
     * Request the list of history version info {@link  VersionListener#onHistoryVersionInfo(List)}.
     *
     * @since 11
     */
    @RequiresPermission(Permissions.ACCESS_OTA)
    default boolean requestHistoryVersionInfo() {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain version change event.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_OTA)
    default boolean registerVersionListener(@NonNull VersionListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_OTA)
    default void unregisterVersionListener(@NonNull VersionListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Set the UUID for this update task.
     *
     * @since 11
     */
    @RequiresPermission(Permissions.ACCESS_OTA)
    default void setUpdateTaskUuid(@NonNull String taskUuid) {
        throw new UnimplementedException();
    }

    /**
     * Homepage check for version updates.
     * #{@link VersionListener#onTargetVersionReceived(VersionInfo)}
     *
     * @since 13
     */
    @RequiresPermission(Permissions.ACCESS_OTA)
    default void checkVersionUpdate() {
        throw new UnimplementedException();
    }

    /**
     * Async get current version.
     *
     * @since 13
     */
    default void getCurrentVersion(BaseCallBack<VersionInfo> callback){
        throw new UnimplementedException();
    }

    /**
     * Async get single historical version details.
     *
     * @since 13
     */
    default void getHistoryVersionDetailInfo(String versionName, BaseCallBack<VersionInfo> callback){
        throw new UnimplementedException();
    }

    /**
     * Returns a manager used to accessing ota download functionality.
     *
     * @since 5
     */
    @NonNull
    default IOtaDownload getDownloadManager() {
        throw new UnimplementedException();
    }

    /**
     * Returns a manager used to accessing ota install functionality.
     *
     * @since 5
     */
    @NonNull
    default IOtaInstall getInstallManager() {
        throw new UnimplementedException();
    }

    /**
     * Returns a manager used to repair error of ota install.
     *
     * @since 11
     */
    @NonNull
    default IOtaInstallErrorRepair getRepairInstallManager() {
        throw new UnimplementedException();
    }
}
