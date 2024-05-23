package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class MisCamionesConductorCamion extends AppCompatActivity implements View.OnClickListener{
    Button btnCCMMCAInformacionCamionAsignado, btnCCMMCAVolver; // CCMMCA - Conductor Camion Menu Mis Camiones Asignados
    Spinner spiCCMMCAMisCamionesAsignados;
    TextView sa;
    int id =0;
    int idc =0;
    String placa;
    Usuario u;
    Camion c;
    daoUsuario daoUCCMMCA; //UCCMMCA - Usuario Conductor Camion Menu Mis Camiones Asignados
    daoCamion daoCCCMMCA; //CCCMMCA - Camion Conductor Camion Menu Mis Camiones Asignados

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_camiones_conductor_camion);
        sa = (TextView) findViewById(R.id.textView_CCMMCA_SesionActual);
        spiCCMMCAMisCamionesAsignados = (Spinner) findViewById(R.id.spinner_CCMMCA_MisCamionesAsignados);
        btnCCMMCAInformacionCamionAsignado = (Button) findViewById(R.id.button_CCMMCA_InformacionCamionAsignado);
        btnCCMMCAVolver = (Button) findViewById(R.id.button_CCMMCA_Volver);

        btnCCMMCAInformacionCamionAsignado.setOnClickListener(this);
        btnCCMMCAVolver.setOnClickListener(this);

        daoUCCMMCA = new daoUsuario(this);
        daoCCCMMCA = new daoCamion(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        u= daoUCCMMCA.getUsuarioById(id);

        List<String> listaDePlacas = daoCCCMMCA.getPlacasDeCamionesByIdConductor(id);
        if (listaDePlacas.isEmpty()) {
            listaDePlacas.add("No hay camiones disponibles");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDePlacas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiCCMMCAMisCamionesAsignados.setAdapter(adapter);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_CCMMCA_InformacionCamionAsignado) {
            String placaSeleccionada = spiCCMMCAMisCamionesAsignados.getSelectedItem().toString();
            int idCamion = daoCCCMMCA.obtenerIdCamionPorPlaca(placaSeleccionada);
            Camion camionSeleccionado = daoCCCMMCA.getCamionById(idCamion);

            if (camionSeleccionado != null) {
                Usuario propietario = daoUCCMMCA.getUsuarioById(camionSeleccionado.getIdPropietario());
                Usuario conductor = daoUCCMMCA.getUsuarioById(camionSeleccionado.getIdConductor());
                if (propietario != null && conductor != null) {
                    String mensaje = "Id Camión: " + camionSeleccionado.getId() +
                            "\nPropietario:" +
                            "\nNombre: " + propietario.getNombre() +
                            "\nCédula: " + propietario.getCedula() +
                            "\nConductor:" +
                            "\nNombre: " + conductor.getNombre() +
                            "\nCédula: " + conductor.getCedula() +
                            "\nViajes: " + camionSeleccionado.getViajes() +
                            "\nPlaca: " + camionSeleccionado.getPlaca() +
                            "\nMarca: " + camionSeleccionado.getMarca() +
                            "\nEstado: " + camionSeleccionado.getEstado() +
                            "\nUbicación: " + camionSeleccionado.getUbicacion();

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Detalle del Camión")
                            .setMessage(mensaje)
                            .setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    TextView textView = dialog.findViewById(android.R.id.message);
                    if (textView != null) {
                        textView.setTextSize(20);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se encontró información del camión asignado seleccionado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No se encontró información del camión asignado seleccionado", Toast.LENGTH_SHORT).show();
            }

        }else if (v.getId() == R.id.button_CCMMCA_Volver) {
            Intent iiccmca2 = new Intent(MisCamionesConductorCamion.this, InicioConductorCamion.class);
            iiccmca2.putExtra("Id", id);
            startActivity(iiccmca2);
            finish();
        }
    }
}