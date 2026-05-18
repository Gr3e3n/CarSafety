/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.connect.bt.model;

import android.bluetooth.BluetoothClass;
import android.os.Parcel;
import android.os.Parcelable;

/**
 *  Bluetooth Device Attribute Object
 *
 *  @since 2
 */
public class BtDeviceInfo implements Parcelable {

    /**
     * Bluetooth protocol connection status -- CONNECTING
     */
    public static final int CONNECTING=1;
    /**
     * Bluetooth protocol connection status -- CONNECTED
     */
    public static final int CONNECTED=2;
    /**
     * Bluetooth protocol connection status -- DISCONNECTING
     */
    public static final int DISCONNECTING=3;
    /**
     * Bluetooth protocol connection status -- DISCONNECTED
     */
    public static final int DISCONNECTED=4;
    /**
     * Bluetooth device MAC address
     */
    private String mMacAddress;
    /**
     * {@link android.bluetooth.BluetoothClass}
     */
    public BluetoothClass mBluetoothClass;
    /**
     * Bluetooth device name
     */
    public String mName;
    /**
     * Bluetooth device battery level
     */
    public int mBtPower;
    /**
     * Bluetooth device volume
     */
    private int mVolume;
    /**
     * Whether Bluetooth bound
     */
    private boolean mBounded;
    /**
     * Advanced Audio Distribution Profile connection status
     *
     */
    private int mA2dpConnectStatus = DISCONNECTED;
    /**
     * Audio/VideoRemote Control Profile connection status
     */
    private int mAvrCpConnectStatus = DISCONNECTED;
    /**
     * Whether support volume adjustment
     */
    private boolean mSupportVolumeCtrl;

    public BtDeviceInfo() {
    }

    protected BtDeviceInfo(Parcel in) {
        this.mMacAddress = in.readString();
        this.mBluetoothClass = in.readParcelable(BluetoothClass.class.getClassLoader());
        this.mName = in.readString();
        this.mBtPower = in.readInt();
        this.mVolume = in.readInt();
        this.mBounded = in.readByte() != 0;
        this.mA2dpConnectStatus= in.readInt();
        this.mAvrCpConnectStatus = in.readInt();
        this.mSupportVolumeCtrl = in.readByte() != 0;
    }

    public String getmMacAddress() {
        return mMacAddress;
    }

    public void setmMacAddress(String mMacAddress) {
        this.mMacAddress = mMacAddress;
    }

    public BluetoothClass getmBluetoothClass() {
        return mBluetoothClass;
    }

    public void setmBluetoothClass(BluetoothClass mBluetoothClass) {
        this.mBluetoothClass = mBluetoothClass;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmBtPower() {
        return mBtPower;
    }

    public void setmBtPower(int mBtPower) {
        this.mBtPower = mBtPower;
    }

    public int getmVolume() {
        return mVolume;
    }

    public void setmVolume(int mVolume) {
        this.mVolume = mVolume;
    }

    public boolean isConnected(){
        return getA2dpConnectStatus()==CONNECTED;
    }

    public boolean isDisConnected(){
        return getA2dpConnectStatus()==DISCONNECTED;
    }

    public boolean isAvrCpConnected(){
        return getAvrcpConnectStatus()==CONNECTED;
    }

    public boolean isBounded() {
        return mBounded;
    }

    public void setBounded(boolean bounded) {
        this.mBounded = bounded;
    }

    public  boolean isSameMacAddress(String macAddress){
        return this.mMacAddress != null && this.mMacAddress.equalsIgnoreCase(
                macAddress);
    }

    public int getA2dpConnectStatus() {
        return mA2dpConnectStatus;
    }

    public void setA2dpConnectStatus(int a2dpConnectStatus) {
        this.mA2dpConnectStatus = a2dpConnectStatus;
    }

    public int getAvrcpConnectStatus() {
        return mAvrCpConnectStatus;
    }
    public void setAvrcpConnectStatus(int avrcpConnectStatus) {
        this.mAvrCpConnectStatus = avrcpConnectStatus;
    }

    public int getConnectStatus(){
        int a2dp=getA2dpConnectStatus();
        if(a2dp==CONNECTING){
            return CONNECTING;
        }else if(a2dp==CONNECTED){
            int avrcp=getAvrcpConnectStatus();
            if(avrcp==CONNECTED){
                return CONNECTED;
            }else {
                return CONNECTING;
            }
        }else if(a2dp==DISCONNECTING){
            return DISCONNECTING;
        }else {
            //disconnected
            return DISCONNECTED;
        }
    }

    public boolean isSupportVolumeCtrl() {
        return mSupportVolumeCtrl;
    }

    public void setSupportVolumeCtrl(boolean supportVolumeCtrl) {
        this.mSupportVolumeCtrl = supportVolumeCtrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mMacAddress);
        dest.writeParcelable(this.mBluetoothClass, flags);
        dest.writeString(this.mName);
        dest.writeInt(this.mBtPower);
        dest.writeInt(this.mVolume);
        dest.writeByte(this.mBounded ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mA2dpConnectStatus);
        dest.writeInt(this.mAvrCpConnectStatus);
        dest.writeByte(this.mSupportVolumeCtrl ? (byte) 1 : (byte) 0);
    }

    public static final Creator<BtDeviceInfo> CREATOR = new Creator<BtDeviceInfo>() {
        @Override
        public BtDeviceInfo createFromParcel(Parcel source) {
            return new BtDeviceInfo(source);
        }

        @Override
        public BtDeviceInfo[] newArray(int size) {
            return new BtDeviceInfo[size];
        }
    };

    @Override
    public String toString() {
        return "BtDeviceInfo{" +
                "mMacAddress='" + mMacAddress + '\'' +
                ", mBluetoothClass=" + mBluetoothClass +
                ", mName='" + mName + '\'' +
                ", mBtPower=" + mBtPower +
                ", mSupportVolumeCtrl=" + mSupportVolumeCtrl +
                ", mVolume=" + mVolume +
                '}';
    }

        @Override
        public boolean equals(Object o) {
            if (this == o){ return true;}
            if (o == null){ return false;}
            BtDeviceInfo device = (BtDeviceInfo) o;
            if (mMacAddress == null){
                return false;
            }
            return mMacAddress.equals(device.mMacAddress);
        }

        @Override
        public int hashCode() {
            return mMacAddress.hashCode();
        }

    }
