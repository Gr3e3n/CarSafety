/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.display.view.window;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * Window observer
 *
 * @since 6
 */
public interface WindowObserver {
    /**
     * Callback when a new window added
     *
     * @param window The window information has added
     * @see android.view.WindowManager#addView(View, ViewGroup.LayoutParams)
     * @since 6
     */
    void onWindowAdded(@NonNull Window window);

    /**
     * Callback when a new window removed
     *
     * @param window The window information has removed
     * @see android.view.WindowManager#removeView(View)
     * @since 6
     */
    void onWindowRemoved(@NonNull Window window);
}
