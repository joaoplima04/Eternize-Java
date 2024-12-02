package com.example.eternize.eternize.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eternize.eternize.model.Cliente;
import com.example.eternize.eternize.repository.ClienteRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;

	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private HttpSession session;

	// Método para listar todos os clientes
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	// Método para buscar um cliente pelo ID
	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}

	public void cadastrar(Cliente cliente) throws Exception {
		// Lógica para verificar e cadastrar cliente, por exemplo, checar se email ou
		// CPF já existem
		if (clienteRepository.existsByEmail(cliente.getEmail()) || clienteRepository.existsByCpf(cliente.getCpf())) {
			throw new Exception("Cliente já cadastrado com esse e-mail ou CPF.");
		}
		if (clienteRepository.existsByTelefone(cliente.getTelefone())) {
			throw new Exception("Cliente já cadastrado com esse telefone.");
		}
		// Hashing a senha antes de salvar
		String hashedPassword = passwordService.hashPassword(cliente.getPassword());
		cliente.setPassword(hashedPassword);
		clienteRepository.save(cliente);
	}

	public Authentication logar(String email, String password) {
	    if (clienteRepository.existsByEmail(email)) {
	        Cliente cliente = clienteRepository.getByEmail(email);
	        String hashedPassword = cliente.getPassword();
	        if (passwordService.verifyPassword(password, hashedPassword)) {
	            UsernamePasswordAuthenticationToken token = 
	                new UsernamePasswordAuthenticationToken(cliente, null, cliente.getAuthorities());
	            SecurityContextHolder.getContext().setAuthentication(token);
	            return token;
	        }
	    }
	    return null;
	}
	
	// Método para deletar um cliente pelo ID
	public void deleteById(Long id) {
		clienteRepository.deleteById(id);
	}
}
