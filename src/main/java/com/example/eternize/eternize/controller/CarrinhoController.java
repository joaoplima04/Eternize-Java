package com.example.eternize.eternize.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.eternize.eternize.model.Aluguel;
import com.example.eternize.eternize.model.Cliente;
import com.example.eternize.eternize.model.ItemCarrinho;
import com.example.eternize.eternize.model.Produto;
import com.example.eternize.eternize.service.AluguelService;
import com.example.eternize.eternize.service.ClienteService;
import com.example.eternize.eternize.service.ItemCarrinhoService;
import com.example.eternize.eternize.service.ProdutoService;
import com.example.eternize.eternize.config.ThymeleafPageRenderer;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("carrinho/")
public class CarrinhoController {
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	ItemCarrinhoService itemCarrinhoService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	AluguelService aluguelService;
	
	@Autowired
	ThymeleafPageRenderer renderer;
	
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
	
	@PostMapping("/envia-aluguel")
	public ResponseEntity<?> salvaAluguel(@ModelAttribute("aluguel") Aluguel aluguel, @RequestParam("itemIds") List<Long> itemIds, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		List<ItemCarrinho> cartItems = itemCarrinhoService.findByIds(itemIds);
		for (ItemCarrinho item : cartItems) {
			if (item.getQuantidade() > item.getProduto().getQuantidadeEstoque()) {
				model.addAttribute("semEstoque", true);
				model.addAttribute("itemSemEstoque", item);
				  return ResponseEntity.ok().body(renderer.renderer("carrinho", request, response));
			}
	        boolean disponivel = itemCarrinhoService.verificarDisponibilidade(
	        		item, aluguel.getDataInicioAluguel(), aluguel.getDataTerminoAluguel());
	        if (!disponivel) {
	        	model.addAttribute("indisponivel", true);
	        	model.addAttribute("itemIdisponivel", item);
	            return ResponseEntity.ok().body(renderer.renderer("carrinho", request, response));
	        }
		}
		
	    // Atribuir os itens ao aluguel
	    for (ItemCarrinho item : cartItems) {
	        item.setAluguel(aluguel);
	    }
	    aluguel.setItensAluguel(cartItems);
	    
	    double total = 0;
	    
	    // Atribuir o cliente ao aluguel
	    var authentication = SecurityContextHolder.getContext().getAuthentication();
	    Object principal = authentication.getPrincipal();
	    if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();

            // Recuperar o cliente pelo e-mail
            Optional<Cliente> cliente = clienteService.findByEmail(email);

            if (cliente.isEmpty()) {
                return ResponseEntity.ok().body("Cliente não encontrado.");
            }
	    
            aluguel.setCliente(cliente.get());
            
            total = itemCarrinhoService.getTotalCarrinho(request.getSession().getId());
            
            System.out.println("Data inicial: " + aluguel.getDataInicioAluguel());
            
            aluguel.setPrecoTotal(total);
		
            aluguelService.save(aluguel);
		
            return ResponseEntity.ok().body("Locação salva com sucesso!");
		
	    } 
	    
		return ResponseEntity.ok().body("Usuário não autenticado");
	
	}
}
