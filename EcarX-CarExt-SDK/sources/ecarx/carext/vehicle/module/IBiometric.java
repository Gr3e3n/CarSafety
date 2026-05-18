//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 生物识别
 */
public interface IBiometric {
    /**
     * OMS gesture events
     * OMS 手势事件
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link Gesture} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-03 13:59:27
     */
    int OMS_EVENT_GESTURE = 0x00010d00;

    /**
     * Driver fatigue status. Driver state is conveyed, including unknown, normal, distracted, and fatigued.
     * 驾驶员疲劳状态。驾驶员状态透出，包含未知、正常、分心、疲劳。
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DriverFatigueStatus} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-09 18:20:52
     */
    int DMS_DRIVER_FATIGUE_STATUS = 0x00016f00;

    /**
     * Driver facial expressions. Driver facial expressions are conveyed, including unknown, happy, angry, surprised, calm, and sad.
     * 驾驶员面部表情。驾驶员面部表情透出，包含未知，高兴，愤怒，惊讶，平静，伤心
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DriverFacialExpression} property type
     * </ul>
     * mutao.yu@hiecarx.com created for project hongqi at 2024-07-09 18:22:13
     */
    int DMS_DRIVER_FACIAL_EXPRESSION = 0x00017000;

    @IntDef({DriverFacialExpression.UNKNOW,
            DriverFacialExpression.HAPPY,
            DriverFacialExpression.ANGRY,
            DriverFacialExpression.SURPRISE,
            DriverFacialExpression.NEUTRAL,
            DriverFacialExpression.SAD,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DriverFacialExpression {
        /**
         * 未知
         */
        int UNKNOW = 0x01;

        /**
         * 高兴
         */
        int HAPPY = 0x02;

        /**
         * 愤怒
         */
        int ANGRY = 0x03;

        /**
         * 惊讶
         */
        int SURPRISE = 0x04;

        /**
         * 平静
         */
        int NEUTRAL = 0x05;

        /**
         * 伤心
         */
        int SAD = 0x06;
    }

    @IntDef({DriverFatigueStatus.UNKNOW,
            DriverFatigueStatus.NORMAL,
            DriverFatigueStatus.DISTRACTED,
            DriverFatigueStatus.TIRED,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DriverFatigueStatus {
        /**
         * 未知
         */
        int UNKNOW = 0x01;

        /**
         * 正常
         */
        int NORMAL = 0x02;

        /**
         * 分心
         */
        int DISTRACTED = 0x03;

        /**
         * 疲劳
         */
        int TIRED = 0x04;
    }

    @IntDef({OMS_EVENT_GESTURE,
            DMS_DRIVER_FATIGUE_STATUS,
            DMS_DRIVER_FACIAL_EXPRESSION,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({Gesture.UNKNOW,
            Gesture.MUTE,
            Gesture.OK,
            Gesture.CLENCH_FIST,
            Gesture.V,
            Gesture.THUMB_UP,
            Gesture.FINGER_LEFT,
            Gesture.FINGER_RIGHT,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Gesture {
        /**
         * 未知
         */
        int UNKNOW = 0x01;

        /**
         *  手势：嘘
         */
        int MUTE = 0x02;

        /**
         *  手势：OK
         */
        int OK = 0x03;

        /**
         * 手势：握拳
         */
        int CLENCH_FIST = 0x04;

        /**
         *  手势：比 V
         */
        int V = 0x05;

        /**
         * 手势：竖大拇指
         */
        int THUMB_UP = 0x06;

        /**
         *  手势：手指指左
         */
        int FINGER_LEFT = 0x07;

        /**
         *  手势：手指指右
         */
        int FINGER_RIGHT = 0x08;
    }
}
