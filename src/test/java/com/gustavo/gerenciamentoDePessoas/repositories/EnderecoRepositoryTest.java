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
		Assertions.assertThat(savedEndereco).isNotNull();
		Assertions.assertThat(savedEndereco.getId()).isNotNull();
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
		Assertions.assertThat(foundEnderecos).hasSize(1).contains(endereco1);
	}
	
	@Test
	@DisplayName("Deve obter um endereço com base no seu id e na pessoa")
	public void findByIdAndPessoaTest() {
		// Cenário
		Pessoa pessoa = new Pessoa(null, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		entityManager.persist(pessoa);
		
		Endereco endereco1 = new Endereco(null, "Rua Belém", "88160-396", "646", "Biguaçu", false, pessoa);
		Endereco endereco2 = new Endereco(null, "Rua Oclécio Barbosa Martins", "79050-460", "341", "Campo Grande", false, pessoa);
		
		entityManager.persist(endereco1);
		entityManager.persist(endereco2);
				
		// Execução
		Endereco foundEndereco = enderecoRepository.findByIdAndPessoa(endereco1.getId(), pessoa).get();
		
		// Verificação
		Assertions.assertThat(foundEndereco.getId()).isNotNull();
		Assertions.assertThat(foundEndereco).isEqualTo(endereco1);
	}
		
	@Test
	@DisplayName("Deve atualizar o enderecoPrincipal de todos os endereços de uma pessoa, exceto o endereço com o id específico")
	public void updateEnderecoPrincipalByPessoaExceptIdTest() {
		// Cenário	
		Pessoa pessoa = new Pessoa(null, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		entityManager.persist(pessoa);
		
		Endereco endereco1 = new Endereco(null, "Rua Belém", "88160-396", "646", "Biguaçu", true, pessoa);
		Endereco endereco2 = new Endereco(null, "Rua Oclécio Barbosa Martins", "79050-460", "341", "Campo Grande", true, pessoa);
		
		entityManager.persist(endereco1);
		entityManager.persist(endereco2);
		
		// Execução
		enderecoRepository.updateEnderecoPrincipalByPessoaExceptId(false, pessoa, endereco1.getId());
		
		// limpa o cache e força entityManager.find do banco de dados
		entityManager.clear();
		
		Endereco foundEndereco1 = entityManager.find(Endereco.class, endereco1.getId());
		Endereco foundEndereco2 = entityManager.find(Endereco.class, endereco2.getId());
		
		// Verificação
		endereco2.setEnderecoPrincipal(false);
		
		Assertions.assertThat(foundEndereco1).isNotNull();
		Assertions.assertThat(foundEndereco1).isEqualTo(endereco1);
		
		Assertions.assertThat(foundEndereco2).isNotNull();
		Assertions.assertThat(foundEndereco2).isEqualTo(endereco2);
	}

}
