package com.example.eternize.eternize.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.eternize.eternize.model.ItemCarrinho;
import com.example.eternize.eternize.model.Produto;
import com.example.eternize.eternize.service.ItemCarrinhoService;
import com.example.eternize.eternize.service.ProdutoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
	
    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private ItemCarrinhoService itemCarrinhoService;
	
	@GetMapping("/")
	public String index() {
		return "index"; 
	}
	
	@GetMapping("categoria/{categoria}")
    public String categoriaView(@PathVariable String categoria, Model model) {
        
        List<Produto> produtos = produtoService.getProdutosByCategoria(categoria);        
        
        categoria = categoria.replace("_", " ");
        
        model.addAttribute("categoria", categoria);
        model.addAttribute("produtos", produtos);
        
        return "categoria";
        		
    }
	
	@GetMapping("carrinho/")
	public String carrinhoView(Model model, HttpServletRequest request) {
		if (request.getSession().getId() != null) {
			Optional<List<ItemCarrinho>> itens = itemCarrinhoService.getPorJsessionId(request.getSession().getId());
			if (itens.isEmpty() || itens.get().size() == 0) {
				model.addAttribute("vazio", true);
			} else {
				System.out.println("Carrinho: " + itens.get().toString());
				model.addAttribute("cartItems", itens.get());
			}
		} //else {
			//model.addAttribute("semLogin", true);
			//return "login";)
		//}
		
		return "carrinho";
	}
	
	
}
