package com.gustavo.gerenciamentoDePessoas.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gustavo.gerenciamentoDePessoas.entities.Pessoa;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class PessoaRepositoryTest {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	@DisplayName("Deve salvar uma nova Pessoa")
	public void savePessoaTest() {
		// Cenário
		Pessoa newPessoa = new Pessoa(null, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		// Execução
		Pessoa savedPessoa = pessoaRepository.save(newPessoa);
		
		// Verificação
		Assertions.assertThat(savedPessoa.getId()).isNotNull();
		Assertions.assertThat(savedPessoa.getNome()).isEqualTo("Gustavo Silva Cruz");
		Assertions.assertThat(savedPessoa.getDataDeNascimento()).isEqualTo(LocalDate.of(1996, 10, 17));
	}
	
	@Test
	@DisplayName("Deve obter uma pessoa pelo id")
	public void findByIdTest() {
		// Cenário
		Pessoa pessoa = new Pessoa(null, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		entityManager.persist(pessoa);
		
		// Execução
		Optional<Pessoa> foundPessoa = pessoaRepository.findById(pessoa.getId());
		
		// Verificação
		Assertions.assertThat(foundPessoa.isPresent()).isTrue();
		Assertions.assertThat(foundPessoa.get().getId()).isNotNull();
		Assertions.assertThat(foundPessoa.get().getNome()).isEqualTo("Gustavo Silva Cruz");
		Assertions.assertThat(foundPessoa.get().getDataDeNascimento()).isEqualTo(LocalDate.of(1996, 10, 17));
	}
	
	@Test
	public void findAllTest() {
		// Cenário
		PageRequest pageRequest = PageRequest.of(0, 24, Direction.valueOf("ASC"), "nome");
		
		Pessoa pessoa = new Pessoa(null, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		entityManager.persist(pessoa);
		
		// Execução
		Page<Pessoa> foundPessoas = pessoaRepository.findAll(pageRequest);
		
		// Verificação
		Assertions.assertThat(foundPessoas.getNumberOfElements()).isEqualTo(1);
		Assertions.assertThat(foundPessoas.getTotalElements()).isEqualTo(1);
		Assertions.assertThat(foundPessoas.getTotalPages()).isEqualTo(1);
	}

}
