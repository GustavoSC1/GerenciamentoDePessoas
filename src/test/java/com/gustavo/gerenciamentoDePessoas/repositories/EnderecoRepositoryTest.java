package com.gustavo.gerenciamentoDePessoas.repositories;

import java.time.LocalDate;
import java.util.List;

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
		Pessoa pessoa = new Pessoa(null, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		entityManager.persist(pessoa);
		
		Endereco newEndereco = new Endereco(null, "Rua Belém", "88160-396", "646", "Biguaçu", false, pessoa);
		
		// Execução
		Endereco savedEndereco = enderecoRepository.save(newEndereco);
		
		// Verificação
		Assertions.assertThat(savedEndereco.getId()).isNotNull();
		Assertions.assertThat(savedEndereco.getLogradouro()).isEqualTo("Rua Belém");
		Assertions.assertThat(savedEndereco.getCep()).isEqualTo("88160-396");
		Assertions.assertThat(savedEndereco.getNumero()).isEqualTo("646");
		Assertions.assertThat(savedEndereco.getCidade()).isEqualTo("Biguaçu");
		Assertions.assertThat(savedEndereco.getEnderecoPrincipal()).isEqualTo(false);
		Assertions.assertThat(savedEndereco.getPessoa()).isNotNull();
	}
	
	@Test
	@DisplayName("Deve obter a lista de endereços da pessoa")
	public void findByPessoaTest() {
		// Cenário
		Pessoa pessoa = new Pessoa(null, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		entityManager.persist(pessoa);
		
		Endereco endereco1 = new Endereco(null, "Rua Belém", "88160-396", "646", "Biguaçu", false, pessoa);
		Endereco endereco2 = new Endereco(null, "Rua Oclécio Barbosa Martins", "79050-460", "341", "Campo Grande", false, null);
		
		entityManager.persist(endereco1);
		entityManager.persist(endereco2);
				
		// Execução
		List<Endereco> foundEnderecos = enderecoRepository.findByPessoa(pessoa);
		
		// Verificação
		Assertions.assertThat(foundEnderecos).hasSize(1);
		Assertions.assertThat(foundEnderecos).contains(endereco1);
	}

}
