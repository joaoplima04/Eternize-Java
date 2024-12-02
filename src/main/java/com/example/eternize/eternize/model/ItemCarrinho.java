package com.example.eternize.eternize.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_carrinho")
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "aluguel_id", nullable = true)
    private Aluguel aluguel;
    
    private String jsessionId;
    
    private Integer quantidade;
    private Double total;
    
 // Métodos construtores da classe
    
	public ItemCarrinho() {
		super();
	}

public ItemCarrinho(Integer id, Produto produto, Aluguel aluguel, Integer quantidade, Double total, String jsessionId) {
	super();
	this.id = id;
	this.produto = produto;
	this.aluguel = aluguel;
	this.quantidade = quantidade;
	this.total = total;
	this.jsessionId = jsessionId;
}

//Métodos de acesso da classe



public Integer getId() {
	return id;
}

public String getJsessionId() {
	return jsessionId;
}

public void setJsessionId(String jsessionId) {
	this.jsessionId = jsessionId;
}

public Produto getProduto() {
	return produto;
}

public void setProduto(Produto produto) {
	this.produto = produto;
}

public Aluguel getAluguel() {
	return aluguel;
}

public void setAluguel(Aluguel aluguel) {
	this.aluguel = aluguel;
}

public Integer getQuantidade() {
	return quantidade;
}

public void setQuantidade(Integer quantidade) {
	this.quantidade = quantidade;
}

public Double getTotal() {
	return total;
}

public void setTotal() {
	this.total = this.getQuantidade() * this.produto.getPreco();
}
	
	
}