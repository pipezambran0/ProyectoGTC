package co.edu.proyectogtc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class daoSolicitud {
    Context c;
    Solicitud s;
    ArrayList<Solicitud> lista;
    SQLiteDatabase sql;
    String bd="BDSolicitud";
    String tabla = "create table if not exists solicitud(id integer primary key autoincrement, idclientes integer, idpropietarios integer, idconductors integer, idchat integer, nombresolicitud text, fechacarga text, fechadescarga text, lugarcarga text, lugardescarga text, tipomaterial text, pesomaterial text, tamañomaterial text, estadosolicitud text, placacamion text, calificacioncliente text)";

    public daoSolicitud(Context c){
        this.c=c;
        sql=c.openOrCreateDatabase(bd,c.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        s=new Solicitud();
    }

    public boolean insertSolicitud(Solicitud s){
        if (buscarS(s.getNombreSolicitud())==0){
            ContentValues cv = new ContentValues();
            cv.put("idclientes", s.getIdClienteS());
            cv.put("idpropietarios", s.getIdPropietarioS());
            cv.put("idconductors", s.getIdConductorS());
            cv.put("idchat", s.getIdChat());
            cv.put("nombresolicitud", s.getNombreSolicitud());
            cv.put("fechacarga", s.getFechaCarga());
            cv.put("fechadescarga", s.getFechaDescarga());
            cv.put("lugarcarga", s.getLugarCarga());
            cv.put("lugardescarga", s.getLugarDescarga());
            cv.put("tipomaterial", s.getTipoMaterial());
            cv.put("pesomaterial", s.getPesoMaterial());
            cv.put("tamañomaterial", s.getTamañoMaterial());
            cv.put("estadosolicitud", s.getEstadoSolicitud());
            cv.put("placacamion", s.getPlacaCamion());
            cv.put("calificacioncliente", s.getCalificacionCliente());
            return (sql.insert("solicitud", null, cv) > 0);
        }else{
            return false;
        }
    }

    public int buscarS(String nombreSolicitud){
        int x=0;
        lista=selectSolicitudes();
        for (Solicitud so:lista) {
            if(so.getNombreSolicitud().equals(nombreSolicitud)){
                x++;
            }
        }
        return x;
    }

    public ArrayList<Solicitud> selectSolicitudes(){
        ArrayList<Solicitud> lista=new ArrayList<Solicitud>();
        lista.clear();
        Cursor cr=sql.rawQuery("select * from solicitud", null);
        if(cr!=null && cr.moveToFirst()){
            do{
                Solicitud s=new Solicitud();
                s.setId(cr.getInt(0));
                s.setIdClienteS(cr.getInt(1));
                s.setIdPropietarioS(cr.getInt(2));
                s.setIdConductorS(cr.getInt(3));
                s.setIdChat(cr.getInt(4));
                s.setNombreSolicitud(cr.getString(5));
                s.setFechaCarga(cr.getString(6));
                s.setFechaDescarga(cr.getString(7));
                s.setLugarCarga(cr.getString(8));
                s.setLugarDescarga(cr.getString(9));
                s.setTipoMaterial(cr.getString(10));
                s.setPesoMaterial(cr.getString(11));
                s.setTamañoMaterial(cr.getString(12));
                s.setEstadoSolicitud(cr.getString(13));
                s.setPlacaCamion(cr.getString(14));
                s.setCalificacionCliente(cr.getString(15));
                lista.add(s);
            }while(cr.moveToNext());
        }
        return lista;
    }

    public ArrayList<Solicitud> selectSolicitudesDisponibles(){
        ArrayList<Solicitud> lista = new ArrayList<Solicitud>();
        lista.clear();
        Cursor cr = sql.rawQuery("SELECT * FROM solicitud WHERE EstadoSolicitud = 'Disponible'", null);
        if(cr != null && cr.moveToFirst()){
            do{
                Solicitud s = new Solicitud();
                s.setId(cr.getInt(0));
                s.setIdClienteS(cr.getInt(1));
                s.setIdPropietarioS(cr.getInt(2));
                s.setIdConductorS(cr.getInt(3));
                s.setIdChat(cr.getInt(4));
                s.setNombreSolicitud(cr.getString(5));
                s.setFechaCarga(cr.getString(6));
                s.setFechaDescarga(cr.getString(7));
                s.setLugarCarga(cr.getString(8));
                s.setLugarDescarga(cr.getString(9));
                s.setTipoMaterial(cr.getString(10));
                s.setPesoMaterial(cr.getString(11));
                s.setTamañoMaterial(cr.getString(12));
                s.setEstadoSolicitud(cr.getString(13));
                s.setPlacaCamion(cr.getString(14));
                s.setCalificacionCliente(cr.getString(15));
                lista.add(s);
            } while(cr.moveToNext());
        }
        return lista;
    }


    public List<String> getNombreSolicitudByIdClienteS(int idC) {
        List<String> listaNombresSolicitud = new ArrayList<>();

        ArrayList<Solicitud> listaDeSolicitudes = selectSolicitudes();

        for (Solicitud solicitud : listaDeSolicitudes) {
            if(solicitud.getIdClienteS() == idC){
                listaNombresSolicitud.add(solicitud.getNombreSolicitud());
            }
        }

        return listaNombresSolicitud;
    }

    public List<String> getNombreSolicitudByIdPropietarioS(int idP) {
        List<String> listaNombresSolicitud = new ArrayList<>();

        ArrayList<Solicitud> listaDeSolicitudes = selectSolicitudes();

        for (Solicitud solicitud : listaDeSolicitudes) {
            if(solicitud.getIdPropietarioS() == idP){
                listaNombresSolicitud.add(solicitud.getNombreSolicitud());
            }
        }

        return listaNombresSolicitud;
    }

    public List<String> getNombreSolicitudByIdConductorS(int idC) {
        List<String> listaNombresSolicitud = new ArrayList<>();

        ArrayList<Solicitud> listaDeSolicitudes = selectSolicitudes();

        for (Solicitud solicitud : listaDeSolicitudes) {
            if(solicitud.getIdConductorS() == idC){
                listaNombresSolicitud.add(solicitud.getNombreSolicitud());
            }
        }

        return listaNombresSolicitud;
    }

    public int obtenerIdSolicitudByNombreSolicitud(String nombreSolicitud) {
        lista= selectSolicitudes();
        for (Solicitud solicitud : lista) {
            if (solicitud.getNombreSolicitud().equals(nombreSolicitud)) {
                return solicitud.getId();
            }
        }
        return -1;
    }

    public Solicitud getSolicitudById(int id) {
        lista = selectSolicitudes();
        for (Solicitud solicitud : lista) {
            if (solicitud.getId() == id) {
                return solicitud;
            }
        }
        return null;
    }

    public boolean updateSolicitud(Solicitud s) {
        ContentValues cv = new ContentValues();
        cv.put("idclientes", s.getIdClienteS());
        cv.put("idpropietarios", s.getIdPropietarioS());
        cv.put("idconductors", s.getIdConductorS());
        cv.put("idchat", s.getIdChat());
        cv.put("nombresolicitud", s.getNombreSolicitud());
        cv.put("fechacarga", s.getFechaCarga());
        cv.put("fechadescarga", s.getFechaDescarga());
        cv.put("lugarcarga", s.getLugarCarga());
        cv.put("lugardescarga", s.getLugarDescarga());
        cv.put("tipomaterial", s.getTipoMaterial());
        cv.put("pesomaterial", s.getPesoMaterial());
        cv.put("tamañomaterial", s.getTamañoMaterial());
        cv.put("estadosolicitud", s.getEstadoSolicitud());
        cv.put("placacamion", s.getPlacaCamion());
        cv.put("calificacioncliente", s.getCalificacionCliente());
        return (sql.update("solicitud", cv, "id=" + s.getId(), null) > 0);
    }

    public boolean deleteSolicitud(int id){
        return (sql.delete("solicitud", "id="+id, null) > 0);
    }
}