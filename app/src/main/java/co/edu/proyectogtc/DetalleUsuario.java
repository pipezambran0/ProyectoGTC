package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.widget.TextView;

public class DetalleUsuario extends AppCompatActivity {
    TextView mostrarId, mostrarNombre, mostrarUsuario, mostrarContraseña, mostrarCorreo, mostrarCedula, mostrarRol, mostrarGenero;
    daoUsuario daoDetalle;
    int id=0;
    Usuario u;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_usuario);

        mostrarId = (TextView) findViewById(R.id.textView_mostrar_id);
        mostrarNombre = (TextView) findViewById(R.id.textView_mostrar_nombre);
        mostrarUsuario = (TextView) findViewById(R.id.textView_mostrar_usuario);
        mostrarContraseña = (TextView) findViewById(R.id.textView_mostrar_contraseña);
        mostrarCorreo = (TextView) findViewById(R.id.textView_mostrar_correo);
        mostrarCedula = (TextView) findViewById(R.id.textView_mostrar_cedula);
        mostrarRol = (TextView) findViewById(R.id.textView_mostrar_rol);
        mostrarGenero = (TextView) findViewById(R.id.textView_mostrar_genero);

        Bundle b= getIntent().getExtras();
        id= b.getInt("usuarioId");
        daoDetalle= new daoUsuario(this);
        u= daoDetalle.getUsuarioById(id);

        mostrarId.setText("Id: " + u.getId());
        mostrarNombre.setText("Nombre: " + u.getNombre());
        mostrarUsuario.setText("Usuario: " + u.getUsuario());
        mostrarContraseña.setText("Contraseña: " + u.getPassword());
        mostrarCorreo.setText("Correo: " + u.getCorreo());
        mostrarCedula.setText("Cédula: " + u.getCedula());
        mostrarRol.setText("Rol: " + u.getRol());
        mostrarGenero.setText("Género: " + u.getGenero());
    }
}