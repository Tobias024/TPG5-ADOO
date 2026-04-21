package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.LoginRequest;
import com.subastar.dto.LoginResponse;
import com.subastar.model.Persona;
import com.subastar.repository.PersonaRepository;
import com.subastar.security.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PersonaRepository personaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(PersonaRepository personaRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.personaRepository = personaRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        Persona persona = personaRepository.findByDocumento(request.getDocumento())
                .orElseThrow(() -> new BadCredentialsException("Credenciales incorrectas"));
        if (!passwordEncoder.matches(request.getContrasenia(), persona.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales incorrectas");
        }
        String token = tokenProvider.generateToken(persona.getDocumento(), persona.getIdentificador());
        return new LoginResponse(token, persona.getIdentificador());
    }
}
