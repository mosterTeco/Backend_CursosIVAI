package mx.ivai;

public class Usuario {
    private int idUsuario;
    private String usuario;
    private String password;
    private String nombre;
    private String rol; 

    public Usuario(int idUsuario, String usuario, String password, String nombre, String rol) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getRol() {
        return rol; 
    }
    public void setRol(String rol) { 
        this.rol = rol;
    }
}
