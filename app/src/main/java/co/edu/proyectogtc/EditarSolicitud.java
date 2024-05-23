package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditarSolicitud extends AppCompatActivity implements View.OnClickListener{

    TextView sa, txvCESNombreSolicitud;
    EditText edtCESFechaCarga, edtCESFechaDescarga;
    EditText edtCESLugarCarga, edtCESLugarDescarga;
    EditText edtCESTipoMaterial, edtCESPesoMaterial, edtCESTamañoMaterial;
    Button btnCESEditarSolicitud, btnCESCancelarSolicitud; // CES - Cliente Editar Solicitud

    int id=0;
    int ids=0;
    Usuario u;
    Solicitud s;
    daoUsuario daoUCES; // UCES Usuario Cliente Editar Solicitud
    daoSolicitud daoSCES; // SCES Solicitud Cliente Editar Solicitud

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_solicitud);
        sa = (TextView) findViewById(R.id.textView_CES_SesionActual);
        txvCESNombreSolicitud = (TextView) findViewById(R.id.textView_CES_NombreSolicitud);
        edtCESFechaCarga = (EditText) findViewById(R.id.editText_CES_FechaCarga);
        edtCESFechaDescarga = (EditText) findViewById(R.id.editText_CES_FechaDescarga);
        edtCESLugarCarga = (EditText) findViewById(R.id.editText_CES_LugarCarga);
        edtCESLugarDescarga = (EditText) findViewById(R.id.editText_CES_LugarDescarga);
        edtCESTipoMaterial = (EditText) findViewById(R.id.editText_CES_TipoMaterial);
        edtCESPesoMaterial = (EditText) findViewById(R.id.editText_CES_PesoMaterial);
        edtCESTamañoMaterial = (EditText) findViewById(R.id.editText_CES_TamañoMaterial);

        btnCESEditarSolicitud = (Button) findViewById(R.id.button_CES_ActualizarSolicitud);
        btnCESCancelarSolicitud = (Button) findViewById(R.id.button_CES_CancelarEditarSolicitud);

        btnCESEditarSolicitud.setOnClickListener(this);
        btnCESCancelarSolicitud.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        daoUCES = new daoUsuario(this);
        id= b.getInt("Id");
        u= daoUCES.getUsuarioById(id);

        daoSCES = new daoSolicitud(this);
        ids= b.getInt("IdS");
        s= daoSCES.getSolicitudById(ids);
        txvCESNombreSolicitud.setText(s.getNombreSolicitud());
        edtCESFechaCarga.setText(s.getFechaCarga());
        edtCESFechaDescarga.setText(s.getFechaDescarga());
        edtCESLugarCarga.setText(s.getLugarCarga());
        edtCESLugarDescarga.setText(s.getLugarDescarga());
        edtCESTipoMaterial.setText(s.getTipoMaterial());
        edtCESPesoMaterial.setText(s.getPesoMaterial());
        edtCESTamañoMaterial.setText(s.getTamañoMaterial());

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_CES_ActualizarSolicitud) {
            s.setFechaCarga(edtCESFechaCarga.getText().toString());
            s.setFechaDescarga(edtCESFechaDescarga.getText().toString());
            s.setLugarCarga(edtCESLugarCarga.getText().toString());
            s.setLugarDescarga(edtCESLugarDescarga.getText().toString());
            s.setTipoMaterial(edtCESTipoMaterial.getText().toString());
            s.setPesoMaterial(edtCESPesoMaterial.getText().toString());
            s.setTamañoMaterial(edtCESTamañoMaterial.getText().toString());

            if(!s.isNull()){
                Toast.makeText(this, "Error: Campos vacios", Toast.LENGTH_LONG).show();
            }else if (daoSCES.updateSolicitud(s)){
                Toast.makeText(this, "Actualizacion Exitosa!", Toast.LENGTH_LONG).show();
                Intent ices1 = new Intent(EditarSolicitud.this, MisSolicitudesCliente.class);
                ices1.putExtra("Id", id);
                startActivity(ices1);
                finish();
            }else{
                Toast.makeText(this, "Actualizacion No Exitosa!", Toast.LENGTH_LONG).show();
            }
        }else if (v.getId() == R.id.button_CES_CancelarEditarSolicitud) {
            Intent ices2 = new Intent(EditarSolicitud.this, MisSolicitudesCliente.class);
            ices2.putExtra("Id", id);
            startActivity(ices2);
            finish();
        }
    }
}