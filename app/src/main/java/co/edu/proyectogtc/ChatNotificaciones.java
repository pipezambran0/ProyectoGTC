package co.edu.proyectogtc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ChatNotificaciones extends AppCompatActivity implements View.OnClickListener{
    Button btnCNToolBarCerrarChat, btnCNEnviar;
    ListView lstvMensajes;
    EditText txvCNMensaje;

    TextView sa;
    int id = 0;
    int ids = 0;
    int idm = 0;
    Usuario u;
    Solicitud s;
    daoUsuario daoUCN;
    daoSolicitud daoSCN;
    daoMensaje daoMCN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_notificaciones);
        Toolbar toolbar = findViewById(R.id.toolbar_CN);
        setSupportActionBar(toolbar);

        sa = (TextView) findViewById(R.id.textView_CN_SesionActual);
        lstvMensajes = (ListView) findViewById(R.id.listView_CN);
        txvCNMensaje = (EditText) findViewById(R.id.editText_CN_Mensaje);
        btnCNEnviar = (Button) findViewById(R.id.button_CN_Enviar);
        btnCNToolBarCerrarChat = (Button) findViewById(R.id.button_toolbar_CN_CerrarChat);

        btnCNEnviar.setOnClickListener(this);
        btnCNToolBarCerrarChat.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoUCN= new daoUsuario(this);
        u= daoUCN.getUsuarioById(id);

        ids= b.getInt("IdS");
        daoSCN = new daoSolicitud(this);
        s= daoSCN.getSolicitudById(ids);

        daoMCN = new daoMensaje(this);

        ArrayList<Mensaje> l= daoMCN.getMensajesByIdSolicitud(s.getId());

        ArrayList<String> list = new ArrayList<String>();
        for (Mensaje M: l) {
            list.add(M.getMensaje());
        }
        ArrayAdapter<String> a=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
        lstvMensajes.setAdapter(a);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_CN_Enviar) {
            Mensaje m= new Mensaje();
            m.setMensaje(u.getNombre() + ": " + txvCNMensaje.getText().toString());
            m.setIdSolicitud(ids);
            if(txvCNMensaje.getText().toString().isEmpty()){
                Toast.makeText(ChatNotificaciones.this, "Mensaje Vacio!", Toast.LENGTH_LONG).show();
            }else{
                if(daoMCN.insertMensaje(m)){
                    Intent icn1 = new Intent(ChatNotificaciones.this, ChatNotificaciones.class);
                    icn1.putExtra("Id", id);
                    icn1.putExtra("IdS", ids);
                    startActivity(icn1);
                    Toast.makeText(ChatNotificaciones.this, "Mensaje Enviado!", Toast.LENGTH_LONG).show();

                } else Toast.makeText(ChatNotificaciones.this, "Mensaje no Enviado!", Toast.LENGTH_LONG).show();
            }

        }else if (v.getId() == R.id.button_toolbar_CN_CerrarChat) {
            String rol= u.getRol();
            if (rol.equals("Cliente")) {
                Intent icn2 = new Intent(ChatNotificaciones.this, MisSolicitudesCliente.class);
                icn2.putExtra("Id", id);
                startActivity(icn2);
                finish();
            }else if (rol.equals("Propietario de Camión")) {
                Intent icn3 = new Intent(ChatNotificaciones.this, MisSolicitudesPropietarioCamion.class);
                icn3.putExtra("Id", id);
                startActivity(icn3);
                finish();
            } else if (rol.equals("Conductor de Camión")) {
                Intent icn4 = new Intent(ChatNotificaciones.this, MisSolicitudesConductorCamion.class);
                icn4.putExtra("Id", id);
                startActivity(icn4);
                finish();
            }
        }
    }
}