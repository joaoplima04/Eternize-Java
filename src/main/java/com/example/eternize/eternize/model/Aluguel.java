package com.example.eternize.eternize.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "aluguel")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", nullable = false)
    private Cliente cliente;

    @Temporal(TemporalType.DATE)
    private Date dataAluguel;

    @Temporal(TemporalType.DATE)
    private Date dataPedido;

    private String objetivo;

    @Temporal(TemporalType.DATE)
    private Date dataDevolucao;

    private Double precoTotal;
    private String tipoEntrega;
    private String contrato;

    @OneToOne(mappedBy = "aluguel", cascade = CascadeType.ALL)
    private Entrega entrega;

    @OneToMany(mappedBy = "aluguel")
    private List<ItemCarrinho> itensCarrinho;
    
    // Métodos construtores da classe
    
	public Aluguel() {
		super();
	}

	public Aluguel(Integer id, Cliente cliente, Date dataAluguel, Date dataPedido, String objetivo, Date dataDevolucao,
			Double precoTotal, String tipoEntrega, String contrato, Entrega entrega, List<ItemCarrinho> itensCarrinho) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.dataAluguel = dataAluguel;
		this.dataPedido = dataPedido;
		this.objetivo = objetivo;
		this.dataDevolucao = dataDevolucao;
		this.precoTotal = precoTotal;
		this.tipoEntrega = tipoEntrega;
		this.contrato = contrato;
		this.entrega = entrega;
		this.itensCarrinho = itensCarrinho;
	}
	
	// Métodos de acesso da classe
	
	public Integer getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDataAluguel() {
		return dataAluguel;
	}

	public void setDataAluguel(Date dataAluguel) {
		this.dataAluguel = dataAluguel;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(Double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public String getTipoEntrega() {
		return tipoEntrega;
	}

	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public List<ItemCarrinho> getItensCarrinho() {
		return itensCarrinho;
	}

	public void setItensCarrinho(List<ItemCarrinho> itensCarrinho) {
		this.itensCarrinho = itensCarrinho;
	}
	
	

    
}
