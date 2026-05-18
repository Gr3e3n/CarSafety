package ecarx.carext.rvdc;

public interface ILogCallback {
    /**
     * 接收LogService的回调
     * logService收集日志完成，回调通知上层app
     * @since 11
     */
    void logReadyDone(String path);

    /**
     * 回调通知app端,日志打包压缩进度
     * @param progress 日志打包压缩进度 范围 0-100
     * @since 11
     * */
    void notifylogCompressProgress(int progress);

    /**
     * bindservice成功后会回调
     * @since 11
     */
    void logServiceReady();

    /**
     * 通知应用log开始执行clean
     * @since 15
     */
    default void notifyLogStartClean() {
    }

    /**
     * 通知应用log状态或者行为变化
     * @since 15
     */
    default void notifyLogReportChanged(String logKey, String logValue) {};
}
