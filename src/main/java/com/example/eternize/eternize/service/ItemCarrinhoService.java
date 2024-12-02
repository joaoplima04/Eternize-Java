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
    
    // Método para achar um itemCarrinho
    public Optional<ItemCarrinho> getPorProdutoIdEJsessionId(Long produtoId, String jsessionId) {
    	return itemCarrinhoRepository.findByProdutoIdAndJsessionId(produtoId, jsessionId);
    }
    
    // Método para listar os itemCarrinho ativos no carrinho
    public Optional<List<ItemCarrinho>> getPorJsessionId(String jsessionId) {
    	return itemCarrinhoRepository.findByJsessionId(jsessionId);
    }
    
    // Método para buscar o total do carrinho
    public double getTotalCarrinho(String jSessionId) {
    	Optional<List<ItemCarrinho>> itens = getPorJsessionId(jSessionId);
    	double total = 0;
    	for (int i = 0; i < itens.get().size(); i++) {
    		total += itens.get().get(i).getQuantidade() * itens.get().get(i).getProduto().getPreco();
    	}
    	return total;
    }
    
    public void atualizaQuantidade(Long id, int quantidade) {
    	Optional<ItemCarrinho> itemCarrinho = this.findById(id);
    	if (itemCarrinho.isPresent()) {
    		itemCarrinho.get().setQuantidade(quantidade);
    		save(itemCarrinho.get());
    	} 
    }
    
    // Método para deletar um itemCarrinho pelo ID
    public void deleteById(Long id) {
    	itemCarrinhoRepository.deleteById(id);
    }
}
