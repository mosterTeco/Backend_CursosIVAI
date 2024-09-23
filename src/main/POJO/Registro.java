package mx.ivai;

public class Registro {
    private int idRegistro;
    private String claveRegistro;
    private String nombre;
    private String apellidos;
    private String so;
    private String telefono;
    private String correo;
    private Integer idCurso;
    private String opcional;
    private String procedencia;
    private String gradoEstudios;
    private String ordenGobierno;
    private String area;
    private String cargo;
    private String titularSO;
    private String titularUA;
    private String comite;
    private String sexo;
    private String estado;
    private String asistio;
    private String otra1;
    private String otra2;
    private String inforEventos;
    private String inter;

    public Registro(int idRegistro, String claveRegistro, String nombre, String apellidos, String so, String telefono,
            String correo, Integer idCurso, String opcional, String procedencia, String gradoEstudios,
            String ordenGobierno, String area, String cargo, String titularSO, String titularUA, String comite,
            String sexo, String estado, String asistio, String otra1, String otra2, String inforEventos, String inter) {
        this.idRegistro = idRegistro;
        this.claveRegistro = claveRegistro;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.so = so;
        this.telefono = telefono;
        this.correo = correo;
        this.idCurso = idCurso;
        this.opcional = opcional;
        this.procedencia = procedencia;
        this.gradoEstudios = gradoEstudios;
        this.ordenGobierno = ordenGobierno;
        this.area = area;
        this.cargo = cargo;
        this.titularSO = titularSO;
        this.titularUA = titularUA;
        this.comite = comite;
        this.sexo = sexo;
        this.estado = estado;
        this.asistio = asistio;
        this.otra1 = otra1;
        this.otra2 = otra2;
        this.inforEventos = inforEventos;
        this.inter = inter;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getClaveRegistro() {
        return claveRegistro;
    }

    public void setClaveRegistro(String claveRegistro) {
        this.claveRegistro = claveRegistro;
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

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getOpcional() {
        return opcional;
    }

    public void setOpcional(String opcional) {
        this.opcional = opcional;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getGradoEstudios() {
        return gradoEstudios;
    }

    public void setGradoEstudios(String gradoEstudios) {
        this.gradoEstudios = gradoEstudios;
    }

    public String getOrdenGobierno() {
        return ordenGobierno;
    }

    public void setOrdenGobierno(String ordenGobierno) {
        this.ordenGobierno = ordenGobierno;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTitularSO() {
        return titularSO;
    }

    public void setTitularSO(String titularSO) {
        this.titularSO = titularSO;
    }

    public String getTitularUA() {
        return titularUA;
    }

    public void setTitularUA(String titularUA) {
        this.titularUA = titularUA;
    }

    public String getComite() {
        return comite;
    }

    public void setComite(String comite) {
        this.comite = comite;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAsistio() {
        return asistio;
    }

    public void setAsistio(String asistio) {
        this.asistio = asistio;
    }

    public String getOtra1() {
        return otra1;
    }

    public void setOtra1(String otra1) {
        this.otra1 = otra1;
    }

    public String getOtra2() {
        return otra2;
    }

    public void setOtra2(String otra2) {
        this.otra2 = otra2;
    }

    public String getInforEventos() {
        return inforEventos;
    }

    public void setInforEventos(String inforEventos) {
        this.inforEventos = inforEventos;
    }

    public String getInter() {
        return inter;
    }

    public void setInter(String inter) {
        this.inter = inter;
    }

}
