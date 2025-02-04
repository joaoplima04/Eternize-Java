package com.example.eternize.eternize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.eternize.eternize.model.Cliente;
import com.example.eternize.eternize.service.ClienteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
	private HttpSession session;

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        // Adiciona um objeto cliente vazio ao modelo para preencher o formulário
        model.addAttribute("cliente", new Cliente());
        return "cadastro"; // Nome do template Thymeleaf para a página de cadastro
    }
    
    @PostMapping("/cadastro_post")
    public String cadastrarCliente(@RequestParam("cpf") String cpf, @ModelAttribute("cliente") Cliente cliente, Model model) {
        try {
            clienteService.cadastrar(cliente);
            return "redirect:/users/login"; // Redireciona para a página de login após cadastro bem-sucedido
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro ao cadastrar: " + e);
            return "cadastro"; // Retorna para a mesma página se houver um erro
        }
    }
    
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se o usuário está autenticado
        if (authentication != null && authentication.isAuthenticated() && 
            !(authentication.getPrincipal() instanceof String)) {
            model.addAttribute("logado", "Seu login já está ativo! Deseja fazer o logout?");
        }

        return "login"; 
    }
    
    @PostMapping("/loging_post")
    public String fazerLogin(@RequestParam("email") String email, 
                             @RequestParam("password") String password, 
                             Model model, 
                             HttpServletRequest request) {
        Authentication authentication = clienteService.logar(email, password);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        } else {
            model.addAttribute("errorMessage", "Credenciais inválidas");
            return "login";
        }
    }
}