package com.gustavo.gerenciamentoDePessoas.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
		Optional<Endereco> foundEndereco = enderecoRepository.findByIdAndPessoa(endereco1.getId(), pessoa);
		
		// Verificação
		Assertions.assertThat(foundEndereco.isPresent()).isTrue();
		Assertions.assertThat(foundEndereco.get().getId()).isNotNull();
		Assertions.assertThat(foundEndereco.get().getLogradouro()).isEqualTo("Rua Belém");
		Assertions.assertThat(foundEndereco.get().getCep()).isEqualTo("88160-396");
		Assertions.assertThat(foundEndereco.get().getEnderecoPrincipal()).isEqualTo(false);
	}
	
	@Test
	@DisplayName("Deve atualizar o campo enderecoPrincipal de um endereço")
	public void updateEnderecoPrincipalByIdTest() {
		// Cenário		
		Pessoa pessoa = new Pessoa(null, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		entityManager.persist(pessoa);
		
		Endereco endereco = new Endereco(null, "Rua Belém", "88160-396", "646", "Biguaçu", false, pessoa);
		
		entityManager.persist(endereco);
		
		// Execução
		enderecoRepository.updateEnderecoPrincipalById(true, endereco.getId());
		
		// limpa o cache e força entityManager.find do banco de dados
		entityManager.clear();
		
		Endereco foundEndereco = entityManager.find(Endereco.class, endereco.getId());
		
		// Verificação		
		Assertions.assertThat(foundEndereco).isNotNull();
		Assertions.assertThat(foundEndereco.getId()).isNotNull();
		Assertions.assertThat(foundEndereco.getLogradouro()).isEqualTo("Rua Belém");
		Assertions.assertThat(foundEndereco.getCep()).isEqualTo("88160-396");
		Assertions.assertThat(foundEndereco.getEnderecoPrincipal()).isEqualTo(true);
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
		
		entityManager.clear();
		
		Endereco foundEndereco1 = entityManager.find(Endereco.class, endereco1.getId());
		Endereco foundEndereco2 = entityManager.find(Endereco.class, endereco2.getId());
		
		// Verificação
		Assertions.assertThat(foundEndereco1).isNotNull();
		Assertions.assertThat(foundEndereco1.getId()).isNotNull();
		Assertions.assertThat(foundEndereco1.getLogradouro()).isEqualTo("Rua Belém");
		Assertions.assertThat(foundEndereco1.getCep()).isEqualTo("88160-396");
		Assertions.assertThat(foundEndereco1.getEnderecoPrincipal()).isEqualTo(true);
		
		Assertions.assertThat(foundEndereco2).isNotNull();
		Assertions.assertThat(foundEndereco2.getId()).isNotNull();
		Assertions.assertThat(foundEndereco2.getLogradouro()).isEqualTo("Rua Oclécio Barbosa Martins");
		Assertions.assertThat(foundEndereco2.getCep()).isEqualTo("79050-460");
		Assertions.assertThat(foundEndereco2.getEnderecoPrincipal()).isEqualTo(false);
	}

}
