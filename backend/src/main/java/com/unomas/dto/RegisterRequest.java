package com.unomas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
    @NotBlank
    private String nombre;
    @NotBlank @Email
    private String mail;
    @NotBlank
    private String password;
    private String nivel;
    private Long deporteFavoritoId;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public Long getDeporteFavoritoId() { return deporteFavoritoId; }
    public void setDeporteFavoritoId(Long deporteFavoritoId) { this.deporteFavoritoId = deporteFavoritoId; }
}
