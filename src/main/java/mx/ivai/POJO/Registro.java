package mx.ivai.POJO;

public class Registro {
    private int idRegistro;
    private String nombre;
    private String apellidos;
    private String so;
    private String telefono;
    private String correo;
    private int idCurso;
    private String nombreCurso;
    private String lugarDeProcedencia;
    private String gradoDeEstudios;
    private String orden;
    private String areaAdquisicion;
    private String cargoPublico;
    private String genero;
    private String estado;
    private String fecha;
    private String recibirInformacion;
    private String interprete;

    public Registro(){
        
    }

    public Registro(int idRegistro, String nombre, String apellidos, String so, String telefono, String correo,
            int idCurso, String nombreCurso, String lugarDeProcedencia, String gradoDeEstudios, String orden,
            String areaAdquisicion, String cargoPublico, String genero, String estado, String fecha,
            String recibirInformacion, String interprete) {
        this.idRegistro = idRegistro;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.so = so;
        this.telefono = telefono;
        this.correo = correo;
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.lugarDeProcedencia = lugarDeProcedencia;
        this.gradoDeEstudios = gradoDeEstudios;
        this.orden = orden;
        this.areaAdquisicion = areaAdquisicion;
        this.cargoPublico = cargoPublico;
        this.genero = genero;
        this.estado = estado;
        this.fecha = fecha;
        this.recibirInformacion = recibirInformacion;
        this.interprete = interprete;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getLugarDeProcedencia() {
        return lugarDeProcedencia;
    }

    public void setLugarDeProcedencia(String lugarDeProcedencia) {
        this.lugarDeProcedencia = lugarDeProcedencia;
    }

    public String getGradoDeEstudios() {
        return gradoDeEstudios;
    }

    public void setGradoDeEstudios(String gradoDeEstudios) {
        this.gradoDeEstudios = gradoDeEstudios;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getAreaAdquisicion() {
        return areaAdquisicion;
    }

    public void setAreaAdquisicion(String areaAdquisicion) {
        this.areaAdquisicion = areaAdquisicion;
    }

    public String getCargoPublico() {
        return cargoPublico;
    }

    public void setCargoPublico(String cargoPublico) {
        this.cargoPublico = cargoPublico;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getRecibirInformacion() {
        return recibirInformacion;
    }

    public void setRecibirInformacion(String recibirInformacion) {
        this.recibirInformacion = recibirInformacion;
    }

    public String getInterprete() {
        return interprete;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }
    
    

}
