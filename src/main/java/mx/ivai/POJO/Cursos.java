package mx.ivai.POJO;

import java.time.LocalDateTime;

public class Cursos {
    private int idCurso;
    private String nombreCurso;
    private String fecha;  
    private String hora;
    private String imparte;
    private int cupo;  
    private String estatusCupo;
    private String estatusCurso;
    private String observaciones;
    private String lugar;
    private String correoSeguimiento;
    private String programa;
    private String archivo;
    private String tipo;
    private String curso;
    private String valorCurricular;
    private String fechaLetra;

    

    public Cursos() {
    }

    public Cursos(int idCurso, String nombreCurso, String fecha, String hora, String imparte, Integer cupo,
            String estatusCupo, String estatusCurso, String observaciones, String lugar, String correoSeguimiento,
            String programa, String archivo, String tipo, String curso, String valorCurricular, String fechaLetra) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.fecha = fecha;  
        this.hora = hora;
        this.imparte = imparte;
        this.cupo = cupo;
        this.estatusCupo = estatusCupo;
        this.estatusCurso = estatusCurso;
        this.observaciones = observaciones;
        this.lugar = lugar;
        this.correoSeguimiento = correoSeguimiento;
        this.programa = programa;
        this.archivo = archivo;
        this.tipo = tipo;
        this.curso = curso;
        this.valorCurricular = valorCurricular;
        this.fechaLetra = fechaLetra;
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

    public String getEstatusCupo() {
        return estatusCupo;
    }

    public void setEstatusCupo(String estatusCupo) {
        this.estatusCupo = estatusCupo;
    }

    public String getEstatusCurso() {
        return estatusCurso;
    }

    public void setEstatusCurso(String estatusCurso) {
        this.estatusCurso = estatusCurso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getCorreoSeguimiento() {
        return correoSeguimiento;
    }

    public void setCorreoSeguimiento(String correoSeguimiento) {
        this.correoSeguimiento = correoSeguimiento;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
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

    public String getFechaLetra() {
        return fechaLetra;
    }

    public void setFechaLetra(String fechaLetra) {
        this.fechaLetra = fechaLetra;
    }

    @Override
    public String toString() {
        return "Cursos [idCurso=" + idCurso + ", nombreCurso=" + nombreCurso + ", fecha=" + fecha + ", hora=" + hora
                + ", imparte=" + imparte + ", cupo=" + cupo + ", estatusCupo=" + estatusCupo + ", estatusCurso="
                + estatusCurso + ", observaciones=" + observaciones + ", lugar=" + lugar + ", correoSeguimiento="
                + correoSeguimiento + ", programa=" + programa + ", archivo=" + archivo + ", tipo=" + tipo + ", curso="
                + curso + ", valorCurricular=" + valorCurricular + ", fechaLetra=" + fechaLetra + "]";
    }

    
    
}
