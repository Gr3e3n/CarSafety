/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.base;

import androidx.annotation.NonNull;

/**
 * Common base interface for all apis.
 *
 * @since 1
 */
public interface BaseAPI {

    /**
     * Initialize the api.
     *
     * <p>Access to other function defined in this api should happen
     * after {@link Callback#onAPIReady(boolean)} call with {@code ready} set {@code true}.</p>
     *
     * @param callback Callback to notify the api is ready or not.
     * @since 1
     */
    void init(@NonNull Callback callback);

    /**
     * Callback to notify the api is ready or not.
     *
     * <p>When {@link Callback#onAPIReady(boolean)} is called with ready set to false,
     * access to other function defined in this api should stop until api is ready again.
     * Otherwise the caller will receive the {@link IllegalStateException} exception.</p>
     *
     * @since 1
     */
    interface Callback {

        /**
         * The api status change.
         *
         * @param ready When {@code true} the api is ready and all accesses are ok.
         *              Otherwise the api should not be called anymore.
         * @since 1
         */
        void onAPIReady(boolean ready);

    }

    /**
     * Release resources.
     *
     * <p>When the API call is completed,
     * this function must be called to reclaim resources.</p>
     *
     * @since 2
     */
    void onRelease();

}

