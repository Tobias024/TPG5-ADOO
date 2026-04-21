package com.subastar.dto;
public class LoginResponse {
    private String token;
    private Long idPersona;
    public LoginResponse(String token, Long idPersona) { this.token = token; this.idPersona = idPersona; }
    public String getToken() { return token; }
    public Long getIdPersona() { return idPersona; }
}
