//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 车外灯
 */
public interface ILamp {
    /**
     * Adaptive headlight switch
     * 自适应大灯开关，原ID：SETTING_FUNC_LAMP_ADAPTIVE_FRONT_LIGHT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 14:34:09
     */
    int ADAPTIVE_FRONT_LIGHT = 0x0001e700;

    /**
     * Bending light switch
     * 弯道照明灯开关，原ID：SETTING_FUNC_LAMP_BENDINGLIGHT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-08-20 14:42:06
     */
    int BENDING_LIGHT = 0x0001ea00;

    /**
     * [V446K private]Voice independent control of turning on or off the reading lights in each area, and reading the status of the reading lights in each area. Parameters: 0 indicates off, 1 indicates on
     * [V446K私有]语音独立控制各区域阅读灯开启或者关闭，并且读取各区域阅读灯状态，参数：0表示关闭，1表示开启
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 0 indicates off, 1 indicates on 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-07-21 09:37:28
     */
    int READING_LIGHTS_SWITCH_VOICE = 0x00033500;

    @IntDef({ADAPTIVE_FRONT_LIGHT,
            BENDING_LIGHT,
            READING_LIGHTS_SWITCH_VOICE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
