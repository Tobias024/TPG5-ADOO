package com.subastar.dto;
import com.subastar.model.enums.CategoriaSubasta;
import com.subastar.model.enums.SiNo;
public class ClienteDTO {
    private Long identificador;
    private Long numeroPais;
    private SiNo admitido;
    private CategoriaSubasta categoria;
    private Integer verificador;
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Long getNumeroPais() { return numeroPais; }
    public void setNumeroPais(Long numeroPais) { this.numeroPais = numeroPais; }
    public SiNo getAdmitido() { return admitido; }
    public void setAdmitido(SiNo admitido) { this.admitido = admitido; }
    public CategoriaSubasta getCategoria() { return categoria; }
    public void setCategoria(CategoriaSubasta categoria) { this.categoria = categoria; }
    public Integer getVerificador() { return verificador; }
    public void setVerificador(Integer verificador) { this.verificador = verificador; }
}
