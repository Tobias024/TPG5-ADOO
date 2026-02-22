package vista.dto;

import modelo.Deporte;
import modelo.nivel.INivel;

// Datos que completa el usuario al registrarse
public class DatosRegistroUsuario {
    private String nombre;
    private String mail;
    private String password;
    private Deporte deporteFavorito;
    private INivel nivelInicial;

    public DatosRegistroUsuario(String nombre, String mail, String password,
                                Deporte deporteFavorito, INivel nivelInicial) {
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
        this.deporteFavorito = deporteFavorito;
        this.nivelInicial = nivelInicial;
    }

    public String getNombre() { return nombre; }
    public String getMail() { return mail; }
    public String getPassword() { return password; }
    public Deporte getDeporteFavorito() { return deporteFavorito; }
    public INivel getNivelInicial() { return nivelInicial; }
}
