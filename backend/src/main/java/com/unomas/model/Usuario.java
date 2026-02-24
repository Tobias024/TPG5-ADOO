package com.unomas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String mail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nivel = "PRINCIPIANTE";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deporte_favorito_id")
    private Deporte deporteFavorito;

    private int victorias = 0;

    @Column
    private Double latitud;

    @Column
    private Double longitud;

    public Usuario() {}

    public Usuario(String nombre, String mail, String password) {
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
    }

    public void registrarVictoria() {
        this.victorias++;
    }

    public boolean passwordCorrecta(String pass) {
        return this.password.equals(pass);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public Deporte getDeporteFavorito() { return deporteFavorito; }
    public void setDeporteFavorito(Deporte deporteFavorito) { this.deporteFavorito = deporteFavorito; }
    public int getVictorias() { return victorias; }
    public void setVictorias(int victorias) { this.victorias = victorias; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
}
