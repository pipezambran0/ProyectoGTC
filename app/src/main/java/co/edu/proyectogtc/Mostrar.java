package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class Mostrar extends AppCompatActivity {
    ListView lstMostrarUsuarios;
    daoUsuario daoMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar);
        lstMostrarUsuarios = (ListView) findViewById(R.id.listView_mostrar);

        daoMostrar= new daoUsuario(this);
        ArrayList<Usuario> l= daoMostrar.selectUsuarios();

        ArrayList<String> list = new ArrayList<String>();
        for (Usuario u: l) {
            list.add("Id: " + u.getId());
        }
        ArrayAdapter<String> a=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
        lstMostrarUsuarios.setAdapter(a);

        lstMostrarUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Usuario usuarioSeleccionado = l.get(position);
                int usuarioId = usuarioSeleccionado.getId();
                Intent imu1 = new Intent(Mostrar.this, DetalleUsuario.class);
                imu1.putExtra("usuarioId", usuarioId);
                startActivity(imu1);
            }
        });
    }
}