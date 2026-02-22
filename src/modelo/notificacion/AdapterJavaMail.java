package modelo.notificacion;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import modelo.Notificacion;
import java.util.Properties;

// Adapter que envia emails reales usando Jakarta Mail + SMTP
public class AdapterJavaMail implements IAdapterMail {

    private String smtpHost;
    private int smtpPort;
    private String usuario;
    private String password;

    public AdapterJavaMail(String smtpHost, int smtpPort, String usuario, String password) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.usuario = usuario;
        this.password = password;
    }

    // Constructor con config por defecto para Gmail
    public AdapterJavaMail() {
        this.smtpHost = System.getenv("MAIL_SMTP_HOST") != null
                ? System.getenv("MAIL_SMTP_HOST")
                : "smtp.gmail.com";
        this.smtpPort = System.getenv("MAIL_SMTP_PORT") != null
                ? Integer.parseInt(System.getenv("MAIL_SMTP_PORT"))
                : 587;
        this.usuario = System.getenv("MAIL_USERNAME") != null
                ? System.getenv("MAIL_USERNAME")
                : "xtest.pruebax@gmail.com";
        this.password = System.getenv("MAIL_PASSWORD") != null
                ? System.getenv("MAIL_PASSWORD")
                : "Hakuna.matat4";
    }

    @Override
    public void enviar(Notificacion notificacion) {
        if (usuario.isEmpty() || password.isEmpty()) {
            System.out.println("[Email] (sin configurar SMTP) Para: "
                    + notificacion.getDestino() + " - " + notificacion.getMensaje());
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, password);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(usuario));
            mensaje.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(notificacion.getDestino()));
            mensaje.setSubject("Notificacion - Organizador Deportivo");
            mensaje.setText(notificacion.getMensaje());

            Transport.send(mensaje);
            System.out.println("[Email] Enviado a: " + notificacion.getDestino());

        } catch (MessagingException e) {
            System.out.println("[Email] Error al enviar a "
                    + notificacion.getDestino() + ": " + e.getMessage());
        }
    }
}
