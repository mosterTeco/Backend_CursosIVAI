package mx.ivai;

import com.google.gson.Gson;

import mx.ivai.POJO.Registro;
import mx.ivai.POJO.Usuario;
import mx.ivai.POJO.Cursos;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
    static Gson gson = new Gson();

    public static void main(String[] args) {
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");

            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        // Registrar Curso (Administrador)
        post("/Registro", (request, response) -> {
            response.type("application/json");
            String payload = request.body();
            try {

                HashMap<String, String> respuestaJson = new HashMap<>();
                return gson.toJson(respuestaJson);

            } catch (Exception e) {
                response.status(500);
            }
            return request;
        });

        post("/validacion", (request, response) -> {
            response.type("application/json");
            String payload = request.body();

            try {
                Usuario usuario = gson.fromJson(payload, Usuario.class);
                System.out.println("Usuario: " + usuario.getUsuario());
                System.out.println("Contrasenia: " + usuario.getPassword());

                boolean respuesta = Dao.usuarioRegistrado(usuario.getUsuario(), usuario.getPassword());

                String mensaje = respuesta ? "Usuario correcto" : "Usuario incorrecto";

                HashMap<String, String> respuestaJson = new HashMap<>();
                respuestaJson.put("mensaje", mensaje);

                return gson.toJson(respuestaJson);

            } catch (Exception e) {
                response.status(500);
                HashMap<String, String> errorJson = new HashMap<>();
                errorJson.put("mensaje", "Error al procesar la solicitud");
                errorJson.put("error", e.getMessage());
                return gson.toJson(errorJson);
            }
        });

        post("/registro", (request, response) -> {
            response.type("application/json");
            String payload = request.body();
            Registro registro = gson.fromJson(payload, Registro.class);
            System.out.println("payload " + payload);
            String respuesta = "";
            // String respuesta = Dao.crearRegistro(registro);
            return respuesta;
        });

        post("/registroCurso", (request, response) -> {
            response.type("application/json");

            String payload = request.body();
            Cursos registro = gson.fromJson(payload, Cursos.class);

            System.out.println("payload " + payload);

            String mensaje = Dao.registrarCurso(registro);

            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", mensaje);

            return gson.toJson(respuesta);
        });

        System.out.println("Hello World!!!!!!!!!!");
    }
}
