package mx.ivai;

import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mx.ivai.Mail;

import mx.ivai.POJO.*;

public class Dao {

    private static Conexion c = new Conexion();

    public static Usuario datosUsuario(String correoUsuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            conn = c.getConnection();
            String query = "SELECT * FROM Usuario WHERE Usuario = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, correoUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("IdUsuario"),
                        rs.getString("Usuario"),
                        rs.getString("Pass"),
                        rs.getString("Nombre"),
                        rs.getString("Rol"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al obtener datos del usuario: " + e.toString());

        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return usuario;
    }

    // !METODO PARA VALIDAR QUE EL USUARIO ESTÁ REGISTRADO
    public static boolean usuarioRegistrado(String email, String contrasena) {
        boolean respuesta = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = c.getConnection();
            String query = "SELECT Usuario, Pass FROM Usuario WHERE Usuario = ? AND Pass = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, contrasena);

            rs = ps.executeQuery();

            if (rs.next()) {
                respuesta = true;
            }

        } catch (Exception ex) {
            System.out.println("Error al iniciar sesión: " + ex.toString());
            ex.printStackTrace();

        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return respuesta;
    }

    // !METODO PARA AGREGAR NUEVOS USUARIOS A LA TABLA DE REGISTRO
    public static String crearRegistro(Registro reg) {
        PreparedStatement stm = null;
        Connection conn = null;
        String msj = "";
        LocalDateTime fecha = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = fecha.format(formato);

        conn = c.getConnection();

        Cursos curso = obtenerCurso(reg.getIdCurso());
        if (curso.getEstatusCupo() > 0) {
            try {
                String sql = "INSERT INTO Registro ( Nombre, Apellidos, SO, Telefono, Correo, IdCurso, Procedencia, GradoEstudios, OrdenGobierno, Area, Cargo, Genero, Estado, Fecha, InfoEventos, Interprete) "
                        +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, reg.getNombre());
                stm.setString(2, reg.getApellidos());
                stm.setString(3, reg.getSo());
                stm.setString(4, reg.getTelefono());
                stm.setString(5, reg.getCorreo());
                stm.setInt(6, reg.getIdCurso());
                stm.setString(7, reg.getLugarDeProcedencia());
                stm.setString(8, reg.getGradoDeEstudios());
                stm.setString(9, reg.getOrden());
                stm.setString(10, reg.getAreaAdquisicion());
                stm.setString(11, reg.getCargoPublico());
                stm.setString(12, reg.getGenero());
                stm.setString(13, reg.getEstado());
                stm.setString(14, fechaFormateada);
                stm.setString(15, reg.getRecibirInformacion());
                stm.setString(16, reg.getInterprete());

                if (stm.executeUpdate() > 0) {
                    msj = "Registro Correcto";
                    reducirCupo(reg.getIdCurso());
                    Mail.inicializarMail(reg, obtenerCurso(reg.getIdCurso()));
                } else
                    msj = "No se ha podido completar el registro";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stm != null) {
                    try {
                        stm.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stm = null;
                }
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            msj = "Curso lleno";
        }

        return msj;
    }

    public static Cursos obtenerCurso(int idCurso) {
        PreparedStatement stm = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cursos curso = new Cursos();
        conn = c.getConnection();
        try {
            String query = "SELECT * FROM curso WHERE idCurso = " + idCurso;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                curso.setIdCurso(rs.getInt("IdCurso"));
                curso.setNombreCurso(rs.getString("NombreCurso"));
                curso.setFecha(rs.getString("Fecha"));
                curso.setHora(rs.getString("Hora"));
                curso.setImparte(rs.getString("Imparte"));
                curso.setCupo(rs.getInt("Cupo"));
                curso.setEstatusCupo(rs.getInt("EstatusCupo"));
                curso.setEstatusCurso(rs.getString("EstatusCurso"));
                curso.setModalidad(rs.getString("Modalidad"));
                curso.setDireccion(rs.getString("Direccion"));
                curso.setCorreoSeguimiento(rs.getString("CorreoSeguimiento"));
                curso.setLigaTeams(rs.getString("LigaTeams"));
                curso.setTipo(rs.getString("Tipo"));
                curso.setCurso(rs.getString("Curso"));
                curso.setValorCurricular(rs.getString("ValorCurricular"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return curso;
    }

    private static String reducirCupo(int idCurso) {
        PreparedStatement stm = null;
        Connection conn = null;
        int cupoDisponible = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String msj = "";
        conn = c.getConnection();
        try {
            String query = "SELECT EstatusCupo FROM curso WHERE idCurso = " + idCurso;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                cupoDisponible = rs.getInt("EstatusCupo") - 1;
            }

            query = "UPDATE curso SET EstatusCupo = ? where idCurso = ?";
            stm = conn.prepareStatement(query);
            stm.setInt(1, cupoDisponible);
            stm.setInt(2, idCurso);

            if (stm.executeUpdate() > 0) {
                msj = "Cupo reducido con éxito";
            } else {
                msj = "No se pudo reducir el cupo";
            }

        } catch (Exception e) {
            System.out.println(e);
            msj = "Error: " + e.getMessage();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return msj;
    }

    public static ArrayList<TipoCurso> obtenerTiposCursos() {
        PreparedStatement stm = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<TipoCurso> TiposCurso = new ArrayList<TipoCurso>();

        conn = c.getConnection();

        try {

            String query = "SELECT * FROM TIPOCURSO";
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                TipoCurso tipo = new TipoCurso(rs.getInt("Id"), rs.getString("Tipo"));
                TiposCurso.add(tipo);
            }

        } catch (Exception ex) {
            System.out.println("Error al iniciar sesión: " + ex.toString());
            ex.printStackTrace();

        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return TiposCurso;

    }

    // Método para obtener el listado de cursos que se pueden impartir
    public static ArrayList<String> obtenerTiposCurso() {
        PreparedStatement stm = null;
        Connection conn = null;
        String tipo = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> TiposCurso = new ArrayList<String>();

        conn = c.getConnection();

        try {

            String query = "SELECT Tipo FROM TIPOCURSO";
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                tipo = rs.getString("tipo");
                TiposCurso.add(tipo);
            }

        } catch (Exception ex) {
            System.out.println("Error al iniciar sesión: " + ex.toString());
            ex.printStackTrace();

        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return TiposCurso;

    }

    // Metodo que devuelve todos los registros de un curso
    public static ArrayList<Registro> obtenerRegistros(int idCurso) {

        PreparedStatement stm = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Registro> registros = new ArrayList<Registro>();

        conn = c.getConnection();

        try {

            String query = "SELECT IdRegistro, Nombre, Apellidos, SO, Telefono, Correo, Registro.IdCurso, Curso.NombreCurso,Procedencia,"
                    + "GradoEstudios, OrdenGobierno, Area, Cargo, Genero, Estado, Registro.Fecha, InfoEventos, Interprete, Asistencia FROM Registro,"
                    + "Curso WHERE Registro.IdCurso = Curso.IdCurso AND Registro.IdCurso =" + idCurso;
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                Registro registro = new Registro();
                registro.setIdRegistro(rs.getInt("IdRegistro"));
                registro.setNombre(rs.getString("Nombre"));
                registro.setApellidos(rs.getString("Apellidos"));
                registro.setSo(rs.getString("SO"));
                registro.setTelefono(rs.getString("Telefono"));
                registro.setCorreo(rs.getString("Correo"));
                registro.setIdCurso(rs.getInt("IdCurso"));
                registro.setNombreCurso(rs.getString("NombreCurso"));
                registro.setLugarDeProcedencia(rs.getString("Procedencia"));
                registro.setGradoDeEstudios(rs.getString("GradoEstudios"));
                registro.setOrden(rs.getString("OrdenGobierno"));
                registro.setAreaAdquisicion(rs.getString("Area"));
                registro.setCargoPublico(rs.getString("Cargo"));
                registro.setGenero(rs.getString("Genero"));
                registro.setEstado(rs.getString("Estado"));
                registro.setFecha(rs.getString("Fecha"));
                registro.setRecibirInformacion(rs.getString("InfoEventos"));
                registro.setInterprete(rs.getString("Interprete"));
                registro.setAsistencia(rs.getString("Asistencia"));
                registros.add(registro);
            }

        } catch (Exception ex) {
            System.out.println("Error al obtener los registros de la tabla registro: " + ex.toString());
            ex.printStackTrace();

        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return registros;

    }

    // Método para obtener los cursos registrados en la base de datos
    public static ArrayList<Cursos> obtenerCursos() {
        PreparedStatement stm = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Cursos> cursos = new ArrayList<Cursos>();

        conn = c.getConnection();

        try {

            String query = "SELECT * FROM Curso";
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setIdCurso(rs.getInt("IdCurso"));
                curso.setNombreCurso(rs.getString("NombreCurso"));
                curso.setFecha(rs.getString("Fecha"));
                curso.setHora(rs.getString("Hora"));
                curso.setImparte(rs.getString("Imparte"));
                curso.setCupo(rs.getInt("Cupo"));
                curso.setEstatusCupo(rs.getInt("EstatusCupo"));
                curso.setEstatusCurso(rs.getString("EstatusCurso"));
                curso.setModalidad(rs.getString("Modalidad"));
                curso.setDireccion(rs.getString("Direccion"));
                curso.setCorreoSeguimiento(rs.getString("CorreoSeguimiento"));
                curso.setTipo(rs.getString("Tipo"));
                curso.setCurso(rs.getString("Curso"));
                curso.setLigaTeams(rs.getString("LigaTeams"));
                curso.setValorCurricular(rs.getString("ValorCurricular"));
                cursos.add(curso);
            }

        } catch (Exception ex) {
            System.out.println("Error al obtener los registros de la tabla cursos: " + ex.toString());
            ex.printStackTrace();

        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return cursos;

    }

    // Método para registrar un curso en la base de datos
    public static String registrarCurso(Cursos curso) {
        PreparedStatement stm = null;
        Connection conn = null;
        String msj = "";
    
        conn = c.getConnection();
    
        try {
            String sql = "INSERT INTO Curso (NombreCurso, Fecha, Hora, Imparte, Cupo, EstatusCupo, EstatusCurso, Modalidad, Direccion, CorreoSeguimiento, Tipo, Curso, LigaTeams, ValorCurricular, Constancia) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'cursos.ivai@gmail.com', ?, ?, ?, ?, ?)";
    
            stm = conn.prepareStatement(sql);
    
            stm.setString(1, curso.getNombreCurso());
            stm.setString(2, curso.getFecha());
            stm.setString(3, curso.getHora());
            stm.setString(4, curso.getImparte());
            stm.setInt(5, curso.getCupo());
            stm.setInt(6, curso.getEstatusCupo());
            stm.setString(7, curso.getEstatusCurso());
            stm.setString(8, curso.getModalidad());
            stm.setString(9, curso.getDireccion());
            stm.setString(10, curso.getTipo());
            stm.setString(11, curso.getCurso());
            stm.setString(12, curso.getLigaTeams());
            stm.setString(13, curso.getValorCurricular());
            
            stm.setBytes(14, curso.getConstancia());
    
            if (stm.executeUpdate() > 0)
                msj = "Curso registrado";
            else
                msj = "Error al registrar el curso";
    
        } catch (Exception e) {
            System.out.println(e);
            msj = "Error: " + e.getMessage();
        } finally {
            try {
                if (stm != null) stm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return msj;
    }
    

    // Método que retorna todos los estados
    public static List<String> obtenerEstados() {
        List<String> estados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = c.getConnection();
            String query = "SELECT Estado FROM Estados";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                estados.add(rs.getString("Estado"));
            }

        } catch (Exception ex) {
            System.out.println("Error al obtener los estados: " + ex.toString());
            ex.printStackTrace();

        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return estados;
    }

    public static String editarCurso(Cursos curso) {
        PreparedStatement stm = null;
        Connection conn = null;
        ResultSet rs = null;
        String msj = "";
        Integer cupo = 0;
        Integer restantes = 0;
        Integer diferencia = 0;

        conn = c.getConnection();

        try {
            String query = "SELECT Cupo, EstatusCupo FROM Curso WHERE idCurso = ?";
            stm = conn.prepareStatement(query);
            stm.setInt(1, curso.getIdCurso());
            rs = stm.executeQuery();
            while (rs.next()) {
                cupo = rs.getInt("Cupo");
                restantes = rs.getInt("EstatusCupo");
            }
            diferencia = cupo - restantes;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            Integer cupoFinal = curso.getCupo() - diferencia;
            String sql = "UPDATE Curso SET NombreCurso = ?, Fecha = ?, Hora = ?, Imparte = ?, Cupo = ?, EstatusCupo = ?, EstatusCurso = ?, "
                    + " Modalidad = ?, Direccion = ?, CorreoSeguimiento = ?, Tipo = ?, Curso = ?, LigaTeams = ?, ValorCurricular = ?"
                    + "WHERE IdCurso = ?";

            stm = conn.prepareStatement(sql);

            stm.setString(1, curso.getNombreCurso());
            stm.setString(2, curso.getFecha());
            stm.setString(3, curso.getHora());
            stm.setString(4, curso.getImparte());
            stm.setInt(5, curso.getCupo());
            stm.setInt(6, cupoFinal);
            stm.setString(7, curso.getEstatusCurso());
            stm.setString(8, curso.getModalidad());
            stm.setString(9, curso.getDireccion());
            stm.setString(10, curso.getCorreoSeguimiento());
            stm.setString(11, curso.getTipo());
            stm.setString(12, curso.getCurso());
            stm.setString(13, curso.getLigaTeams());
            stm.setString(14, curso.getValorCurricular());
            stm.setInt(15, curso.getIdCurso());

            if (stm.executeUpdate() > 0) {
                msj = "Curso actualizado con éxito";
            } else {
                msj = "No se pudo actualizar el curso";
            }

        } catch (Exception e) {
            System.out.println(e);
            msj = "Error: " + e.getMessage();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Datos recibidos: " + curso);
        return msj;
    }

    public static String eliminarRegistro(Registro registro) {
        PreparedStatement stm = null;
        Connection conn = null;
        String msj = "";
    
        try {
            conn = c.getConnection();
    
            String sql = "DELETE FROM Registro WHERE IdRegistro = ?";
    
            stm = conn.prepareStatement(sql);
            stm.setInt(1, registro.getIdRegistro()); 
    
            if (stm.executeUpdate() > 0) {
                msj = "Registro eliminado con éxito";
                aumentarCupo(registro.getIdCurso());
            } else {
                msj = "No se encontró el registro con IdRegistro: " + registro.getIdRegistro();
            }
    
        } catch (Exception e) {
            System.out.println(e);
            msj = "Error: " + e.getMessage();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    
        return msj;
    }

    private static String aumentarCupo(int idCurso) {
        PreparedStatement stm = null;
        Connection conn = null;
        int cupoDisponible = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String msj = "";
        conn = c.getConnection();
        try {
            String query = "SELECT EstatusCupo FROM curso WHERE idCurso = " + idCurso;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                cupoDisponible = rs.getInt("EstatusCupo") + 1;
            }

            query = "UPDATE curso SET EstatusCupo = ? where idCurso = ?";
            stm = conn.prepareStatement(query);
            stm.setInt(1, cupoDisponible);
            stm.setInt(2, idCurso);

            if (stm.executeUpdate() > 0) {
                msj = "Cupo aumentado con éxito";
            } else {
                msj = "No es posible aumentar el cupo";
            }

        } catch (Exception e) {
            System.out.println(e);
            msj = "Error: " + e.getMessage();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return msj;
    }

    public static String editarAsistencia(Registro registro) {
        PreparedStatement stm = null;
        Connection conn = null;
        String msj = "";
    
        conn = c.getConnection();
    
        try {
            String sql = "UPDATE Registro SET Asistencia = ? WHERE idRegistro = ?";
    
            stm = conn.prepareStatement(sql);
    
            stm.setString(1, registro.getAsistencia()); 
            stm.setInt(2, registro.getIdRegistro());
    
            if (stm.executeUpdate() > 0) {
                msj = "Registro actualizado con éxito";
            } else {
                msj = "No se pudo actualizar el registro";
            }
    
        } catch (Exception e) {
            System.out.println(e);
            msj = "Error: " + e.getMessage();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Datos recibidos: " + registro);
        return msj;
    }

    public static String editarNombreCurso(TipoCurso tipoCurso) {
        PreparedStatement stm = null;
        Connection conn = null;
        String msj = "";
    
        conn = c.getConnection();
    
        try {
            String sql = "UPDATE TIPOCURSO SET Tipo = ? WHERE Id = ?";
    
            stm = conn.prepareStatement(sql);
    
            stm.setString(1, tipoCurso.getTipo()); 
            stm.setInt(2, tipoCurso.getId());
    
            if (stm.executeUpdate() > 0) {
                msj = "Tipo de curso actualizado con éxito";
            } else {
                msj = "No se pudo actualizar el tipo de curso";
            }
    
        } catch (Exception e) {
            System.out.println(e);
            msj = "Error: " + e.getMessage();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Datos recibidos: " + tipoCurso);
        return msj;
    }

    public static String eliminarTipoCurso(TipoCurso tipoCurso) {
        PreparedStatement stm = null;
        Connection conn = null;
        String msj = "";
    
        try {
            conn = c.getConnection();
    
            String sql = "DELETE FROM TIPOCURSO WHERE Id = ?";
    
            stm = conn.prepareStatement(sql);
            stm.setInt(1, tipoCurso.getId()); 
    
            if (stm.executeUpdate() > 0) {
                msj = "Tipo curso eliminado con exito";
            } else {
                msj = "Error al eliminar el tipo de curso";
            }
    
        } catch (Exception e) {
            System.out.println(e);
            msj = "Error: " + e.getMessage();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return msj;
    }
    
    public static String registrarTipoCurso(String nombreTipoCurso) {
        PreparedStatement stm = null;
        Connection conn = null;
        String msj = "";

        conn = c.getConnection();

        try {
            String sql = "INSERT INTO tipocurso (tipo) values (?)";

            stm = conn.prepareStatement(sql);

            // Asignación de valores a los parámetros
            stm.setString(1, nombreTipoCurso);

            if (stm.executeUpdate() > 0)
                msj = "Tipo de Curso registrado";
            else
                msj = "Error al registrar el tipo de curso";

        } catch (Exception e) {
            System.out.println(e);
            msj = "Error: " + e.getMessage();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                stm = null;
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return msj;
    }    
    

    public static List<String> obtenerAsistentes(Integer idCurso) {
        List<String> nombreAsistentes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
    
        try {
            conn = c.getConnection();
            String query = "SELECT CONCAT(Nombre, ' ', Apellidos) as Nombre, FROM Registro WHERE Asistencia = 'true' AND IdCurso = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, idCurso);  // Corregido el índice del parámetro
            rs = ps.executeQuery();
    
            while (rs.next()) {
                nombreAsistentes.add(rs.getString("Nombre"));
            }
    
        } catch (Exception ex) {
            System.out.println("Error al obtener los nombres de los asistentes: " + ex.getMessage());
            ex.printStackTrace();
    
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    
        return nombreAsistentes;
    }


     public static List<Registro> obtenerRegistrosAsistentes(Integer idCurso) {
        List<Registro> registros = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = Conexion.getConnection();
            String query = "SELECT * FROM Registro WHERE Asistencia = 'true' AND IdCurso = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, idCurso);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Registro registro = new Registro();
                registro.setNombre(rs.getString("Nombre"));
                registro.setApellidos(rs.getString("Apellidos"));
                registro.setCorreo(rs.getString("Correo"));
                // Si tu clase Registro tiene más campos, asigna los valores correspondientes
                registros.add(registro);
            }
        } catch (Exception ex) {
            System.out.println("Error al obtener los registros de asistentes: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return registros;
    }








    public static byte[] obtenerConstancia(Integer idCurso) {
        byte[] constancia = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = c.getConnection();
            String query = "SELECT Constancia FROM Curso WHERE idCurso = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, idCurso);
            rs = ps.executeQuery();

            while (rs.next()) {
                constancia = rs.getBytes("Constancia");
            }

        } catch (Exception ex) {
            System.out.println("Error al obtener al obtener la constancia: " + ex.toString());
            ex.printStackTrace();

        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return constancia;
    }

}


