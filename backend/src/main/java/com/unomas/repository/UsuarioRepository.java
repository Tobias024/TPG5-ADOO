package com.unomas.repository;

import com.unomas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByMail(String mail);
    Optional<Usuario> findByNombre(String nombre);
    boolean existsByMail(String mail);
    boolean existsByNombre(String nombre);
}
