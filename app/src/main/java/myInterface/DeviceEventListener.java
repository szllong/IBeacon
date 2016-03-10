package myInterface;

import android.bluetooth.BluetoothDevice;

import java.util.List;

/**
 * Created by cooper on 2016.3.7.
 */
public interface DeviceEventListener {
    /**
     * 通知找到蓝牙设备
     * @param devices
     */
    public void notifyDeviceFound(BluetoothDevice devices);
}
