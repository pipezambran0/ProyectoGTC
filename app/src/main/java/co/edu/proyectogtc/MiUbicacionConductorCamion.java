package co.edu.proyectogtc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnSuccessListener;

public class MiUbicacionConductorCamion extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    GoogleMap MapCCMU;
    FusedLocationProviderClient fusedLocationClient;

    Button btnCCMUVolver, btnCCMUCompartirUbicacion;
    TextView edtCCMULatitud, edtCCMULongitud;
    TextView sa;
    int id = 0;
    int ids = 0;
    int idc = 0;
    Usuario u;
    Solicitud s;
    Camion c;
    daoUsuario daoUCCMU;
    daoSolicitud daoSCCMU;
    daoCamion daoCCCMU;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_ubicacion_conductor_camion);
        sa = findViewById(R.id.textView_CCMU_SesionActual);

        edtCCMULatitud = findViewById(R.id.textView_CCMU_Latitud);
        edtCCMULongitud = findViewById(R.id.textView_CCMU_Longitud);
        btnCCMUVolver = findViewById(R.id.button_CCMU_Volver);
        btnCCMUCompartirUbicacion = findViewById(R.id.button_CCMU_CompartirUbicacion);

        btnCCMUVolver.setOnClickListener(this);
        btnCCMUCompartirUbicacion.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            id = b.getInt("Id");
            idc = b.getInt("IdC");
        }
        daoUCCMU = new daoUsuario(this);
        u = daoUCCMU.getUsuarioById(id);

        daoCCCMU = new daoCamion(this);
        c = daoCCCMU.getCamionById(idc);


        if (u != null) {
            sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_CCMU_MiUbicacion);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Solicitar permisos de ubicación si no están concedidos
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapCCMU = googleMap;
        obtenerUbicacionActual();
    }

    private void obtenerUbicacionActual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permisos no concedidos, salir del método
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                            MapCCMU.addMarker(new MarkerOptions().position(miUbicacion).title("Mi Ubicación"));
                            MapCCMU.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion, 15));
                            edtCCMULatitud.setText(String.valueOf(location.getLatitude()));
                            edtCCMULongitud.setText(String.valueOf(location.getLongitude()));
                        } else {
                            edtCCMULatitud.setText("No se pudo obtener la ubicación");
                            edtCCMULongitud.setText("");
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, obtener la ubicación actual
                obtenerUbicacionActual();
            } else {
                // Permiso denegado, manejar adecuadamente
                edtCCMULatitud.setText("Permiso de ubicación denegado");
                edtCCMULongitud.setText("");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_CCMU_Volver) {
            Intent iccmu1 = new Intent(MiUbicacionConductorCamion.this, MisSolicitudesConductorCamion.class);
            iccmu1.putExtra("Id", id);
            startActivity(iccmu1);
            finish();
        } else if (v.getId() == R.id.button_CCMU_CompartirUbicacion) {
            c.setUbicacion(edtCCMULatitud.getText().toString() + "," + edtCCMULongitud.getText().toString());
            if(daoCCCMU.updateCamion(c)){
                Toast.makeText(this, "Ubicación Compartida", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this, "Ubicación No Compartida", Toast.LENGTH_SHORT).show();

        }
    }
}
