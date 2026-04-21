package com.subastar.dto;
import jakarta.validation.constraints.NotBlank;
public class LoginRequest {
    @NotBlank private String documento;
    @NotBlank private String contrasenia;
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getContrasenia() { return contrasenia; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
}
