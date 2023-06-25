package ec.edu.tecnologicoloja.mapslocation;

import android.location.Location;
import android.location.LocationListener;

public class MyPosition implements LocationListener {
    public static double latitude, longitude, altitud;
    public static boolean statusGPS;

    public static Location coordenadas;

    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        coordenadas=location;
        altitud=location.getAltitude();
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderEnabled(String provider) {
        statusGPS=true;

    }

    @Override
    public void onProviderDisabled(String provider) {
        statusGPS=false;
    }
}
