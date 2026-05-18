/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.display.view.window;

import android.graphics.Rect;

import androidx.annotation.NonNull;

/**
 * Window information
 * more detail from 'adb shell dumpsys window windows'
 * see com.android.server.wm.WindowState
 *
 * @since 6
 */
public interface Window {
    /**
     * Display ID
     * see com.android.server.wm.WindowState#getDisplayId()
     *
     * @since 6
     */
    int getDisplayId();

    /**
     * Get the package name of this window
     * see {@link android.view.WindowManager.LayoutParams#packageName}
     *
     * @since 6
     */
    @NonNull
    String getPackage();

    /**
     * Get UserId and appId of the owner, the window belongs to
     * see com.android.server.wm.WindowState#mOwnerUid
     *
     * @since 6
     */
    int getUID();

    /**
     * Get window layer type
     * see {@link android.view.WindowManager.LayoutParams#type}
     *
     * @since 6
     */
    int getType();

    /**
     * Get view visibility
     * see com.android.server.wm.WindowState#mViewVisibility
     *
     * @see android.view.View#getVisibility()
     * @see android.view.View#VISIBLE
     * @see android.view.View#INVISIBLE
     * @see android.view.View#GONE
     * @since 6
     */
    int getViewVisibility();

    /**
     * Get the window identity
     * see com.android.server.wm.WindowState#getName()
     *
     * @since 6
     */
    @NonNull
    String getWindowIdentity();

    /**
     * Get the window tag
     * see com.android.server.wm.WindowState#getWindowTag()
     *
     * @since 6
     */
    @NonNull
    String getWindowTag();

    /**
     * Get the window frame
     * see com.android.server.wm.WindowState#mWindowFrames
     * see com.android.server.wm.WindowFrames#mContentFrame
     * see com.android.server.wm.WindowFrames#mVisibleInsets
     *
     * @since 6
     */
    @NonNull
    Rect getWindowFrame();
}