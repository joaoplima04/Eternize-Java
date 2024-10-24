package com.example.eternize.eternize.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.eternize.eternize.model.Cliente;
import com.example.eternize.eternize.service.ClienteService;

@Controller
@RequestMapping("/users")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        // Adiciona um objeto cliente vazio ao modelo para preencher o formulário
        model.addAttribute("cliente", new Cliente());
        return "cadastro"; // Nome do template Thymeleaf para a página de cadastro
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Define um conversor para o campo dataNascimento
        SimpleDateFormat formatoEsperado = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoAlternativo = new SimpleDateFormat("yyyy-MM-dd");
        
        binder.registerCustomEditor(Date.class, "dataNascimento", new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                try {
                    // Tenta converter no formato dd/MM/yyyy
                    setValue(formatoEsperado.parse(value));
                } catch (Exception e1) {
                    try {
                        // Se falhar, tenta converter no formato yyyy-MM-dd
                        setValue(formatoAlternativo.parse(value));
                    } catch (Exception e2) {
                        setValue(null); // Se ambos falharem, define como null ou trate de outra forma
                    }
                }
            }
        });
    }
    
    @PostMapping("/cadastro_post")
    public String cadastrarCliente(@RequestParam("cpf") String cpf, @ModelAttribute("cliente") Cliente cliente, Model model) {
    	System.out.println("CPF recebido: " + cpf);
    	System.out.println(cliente.toString());
        try {
            clienteService.cadastrar(cliente);
            return "redirect:/users/login"; // Redireciona para a página de login após cadastro bem-sucedido
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro ao cadastrar: " + e);
            System.out.println("O erro é: " + e);
            return "cadastro"; // Retorna para a mesma página se houver um erro
        }
    }
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; 
    }
}