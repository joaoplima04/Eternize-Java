package com.example.eternize.eternize.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eternize.eternize.model.Aluguel;
import com.example.eternize.eternize.model.ItemCarrinho;
import com.example.eternize.eternize.model.Produto;
import com.example.eternize.eternize.repository.ItemCarrinhoRepository;

@Service
public class ItemCarrinhoService {
    
	private final ItemCarrinhoRepository itemCarrinhoRepository;

    @Autowired
    public ItemCarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository) {
        this.itemCarrinhoRepository = itemCarrinhoRepository;
    }
    
    @Autowired
	ProdutoService produtoService;
    
    @Autowired
    AluguelService aluguelService;
    
    // CRUD
    
    // Método para listar todos os itemCarrinhos
    public List<ItemCarrinho> findAll() {
        return itemCarrinhoRepository.findAll();
    }

    // Método para buscar um itemCarrinho pelo ID
    public Optional<ItemCarrinho> findById(Long id) {
        return itemCarrinhoRepository.findById(id);
    }
    
    public List<ItemCarrinho> findByIds(List<Long> ids) {
    	List<ItemCarrinho> itens = new ArrayList<>();
    	for (Long id : ids) {
    		ItemCarrinho item = this.findById(id).get();
    		itens.add(item);
    	}
    	return itens;
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
    
    public boolean verificarDisponibilidade(ItemCarrinho item, Date dataInicio, Date dataTermino) {
        // Obter o produto relacionado ao item
        Optional<Produto> produto = produtoService.findById(item.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        
        // Obter todas as locações com o produto e data sobreposta
        Optional<List<Aluguel>> locacoes = aluguelService.findByProdutoEDataSobreposta(
                produto.get().getId(), dataInicio, dataTermino);
        
        System.out.println("Locações encontradas: " + (locacoes.isPresent() ? locacoes.get().size() : "Nenhuma"));

        // Calcular a quantidade reservada no período
        if (locacoes.isPresent()) {
        	System.out.println("Locações: " + locacoes.get());
        	System.out.println("Passa aqui");
        	int quantidadeReservada = locacoes.get().stream()
                    .flatMap(aluguel -> aluguel.getItensAluguel().stream())
                    .filter(aluguelItem -> aluguelItem.getProduto().getId().equals(produto.getId()))
                    .mapToInt(ItemCarrinho::getQuantidade)
                    .sum();
        	// Verificar se a quantidade desejada está disponível
        	System.out.println("Quantidade reservada: " + quantidadeReservada);
        	System.out.println("Estoque: " + produto.get().getQuantidadeEstoque());
        	return (produto.get().getQuantidadeEstoque() - quantidadeReservada) >= item.getQuantidade();
        }
        return true;
    }
    
    // Método para deletar um itemCarrinho pelo ID
    public void deleteById(Long id) {
    	itemCarrinhoRepository.deleteById(id);
    }
}
