//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Around View Monitor \n 360环视系统
 */
public interface IAvm {
    /**
     * AVM screen display or hide status, supports set/get/callback
     * AVM画面显示或隐藏状态，支持set/get/callback
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DisplayMode} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-09-12 13:53:59
     */
    int AVM_DISPLAY_STATUS = 0x00023e00;

    @IntDef({AVM_DISPLAY_STATUS,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({DisplayMode.FULL_SCREEN,
            DisplayMode.MINI_WINDOW,
            DisplayMode.DISPLAY_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DisplayMode {
        /**
         * 全屏显示
         */
        int FULL_SCREEN = 0x01;

        /**
         * 小窗显示
         */
        int MINI_WINDOW = 0x02;

        /**
         * 退出
         */
        int DISPLAY_OFF = 0xff;
    }
}
