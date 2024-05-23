package co.edu.proyectogtc;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class FinalizarSolicitud extends AppCompatActivity implements View.OnClickListener{

    RatingBar ratingBar;
    Button btnCFSVolver,  btnCFSFinalizar;

    int id=0;
    int ids=0;
    int idc=0;
    Usuario u;
    Solicitud s;
    Camion c;
    daoUsuario daoUCFSS; // UCFS Usuario Cliente Finalizar Solicitud
    daoSolicitud daoSCFS; // SCFS Solicitud Cliente Finalizar Solicitud
    daoCamion daoCCFS; // CCFS Camion Cliente Finalizar Solicitud

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalizar_solicitud);

        ratingBar = findViewById(R.id.ratingBar_CFS_Califica);
        btnCFSVolver = findViewById(R.id.button_CFS_Volver);
        btnCFSFinalizar = findViewById(R.id.button_CFS_Finalizar);

        btnCFSVolver.setOnClickListener(this);
        btnCFSFinalizar.setOnClickListener(this);

        Bundle b= getIntent().getExtras();
        id= b.getInt("Id");
        daoUCFSS = new daoUsuario(this);
        u= daoUCFSS.getUsuarioById(id);

        daoSCFS = new daoSolicitud(this);
        ids= b.getInt("IdS");
        s= daoSCFS.getSolicitudById(ids);

        daoCCFS = new daoCamion(this);
        c = daoCCFS.getCamionByPlaca(s.getPlacaCamion());
        idc = c.getId();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_CFS_Volver) {
            Intent icfs1 = new Intent(FinalizarSolicitud.this, MisSolicitudesCliente.class);
            icfs1.putExtra("Id", id);
            startActivity(icfs1);
        } else if (v.getId() == R.id.button_CFS_Finalizar) {
            String rating = String.valueOf(ratingBar.getProgress());
            AlertDialog.Builder b= new AlertDialog.Builder(this);
            b.setMessage("Estas seguro de Finalizar la Solicitud");
            b.setCancelable(false);
            b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    s.setEstadoSolicitud("Finalizada");
                    s.setCalificacionCliente(rating);
                    c.setViajes(c.getViajes()+1);
                    if (daoSCFS.updateSolicitud(s) && daoCCFS.updateCamion(c)){
                        Toast.makeText(FinalizarSolicitud.this, "Solicitud Finalizada!", Toast.LENGTH_LONG).show();
                        Intent icfs2 = new Intent(FinalizarSolicitud.this, MisSolicitudesCliente.class);
                        icfs2.putExtra("Id", id);
                        startActivity(icfs2);
                        finish();
                    }else{
                        Toast.makeText(FinalizarSolicitud.this, "Error: Solicitud No Finalizada!", Toast.LENGTH_LONG).show();
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
        }
    }
}