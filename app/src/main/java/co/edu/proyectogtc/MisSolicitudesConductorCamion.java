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

public class MisSolicitudesConductorCamion extends AppCompatActivity implements View.OnClickListener{
    Button btnCCMMSVolver, btnCCMMSMiUbicacion, btnCCMMSRutaSugerida, btnCCMMSChatSolicitud, btnCCMMSInformacionSolicitud; //CCMMS Conductor Camion Menu Mis Solicitudes
    Spinner spiCCMMSMisSolicitudes;
    TextView sa;

    int id=0;
    int ids=0;
    int idc=0;
    Usuario u;
    Solicitud s;
    Camion c;
    daoUsuario daoUCCMMS; // UCCMMS Usuario Conductor Camion Menu Mis Solicitudes
    daoSolicitud daoSCCMMS; // SCCMMS Solicitud Conductor Camion Menu Mis Solicitudes
    daoCamion daoCCCMMS; //  CCCMMS Camion Conductor Camion Menu Mis Solicitudes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_solicitudes_conductor_camion);
        sa = (TextView) findViewById(R.id.textView_CCMMS_SesionActual);
        spiCCMMSMisSolicitudes = (Spinner) findViewById(R.id.spinner_CCMMS_MisSolicitudes);

        btnCCMMSMiUbicacion = (Button) findViewById(R.id.button_CCMMS_MiUbicacion);
        btnCCMMSRutaSugerida = (Button) findViewById(R.id.button_CCMMS_RutaSugerida);
        btnCCMMSInformacionSolicitud = (Button) findViewById(R.id.button_CCMMS_InformacionSolicitud);
        btnCCMMSChatSolicitud = (Button) findViewById(R.id.button_CCMMS_ChatSolicitud);
        btnCCMMSVolver = (Button) findViewById(R.id.button_CCMMS_Volver);

        btnCCMMSMiUbicacion.setOnClickListener(this);
        btnCCMMSRutaSugerida.setOnClickListener(this);
        btnCCMMSInformacionSolicitud.setOnClickListener(this);
        btnCCMMSChatSolicitud.setOnClickListener(this);
        btnCCMMSVolver.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoUCCMMS = new daoUsuario(this);
        u= daoUCCMMS.getUsuarioById(id);

        daoSCCMMS = new daoSolicitud(this);

        daoCCCMMS = new daoCamion(this);

        List<String> listaDeNombreSolicitudes = daoSCCMMS.getNombreSolicitudByIdConductorS(id);
        if (listaDeNombreSolicitudes.isEmpty()) {
            listaDeNombreSolicitudes.add("No hay solicitudes disponibles");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDeNombreSolicitudes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiCCMMSMisSolicitudes.setAdapter(adapter);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_CCMMS_MiUbicacion) {
            ids = daoSCCMMS.obtenerIdSolicitudByNombreSolicitud(spiCCMMSMisSolicitudes.getSelectedItem().toString());
            s = daoSCCMMS.getSolicitudById(ids);
            c = daoCCCMMS.getCamionByPlaca(s.getPlacaCamion());
            idc = c.getId();
            Intent iccmms1 = new Intent(MisSolicitudesConductorCamion.this, MiUbicacionConductorCamion.class);
            iccmms1.putExtra("Id", id);
            iccmms1.putExtra("IdC", idc);
            startActivity(iccmms1);

        }else if (v.getId() == R.id.button_CCMMS_RutaSugerida) {
            ids = daoSCCMMS.obtenerIdSolicitudByNombreSolicitud(spiCCMMSMisSolicitudes.getSelectedItem().toString());
            Intent iccmms2 = new Intent(MisSolicitudesConductorCamion.this, RutaSugerida.class);
            iccmms2.putExtra("Id", id);
            iccmms2.putExtra("IdS", ids);
            startActivity(iccmms2);

        }else if (v.getId() == R.id.button_CCMMS_ChatSolicitud) {
            ids = daoSCCMMS.obtenerIdSolicitudByNombreSolicitud(spiCCMMSMisSolicitudes.getSelectedItem().toString());
            Intent iccmms3 = new Intent(MisSolicitudesConductorCamion.this, ChatNotificaciones.class);
            iccmms3.putExtra("Id", id);
            iccmms3.putExtra("IdS", ids);
            startActivity(iccmms3);

        }else if (v.getId() == R.id.button_CCMMS_InformacionSolicitud) {
            String nombreSolicitudSeleccionada = spiCCMMSMisSolicitudes.getSelectedItem().toString();
            int idSolicitud = daoSCCMMS.obtenerIdSolicitudByNombreSolicitud(nombreSolicitudSeleccionada);
            Solicitud solicitudSeleccionada = daoSCCMMS.getSolicitudById(idSolicitud);

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

        }else if (v.getId() == R.id.button_CCMMS_Volver) {
            Intent iccmms4 = new Intent(MisSolicitudesConductorCamion.this, InicioConductorCamion.class);
            iccmms4.putExtra("Id", id);
            startActivity(iccmms4);
            finish();
        }
    }
}