/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.base;

/**
 * All of permissions used.
 *
 * @since 1
 */
public final class Permissions {
    /**
     * Allows an application to read the RVDC data.
     * Protection level:signature|privileged
     *
     * @since 1
     */
    public static final String READ_RVDC = "ecarx.openapi.permission.READ_RVDC";
    /**
     * Allows an application to write the RVDC data.
     * Protection level:signature|privileged
     *
     * @since 1
     */
    public static final String WRITE_RVDC = "ecarx.openapi.permission.WRITE_RVDC";

    /**
     * Allows an application to access extended Bluetooth functionality.
     * Protection level:signature|privileged
     *
     * @since 2
     */
    public static final String ACCESS_EXPENDED_BLUETOOTH = "ecarx.openapi.permission.ACCESS_EXPANDED_BLUETOOTH ";

    /**
     * Allows an application to access oncall functionality.
     * Protection level:signature|privileged
     *
     * @since 4
     */
    public static final String ACCESS_ONCALL = "ecarx.openapi.permission.ACCESS_ONCALL";
    /**
     * Allows an application to manage oncall functionality.
     * Protection level:signature|privileged
     *
     * @since 4
     */
    public static final String MANAGE_ONCALL = "ecarx.openapi.permission.MANAGE_ONCALL";
    /**
     * Allows an application to access oncall center functionality.
     * Protection level:signature|privileged
     *
     * @since 4
     */
    public static final String ACCESS_ONCALL_CENTER = "ecarx.openapi.permission.ACCESS_ONCALL_CENTER";
    /**
     * Allows an application to manage oncall center functionality.
     * Protection level:signature|privileged
     *
     * @since 4
     */
    public static final String MANAGE_ONCALL_CENTER = "ecarx.openapi.permission.MANAGE_ONCALL_CENTER";

    /**
     * Allows an application to access Wi-Fi Sta functionality.
     * Protection level:signature|privileged
     *
     * @since 4
     */
    public static final String ACCESS_WIFI_STA = "ecarx.openapi.permission.ACCESS_WIFI_STA";
    /**
     * Allows an application to manage Wi-Fi Sta functionality.
     * Protection level:signature|privileged
     *
     * @since 4
     */
    public static final String MANAGE_WIFI_STA = "ecarx.openapi.permission.MANAGE_WIFI_STA";
    /**
     * Allows an application to access Wi-Fi Ap functionality.
     * Protection level:signature|privileged
     *
     * @since 4
     */
    public static final String ACCESS_WIFI_AP = "ecarx.openapi.permission.ACCESS_WIFI_AP";
    /**
     * Allows an application to manage Wi-Fi Ap functionality.
     * Protection level:signature|privileged
     *
     * @since 4
     */
    public static final String MANAGE_WIFI_AP = "ecarx.openapi.permission.MANAGE_WIFI_AP";

    /**
     * Allows an application to access data network functionality.
     * Protection level:signature|privileged
     *
     * @since 4
     */
    public static final String ACCESS_DATA_NETWORK = "ecarx.openapi.permission.ACCESS_DATA_NETWORK";
    /**
     * Allows an application to manage data network functionality.
     * Protection level:signature|privileged
     *
     * @since 4
     */
    public static final String MANAGE_DATA_NETWORK = "ecarx.openapi.permission.MANAGE_DATA_NETWORK";

    /**
     * Allows an application to access ota functionality.
     * Protection level:signature|privileged
     *
     * @since 5
     */
    public static final String ACCESS_OTA = "ecarx.openapi.permission.ACCESS_OTA";
    /**
     * Allows an application to access ota download functionality.
     * Protection level:signature|privileged
     *
     * @since 5
     */
    public static final String ACCESS_OTA_DOWNLOAD = "ecarx.openapi.permission.ACCESS_OTA_DOWNLOAD";
    /**
     * Allows an application to manage ota download functionality.
     * Protection level:signature|privileged
     *
     * @since 5
     */
    public static final String MANAGE_OTA_DOWNLOAD = "ecarx.openapi.permission.MANAGE_OTA_DOWNLOAD";
    /**
     * Allows an application to access ota install functionality.
     * Protection level:signature|privileged
     *
     * @since 5
     */
    public static final String ACCESS_OTA_INSTALL = "ecarx.openapi.permission.ACCESS_OTA_INSTALL";
    /**
     * Allows an application to manage ota install functionality.
     * Protection level:signature|privileged
     *
     * @since 5
     */
    public static final String MANAGE_OTA_INSTALL = "ecarx.openapi.permission.MANAGE_OTA_INSTALL";

    /**
     * Allows an application to access night mode functionality.
     * Protection level:signature|privileged
     *
     * @since 5
     */
    public static final String ACCESS_NIGHT_MODE = "ecarx.openapi.permission.ACCESS_NIGHT_MODE";
    /**
     * Allows an application to manage night mode functionality.
     * Protection level:signature|privileged
     *
     * @since 5
     */
    public static final String MANAGE_NIGHT_MODE = "ecarx.openapi.permission.MANAGE_NIGHT_MODE";

    /**
     * Allows an application to read from persistent storage.
     * Protection level:signature|privileged
     *
     * @since 5
     */
    public static final String READ_PERSISTENT_STORAGE = "ecarx.openapi.permission.READ_PERSISTENT_STORAGE";
    /**
     * Allows an application to write to persistent storage.
     * Protection level:signature|privileged
     *
     * @since 5
     */
    public static final String WRITE_PERSISTENT_STORAGE = "ecarx.openapi.permission.WRITE_PERSISTENT_STORAGE";

    /**
     * Allows an application to access window type functionality.
     * Protection level:normal
     *
     * @since 5
     */
    public static final String ACCESS_WINDOW_TYPE = "ecarx.openapi.permission.ACCESS_WINDOW_TYPE";

    /**
     * Allows an application to access PKI functionality.
     * Protection level:signature|privileged
     *
     * @since 6
     */
    public static final String ACCESS_PKI = "ecarx.openapi.permission.ACCESS_PKI";

    /**
     * Allows an application to access PKI public certificate.
     * Protection level:normal
     *
     * @since 6
     */
    public static final String ACCESS_PKI_CA = "ecarx.openapi.permission.ACCESS_PKI_CA";

    /**
     * Allows an application to observer window changed.
     * Protection level:signature|privileged
     *
     * @since 6
     */
    public static final String WINDOW_OBSERVER = "ecarx.openapi.permission.WINDOW_OBSERVER";

    /**
     * Allows an application to manage audio zone
     * Protection level:signature|privileged
     *
     * @since 6
     */
    public static final String MANAGE_AUDIO_ZONE = "ecarx.openapi.permission.MANAGE_AUDIO_ZONE";

    /**
     * Allows an application to access biometric public functionality.
     * Protection level:normal
     *
     * @since 6
     */
    public static final String ACCESS_BIOMETRIC = "ecarx.openapi.permission.ACCESS_BIOMETRIC";

    /**
     * Allows an application to manage biometric functionality.
     * Protection level:signature|privileged
     *
     * @since 6
     */
    public static final String MANAGE_BIOMETRIC = "ecarx.openapi.permission.MANAGE_BIOMETRIC";

    /**
     * Allows an application to manage charging config functionality.
     * Protection level:signature|privileged
     *
     * @since 7
     */
    public static final String MANAGE_CHARGING_CONFIG = "ecarx.openapi.permission.MANAGE_CHARGING_CONFIG";
}
