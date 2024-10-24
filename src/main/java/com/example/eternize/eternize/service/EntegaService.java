package com.example.eternize.eternize.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eternize.eternize.model.Entrega;
import com.example.eternize.eternize.repository.EntregaRepository;

@Service
public class EntegaService {
    
	private final EntregaRepository entregaRepository;

    @Autowired
    public EntegaService(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }
    
    // CRUD
    
    // Método para listar todas as entregas
    public List<Entrega> findAll() {
        return entregaRepository.findAll();
    }

    // Método para buscar uma Entrega pelo ID
    public Optional<Entrega> findById(Long id) {
        return entregaRepository.findById(id);
    }

    // Método para salvar uma nova Entrega ou atualizar uma existente
    public Entrega save(Entrega entrega) {
        return entregaRepository.save(entrega);
    }

    // Método para deletar uma Entrega pelo ID
    public void deleteById(Long id) {
    	entregaRepository.deleteById(id);
    }
}
