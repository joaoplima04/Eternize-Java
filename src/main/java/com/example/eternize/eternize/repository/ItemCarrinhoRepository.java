package com.example.eternize.eternize.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eternize.eternize.model.ItemCarrinho;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long>{
	void deleteByJsessionId(String jsessionId);
	boolean existsByJsessionId(String jsessionId);
	boolean existsByProdutoId(Long produtoId);
	Optional<ItemCarrinho> findByProdutoIdAndJsessionId(Long produtoId, String jsessionId);
	Optional<List<ItemCarrinho>> findByJsessionId(String jsessionId);
}
