package com.subastar.repository;
import com.subastar.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByCliente_IdentificadorOrderByFechaDesc(Long clienteId);
}
