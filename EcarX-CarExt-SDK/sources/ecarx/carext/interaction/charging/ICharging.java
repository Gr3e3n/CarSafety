/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.interaction.charging;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.FunctionStatus;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for manager charging functionality.
 *
 * @since 7
 */
public interface ICharging extends BaseAPI {

    /**
     * schedule charging off
     *
     * @since 7
     */
    int SCHEDULE_CHARGING_MODE_OFF = 0x00;
    /**
     * schedule charging only single
     *
     * @since 7
     */
    int SCHEDULE_CHARGING_MODE_SINGLE = 0x01;
    /**
     * schedule charging cyclicity
     *
     * @since 7
     */
    int SCHEDULE_CHARGING_MODE_CYCLE = 0x02;

    @IntDef({SCHEDULE_CHARGING_MODE_OFF
            , SCHEDULE_CHARGING_MODE_SINGLE
            , SCHEDULE_CHARGING_MODE_CYCLE
    })
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @interface ScheduleChargingMode {
    }

    /**
     * Set the scheduled charging Mode.
     *
     * @param mode {@link ScheduleChargingMode}.
     * @return true - set success, false - set failed.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    default boolean setScheduleChargingMode(@ScheduleChargingMode int mode) {
        throw new UnimplementedException();
    }

    /**
     * Get the scheduled charging mode.
     *
     * @return {@link ScheduleChargingMode}.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    @ScheduleChargingMode
    default int getScheduleChargingMode() {
        throw new UnimplementedException();
    }

    /**
     * Set the schedule charging time.
     *
     * @param times if time is just one, use Calendar[0].
     *              if time are two, use Calendar[0] indicates start time, Calendar[1] indicates end time.
     * @return true - set success, false - set failed.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    default boolean setScheduleChargingTime(@NonNull Calendar[] times) {
        throw new UnimplementedException();
    }

    /**
     * Get the schedule charging time.
     *
     * @return Calendar[] if time is just one, use Calendar[0].
     * if time are two, use Calendar[0] indicates start time, Calendar[1] indicates end time.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    @NonNull
    default Calendar[] getScheduleChargingTime() {
        throw new UnimplementedException();
    }

    /**
     * Set the priority full charged status.
     *
     * @param status true - on, false = off.
     * @return true - set success, false - set failed.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    default boolean setPriorityFullyCharged(boolean status) {
        throw new UnimplementedException();
    }

    /**
     * Get the priority full charged status.
     *
     * @return true - on, false - off.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    default boolean isPriorityFullyCharged() {
        throw new UnimplementedException();
    }

    /**
     * Set the plug in charging switch status.
     *
     * @param status true - on, false = off.
     * @return true - set success, false - set failed.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    default boolean setPlugInCharging(boolean status) {
        throw new UnimplementedException();
    }

    /**
     * Get the plug in charging switch status.
     *
     * @return true - on, false - off.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    default boolean isPlugInCharging() {
        throw new UnimplementedException();
    }

    /**
     * Save the charging config Status.
     *
     * @return true - set success, false - set failed.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    default boolean saveChargingConfig() {
        throw new UnimplementedException();
    }

    /**
     * Get the charging config save Status.
     *
     * @return true - success, false - failed.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    default boolean getChargingConfigSaveStatus() {
        throw new UnimplementedException();
    }


    /**
     * Check charging function is supported or not.
     *
     * @return {@link FunctionStatus#AVAILABLE} indicates the call type is supported and can be dialed by APP side(Applications).
     * {@link FunctionStatus#UNAVAILABLE} indicates the call type is supported and can not be dialed by APP side(Applications).
     * {@link FunctionStatus#ERROR} indicates the call type is something error.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    @NonNull
    default FunctionStatus isSupportChargingConfig() {
        throw new UnimplementedException();
    }

    /**
     * Register charging config status listener, if anything changes will callback to {@link IChargingConfigListener}.
     *
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    default void registerChargingConfigListener(@NonNull IChargingConfigListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister charging config status listener.
     *
     * @param listener before the registered listener.
     * @since 7
     */
    @RequiresPermission(Permissions.MANAGE_CHARGING_CONFIG)
    default void unregisterChargingConfigListener(@NonNull IChargingConfigListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Charging config listener.
     *
     * @since 7
     */
    interface IChargingConfigListener {

        /**
         * Callback schedule charging mode changed.
         *
         * @param status {@link ScheduleChargingMode}
         * @since 7
         */
        void onScheduleChargingModeChanged(@ScheduleChargingMode int status);

        /**
         * callback priority fully charged switch changed.
         *
         * @param status true - on, false - off.
         * @since 7
         */
        void onPriorityFullyChargedConfigChanged(boolean status);

        /**
         * callback plug in charging switch changed.
         *
         * @param status true - on, false - off.
         * @since 7
         */
        void onPlugInChargingChanged(boolean status);

        /**
         * callback charging config save status changed.
         *
         * @param status true - success, false - failed.
         * @since 7
         */
        void onSaveChargingConfigStatusChanged(boolean status);

        /**
         * Callback charging function support status changed.
         *
         * @param functionStatus {@link FunctionStatus#AVAILABLE} indicates the call type is supported and can be dialed by APP side(Applications)
         *                       {@link FunctionStatus#UNAVAILABLE} indicates the call type is supported and can not be dialed by APP side(Applications)
         *                       {@link FunctionStatus#ERROR} indicates the call type is something error
         * @since 7
         */
        void onChargingFunctionStatusChanged(@NonNull FunctionStatus functionStatus);
    }
}
