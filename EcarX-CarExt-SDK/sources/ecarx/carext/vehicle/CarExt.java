//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;

import androidx.annotation.NonNull;

import ecarx.carext.base.exception.UnimplementedException;


public class CarExt {

    /**
     * {@link #create(Context, Handler, String moduleName, long, CarExtServiceLifecycleListener)}'s
     * waitTimeoutMs value to use to wait forever inside the call until car service is ready.
     */
    public static final long CAR_WAIT_TIMEOUT_WAIT_FOREVER = -1;

    /**
     * {@link #create(Context, Handler, String moduleName, long, CarExtServiceLifecycleListener)}'s
     * waitTimeoutMs value to use to skip any waiting inside the call.
     */
    public static final long CAR_WAIT_TIMEOUT_DO_NOT_WAIT = 0;

    /**
     * Creates new {@link ICarManager} object with {@link CarExtServiceLifecycleListener}.
     *
     * <p> If car service is ready inside this call and if the caller is running in the main thread,
     * {@link CarExtServiceLifecycleListener#onLifecycleChanged(ICarManager, boolean)} will be called
     * with ready set to be true. Otherwise,
     * {@link CarExtServiceLifecycleListener#onLifecycleChanged(ICarManager, boolean)} will be called
     * from the main thread later. </p>
     *
     * <p>This call can block up to specified waitTimeoutMs to wait for car service to be ready.
     * If car service is not ready within the given time, it will return a Car instance in
     * disconnected state. Blocking main thread forever can lead into getting ANR (Application Not
     * Responding) killing from system and should not be used if the app is supposed to survive
     * across the crash / restart of car service. It can be still useful in case the app cannot do
     * anything without car service being ready. In any waiting, if the thread is getting
     * interrupted, it will return immediately.
     * </p>
     *
     * <font color='red'><p>Note that returned {@link ICarManager} object is not guaranteed to be connected when there is
     * a limited timeout. Regardless of returned car being connected or not, it is recommended to
     * implement all car related initialization inside
     * {@link CarExtServiceLifecycleListener#onLifecycleChanged(ICarManager, boolean)} and avoid the
     * needs to check if returned {@link ICarManager} is connected or not from returned {@link ICarManager}.</p></font>
     *
     * @param context App's Context. This should not be null. If you are passing
     *                {@link ContextWrapper}, make sure that its base Context is non-null as well.
     *                Otherwise it will throw {@link NullPointerException}.
     * @param handler dispatches all Car*Manager events to this Handler. Exception is
     *                {@link CarExtServiceLifecycleListener} which will be always dispatched to main
     *                thread. Passing null leads into dispatching all Car*Manager callbacks to main
     *                thread as well.
     * @param moduleName using module names from {@link CarExtModule}
     * @param waitTimeoutMs Setting this to {@link #CAR_WAIT_TIMEOUT_DO_NOT_WAIT} will guarantee
     *                      that the API does not wait for the car service at all. Setting this to
     *                      to {@link #CAR_WAIT_TIMEOUT_WAIT_FOREVER} will block the call forever
     *                      until the car service is ready. Setting any positive value will be
     *                      interpreted as timeout value.
     */
    public static ICarManager create(Context context, Handler handler, String moduleName, long waitTimeoutMs,
                                     CarExtServiceLifecycleListener statusChangeListener) {
        //TODO Add your code to return subclass instance
        throw new UnimplementedException("If you code went here, that means you have not imported this library correctly");
    }

    public interface CarExtServiceLifecycleListener {
        /**
         * Car service has gone through status change.
         *
         * <p>This is always called in the main thread context.</p>
         *
         * @param funcManger   {@code ICarManager} object that was originally associated with this lister from
         *              {@link #create(Context, Handler,String moduleName, long, CarExtServiceLifecycleListener)}
         *              call.
         * @param ready when {@code true}, car service is ready and all accesses are ok
         *              Otherwise car service has crashed or killed and will be restarted
         */
        void onLifecycleChanged(@NonNull ICarManager funcManger, boolean ready);
    }
}