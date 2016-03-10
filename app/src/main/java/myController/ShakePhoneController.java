package myController;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import myInterface.OnShakePhoneListener;

/**
 * Created by cooper on 2016.3.7.
 */
public class ShakePhoneController implements SensorEventListener {
    String TAG = "ShakePhoneController";
    // 传感器管理器
    private SensorManager sensorManager;
    // 传感器
    private Sensor sensor;
    // 重力感应监听器
    private OnShakePhoneListener onShakeListener;
    // 上下文
    private Context mContext;
    // 手机上一个位置时重力感应坐标
    private float lastX;
    private float lastY;
    private float lastZ;

    // 构造器
    public ShakePhoneController(Context context) {
        mContext = context;
        // 获得传感器管理器
        sensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            // 获得重力传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    /**
     * 开始检测
     */
    public void start() {

        // 注册
        if (sensor != null) {
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    /**
     * 停止检测
     */
    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        } else {
            Log.i(TAG, "bug sensorManager is null");
        }


    }

    // 设置重力感应监听器
    public void setOnShakeListener(OnShakePhoneListener listener) {
        onShakeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                    .abs(values[2]) > 17)) {
                onShakeListener.onShake();
//                Log.i("sensor x ", "============ values[0] = " + values[0]);
//                Log.i("sensor y ", "============ values[1] = " + values[1]);
//                Log.i("sensor z ", "============ values[2] = " + values[2]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
