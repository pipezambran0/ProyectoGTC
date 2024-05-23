package co.edu.proyectogtc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class daoCamion {
    Context c;
    Camion cc;
    ArrayList<Camion> lista;
    SQLiteDatabase sql;
    String bd="BDCamiones";
    String tabla="create table if not exists camion(id integer primary key autoincrement, idpropietario integer, idconductor integer, viajes integer, placa text, marca text, estado text, ubicacion text)";

    public daoCamion(Context c){
        this.c=c;
        sql=c.openOrCreateDatabase(bd,c.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        cc=new Camion();
    }

    public boolean insertCamion(Camion cc){
        if (buscarC(cc.getPlaca())==0){
            ContentValues cv = new ContentValues();
            cv.put("idpropietario", cc.getIdPropietario());
            cv.put("idconductor", cc.getIdConductor());
            cv.put("viajes", cc.getViajes());
            cv.put("placa", cc.getPlaca());
            cv.put("marca", cc.getMarca());
            cv.put("estado", cc.getEstado());
            cv.put("ubicacion", cc.getUbicacion());
            return (sql.insert("camion", null, cv) > 0);
        }else{
            return false;
        }
    }

    public int buscarC(String placa){
        int x=0;
        lista=selectCamiones();
        for (Camion ca:lista) {
            if(ca.getPlaca().equals(placa)){
                x++;
            }
        }
        return x;
    }

    public ArrayList<Camion> selectCamiones(){
        ArrayList<Camion> lista=new ArrayList<Camion>();
        lista.clear();
        Cursor cr=sql.rawQuery("select * from camion", null);
        if(cr!=null && cr.moveToFirst()){
            do{
                Camion c=new Camion();
                c.setId(cr.getInt(0));
                c.setIdPropietario(cr.getInt(1));
                c.setIdConductor(cr.getInt(2));
                c.setViajes(cr.getInt(3));
                c.setPlaca(cr.getString(4));
                c.setMarca(cr.getString(5));
                c.setEstado(cr.getString(6));
                c.setUbicacion(cr.getString(7));
                lista.add(c);
            }while(cr.moveToNext());
        }
        return lista;
    }

    public Camion getCamionById(int id){
        lista= selectCamiones();
        for (Camion c:lista) {
            if(c.getId()==id){
                return c;
            }
        }
        return null;
    }

    public Camion getCamionByPlaca(String placa){
        lista= selectCamiones();
        for (Camion c:lista) {
            if(c.getPlaca().equals(placa)){
                return c;
            }
        }
        return null;
    }

    public List<String> getPlacasDeCamionesByIdPropietario(int idP) {
        List<String> listaPlacas = new ArrayList<>();

        ArrayList<Camion> listaDeCamiones = selectCamiones();

        for (Camion camion : listaDeCamiones) {
            if(camion.getIdPropietario() == idP){
                listaPlacas.add(camion.getPlaca());
            }
        }

        return listaPlacas;
    }

    public List<String> getPlacasDeCamionesByIdConductor(int idC) {
        List<String> listaPlacas = new ArrayList<>();

        ArrayList<Camion> listaDeCamiones = selectCamiones();

        for (Camion camion : listaDeCamiones) {
            if(camion.getIdConductor() == idC){
                listaPlacas.add(camion.getPlaca());
            }
        }

        return listaPlacas;
    }

    public int obtenerIdCamionPorPlaca(String placa) {
        lista= selectCamiones();
        for (Camion camion : lista) {
            if (camion.getPlaca().equals(placa)) {
                return camion.getId();
            }
        }
        return -1;
    }

    public boolean updateCamion(Camion cc){
        ContentValues cv = new ContentValues();
        cv.put("idpropietario", cc.getIdPropietario());
        cv.put("idconductor", cc.getIdConductor());
        cv.put("viajes", cc.getViajes());
        cv.put("placa", cc.getPlaca());
        cv.put("marca", cc.getMarca());
        cv.put("estado", cc.getEstado());
        cv.put("ubicacion", cc.getUbicacion());
        return (sql.update("camion", cv, "id="+cc.getId(), null) > 0);
    }

    public boolean deleteCamion(int id){
        return (sql.delete("camion", "id="+id, null) > 0);
    }
}