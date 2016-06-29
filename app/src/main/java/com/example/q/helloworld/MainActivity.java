package com.example.q.helloworld;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    int asdf = 1234;
    public String[] contacts = {"Lee Seungwoo",
            "Jung Taeyoung",
            "Gimun"};

    //Supports for Motion sensor
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean mSensorAvailable;

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float x= -1.0f * event.values[0]; // gravity force to right is positive
        float y= 1.0f * event.values[1]; // gravity force to down is positive
        TextView textV=(TextView)findViewById(R.id.textTabC);
        textV.setText("x : "+x+"\ny : "+y);
    }
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(mSensorAvailable)
            mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mSensorAvailable)
            mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Change in Sphere///
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        {
            TabSpec spec1 = tabHost.newTabSpec("Tab A").setContent(R.id.linearLayout).setIndicator("Tab AA");
            ArrayAdapter<String> Adapter;
            Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            ListView list = (ListView) findViewById(R.id.listView);
            list.setAdapter(Adapter);
//        list.setOnItemClickListener((AdapterView.OnItemClickListener) this);
            tabHost.addTab(spec1);
        }

        {
            TabSpec spec2 = tabHost.newTabSpec("Tab B").setContent(R.id.linearLayout2).setIndicator("Tab BB");
            tabHost.addTab(spec2);
        }

        {
            TabSpec spec3 = tabHost.newTabSpec("Tab C").setContent(R.id.linearLayout3).setIndicator("Tab CC");

            //Initializing sensors
            mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
            mSensor=null;
            mSensorAvailable=false; // Init value. It will be turned on when we find google sensor.
            if(mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)!=null) {
//                List<Sensor> sensors=mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
//                for(int i=0;i<sensors.size();i++) {
//                    if(sensors.get(i).getVendor().contains("Google")) {
//                        System.out.println("============ we found a google gravity sensor");
//                        //We found a google gravity sensor.
//                        mSensor=sensors.get(i);
//                        mSensorAvailable=true;
//                        break;
//                    }
//                }
                mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                mSensorAvailable=true;
            }
            if(!mSensorAvailable) {
                TextView textV=(TextView)findViewById(R.id.textTabC);
                textV.setText("Unable to use the sensor");
            }

            tabHost.addTab(spec3);
        }

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 80;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 80;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = 80;
    }
}
