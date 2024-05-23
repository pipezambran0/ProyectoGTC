package co.edu.proyectogtc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class AsignarCamion extends AppCompatActivity implements View.OnClickListener{
    TextView txvPCACNombreSolicitud, sa;
    Spinner spiPCACCamiones;
    Button btnPCACAsignar, btnPCACCancelar; // PCAC - Propietario Camion Asignar Camion
    int id =0;
    int ids =0;
    int idc =0;
    String placa;
    Usuario u;
    Camion c;
    Solicitud s;
    daoUsuario daoUPCAC;  // UPCAC - Usuario Propietario Camion Asignar Camion
    daoCamion daoCPCAC;   // CPCAC - Camion Propietario Camion Asignar Camion
    daoSolicitud daoPCAC; // SPCAC - Solicictud Propietario Camion Asignar Camion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asignar_camion);

        sa = (TextView) findViewById(R.id.textView_PCAC_SesionActual);
        txvPCACNombreSolicitud = (TextView) findViewById(R.id.textView_PCAC_NombreSolicitud);
        spiPCACCamiones = (Spinner) findViewById(R.id.spinner_PCAC_Camiones);

        btnPCACAsignar = (Button) findViewById(R.id.button_PCAC_Asignar);
        btnPCACCancelar = (Button) findViewById(R.id.button_PCAC_Cancelar);

        btnPCACAsignar.setOnClickListener(this);
        btnPCACCancelar.setOnClickListener(this);

        daoUPCAC = new daoUsuario(this);
        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        u= daoUPCAC.getUsuarioById(id);

        daoPCAC = new daoSolicitud(this);
        ids= b.getInt("IdS");
        s= daoPCAC.getSolicitudById(ids);
        txvPCACNombreSolicitud.setText(s.getNombreSolicitud());

        daoCPCAC = new daoCamion(this);

        List<String> listaDePlacas = daoCPCAC.getPlacasDeCamionesByIdPropietario(id);
        if (listaDePlacas.isEmpty()) {
            listaDePlacas.add("No hay camiones disponibles");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDePlacas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiPCACCamiones.setAdapter(adapter);

        sa.setText("Sesión: " + u.getId() + " Nombre: " + u.getNombre() + " Cedula: " + u.getCedula() + "\n" + "Usuario: " + u.getUsuario() + " Rol: " + u.getRol());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_PCAC_Asignar) {
            c = daoCPCAC.getCamionByPlaca(spiPCACCamiones.getSelectedItem().toString());
            s.setIdConductorS(c.getIdConductor());
            s.setPlacaCamion(c.getPlaca());
            c.setEstado("En Uso");
            if(!s.isNull()){
                Toast.makeText(this, "Error: Campos vacios", Toast.LENGTH_LONG).show();
            }else if (daoPCAC.updateSolicitud(s) &&  daoCPCAC.updateCamion(c)){
                Toast.makeText(this, "Asignación Exitosa!", Toast.LENGTH_LONG).show();
                Intent ipcac1 = new Intent(AsignarCamion.this, MisSolicitudesPropietarioCamion.class);
                ipcac1.putExtra("Id", id);
                startActivity(ipcac1);
                finish();
            }else{
                Toast.makeText(this, "Asiganación No Exitosa!", Toast.LENGTH_LONG).show();
            }
        }else if (v.getId() == R.id.button_PCAC_Cancelar) {
            Intent ipcac2 = new Intent(AsignarCamion.this, MisSolicitudesPropietarioCamion.class);
            ipcac2.putExtra("Id", id);
            startActivity(ipcac2);
            finish();
        }
    }
}