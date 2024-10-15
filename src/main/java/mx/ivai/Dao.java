package mx.ivai;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mx.ivai.Mail;

import mx.ivai.POJO.*;

public class Dao {

    private static Conexion c = new Conexion();

    // !METODO QUE RETORNA OBJETO CON LOS DATOS DEL USUARIO
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

            if (stm.executeUpdate() > 0){
                msj = "Registro Correcto";
                reducirCupo(reg.getIdCurso());
                Mail.inicializarMail(reg, obtenerCurso(reg.getIdCurso()));
            }
            else
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
        return msj;
    }

    public static Cursos obtenerCurso(int idCurso){
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
        int cupo = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String msj = "";
        conn = c.getConnection();
        try {
            String query = "SELECT EstatusCupo FROM curso WHERE idCurso = " + idCurso;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                cupo = rs.getInt("EstatusCupo") - 1;
            }

            query = "UPDATE curso SET EstatusCupo = ? where idCurso = ?";
            stm = conn.prepareStatement(query);
            stm.setInt(1, cupo);
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


    //Metodo que devuelve todos los registros de un curso
    public static ArrayList<Registro> obtenerRegistros(int idCurso) {

        PreparedStatement stm = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Registro> registros = new ArrayList<Registro>();

        conn = c.getConnection();

        try {

            String query = "SELECT * FROM Registro WHERE IdCurso = " + idCurso;
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
            String sql = "INSERT INTO Curso (NombreCurso, Fecha, Hora, Imparte, Cupo, EstatusCupo, EstatusCurso, Modalidad, Direccion, CorreoSeguimiento, Tipo, Curso, LigaTeams, ValorCurricular) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,'cursos.ivai@gmail.com', ?, ?, ?, ? )";

            stm = conn.prepareStatement(sql);

            // Asignación de valores a los parámetros
            stm.setString(1, curso.getNombreCurso());
            stm.setString(2, curso.getFecha()); // Conversión de LocalDateTime a Timestamp
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

            if (stm.executeUpdate() > 0)
                msj = "Curso registrado con exito";
            else
                msj = "Error al registrar el curso";

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
        String msj = "";
    
        conn = c.getConnection();
    
        try {
            String sql = "UPDATE Curso SET NombreCurso = ?, Fecha = ?, Hora = ?, Imparte = ?, EstatusCupo = ?, EstatusCurso = ?, "
                       + " Modalidad = ?, Direccion = ?, CorreoSeguimiento = ?, Tipo = ?, Curso = ?, LigaTeams = ?, ValorCurricular = ?"
                       + "WHERE IdCurso = ?";
    
            stm = conn.prepareStatement(sql);

            stm.setString(1, curso.getNombreCurso());
            stm.setString(2, curso.getFecha());
            stm.setString(3, curso.getHora());
            stm.setString(4, curso.getImparte());
            stm.setInt(5, curso.getEstatusCupo());
            stm.setString(6, curso.getEstatusCurso());
            stm.setString(7, curso.getModalidad());
            stm.setString(8, curso.getDireccion());
            stm.setString(9, curso.getCorreoSeguimiento());
            stm.setString(10, curso.getTipo());
            stm.setString(11, curso.getCurso());
            stm.setString(12, curso.getLigaTeams());
            stm.setString(13, curso.getValorCurricular());
            stm.setInt(14, curso.getIdCurso());
    
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
}
