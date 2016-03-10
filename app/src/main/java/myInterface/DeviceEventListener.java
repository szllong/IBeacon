package myInterface;

import android.bluetooth.BluetoothDevice;

/**
 * Created by cooper on 2016.3.7. the file is in project IBeacon in pagekage ${PACKAGE}
 */
public interface DeviceEventListener {
    /**
     * 通知找到蓝牙设备
     *
     * @param devices
     */
    void notifyDeviceFound(BluetoothDevice devices);
}
