package com.subastar.model;

import com.subastar.model.enums.CategoriaSubasta;
import com.subastar.model.enums.SiNo;
import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    private Long identificador;

    @OneToOne
    @MapsId
    @JoinColumn(name = "identificador")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "numero_pais")
    private Pais pais;

    @Enumerated(EnumType.STRING)
    private SiNo admitido = SiNo.no;

    @Enumerated(EnumType.STRING)
    private CategoriaSubasta categoria = CategoriaSubasta.comun;

    private Integer verificador;

    public Cliente() {}

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }
    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }
    public SiNo getAdmitido() { return admitido; }
    public void setAdmitido(SiNo admitido) { this.admitido = admitido; }
    public CategoriaSubasta getCategoria() { return categoria; }
    public void setCategoria(CategoriaSubasta categoria) { this.categoria = categoria; }
    public Integer getVerificador() { return verificador; }
    public void setVerificador(Integer verificador) { this.verificador = verificador; }
}
