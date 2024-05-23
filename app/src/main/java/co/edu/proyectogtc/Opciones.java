package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Opciones extends AppCompatActivity implements View.OnClickListener{
    Button btnOpcionesEditar, btnOpcionesEliminar, btnOpcionesVer, btnOpcionesVolver;
    TextView sa;
    int id =0;
    Usuario u;
    daoUsuario daoOpciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones);
        sa = (TextView) findViewById(R.id.textView_opciones_SesionActual);
        btnOpcionesEditar = (Button) findViewById(R.id.button_opciones_editar);
        btnOpcionesEliminar = (Button) findViewById(R.id.button_opciones_eliminar);
        btnOpcionesVer = (Button) findViewById(R.id.button_opciones_ver);
        btnOpcionesVolver = (Button) findViewById(R.id.button_opciones_volver);

        btnOpcionesEditar.setOnClickListener(this);
        btnOpcionesEliminar.setOnClickListener(this);
        btnOpcionesVer.setOnClickListener(this);
        btnOpcionesVolver.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoOpciones = new daoUsuario(this);
        u= daoOpciones.getUsuarioById(id);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_opciones_editar) {
            Intent io1 = new Intent(Opciones.this, EditarUsuario.class);
            io1.putExtra("Id", id);
            startActivity(io1);

        }else if (v.getId() == R.id.button_opciones_eliminar) {
            AlertDialog.Builder b= new AlertDialog.Builder(this);
            b.setMessage("Estas seguro de Eliminar tu Cuenta?");
            b.setCancelable(false);
            b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(daoOpciones.deleteUsuario(id)){
                        Toast.makeText(Opciones.this, "Eliminacion Exitosa!", Toast.LENGTH_LONG).show();
                        Intent io2 = new Intent(Opciones.this, Main.class);
                        startActivity(io2);
                        finish();
                    }else{
                        Toast.makeText(Opciones.this, "Eliminacion No Exitosa!", Toast.LENGTH_LONG).show();
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

        }else if (v.getId() == R.id.button_opciones_ver) {
            Intent io3 = new Intent(Opciones.this, Mostrar.class);
            startActivity(io3);

        }else if (v.getId() == R.id.button_opciones_volver) {
            String rol= u.getRol();
            if (rol.equals("Cliente")) {
                Intent io4c = new Intent(Opciones.this, InicioClienteToolBar.class);
                io4c.putExtra("Id", id);
                startActivity(io4c);
                finish();
            }else if (rol.equals("Propietario de Camión")) {
                Intent iO4pc = new Intent(Opciones.this, InicioPropietarioCamion.class);
                iO4pc.putExtra("Id", id);
                startActivity(iO4pc);
                finish();
            } else if (rol.equals("Conductor de Camión")) {
                Intent iO4cc = new Intent(Opciones.this, InicioConductorCamion.class);
                iO4cc.putExtra("Id", id);
                startActivity(iO4cc);
                finish();
            }
        }
    }
}