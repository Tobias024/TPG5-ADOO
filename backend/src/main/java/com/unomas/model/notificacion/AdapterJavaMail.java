package com.unomas.model.notificacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AdapterJavaMail implements IAdapterMail {

    private static final Logger log = LoggerFactory.getLogger(AdapterJavaMail.class);

    private final JavaMailSender mailSender;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    @Value("${app.mail.from:unomas@app.com}")
    private String fromAddress;

    public AdapterJavaMail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void enviar(String destino, String mensaje) {
        log.info("[JavaMail] Enviando email a {}: {}", destino, mensaje);
        
        if (!mailEnabled) {
            log.info("[JavaMail] Mail disabled, skipping actual send");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(destino);
            message.setSubject("Notificación de Uno Más");
            message.setText(mensaje);
            
            mailSender.send(message);
            log.info("[JavaMail] Email enviado exitosamente a {}", destino);
        } catch (Exception e) {
            log.error("[JavaMail] Error enviando email a {}: {}", destino, e.getMessage());
        }
    }
}
