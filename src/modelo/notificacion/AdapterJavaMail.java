package modelo.notificacion;

import modelo.Notificacion;

// Adapter concreto que usa JavaMail para enviar emails
public class AdapterJavaMail implements IAdapterMail {

    @Override
    public void enviar(Notificacion notificacion) {
        // Aca iria la integracion real con JavaMail
        System.out.println("[Email] Para: " + notificacion.getDestino()
                + " - " + notificacion.getMensaje());
    }
}
