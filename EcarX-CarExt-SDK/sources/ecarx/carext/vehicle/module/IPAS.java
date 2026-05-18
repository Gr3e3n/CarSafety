//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * function definitions for PAS, PAC, SAP, RTCA  | PAS : Parking Assist System | PAC : Park Assist Camera<br>  | SAP : Semi Automatic Parking | RCTA : Rear Collision Traffic Alert
 */
public interface IPAS {
    /**
     * Allows the user to adjust the volume level of the Rear Collision Traffic Alert(RCTA) warning sound
     * RCTA(倒车预警系统)警报音等级设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link RCTAWarningVolumeMode} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 16:04:53
     */
    int RCTA_WARNING_VOLUME = 0x0000f300;

    /**
     * Parking emergency brake switch
     * 泊车紧急制动开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-25 16:06:53
     */
    int PEB_SWITCH = 0x0000f400;

    /**
     * HMI displays vehicle status, original ID is PAS_FUNC_HPA_CAR_CONFIG_HMI_CAR_STATE
     * HMI显示车辆状态，原 ID 为PAS_FUNC_HPA_CAR_CONFIG_HMI_CAR_STATE
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 状态数组，每个下标代表一个类别的状态，不同项目下标代表含义不尽相同，使用前需自行确认。0 表示无效。 
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-17 15:27:35
     */
    int HPA_CAR_CONFIG_HMI_CAR_STATE = 0x00019200;

    /**
     * DLC Vehicle Data Collect Function Switch，original ID  PAS_FUNC_DCL_SWITCH
     * DLC车辆数据采集开关，原 ID 为 PAS_FUNC_DCL_SWITCH（属于ADAS功能需求）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 14:11:14
     */
    int DLC_SWITCH = 0x0001e300;

    /**
     * DCL  Data Collect Authorized Time，original ID PAS_FUNC_DCLAT_AUTHORIZED_TIME
     * DLC车辆数据采集授权时间，原 ID 为 PAS_FUNC_DCLAT_AUTHORIZED_TIME
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Long} property type 具体值由业务约定 
     * </ul>
     * mutao.yu@hiecarx.com created for project v446k at 2024-08-20 14:17:41
     */
    int DLC_AUTHORIZED_TIME = 0x0001e400;

    @IntDef({RCTA_WARNING_VOLUME,
            PEB_SWITCH,
            HPA_CAR_CONFIG_HMI_CAR_STATE,
            DLC_SWITCH,
            DLC_AUTHORIZED_TIME,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({RCTAWarningVolumeMode.LOW,
            RCTAWarningVolumeMode.MID,
            RCTAWarningVolumeMode.HIGH,
            RCTAWarningVolumeMode.OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface RCTAWarningVolumeMode {
        /**
         * Rear Collision Traffic Alert(RCTA) warning volume: low
         */
        int LOW = 0x01;

        /**
         * Rear Collision Traffic Alert(RCTA) warning volume: middle
         */
        int MID = 0x02;

        /**
         * Rear Collision Traffic Alert(RCTA) warning volume: high
         */
        int HIGH = 0x03;

        /**
         * Rear Collision Traffic Alert(RCTA) warning volume off
         */
        int OFF = 0xff;
    }
}
