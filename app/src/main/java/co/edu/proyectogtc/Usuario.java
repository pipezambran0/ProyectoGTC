package co.edu.proyectogtc;

public class Usuario {
    int id;
    String Nombre, Usuario, Password, Correo, Cedula, Rol, Genero;

    public Usuario() {
    }

    public Usuario(String nombre, String usuario, String password, String correo, String cedula, String rol, String genero) {
        Nombre = nombre;
        Usuario = usuario;
        Password = password;
        Correo = correo;
        Cedula = cedula;
        Rol = rol;
        Genero = genero;
    }

    public boolean isNull(){
        if (Nombre.equals("") || Usuario.equals("") || Password.equals("") || Correo.equals("") || Cedula.equals("")){
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

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                ", Usuario='" + Usuario + '\'' +
                ", Password='" + Password + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Cedula='" + Cedula + '\'' +
                ", Rol='" + Rol + '\'' +
                ", Genero='" + Genero + '\'' +
                '}';
    }
}