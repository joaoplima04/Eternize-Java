package com.example.eternize.eternize.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eternize.eternize.model.Aluguel;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long>{

}
