/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import java.util.List;

import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for installation anomaly repair.
 *
 * @since 11
 */
public interface IOtaInstallErrorRepair {

    /**
     * Request get the list of install error ecu.
     * through the callback {@link InstallErrorRepairListener#onRepairInstallStateCallback(List)}.
     *
     * @since 11
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean requestInstallErrorList() {
        throw new UnimplementedException();
    }

    /**
     * Fix the installation errors of ECU.
     *
     * @return whether this call was successful. The status will be notified later
     * through the callback {@link InstallErrorRepairListener#onRepairInstallStateCallback(List)}.
     * @since 11
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean repairInstallError() {
        throw new UnimplementedException();
    }
    /**
     * Register an observer to obtain install error repair.
     *
     * @since 11
     */
    @RequiresPermission(Permissions.ACCESS_OTA_INSTALL)
    default boolean registerErrorRepairListener(@NonNull InstallErrorRepairListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @since 11
     */
    @RequiresPermission(Permissions.ACCESS_OTA_INSTALL)
    default void unregisterErrorRepairListener(@NonNull InstallErrorRepairListener listener) {
        throw new UnimplementedException();
    }

}
