package co.edu.proyectogtc;

public class Solicitud {
    int id;
    Integer IdClienteS, IdPropietarioS, IdConductorS, IdChat;
    String NombreSolicitud;
    String FechaCarga, FechaDescarga;
    String LugarCarga, LugarDescarga;
    String TipoMaterial, PesoMaterial, TamañoMaterial;
    String EstadoSolicitud;
    String PlacaCamion;
    String CalificacionCliente;

    public Solicitud(){

    }

    public Solicitud(Integer idClienteS, Integer idPropietarioS, Integer idConductorS, Integer idChat, String nombreSolicitud, String fechaCarga, String fechaDescarga, String lugarCarga, String lugarDescarga, String tipoMaterial, String pesoMaterial, String tamañoMaterial, String estadoSolicitud, String placaCamion, String calificacionCliente) {
        IdClienteS = idClienteS;
        IdPropietarioS = idPropietarioS;
        IdConductorS = idConductorS;
        IdChat = idChat;
        NombreSolicitud = nombreSolicitud;
        FechaCarga = fechaCarga;
        FechaDescarga = fechaDescarga;
        LugarCarga = lugarCarga;
        LugarDescarga = lugarDescarga;
        TipoMaterial = tipoMaterial;
        PesoMaterial = pesoMaterial;
        TamañoMaterial = tamañoMaterial;
        EstadoSolicitud = estadoSolicitud;
        PlacaCamion = placaCamion;
        CalificacionCliente = calificacionCliente;
    }

    public boolean isNull(){
        if (IdClienteS == null || FechaCarga.equals("") || FechaDescarga.equals("") || LugarCarga.equals("") || LugarDescarga.equals("") || TipoMaterial.equals("") || PesoMaterial.equals("") || TamañoMaterial.equals("")){
            return  false;
        }else{
            return true;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdClienteS() {
        return IdClienteS;
    }

    public void setIdClienteS(Integer idClienteS) {
        IdClienteS = idClienteS;
    }

    public Integer getIdPropietarioS() {
        return IdPropietarioS;
    }

    public void setIdPropietarioS(Integer idPropietarioS) {
        IdPropietarioS = idPropietarioS;
    }

    public Integer getIdConductorS() {
        return IdConductorS;
    }

    public void setIdConductorS(Integer idConductorS) {
        IdConductorS = idConductorS;
    }

    public Integer getIdChat() {
        return IdChat;
    }

    public void setIdChat(Integer idChat) {
        IdChat = idChat;
    }

    public String getNombreSolicitud() {
        return NombreSolicitud;
    }

    public void setNombreSolicitud(String nombreSolicitud) {
        NombreSolicitud = nombreSolicitud;
    }

    public String getFechaCarga() {
        return FechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        FechaCarga = fechaCarga;
    }

    public String getFechaDescarga() {
        return FechaDescarga;
    }

    public void setFechaDescarga(String fechaDescarga) {
        FechaDescarga = fechaDescarga;
    }

    public String getLugarCarga() {
        return LugarCarga;
    }

    public void setLugarCarga(String lugarCarga) {
        LugarCarga = lugarCarga;
    }

    public String getLugarDescarga() {
        return LugarDescarga;
    }

    public void setLugarDescarga(String lugarDescarga) {
        LugarDescarga = lugarDescarga;
    }

    public String getTipoMaterial() {
        return TipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        TipoMaterial = tipoMaterial;
    }

    public String getPesoMaterial() {
        return PesoMaterial;
    }

    public void setPesoMaterial(String pesoMaterial) {
        PesoMaterial = pesoMaterial;
    }

    public String getTamañoMaterial() {
        return TamañoMaterial;
    }

    public void setTamañoMaterial(String tamañoMaterial) {
        TamañoMaterial = tamañoMaterial;
    }

    public String getEstadoSolicitud() {
        return EstadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        EstadoSolicitud = estadoSolicitud;
    }

    public String getPlacaCamion() {
        return PlacaCamion;
    }

    public void setPlacaCamion(String placaCamion) {
        PlacaCamion = placaCamion;
    }

    public String getCalificacionCliente() {
        return CalificacionCliente;
    }

    public void setCalificacionCliente(String calificacionCliente) {
        CalificacionCliente = calificacionCliente;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", IdClienteS=" + IdClienteS +
                ", IdPropietarioS=" + IdPropietarioS +
                ", IdConductorS=" + IdConductorS +
                ", IdChat=" + IdChat +
                ", NombreSolicitud='" + NombreSolicitud + '\'' +
                ", FechaCarga='" + FechaCarga + '\'' +
                ", FechaDescarga='" + FechaDescarga + '\'' +
                ", LugarCarga='" + LugarCarga + '\'' +
                ", LugarDescarga='" + LugarDescarga + '\'' +
                ", TipoMaterial='" + TipoMaterial + '\'' +
                ", PesoMaterial='" + PesoMaterial + '\'' +
                ", TamañoMaterial='" + TamañoMaterial + '\'' +
                ", EstadoSolicitud='" + EstadoSolicitud + '\'' +
                ", PlacaCamion='" + PlacaCamion + '\'' +
                ", CalificacionCliente='" + CalificacionCliente + '\'' +
                '}';
    }
}