/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.display.view.window;

import android.graphics.Rect;

import androidx.annotation.NonNull;

/**
 * Window Root View observer
 *
 * @since 6
 */
public interface WindowViewObserver extends WindowObserver {
    /**
     * Callback when the visibility state of the window root view changed
     *
     * @param window     The window information of the root view
     * @param visibility The visibility state of the window root view
     * @see android.view.View#onWindowVisibilityChanged(int)
     * @see android.view.View#getVisibility()
     * @see android.view.View#VISIBLE
     * @see android.view.View#INVISIBLE
     * @see android.view.View#GONE
     * @since 6
     */
    void onWindowVisibilityChanged(@NonNull Window window, int visibility);

    /**
     * Callback when the size of the window root view changed
     * <p>
     * see com.android.server.wm.WindowState#mWindowFrames
     * see com.android.server.wm.WindowFrames#mContentFrame
     * see com.android.server.wm.WindowFrames#mVisibleInsets
     *
     * @param window   The window information of the root view
     * @param oldFrame The old frame of the window root view
     * @param newFrame The new frame of the window root view
     * @see android.view.View#onSizeChanged(int, int, int, int)
     * @see android.view.View#getGlobalVisibleRect(Rect)
     * @since 6
     */
    void onWindowFrameChanged(@NonNull Window window, @NonNull Rect oldFrame, @NonNull Rect newFrame);
}
