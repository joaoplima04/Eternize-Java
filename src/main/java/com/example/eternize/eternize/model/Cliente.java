package com.example.eternize.eternize.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    private String nome;
    private String email;
    private String telefone;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    
    private String password;
    private Boolean superuser = false;

    
    // Métodos construtores da classe
    
	public Cliente() {
		super();
	}

	public Cliente(String cpf, String nome, String email, String telefone, Date dataNascimento, String password,
			Boolean superuser) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.setDataNascimento(dataNascimento);
		this.password = password;
		this.superuser = superuser;
	}
	
	// Métodos de acesso da classe

	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Object dataNascimento) {
		
		System.out.println("Está passando aqui porra");
		
	    SimpleDateFormat desiredFormat = new SimpleDateFormat("dd/MM/yyyy");

	    if (dataNascimento instanceof String) {
	        String dateStr = (String) dataNascimento;
	        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
	        try {
	            Date parsedDate = inputFormat.parse(dateStr);
	            this.dataNascimento = parsedDate;
	        } catch (ParseException e) {
	            e.printStackTrace();
	            System.out.println("Erro ao converter data de nascimento: " + dateStr);
	        }
	    } else if (dataNascimento instanceof Date) {
	        Date date = (Date) dataNascimento;
	        // Formata a data para "dd/MM/yyyy" e depois reparseia para garantir o formato desejado
	        try {
	            String formattedDate = desiredFormat.format(date);
	            this.dataNascimento = desiredFormat.parse(formattedDate);
	        } catch (ParseException e) {
	            e.printStackTrace();
	            System.out.println("Erro ao formatar data de nascimento: " + date);
	        }
	    } else {
	        throw new IllegalArgumentException("Formato de data inválido");
	    }
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getSuperuser() {
		return superuser;
	}

	public void setSuperuser(Boolean superuser) {
		this.superuser = superuser;
	}
	
	@Override
	public String toString() {
	    return "Cliente{" +
	            "cpf='" + cpf + '\'' +
	            ", nome='" + nome + '\'' +
	            ", email='" + email + '\'' +
	            ", telefone='" + telefone + '\'' +
	            ", dataNascimento=" + dataNascimento +
	            ", superuser=" + superuser +
	            '}';
	}
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
