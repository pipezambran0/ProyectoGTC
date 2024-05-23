package co.edu.proyectogtc;

public class Mensaje {
    int id;
    Integer IdSolicitud;
    String Mensaje;

    public Mensaje() {
    }

    public Mensaje(Integer idSolicitud, String mensaje) {
        IdSolicitud = idSolicitud;
        Mensaje = mensaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdSolicitud() {
        return IdSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        IdSolicitud = idSolicitud;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", IdSolicitud=" + IdSolicitud +
                ", Mensaje='" + Mensaje + '\'' +
                '}';
    }
}
