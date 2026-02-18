package patrones.strategy;

import modelo.Partido;
import modelo.Usuario;

public class EmparejamientoPorHistorial implements IEstrategiaEmparejamiento {

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        // Simulacion: se verifica si el jugador tiene experiencia previa en el deporte.
        // Para el TP, se considera compatible si tiene al menos un partido previo
        // inscripto
        // o si no tiene historial (es nuevo).
        if (jugador.getPartidosInscripto().isEmpty()) {
            System.out.println("[Emparejamiento] Jugador " + jugador.getNombre() +
                    " es nuevo, se permite unirse.");
            return true;
        }

        boolean tieneExperiencia = jugador.getPartidosInscripto().stream()
                .anyMatch(p -> p.getTipoDeDeporte().equalsIgnoreCase(partido.getTipoDeDeporte()));

        System.out.println("[Emparejamiento] Jugador " + jugador.getNombre() +
                (tieneExperiencia ? " tiene" : " no tiene") +
                " experiencia en " + partido.getTipoDeDeporte());
        return tieneExperiencia;
    }
}
