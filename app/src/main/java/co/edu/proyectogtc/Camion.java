package co.edu.proyectogtc;

public class Camion {
    int id;
    Integer IdPropietario, IdConductor, Viajes;
    String Placa, Marca, Estado, Ubicacion;

    public Camion() {
    }

    public Camion(int idPropietario, int idConductor, int viajes, String placa, String marca, String estado, String ubicacion) {
        IdPropietario = idPropietario;
        IdConductor = idConductor;
        Viajes = viajes;
        Placa = placa;
        Marca = marca;
        Estado = estado;
        Ubicacion = ubicacion;
    }

    public boolean isNull(){
        if (IdPropietario == null || IdConductor == null || Placa.equals("") || Marca.equals("")){
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

    public Integer getIdPropietario() {
        return IdPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        IdPropietario = idPropietario;
    }

    public Integer getIdConductor() {
        return IdConductor;
    }

    public void setIdConductor(Integer idConductor) {
        IdConductor = idConductor;
    }

    public Integer getViajes() {
        return Viajes;
    }

    public void setViajes(Integer viajes) {
        Viajes = viajes;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Camion{" +
                "id=" + id +
                ", IdPropietario=" + IdPropietario +
                ", IdConductor=" + IdConductor +
                ", Viajes=" + Viajes +
                ", Placa='" + Placa + '\'' +
                ", Marca='" + Marca + '\'' +
                ", Estado='" + Estado + '\'' +
                ", Ubicacion='" + Ubicacion + '\'' +
                '}';
    }
}