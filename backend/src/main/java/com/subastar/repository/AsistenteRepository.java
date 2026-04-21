package com.subastar.repository;
import com.subastar.model.Asistente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface AsistenteRepository extends JpaRepository<Asistente, Long> {
    Optional<Asistente> findByCliente_IdentificadorAndSubasta_Identificador(Long clienteId, Long subastaId);
    List<Asistente> findBySubasta_Identificador(Long subastaId);
}
