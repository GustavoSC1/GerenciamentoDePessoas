package com.gustavo.gerenciamentoDePessoas.repositories;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gustavo.gerenciamentoDePessoas.entities.Endereco;
import com.gustavo.gerenciamentoDePessoas.entities.Pessoa;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class EnderecoRepositoryTest {
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	@DisplayName("Deve salvar um novo Endereço")
	public void saveEnderecoTest() {
		// Cenário
		Pessoa newPessoa = new Pessoa(null, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		entityManager.persist(newPessoa);
		
		Endereco newEndereco = new Endereco(null, "Rua Belém", "88160-396", "646", "Biguaçu", false, newPessoa);
		
		// Execução
		Endereco savedEndereco = enderecoRepository.save(newEndereco);
		
		// Verificação
		Assertions.assertThat(savedEndereco.getId()).isNotNull();
		Assertions.assertThat(savedEndereco.getLogradouro()).isEqualTo("Rua Belém");
		Assertions.assertThat(savedEndereco.getCep()).isEqualTo("88160-396");
		Assertions.assertThat(savedEndereco.getNumero()).isEqualTo("646");
		Assertions.assertThat(savedEndereco.getCidade()).isEqualTo("Biguaçu");
		Assertions.assertThat(savedEndereco.getPessoa()).isNotNull();
	}

}
