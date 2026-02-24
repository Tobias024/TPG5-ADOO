package com.unomas.model.emparejamiento;

import com.unomas.model.Partido;
import com.unomas.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class EmparejamientoPorCercania implements IEstrategiaEmparejamiento {

    private static final double MAX_DISTANCIA_KM = 20.0;

    @Override
    public boolean esCompatible(Usuario jugador, Partido partido) {
        if (jugador.getLatitud() == null || jugador.getLongitud() == null) {
            return true;
        }
        Usuario organizador = partido.getOrganizador();
        if (organizador.getLatitud() == null || organizador.getLongitud() == null) {
            return true;
        }
        double distancia = calcularDistancia(
            jugador.getLatitud(), jugador.getLongitud(),
            organizador.getLatitud(), organizador.getLongitud()
        );
        return distancia <= MAX_DISTANCIA_KM;
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    @Override
    public String getNombre() {
        return "POR_CERCANIA";
    }
}
