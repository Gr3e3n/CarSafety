//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 屏幕 显示 等
 */
public interface IDisplay {
    /**
     * screen switch&lt;br/&gt;
     * 屏幕开关&lt;br/&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type DIM(仪表屏) use with VehicleAreaSeat#SEAT_ROW_1_LEFT CSD(中控屏屏) use with VehicleAreaSeat#SEAT_ROW_1_CENTER PSD(乘客屏) use with VehicleAreaSeat#SEAT_ROW_1_RIGHT   RSD(后排屏) use with VehicleAreaSeat#SEAT_ROW_2_LEFT|VehicleAreaSeat#SEAT_ROW_2_RIGHT RFDM(吸顶屏) use with VehicleAreaSeat#SEAT_ROW_2_CENTER 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-01-03 19:03:05
     */
    int SCREEN_ON = 0x0002cb00;

    @IntDef({SCREEN_ON,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
