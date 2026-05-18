/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.security.biometrics.face;

/**
 * Container for face metadata.
 *
 * @since 6
 */
public interface Face {

    /**
     * Gets the human-readable name for the given biometric.
     *
     * @return name given to the biometric.
     * @since 6
     */
    CharSequence getName();

    /**
     * Gets the device-specific biometric id.  Used by Settings to map a name to a specific
     * biometric template.
     * @since 6
     */
    int getBiometricId();

    /**
     * Device this biometric belongs to.
     * @since 6
     */
    long getDeviceId();
}