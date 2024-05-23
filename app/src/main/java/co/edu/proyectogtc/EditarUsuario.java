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

public class EditarUsuario extends AppCompatActivity implements View.OnClickListener{
    EditText edtEditarUsuario, edtEditarContraseña, edtEditarCorreo, edtEditarNombre, edtEditarCedula;
    TextView sa;
    Button btnEditarActualizar, btnEditarCancelar;
    int id=0;
    Usuario u;
    daoUsuario daoEditar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_usuario);

        sa = (TextView) findViewById(R.id.textView_editar_SesionActual);
        edtEditarUsuario = (EditText) findViewById(R.id.editText_editar_usuario);
        edtEditarContraseña = (EditText) findViewById(R.id.editText_editar_contraseña);
        edtEditarCorreo = (EditText) findViewById(R.id.editText_editar_correo);
        edtEditarNombre = (EditText) findViewById(R.id.editText_editar_nombre);
        edtEditarCedula = (EditText) findViewById(R.id.editText_editar_cedula);
        btnEditarActualizar = (Button) findViewById(R.id.button_editar_actualizar);
        btnEditarCancelar = (Button) findViewById(R.id.button_editar_cancelar);

        btnEditarActualizar.setOnClickListener(this);
        btnEditarCancelar.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoEditar= new daoUsuario(this);
        u= daoEditar.getUsuarioById(id);
        edtEditarUsuario.setText(u.getUsuario());
        edtEditarContraseña.setText(u.getPassword());
        edtEditarCorreo.setText(u.getCorreo());
        edtEditarNombre.setText(u.getNombre());
        edtEditarCedula.setText(u.getCedula());

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_editar_actualizar) {
            u.setUsuario(edtEditarUsuario.getText().toString());
            u.setPassword(edtEditarContraseña.getText().toString());
            u.setCorreo(edtEditarCorreo.getText().toString());
            u.setNombre(edtEditarNombre.getText().toString());
            u.setCedula(edtEditarCedula.getText().toString());
            if(!u.isNull()){
                Toast.makeText(this, "Error: Campos vacios", Toast.LENGTH_LONG).show();
            }else if (daoEditar.updateUsuario(u)){
                Toast.makeText(this, "Actualizacion Exitosa!", Toast.LENGTH_LONG).show();
                Intent ie1 = new Intent(EditarUsuario.this, Opciones.class);
                ie1.putExtra("Id", id);
                startActivity(ie1);
                finish();
            }else{
                Toast.makeText(this, "Actualizacion No Exitosa!", Toast.LENGTH_LONG).show();
            }

        }else if (v.getId() == R.id.button_editar_cancelar) {
            Intent ie2 = new Intent(EditarUsuario.this, Opciones.class);
            ie2.putExtra("Id", id);
            startActivity(ie2);
            finish();
        }
    }
}