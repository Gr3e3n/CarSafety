package ecarx.carext.userprofile;
/**
 * <pre>
 *     author  : KING.SUN
 *     e-mail  : xiaogangsun@ecarx.com.cn
 *     date    : 19-2-15
 *     desc    :
 *     version : 1.0
 * </pre>
 *
 * @Deprecated 仅用作V216/P417项目兼容，后续使用VehiclePropertyId扩展的方式进行定义
 */
@Deprecated
public interface IProfile {
    /**
     * The {@link IProfile} to JOSN String<br>
     * JSON String <b>KEY-VALUE</b> must be follow the rules<br>
     * <b>KEY</b>： FuncID|zone<br>
     *       e.g. <b>Function without zones</b>: String.valueOf(IHvac.HVAC_FUNC_AUTO)<br>
     *           <b>Function with zones</b>: String.valueOf(IHvac.HVAC_FUNC_AUTO_SEAT_VENTILATION)|String.valueOf(VehicleSeat#SEAT_ROW_1_LEFT)<br>
     * <b>VALUE</b>: Func value<br>
     *       e.g. value: String.valueOf(getProfileFuncValue(IHvac.HVAC_FUNC_AUTO, VehicleZone.ZONE_ALL))<br>
     *
     * @return JOSN String
     */
    String toJOSNString();
}
