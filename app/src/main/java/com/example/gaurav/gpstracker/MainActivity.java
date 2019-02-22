package com.example.gaurav.gpstracker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private LocationManager lm;
    private LocationHelper lh;
    private CustomView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lh = new LocationHelper();

        graph = findViewById(R.id.graph);
        lh.addObserver(graph);

        ((Button)findViewById(R.id.button)).setText("start");
        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (button.getText().toString() == "start") {
                    if (addLocationListener()) {
                        graph.reset();
                        button.setText("stop");
                    }
                } else {
                    removeLocationListener();
                    button.setText("start");
                }
            }
        });

        ((Button)findViewById(R.id.button2)).setText("show time");
        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (button.getText().toString() == "show time") {
                    button.setText("show speed");
                    graph.setMetric(1);
                } else {
                    button.setText("show time");
                    graph.setMetric(0);
                }
            }
        });
    }

    private boolean addLocationListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 0);
            return false;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, lh);
        return true;
    }

    private void removeLocationListener() {
        lm.removeUpdates(lh);
    }
}
