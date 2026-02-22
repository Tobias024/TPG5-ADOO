package modelo;

import modelo.nivel.INivel;

public class Usuario {
    private String nombre;
    private String mail;
    private String password;
    private INivel nivel;
    private Deporte deporteFavorito;

    public Usuario(String nombre, String mail, String password) {
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
    }

    public boolean passwordCorrecta(String pass) {
        return this.password.equals(pass);
    }

    public String getMail() {
        return mail;
    }

    public INivel getNivel() {
        return nivel;
    }

    public void setNivel(INivel nivel) {
        this.nivel = nivel;
    }

    public void registrarVictoria() {
        // Avanza de nivel si corresponde
        if (nivel != null) {
            nivel.avanzar(this);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Deporte getDeporteFavorito() {
        return deporteFavorito;
    }

    public void setDeporteFavorito(Deporte deporteFavorito) {
        this.deporteFavorito = deporteFavorito;
    }

    @Override
    public String toString() {
        return nombre + " (" + mail + ")";
    }
}
