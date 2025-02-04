package com.example.eternize.eternize.model;

import java.security.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "aluguel")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", nullable = false)
    private Cliente cliente;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataInicioAluguel;
    
    private LocalTime horaInicial;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataTerminoAluguel;
    
    private LocalTime horaFinal;

    private String objetivo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date dataDevolucao;

    private Double precoTotal;
    private String tipoEntrega;
    
    @Column(nullable = true)
    private String contrato;

    @OneToOne(mappedBy = "aluguel", cascade = CascadeType.ALL)
    private Entrega entrega;

    @OneToMany(mappedBy = "aluguel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itensAluguel;
    
    // Métodos construtores da classe
    
	public Aluguel() {
		super();
	}

	
	public Aluguel(Integer id, Cliente cliente, Date dataInicioAluguel, LocalTime horaInicial, Date dataTerminoAluguel,
			LocalTime horaFinal, String objetivo, Date dataDevolucao, Double precoTotal, String tipoEntrega, String contrato,
			Entrega entrega, List<ItemCarrinho> itensAluguel) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.dataInicioAluguel = dataInicioAluguel;
		this.horaInicial = horaInicial;
		this.dataTerminoAluguel = dataTerminoAluguel;
		this.horaFinal = horaFinal;
		this.objetivo = objetivo;
		this.dataDevolucao = dataDevolucao;
		this.precoTotal = precoTotal;
		this.tipoEntrega = tipoEntrega;
		this.contrato = contrato;
		this.entrega = entrega;
		this.itensAluguel = itensAluguel;
	}


	public Aluguel(Entrega entrega) {
		super();
		this.entrega = entrega;
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
	
	public LocalTime getHoraInicial() {
		return horaInicial;
	}


	public void setHoraInicial(LocalTime horaInicial) {
		this.horaInicial = horaInicial;
	}


	public LocalTime getHoraFinal() {
		return horaFinal;
	}


	public void setHoraFinal(LocalTime horaFinal) {
		this.horaFinal = horaFinal;
	}


	public Date getDataInicioAluguel() {
		return dataInicioAluguel;
	}



	public void setDataInicioAluguel(Date dataInicioAluguel) {
		this.dataInicioAluguel = dataInicioAluguel;
	}



	public Date getDataTerminoAluguel() {
		return dataTerminoAluguel;
	}



	public void setDataTerminoAluguel(Date dataTerminoAluguel) {
		this.dataTerminoAluguel = dataTerminoAluguel;
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

	public List<ItemCarrinho> getItensAluguel() {
		return itensAluguel;
	}

	public void setItensAluguel(List<ItemCarrinho> itensAluguel) {
		this.itensAluguel = itensAluguel;
	}
	
	

    
}
