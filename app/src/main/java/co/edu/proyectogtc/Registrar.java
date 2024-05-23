package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Registrar extends AppCompatActivity implements View.OnClickListener {

    EditText edtRegistrarUsuario, edtRegistrarContraseña, edtRegistrarConfirmarContraseña, edtRegistrarCorreo, edtRegistrarNombre, edtRegistrarCedula;
    Spinner edtRegistrarRol;
    Button btnRegistrarRegistrarse, btnRegistrarCancelar;
    RadioButton rbtnRegistrarMasculino, rbtnRegistrarFemenino, rbtnRegistrarOtro;
    daoUsuario daoRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
        edtRegistrarUsuario = (EditText) findViewById(R.id.editText_registrar_usuario);
        edtRegistrarContraseña = (EditText) findViewById(R.id.editText_registrar_contraseña);
        edtRegistrarConfirmarContraseña = (EditText) findViewById(R.id.editText_registrar_confirmar_contraseña);
        edtRegistrarCorreo = (EditText) findViewById(R.id.editText_registrar_correo);
        edtRegistrarNombre = (EditText) findViewById(R.id.editText_registrar_nombre);
        edtRegistrarCedula = (EditText) findViewById(R.id.editText_registrar_cedula);

        rbtnRegistrarMasculino = (RadioButton) findViewById(R.id.radioButton_registrar_masculino);
        rbtnRegistrarFemenino = (RadioButton) findViewById(R.id.radioButton_registrar_femenino);
        rbtnRegistrarOtro = (RadioButton) findViewById(R.id.radioButton_registrar_otro);

        edtRegistrarRol = (Spinner) findViewById(R.id.spinner_registrar_rol);
        btnRegistrarRegistrarse = (Button) findViewById(R.id.button_registrar_registrarse);
        btnRegistrarCancelar = (Button) findViewById(R.id.button_registrar_cancelar);
        daoRegistrar = new daoUsuario(this);

        btnRegistrarRegistrarse.setOnClickListener(this);
        btnRegistrarCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_registrar_registrarse) {
            Usuario u= new Usuario();
            u.setUsuario(edtRegistrarUsuario.getText().toString());
            u.setPassword(edtRegistrarContraseña.getText().toString());
            u.setCorreo(edtRegistrarCorreo.getText().toString());
            u.setNombre(edtRegistrarNombre.getText().toString());
            u.setCedula(edtRegistrarCedula.getText().toString());
            u.setRol(edtRegistrarRol.getSelectedItem().toString());

            if (rbtnRegistrarMasculino.isChecked() == true) {
                u.setGenero(rbtnRegistrarMasculino.getText().toString());
            } else if (rbtnRegistrarFemenino.isChecked() == true) {
                u.setGenero(rbtnRegistrarFemenino.getText().toString());
            } else if (rbtnRegistrarOtro.isChecked() == true) {
                u.setGenero(rbtnRegistrarOtro.getText().toString());
            }


            if(!u.isNull()){
                Toast.makeText(this, "Error: Campos vacios!", Toast.LENGTH_LONG).show();
            } else if (!edtRegistrarContraseña.getText().toString().equals(edtRegistrarConfirmarContraseña.getText().toString())){
                Toast.makeText(this, "Error: Contraseñas no concuerdan!", Toast.LENGTH_LONG).show();
            } else if (daoRegistrar.buscarCedula(edtRegistrarCedula.getText().toString()) == true){
                Toast.makeText(this, "Error: Cedula ya registrada!", Toast.LENGTH_LONG).show();
            } else if (daoRegistrar.insertUsuario(u)){
                Toast.makeText(this, "Registro Exitoso!", Toast.LENGTH_LONG).show();
                Intent ir1 = new Intent(Registrar.this, Main.class);
                startActivity(ir1);
                finish();
            }else{
                Toast.makeText(this, "Usuario ya registrado!", Toast.LENGTH_LONG).show();
            }

        } else if (v.getId() == R.id.button_registrar_cancelar) {
            Intent ir2 = new Intent(Registrar.this, Main.class);
            startActivity(ir2);
            finish();
        }
    }
}