package com.subastar.security;

import com.subastar.model.Persona;
import com.subastar.repository.PersonaRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonaRepository personaRepository;

    public UserDetailsServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String documento) throws UsernameNotFoundException {
        Persona persona = personaRepository.findByDocumento(documento)
                .orElseThrow(() -> new UsernameNotFoundException("Persona no encontrada: " + documento));
        return User.builder()
                .username(persona.getDocumento())
                .password(persona.getPasswordHash())
                .roles("USER")
                .build();
    }
}
