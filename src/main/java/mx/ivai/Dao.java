package mx.ivai;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.nimbus.State;

import java.sql.*;

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

        conn = c.getConnection();

        try {
            String sql = "INSERT INTO Registro (ClaveRegistro, Nombre, Apellidos, SO, Telefono, Correo, IdCurso, Opcional, Procedencia, GradoEstudios, OrdenGobierno, Area, Cargo, TitularUA, Comite, Sexo, Estado, Asistio, Otra1, Otra2, InforEventos, Inter) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stm.setString(1, reg.getClaveRegistro());
            stm.setString(2, reg.getNombre());
            stm.setString(3, reg.getApellidos());
            stm.setString(4, reg.getSo());
            stm.setString(5, reg.getTelefono());
            stm.setString(6, reg.getCorreo());
            stm.setInt(7, reg.getIdCurso());
            stm.setString(8, reg.getOpcional());
            stm.setString(9, reg.getProcedencia());
            stm.setString(10, reg.getGradoEstudios());
            stm.setString(11, reg.getOrdenGobierno());
            stm.setString(12, reg.getArea());
            stm.setString(13, reg.getCargo());
            stm.setString(14, reg.getTitularUA());
            stm.setString(15, reg.getComite());
            stm.setString(16, reg.getSexo());
            stm.setString(17, reg.getEstado());
            stm.setString(18, reg.getAsistio());
            stm.setString(19, reg.getOtra1());
            stm.setString(20, reg.getOtra2());
            stm.setString(21, reg.getInforEventos());
            stm.setString(22, reg.getInter());

            if (stm.executeUpdate() > 0)
                msj = "usuario agregado";
            else
                msj = "usuario no agregado";
        } catch (Exception e) {
            System.out.println(e);
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
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return msj;
    }

    // Método para registrar un curso en la base de datos
    public static String registrarCurso(Cursos curso) {
        PreparedStatement stm = null;
        Connection conn = null;
        String msj = "";

        conn = c.getConnection();

        try {
            String sql = "INSERT INTO Curso (NombreCurso, Fecha, Hora, Imparte, Cupo, EstatusCupo, EstatusCurso, Observaciones, Lugar, CorreoSeguimiento, Programa, Archivo, Tipo, Curso, ValorCurricular, FechaLetra) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            stm = conn.prepareStatement(sql);

            // Asignación de valores a los parámetros
            stm.setString(1, curso.getNombreCurso());
            stm.setTimestamp(2, java.sql.Timestamp.valueOf(curso.getFecha())); // Conversión de LocalDateTime a
                                                                               // Timestamp
            stm.setString(3, curso.getHora());
            stm.setString(4, curso.getImparte());
            stm.setInt(5, curso.getCupo());
            stm.setString(6, curso.getEstatusCupo());
            stm.setString(7, curso.getEstatusCurso());
            stm.setString(8, curso.getObservaciones());
            stm.setString(9, curso.getLugar());
            stm.setString(10, curso.getCorreoSeguimiento());
            stm.setString(11, curso.getPrograma());
            stm.setString(12, curso.getArchivo());
            stm.setString(13, curso.getTipo());
            stm.setString(14, curso.getCurso());
            stm.setString(15, curso.getValorCurricular());
            stm.setString(16, curso.getFechaLetra());

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
    
}
