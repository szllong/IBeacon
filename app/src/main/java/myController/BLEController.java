package myController;

/**
 * Created by cooper on 2016.3.7. the file is in project IBeacon in pagekage ${PACKAGE}
 */


import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import myInterface.DeviceEventListener;

public class BLEController extends Service {

    private final static String TAG = BLEController.class.getSimpleName();
    private BluetoothAdapter mBluetoothAdapter;
    private List<BluetoothDevice> mDevices = new ArrayList<>();
    private DeviceEventListener mDeviceEventListener;

    // 上次检测时间
    private long lastUpdateTime;
    // 两次检测的时间间隔
    private static final int UPTATE_INTERVAL_TIME = 1000;

    private Handler mHandler;
    //开始停止扫描时间间隔
    private static final int StopScanInterval = 5000;

    private Activity activity;

    public BLEController(Activity ac) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        assert (mBluetoothAdapter != null);
        mHandler = new Handler();
        activity = ac;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * @param deviceEventListener
     */
    public void setDeviceEventListener(DeviceEventListener deviceEventListener) {
        mDeviceEventListener = deviceEventListener;
    }


    /**
     * 打开蓝牙
     *
     * @param requestCode
     */
    public void turnOnBluetooth(int requestCode) {
        assert (mBluetoothAdapter != null);
        if (!mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 开始搜索
     */
    public void startLeScan() {
        // 现在检测时间
        long currentUpdateTime = System.currentTimeMillis();
        // 两次检测的时间间隔
        long timeInterval = currentUpdateTime - lastUpdateTime;
        // 判断是否达到了检测时间间隔
        if (timeInterval < UPTATE_INTERVAL_TIME)
            return;
        // 现在的时间变成last时间
        lastUpdateTime = currentUpdateTime;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopLeScan();
            }
        }, StopScanInterval);
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    public void stopLeScan() {
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            if (!mDevices.contains(device)) {
                Log.i(TAG, "Find a new BLE Device: name = " + device.getName() + ",mac = " + device.getAddress() + ",rssi = " + rssi + ",scanRecord.length = " + scanRecord.length);
                mDevices.add(device);
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mDeviceEventListener != null) {
                        mDeviceEventListener.notifyDeviceFound(device);
                    }
                }
            });
            //runOnUiThread
        }
    };


    public List<BluetoothDevice> getDeviceList() {
        return mDevices;
    }


}
