package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

public class DetalleSolicitudToolBar extends AppCompatActivity implements View.OnClickListener{
    Button btnVSDSPCVolver, btnToolbarAceptar; //VSDSPC - Ver Solicitudes Detalle Solicitud Propietario Camion
    TextView txvVSDSPCNombreSolicitud, txvVSDSPCFechaCarga, txvVSDSPCFechaDescarga, txvVSDSPCLugarCarga, txvVSDSPCLugarDescarga, txvVSDSPCTipoMaterial, txvVSDSPCPesoMaterial, txvVSDSPCTamañoMaterial;
    TextView sa;

    int id=0;
    int ids=0;
    Usuario u;
    Solicitud s;
    daoUsuario daoUVSDSPC; // UVSDCPC Usuario Ver Solicitudes Detalle Solicitud Propietario Camion
    daoSolicitud daoSVSDSPC; // SVSDSPC Solicitud Ver Solicitudes Detalle Solicitud Propietario Camion

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_solicitud_tool_bar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txvVSDSPCNombreSolicitud = findViewById(R.id.textView_VSDSPC_NombreSolicitud);
        txvVSDSPCFechaCarga = findViewById(R.id.textView_VSDSPC_FechaCarga);
        txvVSDSPCFechaDescarga = findViewById(R.id.textView_VSDSPC_FechaDescarga);
        txvVSDSPCLugarCarga = findViewById(R.id.textView_VSDSPC_LugarCarga);
        txvVSDSPCLugarDescarga = findViewById(R.id.textView_VSDSPC_LugarDescarga);
        txvVSDSPCTipoMaterial = findViewById(R.id.textView_VSDSPC_TipoMaterial);
        txvVSDSPCPesoMaterial = findViewById(R.id.textView_VSDSPC_PesoMaterial);
        txvVSDSPCTamañoMaterial = findViewById(R.id.textView_VSDSPC_TamañoMaterial);

        sa = findViewById(R.id.textView_VSDSPC_SesionActual);
        btnToolbarAceptar = findViewById(R.id.button_toolbar_aceptar);
        btnVSDSPCVolver = findViewById(R.id.button_VSDSPC_Volver);

        btnToolbarAceptar.setOnClickListener(this);
        btnVSDSPCVolver.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoUVSDSPC= new daoUsuario(this);
        u= daoUVSDSPC.getUsuarioById(id);

        ids= b.getInt("SId");
        daoSVSDSPC = new daoSolicitud(this);
        s= daoSVSDSPC.getSolicitudById(ids);

        txvVSDSPCNombreSolicitud.setText("Nombre de la solicitud: " + s.getNombreSolicitud());
        txvVSDSPCFechaCarga.setText("Fecha de carga: " + s.getFechaCarga());
        txvVSDSPCFechaDescarga.setText("Fecha de descarga: " + s.getFechaDescarga());
        txvVSDSPCLugarCarga.setText("Lugar de carga: " + s.getLugarCarga());
        txvVSDSPCLugarDescarga.setText("Lugar de descarga: " + s.getLugarDescarga());
        txvVSDSPCTipoMaterial.setText("Tipo de material: " + s.getTipoMaterial());
        txvVSDSPCPesoMaterial.setText("Peso del material: " + s.getPesoMaterial());
        txvVSDSPCTamañoMaterial.setText("Tamaño del material: " + s.getTamañoMaterial());

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_toolbar_aceptar) {
            AlertDialog.Builder b= new AlertDialog.Builder(this);
            b.setMessage("Estas seguro de Aceptar la Solicitud");
            b.setCancelable(false);
            b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    s.setEstadoSolicitud("En Proceso");
                    s.setIdPropietarioS(u.getId());
                    if (daoSVSDSPC.updateSolicitud(s)){
                        Toast.makeText(DetalleSolicitudToolBar.this, "Solicitud Aceptada!", Toast.LENGTH_LONG).show();
                        Intent ivsdspc1 = new Intent(DetalleSolicitudToolBar.this, VerSolicitudes.class);
                        ivsdspc1.putExtra("Id", id);
                        startActivity(ivsdspc1);
                        finish();
                    }else{
                        Toast.makeText(DetalleSolicitudToolBar.this, "Error: Solicitud No Aceptada!", Toast.LENGTH_LONG).show();
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
        } else if (v.getId() == R.id.button_VSDSPC_Volver) {
            Intent ivsdspc2 = new Intent(DetalleSolicitudToolBar.this, VerSolicitudes.class);
            ivsdspc2.putExtra("Id", id);
            startActivity(ivsdspc2);
            finish();
        }
    }
}