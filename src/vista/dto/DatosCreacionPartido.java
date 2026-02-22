package vista.dto;

import modelo.Deporte;
import modelo.nivel.INivel;

// Datos que completa el usuario cuando quiere crear un partido
public class DatosCreacionPartido {
    private Deporte deporte;
    private INivel nivelMinimo;
    private String ubiHora;
    private String duracion;
    private int cantJugadores;

    public DatosCreacionPartido(Deporte deporte, INivel nivelMinimo,
                                String ubiHora, String duracion, int cantJugadores) {
        this.deporte = deporte;
        this.nivelMinimo = nivelMinimo;
        this.ubiHora = ubiHora;
        this.duracion = duracion;
        this.cantJugadores = cantJugadores;
    }

    public Deporte getDeporte() { return deporte; }
    public INivel getNivelMinimo() { return nivelMinimo; }
    public String getUbiHora() { return ubiHora; }
    public String getDuracion() { return duracion; }
    public int getCantJugadores() { return cantJugadores; }
}
