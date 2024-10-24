package com.example.eternize.eternize.model;

import jakarta.persistence.*;

@Entity
@Table(name = "entrega")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "aluguel_id")
    private Aluguel aluguel;

    private String cep;
    private String endereco;
    private String bairro;
    private String cidade;
    private String numero;
    private String complemento;
    private String nomeDestinatario;
	
 // Métodos construtores da classe
    
    public Entrega() {
		super();
	}

	public Entrega(Integer id, Aluguel aluguel, String cep, String endereco, String bairro, String cidade, String numero,
			String complemento, String nomeDestinatario) {
		super();
		this.id = id;
		this.aluguel = aluguel;
		this.cep = cep;
		this.endereco = endereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.numero = numero;
		this.complemento = complemento;
		this.nomeDestinatario = nomeDestinatario;
	}
	
	// Métodos de acesso da classe
	
	public Integer getId() {
		return id;
	}

	public Aluguel getAluguel() {
		return aluguel;
	}

	public void setAluguel(Aluguel aluguel) {
		this.aluguel = aluguel;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNomeDestinatario() {
		return nomeDestinatario;
	}

	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}
    
    
    
    
    
}
