package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.util.List;

public class MisSolicitudesPropietarioCamion extends AppCompatActivity implements View.OnClickListener{
    Button btnPCMMSVolver, btnPCMMSAsignarCamion, btnPCMMSChatSolicitud, btnPCMMSInformacionSolicitud; //PCMMS Propietario Camion Menu Mis Solicitudes
    Spinner spiPCMMSMisSolicitudes;
    TextView sa;

    int id=0;
    int ids=0;
    Usuario u;
    Solicitud s;
    daoUsuario daoUPCMMS; // UPCMMS Usuario Propietario Camion Menu Mis Solicitudes
    daoSolicitud daoSPCMMS; // SPCMMS Solicitud Propietario Camion Menu Mis Solicitudes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_solicitudes_propietario_camion);

        sa = (TextView) findViewById(R.id.textView_PCMMS_SesionActual);
        spiPCMMSMisSolicitudes = (Spinner) findViewById(R.id.spinner_PCMMS_MisSolicitudes);

        btnPCMMSAsignarCamion = (Button) findViewById(R.id.button_PCMMS_AsignarCamion);
        btnPCMMSChatSolicitud = (Button) findViewById(R.id.button_PCMMS_ChatSolicitud);
        btnPCMMSInformacionSolicitud = (Button) findViewById(R.id.button_PCMMS_InformacionSolicitud);
        btnPCMMSVolver = (Button) findViewById(R.id.button_PCMMS_Volver);

        btnPCMMSAsignarCamion.setOnClickListener(this);
        btnPCMMSChatSolicitud.setOnClickListener(this);
        btnPCMMSInformacionSolicitud.setOnClickListener(this);
        btnPCMMSVolver.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoUPCMMS = new daoUsuario(this);
        u= daoUPCMMS.getUsuarioById(id);

        daoSPCMMS = new daoSolicitud(this);

        List<String> listaDeNombreSolicitudes = daoSPCMMS.getNombreSolicitudByIdPropietarioS(id);
        if (listaDeNombreSolicitudes.isEmpty()) {
            listaDeNombreSolicitudes.add("No hay solicitudes disponibles");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDeNombreSolicitudes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiPCMMSMisSolicitudes.setAdapter(adapter);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_PCMMS_AsignarCamion) {
            ids = daoSPCMMS.obtenerIdSolicitudByNombreSolicitud(spiPCMMSMisSolicitudes.getSelectedItem().toString());
            Intent ipcmms1 = new Intent(MisSolicitudesPropietarioCamion.this, AsignarCamion.class);
            ipcmms1.putExtra("Id", id);
            ipcmms1.putExtra("IdS", ids);
            startActivity(ipcmms1);

        }else if (v.getId() == R.id.button_PCMMS_InformacionSolicitud) {
            String nombreSolicitudSeleccionada = spiPCMMSMisSolicitudes.getSelectedItem().toString();
            int idSolicitud = daoSPCMMS.obtenerIdSolicitudByNombreSolicitud(nombreSolicitudSeleccionada);
            Solicitud solicitudSeleccionada = daoSPCMMS.getSolicitudById(idSolicitud);

            if (solicitudSeleccionada != null) {
                String mensaje = "Id Solicitud: " + solicitudSeleccionada.getId() +
                        "\nLugar Carga: " + solicitudSeleccionada.getLugarCarga() +
                        "\nLugar Descarga: " + solicitudSeleccionada.getLugarDescarga() +
                        "\nFecha Carga: " + solicitudSeleccionada.getFechaCarga() +
                        "\nFecha Descarga: " + solicitudSeleccionada.getFechaDescarga() +
                        "\nTipo Material: " + solicitudSeleccionada.getTipoMaterial() +
                        "\nPeso Material: " + solicitudSeleccionada.getPesoMaterial() +
                        "\nEstado Solicitud: " + solicitudSeleccionada.getEstadoSolicitud() +
                        "\nId Propietario: " + solicitudSeleccionada.getIdPropietarioS() +
                        "\nId Conductor: " + solicitudSeleccionada.getIdConductorS() +
                        "\nPlaca Camion: " + solicitudSeleccionada.getPlacaCamion() +
                        "\nCalificacion: " + solicitudSeleccionada.getCalificacionCliente();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Detalle de la Solicitud")
                        .setMessage(mensaje)
                        .setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();

                TextView textView = dialog.findViewById(android.R.id.message);
                if (textView != null) {
                    textView.setTextSize(20);
                }
            } else {
                Toast.makeText(getApplicationContext(), "No se encontró información de la solicitud seleccionada", Toast.LENGTH_SHORT).show();
            }

        }else if (v.getId() == R.id.button_PCMMS_ChatSolicitud) {
            ids = daoSPCMMS.obtenerIdSolicitudByNombreSolicitud(spiPCMMSMisSolicitudes.getSelectedItem().toString());
            Intent ipcmms3 = new Intent(MisSolicitudesPropietarioCamion.this, ChatNotificaciones.class);
            ipcmms3.putExtra("Id", id);
            ipcmms3.putExtra("IdS", ids);
            startActivity(ipcmms3);

        }else if (v.getId() == R.id.button_PCMMS_Volver) {
            Intent ipcmms4 = new Intent(MisSolicitudesPropietarioCamion.this, InicioPropietarioCamion.class);
            ipcmms4.putExtra("Id", id);
            startActivity(ipcmms4);
            finish();
        }
    }
}