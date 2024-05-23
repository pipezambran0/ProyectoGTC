package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class AgregarCamion extends AppCompatActivity implements View.OnClickListener{
    EditText edtPCMCACPlaca, edtPCMCACMarca;
    TextView txvPCMCACPropietario, txvPCMCACViajes, txvPCMCACEstado, sa;
    Spinner spiPCMCACConductor;
    Button btnPCMCACAgregar, btnPCMCACCancelar; // PCMCAC - Propietario Camion Mis Camiones Agregar Camiones
    int id =0;
    int idc =0;
    String placa;
    Usuario u;
    Camion c;
    daoUsuario daoUPCMCAC; // UPCMCAC - Usuario Propietario Camion Mis Camiones Agregar Camiones
    daoCamion daoCPCMCAC; // CPCMCAC - Camion Propietario Camion Mis Camiones Agregar Camiones

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_camion);
        sa = (TextView) findViewById(R.id.textView_PCMCAC_SesionActual);
        edtPCMCACPlaca = (EditText) findViewById(R.id.editText_PCMCAC_Placa);
        edtPCMCACMarca = (EditText) findViewById(R.id.editText_PCMCAC_Marca);
        txvPCMCACPropietario = (TextView) findViewById(R.id.textView_PCMCAC_Propietario);
        txvPCMCACViajes = (TextView) findViewById(R.id.textView_PCMCAC_Viajes);
        txvPCMCACEstado = (TextView) findViewById(R.id.textView_PCMCAC_Estado);
        spiPCMCACConductor = (Spinner) findViewById(R.id.spinner_PCMCAC_ConductoresDisponibles);

        btnPCMCACAgregar = (Button) findViewById(R.id.button_PCMCAC_AgregarCamion);
        btnPCMCACCancelar = (Button) findViewById(R.id.button_PCMCAC_Cancelar);

        daoUPCMCAC = new daoUsuario(this);
        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        u= daoUPCMCAC.getUsuarioById(id);
        txvPCMCACPropietario.setText(u.getNombre());

        daoCPCMCAC = new daoCamion(this);

        btnPCMCACAgregar.setOnClickListener(this);
        btnPCMCACCancelar.setOnClickListener(this);

        List<String> listaDeCedulas = daoUPCMCAC.getCedulasDeConductores();

        if (listaDeCedulas.isEmpty()) {
            listaDeCedulas.add("No hay conductores disponibles");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDeCedulas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiPCMCACConductor.setAdapter(adapter);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_PCMCAC_AgregarCamion) {
            Camion ca= new Camion();
            ca.setIdPropietario(id);
            ca.setIdConductor(daoUPCMCAC.getIdUsuarioPorCedula(spiPCMCACConductor.getSelectedItem().toString()));

            String viajesStr = txvPCMCACViajes.getText().toString();
            int viajes = Integer.parseInt(viajesStr);
            ca.setViajes(viajes);
            ca.setUbicacion("---/---");
            ca.setPlaca(edtPCMCACPlaca.getText().toString());
            ca.setMarca(edtPCMCACMarca.getText().toString());
            ca.setEstado(txvPCMCACEstado.getText().toString());

            if(!ca.isNull()){
                Toast.makeText(this, "Error: Campos vacios", Toast.LENGTH_LONG).show();
            }else if (daoCPCMCAC.insertCamion(ca)){
                Toast.makeText(this, "Camión Agregado", Toast.LENGTH_LONG).show();
                Intent ipcmcac1 = new Intent(AgregarCamion.this, MisCamionesPropietarioCamion.class);
                ipcmcac1.putExtra("Id", id);
                startActivity(ipcmcac1);
                finish();
            }else{
                Toast.makeText(this, "Camión ya agregado!", Toast.LENGTH_LONG).show();
            }

        }else if (v.getId() == R.id.button_PCMCAC_Cancelar) {
            Intent ipcmcac2 = new Intent(AgregarCamion.this, MisCamionesPropietarioCamion.class);
            ipcmcac2.putExtra("Id", id);
            startActivity(ipcmcac2);
            finish();
        }
    }
}