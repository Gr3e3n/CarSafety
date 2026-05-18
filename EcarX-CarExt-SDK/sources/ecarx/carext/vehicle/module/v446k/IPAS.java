//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * function definitions for PAS, PAC, SAP, RTCA  | PAS : Parking Assist System | PAC : Park Assist Camera<br>  | SAP : Semi Automatic Parking | RCTA : Rear Collision Traffic Alert
 */
public interface IPAS {
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

    /**
     * the Park Assist System is muted or not&lt;br&gt;
     * 泊车辅助系统(PAS)静音设置&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 18:59:10
     */
    int PAS_MUTE = 0x0002af00;

    /**
     * distance detected by front inner left radar
     * 前内左雷达距离 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 19:38:57
     */
    int RADAR_FRONT_INNER_LEFT = 0x0002b100;

    /**
     * distance detected by front inner right radar
     * 前内右雷达探测距离  
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 19:40:29
     */
    int RADAR_FRONT_INNER_RIGHT = 0x0002b200;

    /**
     * distance detected by front out left radar
     * 前外左雷达距离 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 19:52:41
     */
    int RADAR_FRONT_OUT_LEFT = 0x0002b300;

    /**
     * distance detected by front out right radar
     * 前外右雷达距离 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 19:53:22
     */
    int RADAR_FRONT_OUT_RIGHT = 0x0002b400;

    /**
     * distance detected by radar on front left side
     * 左侧前雷达距离 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 19:55:09
     */
    int RADAR_FRONT_LEFT_SIDE = 0x0002b500;

    /**
     * distance detected by radar on front right side
     * 右侧前雷达距离 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 19:58:31
     */
    int RADAR_FRONT_RIGHT_SIDE = 0x0002b600;

    /**
     * distance detected by radar on rear left side
     * 左侧后雷达距离 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 19:59:15
     */
    int RADAR_REAR_LEFT_SIDE = 0x0002b700;

    /**
     * distance detected by radar on rear right side
     * 右侧后雷达距离 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 19:59:51
     */
    int RADAR_REAR_RIGHT_SIDE = 0x0002b800;

    /**
     * distance detected by radar on rear out left
     * 后外左雷达距离 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 20:01:13
     */
    int RADAR_REAR_OUT_LEFT = 0x0002b900;

    /**
     * distance detected by radar on rear out right
     * 后外右雷达距离 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 20:01:55
     */
    int RADAR_REAR_OUT_RIGHT = 0x0002ba00;

    /**
     * distance detected by radar on rear inner left
     * 后内左雷达距离 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 20:02:38
     */
    int RADAR_REAR_INNER_LEFT = 0x0002bb00;

    /**
     * distance detected by radar on rear inner right
     * 后内右雷达距离   
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_CONTINUOUS}
     *  <li>{@code Integer} property type 单位厘米 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 20:04:34
     */
    int RADAR_REAR_INNER_RIGHT = 0x0002bc00;

    /**
     * The maximum detection distance supported by the on-board radar&lt;br&gt;
     * 雷达支持最大探测距离&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     *  <li>{@code Integer} property type unit cm 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 20:08:59
     */
    int RADAR_MAX_DISTANCE = 0x0002bd00;

    /**
     * The minimum detection distance supported by the on-board radar&lt;br&gt;
     * 雷达支持最小探测距离&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     *  <li>{@code Integer} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 20:10:10
     */
    int RADAR_MIN_DISTANCE = 0x0002be00;

    @IntDef({DLC_SWITCH,
            DLC_AUTHORIZED_TIME,
            PAS_MUTE,
            RADAR_FRONT_INNER_LEFT,
            RADAR_FRONT_INNER_RIGHT,
            RADAR_FRONT_OUT_LEFT,
            RADAR_FRONT_OUT_RIGHT,
            RADAR_FRONT_LEFT_SIDE,
            RADAR_FRONT_RIGHT_SIDE,
            RADAR_REAR_LEFT_SIDE,
            RADAR_REAR_RIGHT_SIDE,
            RADAR_REAR_OUT_LEFT,
            RADAR_REAR_OUT_RIGHT,
            RADAR_REAR_INNER_LEFT,
            RADAR_REAR_INNER_RIGHT,
            RADAR_MAX_DISTANCE,
            RADAR_MIN_DISTANCE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
