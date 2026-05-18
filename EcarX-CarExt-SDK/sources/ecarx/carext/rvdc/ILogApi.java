/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.rvdc;

import androidx.annotation.NonNull;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.exception.UnimplementedException;

/**
 *
 *
 * @since 11
 */
public interface ILogApi extends BaseAPI {

    /**
     * 注册log回调监听<br>
     * register a Log callback.
     *
     * @param listener Log操作回调
     * @since 11
     */
    default void registerCallback(@NonNull ILogCallback listener) {
        throw new UnimplementedException();
    }

    /**
     * 取消注册LogService回调监听<br>
     * unregister a logService callback.
     *
     * @param listener logService操作回调
     * @since 11
     */
    default void unregisterCallback(@NonNull ILogCallback listener) {
        throw new UnimplementedException();
    }


    /**
     * 通知LogService开始收集日志
     * @since 11
     */
    public abstract void startCollectLog();

    /**
     * 通知LogService开始收集日志
     * @since 15
     */
    public abstract void startCollectLog(String startTime, String endTime);
}
