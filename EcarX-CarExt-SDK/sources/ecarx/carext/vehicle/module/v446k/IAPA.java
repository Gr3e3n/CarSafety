//
// ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
// Without permission, no one has the right to obtain, disclose or use this software in any way.
//
package ecarx.carext.vehicle.module.v446k;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Auto Parking Assist and History Parking Assist (APA and HPA)
 */
public interface IAPA {
    /**
     * Obtain the overall functional effectiveness of APA from CC
     * 从CC获取APA整体功能有效性
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 10:10:27
     */
    int IS_FUNC_APA_ACTIVE = 0x00028600;

    /**
     * Obtain the overall functional effectiveness of RPA from CC
     * 从CC获取RPA整体功能有效性
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 10:11:13
     */
    int IS_FUNC_RPA_ACTIVE = 0x00028700;

    /**
     * Obtain the overall functional effectiveness of RPP from CC
     * 从CC获取RPP整体功能有效性
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 10:11:53
     */
    int IS_FUNC_RPP_ACTIVE = 0x00028800;

    /**
     * Obtain the overall functional effectiveness of RSPA from CC
     * 从CC获取RSPA整体功能有效性
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 10:12:50
     */
    int IS_FUNC_RSPA_ACTIVE = 0x00028900;

    /**
     * After starting APA, obtain whether the corresponding data flow status is in APA mode, and ADCU sends PrkglmgDispReq signal to DHU
     * 启动APA后，获取对应数据流状态是否为APA模式，ADCU发送PrkglmgDispReq信号到DHU
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 10:17:02
     */
    int APA_IS_DISPLAY = 0x00028a00;

    /**
     * APA displays classical Chinese prompts internally, and ADCU sends PrkgFctDiDisp signal to DHU to display abnormal prompts
     * APA内部展示文言提示，ADCU发送PrkgFctDiDisp信号到DHU用于展示异常的提示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 反馈对应弹窗的ID 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 10:19:52
     */
    int APA_TOAST_NOTIFICATION = 0x00028b00;

    /**
     * After the interruption of APA parking, DHU sends a signal PrkgintrptReldBtn to ADCU to control the subsequent parking status; ADCU provides feedback on the DrrAsscSysDisp signal, which is used as a status button to display the current parking progress
     * APA泊车中断后的控制，DHU下发信号PrkgintrptReldBtn到ADCU，控制后续泊车状态 ；ADCU反馈DrvrAsscSysDisp信号，用于展示当前泊车进度的状态按钮
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link ParkingInterruptStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 10:48:13
     */
    int APA_PARKING_INTERRUPT_CONTROL = 0x00028c00;

    /**
     * The APA application switches internally to AVM, and DHU sends a signal PrkgFctSwt to ADCU, changing the data flow from APA to AVM; ADCU provides feedback on the current button display status to DHU
     * APA应用内部跳转至AVM，DHU下发信号PrkgFctSwt到ADCU，改变数据流从APA变为AVM；ADCU反馈当前按钮展示状态到DHU
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 10:52:25
     */
    int APA_SWITCH_TO_AVM = 0x00028d00;

    /**
     * Current parking status and progress
     * 当前泊车的状态与进度
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 例：[0]:当前泊车进度 ；[1]:挡位 ；[2]：泊车方向显示时间 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 10:58:17
     */
    int APA_PARKING_PROGRESS_AND_STATUS = 0x00028e00;

    /**
     * The approximate state of the vehicle when parked
     * 泊车时车辆的大致状态集合
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 例：[0]:泊车指示1 ；[1]:泊车指示2 ；[2]:水平左泊出状态 ；[3]:水平右泊出状态 ；[4]:车门状态 ；[5]:车锁状态 ；[6]:左右舵（0代表左、1代表右）；[7]:Camera状态 ；[8]:Rcta状态		 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 11:04:36
     */
    int APA_STATUS_MARKET = 0x00028f00;

    /**
     * APA voice broadcast
     * APA内部的语音播报
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 信号对应的文言ID 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 11:15:12
     */
    int APA_VOICE_BROADCAST = 0x00029000;

    /**
     * APA single step travel distance, ADCU feedback ValtPrkgL1SpecialPromptDisp to DHU
     * APA单步行驶距离,ADCU反馈ValtPrkgL1SpecialPromptDisp到DHU，DHU根据信号的值，以及bit位callback给应用参数
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 例：[0]:是否显示单步剩余距离 ；[1]:距离值 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 11:17:47
     */
    int APA_PARKING_TRAVELED_DISTANCE = 0x00029100;

    /**
     * APA parking in function
     * APA泊入功能,DHU下发信号PrkglnOrOutAndCncl = 1 控制ADCU状态机切换为泊入动作 ;ADCU反馈PrkgBtnStsDispGroupPrkgin值用于通知DHU泊入按钮的展示状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 11:21:13
     */
    int APA_PARKING_IN_CONTROL = 0x00029200;

    /**
     * APA parking out function
     * APA泊出功能，DHU下发信号PrkglnOrOutAndCncl = 2 控制ADCU状态机切换为泊出动作 ；ADCU反馈PrkgBtnStsDispGroupPrkgout值用于通知DHU泊出按钮的展示状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 11:23:14
     */
    int APA_PARKING_OUT_CONTROL = 0x00029300;

    /**
     * APA parking out direction selection
     * APA泊出方向选择，左/右泊出方向以座椅的area来区分下发
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_SEAT}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 11:29:08
     */
    int APA_PARKING_OUT_DIRECTION = 0x00029400;

    /**
     * APA setting button status acquisition, ADCU feedback PrkgBtnTsDispset value is used to control the display status of this icon
     * APA设置按钮状态获取，ADCU反馈PrkgBtnStsDispset值用于控制此图标的展示状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 16:59:13
     */
    int APA_SETTINGS_STATUS = 0x00029500;

    /**
     * V446K has remote parking, and after selecting a parking space, two parking methods will appear: automatic parking on the car's infotainment system and remote parking on the mobile phone. The buttons are not frequently displayed
     * V446K有遥控泊车，在选择车位后会出现两种泊车方式，车机端自动泊车和手机端遥控泊车，按钮不常显
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 17:01:54
     */
    int APA_PREMOTE_PARKING_CONTROL = 0x00029600;

    /**
     * The distance setting required for remote parking
     * 遥控泊车需要行驶的距离设置，ADCU反馈RemStraightPrkgAssiSelnReqResp值用于展示当前遥控泊车的形式距离；DHU下发RemStraightPrkgAssiSelnReq控制需要遥控的距离
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APAParkingDistance} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 17:07:01
     */
    int APA_PREMOTE_PARKING_DISTANCE_CONTROL = 0x00029700;

    /**
     * Parking assist sound mode switching
     * 泊车辅助声音模式切换
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APAVoiceMode} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 17:11:41
     */
    int APA_SWITCH_HELP_VOICE = 0x00029800;

    /**
     * AVM settings in APA settings
     * APA设置中的AVM设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 17:13:52
     */
    int APA_AVM_SETTINGS = 0x00029900;

    /**
     * Low speed active exploration function switch
     * 低速主动探索功能开关，DHU下发LSDANotifPushRec控制是否开启此功能，ADCU反馈LSDANotifPushResp通知DHU当前低速主动探索开关状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 17:15:59
     */
    int APA_LOW_SPEED_EXPLORE = 0x00029a00;

    /**
     * Parking space selection, the value of AutPrkgSlotNrReq issued by DHU is the parking space ID value, and there is no feedback signal. The issuance is successful
     * 车位选择，DHU下发AutPrkgSlotNrReq的值为车位ID值，无反馈信号，下发即成功
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 车位ID，无法等待反馈，下发即是成功 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 17:18:24
     */
    int APA_SELECT_VIR_PARKING_SPACE = 0x00029b00;

    /**
     * Display all details of the vehicle's parking status during parking
     * 展示泊车时车辆的所有细节状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type [0]:驾驶员车门状态（0：关 1：开） 		[1]:副驾车门状态（0：关 1：开） 		[2]:左后车门状态（0：关 1：开） 		[3]:右后车门状态（0：关 1：开） 		[4]:引擎盖状态（0：关 1：开） 		[5]:后备箱状态（0：关 1：开） 		[6]:驾驶员外后折叠状态 		[7]:乘客外后视镜折叠 		[8]:刹车灯状态（0：关 1：开） 		[9]:左转向灯状态（0：关 1：开） 		[10]:右转向灯状态（0：关 1：开） 		[11]:远光灯状态（0：关 1：亮  2：错误亮起） 		[12]:近关灯状态（0：关 1：亮  2：错误亮起） 		[13]:转向角度 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-03 17:20:20
     */
    int APA_HMI_SHOW_PARKING_SPOT = 0x00029c00;

    /**
     * Second confirmation of the vehicle's parking direction for sensorless parking function
     * 无感泊车功能，泊出方向二次确认
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type 当用户泊出选择方向后再次点击确认按钮时传值true以启动泊出操作，返回false代表功能开启失败 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-16 19:11:22
     */
    int PARKING_OUT_CONFIRMATION = 0x0002b000;

    /**
     * Sensorless parking&lt;br&gt;
     * 无感泊车开关&lt;br&gt;
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-18 18:12:37
     */
    int SENSORLESS_PARKING = 0x0002bf00;

    /**
     * [v446 private] apa exit action，V446K requires independent validity for enter and exit.
     * [v446k私有]apa自动泊车退出动作，V446K要求开启和关闭是独立有效性
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type 只能下发true 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-18 18:49:07
     */
    int APA_EXIT = 0x0002c000;

    /**
     * [v446k private]APA exit buttion enable or not,V446K requires independent validity for enter and exit.
     * [v446k私有]APA退出按钮有效性，V446K要求开启和关闭是独立有效性
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2024-12-18 18:51:47
     */
    int APA_EXIT_BUTTON_ENABLE = 0x0002c100;

    /**
     * Parking assist: PDC mute switch
     * 泊车辅助：PDC静音开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2024-12-31 10:22:52
     */
    int APA_PDC_MUTE_SWITCH = 0x0002c300;

    /**
     * Obtain the overall functional effectiveness of HPA from CC
     * 从CC获取HPA整体功能有效性
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-13 17:46:35
     */
    int IS_FUNC_HPA_ACTIVE = 0x0002d500;

    /**
     * HPA entry and exit control
     * HPA 进入和退出控制
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-13 17:49:17
     */
    int HPA_ACTION_CONTROL = 0x0002d600;

    /**
     * Passive entry into HPA confirmation pop
     * 被动进入HPA确认弹框，callback的值代表显示/隐藏，set下发的值代表弹框的确认/取消按钮
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-13 17:55:21
     */
    int HPA_CONFIRM_POP = 0x0002d700;

    /**
     * HPA recovery function control
     * HPA恢复功能控制
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-14 15:54:13
     */
    int HPA_RECOVER_BTN = 0x0002d800;

    /**
     * HPA return key to exit
     * HPA返回键退出
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-14 15:56:35
     */
    int HPA_EXIT_BACK_BTN = 0x0002d900;

    /**
     * HPA Notifications
     * HPA 通知文言
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-14 16:47:16
     */
    int HPA_NOTIFICATIONS = 0x0002da00;

    /**
     * HPA's confirmation/cancellation operation after receiving notification
     * HPA收到通知后的确认/取消操作
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type int[0]：信号值；int[1]：确认/取消 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-14 16:51:46
     */
    int HPA_NOTIFICATIONS_CONTROL = 0x0002db00;

    /**
     * Is the HPA interface in display state
     * HPA 界面是否处于显示状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type False： 退出状态 True： 进入状态 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-14 16:58:46
     */
    int HPA_IS_DISPLAY = 0x0002dc00;

    /**
     * HPA video stream type
     * HPA 视频流类型
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-14 17:09:58
     */
    int HPA_VIDEO_TYPE = 0x0002dd00;

    /**
     * HPA voice broadcast
     * HPA 语音播报
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-14 17:11:07
     */
    int HPA_TTS_CALLBACK = 0x0002de00;

    /**
     * HPA voice control
     * HPA 语音控制
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type int[0]: 系统需求中的语音操控指令序号; int[1]: 0：取消；1：确认; int[2]: 额外操控的 地图ID  车位ID 行为  等，根据项目自定扩展 比如： 0 忽略，1： 泊入，2 泊出; int[..]:根据项目扩展 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-14 17:16:23
     */
    int HPA_TTS_CONTROL = 0x0002df00;

    /**
     * HPA Arrival Destination Button
     * HPA 到达目的地按钮
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-15 10:15:41
     */
    int HPA_CONFIRM_DEXTINATION_BTN = 0x0002e000;

    /**
     * HPA Settings Button
     * HPA 设置按钮
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-15 10:16:48
     */
    int HPA_SETTING_BTN = 0x0002e100;

    /**
     * Entering and exiting the HPA control settings interface
     * hpa 控制设置界面的进入退出
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-15 10:20:19
     */
    int HPA_SETTING_PAGE_CONTROL = 0x0002e200;

    /**
     * The current display type on the HPA settings interface
     * HPA设置界面当前显示类型
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HPASettingPageType} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-15 10:26:07
     */
    int HPA_SETTING_PAGE_CONFIG = 0x0002e300;

    /**
     * HPA intelligent generation route setting switch
     * hpa 智能生成路线设置开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-15 10:28:23
     */
    int HPA_SETTING_AUTO_ROUTES_SWITCH = 0x0002e400;

    /**
     * HPA intelligent push setting switch
     * hpa 智能推送设置开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-15 10:29:35
     */
    int HPA_SETTING_PUSH_SWITCH = 0x0002e500;

    /**
     *  Tracking Back Assist available  
     *  倒车循迹 有效性 
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * biao.peng@hiecarx.com created for project v446k at 2025-01-15 10:37:41
     */
    int TBA_CAR_CONFIG = 0x0002e600;

    /**
     * Tracking Back Assist HMI Enter Exit state
     * 倒车循迹 界面进入退出状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * biao.peng@hiecarx.com created for project v446k at 2025-01-15 10:42:56
     */
    int TBA_HMI_STATUS = 0x0002e700;

    /**
     *  Tracking Back Assist back button
     *  倒车循迹 退出按钮
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * biao.peng@hiecarx.com created for project v446k at 2025-01-15 10:46:30
     */
    int TBA_BACK_BTN = 0x0002e800;

    /**
     * Tracking Back Assist start back trace button
     * 倒车循迹 开始循迹按钮
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * biao.peng@hiecarx.com created for project v446k at 2025-01-15 10:49:58
     */
    int TBA_START_BACK_TRACE_BTN = 0x0002e900;

    /**
     * Tracking Back Assist text park before
     * 倒车循迹 控车前文言
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     *  <li>{@code Integer} property type  
     * </ul>
     * biao.peng@hiecarx.com created for project v446k at 2025-01-15 10:56:42
     */
    int TBA_TEXT_PARK_BEFORE = 0x0002ea00;

    /**
     * Tracking Back Assist text park after
     * 倒车循迹 控车后文言
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     *  <li>{@code Integer} property type  
     * </ul>
     * biao.peng@hiecarx.com created for project v446k at 2025-01-15 10:57:54
     */
    int TBA_TEXT_PARK_AFTER = 0x0002eb00;

    /**
     *  Tracking Back Assist voice broadcast
     *  倒车循迹 语音播报
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_STATIC}
     *  <li>{@code Integer} property type  
     * </ul>
     * biao.peng@hiecarx.com created for project v446k at 2025-01-15 11:01:16
     */
    int TBA_TTS_CALLBACK = 0x0002ec00;

    /**
     * Tracking Back Assist Text To Speech control confirm or cancel
     * 倒车循迹 语音控制 控制确认或取消
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type  
     * </ul>
     * biao.peng@hiecarx.com created for project v446k at 2025-01-15 11:06:14
     */
    int TBA_TTS_CONTROL = 0x0002ed00;

    /**
     * Tracking Back Assist Interrupt Or Continue Button
     * 倒车循迹-中断继续按钮
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_WHEEL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * biao.peng@hiecarx.com created for project v446k at 2025-01-15 11:09:40
     */
    int TBA_CONFIRM_DEXTINATION_BTN = 0x0002ee00;

    /**
     * HPA Settings: Voice Broadcast Mode Settings
     * HPA设置：语音播报模式设置
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HPAAoundMode} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 13:51:21
     */
    int HPA_SETTING_TTS_CALLBACK_MODE = 0x0002ef00;

    /**
     * HPA View Type，currently, there is a switch between front and rear views
     * HPA视图类型，目前有前后视图切换
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HPAViewType} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 14:01:59
     */
    int HPA_VIEW_TYPE = 0x0002f000;

    /**
     * HPA displays the distance of memory
     * HPA 显示记忆的距离
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float[]} property type 第一位：0=not display，1=display 第二位：记忆距离（米） 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 14:05:12
     */
    int HPA_DISPLAY_ELEMENT_REC_DST = 0x0002f100;

    /**
     * Number of HPA intersections displayed
     * hpa 路口数量显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 第一位：0=not display，1=display   第二位：路口数量 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 14:07:04
     */
    int HPA_DISPLAY_ELEMENT_REC_CRSSING = 0x0002f200;

    /**
     * HPA speed bump quantity display
     * hpa减速带数量显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 第一位：0=not display，1=display   第二位：减速带数量 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 14:08:11
     */
    int HPA_DISPLAY_ELEMENT_REC_BUM = 0x0002f300;

    /**
     * Display the distance between HPA and the endpoint
     * hpa距离终点的距离显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float[]} property type 第一位：0=not display，1=display   第二位：距离终点的距离（米） 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 14:09:21
     */
    int HPA_DISPLAY_ELEMENT_REC_TO_END = 0x0002f400;

    /**
     * Display of the number of pedestrians avoided by HPA
     * hpa避让行人数量显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 第一位：0=not display，1=display   第二位：避让行人数量 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 14:10:28
     */
    int HPA_DISPLAY_ELEMENT_REC_PED = 0x0002f500;

    /**
     * HPA avoidance vehicle quantity display
     * hpa避让车辆数量显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 第一位：0=not display，1=display   第二位：避让车辆数量 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 14:11:30
     */
    int HPA_DISPLAY_ELEMENT_REC_VEH = 0x0002f600;

    /**
     * HPA slope quantity display
     * hpa斜坡数量显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 第一位：0=not display，1=display   第二位：斜坡数量 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 14:13:00
     */
    int HPA_DISPLAY_ELEMENT_REC_RAMP = 0x0002f700;

    /**
     * HPA remaining distance display
     * hpa剩余距离显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float[]} property type 第一位：0=not display，1=display   第二位：剩余距离（米） 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 14:14:28
     */
    int HPA_DISPLAY_ELEMENT_REMAINING_DST = 0x0002f800;

    /**
     * HPA cruise/remaining time display
     * hpa巡航/剩余时间显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 第一位：0:Not display, 1:更新巡航时间，2:巡航完成总用时，3:巡航剩余时间   第二位：巡航/剩余时间的具体值 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 15:02:13
     */
    int HPA_DISPLAY_ELEMENT_TIME_TYPE = 0x0002f900;

    /**
     * HPA tutorial interface display and hide
     * hpa 使用教程界面显示与隐藏
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 15:03:46
     */
    int HPA_DISPLAY_ELEMENT_TUTORIAL = 0x0002fa00;

    /**
     * HPA creates parking route button status
     * hpa 创建泊入路线按钮状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 16:21:21
     */
    int HPA_ELEMENT_CREATE_PARK_IN_BTN = 0x0002fb00;

    /**
     * HPA creates berthing route button status
     * hpa 创建泊出路线按钮状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APABtnStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 16:22:38
     */
    int HPA_ELEMENT_CREATE_PARK_OUT_BTN = 0x0002fc00;

    /**
     * HPA initiates the creation of a parking route
     * hpa 发起创建泊入路线
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 16:29:46
     */
    int HPA_CREATE_PARK_IN = 0x0002fd00;

    /**
     * HPA initiates the creation of a berthing route
     * hpa 发起创建泊出路线
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 路线ID 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 16:30:59
     */
    int HPA_CREATE_PARK_OUT = 0x0002fe00;

    /**
     * Existing map 0 located by HPA
     * hpa 定位到的已有地图0
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type INDEX: 0:map_id；1:map_state；2:map_used，3:distance 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 16:54:11
     */
    int HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP0 = 0x0002ff00;

    /**
     * Existing map 1 located by HPA
     * hpa 定位到的已有地图1
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type INDEX: 0:map_id；1:map_state；2:map_used，3:distance 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 16:55:17
     */
    int HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP1 = 0x00030000;

    /**
     * Existing map 2 located by HPA
     * hpa 定位到的已有地图2
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type INDEX: 0:map_id；1:map_state；2:map_used，3:distance 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 16:56:09
     */
    int HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP2 = 0x00030100;

    /**
     * Existing map 3 located by HPA
     * hpa 定位到的已有地图3
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type INDEX: 0:map_id；1:map_state；2:map_used，3:distance 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 16:57:13
     */
    int HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP3 = 0x00030200;

    /**
     * Existing map 4 located by HPA
     * hpa 定位到的已有地图4
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type INDEX: 0:map_id；1:map_state；2:map_used，3:distance 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 16:58:10
     */
    int HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP4 = 0x00030300;

    /**
     * HPA positioning specifies the map route
     * hpa 定位指定地图路线
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 16:59:29
     */
    int HPA_DISPLAY_ELEMENT_LOCAL_MAP_SELECT = 0x00030400;

    /**
     * HPA initiates the closure of the location map page
     * hpa 发起关闭定位地图页面
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:00:21
     */
    int HPA_DISPLAY_ELEMENT_LOCAL_MAP_CLOSE = 0x00030500;

    /**
     * HPA docking route replacement display
     * hpa 泊入路线替换显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 0：是否显示（0=Not display，1=Display）展示 路线替换揭密胺 1：第一条路线的ID（ValPrkgMapinfDisp0.Map_id） 2：第二条路线的ID（ValPrkgMapinfDisp1.Map_id） 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:01:47
     */
    int HPA_DISPLAY_ELEMENT_PARK_IN_COVER = 0x00030600;

    /**
     * HPA berthing route replacement display
     * hpa 泊出路线替换显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 0：是否显示（0=Not display，1=Display）展示 路线替换揭密胺 1：第一条路线的ID（ValPrkgMapinfDisp0.Map_id） 2：第二条路线的ID（ValPrkgMapinfDisp1.Map_id） 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:02:34
     */
    int HPA_DISPLAY_ELEMENT_PARK_OUT_COVER = 0x00030700;

    /**
     * HPA initiates coverage of berthing routes
     * hpa 发起覆盖泊入路线
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type MAPID 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:04:04
     */
    int HPA_DISPLAY_ELEMENT_PARK_IN_COVER_SET = 0x00030800;

    /**
     * HPA initiates coverage of berthing routes
     * hpa 发起覆盖泊出路线
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type MAP ID 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:04:55
     */
    int HPA_DISPLAY_ELEMENT_PARK_OUT_COVER_SET = 0x00030900;

    /**
     * HPA parking progress
     * hpa 泊车进度
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 0：是否显示（0=Not display，1=Display） 1：泊车进度 2：挡位信息 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:06:01
     */
    int HPA_PARKING_PROGRESS = 0x00030a00;

    /**
     * Progress of HPA route creation
     * hpa 路线创建进度
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 0：是否显示（0=Not display，1=Display） 1：路径生成进度 2：挡位信息 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:06:49
     */
    int HPA_CREATE_ROUTR_PROGRESS = 0x00030b00;

    /**
     * HPA creates map results
     * hpa 创建地图结果
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HPAMapBuildStatus} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:10:59
     */
    int HPA_MAP_BUILD_RESULT = 0x00030c00;

    /**
     * The HPA map management page displays
     * hpa 地图管理页面显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link HPARouteDisplay} property type
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:16:36
     */
    int HPA_ROUTER_MANAGE_DISPLAY = 0x00030d00;

    /**
     * Naming notification and operation after successful HPA mapping
     * hpa 建图成功后的命名通知及操作
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type GET：true=显示，false=不显示 SET：true=Save，false=Cancel 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:20:11
     */
    int HPA_MAP_RENAME_NOTICE_CONTROL = 0x00030e00;

    /**
     * HPA deleting parking route
     * hpa 删除泊入路线
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:21:34
     */
    int HPA_PARK_IN_ROUTES_DELETE = 0x00030f00;

    /**
     * Remove the parking route from HPA
     * hpa 删除泊出路线
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:22:22
     */
    int HPA_PARK_OUT_ROUTES_DELETE = 0x00031000;

    /**
     * HPA parking route synchronization
     * HPA泊车路线同步
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type  
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:23:50
     */
    int HPA_PARK_ROUTE_SYNC = 0x00031100;

    /**
     * HPA restoration world perspective switching
     * hpa 还原世界视角切换
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 0=NotDisplay，1=GlbView，2=PartView 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 17:25:44
     */
    int HPA_DISPLAY_ELEMENT_VIEW_SWITCH = 0x00031200;

    /**
     * Is the HPA pop-up view displayed in a reduced size
     * hpa 弹窗视图是否缩小显示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type true代表缩小显示，false代表不缩小 
     * </ul>
     * hang.jiang@ecarxgroup.com created for project v446k at 2025-01-17 18:02:07
     */
    int HPA_POP_VIEW_IS_REDUCE = 0x00031300;

    /**
     * Set obstacle triggered switch in APA
     * apa中设置障碍物触发的开关
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Boolean} property type  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-01-18 11:08:32
     * @deprecated 使用 APA_OBSTACLE_DETECTION_SWITCH，是一样的功能 --- by deliang.feng@ecarxgroup.com
     */
    @Deprecated
    int APA_OBSTACLE_TRIGGER_SWITCH = 0x00031400;

    /**
     * (Parking Distance Control)The distance between the front radar and the obstacle
     * (泊车距离控制系统)前雷达距离障碍物的距离
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 单位厘米, unit cm 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-02-08 16:29:27
     */
    int PDC_FRONT_DISTANCE = 0x00031a00;

    /**
     * (Parking Distance Control)The distance between the rear radar and the obstacle
     * (泊车距离控制系统)后雷达距离障碍物的距离
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 单位厘米, unit cm  
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-02-08 16:30:40
     */
    int PDC_REAR_DISTANCE = 0x00031b00;

    /**
     * In the R gear and system control mode, the dock bar is grayed out and cannot be clicked. &lt;br&gt;request dock bar display or graying out.
     * R档和系统控车状态下，dock栏置灰不可点击。Dock栏显示与置灰&lt;br/&gt;Dock栏显示与置灰FR信号：PrkgDockDisReq。&lt;br/&gt;原AdaptAPI：IPAS.PAS_FUNC_APA_PRKG_DOCK_DIS_REQ
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer} property type 0x00: Normal Dock 栏正常显示<br>0x01: Gray Dock 栏全部置灰<br>0x02~0x03:Reserve 预留 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-03-21 10:16:06
     */
    int APA_PARKING_DOCK_DISABLE_REQUEST = 0x00032500;

    /**
     * hpa notification/prompt
     * HPA记忆泊车文言提示
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type [0]:ValtPrkgMaiProcPromtReq [1]:ValtPrkgReminder [2]:ValtPrkgSmallProcPromtReq 返回值示例:[1,3,2] 
     * </ul>
     * deliang.feng@ecarxgroup.com created for project v446k at 2025-04-11 09:46:32
     */
    int HPA_PARKING_TEXT = 0x00032c00;

    /**
     * Directly monitor the PrkgImgDispReq signal and provide feedback on the display status of AVM(Around View Monitor), APA(Auto Parking Assist) and TBA(Tracking Backup Assistance) ,etc 
     * 直接监听PrkgImgDispReq信号，反馈360环视、自动泊车辅助、循迹倒车辅助等系统显示状态
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link DisplayStatus} property type
     * </ul>
     * yujing.wu@ecarxgroup.com created for project v446k at 2025-05-15 19:36:04
     */
    int PRKG_IMG_DISPLAY_STATUS = 0x00033200;

    /**
     * Report parking-related high-frequency signals
     * 上报泊车相关的高频信号
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Float[]} property type [0]:前转向装置的小齿轮转向角 [1]:前转向装置的小齿轮转向角速度 [2]:方向盘扭矩 
     * </ul>
     * yifei2.zhang@hiecarx.com created for project v446k at 2025-08-12 10:00:33
     */
    int APA_HIGH_FREQUENCY_STATUS = 0x00033c00;

    /**
     * Existing map 5 located by HPA
     * hpa 定位到的已有地图5
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 0:map_id；1:map_state；2:map_used，3:distance  
     * </ul>
     * yifei2.zhang@hiecarx.com created for project v446k at 2025-09-26 14:49:15
     */
    int HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP5 = 0x00034100;

    /**
     * Existing map 6 located by HPA
     * hpa 定位到的已有地图6
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 0:map_id；1:map_state；2:map_used，3:distance  
     * </ul>
     * yifei2.zhang@hiecarx.com created for project v446k at 2025-09-26 14:51:17
     */
    int HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP6 = 0x00034200;

    /**
     * Existing map 7 located by HPA
     * hpa 定位到的已有地图7
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 0:map_id；1:map_state；2:map_used，3:distance  
     * </ul>
     * yifei2.zhang@hiecarx.com created for project v446k at 2025-09-26 14:53:01
     */
    int HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP7 = 0x00034300;

    /**
     * Existing map 8 located by HPA
     * hpa 定位到的已有地图8
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 0:map_id；1:map_state；2:map_used，3:distance  
     * </ul>
     * yifei2.zhang@hiecarx.com created for project v446k at 2025-09-26 14:54:00
     */
    int HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP8 = 0x00034400;

    /**
     * Existing map 9 located by HPA
     * hpa 定位到的已有地图9
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     *  <li>{@code Integer[]} property type 0:map_id；1:map_state；2:map_used，3:distance 
     * </ul>
     * yifei2.zhang@hiecarx.com created for project v446k at 2025-09-26 14:54:43
     */
    int HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP9 = 0x00034500;

    /**
     * APA/TBA View Type，currently, there is a switch between front and rear views
     * APA/TBA视图类型，目前有前后视图切换
     * <ul>
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_ACCESS_READ_WRITE}
     *  <li>{@link ecarx.carext.vehicle.VehicleAreaType#VEHICLE_AREA_TYPE_GLOBAL}
     *  <li>{@link ecarx.carext.vehicle.ICarConfig#VEHICLE_PROPERTY_CHANGE_MODE_ONCHANGE}
     * <li>{@link APAViewType} property type
     * </ul>
     * yifei2.zhang@hiecarx.com created for project v446k at 2025-10-24 10:30:19
     */
    int APA_VIEW_TYPE = 0x00034a00;

    @IntDef({APABtnStatus.APA_BTN_NOTDISPLAY,
            APABtnStatus.APA_BTN_NORMAL,
            APABtnStatus.APA_BTN_GRAY,
            APABtnStatus.APA_BTN_HIGHLIGHT,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface APABtnStatus {
        /**
         * 不显示，APA按钮显示状态根据对手件反馈信号来展示，仅在功能有效性为active时使用
         */
        int APA_BTN_NOTDISPLAY = 0x01;

        /**
         * 正常，APA按钮显示状态根据对手件反馈信号来展示，仅在功能有效性为active时使用
         */
        int APA_BTN_NORMAL = 0x02;

        /**
         * 置灰，APA按钮显示状态根据对手件反馈信号来展示，仅在功能有效性为active时使用
         */
        int APA_BTN_GRAY = 0x03;

        /**
         * 高亮，APA按钮显示状态根据对手件反馈信号来展示，仅在功能有效性为active时使用
         */
        int APA_BTN_HIGHLIGHT = 0x04;
    }

    @IntDef({APAVoiceMode.ITEM1,
            APAVoiceMode.ITEM2,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface APAVoiceMode {
        /**
         * 简洁模式
         */
        int ITEM1 = 0x01;

        /**
         * 标准模式
         */
        int ITEM2 = 0x02;
    }

    @IntDef({APAViewType.TYPE_VIEW_DEFAULT,
            APAViewType.TYPE_VIEW_FRONT,
            APAViewType.TYPE_VIEW_REAR,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface APAViewType {
        /**
         * 默认视图
         */
        int TYPE_VIEW_DEFAULT = 0x01;

        /**
         * 前视图
         */
        int TYPE_VIEW_FRONT = 0x02;

        /**
         * 后视图
         */
        int TYPE_VIEW_REAR = 0x03;
    }

    @IntDef({HPARouteDisplay.ITEM0,
            HPARouteDisplay.ITEM1,
            HPARouteDisplay.ITEM2,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface HPARouteDisplay {
        /**
         * 不显示
         */
        int ITEM0 = 0x01;

        /**
         * 泊入路线
         */
        int ITEM1 = 0x02;

        /**
         * 泊出路线
         */
        int ITEM2 = 0x03;
    }

    @IntDef({HPAAoundMode.ITEM0,
            HPAAoundMode.ITEM1,
            HPAAoundMode.ITEM2,
            HPAAoundMode.ITEM3,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface HPAAoundMode {
        /**
         * 精简
         */
        int ITEM0 = 0x01;

        /**
         * 标准
         */
        int ITEM1 = 0x02;

        /**
         * 静音
         */
        int ITEM2 = 0x03;

        /**
         * 默认
         */
        int ITEM3 = 0x04;
    }

    @IntDef({DisplayStatus.DISPLAY_RSPA,
            DisplayStatus.DISPLAY_APA,
            DisplayStatus.DISPLAY_RPA,
            DisplayStatus.DISPLAY_RPAP,
            DisplayStatus.DISPLAY_HPA,
            DisplayStatus.DISPLAY_AVP,
            DisplayStatus.DISPLAY_AVM,
            DisplayStatus.DISPLAY_TBA,
            DisplayStatus.DISPLAY_LSDA,
            DisplayStatus.DISPLAY_OFF,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface DisplayStatus {
        /**
         * Remote Smart Parking Assist，远程智能停车辅助，一般为APA应用功能的一部分
         */
        int DISPLAY_RSPA = 0x01;

        /**
         * Auto Parking Assist，自动泊车辅助
         */
        int DISPLAY_APA = 0x02;

        /**
         * Remote Parking Assist，遥控泊车辅助系统，一般为APA应用功能的一部分
         */
        int DISPLAY_RPA = 0x03;

        /**
         * Remote Parking Assist的一种，一般为APA应用功能的一部分
         */
        int DISPLAY_RPAP = 0x04;

        /**
         * Home-zone Parking Assist，记忆泊车辅助
         */
        int DISPLAY_HPA = 0x05;

        /**
         * Automated Valet Parking，自动代客泊车
         */
        int DISPLAY_AVP = 0x06;

        /**
         * Around View Monitor, 全景环视系统
         */
        int DISPLAY_AVM = 0x07;

        /**
         * Tracking Backup Assistance, 循迹倒车辅助系统
         */
        int DISPLAY_TBA = 0x08;

        /**
         * Low Speed Driving Assist，低速辅助驾驶助手
         */
        int DISPLAY_LSDA = 0x09;

        /**
         * 未显示/退出
         */
        int DISPLAY_OFF = 0xff;
    }

    @IntDef({HPASettingPageType.ITEM0,
            HPASettingPageType.ITEM1,
            HPASettingPageType.ITEM2,
            HPASettingPageType.ITEM3,
            HPASettingPageType.ITEM4,
            HPASettingPageType.ITEM5,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface HPASettingPageType {
        /**
         * 默认
         */
        int ITEM0 = 0x01;

        /**
         * 设置的功能开关页
         */
        int ITEM1 = 0x02;

        /**
         * 设置的帮助页
         */
        int ITEM2 = 0x03;

        /**
         * 泊入路线
         */
        int ITEM3 = 0x04;

        /**
         * 泊出路线
         */
        int ITEM4 = 0x05;

        /**
         * 我的路线
         */
        int ITEM5 = 0x06;
    }

    @IntDef({APAParkingDistance.ITME1,
            APAParkingDistance.ITEM2,
            APAParkingDistance.ITEM3,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface APAParkingDistance {
        /**
         * 5米
         */
        int ITME1 = 0x01;

        /**
         * 10米
         */
        int ITEM2 = 0x02;

        /**
         * 15米
         */
        int ITEM3 = 0x03;
    }

    @IntDef({HPAViewType.ITEM0,
            HPAViewType.ITEM1,
            HPAViewType.ITEM2,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface HPAViewType {
        /**
         * 默认
         */
        int ITEM0 = 0x01;

        /**
         * 前视图
         */
        int ITEM1 = 0x02;

        /**
         * 后视图
         */
        int ITEM2 = 0x03;
    }

    @IntDef({HPAMapBuildStatus.ITEM0,
            HPAMapBuildStatus.ITEM1,
            HPAMapBuildStatus.ITEM2,
            HPAMapBuildStatus.ITEM3,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface HPAMapBuildStatus {
        /**
         * 构建成功
         */
        int ITEM0 = 0x01;

        /**
         * 构建失败
         */
        int ITEM1 = 0x02;

        /**
         * 保存成功
         */
        int ITEM2 = 0x03;

        /**
         * 保存失败
         */
        int ITEM3 = 0x04;
    }

    @IntDef({ParkingInterruptStatus.CONTROL_ITEM1,
            ParkingInterruptStatus.CONTROL_ITEM2,
            ParkingInterruptStatus.CONTROL_ITEM3,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface ParkingInterruptStatus {
        /**
         * 无任何动作
         */
        int CONTROL_ITEM1 = 0x01;

        /**
         * 继续泊车
         */
        int CONTROL_ITEM2 = 0x02;

        /**
         * 回到起点后继续泊车
         */
        int CONTROL_ITEM3 = 0x03;
    }

    @IntDef({IS_FUNC_APA_ACTIVE,
            IS_FUNC_RPA_ACTIVE,
            IS_FUNC_RPP_ACTIVE,
            IS_FUNC_RSPA_ACTIVE,
            APA_IS_DISPLAY,
            APA_TOAST_NOTIFICATION,
            APA_PARKING_INTERRUPT_CONTROL,
            APA_SWITCH_TO_AVM,
            APA_PARKING_PROGRESS_AND_STATUS,
            APA_STATUS_MARKET,
            APA_VOICE_BROADCAST,
            APA_PARKING_TRAVELED_DISTANCE,
            APA_PARKING_IN_CONTROL,
            APA_PARKING_OUT_CONTROL,
            APA_PARKING_OUT_DIRECTION,
            APA_SETTINGS_STATUS,
            APA_PREMOTE_PARKING_CONTROL,
            APA_PREMOTE_PARKING_DISTANCE_CONTROL,
            APA_SWITCH_HELP_VOICE,
            APA_AVM_SETTINGS,
            APA_LOW_SPEED_EXPLORE,
            APA_SELECT_VIR_PARKING_SPACE,
            APA_HMI_SHOW_PARKING_SPOT,
            PARKING_OUT_CONFIRMATION,
            SENSORLESS_PARKING,
            APA_EXIT,
            APA_EXIT_BUTTON_ENABLE,
            APA_PDC_MUTE_SWITCH,
            IS_FUNC_HPA_ACTIVE,
            HPA_ACTION_CONTROL,
            HPA_CONFIRM_POP,
            HPA_RECOVER_BTN,
            HPA_EXIT_BACK_BTN,
            HPA_NOTIFICATIONS,
            HPA_NOTIFICATIONS_CONTROL,
            HPA_IS_DISPLAY,
            HPA_VIDEO_TYPE,
            HPA_TTS_CALLBACK,
            HPA_TTS_CONTROL,
            HPA_CONFIRM_DEXTINATION_BTN,
            HPA_SETTING_BTN,
            HPA_SETTING_PAGE_CONTROL,
            HPA_SETTING_PAGE_CONFIG,
            HPA_SETTING_AUTO_ROUTES_SWITCH,
            HPA_SETTING_PUSH_SWITCH,
            TBA_CAR_CONFIG,
            TBA_HMI_STATUS,
            TBA_BACK_BTN,
            TBA_START_BACK_TRACE_BTN,
            TBA_TEXT_PARK_BEFORE,
            TBA_TEXT_PARK_AFTER,
            TBA_TTS_CALLBACK,
            TBA_TTS_CONTROL,
            TBA_CONFIRM_DEXTINATION_BTN,
            HPA_SETTING_TTS_CALLBACK_MODE,
            HPA_VIEW_TYPE,
            HPA_DISPLAY_ELEMENT_REC_DST,
            HPA_DISPLAY_ELEMENT_REC_CRSSING,
            HPA_DISPLAY_ELEMENT_REC_BUM,
            HPA_DISPLAY_ELEMENT_REC_TO_END,
            HPA_DISPLAY_ELEMENT_REC_PED,
            HPA_DISPLAY_ELEMENT_REC_VEH,
            HPA_DISPLAY_ELEMENT_REC_RAMP,
            HPA_DISPLAY_ELEMENT_REMAINING_DST,
            HPA_DISPLAY_ELEMENT_TIME_TYPE,
            HPA_DISPLAY_ELEMENT_TUTORIAL,
            HPA_ELEMENT_CREATE_PARK_IN_BTN,
            HPA_ELEMENT_CREATE_PARK_OUT_BTN,
            HPA_CREATE_PARK_IN,
            HPA_CREATE_PARK_OUT,
            HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP0,
            HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP1,
            HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP2,
            HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP3,
            HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP4,
            HPA_DISPLAY_ELEMENT_LOCAL_MAP_SELECT,
            HPA_DISPLAY_ELEMENT_LOCAL_MAP_CLOSE,
            HPA_DISPLAY_ELEMENT_PARK_IN_COVER,
            HPA_DISPLAY_ELEMENT_PARK_OUT_COVER,
            HPA_DISPLAY_ELEMENT_PARK_IN_COVER_SET,
            HPA_DISPLAY_ELEMENT_PARK_OUT_COVER_SET,
            HPA_PARKING_PROGRESS,
            HPA_CREATE_ROUTR_PROGRESS,
            HPA_MAP_BUILD_RESULT,
            HPA_ROUTER_MANAGE_DISPLAY,
            HPA_MAP_RENAME_NOTICE_CONTROL,
            HPA_PARK_IN_ROUTES_DELETE,
            HPA_PARK_OUT_ROUTES_DELETE,
            HPA_PARK_ROUTE_SYNC,
            HPA_DISPLAY_ELEMENT_VIEW_SWITCH,
            HPA_POP_VIEW_IS_REDUCE,
            APA_OBSTACLE_TRIGGER_SWITCH,
            PDC_FRONT_DISTANCE,
            PDC_REAR_DISTANCE,
            APA_PARKING_DOCK_DISABLE_REQUEST,
            HPA_PARKING_TEXT,
            PRKG_IMG_DISPLAY_STATUS,
            APA_HIGH_FREQUENCY_STATUS,
            HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP5,
            HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP6,
            HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP7,
            HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP8,
            HPA_DISPLAY_ELEMENT_MATCH_LOCAL_MAP9,
            APA_VIEW_TYPE,
            })
    @Retention(RetentionPolicy.SOURCE)
    @interface Functions {
    }
}
