package com.example.eternize.eternize.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eternize.eternize.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

}
