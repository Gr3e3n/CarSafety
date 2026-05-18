/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import android.os.Bundle;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The bean of install error ecu
 *
 * @since 11
 */
public interface InstallErrorEcu {

    /**
     * ECU state.
     *
     * @since 11
     */
    @IntDef({EcuStatus.SUCCESS,
            EcuStatus.UPDATING,
            EcuStatus.NO_INSTALLED})
    @Retention(RetentionPolicy.SOURCE)
    @interface EcuStatus {
        /**
         * Installation success.
         *
         * @since 11
         */
        int SUCCESS = 0x01;
        /**
         * Installation in progress.
         *
         * @since 11
         */
        int UPDATING = 0x02;
        /**
         * Not installed.
         *
         * @since 11
         */
        int NO_INSTALLED = 0x03;
    }

    /**
     * Returns baseline software structure identifier.
     *
     * @since 11
     */
    @NonNull
    String getEcuName();


    /**
     * Returns the time spent for installation.
     *
     * @return The time in seconds.
     * @since 11
     */
    @EcuStatus
    int getEcuState();

    /**
     * Returns the repair progress when ecu state is UPDATING.
     *
     * @return The repair progress(0-100).
     * @since 15
     */
    default int getRepairProgress() {
        return 0;
    };

    /**
     * Returns extra info.
     *
     * @return extra info.
     * @since 15
     */
    default @NonNull Bundle getExtraInfo() {
        return new Bundle();
    }
}
