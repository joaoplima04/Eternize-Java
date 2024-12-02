package com.example.eternize.eternize.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eternize.eternize.model.ItemCarrinho;
import com.example.eternize.eternize.model.Produto;
import com.example.eternize.eternize.service.ItemCarrinhoService;
import com.example.eternize.eternize.service.ProdutoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("carrinho/")
public class CarrinhoController {
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	ItemCarrinhoService itemCarrinhoService;
	
	
	@PostMapping("adiciona-ao-carrinho/{produtoId}")
	public ResponseEntity<?> adicionaAoCarrinho(@PathVariable Long produtoId, HttpServletRequest request) {
		
		Optional<Produto> produto = produtoService.findById(produtoId);
		
		if (produto.isPresent()) {
			Optional<ItemCarrinho> itemCarrinhoExistente = itemCarrinhoService.getPorProdutoIdEJsessionId(produtoId, request.getSession().getId());
			
			if (itemCarrinhoExistente.isPresent()) {
				int adicionar = itemCarrinhoExistente.get().getQuantidade() + 1;
				itemCarrinhoExistente.get().setQuantidade(adicionar);
				itemCarrinhoService.save(itemCarrinhoExistente.get());
				return ResponseEntity.ok().body("Quantidade atualizada para " + adicionar);
			} else {
				ItemCarrinho itemCarrinho = new ItemCarrinho();
				itemCarrinho.setProduto(produto.get());
				itemCarrinho.setQuantidade(1);
				itemCarrinho.setTotal();
				itemCarrinho.setJsessionId(request.getSession().getId());
				itemCarrinhoService.save(itemCarrinho);
				return ResponseEntity.ok().body("Produto adicionado ao carrinho com sucesso.");
			}
		} else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: Produto não encontrado.");
		}
	}
	
	@GetMapping("total-carrinho")
	public ResponseEntity<?> getTotalCarrinho(HttpServletRequest request) {
		double total = itemCarrinhoService.getTotalCarrinho(request.getSession().getId());
		return ResponseEntity.ok(total);
	}
	
	@PostMapping("remover-do-carrinho/{itemCarrinhoId}")
	public ResponseEntity<?> removeDoCarrinho(@PathVariable Long itemCarrinhoId) {
		try {
			itemCarrinhoService.deleteById(itemCarrinhoId);
		} catch (Exception e) {
			return ResponseEntity.ok().body("Erro ao deletar o Item: " + e);
		}
		return ResponseEntity.ok().body("Item deletado com sucesso.");
	}
	
	@PostMapping("atualiza-quantidade/{itemCarrinhoId}/{quantidade}")
	public ResponseEntity<?> atualizaQuantidade(@PathVariable Long itemCarrinhoId, @PathVariable int quantidade) {
		try {
			itemCarrinhoService.atualizaQuantidade(itemCarrinhoId, quantidade);
		} catch (Exception e) {
			ResponseEntity.ok().body("Erro ao atualizar o Item: " + e);
		}
		return ResponseEntity.ok().body("Item atualizado com sucesso.");
	}
	
	@GetMapping("/carrinho/verificar-vazio")
	public boolean verificarCarrinhoVazio(HttpServletRequest request) {
		Optional<List<ItemCarrinho>> cartItems = itemCarrinhoService.getPorJsessionId(request.getSession().getId());
	  return cartItems.isEmpty();
	}
	
}
