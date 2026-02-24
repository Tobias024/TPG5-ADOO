package com.unomas.repository;

import com.unomas.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByDestinoIdOrderByFechaEnvioDesc(Long userId);
    long countByDestinoIdAndLeidaFalse(Long userId);
}
