package patrones.adapter;

import modelo.Notificacion;

public class AdapterFirebase implements IAdapterFirebase {

    @Override
    public void enviar(Notificacion notificacion) {
        // Simulacion de envio de push notification via Firebase
        System.out.println("[Firebase] Enviando push notification a " +
                notificacion.getEmailDestino() + ": " + notificacion.getMensaje());
    }
}
