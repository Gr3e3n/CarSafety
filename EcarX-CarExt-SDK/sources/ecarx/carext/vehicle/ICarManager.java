//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;


import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;

import java.util.List;


public interface ICarManager {

    /** Read ONCHANGE sensors. */
    public static final float SENSOR_RATE_ONCHANGE = 0f;
    /** Read sensors at the rate of  1 hertz */
    public static final float SENSOR_RATE_NORMAL = 1f;
    /** Read sensors at the rate of 5 hertz */
    public static final float SENSOR_RATE_UI = 5f;
    /** Read sensors at the rate of 10 hertz */
    public static final float SENSOR_RATE_FAST = 10f;
    /** Read sensors at the rate of 100 hertz */
    public static final float SENSOR_RATE_FASTEST = 100f;

    String getModuleName();

    ICarConfig<?> getFuncConfig(int propertyId);

    <E> ICarValue<E> getFuncValue(int propertyId, int areaId);

    <E> ICarValue<E> getFuncValue(@NonNull Class<E> clazz, int propertyId, int areaId);

    <E> boolean setFuncValue(@NonNull Class<E> clazz, int propertyId, int areaId, @NonNull E value);

    boolean registerCallback(@NonNull CarEventCallback callback, int propertyId, float updateRateHz);

    boolean registerCallback(@NonNull CarEventCallback callback, int[] propertyIds, float updateRateHz);

    boolean unregisterCallback(@NonNull CarEventCallback callback);

    boolean unregisterCallback(@NonNull CarEventCallback callback, int propertyId);

    boolean isPropertyAvailable(int propId, int area);

    /** @noinspection rawtypes*/
    interface CarEventCallback {
        void onChangeEvent(ICarValue value);

        void onChangeEvent(List<ICarValue> values);

        void onErrorEvent(int propertyId, int areaId, int errorCode, String errorMsg);
    }

    /**
     * Returns areaId contains the seletcted area for the property.
     *
     * @param propId Property ID
     * @param area Area enum such as Enums in {@link VehicleAreaSeat}.
     * @throws IllegalArgumentException if the property is not available in the vehicle for
     * the selected area.
     * @return AreaId contains the selected area for the property.
     */
    default int getAreaId(int propId, int area) {
        return -1;
    }

    /**
     * Disconnect from carext service. This can be called while disconnected. Once disconnect is
     * called, this instance becomes invalid, and
     * {@link CarExt#create(Context, Handler, String, long, CarExt.CarExtServiceLifecycleListener)}
     * will return different instance if it is connected again.
     */
    default void disconnect() {
        return;
    }

}