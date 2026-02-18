package modelo;

public enum Nivel {
    PRINCIPIANTE,
    INTERMEDIO,
    AVANZADO;

    public int getValor() {
        switch (this) {
            case PRINCIPIANTE:
                return 1;
            case INTERMEDIO:
                return 2;
            case AVANZADO:
                return 3;
            default:
                return 0;
        }
    }
}
