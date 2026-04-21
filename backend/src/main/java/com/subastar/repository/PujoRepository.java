package com.subastar.repository;
import com.subastar.model.Pujo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PujoRepository extends JpaRepository<Pujo, Long> {
    List<Pujo> findByItem_IdentificadorOrderByImporteDesc(Long itemId);
    List<Pujo> findByAsistente_Cliente_Identificador(Long clienteId);
}
