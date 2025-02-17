package mx.ivai;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import mx.ivai.POJO.Cursos;
import mx.ivai.POJO.Registro;

public class MailConstancia {
    
    public static void inicializarMail(Registro registro, Cursos curso, byte[] archivoBytes) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");  
        props.put("mail.smtp.port", "587");  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.starttls.enable", "true");  
        
        Session sesion = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("cursos.ivai@gmail.com", "zyykxeyxuijpcqxy");
            }
        });
        
        try {
            MimeMessage message = new MimeMessage(sesion);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(registro.getCorreo(), true));
            message.setSubject("Tu Constancia de Participación en " + curso.getNombreCurso());
            
            // Crea el contenido del mensaje multipart
            Multipart multipart = new MimeMultipart();
            
            // Parte 1: Contenido HTML del correo
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlContent = "<h2>Apreciado(a) " + registro.getNombre() + " " + registro.getApellidos() + "</h2>"
                    + "<p>Adjunto encontrarás tu constancia de participación para el curso <strong>" 
                    + curso.getNombreCurso() + "</strong>.</p>"
                    + "<p>Gracias por tu participación.</p>";
            messageBodyPart.setContent(htmlContent, "text/html");
            multipart.addBodyPart(messageBodyPart);
            
            // Parte 2: Archivo adjunto (la constancia en PNG)
            BodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(archivoBytes, "image/png");
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName("constancia.png");
            multipart.addBodyPart(attachmentPart);
            
            // Establece el contenido completo del mensaje
            message.setContent(multipart);
            
            // Envía el correo
            Transport.send(message);
            System.out.println("Correo enviado a: " + registro.getCorreo());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}


