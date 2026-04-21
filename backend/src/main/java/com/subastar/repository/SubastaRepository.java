package com.subastar.repository;
import com.subastar.model.Subasta;
import com.subastar.model.enums.EstadoSubasta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SubastaRepository extends JpaRepository<Subasta, Long> {
    List<Subasta> findByEstado(EstadoSubasta estado);
}
