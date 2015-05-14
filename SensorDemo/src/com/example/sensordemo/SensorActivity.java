package com.example.sensordemo;

import java.util.List;  

import android.app.Activity;  
import android.content.Context;  
import android.hardware.Sensor;  
import android.hardware.SensorEvent;  
import android.hardware.SensorEventListener;  
import android.hardware.SensorManager;  
import android.os.Bundle;  
import android.util.Log;
import android.widget.TextView;  
    
public class SensorActivity extends Activity {
	private static final String Tag = "SensorActivity";
    Sensor sensor ; 
    TextView tx1;
    private float x, y, z;  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activiyt_sensor);  
    
        //׼����ʾ��Ϣ��UI�齨
        tx1 = (TextView) findViewById(R.id.textView1); 
    
        //��ϵͳ�����л�ô�����������  
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  
    
        //�Ӵ������������л��ȫ���Ĵ������б�  
        List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);  

        //��ʾ�ж��ٸ�������  
        tx1.setText("�������ֻ���" + allSensors.size() + "�������������Ƿֱ��ǣ�\n");  
    
        //��ʾÿ���������ľ�����Ϣ  
        for (Sensor s : allSensors) {  
    
            String tempString = "\n" + "  �豸���ƣ�" + s.getName() + "\n" + "  �豸�汾��" + s.getVersion() + "\n" + "  ��Ӧ�̣�"  
                    + s.getVendor() + "\n";  
    
            switch (s.getType()) {  
            case Sensor.TYPE_ACCELEROMETER:  
                tx1.setText(tx1.getText().toString() + s.getType() + " ���ٶȴ�����accelerometer" + tempString);  
                break;  
            case Sensor.TYPE_GRAVITY:  
                tx1.setText(tx1.getText().toString() + s.getType() + " ����������gravity API 9" + tempString);  
                break;
            case Sensor.TYPE_GYROSCOPE:  
                tx1.setText(tx1.getText().toString() + s.getType() + " �����Ǵ�����gyroscope" + tempString);  
                break;  
            case Sensor.TYPE_LIGHT:  
                tx1.setText(tx1.getText().toString() + s.getType() + " �������ߴ�����light" + tempString);  
                break;  
            case Sensor.TYPE_LINEAR_ACCELERATION:  
                tx1.setText(tx1.getText().toString() + s.getType() + " ���Լ�����LINEAR_ACCELERATION API 9" + tempString);  
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:  
                tx1.setText(tx1.getText().toString() + s.getType() + " ��ų�������magnetic field" + tempString);  
                break;  
            case Sensor.TYPE_PRESSURE:  
                tx1.setText(tx1.getText().toString() + s.getType() + " ѹ��������pressure" + tempString);  
                break;  
            case Sensor.TYPE_PROXIMITY:  
                tx1.setText(tx1.getText().toString() + s.getType() + " ���봫����proximity" + tempString);  
                break;  
            case Sensor.TYPE_ROTATION_VECTOR:  
                tx1.setText(tx1.getText().toString() + s.getType() + " ��ת����ROTATION" + tempString);  
                break; 
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                tx1.setText(tx1.getText().toString() + s.getType() + " �¶ȴ�����temperature" + tempString);  
                break;  
            default:
                tx1.setText(tx1.getText().toString() + s.getType() + " δ֪������" + tempString);  
                break;
            }
        }  
             
        //��title����ʾ�����������ı仯  
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  
        SensorEventListener lsn = new SensorEventListener() {  
            public void onSensorChanged(SensorEvent e) {  
                x = e.values[0];  
                y = e.values[1];  
                z = e.values[2];  
                setTitle("x=" + (int) x + "," + "y=" + (int) y + "," + "z="+ (int) z);  
            }
    
            public void onAccuracyChanged(Sensor s, int accuracy) {  
            }  
        };
        // ע��listener�������������Ǽ��ľ�ȷ��  
        sm.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);  
    
    }  
} 