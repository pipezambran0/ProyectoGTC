package co.edu.proyectogtc;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class MisSolicitudesCliente extends AppCompatActivity implements View.OnClickListener{
    Button btnCMMSVolver, btnCMMSCrearSolicitud, btnCMMSEditarSolicitud, btnCMMSEliminarSolicitud, btnCMMSChatSolicitud, btnCMMSFinalizarSolicitud, btnCMMSInformacionSolicitud; //CMMS Cliente Menu Mis Solicitudes
    Spinner spiCMMSMisSolicitudes;
    TextView sa;

    int id=0;
    int ids=0;
    Usuario u;
    Solicitud s;
    daoUsuario daoUCMMS; // UCMMS Usuario Cliente Menu Mis Solicitudes
    daoSolicitud daoSCMMS; // SCMMS Solicitud Cliente Menu Mis Solicitudes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_solicitudes_cliente);

        sa = (TextView) findViewById(R.id.textView_CMMS_SesionActual);
        spiCMMSMisSolicitudes = (Spinner) findViewById(R.id.spinner_CMMS_MisSolicitudes);

        btnCMMSCrearSolicitud = (Button) findViewById(R.id.button_CMMS_CrearSolicitud);
        btnCMMSEditarSolicitud = (Button) findViewById(R.id.button_CMMS_EditarSolicitud);
        btnCMMSEliminarSolicitud = (Button) findViewById(R.id.button_CMMS_EliminarSolicitud);
        btnCMMSChatSolicitud = (Button) findViewById(R.id.button_CMMS_ChatSolicitud);
        btnCMMSFinalizarSolicitud = (Button) findViewById(R.id.button_CMMS_FinalizarSolicitud);
        btnCMMSInformacionSolicitud = (Button) findViewById(R.id.button_CMMS_InformacionSolicitud);
        btnCMMSVolver = (Button) findViewById(R.id.button_CMMS_Volver);

        btnCMMSCrearSolicitud.setOnClickListener(this);
        btnCMMSEditarSolicitud.setOnClickListener(this);
        btnCMMSEliminarSolicitud.setOnClickListener(this);
        btnCMMSChatSolicitud.setOnClickListener(this);
        btnCMMSFinalizarSolicitud.setOnClickListener(this);
        btnCMMSInformacionSolicitud.setOnClickListener(this);
        btnCMMSVolver.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoUCMMS = new daoUsuario(this);
        u= daoUCMMS.getUsuarioById(id);

        daoSCMMS = new daoSolicitud(this);

        List<String> listaDeNombreSolicitudes = daoSCMMS.getNombreSolicitudByIdClienteS(id);
        if (listaDeNombreSolicitudes.isEmpty()) {
            listaDeNombreSolicitudes.add("No hay solicitudes disponibles");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDeNombreSolicitudes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiCMMSMisSolicitudes.setAdapter(adapter);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_CMMS_CrearSolicitud) {
            Intent icmms1 = new Intent(MisSolicitudesCliente.this, CrearSolicitud.class);
            icmms1.putExtra("Id", id);
            startActivity(icmms1);

        }else if (v.getId() == R.id.button_CMMS_EditarSolicitud) {
            ids = daoSCMMS.obtenerIdSolicitudByNombreSolicitud(spiCMMSMisSolicitudes.getSelectedItem().toString());
            Intent icmms2 = new Intent(MisSolicitudesCliente.this, EditarSolicitud.class);
            icmms2.putExtra("Id", id);
            icmms2.putExtra("IdS", ids);
            startActivity(icmms2);

        }else if (v.getId() == R.id.button_CMMS_EliminarSolicitud) {
            AlertDialog.Builder b= new AlertDialog.Builder(this);
            b.setMessage("Estas seguro de Eliminar la solicitud?");
            b.setCancelable(false);
            b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ids = daoSCMMS.obtenerIdSolicitudByNombreSolicitud(spiCMMSMisSolicitudes.getSelectedItem().toString());
                    if(daoSCMMS.deleteSolicitud(ids)){
                        Toast.makeText(MisSolicitudesCliente.this, "Eliminacion Exitosa!", Toast.LENGTH_LONG).show();
                        Intent icmms3 = new Intent(MisSolicitudesCliente.this, MisSolicitudesCliente.class);
                        icmms3.putExtra("Id", id);
                        finish();
                        startActivity(icmms3);
                    }else{
                        Toast.makeText(MisSolicitudesCliente.this, "Eliminacion No Exitosa!", Toast.LENGTH_LONG).show();
                    }
                }
            });
            b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b.show();

        }else if (v.getId() == R.id.button_CMMS_InformacionSolicitud) {
            String nombreSolicitudSeleccionada = spiCMMSMisSolicitudes.getSelectedItem().toString();
            int idSolicitud = daoSCMMS.obtenerIdSolicitudByNombreSolicitud(nombreSolicitudSeleccionada);
            Solicitud solicitudSeleccionada = daoSCMMS.getSolicitudById(idSolicitud);
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

        }else if (v.getId() == R.id.button_CMMS_ChatSolicitud) {
//            ids = daoSCMMS.obtenerIdSolicitudByNombreSolicitud(spiCMMSMisSolicitudes.getSelectedItem().toString());
//            Intent icmms5 = new Intent(MisSolicitudesCliente.this, .class);
//            icmms5.putExtra("Id", id);
//            icmms5.putExtra("IdS", ids);
//            startActivity(icmms5);

        }else if (v.getId() == R.id.button_CMMS_FinalizarSolicitud) {
            ids = daoSCMMS.obtenerIdSolicitudByNombreSolicitud(spiCMMSMisSolicitudes.getSelectedItem().toString());
            Intent icmms6 = new Intent(MisSolicitudesCliente.this, FinalizarSolicitud.class);
            icmms6.putExtra("Id", id);
            icmms6.putExtra("IdS", ids);
            startActivity(icmms6);

        }else if (v.getId() == R.id.button_CMMS_Volver) {
            Intent icmms7 = new Intent(MisSolicitudesCliente.this, InicioClienteToolBar.class);
            icmms7.putExtra("Id", id);
            startActivity(icmms7);
            finish();
        }
    }
}