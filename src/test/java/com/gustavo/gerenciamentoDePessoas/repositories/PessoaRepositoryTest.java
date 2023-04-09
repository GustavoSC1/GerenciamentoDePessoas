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

}
