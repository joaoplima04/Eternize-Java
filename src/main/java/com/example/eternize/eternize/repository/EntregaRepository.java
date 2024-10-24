package com.example.eternize.eternize.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eternize.eternize.model.Entrega;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long>{
	
}
