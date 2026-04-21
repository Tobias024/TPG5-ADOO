package com.subastar.model;

import com.subastar.model.enums.SiNo;
import jakarta.persistence.*;

@Entity
@Table(name = "pujo")
public class Pujo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    @ManyToOne
    @JoinColumn(name = "asistente_id", nullable = false)
    private Asistente asistente;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ItemCatalogo item;

    @Column(nullable = false)
    private Double importe;

    @Enumerated(EnumType.STRING)
    private SiNo ganador = SiNo.no;

    public Pujo() {}

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }
    public Asistente getAsistente() { return asistente; }
    public void setAsistente(Asistente asistente) { this.asistente = asistente; }
    public ItemCatalogo getItem() { return item; }
    public void setItem(ItemCatalogo item) { this.item = item; }
    public Double getImporte() { return importe; }
    public void setImporte(Double importe) { this.importe = importe; }
    public SiNo getGanador() { return ganador; }
    public void setGanador(SiNo ganador) { this.ganador = ganador; }
}
