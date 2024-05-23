package co.edu.proyectogtc;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class RutaSugerida extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    GoogleMap MapREC;
    FusedLocationProviderClient fusedLocationClient;

    Button btnCCRSVolver;
    TextView edtCCRSLugar1, edtCCRSLugar2, edtCCRSDistancia, sa;
    LatLng punto1, punto2;

    int id=0;
    int ids=0;
    Usuario u;
    Solicitud s;
    daoUsuario daoUCCRS; // UCCRS Usuario Conductor Camion Ruta Sugerida
    daoSolicitud daoSCCRS; // SCCRS Solicitud Conductor Camion Ruta Sugerida

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruta_sugerida);
        sa = findViewById(R.id.textView_CCRS_SesionActual);

        edtCCRSLugar1 = findViewById(R.id.textView_CCRS_Lugar1);
        edtCCRSLugar2 = findViewById(R.id.textView_CCRS_Lugar2);
        edtCCRSDistancia = findViewById(R.id.textView_CCRS_Distancia);
        btnCCRSVolver = findViewById(R.id.button_CCRS_Volver);

        btnCCRSVolver.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            id = b.getInt("Id");
            ids = b.getInt("IdS");
        }
        daoUCCRS = new daoUsuario(this);
        u = daoUCCRS.getUsuarioById(id);

        daoSCCRS = new daoSolicitud(this);
        s = daoSCCRS.getSolicitudById(ids);

        if (u != null) {
            sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
        }

        String l1 = s.getLugarCarga();
        String[] pl1 = l1.split(",");
        double latitud1 = Double.parseDouble(pl1[0]);
        double longitud1 = Double.parseDouble(pl1[1]);

        String l2 = s.getLugarDescarga();
        String[] pl2 = l2.split(",");
        double latitud2 = Double.parseDouble(pl2[0]);
        double longitud2 = Double.parseDouble(pl2[1]);

        punto1 = new LatLng(latitud1, longitud1);
        punto2 = new LatLng(latitud2, longitud2);

        edtCCRSLugar1.setText("Punto de Carga: " + punto1.latitude + ", " + punto1.longitude);
        edtCCRSLugar2.setText("Punto de Descarga: " + punto2.latitude + ", " + punto2.longitude);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_CCRS_Recorrido);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapREC = googleMap;
        mostrarRecorrido();
    }

    private void mostrarRecorrido() {
        MapREC.addMarker(new MarkerOptions().position(punto1).title("Punto de Carga"));
        MapREC.addMarker(new MarkerOptions().position(punto2).title("Punto de Descarga"));
        MapREC.moveCamera(CameraUpdateFactory.newLatLngZoom(punto1, 10));

//        PolylineOptions polylineOptions = new PolylineOptions().add(punto1).add(punto2).color(0xFF00FF00).width(5);
//        MapREC.addPolyline(polylineOptions);

        float[] results = new float[1];
        Location.distanceBetween(punto1.latitude, punto1.longitude, punto2.latitude, punto2.longitude, results);
        float distanciaEnMetros = results[0];
        edtCCRSDistancia.setText("Distancia: " + distanciaEnMetros + " metros");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mostrarRecorrido();
            } else {
                edtCCRSDistancia.setText("Permiso de ubicación denegado");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_CCRS_Volver) {
            Intent iccrs1 = new Intent(RutaSugerida.this, MisSolicitudesConductorCamion.class);
            iccrs1.putExtra("Id", id);
            startActivity(iccrs1);
            finish();
        }
    }
}