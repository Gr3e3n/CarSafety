/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * A listener of version events.
 *
 * @since 5
 */
public interface VersionListener {
    /**
     * Called when a target version received.
     *
     * @since 5
     */
    void onTargetVersionReceived(@NonNull VersionInfo versionInfo);

    /**
     * Called when request get history version info.
     *
     * @since 11
     */
    void onHistoryVersionInfo(@NonNull List<VersionInfo> versionInfo);
}
