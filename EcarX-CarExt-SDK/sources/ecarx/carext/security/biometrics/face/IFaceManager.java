/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.security.biometrics.face;

import android.os.CancellationSignal;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for managing face recognition functionality.
 *
 * @since 6
 */
public interface IFaceManager {

    /**
     * Request authentication, This call operates the face recognition hardware
     * and starts capturing images. It terminates when
     * {@link AuthenticationCallback#onAuthenticationError(int, CharSequence)} is called, at
     * which point the object is no longer valid. The operation can be canceled by using the
     * provided cancel object.
     *
     * @param cancel   an object that can be used to cancel authentication
     * @param callback an object to receive authentication events
     * @param handler  an optional handler to handle callback events
     * @param userId   userId to authenticate for(not UserHandle.getUserId)
     * @since 6
     */
    @RequiresPermission(Permissions.ACCESS_BIOMETRIC)
    default void authenticate(@Nullable CancellationSignal cancel, @NonNull AuthenticationCallback callback, @Nullable Handler handler, @Nullable String userId) {
        throw new UnimplementedException();
    }

    /**
     * Request face authentication enrollment. This call operates the face authentication hardware
     * and starts capturing images. Progress will be indicated by callbacks to the
     * {@link EnrollmentCallback} object. It terminates when
     * {@link EnrollmentCallback#onEnrollmentError(int, CharSequence)} or
     * {@link EnrollmentCallback#onEnrollmentProgress(int) is called with remaining == 0, at
     * which point the object is no longer valid. The operation can be canceled by using the
     * provided cancel object.
     *
     * @param token    a unique token provided by a recent creation or verification of device
     *                 credentials (e.g. pin, pattern or password).
     * @param cancel   an object that can be used to cancel enrollment
     * @param callback an object to receive enrollment events
     * @since 6
     */
    @RequiresPermission(Permissions.MANAGE_BIOMETRIC)
    default void enroll(@Nullable byte[] hardwareAuthToken, @Nullable CancellationSignal cancel, @NonNull EnrollmentCallback callback) {
        throw new UnimplementedException();
    }

    /**
     * Request face authentication enrollment. This call operates the face authentication hardware
     * and starts capturing images. Progress will be indicated by callbacks to the
     * {@link EnrollmentCallback} object. It terminates when
     * {@link EnrollmentCallback#onEnrollmentError(int, CharSequence)} or
     * {@link EnrollmentCallback#onEnrollmentProgress(int) is called with remaining == 0, at
     * which point the object is no longer valid. The operation can be canceled by using the
     * provided cancel object.
     *
     * @param userId   Cloud account ID, non Android account UserID
     * @param token    a unique token provided by a recent creation or verification of device
     *                 credentials (e.g. pin, pattern or password).
     * @param cancel   an object that can be used to cancel enrollment
     * @param callback an object to receive enrollment events
     * @since 12
     */
    @RequiresPermission(Permissions.MANAGE_BIOMETRIC)
    default void enroll(@Nullable String userId, @Nullable byte[] hardwareAuthToken, @Nullable CancellationSignal cancel, @NonNull EnrollmentCallback callback) {
        throw new UnimplementedException();
    }

    /**
     * Remove given face template from face hardware and/or protected storage.
     * It is only visible to the car owner. If the car owner deletes a user's local data,
     * including user space and face data, they are required to delete
     *
     * @param userId   userId to authenticate for(not UserHandle.getUserId)
     * @param callback an optional callback to verify that face templates have been
     *                 successfully removed. May be null if no callback is required.
     * @since 6
     */
    @RequiresPermission(Permissions.MANAGE_BIOMETRIC)
    default void removeAll(@Nullable String userId, @NonNull RemovalCallback callback) {
        throw new UnimplementedException();
    }

    /**
     * Determine if there is a face enrolled(sync).
     *
     * @param userId   userId to authenticate for(not UserHandle.getUserId)
     * @return true if a face is enrolled, false otherwise
     * @since 6
     */
    @RequiresPermission(Permissions.ACCESS_BIOMETRIC)
    default boolean hasEnrolledTemplates(@NonNull String userId) {
        throw new UnimplementedException();
    }

    /**
     * Determine if there is a face enrolled(async).
     *
     * @param userId   userId to authenticate for(not UserHandle.getUserId)
     * @param callback Search result callback
     * @since 12
     */
    @RequiresPermission(Permissions.ACCESS_BIOMETRIC)
    default void hasEnrolledTemplates(@NonNull String userId, @NonNull SearchCallback callback) {
        throw new UnimplementedException();
    }

    /**
     * Search result callback.
     * resultCode 0:No facial recognition was recorded, 1:Enter facial features
     * @since 12
     */
    interface SearchCallback {
        void onSearchResult(int resultCode);
    }

    /**
     * Determine if face authentication sensor hardware is present and functional.
     *
     * @return true if hardware is present and functional, false otherwise.
     * @since 6
     */
    @RequiresPermission(Permissions.ACCESS_BIOMETRIC)
    default boolean isHardwareDetected() {
        throw new UnimplementedException();
    }

    /**
     * Callback structure provided to {@link #authenticate(CancellationSignal, AuthenticationCallback, Handler, String)}.
     * Users of this must provide an implementation of this for listening to face events.
     *
     * @since 6
     */
    interface AuthenticationCallback {

        /**
         * Called when an unrecoverable error has been encountered and the operation is complete.
         * No further callbacks will be made on this object.
         *
         * @param errorCode An integer identifying the error message
         * @param errString A human-readable error string that can be shown in UI
         * @since 6
         */
        void onAuthenticationError(int errorCode, @NonNull CharSequence errString);

        /**
         * Called when a recoverable error has been encountered during authentication. The help
         * string is provided to give the user guidance for what went wrong, such as
         * "Sensor dirty, please clean it."
         *
         * @param helpCode   An integer identifying the error message
         * @param helpString A human-readable string that can be shown in UI
         * @since 6
         */
        void onAuthenticationHelp(int helpCode, @NonNull CharSequence helpString);

        /**
         * Called when a face is recognized.
         *
         * @param userId    userId to authenticate for(not UserHandle.getUserId)
         * @since 6
         */
        void onAuthenticationSucceeded(@NonNull String userId);

        /**
         * Called when a face is detected but not recognized.
         *
         * @since 6
         */
        void onAuthenticationFailed();

        /**
         * Called when a face image has been acquired, but wasn't processed yet.
         *
         * @param acquireInfo one of FACE_ACQUIRED_* constants
         * @since 6
         */
        void onAuthenticationAcquired(int acquireInfo);
    }

    /**
     * Callback structure provided to {@link #enroll(byte[], CancellationSignal, EnrollmentCallback). Users
     * must provide an implementation of this to {@link #enroll(byte[], CancellationSignal, EnrollmentCallback) for listening to face enrollment events.
     *
     * @since 6
     */
    interface EnrollmentCallback {

        /**
         * Called when an unrecoverable error has been encountered and the operation is complete.
         * No further callbacks will be made on this object.
         *
         * @param errMsgId  An integer identifying the error message
         * @param errString A human-readable error string that can be shown in UI
         * @since 6
         */
        void onEnrollmentError(int errMsgId, @NonNull CharSequence errString);

        /**
         * Called when a recoverable error has been encountered during enrollment. The help
         * string is provided to give the user guidance for what went wrong, such as
         * "Image too dark, uncover light source" or what they need to do next, such as
         * "Rotate face up / down."
         *
         * @param helpMsgId  An integer identifying the error message
         * @param helpString A human-readable string that can be shown in UI
         * @since 6
         */
        void onEnrollmentHelp(int helpMsgId, @NonNull CharSequence helpString);

        /**
         * Called as each enrollment step progresses. Enrollment is considered complete when
         * remaining reaches 0. This function will not be called if enrollment fails. See
         * {@link EnrollmentCallback#onEnrollmentError(int, CharSequence)}
         *
         * @param remaining The number of remaining steps
         * @since 6
         */
        void onEnrollmentProgress(int remaining);

        /**
         * In the process of face registration, it is calculated from the initiation of the formal
         * face registration, and if it exceeds a certain period of time [tentatively 3 minutes],
         * if the registration is not successful, it will return to the input timeout input timeout
         * interface
         *
         * @since 6
         */
        void onEnrollmentTimeout();
    }

    /**
     * Callback structure provided to {@link #removeAll}. Users of {@link IFaceManager}
     * may
     * optionally provide an implementation of this to
     * {@link #removeAll(String, RemovalCallback)} for listening to face template
     * removal events.
     *
     * @since 6
     */
    interface RemovalCallback {

        /**
         * Called when the given face can't be removed.
         *
         * @param face      The face that the call attempted to remove
         * @param errMsgId  An associated error message id
         * @param errString An error message indicating why the face id can't be removed
         * @since 6
         */
        void onRemovalError(@Nullable Face face, int errMsgId, @NonNull CharSequence errString);

        /**
         * Called when a given face is successfully removed.
         *
         * @param face The face template that was removed.
         * @since 6
         */
        void onRemovalSucceeded(@Nullable Face face, int remaining);
    }
}
