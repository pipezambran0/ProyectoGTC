package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrearSolicitud extends AppCompatActivity implements View.OnClickListener{

    TextView sa, txvCCSNombreSolicitud;
    EditText edtCCSFechaCarga, edtCCSFechaDescarga;
    EditText edtCCSLugarCarga, edtCCSLugarDescarga;
    EditText edtCCSTipoMaterial, edtCCSPesoMaterial, edtCCSTamañoMaterial;
    Button btnCCSCrearSolicitud, btnCCSCancelarSolicitud; // CCS - Cliente Crear Solicitud

    int id=0;
    int ids=0;
    Usuario u;
    Solicitud s;
    daoUsuario daoUCCS; // UCCS Usuario Cliente Crear Solicitud
    daoSolicitud daoSCCS; // SCCS Solicitud Cliente Crear Solicitud

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_solicitud);

        sa = (TextView) findViewById(R.id.textView_CCS_SesionActual);
        txvCCSNombreSolicitud = (TextView) findViewById(R.id.textView_CCS_NombreSolicitud);
        edtCCSFechaCarga = (EditText) findViewById(R.id.editText_CCS_FechaCarga);
        edtCCSFechaDescarga = (EditText) findViewById(R.id.editText_CCS_FechaDescarga);
        edtCCSLugarCarga = (EditText) findViewById(R.id.editText_CCS_LugarCarga);
        edtCCSLugarDescarga = (EditText) findViewById(R.id.editText_CCS_LugarDescarga);
        edtCCSTipoMaterial = (EditText) findViewById(R.id.editText_CCS_TipoMaterial);
        edtCCSPesoMaterial = (EditText) findViewById(R.id.editText_CCS_PesoMaterial);
        edtCCSTamañoMaterial = (EditText) findViewById(R.id.editText_CCS_TamañoMaterial);

        btnCCSCrearSolicitud = (Button) findViewById(R.id.button_CCS_CrearSolicitud);
        btnCCSCancelarSolicitud = (Button) findViewById(R.id.button_CCS_CancelarSolicitud);

        btnCCSCrearSolicitud.setOnClickListener(this);
        btnCCSCancelarSolicitud.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoUCCS = new daoUsuario(this);
        u= daoUCCS.getUsuarioById(id);

        daoSCCS = new daoSolicitud(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        String currentTime = timeFormat.format(new Date());
        txvCCSNombreSolicitud.setText(u.getCedula()+ "-" + currentDate + "-" + currentTime);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_CCS_CrearSolicitud) {
            Solicitud so= new Solicitud();

            so.setIdClienteS(id);
            so.setIdPropietarioS(0);
            so.setIdConductorS(0);
            so.setIdChat(0);

            so.setNombreSolicitud(txvCCSNombreSolicitud.getText().toString());
            so.setFechaCarga(edtCCSFechaCarga.getText().toString());
            so.setFechaDescarga(edtCCSFechaDescarga.getText().toString());
            so.setLugarCarga(edtCCSLugarCarga.getText().toString());
            so.setLugarDescarga(edtCCSLugarDescarga.getText().toString());
            so.setTipoMaterial(edtCCSTipoMaterial.getText().toString());
            so.setPesoMaterial(edtCCSPesoMaterial.getText().toString());
            so.setTamañoMaterial(edtCCSTamañoMaterial.getText().toString());
            so.setEstadoSolicitud("Disponible");
            so.setPlacaCamion("***---");
            so.setCalificacionCliente("*");

            if(!so.isNull()){
                Toast.makeText(this, "Error: Campos vacios", Toast.LENGTH_LONG).show();
            }else if (daoSCCS.insertSolicitud(so)){
                Toast.makeText(this, "Solicitud Creada", Toast.LENGTH_LONG).show();
                Intent icmmscs1 = new Intent(CrearSolicitud.this, MisSolicitudesCliente.class);
                icmmscs1.putExtra("Id", id);
                startActivity(icmmscs1);
                finish();
            }else{
                Toast.makeText(this, "Solicitud ya creada!", Toast.LENGTH_LONG).show();
            }

        }else if (v.getId() == R.id.button_CCS_CancelarSolicitud) {
            Intent icmmscs2 = new Intent(CrearSolicitud.this, MisSolicitudesCliente.class);
            icmmscs2.putExtra("Id", id);
            startActivity(icmmscs2);
            finish();
        }
    }
}