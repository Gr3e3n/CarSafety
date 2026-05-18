/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.base;

/**
 * The OpenAPI version info.
 *
 * @since 1
 */
public final class Version {
    /**
     * Current version value. <p> Before calling the caller need to check
     * whether the api exists in the current version.<p/>
     *
     * @since 1
     */
    public static final int VERSION_INT = 16;
    /**
     * Current build type.
     *
     * @since 1
     */
    public static final BuildType BUILD_TYPE = BuildType.RELEASE;

    /**
     * @since 1
     */
    public enum BuildType {
        /**
         * While a release is in release, APIs is stable and will not remove or change.
         *
         * @since 1
         */
        RELEASE,
        /**
         * While a release is in beta, APIs may be stable but can not guarantee not to modify.
         *
         * @since 1
         */
        BETA,
        /**
         * While a release is in alpha, APIs may be added, removed, or changed.
         *
         * @since 1
         */
        ALPHA,
    }
}
