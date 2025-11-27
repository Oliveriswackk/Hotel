package com.hotel.services;

import com.hotel.models.Cliente;
import com.hotel.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    public Cliente save(Cliente cliente) {
        // Validar que el email no esté duplicado
        if (cliente.getId() == null) {
            Optional<Cliente> existente = clienteRepository.findByEmail(cliente.getEmail());
            if (existente.isPresent()) {
                throw new IllegalArgumentException("Ya existe un cliente con el email: " + cliente.getEmail());
            }
        } else {
            Optional<Cliente> existente = clienteRepository.findByEmail(cliente.getEmail());
            if (existente.isPresent() && !existente.get().getId().equals(cliente.getId())) {
                throw new IllegalArgumentException("Ya existe un cliente con el email: " + cliente.getEmail());
            }
        }
        return clienteRepository.save(cliente);
    }
    
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return clienteRepository.existsById(id);
    }
}

