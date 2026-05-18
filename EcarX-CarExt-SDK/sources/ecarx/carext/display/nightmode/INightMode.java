/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.display.nightmode;

import android.app.UiModeManager;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.LocalTime;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for accessing night mode functionality.
 *
 * @since 5
 */
public interface INightMode extends BaseAPI {
    /**
     * Constant for {@link #setNightMode(int)} and {@link #getNightMode()}:
     * night mode value invalid.
     *
     * @see NightMode
     * @since 5
     */
    int MODE_NIGHT_INVALID = -1;
    /**
     * Constant for {@link #setNightMode(int)} and {@link #getNightMode()}:
     * automatically switch night mode on and off based on the sunlight time.
     *
     * @see NightMode
     * @since 5
     */
    int MODE_NIGHT_SUNRISE_AND_SUNSET = UiModeManager.MODE_NIGHT_AUTO;
    /**
     * Constant for {@link #setNightMode(int)} and {@link #getNightMode()}:
     * never run in night mode.
     *
     * @see NightMode
     * @since 5
     */
    int MODE_NIGHT_NO = UiModeManager.MODE_NIGHT_NO;
    /**
     * Constant for {@link #setNightMode(int)} and {@link #getNightMode()}:
     * always run in night mode.
     *
     * @see NightMode
     * @since 5
     */
    int MODE_NIGHT_YES = UiModeManager.MODE_NIGHT_YES;
    /**
     * Constant for {@link #setNightMode(int)} and {@link #getNightMode()}:
     * automatically switch night mode on and off based on the time.
     *
     * @see NightMode
     * @since 5
     */
    int MODE_NIGHT_CUSTOM = UiModeManager.MODE_NIGHT_CUSTOM;
    /**
     * Constant for {@link #setNightMode(int)} and {@link #getNightMode()}:
     * automatically switch night mode on and off based on sunlight sensor.
     *
     * @see NightMode
     * @since 5
     */
    int MODE_NIGHT_SUNLIGHT_SENSOR = 99;

    /**
     * NightMode
     * <p>
     * {@link #MODE_NIGHT_INVALID}<br/>
     * {@link #MODE_NIGHT_SUNRISE_AND_SUNSET}<br/>
     * {@link #MODE_NIGHT_NO}<br/>
     * {@link #MODE_NIGHT_YES}<br/>
     * {@link #MODE_NIGHT_CUSTOM}<br/>
     * {@link #MODE_NIGHT_SUNLIGHT_SENSOR}<br/>
     *
     * @since 5
     */
    @IntDef({MODE_NIGHT_INVALID, MODE_NIGHT_SUNRISE_AND_SUNSET, MODE_NIGHT_NO, MODE_NIGHT_YES,
            MODE_NIGHT_CUSTOM, MODE_NIGHT_SUNLIGHT_SENSOR})
    @Retention(RetentionPolicy.SOURCE)
    @interface NightMode {
    }

    /**
     * Sets the system-wide night mode.
     * <p>
     * The mode can be one of:
     * <ul>
     *   <li><em>{@link #MODE_NIGHT_NO}<em> sets the device into
     *       {@code notnight} mode</li>
     *   <li><em>{@link #MODE_NIGHT_YES}</em> sets the device into
     *       {@code night} mode</li>
     *   <li><em>{@link #MODE_NIGHT_CUSTOM}</em> automatically switches between
     *       {@code night} and {@code notnight} based on the custom time set (or default)</li>
     *   <li><em>{@link #MODE_NIGHT_SUNRISE_AND_SUNSET}</em> automatically switches between
     *       {@code night} and {@code notnight} based on the device's current
     *       location and sunlight time</li>
     *   <li><em>{@link #MODE_NIGHT_SUNLIGHT_SENSOR}</em> automatically switches between
     *       {@code night} and {@code notnight} based on sunlight sensor</li>
     * </ul>
     *
     * @param mode the night mode to set
     * @see #getNightMode()
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_NIGHT_MODE)
    default boolean setNightMode(@NightMode int mode) {
        throw new UnimplementedException();
    }

    /**
     * Returns the currently configured night mode.
     * <p>
     * May be one of:
     * <ul>
     *   <li>{@link #MODE_NIGHT_NO}</li>
     *   <li>{@link #MODE_NIGHT_YES}</li>
     *   <li>{@link #MODE_NIGHT_SUNLIGHT_SENSOR}</li>
     *   <li>{@link #MODE_NIGHT_CUSTOM}</li>
     *   <li>{@link #MODE_NIGHT_SUNRISE_AND_SUNSET}</li>
     *   <li>{@link #MODE_NIGHT_INVALID} on error</li>
     * </ul>
     *
     * @return the current night mode, or {@link #MODE_NIGHT_INVALID} on error
     * @see #setNightMode(int)
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_NIGHT_MODE)
    @NightMode
    default int getNightMode() {
        throw new UnimplementedException();
    }

    /**
     * Returns the time of the day Dark theme activates
     * <p>
     * When night mode is {@link #MODE_NIGHT_CUSTOM}, the system uses
     * this time set to activate it automatically.
     *
     * @since 5
     */
    @NonNull
    @RequiresPermission(Permissions.ACCESS_NIGHT_MODE)
    default LocalTime getCustomNightModeStart() {
        throw new UnimplementedException();
    }

    /**
     * Sets the time of the day Dark theme activates
     * <p>
     * When night mode is {@link #MODE_NIGHT_CUSTOM}, the system uses
     * this time set to activate it automatically
     *
     * @param time The time of the day Dark theme should activate
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_NIGHT_MODE)
    default boolean setCustomNightModeStart(@NonNull LocalTime time) {
        throw new UnimplementedException();
    }

    /**
     * Returns the time of the day Dark theme deactivates
     * <p>
     * When night mode is {@link #MODE_NIGHT_CUSTOM}, the system uses
     * this time set to deactivate it automatically.
     *
     * @since 5
     */
    @NonNull
    @RequiresPermission(Permissions.ACCESS_NIGHT_MODE)
    default LocalTime getCustomNightModeEnd() {
        throw new UnimplementedException();
    }

    /**
     * Sets the time of the day Dark theme deactivates
     * <p>
     * When night mode is {@link #MODE_NIGHT_CUSTOM}, the system uses
     * this time set to deactivate it automatically.
     *
     * @param time The time of the day Dark theme should deactivate
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_NIGHT_MODE)
    default boolean setCustomNightModeEnd(@NonNull LocalTime time) {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain night mode event.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_NIGHT_MODE)
    default boolean registerNightModeListener(NightModeListener listener) {
        throw new UnimplementedException();
    }

    /***
     * Unregister the observer.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_NIGHT_MODE)
    default void unregisterNightModeListener(NightModeListener listener) {
        throw new UnimplementedException();
    }

}
