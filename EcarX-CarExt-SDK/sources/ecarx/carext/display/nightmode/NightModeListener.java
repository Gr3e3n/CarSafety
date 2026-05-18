/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.display.nightmode;

/**
 * A listener of night mode events.
 *
 * @since 5
 */
public interface NightModeListener {
    /**
     * Called when the night mode changed.
     *
     * @since 5
     */
    void onNightModeChanged(@INightMode.NightMode int mode);
}