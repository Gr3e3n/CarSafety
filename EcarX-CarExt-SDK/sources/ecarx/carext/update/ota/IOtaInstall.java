/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.update.ota;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;
import ecarx.carext.base.BaseCallBack;

/**
 * This class provides the API for managing ota install functionality.
 *
 * @since 5
 */
public interface IOtaInstall {
    /**
     * Regret showing
     *
     * @since 5
     */
    int REGRET_SHOWING = 0;
    /**
     * Regret timeout
     *
     * @since 5
     */
    int REGRET_TIMEOUT = 1;
    /**
     * Regret terminate
     *
     * @since 5
     */
    int REGRET_TERMINATE = 2;

    /**
     * Regret state
     *
     * @since 5
     */
    @IntDef({REGRET_SHOWING, REGRET_TIMEOUT, REGRET_TERMINATE})
    @Retention(RetentionPolicy.SOURCE)
    @interface RegretState {
    }

    /**
     * Set the state of the regret.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean setRegretState(@RegretState int state) {
        throw new UnimplementedException();
    }

    /**
     * Install.
     *
     * @return whether this call was successful. The status will be notified later
     * through the callback {@link InstallListener#onStatusChanged(int, int)}.
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean install() {
        throw new UnimplementedException();
    }

    /**
     * Reinstall.
     *
     * @return whether this call was successful. The status will be notified later
     * through the callback {@link InstallListener#onStatusChanged(int, int)}.
     * @since 13
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean reinstall() {
        throw new UnimplementedException();
    }

    /**
     * Cancel installation.
     *
     * @return whether this call was successful. The status will be notified later
     * through the callback {@link InstallListener#onStatusChanged(int, int)}.
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean cancel() {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain install event.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_OTA_INSTALL)
    default boolean registerInstallListener(@NonNull InstallListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_OTA_INSTALL)
    default void unregisterInstallListener(@NonNull InstallListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Request scheduled time.
     *
     * @return whether this call was successful. The scheduled time will be notified later
     * through the callback {@link InstallScheduleListener#onTimeChanged(long, int)}.
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean requestScheduledTime() {
        throw new UnimplementedException();
    }

    /**
     * 设置预约安装时间
     * Set a time clock for OTA Update
     *
     * @param timestamp when the installation needs to perform in milliseconds since the epoch.
     * @return whether this call was successful. The cancel status will be notified later
     * through the callback {@link InstallScheduleListener#onTimeChanged(long, int)}.
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean setScheduledTime(long timestamp) {
        throw new UnimplementedException();
    }

    /**
     * 设置夜间自动更新开关并设置对应的时间
     * Set a time clock and a automatic upgrade switch for OTA Update
     * 注意：由于 TCAM 只以预约时间作为实际安装时间，因此在使用此接口后需要再次调用{@link #setScheduledTime}
     *
     * @param autoUpgrade automatic upgrade switch.
     * @param timestamp when the installation needs to perform in milliseconds since the epoch.
     * @return whether this call was successful.
     * @since 8
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean setAutoUpgradeStatus(boolean autoUpgrade, long timestamp) {
        throw new UnimplementedException();
    }

    /**
     * 设置夜间自动更新开关
     * Set the automatic upgrade switch for OTA Update
     *
     * @param autoUpgrade automatic upgrade switch.
     * @param callback status callback from backend.
     * @return whether this call was successful.
     * @since 14
     */
    default boolean setAutoUpgradeStatus(boolean autoUpgrade, BaseCallBack<Integer> callback) {
        throw new UnimplementedException();
    }

    /**
     * Request automatic upgrade switch status for OTA.
     *
     * @return true if the switch is on. false if the switch is off.
     * @since 8
     */
    @RequiresPermission(Permissions.ACCESS_OTA_INSTALL)
    default boolean getAutoUpgradeSwitch() {
        throw new UnimplementedException();
    }

    /**
     * Request automatic upgrade time for OTA.
     *
     * @return the automatic upgrade time.
     * @since 8
     */
    @RequiresPermission(Permissions.ACCESS_OTA_INSTALL)
    default long getAutoUpgradeTime() {
        throw new UnimplementedException();
    }

    /**
     * Set a listener to FWK.
     *
     * @param autoUpgradeListener Listener.
     * @return whether this call was successful.
     * @since 8
     */
    @RequiresPermission(Permissions.ACCESS_OTA_INSTALL)
    default boolean registerAutoUpgradeListener(@NonNull AutoUpgradeListener autoUpgradeListener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @since 8
     */
    @RequiresPermission(Permissions.ACCESS_OTA_INSTALL)
    default void unregisterAutoUpgradeListener(@NonNull AutoUpgradeListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Cancel installation schedule.
     *
     * @return whether this call was successful. The cancel status will be notified later
     * through the callback {@link InstallScheduleListener#onCanceled(int)}.
     * @since 5
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean cancelSchedule() {
        throw new UnimplementedException();
    }

    /**
     * Register an observer to obtain schedule event.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_OTA_INSTALL)
    default boolean registerScheduleListener(@NonNull InstallScheduleListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Unregister the observer.
     *
     * @since 5
     */
    @RequiresPermission(Permissions.ACCESS_OTA_INSTALL)
    default void unregisterScheduleListener(@NonNull InstallScheduleListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Mobile phone assisted upgrade
     *
     * @param installationOrder UUID
     * @param ecuAddress        ecuAddress
     * @param downloadType      downloadType
     * @param encryptionType    encryptionType
     * @param encryptionInfo    encryptionInfo
     * @param fileSize          fileSize
     * @param urlFlag           urlFlag
     * @param urlList           urlList
     * @since 9
     *
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default void setFlymeMobileUrlInfo(String installationOrder, String ecuAddress, String downloadType, String encryptionType, String encryptionInfo, int fileSize, String urlFlag, ArrayList<String> urlList) {
        throw new UnimplementedException();
    }

    /**
     * 设置电源状态
     * set the state of power
     *
     * @param state {@link OtaUpdateInProgressState#OTA_UPDATE_INPROGRESS_STATE_IDLE}
     * {@link OtaUpdateInProgressState#OTA_UPDATE_INPROGRESS_STATE_UPGRADE}
     * @return setting power state complete(设置电源状态是否完成)
     * @since 10
     */
    @RequiresPermission(Permissions.MANAGE_OTA_INSTALL)
    default boolean setPowerState(@OtaUpdateInProgressState int state) {
        throw new UnimplementedException();
    }

    /**
     * FOTA power state<br>
     * {@link #OTA_UPDATE_INPROGRESS_STATE_IDLE}<br>
     * {@link #OTA_UPDATE_INPROGRESS_STATE_UPGRADE}<br>
     * {@link #OTA_UPDATE_INPROGRESS_STATE_IMMEDIATE}<br>
     * {@link #OTA_UPDATE_INPROGRESS_STATE_DEFAULT}<br>
     *
     * @since 10
     */
    @IntDef({OtaUpdateInProgressState.OTA_UPDATE_INPROGRESS_STATE_IDLE
            , OtaUpdateInProgressState.OTA_UPDATE_INPROGRESS_STATE_UPGRADE
            , OtaUpdateInProgressState.OTA_UPDATE_INPROGRESS_STATE_IMMEDIATE
            , OtaUpdateInProgressState.OTA_UPDATE_INPROGRESS_STATE_DEFAULT
    })
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @interface OtaUpdateInProgressState {
        /**
         * Power state idle
         *
         * @since 10
         */
        int OTA_UPDATE_INPROGRESS_STATE_IDLE = 0x01;

        /**
         * Power state upgrade
         *
         * @since 10
         */
        int OTA_UPDATE_INPROGRESS_STATE_UPGRADE = 0x02;

        /**
         * Power state immediate
         * 立即熄屏
         *
         * @since 10
         */
        int OTA_UPDATE_INPROGRESS_STATE_IMMEDIATE = 0x03;

        /**
         * Power state default
         * 默认信号值
         *
         * @since 10
         */
        int OTA_UPDATE_INPROGRESS_STATE_DEFAULT = 0x04;
    }



    /**
     * 检查现在是否可以立即开始升级.
     * Check if ota could be started right now.
     *
     * @return 更新条件状态值，取值参考如下：
     * {@link IOtaSessionState#UPDATE_READY_FOR_INSTALLATION}: 可以开始升级状态；
     * {@link IOtaSessionState#UPDATE_FAILED_CONNECTIVITY_NOK}: 网络未连接状态；
     * {@link IOtaSessionState#UPDATE_FAILED_GEARS_NOK}: 挡位未在P档；
     * {@link IOtaSessionState#UPDATE_FAILED_SPEED_NOK}: 速度不满足；
     * {@link IOtaSessionState#UPDATE_FAILED_SCENE_MODE_NOK}: 场景模式不满足；
     * {@link IOtaSessionState#UPDATE_FAILED_REASON_DEFAULT}: 其他原因不满足；
     */
    int couldBeginInstallRightNow();

    /**
     * Reason for inability to install<br>
     * {@link #UPDATE_READY_FOR_INSTALLATION}<br>
     * {@link #UPDATE_FAILED_CONNECTIVITY_NOK}<br>
     * {@link #UPDATE_FAILED_GEARS_NOK}<br>
     * {@link #UPDATE_FAILED_SPEED_NOK}<br>
     * {@link #UPDATE_FAILED_SCENE_MODE_NOK}: 场景模式不满足；
     * {@link #UPDATE_FAILED_REASON_DEFAULT}: 其他原因不满足；
     * @since 10
     */
    @IntDef({IOtaSessionState.UPDATE_READY_FOR_INSTALLATION
            , IOtaSessionState.UPDATE_FAILED_CONNECTIVITY_NOK
            , IOtaSessionState.UPDATE_FAILED_GEARS_NOK
            , IOtaSessionState.UPDATE_FAILED_SPEED_NOK
            , IOtaSessionState.UPDATE_FAILED_SCENE_MODE_NOK
            , IOtaSessionState.UPDATE_FAILED_REASON_DEFAULT
    })
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @interface IOtaSessionState {
        /**
         * 升级失败原因 - 缺省
         * Update failed reason code - default
         */
        int UPDATE_FAILED_REASON_DEFAULT = 0;
        /**
         * 立即更新条件具备
         *
         * @since 10
         */
        int UPDATE_READY_FOR_INSTALLATION = 0x28;

        /**
         * gear_position  档位不满足，导致最近一次更新任务未执行
         *
         * @since 10
         */
        int UPDATE_FAILED_GEARS_NOK = 0x21;

        /**
         * gear_position  连通性不满足，导致最近一次更新任务未执行
         *
         * @since 10
         */
        int UPDATE_FAILED_CONNECTIVITY_NOK = 0x22;

        /**
         * gear_position  车速不满足，导致最近一次更新任务未执行
         *
         * @since 10
         */
        int UPDATE_FAILED_SPEED_NOK = 0x23;

        /**
         * gear_position  情景模式不满足，导致最近一次更新任务未执行
         *
         * @since 10
         */
        int UPDATE_FAILED_SCENE_MODE_NOK = 0x24;
    }
}
