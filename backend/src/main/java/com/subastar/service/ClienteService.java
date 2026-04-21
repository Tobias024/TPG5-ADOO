package com.subastar.service;

import com.subastar.config.EntityNotFoundException;
import com.subastar.dto.ClienteDTO;
import com.subastar.dto.ClienteRegistroDTO;
import com.subastar.dto.MetricasClienteDTO;
import com.subastar.model.Cliente;
import com.subastar.model.Persona;
import com.subastar.model.Pujo;
import com.subastar.model.enums.SiNo;
import com.subastar.repository.ClienteRepository;
import com.subastar.repository.PaisRepository;
import com.subastar.repository.PersonaRepository;
import com.subastar.repository.PujoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    private final PaisRepository paisRepository;
    private final PujoRepository pujoRepository;

    public ClienteService(ClienteRepository clienteRepository, PersonaRepository personaRepository,
                          PaisRepository paisRepository, PujoRepository pujoRepository) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
        this.paisRepository = paisRepository;
        this.pujoRepository = pujoRepository;
    }

    public Cliente registrar(ClienteRegistroDTO dto) {
        Persona persona = personaRepository.findById(dto.getIdentificador())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró persona con id " + dto.getIdentificador()));
        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        if (dto.getNumeroPais() != null) {
            cliente.setPais(paisRepository.findById(dto.getNumeroPais())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró país con número " + dto.getNumeroPais())));
        }
        return clienteRepository.save(cliente);
    }

    public ClienteDTO obtener(Long id) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró cliente con id " + id));
        return toDTO(c);
    }

    public MetricasClienteDTO obtenerMetricas(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró cliente con id " + id);
        }
        List<Pujo> pujos = pujoRepository.findByAsistente_Cliente_Identificador(id);
        MetricasClienteDTO m = new MetricasClienteDTO();
        m.setSubastasParticipadas(pujos.size());
        long ganadas = pujos.stream().filter(p -> SiNo.si.equals(p.getGanador())).count();
        m.setSubastasGanadas((int) ganadas);
        m.setTasaExito(pujos.isEmpty() ? 0.0 : (ganadas * 100.0) / pujos.size());
        double total = pujos.stream().filter(p -> SiNo.si.equals(p.getGanador())).mapToDouble(Pujo::getImporte).sum();
        m.setTotalGastado(total);
        double mayor = pujos.stream().mapToDouble(Pujo::getImporte).max().orElse(0.0);
        m.setMayorPuja(mayor);
        return m;
    }

    public ClienteDTO toDTO(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdentificador(c.getIdentificador());
        dto.setAdmitido(c.getAdmitido());
        dto.setCategoria(c.getCategoria());
        dto.setVerificador(c.getVerificador());
        if (c.getPais() != null) dto.setNumeroPais(c.getPais().getNumero());
        return dto;
    }
}
