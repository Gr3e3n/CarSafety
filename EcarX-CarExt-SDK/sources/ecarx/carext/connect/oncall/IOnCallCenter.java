/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.oncall;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the primary API for managing call center.
 *
 * @since 4
 */
public interface IOnCallCenter extends BaseAPI {

    /**
     * Public Safety Answering Point.
     *
     * @since 4
     */
    int CONFIG_PSAP = 0;

    /**
     * Third Party Service Provider.
     *
     * @since 4
     */
    int CONFIG_TPSP = 1;

    /**
     * Call center config.
     *
     * @since 4
     */
    @IntDef({CONFIG_PSAP, CONFIG_TPSP})
    @Retention(RetentionPolicy.SOURCE)
    @interface Config {
    }

    /**
     * Get supported configs.
     *
     * @return The empty array indicate there is no call center.
     * If there is only one element in the array, indicate configuration is not supported.
     * Otherwise you can call {@link #setConfig(int, int)} to change the configuration.
     * @since 4
     */
    @NonNull
    @Config
    @RequiresPermission(Permissions.ACCESS_ONCALL_CENTER)
    default int[] getSupportedConfigs(@Call.Type int callType) {
        throw new UnimplementedException();
    }

    /**
     * Get current config.
     *
     * @return the current config.
     * @since 4
     */
    @Config
    @RequiresPermission(Permissions.ACCESS_ONCALL_CENTER)
    default int getConfig(@Call.Type int callType) {
        throw new UnimplementedException();
    }

    /**
     * Set the config.
     *
     * @param callType call type.
     * @param config   new config.
     * @return {@code true} if the operation succeeded, {@code false} otherwise.
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_ONCALL_CENTER)
    default boolean setConfig(@Call.Type int callType, @Config int config) {
        throw new UnimplementedException();
    }
}
