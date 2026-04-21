package com.subastar.repository;
import com.subastar.model.RegistroSubasta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RegistroSubastaRepository extends JpaRepository<RegistroSubasta, Long> {
    List<RegistroSubasta> findByCliente_Identificador(Long clienteId);
}
