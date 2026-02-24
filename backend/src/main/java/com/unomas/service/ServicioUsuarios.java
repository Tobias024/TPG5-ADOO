package com.unomas.service;

import com.unomas.dto.RegisterRequest;
import com.unomas.model.Deporte;
import com.unomas.model.Usuario;
import com.unomas.repository.DeporteRepository;
import com.unomas.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarios {

    private final UsuarioRepository usuarioRepository;
    private final DeporteRepository deporteRepository;
    private final PasswordEncoder passwordEncoder;

    public ServicioUsuarios(UsuarioRepository usuarioRepository,
                            DeporteRepository deporteRepository,
                            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.deporteRepository = deporteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrar(RegisterRequest req) {
        if (usuarioRepository.existsByMail(req.getMail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        if (usuarioRepository.existsByNombre(req.getNombre())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        Usuario usuario = new Usuario(req.getNombre(), req.getMail(),
            passwordEncoder.encode(req.getPassword()));

        if (req.getNivel() != null && !req.getNivel().isBlank()) {
            usuario.setNivel(req.getNivel());
        }
        if (req.getDeporteFavoritoId() != null) {
            Deporte deporte = deporteRepository.findById(req.getDeporteFavoritoId())
                .orElseThrow(() -> new RuntimeException("Deporte no encontrado"));
            usuario.setDeporteFavorito(deporte);
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String mail, String rawPassword) {
        Usuario usuario = usuarioRepository.findByMail(mail)
            .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
        if (!passwordEncoder.matches(rawPassword, usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return usuario;
    }

    public Usuario obtenerPorMail(String mail) {
        return usuarioRepository.findByMail(mail)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
