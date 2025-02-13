package mx.ivai.POJO;

public class Cursos {
    private int idCurso;
    private String nombreCurso;
    private String fecha;  
    private String hora;
    private String imparte;
    private int cupo;  
    private int estatusCupo;
    private String estatusCurso;
    private String modalidad;
    private String direccion;
    private String correoSeguimiento;
    private String tipo;
    private String curso;
    private String ligaTeams;
    private String valorCurricular;
    private byte[] constancia;

    public Cursos() {
    }


    public Cursos(int idCurso, String nombreCurso, String fecha, String hora, String imparte, int cupo, int estatusCupo,
            String estatusCurso, String modalidad, String direccion, String correoSeguimiento, String tipo, String curso, String ligaTeams,
            String valorCurricular, byte[] constancia) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.fecha = fecha;
        this.hora = hora;
        this.imparte = imparte;
        this.cupo = cupo;
        this.estatusCupo = estatusCupo;
        this.estatusCurso = estatusCurso;
        this.modalidad = modalidad;
        this.direccion = direccion;
        this.correoSeguimiento = correoSeguimiento;
        this.tipo = tipo;
        this.curso = curso;
        this.ligaTeams = ligaTeams;
        this.valorCurricular = valorCurricular;
        this.constancia = constancia;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getImparte() {
        return imparte;
    }

    public void setImparte(String imparte) {
        this.imparte = imparte;
    }

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public String getEstatusCurso() {
        return estatusCurso;
    }

    public void setEstatusCurso(String estatusCurso) {
        this.estatusCurso = estatusCurso;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getCorreoSeguimiento() {
        return correoSeguimiento;
    }

    public void setCorreoSeguimiento(String correoSeguimiento) {
        this.correoSeguimiento = correoSeguimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getValorCurricular() {
        return valorCurricular;
    }

    public void setValorCurricular(String valorCurricular) {
        this.valorCurricular = valorCurricular;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public void setConstancia(byte[] constancia) {
        this.constancia = constancia;
    }

    public int getEstatusCupo() {
        return estatusCupo;
    }

    public void setEstatusCupo(int estatusCupo) {
        this.estatusCupo = estatusCupo;
    }


    public String getLigaTeams() {
        return ligaTeams;
    }


    public void setLigaTeams(String ligaTeams) {
        this.ligaTeams = ligaTeams;
    }
    
    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public byte[] getConstancia(){
        return constancia;
    }
    
    @Override
    public String toString() {
        return "Cursos [idCurso=" + idCurso + ", nombreCurso=" + nombreCurso + ", fecha=" + fecha + ", hora=" + hora
                + ", imparte=" + imparte + ", cupo=" + cupo + ", estatusCupo=" + estatusCupo + ", estatusCurso="
                + estatusCurso + ", modalidad=" + modalidad + ", correoSeguimiento="
                + correoSeguimiento + ", tipo=" + tipo + ", curso=" + curso + ", valorCurricular=" + valorCurricular + "]";
    }




    
    
}
