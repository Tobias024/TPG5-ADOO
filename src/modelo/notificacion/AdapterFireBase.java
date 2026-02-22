package modelo.notificacion;

import modelo.Notificacion;

// Adapter concreto que usa Firebase para enviar push notifications
public class AdapterFireBase implements IAdapterPush {

    @Override
    public void enviar(Notificacion notificacion) {
        // Aca iria la integracion real con Firebase
        System.out.println("[Push] Para: " + notificacion.getDestino()
                + " - " + notificacion.getMensaje());
    }
}
