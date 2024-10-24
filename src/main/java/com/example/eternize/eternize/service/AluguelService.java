package com.example.eternize.eternize.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eternize.eternize.model.Aluguel;
import com.example.eternize.eternize.repository.AluguelRepository;

@Service
public class AluguelService {
    
	private final AluguelRepository aluguelRepository;

    @Autowired
    public AluguelService(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    // Método para listar todos os alugueis
    public List<Aluguel> findAll() {
        return aluguelRepository.findAll();
    }

    // Método para buscar um aluguel pelo ID
    public Optional<Aluguel> findById(Long id) {
        return aluguelRepository.findById(id);
    }

    // Método para salvar um novo aluguel ou atualizar um existente
    public Aluguel save(Aluguel aluguel) {
        return aluguelRepository.save(aluguel);
    }

    // Método para deletar um aluguel pelo ID
    public void deleteById(Long id) {
    	aluguelRepository.deleteById(id);
    }
}
