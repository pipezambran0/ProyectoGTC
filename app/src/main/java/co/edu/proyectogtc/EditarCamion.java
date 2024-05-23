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

public class EditarCamion extends AppCompatActivity implements View.OnClickListener{
    EditText edtECPlaca, edtECMarca, edtECEstado;
    TextView sa;
    Spinner spiECConductor;
    Button btnECActulizar, btnECCancelar; // EC - Editar Camion
    int id =0;
    int idc =0;
    String placa;
    Usuario u;
    Camion c;
    daoUsuario daoUEC; // UEC - Usuario Editar Camion
    daoCamion daoCEC; // CEC - Camion Editar Camion

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_camion);
        sa = (TextView) findViewById(R.id.textView_editarCamion_SesionActual);
        edtECPlaca = (EditText) findViewById(R.id.editText_editarCamion_Placa);
        edtECMarca = (EditText) findViewById(R.id.editText_editarCamion_Marca);
        edtECEstado = (EditText) findViewById(R.id.editText_editarCamion_Estado);
        spiECConductor = (Spinner) findViewById(R.id.spinner_editarCamion_ConductoresDisponibles);
        btnECActulizar = (Button) findViewById(R.id.button_editarCamion_actualizar);
        btnECCancelar = (Button) findViewById(R.id.button_editarCamion_cancelar);

        Bundle b= getIntent().getExtras();
        daoUEC = new daoUsuario(this);
        id= b.getInt("Id");
        u= daoUEC.getUsuarioById(id);

        daoCEC = new daoCamion(this);
        idc= b.getInt("IdC");
        c= daoCEC.getCamionById(idc);
        edtECPlaca.setText(c.getPlaca());
        edtECMarca.setText(c.getMarca());
        edtECEstado.setText(c.getEstado());

        btnECActulizar.setOnClickListener(this);
        btnECCancelar.setOnClickListener(this);

        List<String> listaDeCedulas = daoUEC.getCedulasDeConductores();

        if (listaDeCedulas.isEmpty()) {
            listaDeCedulas.add("No hay conductores disponibles");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDeCedulas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiECConductor.setAdapter(adapter);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_editarCamion_actualizar) {
            int ucc = daoUEC.getIdUsuarioPorCedula(spiECConductor.getSelectedItem().toString());
            c.setPlaca(edtECPlaca.getText().toString());
            c.setMarca(edtECMarca.getText().toString());
            c.setEstado(edtECEstado.getText().toString());
            c.setIdConductor(ucc);
            if(!c.isNull()){
                Toast.makeText(this, "Error: Campos vacios", Toast.LENGTH_LONG).show();
            }else if (daoCEC.updateCamion(c)){
                Toast.makeText(this, "Actualizacion Exitosa!", Toast.LENGTH_LONG).show();
                Intent iec1 = new Intent(EditarCamion.this, MisCamionesPropietarioCamion.class);
                iec1.putExtra("Id", id);
                startActivity(iec1);
                finish();
            }else{
                Toast.makeText(this, "Actualizacion No Exitosa!", Toast.LENGTH_LONG).show();
            }
        }else if (v.getId() == R.id.button_editarCamion_cancelar) {
            Intent iec2 = new Intent(EditarCamion.this, MisCamionesPropietarioCamion.class);
            iec2.putExtra("Id", id);
            startActivity(iec2);
            finish();
        }
    }
}