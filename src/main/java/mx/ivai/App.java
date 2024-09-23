package mx.ivai;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App 
{
    static Gson gson = new Gson();

    public static void main( String[] args )
    {
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

        //Registrar Curso (Administrador)
        post("/Registro", (request,response) -> {
            response.type("application/json");
            String payload = request.body();
            try {

                HashMap<String, String> respuestaJson = new HashMap<>();
                return gson.toJson(respuestaJson);

            } catch (Exception e){
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


        System.out.println( "Hello World!!!!!!!!!!" );
    }
}
