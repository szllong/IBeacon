package cqhh.ibeacon;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import myController.BLEController;
import myController.HttpDataController;
import myController.ShakePhoneController;
import myInterface.DeviceEventListener;
import myInterface.HttpGetDataListener;
import myInterface.OnShakePhoneListener;
import myModel.ListData;
import myView.TextAdapter;

import static myTools.MyToolS.getRandomWelcomeTips;
import static myTools.MyToolS.getTime;


public class MainActivity extends Activity implements View.OnClickListener, HttpGetDataListener, DeviceEventListener, OnShakePhoneListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    HttpDataController httpDataController;
    private List<ListData> lists;
    private ListView listView;
    private EditText sendText;
    private Button sendButton;
    private TextAdapter textAdapter;


    //蓝牙
    private final static int RequestCode = 0;
    private BLEController bleController;

    //摇一摇
    private ShakePhoneController shakePhoneController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initBluetoothController();
        initShakePhone();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv);
        sendText = (EditText) findViewById(R.id.sendText);
        sendButton = (Button) findViewById(R.id.sendButton);
        lists = new ArrayList<>();
        sendButton.setOnClickListener(this);
        lists.add(new ListData(getRandomWelcomeTips(this), ListData.RECEIVE, getTime()));
        textAdapter = new TextAdapter(lists, this);
        listView.setAdapter(textAdapter);
    }

    private void initBluetoothController() {
        bleController = new BLEController(MainActivity.this);
        bleController.turnOnBluetooth(RequestCode);
        bleController.setDeviceEventListener(this);
    }

    private void initShakePhone() {
        shakePhoneController = new ShakePhoneController(this);
        shakePhoneController.setOnShakeListener(this);
    }

    @Override
    public void notifyDeviceFound(BluetoothDevice bluetoothDevice) {
        //contentStr = sendText.getText().toString();
        //将发送的字符转变为蓝牙地址
        Log.i(TAG, "in function notifyDeviceFound");
        String contentStr = "发现蓝牙设备的地址是" + bluetoothDevice.getAddress();
        //String newstr = contentStr.replace(" ", "");
        //将发送的内容变成蓝牙地址
        String newstr = contentStr;
        sendText.setText("");
        ListData listData = new ListData(contentStr, ListData.SEND, getTime());
        lists.add(listData);
        textAdapter.notifyDataSetChanged();
        try {
            httpDataController = new HttpDataController(new URL("http://www.tuling123.com/openapi/api?key=997ed7fa8b069871c0d28e61ca03f1af&info=" + newstr), this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        httpDataController.execute();

    }

    @Override
    public void getDataUrl(String data) {
        Log.i(TAG, "in function getDataUrl");
        parseText(data);
    }

    public void parseText(String str) {
        try {
            JSONObject jb = new JSONObject(str);
            ListData listData = new ListData(jb.getString("text"), ListData.RECEIVE, getTime());
            lists.add(listData);
            textAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onShake() {
        //摇一摇开始搜索蓝牙
        bleController.startLeScan();
    }

    @Override
    public void onClick(View v) {
        lists.clear();
        textAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        shakePhoneController.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shakePhoneController.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shakePhoneController.stop();
    }
}
