package ecarx.carext.userprofile;

import android.os.Bundle;


import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * <pre>
 *     author  : KING.SUN
 *     e-mail  : xiaogangsun@ecarx.com.cn
 *     date    : 18-12-25
 *     desc    : The Profile ID must between 1 & the count(included)
 *     version : 1.0
 * </pre>
 * @Deprecated 仅用作V216/P417项目兼容，后续使用VehiclePropertyId扩展的方式进行定义
 */
@Deprecated
public interface IUserProfile extends BaseAPI {
    /**
     * add user profile {@link #addUserProfile()}
     */
    int ACTION_TYPE_PROFILE_ADD = 0x01;
    /**
     * remove user profile {@link #removeUserProfile(int)}
     */
    int ACTION_TYPE_PROFILE_REMOVE = 0x02;
    /**
     * login user profile {@link #loginUserProfile(int)}
     */
    int ACTION_TYPE_PROFILE_LOGIN = 0x03;
    /**
     * logout user profile {@link #logoutUserProfile(int)}
     */
    int ACTION_TYPE_PROFILE_LOGOUT = 0x04;
    /**
     * switch user profile {@link #switchUserProfile(int, int)}
     */
    int ACTION_TYPE_PROFILE_SWITCH = 0x05;
    /**
     * apply user profile data {@link #applyUserProfileData(int, IProfile)}
     */
    int ACTION_TYPE_PROFILE_APPLY = 0x06;
    /**
     * reset user profile data to default {@link #resetUserProfileDataDefault(int)}
     */
    int ACTION_TYPE_PROFILE_RESET = 0x07;
    /**
     * Bind User ID to profile
     */
    int ACTION_TYPE_PROFILE_BIND = 0x11;
    /**
     * Unbind User ID to profile
     */
    int ACTION_TYPE_PROFILE_UNBIND = 0x12;
    /**
     * Reset all the bind User ID to profile
     */
    int ACTION_TYPE_PROFILE_UNBIND_RESET = 0x13;

    /**
     * Bind User ID to faceID
     */
    int ACTION_TYPE_FACE_BIND = 0x21;
    /**
     * Unbind User ID to faceID
     */
    int ACTION_TYPE_FACE_UNBIND = 0x22;
    /**
     * face recognize
     */
    int ACTION_TYPE_FACE_RECOGNIZE = 0x23;

    /**
     * Profile action type<br>
     * {@link #ACTION_TYPE_PROFILE_ADD}<br>
     * {@link #ACTION_TYPE_PROFILE_REMOVE}<br>
     * {@link #ACTION_TYPE_PROFILE_LOGIN}<br>
     * {@link #ACTION_TYPE_PROFILE_LOGOUT}<br>
     * {@link #ACTION_TYPE_PROFILE_SWITCH}<br>
     * {@link #ACTION_TYPE_PROFILE_APPLY}<br>
     * {@link #ACTION_TYPE_PROFILE_RESET}<br>
     * {@link #ACTION_TYPE_PROFILE_BIND}<br>
     * {@link #ACTION_TYPE_PROFILE_UNBIND}<br>
     * {@link #ACTION_TYPE_PROFILE_UNBIND_RESET}<br>
     * {@link #ACTION_TYPE_FACE_BIND}<br>
     * {@link #ACTION_TYPE_FACE_UNBIND}<br>
     * {@link #ACTION_TYPE_FACE_RECOGNIZE}<br>
     */
    @IntDef({ACTION_TYPE_PROFILE_ADD
            , ACTION_TYPE_PROFILE_REMOVE
            , ACTION_TYPE_PROFILE_LOGIN
            , ACTION_TYPE_PROFILE_LOGOUT
            , ACTION_TYPE_PROFILE_SWITCH
            , ACTION_TYPE_PROFILE_APPLY
            , ACTION_TYPE_PROFILE_RESET
            , ACTION_TYPE_PROFILE_BIND
            , ACTION_TYPE_PROFILE_UNBIND
            , ACTION_TYPE_PROFILE_UNBIND_RESET
            , ACTION_TYPE_FACE_BIND
            , ACTION_TYPE_FACE_UNBIND
            , ACTION_TYPE_FACE_RECOGNIZE
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface ActionType {
    }


    /**
     * status: unknown
     */
    int STATUS_UNKNOWN = 0x00;
    /**
     * status: prepare
     */
    int STATUS_PREPARE = 0x01;
    /**
     * status: in progress
     */
    int STATUS_PROGRESS = 0x02;
    /**
     * status: succeed
     */
    int STATUS_SUCCEED = 0x03;
    /**
     * status: failed
     */
    int STATUS_FAILED = 0x04;
    /**
     * status: repeat
     */
    int STATUS_REPEAT = 0x05;
    /**
     * status: timeout
     */
    int STATUS_TIMEOUT = 0x06;
    /**
     * status: GID exist
     */
    int STATUS_GID_EXIST = 0x07;
    /**
     * status: face near
     */
    int STATUS_FACE_NEAR = 0x08;
    /**
     * status: face far
     */
    int STATUS_FACE_FAR = 0x09;
    /**
     * status: error DMM state
     */
    int STATUS_ERROR_DMM_STATE = 0x0A;
    /**
     * status: face not match
     */
    int STATUS_FACE_NOT_MATCH = 0x0B;

    /**
     * status<br>
     * {@link #STATUS_UNKNOWN}<br>
     * {@link #STATUS_PREPARE}<br>
     * {@link #STATUS_PROGRESS}<br>
     * {@link #STATUS_SUCCEED}<br>
     * {@link #STATUS_FAILED}<br>
     * {@link #STATUS_REPEAT}<br>
     */
    @IntDef({STATUS_UNKNOWN
            , STATUS_PREPARE
            , STATUS_PROGRESS
            , STATUS_SUCCEED
            , STATUS_FAILED
            , STATUS_REPEAT
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface ActionStatus {
    }

    /**
     * error code: unknown
     */
    int ERROR_CODE_UNKNOWN = 0x00;
    /**
     * error code: time out
     */
    int ERROR_CODE_TIMEOUT = 0x01;
    /**
     * error code<br>
     * {@link #ERROR_CODE_UNKNOWN}<br>
     */
    @IntDef({ERROR_CODE_UNKNOWN
            , ERROR_CODE_TIMEOUT

    })
    @Retention(RetentionPolicy.SOURCE)
    @interface ErrorCode {
    }

    /**
     * Profile login type: Unknown
     */
    int LOGIN_TYPE_UNKNOWN = 0x00;
    /**
     * Profile login type: User Account(账户)
     */
    int LOGIN_TYPE_USER_ACC = 0x01;
    /**
     * Profile login type: Fingerprint(指纹)
     */
    int LOGIN_TYPE_FINGERPRINT = 0x02;
    /**
     * Profile login type: Face ID(人脸)
     */
    int LOGIN_TYPE_FACEID = 0x03;
    /**
     * Profile login type: Voice(声纹)
     */
    int LOGIN_TYPE_VOICE = 0x04;
    /**
     * Profile login type: Car key(车钥匙)
     */
    int LOGIN_TYPE_CARKEY = 0x05;
    /**
     * Profile login type: Digital key(数字钥匙)
     */
    int LOGIN_TYPE_DIGITALKEY = 0x06;
    /**
     * Profile login type<br>
     * {@link #LOGIN_TYPE_UNKNOWN}<br>
     * {@link #LOGIN_TYPE_USER_ACC}<br>
     * {@link #LOGIN_TYPE_FINGERPRINT}<br>
     * {@link #LOGIN_TYPE_FACEID}<br>
     * {@link #LOGIN_TYPE_VOICE}<br>
     * {@link #LOGIN_TYPE_CARKEY}<br>
     * {@link #LOGIN_TYPE_DIGITALKEY}<br>
     */
    @IntDef({LOGIN_TYPE_UNKNOWN
            , LOGIN_TYPE_USER_ACC
            , LOGIN_TYPE_FINGERPRINT
            , LOGIN_TYPE_FACEID
            , LOGIN_TYPE_VOICE
            , LOGIN_TYPE_CARKEY
            , LOGIN_TYPE_DIGITALKEY
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface ProfileLoginType {
    }


    /**
     * Get the count of the Profile which the hardware is Supported<br>
     * The Profile ID must between 1 & the count(included)
     *
     */
    int getSupportUserProfileCount();

    /**
     * Get the ID of all profiles that have been created
     *
     */
    int[] getUserProfileCreated();


    /**
     * get current user profile id
     * @return user profile id
     */
    int getCurrentId();

    /**
     * get the user profile id bind to User id(EID, GID...)
     * @param uid User Identification
     *
     */
    int getProfileId(String uid);

    /**
     * get the User id(EID, GID...) bind to special profile id
     * @param profileId Profile id
     *
     */
    String getUserId(int profileId);

    /**
     * Get the type {@link ProfileLoginType} that how the profile login
     * @param profileId Profile id
     *
     */
    @ProfileLoginType
    int getProfileLoginType(int profileId);

    /**
     * add new user profile with the default data
     * @return The profile id has created, if <= 0, the caller should listen the callback
     */
    int addUserProfile();

    /**
     * add new user profile & copy profile data {@link IProfile} from a given profile
     * @param profileId The profile which copied from
     * @return The profile id has created, if <= 0, the caller should listen the callback
     *
     */
    int addUserProfileCopyFrom(int profileId);

    /**
     * remove user profile
     * @param profileId The User profile id to remove
     * @return true indicates the function is succeed
     */
    boolean removeUserProfile(int profileId);

    /**
     * login a user profile
     * @param profileId The User profile id to login
     * @return true indicates the function is succeed
     */
    boolean loginUserProfile(int profileId);

    /**
     * logout a user profile
     * @param profileId The User profile id to logout
     * @return true indicates the function is succeed
     */
    boolean logoutUserProfile(int profileId);

    /**
     * switch a user profile
     * @param oldProfileId The User profile id to logout
     * @param newProfileId The User profile id to login
     * @return true indicates the function is succeed
     */
    boolean switchUserProfile(int oldProfileId, int newProfileId);

    /**
     * Request get profile data stored for a profile id, {@link IUserProfileObserver#onUserProfileActionGet}
     * @param profileId User profile id
     * @return true-success false-fail
     */
    boolean getUserProfileData(int profileId);

    /**
     * Sync get profile data stored for a profile id
     * @param profileId User profile id
     * @return profile data
     */
    default IProfile getUserProfileDataSync(int profileId) {
        throw new UnimplementedException();
    }

    /**
     * apply user profile data for a profile id
     * @param profileId User profile id
     * @param profile user profile data {@link IProfile} to be set
     * @return true indicates the function is succeed
     */
    boolean applyUserProfileData(int profileId, IProfile profile);

    /**
     * reset user profile data to default for a profile id
     * @param profileId User profile id
     * @return true indicates the function is succeed
     */
    boolean resetUserProfileDataDefault(int profileId);

    /**
     * Notify User action & information to profile(vehicle HAL)
     * @param notifyAction Notify action type {@link ActionType}, maybe use login/logout or bind to profile
     * @param uid User Identification
     * @param uidExtras User extras, can be null
     * @return true indicates the function is succeed
     *
     */
    boolean notifyUIDInfoToProfile(@ActionType int notifyAction, String uid, @Nullable Bundle uidExtras);

    /**
     * register user profile observer
     * @param observer user profile observer {@link IUserProfileObserver} try to register
     * @return true indicates that register observer is succeeded
     */
    boolean registerUserProfileObserver(IUserProfileObserver observer);

    /**
     * remove user profile observer
     * @param observer user profile observer {@link IUserProfileObserver} to be removed
     * @return true indicates that remove observer is succeeded
     */
    boolean unregisterUserProfileObserver(IUserProfileObserver observer);

    /**
     * 保存人体工学设置到对应用车偏好
     * @param functionId 传入功能ID
     * @param mid 目前一个Profile对应3个槽位，mid固定为1/2/3其中一个，分别对应M1/M2/M3
     * @return false：请求发送失败；true：请求发送成功
     */
    boolean saveToPreference(int mid, int functionId);

    /**
     * 恢复人体工学设置到调节前的状态
     * @return false：请求发送失败；true：请求发送成功
     */
    boolean restorePreference(int mid, int functionId);

    /**
     * 保存人体工学设置到对应用车偏好
     * @param functionId 传入功能ID
     * @return false：请求发送失败；true：请求发送成功
     */
    boolean saveToPreference(int functionId);

    /**
     * 恢复某mid人体工学设置到调节前的状态
     * @param functionId 传入功能ID
     * @return false：请求发送失败；true：请求发送成功
     */
    boolean restorePreference(int functionId);

    /**
     * 判断当前用户是否有人脸数据
     * @param userid 传入用户ID
     * @return false：当前用户没有人脸数据；true：当前用户有人脸数据
     */
    boolean isEnrolledFaceData(String userid);

    /**
     * 获取用车偏好ID
     * @param type -->0：常用（默认）偏好； 1--> 正在使用的偏好
     * @return 固定为1/2/3其中一个，分别对应M1/M2/M3
     */
    int getPreferenceId(int type);

    /**
     * user profile observer
     */
    interface IUserProfileObserver {
        /**
         * callback when called {@link #addUserProfile()}, and add user profile succeed
         * @param profileId The User profile id added
         */
        void onUserProfileAdded(int profileId);

        /**
         * callback when do user profile action {@link ActionType} or profile action status changed self
         * @param action profile action {@link ActionType}, e.g. add / remove profile
         * @param profileId The User profile id, can be ignored for {@link #ACTION_TYPE_PROFILE_ADD}
         * @param status action status {@link ActionStatus}
         */
        void onUserProfileActionStatus(@ActionType int action, int profileId, @ActionStatus int status);

        /**
         * Error code when something is wrong
         * @param action profile action {@link ActionType}, e.g. add / remove profile
         * @param code error code {@link ErrorCode}
         */
        void onUserProfileActionError(@ActionType int action, @ErrorCode int code);

        /**
         * progress Face entry progress value
         *
         * @param action profile action {@link ActionType}, e.g. add / remove profile
         * @param profileId The User profile id, can be ignored for {@link #ACTION_TYPE_PROFILE_ADD}
         * @param status action status {@link ActionStatus}
         * @param progress face entry progress
         * */
        void onUserProfileActionStatus(@ActionType int action, int profileId, @ActionStatus int status, int progress);

        /**
         * Profile json string
         * @param profileJsonString profile data {@link #getUserProfileData(int)}
         */
        void onUserProfileActionGet(String profileJsonString);
    }


    /**
     * 保存人体工学设置到当前激活的用车偏好中
     *
     * @return true 信号发送是否成功
     */
    boolean saveCurrentUserProfile();

    /**
     * 恢复上次在用车偏好中保存的人体工学设置
     * @return true 信号发送是否成功
     */
    boolean restoreLastUserProfile();

    /**
     * 用车偏好回调监听
     */
    interface IPreferenceObserver {

        /**
         * 人体工学设置保存/恢复的状态返回
         *
         * @param action --> 0：保存动作；1：恢复动作
         * @param state  --> 0：失败；1：进行中；2：成功
         */
        void onPreferenceStateChange(int action, int state);

        /**
         * 人体工学调节的回调接口
         *
         * @param status 人体工学的调整状态 {@link AdjustStatus}，e.g. profile adjusting / adjusted
         * @param items  人体工学的调整项 {@link AdjustItem}
         */
        void onItemAdjusted(@AdjustStatus int status, @AdjustItem int[] items);
    }

    /**
     * 注册用车偏好回调监听
     *
     * @param pb IPreferenceObserver 回调接口对象
     * @return false：请求发送失败；true：请求发送成功
     */
    boolean registerPreferenceObserver(IPreferenceObserver pb);

    /**
     * 反注册用车偏好回调监听
     *
     * @param pb IPreferenceObserver 回调接口对象
     * @return false：请求发送失败；true：请求发送成功
     */
    boolean unregisterPreferenceObserver(IPreferenceObserver pb);

    /**
     * adjust status：adjusting（调整中）
     */
    int STATUS_ADJUSTING = 0x01;
    /**
     * adjust status：adjusted（调整完毕）
     */
    int STATUS_ADJUSTED = 0x02;

    /**
     * Profile adjust status<br>
     * {@link #STATUS_ADJUSTING}<br>
     * {@link #STATUS_ADJUSTED}<br>
     */
    @IntDef({STATUS_ADJUSTING,
            STATUS_ADJUSTED
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface AdjustStatus {
    }

    /**
     * adjust item：wheel（方向盘）
     */
    int ADJUST_ITEM_WHEEL = 0x01;
    /**
     * adjust item：seat（主驾座椅）
     */
    int ADJUST_ITEM_SEAT = 0x02;
    /**
     * adjust item：rear mirror（后视镜）
     */
    int ADJUST_ITEM_REAR_MIRROR = 0x04;
    /**
     * adjust item：HUD
     */
    int ADJUST_ITEM_HUD = 0x08;
    /**
     * adjust item：electronic rear mirror（电子后视镜）
     */
    int ADJUST_ITEM_ELECTRONIC_REAR_MIRROR = 0x10;

    /**
     * Profile adjust item<br>
     * {@link #ADJUST_ITEM_WHEEL}<br>
     * {@link #ADJUST_ITEM_SEAT}<br>
     * {@link #ADJUST_ITEM_REAR_MIRROR}<br>
     * {@link #ADJUST_ITEM_HUD}<br>
     * {@link #ADJUST_ITEM_ELECTRONIC_REAR_MIRROR}<br>
     */
    @IntDef({ADJUST_ITEM_WHEEL,
            ADJUST_ITEM_SEAT,
            ADJUST_ITEM_REAR_MIRROR,
            ADJUST_ITEM_HUD,
            ADJUST_ITEM_ELECTRONIC_REAR_MIRROR
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface AdjustItem {
    }

}
