package co.edu.proyectogtc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
public class daoMensaje {
    Context c;
    Mensaje m;
    ArrayList<Mensaje> lista;
    SQLiteDatabase sql;
    String bd="BDMensajes";
    String tabla="create table if not exists mensaje(id integer primary key autoincrement, idsolicitud integer, mensaje text)";

    public daoMensaje(Context c){
        this.c=c;
        sql=c.openOrCreateDatabase(bd,c.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        m=new Mensaje();
    }

    public boolean insertMensaje(Mensaje m) {
        ContentValues cv = new ContentValues();
        cv.put("idsolicitud", m.getIdSolicitud());
        cv.put("mensaje", m.getMensaje());
        return (sql.insert("mensaje", null, cv) > 0);
    }

    public Mensaje getMensajeById(int id) {
        lista = selectMensajes();
        for (Mensaje m : lista) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Mensaje> selectMensajes() {
        ArrayList<Mensaje> lista = new ArrayList<>();
        Cursor cr = sql.rawQuery("select * from mensaje", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                Mensaje m = new Mensaje();
                m.setId(cr.getInt(0));
                m.setIdSolicitud(cr.getInt(1));
                m.setMensaje(cr.getString(2));
                lista.add(m);
            } while (cr.moveToNext());
        }
        return lista;
    }

    public ArrayList<Mensaje> getMensajesByIdSolicitud(int idSolicitud) {
        ArrayList<Mensaje> lista = new ArrayList<>();
        Cursor cr = sql.rawQuery("select * from mensaje where idsolicitud=" + idSolicitud, null);
        if (cr != null && cr.moveToFirst()) {
            do {
                Mensaje m = new Mensaje();
                m.setId(cr.getInt(0));
                m.setIdSolicitud(cr.getInt(1));
                m.setMensaje(cr.getString(2));
                lista.add(m);
            } while (cr.moveToNext());
        }
        return lista;
    }

//    public List<String> getMensajesByIdSolicitud(int idSolicitud) {
//        List<String> listaMensajes = new ArrayList<>();
//        Cursor cr = sql.rawQuery("select mensaje from mensaje where idsolicitud = ?", new String[]{String.valueOf(idSolicitud)});
//        if (cr != null && cr.moveToFirst()) {
//            do {
//                listaMensajes.add(cr.getString(0));
//            } while (cr.moveToNext());
//        }
//        return listaMensajes;
//    }

    public boolean updateMensaje(Mensaje m) {
        ContentValues cv = new ContentValues();
        cv.put("idsolicitud", m.getIdSolicitud());
        cv.put("mensaje", m.getMensaje());
        return (sql.update("mensaje", cv, "id=" + m.getId(), null) > 0);
    }

    public boolean deleteMensaje(int id) {
        return (sql.delete("mensaje", "id=" + id, null) > 0);
    }

}
