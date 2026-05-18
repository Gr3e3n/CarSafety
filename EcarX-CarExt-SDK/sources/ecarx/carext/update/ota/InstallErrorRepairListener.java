/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import java.util.List;

/**
 * A listener of install error repair.
 *
 * @since 11
 */
public interface InstallErrorRepairListener {

    void onRepairInstallStateCallback(List<InstallErrorEcu> errorEcus);
}

