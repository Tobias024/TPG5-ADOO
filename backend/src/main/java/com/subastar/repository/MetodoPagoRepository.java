package com.subastar.repository;
import com.subastar.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    List<MetodoPago> findByCliente_Identificador(Long clienteId);
}
