package ecarx.carext.vehicle;

import android.content.Context;

import androidx.annotation.NonNull;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * 配置字接口
 *
 *     以红旗获取OMS配置字为例
 *     配置字表格地址: https://ecarxgroup.feishu.cn/sheets/EekNs9JTHhY6u9tK5VecEoEQngd?sheet=1IOIaV
 *     <table>
 *         <tr>
 *             <th>API</th>
 *             <th>Byte</th>
 *             <th>StartBit</th>
 *             <th>BitLength</th>
 *             <th>Comment</th>
 *         </tr>
 *         <tr>
 *             <td>OMS</td>
 *             <td>1</td>
 *             <td>7</td>
 *             <td>1</td>
 *             <td>成员状态监测(OMS)硬件配置</td>
 *         </tr>
 *     </table>
 *   <pre>
 *     int oms = CarConfigWord.get(context).getConfigWord("OMS");
 *     if (oms != CarConfigWord.INVALID_CONFIG_WORD) {
 *         // 配置字存在
 *     }
 *     if (oms == 1) {
 *         // OMS配置字值为1,代表有，根据表格中的Comment解释
 *     } else if (oms == 0) {
 *         // OMS配置字值为0,代表冇，根据表格中的Comment解释
 *     }
 *    </pre>
 */
public interface CarConfigWord extends BaseAPI {

    /**
     * A factory method that creates {@link CarConfigWord} instance.
     *
     * @param context App's Context.
     * @return CarConfigWord instance.
     * @throws UnimplementedException If this function is not implemented.
     */
    @NonNull
    public static CarConfigWord get(@NonNull Context context) {
        throw new UnimplementedException();
    }

    /**
     * 无效的配置字
     */
    int INVALID_CONFIG_WORD = Integer.MIN_VALUE;

    /**
     * 获取配置字
     * @param configWordName 配置字名称请参考<a href="https://ecarxgroup.feishu.cn/sheets/EekNs9JTHhY6u9tK5VecEoEQngd?sheet=1IOIaV">红旗配置字</a>
     * @return 配置字值, 如果配置字不存在则返回 {@link #INVALID_CONFIG_WORD}
     */
    int getConfigWord(String configWordName);
}
