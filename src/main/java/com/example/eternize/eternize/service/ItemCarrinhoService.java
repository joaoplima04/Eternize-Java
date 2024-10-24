package com.example.eternize.eternize.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eternize.eternize.model.ItemCarrinho;
import com.example.eternize.eternize.repository.ItemCarrinhoRepository;

@Service
public class ItemCarrinhoService {
    
	private final ItemCarrinhoRepository itemCarrinhoRepository;

    @Autowired
    public ItemCarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository) {
        this.itemCarrinhoRepository = itemCarrinhoRepository;
    }
    
    // CRUD
    
    // Método para listar todos os itemCarrinhos
    public List<ItemCarrinho> findAll() {
        return itemCarrinhoRepository.findAll();
    }

    // Método para buscar um itemCarrinho pelo ID
    public Optional<ItemCarrinho> findById(Long id) {
        return itemCarrinhoRepository.findById(id);
    }

    // Método para salvar um novo itemCarrinho ou atualizar um existente
    public ItemCarrinho save(ItemCarrinho itemCarrinho) {
        return itemCarrinhoRepository.save(itemCarrinho);
    }

    // Método para deletar um itemCarrinho pelo ID
    public void deleteById(Long id) {
    	itemCarrinhoRepository.deleteById(id);
    }
}
