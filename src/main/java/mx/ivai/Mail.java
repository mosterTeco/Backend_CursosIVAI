package mx.ivai;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mx.ivai.POJO.Cursos;
import mx.ivai.POJO.Registro;

public class Mail {
    
    public static String inicializarMail(Registro registro, Cursos curso){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");  
        props.put("mail.smtp.port", "587");  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.starttls.enable", "true");  
        
        Session sesion = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dtistecopruebas2024@gmail.com", "cwsuggfzvhyskvbw");
            }
        });

        
        
        MimeMessage message = new MimeMessage(sesion);
        try {
            String htmlContent = "";
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(registro.getCorreo(), true));
            message.setSubject("Prueba");
            
            if(curso.getModalidad().equals("Presencial")){
                htmlContent = "<h2>Apreciado(a) " + registro.getNombre() + " " + registro.getApellidos() + "</h2>"
                + "<p>El instituto Veracruzano de Acceso a la Información y Protección de Datos Personales, a través de la "
                + "Dirección de Capacitación y Vinculación Ciudadana, le confirma su registro a la capacitación presencial denominada "
                + "<strong>" + curso.getNombreCurso() + "</strong>, a realizarse el día <strong>" + curso.getFecha() + "</strong> a las <strong>"
                + curso.getHora() + "</strong>.</p>"
                + "<p>Le invitamos a consultar nuestro Aviso de Privacidad en el siguiente enlace:</p>"
                + "<p><a href='http://www.ivai.org.mx/AvisosdePrivacidad/Capacitacion/AvisosdePrivacidadCapacitacion.pdf'><strong><u>http://www.ivai.org.mx/AvisosdePrivacidad/Capacitacion/AvisosdePrivacidadCapacitacion.pdf</u></strong></a></p>"
                + "<p>En breve recibirá la información correspondiente.</p>"
                + "<p>En caso de tener alguna duda, puede enviar un correo a: <a href='direcciondecapacitacion@ivai.org.mx'><strong><u>direcciondecapacitacion@ivai.org.mx</u></strong></a></p>";
            }

            if(curso.getModalidad().equals("Virtual")){
                htmlContent = "<h2>Apreciado(a) " + registro.getNombre() + " " + registro.getApellidos() + "</h2>"
                    + "<p>El instituto Veracruzano de Acceso a la Información y Protección de Datos Personales, a través de la "
                    + "Dirección de Capacitación y Vinculación Ciudadana, le envía el enlace de la capacitación virtual denominada "
                    + "<strong>" + curso.getNombreCurso() + "</strong>, a realizarse el día <strong>" + curso.getFecha() + "</strong> a las <strong>"
                    + curso.getHora() + "</strong>.</p>"
                    + "<p><a href='" + curso.getLigaTeams() + "'><strong><u>" + curso.getLigaTeams() + "</u></strong></a></p>"
                    + "<p>Le invitamos a consultar nuestro Aviso de Privacidad en el siguiente enlace:</p>"
                    + "<p><a href='http://www.ivai.org.mx/AvisosdePrivacidad/Capacitacion/AvisosdePrivacidadCapacitacion.pdf'><strong><u>http://www.ivai.org.mx/AvisosdePrivacidad/Capacitacion/AvisosdePrivacidadCapacitacion.pdf</u></strong></a></p>"
                    + "<h3>Reglas de participación</h3>"
                    + "<ul>"
                    + "<li>Es indispensable ingresar al curso con su nombre completo.</li>"
                    + "<li>Recomendamos inscribirse con un correo que sólo ustedes utilicen para evitar errores en el registro.</li>"
                    + "<li>Los micrófonos y cámaras deben permanecer apagados durante la capacitación.</li>"
                    + "<li>Las dudas se formularán mediante el chat y se responderán al final de la capacitación.</li>"
                    + "<li>Participantes que <u>no</u> se hayan inscrito con su nombre completo en la página del instituto no podrán acreditar la evaluación.</li>"
                    + "<li>En el tema de la cuenta de Usuario en la plataforma de Moodle, les recordamos que la cuenta creada será para todos los cursos que asistan.</li>"
                    + "<li>Para poder obtener la constancia de participación, es necesario presentar la evaluación, la cual de ser aprobatoria le permitirá acceder de manera automática a ella.</li>"
                    + "</ul>"
                    + "<p><strong>Nota:</strong> Para ingresar al curso, ya sea que haya descargado o no la aplicación, solo debe oprimir el enlace electrónico.</p>"
                    + "<p>Es importante asegurarse de contar con buena conexión a internet para que no tengas fallas con la conexión.</p>"
                    + "<p>En caso de dificultad para conectarse, puede enviar un correo a: <a href='direcciondecapacitacion@ivai.org.mx'><strong><u>direcciondecapacitacion@ivai.org.mx</u></strong></a></p>";
            }

            message.setContent(htmlContent, "text/html");

            System.out.println("Enviando");
            Transport.send(message);
            System.out.println("Envio de mensaje exitoso");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
