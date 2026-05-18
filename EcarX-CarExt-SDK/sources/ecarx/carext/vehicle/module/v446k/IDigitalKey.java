//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 数字钥匙
 */
public interface IDigitalKey {
    /**
     * Create the digital key for vehicle&lt;br&gt;Original AdaptAPI is KEY_FUNC_CREATE_DIGITAL_KEY
     * 创建数字钥匙&lt;br&gt;原AdaptAPI为KEY_FUNC_CREATE_DIGITAL_KEY
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DigitalKeyType} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-25 11:13:01
     */
    int CREATE_DIGITAL_KEY = 0x00025700;

    /**
     * delete digitalKey by slotID&lt;br&gt;Original AdaptAPI is KEY_FUNC_DEL_DIGITAL_KEY_ITEM
     * 根据slotID删除数字钥匙&lt;br&gt;原AdaptAPI为KEY_FUNC_DEL_DIGITAL_KEY_ITEM
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type slotID 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-25 11:17:05
     */
    int DELETE_DIGITAL_KEY = 0x00025800;

    /**
     * Digital key delete status&lt;br&gt;Original AdaptAPI is KEY_FUNC_DIGITAL_KEY_TERMINATION
     * 数字钥匙删除状态&lt;br&gt; 原AdaptAPI为KEY_FUNC_DIGITAL_KEY_TERMINATION
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DigitalKeyTerminationStatus} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-25 11:35:40
     */
    int DIGITAL_KEY_TERMINATION = 0x00025900;

    /**
     * delete all digitalKey&lt;br&gt;Original AdaptAPI is KEY_FUNC_DEL_ALL_DIGITAL_KEYS
     * 删除所有数字钥匙&lt;br&gt;原AdaptAPI为KEY_FUNC_DEL_ALL_DIGITAL_KEYS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-25 11:42:40
     */
    int DELETE_ALL_DIGITAL_KEYS = 0x00025a00;

    /**
     * Get the DigitalKeys list&lt;br&gt;Original AdaptApi is KEY_FUNC_GET_DIGITAL_KEYS
     * 获取数字钥匙列表&lt;br&gt;原AdaptAPI为KEY_FUNC_GET_DIGITAL_KEYS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-25 15:20:55
     */
    int GET_DIGITAL_KEYS = 0x00025b00;

    /**
     * Digital key ready statusOriginal AdaptApi is KEY_FUNC_DIGITAL_KEY_REQ_STS
     * 数字钥匙准备状态&lt;br&gt;原AdaptApi为KEY_FUNC_DIGITAL_KEY_REQ_STS
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DigitalKeyReadyStatus} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-25 15:35:47
     */
    int DIGITAL_KEY_READY_STATUS = 0x00025c00;

    /**
     * Digital key paring status&lt;br&gt;Original AdaptApi is KEY_FUNC_DIGITAL_KEY_PAIRING_FAILED
     * 数字钥匙配对状态&lt;br&gt;原AdaptApi为KEY_FUNC_DIGITAL_KEY_PAIRING_FAILED
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link PairingStatus} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-25 17:06:32
     */
    int DIGITAL_KEY_PAIRING_STATUS = 0x00025d00;

    /**
     * Callback for deleting digital key on mobile phone
     * 手机端删除数字钥匙回调&lt;br&gt;原AdaptApi为KEY_FUNC_DIGITAL_KEY_TRACKING_RESULT
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type slotID 数据钥匙id 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-25 17:19:04
     */
    int DIGITAL_KEY_TRACKING_RESULT = 0x00025e00;

    /**
     * Digital key unpairing status&lt;br&gt;Original AdaptApi is KEY_FUNC_DIGITAL_KEY_UNPAIR
     * 数字钥匙解除匹配状态&lt;br&gt;原AdaptApi为KEY_FUNC_DIGITAL_KEY_UNPAIR
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DigitalKeyUnpairStatus} property type
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-10-25 17:30:39
     */
    int DIGITAL_KEY_UNPAIR = 0x00025f00;

    @IntDef({PairingStatus.SUCCESS,
            PairingStatus.TIMEOUT,
            PairingStatus.BCNM_LACK_VIN,
            PairingStatus.VERIFIER_UPDATED,
            PairingStatus.UTC_TI_LOST,
            PairingStatus.CERTIFICATE_INVALID,
            PairingStatus.ERROR_MSG,
            PairingStatus.SECURE_CREATFAL,
            PairingStatus.WRITE_DATAFAL,
            PairingStatus.GET_DATAFAL,
            PairingStatus.PROTOCOL_MISMATCH,
            PairingStatus.AUTH_FAL,
            PairingStatus.PHONE_NOT_FOUND,
            PairingStatus.OWNER_KEY_NOT_FOUND,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface PairingStatus {
        /**
         * 数字钥匙配对成功
         */
        int SUCCESS = 0x01;

        /**
         * 超时
         */
        int TIMEOUT = 0x02;

        /**
         * BNCM 没有拿到vin码等数据
         */
        int BCNM_LACK_VIN = 0x08;

        /**
         * VERIFIER UPDATED
         */
        int VERIFIER_UPDATED = 0x09;

        /**
         * UTC时间缺失
         */
        int UTC_TI_LOST = 0x0a;

        /**
         * 证书无效？
         */
        int CERTIFICATE_INVALID = 0x0b;

        /**
         * 错误消息
         */
        int ERROR_MSG = 0x0c;

        /**
         * SECURE CREATFAL
         */
        int SECURE_CREATFAL = 0x0d;

        /**
         * WRITE DATAFAL
         */
        int WRITE_DATAFAL = 0x0e;

        /**
         * GET DATA FAIL
         */
        int GET_DATAFAL = 0x0f;

        /**
         * PROTOCOL MISMATCH 
         */
        int PROTOCOL_MISMATCH = 0x10;

        /**
         * AUTH_FAL
         */
        int AUTH_FAL = 0x11;

        /**
         * 未检测到手机
         */
        int PHONE_NOT_FOUND = 0x13;

        /**
         * 没有车主钥匙在车上
         */
        int OWNER_KEY_NOT_FOUND = 0x14;
    }

    @IntDef({DigitalKeyTerminationStatus.SUCCESS,
            DigitalKeyTerminationStatus.REJECT_USGMOD,
            DigitalKeyTerminationStatus.REJECT_CARMOD,
            DigitalKeyTerminationStatus.REJECT_KEY_NOT_EXIST,
            DigitalKeyTerminationStatus.REJECT_NO_KEY,
            DigitalKeyTerminationStatus.REJECT_BNCM,
            DigitalKeyTerminationStatus.IDLE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DigitalKeyTerminationStatus {
        /**
         * 0x01:success
         */
        int SUCCESS = 0x01;

        /**
         * 0x02:reject for UsgMod
         */
        int REJECT_USGMOD = 0x02;

        /**
         * 0x03:reject for CarMod
         */
        int REJECT_CARMOD = 0x03;

        /**
         * 0x04:reject for the key does not exist
         */
        int REJECT_KEY_NOT_EXIST = 0x04;

        /**
         * 0x05:reject for no valid owner key detected inside vehicle
         */
        int REJECT_NO_KEY = 0x05;

        /**
         * 0x06:reject for BNCM is dealing with other request
         */
        int REJECT_BNCM = 0x06;

        /**
         * 0xff:idle
         */
        int IDLE = 0xff;
    }

    @IntDef({CREATE_DIGITAL_KEY,
            DELETE_DIGITAL_KEY,
            DIGITAL_KEY_TERMINATION,
            DELETE_ALL_DIGITAL_KEYS,
            GET_DIGITAL_KEYS,
            DIGITAL_KEY_READY_STATUS,
            DIGITAL_KEY_PAIRING_STATUS,
            DIGITAL_KEY_TRACKING_RESULT,
            DIGITAL_KEY_UNPAIR,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }

    @IntDef({DigitalKeyType.DIGITAL_KEY_TYPE_OWNER,
            DigitalKeyType.DIGITAL_KEY_TYPE_SHARED,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DigitalKeyType {
        /**
         * Digital key type owner key
         */
        int DIGITAL_KEY_TYPE_OWNER = 0x01;

        /**
         * Digital key type shared key
         */
        int DIGITAL_KEY_TYPE_SHARED = 0x02;
    }

    @IntDef({DigitalKeyUnpairStatus.IDLE,
            DigitalKeyUnpairStatus.SUCCESS,
            DigitalKeyUnpairStatus.REJECT_USGMOD,
            DigitalKeyUnpairStatus.REJECT_CARMOD,
            DigitalKeyUnpairStatus.REJECT_NO_MOBILE,
            DigitalKeyUnpairStatus.REJECT_NO_KEY,
            DigitalKeyUnpairStatus.REJECT_BNCM,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DigitalKeyUnpairStatus {
        /**
         * idle
         */
        int IDLE = 0x01;

        /**
         * success
         */
        int SUCCESS = 0x02;

        /**
         * reject for UsgMod
         */
        int REJECT_USGMOD = 0x03;

        /**
         * reject for CarMod
         */
        int REJECT_CARMOD = 0x04;

        /**
         * reject for no mobile phone key exist
         */
        int REJECT_NO_MOBILE = 0x05;

        /**
         * reject for no valid owner key detected inside vehicle
         */
        int REJECT_NO_KEY = 0x06;

        /**
         * reject for BNCM is dealing with other request
         */
        int REJECT_BNCM = 0x07;
    }

    @IntDef({DigitalKeyReadyStatus.IDLE,
            DigitalKeyReadyStatus.CREATING,
            DigitalKeyReadyStatus.RESETTING,
            DigitalKeyReadyStatus.DELETING,
            DigitalKeyReadyStatus.SEARCHING_CARD,
            DigitalKeyReadyStatus.SEARCHING_PHONE,
            DigitalKeyReadyStatus.PREPARING_DATA,
            DigitalKeyReadyStatus.CREATE_SUCCESS,
            DigitalKeyReadyStatus.SMS_SENT,
            DigitalKeyReadyStatus.PAIRING_CODE_VERIFIED,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DigitalKeyReadyStatus {
        /**
         * idle 状态
         */
        int IDLE = 0x01;

        /**
         * 数字钥匙创建中...
         */
        int CREATING = 0x02;

        /**
         * 数字钥匙重置中...
         */
        int RESETTING = 0x03;

        /**
         * 数字钥匙删除中...
         */
        int DELETING = 0x04;

        /**
         * 钥匙或者NFC卡片搜索中，请确认钥匙或NFC卡片在车内
         */
        int SEARCHING_CARD = 0x05;

        /**
         * 智能手机搜索中，请确认手机已放置在WPC区域
         */
        int SEARCHING_PHONE = 0x06;

        /**
         * 数据准备中
         */
        int PREPARING_DATA = 0x07;

        /**
         * 创建成功
         */
        int CREATE_SUCCESS = 0x08;

        /**
         * 短信已下发，此状态在 {@link #PAIRING_CODE_VERIFIED}之前发生
         */
        int SMS_SENT = 0x09;

        /**
         * 配对码校验成功，此状态在 {@link #CREATE_SUCCESS}之前发生
         */
        int PAIRING_CODE_VERIFIED = 0x0a;
    }
}
