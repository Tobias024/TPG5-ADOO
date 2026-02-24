package com.unomas.model.emparejamiento;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EstrategiaEmparejamientoFactory {

    private final Map<String, IEstrategiaEmparejamiento> estrategias;

    public EstrategiaEmparejamientoFactory(List<IEstrategiaEmparejamiento> estrategiasList) {
        this.estrategias = estrategiasList.stream()
            .collect(Collectors.toMap(IEstrategiaEmparejamiento::getNombre, Function.identity()));
    }

    public IEstrategiaEmparejamiento obtener(String nombre) {
        IEstrategiaEmparejamiento estrategia = estrategias.get(nombre);
        if (estrategia == null) {
            return estrategias.get("LIBRE");
        }
        return estrategia;
    }
}
