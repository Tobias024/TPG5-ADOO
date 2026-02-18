package modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private String mail;
    private String contrasena;
    private Nivel nivel;
    private String deporteFavorito;
    private List<Partido> partidosCreados;
    private List<Partido> partidosInscripto;

    public Usuario(String nombre, String mail, String contrasena) {
        this.nombre = nombre;
        this.mail = mail;
        this.contrasena = contrasena;
        this.nivel = Nivel.PRINCIPIANTE;
        this.deporteFavorito = null;
        this.partidosCreados = new ArrayList<>();
        this.partidosInscripto = new ArrayList<>();
    }

    public Usuario(String nombre, String mail, String contrasena, Nivel nivel, String deporteFavorito) {
        this(nombre, mail, contrasena);
        this.nivel = nivel;
        this.deporteFavorito = deporteFavorito;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getDeporteFavorito() {
        return deporteFavorito;
    }

    public void setDeporteFavorito(String deporteFavorito) {
        this.deporteFavorito = deporteFavorito;
    }

    public List<Partido> getPartidosCreados() {
        return partidosCreados;
    }

    public List<Partido> getPartidosInscripto() {
        return partidosInscripto;
    }

    public void agregarPartidoCreado(Partido partido) {
        this.partidosCreados.add(partido);
    }

    public void agregarPartidoInscripto(Partido partido) {
        this.partidosInscripto.add(partido);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", nivel=" + nivel +
                ", deporteFavorito=" + deporteFavorito +
                '}';
    }
}
