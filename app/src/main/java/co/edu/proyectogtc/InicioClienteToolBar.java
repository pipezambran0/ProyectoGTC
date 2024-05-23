package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InicioClienteToolBar extends AppCompatActivity implements View.OnClickListener {
    Button btnMisSolicitudes, btnOpciones, btnSalir;
    TextView sa;
    int id = 0;
    Usuario u;
    daoUsuario daoUMIC;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_cliente_tool_bar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sa = findViewById(R.id.tvSesionActual);
        btnMisSolicitudes = findViewById(R.id.btnMisSolicitudes);
        btnOpciones = findViewById(R.id.btnOpciones);
        btnSalir = findViewById(R.id.button_toolbar_salir);

        btnMisSolicitudes.setOnClickListener(this);
        btnOpciones.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        id = b.getInt("Id");
        daoUMIC = new daoUsuario(this);
        u = daoUMIC.getUsuarioById(id);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnMisSolicitudes) {
            Intent iic1 = new Intent(InicioClienteToolBar.this, MisSolicitudesCliente.class);
            iic1.putExtra("Id", this.id);
            startActivity(iic1);
        } else if (v.getId() == R.id.btnOpciones) {
            Intent intent = new Intent(InicioClienteToolBar.this, Opciones.class);
            intent.putExtra("Id", this.id);
            startActivity(intent);
        } else if (v.getId() == R.id.button_toolbar_salir) {
            Intent intent = new Intent(InicioClienteToolBar.this, Main.class);
            startActivity(intent);
            finish();
        }
    }
}