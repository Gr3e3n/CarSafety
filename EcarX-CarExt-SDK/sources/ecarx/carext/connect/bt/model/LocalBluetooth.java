/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.bt.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *Local Bluetooth device properties
 *
 * @since 2
 */
public class LocalBluetooth implements Parcelable {
    /**
     * Bluetooth switch status -- ON
     */
    public static final int ON = 1;
    /**
     * Bluetooth switch status -- TURNING_ON
     */
    public static final int TURNING_ON = 2;
    /**
     * Bluetooth switch status -- TURNING_OFF
     */
    public static final int TURNING_OFF = 3;
    /**
     * Bluetooth switch status -- OFF
     */
    public static final int OFF = 4;
    /**
     *Bluetooth switch status
     */
    private int mEnableStatus=OFF;
    /**
     * Local device name
     */
    private String mName;
    /**
     *Local device MAC address
     */
    private String mMacAddress;
    /**
     * Profile supported by screen Bluetooth module
     * eg:0x01:A2DP 0x02:AVRCP 0x03:A2DP+AVRCP
     */
    private int mProfile;
    /**
     * Can it be discovered
     */
    private boolean isDiscovery;

    public LocalBluetooth() {
    }

    protected LocalBluetooth(Parcel in) {
        this.mEnableStatus = in.readInt();
        this.mName = in.readString();
        this.mMacAddress = in.readString();
        this.mProfile = in.readInt();
        this.isDiscovery=in.readByte() != 0;

    }

    public int getmEnableStatus() {
        return mEnableStatus;
    }

    public void setmEnableStatus(int mEnableStatus) {
        this.mEnableStatus = mEnableStatus;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmMacAddress() {
        return mMacAddress;
    }

    public void setmMacAddress(String mMacAddress) {
        this.mMacAddress = mMacAddress;
    }

    public int getProfile() {
        return mProfile;
    }

    public void setProfile(int profile) {
        mProfile = profile;
    }


    public boolean isEnable(){
        return mEnableStatus == ON;
    }
    public boolean isDiscovery() {
        return isDiscovery;
    }

    public void setDiscovery(boolean discovery) {
        isDiscovery = discovery;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mEnableStatus);
        dest.writeString(mName);
        dest.writeString(mMacAddress);
        dest.writeInt(mProfile);
        dest.writeByte(this.isDiscovery ? (byte) 1 : (byte) 0);

    }

    public static final Creator<LocalBluetooth> CREATOR = new Creator<LocalBluetooth>() {
        @Override
        public LocalBluetooth createFromParcel(Parcel source) {
            return new LocalBluetooth(source);
        }

        @Override
        public LocalBluetooth[] newArray(int size) {
            return new LocalBluetooth[size];
        }
    };

}
