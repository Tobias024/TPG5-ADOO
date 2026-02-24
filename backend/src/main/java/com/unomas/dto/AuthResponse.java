package com.unomas.dto;

public class AuthResponse {
    private String token;
    private String nombre;
    private String mail;
    private Long id;

    public AuthResponse(String token, String nombre, String mail, Long id) {
        this.token = token;
        this.nombre = nombre;
        this.mail = mail;
        this.id = id;
    }

    public String getToken() { return token; }
    public String getNombre() { return nombre; }
    public String getMail() { return mail; }
    public Long getId() { return id; }
}
