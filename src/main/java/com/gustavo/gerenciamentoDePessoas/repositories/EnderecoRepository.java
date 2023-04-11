package com.gustavo.gerenciamentoDePessoas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavo.gerenciamentoDePessoas.entities.Endereco;
import com.gustavo.gerenciamentoDePessoas.entities.Pessoa;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
	List<Endereco> findByPessoa(Pessoa pessoa);

}
