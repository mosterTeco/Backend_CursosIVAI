package mx.ivai;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.ivai.POJO.Registro;
import mx.ivai.POJO.TipoCurso;
import mx.ivai.POJO.Usuario;
import mx.ivai.POJO.Cursos;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Hello world!
 *
 */
public class App {
    static Gson gson = new Gson();

    private static final String SECRET_KEY = "miClaveSecreta";

    private static byte[] agregarTextoAPNG(byte[] imagenBytes, String texto) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imagenBytes);
        BufferedImage imagen = ImageIO.read(bais);
    
        Graphics2D g2d = imagen.createGraphics();
        g2d.setFont(new Font("Arial", Font.BOLD, 55));
        g2d.setColor(Color.BLACK);
    
        // Obtener las métricas de la fuente
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(texto);
    
        // Calcular la posición X centrada
        int x = (imagen.getWidth() - textWidth) / 2;
        int y = 540; // Posición Y fija
    
        g2d.drawString(texto, x, y);
        g2d.dispose();
    
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagen, "png", baos);
        return baos.toByteArray();
    }
    
    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bytesRead;
        byte[] data = new byte[4096];

        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }

        return buffer.toByteArray();
    }

    public static void main(String[] args) {

        CorsMiddleware.enableCORS();

        before("/registroCurso", (request, response) -> {
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/tmp"));
        });

        post("/validacion", (request, response) -> {
            response.type("application/json");
            String payload = request.body();

            try {
                Usuario usuario = gson.fromJson(payload, Usuario.class);
                boolean esValido = Dao.usuarioRegistrado(usuario.getUsuario(), usuario.getPassword());

                Map<String, String> respuestaJson = new HashMap<>();
                if (esValido) {
                    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
                    String token = JWT.create()
                            .withIssuer("miApp")
                            .withClaim("usuario", usuario.getUsuario())
                            .sign(algorithm);

                    respuestaJson.put("mensaje", "Usuario correcto");
                    respuestaJson.put("token", token);
                } else {
                    respuestaJson.put("mensaje", "Usuario incorrecto");
                }

                return gson.toJson(respuestaJson);

            } catch (Exception e) {
                response.status(500);
                Map<String, String> errorJson = new HashMap<>();
                errorJson.put("mensaje", "Error al procesar la solicitud");
                errorJson.put("error", e.getMessage());
                return gson.toJson(errorJson);
            }
        });

        post("/registro", (request, response) -> {
            response.type("application/json");
            String payload = request.body();
            Registro registro = gson.fromJson(payload, Registro.class);
            String respuesta = "";
            // String respuesta = Dao.crearRegistro(registro);
            return respuesta;
        });

        // Obtener tipos de curso
        get("/tipos", (request, response) -> {
            ArrayList<String> tiposCurso = Dao.obtenerTiposCurso();
            return new Gson().toJson(tiposCurso);
        });

        get("/obtenerAsistentes/:idCurso", (request, response) -> {
            response.type("application/json");
        
            try {
                int idCurso = Integer.parseInt(request.params("idCurso"));
                List<String> asistentes = Dao.obtenerAsistentes(idCurso);
                return new Gson().toJson(asistentes);
            } catch (NumberFormatException e) {
                response.status(400);
                return new Gson().toJson("Error: idCurso debe ser un número válido.");
            } catch (Exception e) {
                response.status(500); 
                return new Gson().toJson("Error interno en el servidor: " + e.getMessage());
            }
        });
        
        get("/tiposs", (request, response) -> {
            response.type("application/json");
            ArrayList<String> tiposCurso = Dao.obtenerTiposCurso();
            return new Gson().toJson(tiposCurso);
        });

        // Obtener los registros de un Curso
        get("/obtenerRegistros/:idCurso", (request, response) -> {
            response.type("application/json");
            int idCurso = Integer.parseInt(request.params("idCurso"));
            ArrayList<Registro> registros = Dao.obtenerRegistros(idCurso);
            return new Gson().toJson(registros);
        });

        // Obtener información de cursos registrados
        get("/obtenerCursos", (request, response) -> {
            response.type("application/json");
            ArrayList<Cursos> Cursos = Dao.obtenerCursos();
            return new Gson().toJson(Cursos);
        });

        // Registrar Curso (Administrador)
        // post("/registroCurso", (request, response) -> {
        // response.type("application/json");

        // Part filePart = request.raw().getPart("constancia");
        // byte[] constanciaBytes = null;

        // if (filePart != null) {
        // InputStream fileContent = filePart.getInputStream();
        // constanciaBytes = inputStreamToByteArray(fileContent);
        // }

        // Cursos curso = gson.fromJson(request.queryParams("curso"), Cursos.class);
        // System.out.println(curso);
        // curso.setConstancia(constanciaBytes);
        // System.out.println(curso);
        // String mensaje = Dao.registrarCurso(curso);

        // Map<String, String> respuesta = new HashMap<>();
        // respuesta.put("mensaje", mensaje);

        // return gson.toJson(respuesta);
        // });

        post("/registroCurso", (request, response) -> {
            response.type("application/json");

            try {
                String body = request.body();
                JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();

                Cursos curso = gson.fromJson(jsonObject.get("curso"), Cursos.class);

                String constanciaBase64 = jsonObject.get("constancia").getAsString();

                if (constanciaBase64 != null && !constanciaBase64.isEmpty()) {
                    byte[] constanciaBytes = Base64.getDecoder().decode(constanciaBase64);

                    curso.setConstancia(constanciaBytes);
                }

                String mensaje = Dao.registrarCurso(curso);

                Map<String, String> respuesta = new HashMap<>();
                respuesta.put("mensaje", mensaje);
                return gson.toJson(respuesta);

            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Error interno: " + e.getMessage());
                return gson.toJson(errorResponse);
            }
        });

        get("/obtenerPdf/:idCurso", (request, response) -> {
            int idCurso = Integer.parseInt(request.params("idCurso"));
            List<String> asistentes = Dao.obtenerAsistentes(idCurso);

            if (asistentes.isEmpty()) {
                response.status(404);
                return "No hay asistentes registrados para este curso.";
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

            for (String asistente : asistentes) {
                byte[] archivoBytes = Dao.obtenerConstancia(idCurso);

                if (archivoBytes == null || archivoBytes.length == 0) {
                    continue;
                }

                archivoBytes = agregarTextoAPNG(archivoBytes, asistente);

                ZipEntry entry = new ZipEntry(asistente.replace(" ", "_") + ".png");
                zos.putNextEntry(entry);
                zos.write(archivoBytes);
                zos.closeEntry();
            }

            zos.close();

            response.type("application/zip");
            response.header("Content-Disposition", "attachment; filename=\"constancias.zip\"");

            OutputStream os = response.raw().getOutputStream();
            os.write(baos.toByteArray());
            os.flush();
            os.close();

            return response.raw();
        });



        get("/enviarConstancias/:idCurso", (request, response) -> {
            int idCurso = Integer.parseInt(request.params("idCurso"));
        
            // Obtiene la información del curso
            Cursos curso = Dao.obtenerCurso(idCurso);
            if (curso == null) {
                response.status(404);
                return "Curso no encontrado";
            }
        
            // Obtiene los registros de los asistentes (con su correo, nombre, etc.)
            List<Registro> registros = Dao.obtenerRegistrosAsistentes(idCurso);
            if (registros.isEmpty()) {
                response.status(404);
                return "No hay asistentes registrados para este curso.";
            }
        
            for (Registro registro : registros) {
                // Obtiene los bytes de la constancia (por ejemplo, una imagen base)
                byte[] archivoBytes = Dao.obtenerConstancia(idCurso);
                if (archivoBytes == null || archivoBytes.length == 0) {
                    continue;
                }
                
                // Agrega el nombre del asistente sobre la imagen (puedes implementar esta función según tus necesidades)
                String nombreCompleto = registro.getNombre() + " " + registro.getApellidos();
                archivoBytes = agregarTextoAPNG(archivoBytes, nombreCompleto);
                
                // Envía el correo con la constancia adjunta
                MailConstancia.inicializarMail(registro, curso, archivoBytes);
            }
        
            response.type("text/plain");
            return "Correos enviados exitosamente";
        });


        // Petición para obtener archivo excel de los regitros
        get("/obtenerExcelRegistros/:idCurso", (request, response) -> {
            int idCurso = Integer.parseInt(request.params("idCurso"));
            String nombreCurso = Dao.obtenerCurso(idCurso).getNombreCurso();
            ArrayList<Registro> registros = Dao.obtenerRegistros(idCurso);

            byte[] excelData = Excel.excelRegistros(registros, nombreCurso);
            if (excelData != null) {
                response.type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.header("Content-Disposition", "attachment; filename=\"registros_curso_" + idCurso + ".xlsx\"");
                response.raw().getOutputStream().write(excelData);
                response.raw().getOutputStream().flush();
                return null;
            } else {
                response.status(500);
                return "Error al generar el archivo Excel";
            }
        });

        post("/estado", (request, response) -> {
            response.type("application/json");

            List<String> estados = Dao.obtenerEstados();

            Gson gson = new Gson();
            String jsonEstados = gson.toJson(estados);

            return jsonEstados;
        });

        put("/actualizar", (request, response) -> {
            response.type("application/json");
            String fecha = request.queryParams("Fecha");
            System.out.println(fecha);

            String body = request.body();

            System.out.println("Datos recibidos en el backend: " + body);

            Gson gson = new Gson();

            Cursos curso = gson.fromJson(body, Cursos.class);

            String resultado = Dao.editarCurso(curso);

            return gson.toJson(Collections.singletonMap("mensaje", resultado));
        });

        post("/registrarse", (request, response) -> {
            response.type("application/json");
            String payload = request.body();
            Registro registro = gson.fromJson(payload, Registro.class);
            String respuesta = Dao.crearRegistro(registro);
            return respuesta;
        });

        put("/actualizarRegistro", (request, response) -> {
            response.type("application/json");

            String body = request.body();

            System.out.println("Datos recibidos en el backend: " + body);

            Gson gson = new Gson();

            Registro registro = gson.fromJson(body, Registro.class);

            String resultado = Dao.editarAsistencia(registro);

            return new Gson().toJson(Collections.singletonMap("mensaje", resultado));
        });

        delete("/eliminarRegistro", (request, response) -> {
            response.type("application/json");
            String payload = request.body();
            Registro registro = gson.fromJson(payload, Registro.class);
            String respuesta = Dao.eliminarRegistro(registro);
            return respuesta;
        });

        get("/obtenerTipoCurso", (request, response) -> {
            ArrayList<TipoCurso> tipoCursos = Dao.obtenerTiposCursos();
            response.type("application/json");
            return new Gson().toJson(tipoCursos);
        });

        post("/eliminarTipoCurso", (request, response) -> {
            response.type("application/json");
            String payload = request.body();
            TipoCurso registro = gson.fromJson(payload, TipoCurso.class);
            String respuesta = Dao.eliminarTipoCurso(registro);

            Map<String, String> jsonResponse = new HashMap<>();
            jsonResponse.put("message", respuesta);

            return gson.toJson(jsonResponse);
        });

        put("/actualizarTipoCurso", (request, response) -> {
            response.type("application/json");

            String body = request.body();
            System.out.println("Datos recibidos en el backend: " + body);

            Gson gson = new Gson();
            TipoCurso tipoCurso = gson.fromJson(body, TipoCurso.class);

            String resultado = Dao.editarNombreCurso(tipoCurso);
            return gson.toJson(Collections.singletonMap("mensaje", resultado));
        });

        post("/registroTipoCurso", (request, response) -> {
            response.type("application/json");

            String payload = request.body();
            TipoCurso tipoCurso = gson.fromJson(payload, TipoCurso.class);

            String mensaje = Dao.registrarTipoCurso(tipoCurso.getTipo());

            return mensaje;
        });

        get("/mandarConstancias/:idCurso", (request, response) -> {
            response.type("application/json");
            int idCurso = Integer.parseInt(request.params("idCurso"));
            List<String> nombreAsistentes = Dao.obtenerAsistentes(idCurso);

            Gson gson = new Gson();
            String jsonEstados = gson.toJson(nombreAsistentes);

            return jsonEstados;
        });

    }
}
