//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 驾驶模式
 */
public interface IDriveMode {
    /**
     * to change the Drive Mode selection
     * 驾驶模式切换,原ID为DM_FUNC_DRIVE_MODE_SELECT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DriveModeSelect} property type
     * </ul>
     * wei.jin@ecarxgroup.com created for project hongqi at 2024-06-19 10:29:26
     */
    int DRIVE_MODE_SELECT = 0x00009d00;

    @IntDef({DriveModeSelect.DRIVE_MODE_SELECTION_ECO,
            DriveModeSelect.DRIVE_MODE_SELECTION_NORMAL,
            DriveModeSelect.DRIVE_MODE_SELECTION_DYNAMIC,
            DriveModeSelect.DRIVE_MODE_SELECTION_CUSTOM,
            DriveModeSelect.DRIVE_MODE_SELECTION_PURE,
            DriveModeSelect.DRIVE_MODE_SELECTION_HYBRID,
            DriveModeSelect.DRIVE_MODE_SELECTION_OFFROAD,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DriveModeSelect {
        /**
         * 经济模式
         */
        int DRIVE_MODE_SELECTION_ECO = 0x01;

        /**
         * 正常模式
         */
        int DRIVE_MODE_SELECTION_NORMAL = 0x02;

        /**
         * 运动模式
         */
        int DRIVE_MODE_SELECTION_DYNAMIC = 0x03;

        /**
         * 自定义驾驶模式
         */
        int DRIVE_MODE_SELECTION_CUSTOM = 0x04;

        /**
         * 纯电动模式
         */
        int DRIVE_MODE_SELECTION_PURE = 0x06;

        /**
         * 混动模式
         */
        int DRIVE_MODE_SELECTION_HYBRID = 0x07;

        /**
         * 越野模式
         */
        int DRIVE_MODE_SELECTION_OFFROAD = 0x08;
    }

    @IntDef({DRIVE_MODE_SELECT,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
