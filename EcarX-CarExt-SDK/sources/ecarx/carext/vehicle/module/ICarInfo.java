//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * CC值以及一些静态信息
 */
public interface ICarInfo {
    /**
     * The last  MCU firmware version
     * MCU 软件版本名称&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     *  <li>{@code String} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-22 19:29:52
     */
    int DEVICE_VER_MCU_FIRMWARE = 0x0000e300;

    /**
     * The hardware version of MCU&lt;br&gt;
     * MCU 硬件版本名称&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     *  <li>{@code String} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-22 19:30:27
     */
    int DEVICE_VER_MCU_HARDWARE = 0x0000e400;

    /**
     * The MCU firmware version int, same with{@link android.os.Build.VERSION#SDK_INT}&lt;br&gt;
     * MCU 软件版号, OTA 升级可能需要检查此版号&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     *  <li>{@code Integer} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-06-22 19:31:21
     */
    int DEVICE_VER_MCU_FIRMWARE_INT = 0x0000e500;

    /**
     * Obtain the vehicle equipment identification code
     * 获取车机设备识别码
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code String} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-01 14:53:14
     */
    int DEVICE_XDSN = 0x00010400;

    /**
     * Number of Geely IHU device, defined by GEELY, 24 characters, first six are vendor code
     * 获取 IHU ID, GEELY 规范的 ID, 24位, 给GEELY车机的编码, 前六位是厂商代码
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code String} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-01 14:54:46
     */
    int DEVICE_IHUID = 0x00010500;

    /**
     * Vehicle type configuration
     * 获取车型配置信息, 具体的车型代号(如区分高、中、低配, 旗舰, 至尊等)
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code String} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-01 14:55:53
     */
    int DEVICE_VEHICLE_TYPE_CONFIG = 0x00010600;

    /**
     * Get project code（PCODE）
     * 获取项目代码（PCODE）,遵循 eCarX pcode 规则, 也就是 PLM 里的项目代号 比如 XE210SH
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code String} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-01 14:57:03
     */
    int DEVICE_PROJECT_CODE = 0x00010700;

    /**
     * Get supplier code
     * 获取产品侧定义的供应商代码
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code String} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project hongqi at 2024-07-01 14:57:49
     */
    int DEVICE_SUPPLIER_CODE = 0x00010800;

    /**
     * Car model color, original ID is SETTING_FUNC_VEHICLE_MODEL_COLOR
     * 车模颜色，原 ID 为 SETTING_FUNC_VEHICLE_MODEL_COLOR
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-16 16:37:59
     */
    int VEHICLE_MODEL_COLOR = 0x00018d00;

    /**
     * Vehicle motion status, original ID is `VehMtnStVehMtnSt`
     * 车辆运动状态，原 ID 为 `VehMtnStVehMtnSt`
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 0 未知，1-3 静止，4-5 前行运动，6-7 向后运动 
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-17 09:31:50
     * @deprecated using {@link android.car.VehiclePropertyIds#PERF_VEHICLE_SPEED} instead.When the vehicle is moving forward, {@code PERF_VEHICLE_SPEED} is positive and negative when the vehicle is moving backward. --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int VEHICLE_MOTION_STATUS = 0x00018f00;

    /**
     * Vehicle type, vehicle power type
     * 车辆类型，车辆动力类型（燃油汽车、电动汽车，插电混动等）
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     * <li>{@link VehicleTypes} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project hongqi at 2024-07-22 13:49:50
     */
    int VEHICLE_TYPES = 0x00019f00;

    @IntDef({DEVICE_VER_MCU_FIRMWARE,
            DEVICE_VER_MCU_HARDWARE,
            DEVICE_VER_MCU_FIRMWARE_INT,
            DEVICE_XDSN,
            DEVICE_IHUID,
            DEVICE_VEHICLE_TYPE_CONFIG,
            DEVICE_PROJECT_CODE,
            DEVICE_SUPPLIER_CODE,
            VEHICLE_MODEL_COLOR,
            VEHICLE_MOTION_STATUS,
            VEHICLE_TYPES,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({VehicleTypes.FUEL,
            VehicleTypes.HEV,
            VehicleTypes.PHEV,
            VehicleTypes.EREV,
            VehicleTypes.FCEV,
            VehicleTypes.FCV,
            VehicleTypes.MHEV,
            VehicleTypes.BEV,
            VehicleTypes.UNKNOWN,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface VehicleTypes {
        /**
         * 油车
         */
        int FUEL = 0x01;

        /**
         * 混合动力汽车
         */
        int HEV = 0x02;

        /**
         * 插电式混合动力汽车
         */
        int PHEV = 0x03;

        /**
         * 增程式混合动力汽车
         */
        int EREV = 0x04;

        /**
         * 燃料电池电动汽车
         */
        int FCEV = 0x05;

        /**
         * 氢燃料电池汽车
         */
        int FCV = 0x06;

        /**
         * 轻度混合动力汽车
         */
        int MHEV = 0x07;

        /**
         * 纯电动汽车
         */
        int BEV = 0x08;

        /**
         * 类型未知
         */
        int UNKNOWN = 0xff;
    }
}
