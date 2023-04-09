package com.gustavo.gerenciamentoDePessoas.repositories;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gustavo.gerenciamentoDePessoas.entities.Pessoa;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class PessoaRepositoryTest {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@Test
	@DisplayName("Deve salvar uma Pessoa")
	public void savePessoaTest() {
		// Scenario
		Pessoa newPessoa = new Pessoa(null, "Gustavo", LocalDate.of(1996, 10, 17));
		
		// Execution
		Pessoa savedPessoa = pessoaRepository.save(newPessoa);
		
		// Verification
		Assertions.assertThat(savedPessoa.getId()).isNotNull();
	}

}
