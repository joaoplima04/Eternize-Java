package com.example.eternize.eternize.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eternize.eternize.model.Categoria;
import com.example.eternize.eternize.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	List<Produto> findByCategoria(Categoria categoria);
}
