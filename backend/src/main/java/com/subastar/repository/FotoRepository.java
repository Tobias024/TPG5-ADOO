package com.subastar.repository;
import com.subastar.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FotoRepository extends JpaRepository<Foto, Long> {
    List<Foto> findByProducto_Identificador(Long productoId);
}
