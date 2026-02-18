package patrones.factory;

import modelo.Nivel;
import modelo.Partido;
import modelo.IPartido;
import patrones.decorator.*;

public class FactoryGestorPartido {

    public Partido crearPartido(String deporte, Nivel nivel, String ubiHora,
            String duracion, int cantJugadores) {
        Partido partido = new Partido(cantJugadores, duracion, ubiHora, nivel);

        // Decorar con el deporte correspondiente
        IPartido partidoDecorado = decorarConDeporte(partido, deporte);

        // Guardar el nombre del deporte en el partido base
        partido.setTipoDeDeporte(partidoDecorado.getTipoDeDeporte());

        // Si el decorator define cantidad de jugadores por defecto y no se especifico
        if (cantJugadores <= 0) {
            partido.setCantJugadoresReq(partidoDecorado.getCantJugadoresReq());
        }

        System.out.println("[Factory] Partido creado: " + partidoDecorado.getDescripcion() +
                " | Deporte: " + partidoDecorado.getTipoDeDeporte() +
                " | Jugadores requeridos: " + partidoDecorado.getCantJugadoresReq());
        return partido;
    }

    public IPartido decorarConDeporte(IPartido partido, String deporte) {
        switch (deporte.toLowerCase()) {
            case "futbol":
                return new FutbolDecorator(partido);
            case "basquet":
                return new BasquetDecorator(partido);
            case "voley":
                return new VoleyDecorator(partido);
            case "tenis":
                return new TenisDecorator(partido);
            default:
                System.out.println("[Factory] Deporte no reconocido, creando partido generico.");
                return partido;
        }
    }
}
