package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity implements View.OnClickListener{

    EditText edtMainUser, edtMainPassword;
    Button btnMainEntrar, btnMainRegistrar;
    daoUsuario daoMain;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //getSupportActionBar().setTitle("Hola Que Tal");
        edtMainUser = (EditText) findViewById(R.id.editText_main_usuario);
        edtMainPassword = (EditText) findViewById(R.id.editText_main_contraseña);
        btnMainEntrar = (Button) findViewById(R.id.button_main_entrar);
        btnMainRegistrar = (Button) findViewById(R.id.button_main_registrar);
        daoMain = new daoUsuario(this);

        btnMainEntrar.setOnClickListener(this);
        btnMainRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_main_entrar) {
            String u= edtMainUser.getText().toString();
            String p= edtMainPassword.getText().toString();
            if(u.equals("") && p.equals("")){
                Toast.makeText(this, "Error: Campos vacios", Toast.LENGTH_LONG).show();
            } else if (daoMain.Login(u,p)==1) {
                Usuario ux= daoMain.getUsuario(u,p);
                String rol= ux.getRol();
                if (rol.equals("Cliente")) {
                    Toast.makeText(this, "Inicio Exitoso como Cliente", Toast.LENGTH_LONG).show();
                    Intent im1c = new Intent(Main.this, InicioClienteToolBar.class);
                    im1c.putExtra("Id", ux.getId());
                    startActivity(im1c);
                }else if (rol.equals("Propietario de Camión")) {
                    Toast.makeText(this, "Inicio Exitoso como Propietario de Camión", Toast.LENGTH_LONG).show();
                    Intent im1pc = new Intent(Main.this, InicioPropietarioCamion.class);
                    im1pc.putExtra("Id", ux.getId());
                    startActivity(im1pc);
                } else if (rol.equals("Conductor de Camión")) {
                    Toast.makeText(this, "Inicio Exitoso como Conductor de Camión", Toast.LENGTH_LONG).show();
                    Intent im1cc = new Intent(Main.this, InicioConductorCamion.class);
                    im1cc.putExtra("Id", ux.getId());
                    startActivity(im1cc);
                }
            }else if (daoMain.Login(u,p)==0) {
                Toast.makeText(this, "Error: Usuario Inexistente o Datos Incorrectos", Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.button_main_registrar) {
            Intent im2 = new Intent(Main.this, Registrar.class);
            startActivity(im2);
        }
    }
}