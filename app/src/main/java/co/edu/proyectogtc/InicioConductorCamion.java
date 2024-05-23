package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InicioConductorCamion extends AppCompatActivity implements View.OnClickListener{
    Button btnMICCMisCamionesAsignados, btnMICCMisSolicitudesAsignadas, btnMICCOpciones, btnMICCSalir; //MICC - Menu Inicio Conducto Camion
    TextView sa;
    int id =0;
    Usuario u;
    daoUsuario daoUMICC; //UMICC - Usuario Menu Inicio Conductor Camion

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_conductor_camion);
        sa = (TextView) findViewById(R.id.textView_MICC_SesionActual);
        btnMICCMisCamionesAsignados = (Button) findViewById(R.id.button_MICC_MisCamionesAsignados);
        btnMICCMisSolicitudesAsignadas = (Button) findViewById(R.id.button_MICC_MisSolicitudesAsignadas);
        btnMICCOpciones = (Button) findViewById(R.id.button_MICC_Opciones);
        btnMICCSalir = (Button) findViewById(R.id.button_MICC_Salir);

        btnMICCMisCamionesAsignados.setOnClickListener(this);
        btnMICCMisSolicitudesAsignadas.setOnClickListener(this);
        btnMICCOpciones.setOnClickListener(this);
        btnMICCSalir.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoUMICC = new daoUsuario(this);
        u= daoUMICC.getUsuarioById(id);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_MICC_MisCamionesAsignados) {
            Intent iicc1 = new Intent(InicioConductorCamion.this, MisCamionesConductorCamion.class);
            iicc1.putExtra("Id", id);
            startActivity(iicc1);
        }else if (v.getId() == R.id.button_MICC_MisSolicitudesAsignadas) {
            Intent iicc2 = new Intent(InicioConductorCamion.this, MisSolicitudesConductorCamion.class);
            iicc2.putExtra("Id", id);
            startActivity(iicc2);
        }else if (v.getId() == R.id.button_MICC_Opciones) {
            Intent iicc3 = new Intent(InicioConductorCamion.this, Opciones.class);
            iicc3.putExtra("Id", id);
            startActivity(iicc3);
        }else if (v.getId() == R.id.button_MICC_Salir) {
            Intent iicc4= new Intent(InicioConductorCamion.this, Main.class);
            startActivity(iicc4);
            finish();
        }
    }
}