package com.example.eternize.eternize.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Produto")
public class Produto {
	
	// Propriedades da classe
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;	
    
    private String nome;
    
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    
    private Double preco;
    private Integer quantidadeEstoque;
    private String imagem;
    private String cor;
 
    @Enumerated(EnumType.STRING)
    private Estilo estilo;
    
    private Boolean publicado;
    
    // Métodos construtores da classe
    public Produto() {
		super();
	}

	public Produto(String nome, Categoria categoria, Double preco, Integer quantidadeEstoque, String imagem,
			String cor, Estilo estilo, Boolean publicado) {
		super();
		this.nome = nome;
		this.categoria = categoria;
		this.preco = preco;
		this.quantidadeEstoque = quantidadeEstoque;
		this.imagem = imagem;
		this.cor = cor;
		this.estilo = estilo;
		this.publicado = publicado;
	}
	
	// Métodos de acesso da classe
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Estilo getEstilo() {
		return estilo;
	}

	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}

	public Boolean getPublicado() {
		return publicado;
	}

	public void setPublicado(Boolean publicado) {
		this.publicado = publicado;
	}
    

	
	
	
    
}
