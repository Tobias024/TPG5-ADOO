package com.unomas.repository;

import com.unomas.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findByEstadoNombre(String estadoNombre);

    @Query("SELECT p FROM Partido p WHERE p.estadoNombre = 'CONFIRMADO' AND p.fechaHora <= :now")
    List<Partido> findConfirmadosParaIniciar(@Param("now") LocalDateTime now);

    @Query("SELECT p FROM Partido p WHERE p.deporte.id = :deporteId")
    List<Partido> findByDeporteId(@Param("deporteId") Long deporteId);

    @Query("SELECT p FROM Partido p WHERE p.organizador.id = :userId")
    List<Partido> findByOrganizadorId(@Param("userId") Long userId);

    @Query("SELECT p FROM Partido p JOIN p.jugadores j WHERE j.id = :userId")
    List<Partido> findByJugadorId(@Param("userId") Long userId);

    @Query("SELECT p FROM Partido p WHERE p.estadoNombre = 'FALTAN_JUGADORES' AND p.deporte.id = :deporteId")
    List<Partido> findDisponiblesPorDeporte(@Param("deporteId") Long deporteId);
}
