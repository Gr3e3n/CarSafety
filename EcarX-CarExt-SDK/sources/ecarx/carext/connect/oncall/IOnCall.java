/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.oncall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.FunctionStatus;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * Call the call center for help.
 *
 * @since 4
 */
public interface IOnCall extends BaseAPI {

    /**
     * Get current function status.
     *
     * @since 4
     */
    @NonNull
    @RequiresPermission(Permissions.ACCESS_ONCALL)
    default FunctionStatus getFunctionStatus(@Call.Type int callType) {
        throw new UnimplementedException();
    }

    /**
     * Get the current call.
     *
     * @return null indicates not in a call.
     * @since 4
     */
    @Nullable
    @RequiresPermission(Permissions.ACCESS_ONCALL)
    default Call getCurrentCall() {
        throw new UnimplementedException();
    }

    /**
     * Start a new outgoing call.
     *
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_ONCALL)
    default void startCall(@Call.Type int callType) {
        throw new UnimplementedException();
    }

    /**
     * Accept a ringing incoming call.
     *
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_ONCALL)
    default void acceptCall() {
        throw new UnimplementedException();
    }

    /**
     * Ends the call. If there is a ringing call, calling this method rejects the ringing call.
     * Otherwise the call is ended.
     *
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_ONCALL)
    default void endCall() {
        throw new UnimplementedException();
    }
	
	/**
     * Keyboard function transfers number to framework for Call service evaluation
     *
     * @since 4
     */
    @RequiresPermission(Permissions.MANAGE_ONCALL)
    default void callServiceEval(int number) {
        throw new UnimplementedException();
    }

    /**
     * Get rescue info.
     *
     * @return null indicates no rescue.
     * @since 4
     */
    @Nullable
    @RequiresPermission(Permissions.ACCESS_ONCALL)
    default RescueInfo getRescueInfo() {
        throw new UnimplementedException();
    }

    /**
     * Register call listener.
     * If there is a call or rescue, you will receive a corresponding callback immediately.
     *
     * @param listener The callback that will run.
     * @return {@code true} if the operation succeeded, {@code false} otherwise.
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_ONCALL)
    default boolean registerCallListener(@NonNull ICallListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister call listener.
     *
     * @param listener The callback that don't want to receive event.
     * @since 4
     */
    @RequiresPermission(Permissions.ACCESS_ONCALL)
    default void unregisterCallListener(@NonNull ICallListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Call listener.
     *
     * @since 4
     */
    interface ICallListener {
        /**
         * Called when a call changed.
         *
         * @param call indicate a call.
         * @since 4
         */
        void onCallChanged(@NonNull Call call);

        /**
         * Called when a rescue info changed.
         *
         * @param rescueInfo indicate a rescue.
         * @since 4
         */
        void onRescueInfoChanged(@NonNull RescueInfo rescueInfo);

        /**
         * Called when the ICall available status changed.
         *
         * @since 11
         */
        void onICallSystemStatusChanged(@NonNull int state);

        /**
         * Called when the ECall data status changed.
         *
         * @param state indicate a rescue.
         * @since 11
         */
        void onECallSendingDataStatusChanged(@NonNull int state);
    }

}
