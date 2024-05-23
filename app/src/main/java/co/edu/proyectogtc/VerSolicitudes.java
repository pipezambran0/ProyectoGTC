package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class VerSolicitudes extends AppCompatActivity implements View.OnClickListener{
    Button btnVSPCVolver; //VSPC - Ver Solicitudes Propietario Camion
    ListView lstVSPCSolicitudesDisponibles;
    TextView sa;

    int id=0;
    int ids=0;
    Usuario u;
    Solicitud s;
    daoUsuario daoUVSPC; // UVSPC Usuario Ver Solicitudes Propietario Camion
    daoSolicitud daoSVSPC; // SVSPC Solicitud Ver Solicitudes Propietario Camion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_solicitudes);

        lstVSPCSolicitudesDisponibles = (ListView) findViewById(R.id.listView_VSPC);
        sa = (TextView) findViewById(R.id.textView_VSPC_SesionActual);
        btnVSPCVolver = (Button) findViewById(R.id.button_VSPC_Volver);

        btnVSPCVolver.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoUVSPC = new daoUsuario(this);
        u= daoUVSPC.getUsuarioById(id);

        daoSVSPC = new daoSolicitud(this);
        ArrayList<Solicitud> l= daoSVSPC.selectSolicitudesDisponibles();

        ArrayList<String> list = new ArrayList<String>();
        for (Solicitud S: l) {
            list.add(S.getNombreSolicitud());
        }
        ArrayAdapter<String> a=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
        lstVSPCSolicitudesDisponibles.setAdapter(a);

        lstVSPCSolicitudesDisponibles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Solicitud solicitudSeleccionada = l.get(position);
                int IdSolicitud = solicitudSeleccionada.getId();
                Intent ivspc1 = new Intent(VerSolicitudes.this, DetalleSolicitudToolBar.class);
                ivspc1.putExtra("Id", u.getId());
                ivspc1.putExtra("SId", IdSolicitud);
                startActivity(ivspc1);
            }
        });
        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_VSPC_Volver) {
            Intent ivspc2 = new Intent(VerSolicitudes.this, InicioPropietarioCamion.class);
            ivspc2.putExtra("Id", id);
            startActivity(ivspc2);
            finish();
        }
    }
}