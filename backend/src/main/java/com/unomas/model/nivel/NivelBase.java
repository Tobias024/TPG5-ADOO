package com.unomas.model.nivel;

public abstract class NivelBase implements INivel {

    protected String nombre;
    protected int valor;

    protected NivelBase(String nombre, int valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    @Override
    public String getNombre() { return nombre; }

    @Override
    public int getValor() { return valor; }

    @Override
    public INivel avanzar(Usuario usuario) {
        return this;
    }

    public static int getValorPorNombre(String nombre) {
        return switch (nombre) {
            case "PRINCIPIANTE" -> 1;
            case "INTERMEDIO" -> 2;
            case "AVANZADO" -> 3;
            default -> 1;
        };
    }

    public static INivel crearPorNombre(String nombre) {
        return switch (nombre) {
            case "PRINCIPIANTE" -> new NivelPrincipiante();
            case "INTERMEDIO" -> new NivelIntermedio();
            case "AVANZADO" -> new NivelAvanzado();
            default -> new NivelPrincipiante();
        };
    }

    public interface Usuario {
        int getVictorias();
    }
}
