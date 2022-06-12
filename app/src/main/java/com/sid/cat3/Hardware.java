package com.sid.cat3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

public class Hardware extends AppCompatActivity {
    ImageView iv;
    Button tp;
    Button b1,b2,b3;
    Button w1,w2;
    SensorManager sm = null;
    TextView textView1 = null;
    List list;
    SensorEventListener sel = new SensorEventListener(){
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            textView1.setText("x: "+values[0]+"\ny: "+values[1]+"\nz: "+values[2]);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);

        textView1 = (TextView)findViewById(R.id.sensorresults);

        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(list.size()>0){
            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }

        iv=findViewById(R.id.imageview);
        tp=findViewById(R.id.takepic);
        b1=findViewById(R.id.on);
        b2=findViewById(R.id.off);
        b3=findViewById(R.id.disc);
        w1=findViewById(R.id.won);
        w2=findViewById(R.id.woff);

        w1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);


            }
        });
        w2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(false);

            }
        });

        BluetoothAdapter bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE),0);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    bluetoothAdapter.disable();
                }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(bluetoothAdapter.ACTION_REQUEST_DISCOVERABLE),0);

            }
        });



        tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE),1888);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1888){
            Bitmap image= (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(image);


        }
    }
}