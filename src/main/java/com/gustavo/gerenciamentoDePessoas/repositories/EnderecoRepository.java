package com.gustavo.gerenciamentoDePessoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavo.gerenciamentoDePessoas.entities.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
