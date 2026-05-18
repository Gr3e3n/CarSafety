//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 硬按键映射，不走原生按键服务的情况
 */
public interface IHardKey {
    /**
     * control button on armrest box, left key
     * 扶手台上的控制按钮, 左键
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HardKeyState} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-11-07 15:01:06
     */
    int DMSM_HARD_KEY_LEFT = 0x00026500;

    /**
     * control button on armrest box, right key
     * 扶手台上的控制按钮, 右键
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HardKeyState} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-11-07 17:20:08
     */
    int DMSM_HARD_KEY_RIGHT = 0x00026600;

    /**
     * control button on armrest box, confirm key
     * 扶手台上的控制按钮, 确认键
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HardKeyState} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-11-07 17:26:00
     */
    int DMSM_HARD_KEY_CONFIRM = 0x00026700;

    @IntDef({HardKeyState.NORMAL,
            HardKeyState.PRESS,
            HardKeyState.RELEASE,
            HardKeyState.CLICK,
            HardKeyState.LONG_CLICK,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface HardKeyState {
        /**
         * 物理按键正常状态
         */
        int NORMAL = 0x01;

        /**
         * 物理按键按下状态
         */
        int PRESS = 0x02;

        /**
         * 物理按键释放状态
         */
        int RELEASE = 0x03;

        /**
         * 物理按键点击状态
         */
        int CLICK = 0x04;

        /**
         * 物理按键长按状态
         */
        int LONG_CLICK = 0x05;
    }

    @IntDef({DMSM_HARD_KEY_LEFT,
            DMSM_HARD_KEY_RIGHT,
            DMSM_HARD_KEY_CONFIRM,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
