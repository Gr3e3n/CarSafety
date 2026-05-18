//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;


import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public interface ICarValue<T> {

    @IntDef({
            STATUS_AVAILABLE,
            STATUS_UNAVAILABLE,
            STATUS_ERROR
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface PropertyStatus {}

    /**
     * CarFuncValue is available.
     */
    int STATUS_AVAILABLE = 0;

    /**
     * CarFuncValue is unavailable.
     */
    int STATUS_UNAVAILABLE = 1;

    /**
     * CarPropertyVale has an error.
     */
    int STATUS_ERROR = 2;

    int getPropertyId();

    int getAreaId();

    @PropertyStatus int getStatus();

    long getTimestamp();

    @NonNull
    T getValue();

    /**
     * @deprecated using {@link ecarx.carext.vehicle.ICarConfig#getSupportValues} instead
     */
    @Deprecated
    T[] getSupportValues();

}
