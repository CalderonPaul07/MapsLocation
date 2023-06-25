package ec.edu.tecnologicoloja.mapslocation;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ec.edu.tecnologicoloja.mapslocation.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    ImageButton IMG_Ubicar;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    EditText tf_latitud, tf_longitud;
    double longitud, latitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableMyLocation();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tf_latitud = findViewById(R.id.ET_latitud);
        tf_longitud = findViewById(R.id.ET_longitud);
        IMG_Ubicar = findViewById(R.id.imagemylocation);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        IMG_Ubicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MiPosicion();
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void MiPosicion(){
        LocationManager objLocation=null;
        LocationListener objLocListener;
        objLocation=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objLocListener=new MyPosition();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 1000);
        }

        objLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,objLocListener);

        if (objLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if (MyPosition.latitude !=0){
                latitud=MyPosition.latitude;
                longitud=MyPosition.longitude;
                tf_longitud.setText(longitud+"");
                tf_latitud.setText(latitud+"");
//                Se declara una variable de tipo LatLng (Latitud, Longitud) y en ella se instancia los valores obtenidos de mi posicion
                LatLng miPosicio = new LatLng(latitud, longitud);
//                Se agrega esa variable latlng en posicion y se agrega un nombre a esa marca
                mMap.addMarker(new MarkerOptions().position(miPosicio).title("Mi casa"));
//                Se realiza un zoom de 16.0 al momento de obtener posicion
                float zoomLevel = 16.0f;
//                De acuerdo a donde se este mueve el mapa hacia los valores determinados antes y se agrega el zoom
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(miPosicio, zoomLevel));

            }

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        Botón zoom
//        mMap.getUiSettings().setZoomControlsEnabled(true);
        MiPosicion();

    }

    public void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        }
    }
}