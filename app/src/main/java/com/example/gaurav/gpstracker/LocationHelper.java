package com.example.gaurav.gpstracker;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Pair;
import java.util.Observable;

public class LocationHelper extends Observable implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        setChanged();
        notifyObservers(new Pair<>(location, SystemClock.elapsedRealtime()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
