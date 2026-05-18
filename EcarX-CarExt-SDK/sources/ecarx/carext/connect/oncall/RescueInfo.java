/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.oncall;

/**
 * Represents a rescue.
 *
 * @since 4
 */
public interface RescueInfo {

    /**
     * Get estimated time of arrival.
     *
     * @return the time of arrival in milliseconds based on boot time.
     * @since 4
     */
    long getETA();

}