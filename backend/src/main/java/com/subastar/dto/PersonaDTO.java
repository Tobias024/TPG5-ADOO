package com.subastar.dto;

import com.subastar.model.enums.EstadoPersona;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class PersonaDTO {
    private Long identificador;
    @NotBlank private String documento;
    @NotBlank private String nombre;
    private String direccion;
    private EstadoPersona estado;
    private String foto;
    private String email;
    private String contrasenia;
    private LocalDate fechaNacimiento;

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public EstadoPersona getEstado() { return estado; }
    public void setEstado(EstadoPersona estado) { this.estado = estado; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContrasenia() { return contrasenia; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}
