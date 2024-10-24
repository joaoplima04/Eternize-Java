package com.example.eternize.eternize.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.eternize.eternize.model.Produto;
import com.example.eternize.eternize.service.ProdutoService;

@Controller
public class IndexController {
	
    @Autowired
    private ProdutoService produtoService;
	
	@GetMapping("/")
	public String index() {
		return "index"; 
	}
	
	@GetMapping("categoria/{categoria}")
    public String categoria(@PathVariable String categoria, Model model) {
        
        List<Produto> produtos = produtoService.getProdutosByCategoria(categoria);        
        
        categoria = categoria.replace("_", " ");
        
        model.addAttribute("categoria", categoria);
        model.addAttribute("produtos", produtos);
        
        return "categoria";
        		
    }
	
	
}
