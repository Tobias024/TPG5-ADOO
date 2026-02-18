package patrones.adapter;

import modelo.Notificacion;

public class AdapterJavaMail implements IAdapterMail {

    @Override
    public void enviar(Notificacion notificacion) {
        // Simulacion de envio de email via JavaMail
        System.out.println("[JavaMail] Enviando email a " + notificacion.getEmailDestino() +
                ": " + notificacion.getMensaje());
    }
}
