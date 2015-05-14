package com.example.wifidemo;

import java.util.List;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    private TextView allNetWork;
    private Button scan;
    private Button start;
    private Button stop;
    private Button check;
    private WifiAdmin mWifiAdmin;
    // 扫描结果列表
    private List<ScanResult> list;
    private ScanResult mScanResult;
    private StringBuffer sb = new StringBuffer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWifiAdmin = new WifiAdmin(MainActivity.this);
        init();
    }

    public void init() {
        allNetWork = (TextView) findViewById(R.id.allNetWork);
        scan = (Button) findViewById(R.id.scan);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        check = (Button) findViewById(R.id.check);
        scan.setOnClickListener(new MyListener());
        start.setOnClickListener(new MyListener());
        stop.setOnClickListener(new MyListener());
        check.setOnClickListener(new MyListener());
    }

    private class MyListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
            case R.id.scan:// 扫描网络
                getAllNetWorkList();
                break;
            case R.id.start:// 打开Wifi
                mWifiAdmin.openWifi();
                Toast.makeText(MainActivity.this,
                        "当前wifi状态为：" + mWifiAdmin.checkState(), 1).show();
                break;
            case R.id.stop:// 关闭Wifi
                mWifiAdmin.closeWifi();
                Toast.makeText(MainActivity.this,
                        "当前wifi状态为：" + mWifiAdmin.checkState(), 1).show();
                break;
            case R.id.check:// Wifi状态
                Toast.makeText(MainActivity.this,
                        "当前wifi状态为：" + mWifiAdmin.checkState() + 
                        " IP:"+((mWifiAdmin.getIpAddress() & 0xFF000000)>>24), 1).show();              
                break;
            default:
                break;
            }
        }
    }

    public void getAllNetWorkList() {
        // 每次点击扫描之前清空上一次的扫描结果
        if (sb != null) {
            sb = new StringBuffer();
        }
        // 开始扫描网络
        mWifiAdmin.startScan();
        list = mWifiAdmin.getWifiList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                // 得到扫描结果
                mScanResult = list.get(i);
                sb = sb.append(mScanResult.BSSID + "  ")
                        .append(mScanResult.SSID + "   ")
                        .append(mScanResult.capabilities + "   ")
                        .append(mScanResult.frequency + "   ")
                        .append(mScanResult.level + "\n\n");
            }
            allNetWork.setText("扫描到的wifi网络：\n" + sb.toString());
        }
    }
}
