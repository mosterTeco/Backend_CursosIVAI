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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

/**
 * Hello world!
 *
 */
public class App {
    static Gson gson = new Gson();

    private static final String SECRET_KEY = "miClaveSecreta";

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
            byte[] constanciaBytes = Dao.obtenerConstancia(idCurso);

            if (constanciaBytes == null) {
                response.status(404);
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Constancia no encontrada");
                return gson.toJson(errorResponse);
            }

            response.type("application/pdf");
            response.header("Content-Disposition", "inline; filename=\"constancia_" + idCurso + ".pdf\"");

            OutputStream outputStream = response.raw().getOutputStream();
            outputStream.write(constanciaBytes);
            outputStream.flush();

            return response.raw();
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

            return resultado;
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
