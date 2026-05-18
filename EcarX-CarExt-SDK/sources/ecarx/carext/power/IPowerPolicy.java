package ecarx.carext.power;


import ecarx.carext.base.BaseAPI;

/**
 * Power Policy function entry.
 */
public interface IPowerPolicy extends BaseAPI {

    /**
     * report the state of SOC reboot permits.
     *
     * @param socRebootPermits the state of SOC reboot permits, see {@link SocRebootPermits}
     * @link <a href="https://ecarxgroup.feishu.cn/record/Gq6CrswvFevl4zcD7sAczlrhnVh">需求來源</a>
     * @return true if report succeed to server(not means server execute success), false otherwise.
     */
    boolean reportSocRebootPermits(@SocRebootPermits int socRebootPermits);

    @interface SocRebootPermits {
        int IGNORE = 0x01;//忽略
        int DISABLE_RESET = 0x02;//禁止重启
        int ENABLE_RESET = 0x03;//允许重启
    }
}
