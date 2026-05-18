/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.display.view;

import android.view.Display;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ecarx.carext.base.Permissions;
import ecarx.carext.display.view.window.WindowViewObserver;
import ecarx.carext.base.exception.UnimplementedException;
import ecarx.carext.display.view.window.WindowObserver;

/**
 * This class provides the API for accessing window functionality.
 *
 * @since 5
 */
public interface IWindow {
    /**
     * OTA popup
     *
     * @since 5
     */
    int CODE_OTA_POPUP = 1;

    /**
     * Window level applications, such as CPAA, pop of hvac...
     *
     * @since 11
     */
    int CODE_POPUP_APPLICATION = 101;

    /**
     * VPA
     *
     * @since 11
     */
    int CODE_VPA = 201;

    /**
     * Screen saver, scene mode
     *
     * @since 11
     */
    int CODE_SCREEN_SAVER = 301;

    /**
     * Hard key
     *
     * @since 11
     */
    int CODE_HARD_KEY = 401;

    /**
     * Emergency pop
     *
     * @since 11
     */
    int CODE_EMERGENCY_ALERT = 501;

    /**
     * Call
     *
     * @since 11
     */
    int CODE_XCALL = 601;

    /**
     * AVM/360
     *
     * @since 11
     */
    int CODE_CAMERA = 701;

    /**
     * The highest pop: Thermal runaway、SOS...
     *
     * @since 11
     */
    int CODE_HIGHEST = 801;

    /**
     * Window code.
     *
     * @since 5
     */
    @IntDef({CODE_OTA_POPUP,
            CODE_POPUP_APPLICATION,
            CODE_VPA,
            CODE_SCREEN_SAVER,
            CODE_HARD_KEY,
            CODE_EMERGENCY_ALERT,
            CODE_XCALL,
            CODE_CAMERA,
            CODE_HIGHEST})
    @Retention(RetentionPolicy.SOURCE)
    @interface WindowCode {
    }

    /**
     * Returns window type according to the window code.
     *
     * @param code The code of window.
     * @return The type of window.
     * @see android.view.WindowManager.LayoutParams#type
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_WINDOW_TYPE)
    default int getWindowTypeByCode(@WindowCode int code) {
        throw new UnimplementedException();
    }

    /**
     * Returns window type according to the window code and the display.
     *
     * @param code    The code of window.
     * @param display The display to show the window on.
     * @return The type of window.
     * @see android.view.WindowManager.LayoutParams#type
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_WINDOW_TYPE)
    default int getWindowTypeByCode(@WindowCode int code, @NonNull Display display) {
        throw new UnimplementedException();
    }

    /**
     * Register Window observer, A process can only hold one callback instance
     *
     * @param observer Window observer
     * @see WindowObserver
     * @see WindowViewObserver
     * @since 6
     */
    @RequiresPermission(Permissions.WINDOW_OBSERVER)
    default boolean registerWindowObserver(@NonNull WindowObserver observer) {
        throw new UnimplementedException();
    }

    /**
     * Unregister Window observer
     *
     * @param observer Window observer
     * @see WindowObserver
     * @see WindowViewObserver
     * @since 6
     */
    @RequiresPermission(Permissions.WINDOW_OBSERVER)
    default boolean unregisterWindowObserver(@NonNull WindowObserver observer) {
        throw new UnimplementedException();
    }

}
