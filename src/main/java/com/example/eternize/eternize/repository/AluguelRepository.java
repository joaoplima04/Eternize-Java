package com.example.eternize.eternize.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.eternize.eternize.model.Aluguel;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long>{
	
	@Query("SELECT a FROM Aluguel a " +
		       "JOIN a.itensAluguel i " +
		       "WHERE i.produto.id = :produtoId " +
		       "AND (:dataInicio BETWEEN a.dataInicioAluguel AND a.dataTerminoAluguel " +
		       "OR :dataTermino BETWEEN a.dataInicioAluguel AND a.dataTerminoAluguel " +
		       "OR a.dataInicioAluguel BETWEEN :dataInicio AND :dataTermino " +
		       "OR a.dataTerminoAluguel BETWEEN :dataInicio AND :dataTermino)")
		Optional<List<Aluguel>> findByProdutoEDataSobreposta(
		        @Param("produtoId") Long produtoId,
		        @Param("dataInicio") Date dataInicio,
		        @Param("dataTermino") Date dataTermino);

}
