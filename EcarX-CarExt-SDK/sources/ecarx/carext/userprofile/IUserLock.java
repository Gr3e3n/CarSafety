/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.userprofile;

import ecarx.carext.base.BaseAPI;

/**
 * pin码/pattern密码 接口类
 * 切换账户时使用，用于保存和验证
 */
public interface IUserLock extends BaseAPI {

    /**
     * 保存锁密码
     *
     * @param uid      用户id
     * @param lockType 密码类型：pin/pattern
     * @param password 要保存的经过MD5加密后的密码
     * @return 保存结果  true/false
     */
    boolean saveLockCode(int uid, int lockType, String password);

    /**
     * 删除锁密码
     *
     * @param uid      用户id
     * @param lockType 密码类型：pin/pattern
     * @return 删除结果  true/false
     */
    boolean deleteLockCode(int uid, int lockType);

    /**
     * 验证锁密码
     *
     * @param uid      用户id
     * @param lockType 密码类型：pin/pattern
     * @param password 要验证的经过MD5加密后的密码
     * @return 验证结果  true/false
     */
    boolean verifyLockCode(int uid, int lockType, String password);

    /**
     * 获取当前的锁类型 未设置/pin/pattern (pin/pattern互斥)
     *
     * @param uid 用户id
     * @return 锁类型
     */
    int getCurrentLockType(int uid);

}