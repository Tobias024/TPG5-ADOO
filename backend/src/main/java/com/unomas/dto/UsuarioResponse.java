package com.unomas.dto;

import com.unomas.model.Usuario;

public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String mail;
    private String nivel;
    private String deporteFavorito;
    private Long deporteFavoritoId;
    private int victorias;

    public static UsuarioResponse from(Usuario u) {
        UsuarioResponse r = new UsuarioResponse();
        r.id = u.getId();
        r.nombre = u.getNombre();
        r.mail = u.getMail();
        r.nivel = u.getNivel();
        r.deporteFavorito = u.getDeporteFavorito() != null ? u.getDeporteFavorito().getNombre() : null;
        r.deporteFavoritoId = u.getDeporteFavorito() != null ? u.getDeporteFavorito().getId() : null;
        r.victorias = u.getVictorias();
        return r;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMail() { return mail; }
    public String getNivel() { return nivel; }
    public String getDeporteFavorito() { return deporteFavorito; }
    public Long getDeporteFavoritoId() { return deporteFavoritoId; }
    public int getVictorias() { return victorias; }
}
