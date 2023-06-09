package com.gustavo.gerenciamentoDePessoas.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoNewDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoPrincipalUpdateDTO;
import com.gustavo.gerenciamentoDePessoas.entities.Endereco;
import com.gustavo.gerenciamentoDePessoas.entities.Pessoa;
import com.gustavo.gerenciamentoDePessoas.repositories.EnderecoRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EnderecoServiceTest {
	
	EnderecoService enderecoService;
	
	@MockBean
	EnderecoRepository enderecoRepository;
	
	@MockBean
	PessoaService pessoaService;
	
	@BeforeEach
	public void setUp() {
		enderecoService = new EnderecoService(enderecoRepository, pessoaService);
	}
	
	@Test
	@DisplayName("Deve salvar um novo Endereço")
	public void saveEnderecoTest() {
		// Cenário
		Long id = 1l;
		
		EnderecoNewDTO newEndereco = new EnderecoNewDTO("Rua Belém", "88160-396", "646", "Biguaçu", true);		
		Pessoa foundPessoa = new Pessoa(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));		
		Endereco savedEndereco = new Endereco(id, "Rua Belém", "88160-396", "646", "Biguaçu", true, foundPessoa);
		
		Mockito.when(pessoaService.findById(id)).thenReturn(foundPessoa);
		Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(savedEndereco);
		
		// Execução
		EnderecoDTO savedEnderecoDto = org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> enderecoService.save(id, newEndereco));
		
		// Verificação
		Assertions.assertThat(savedEnderecoDto.getId()).isEqualTo(id);
		Assertions.assertThat(savedEnderecoDto.getLogradouro()).isEqualTo("Rua Belém");
		Assertions.assertThat(savedEnderecoDto.getCep()).isEqualTo("88160-396");
		Assertions.assertThat(savedEnderecoDto.getNumero()).isEqualTo("646");
		Assertions.assertThat(savedEnderecoDto.getCidade()).isEqualTo("Biguaçu");
		Assertions.assertThat(savedEnderecoDto.getEnderecoPrincipal()).isEqualTo(true);
		Mockito.verify(enderecoRepository, Mockito.times(1)).updateEnderecoPrincipalByPessoaExceptId(false, foundPessoa, id);
	}
	
	@Test
	@DisplayName("Deve obter a lista de endereços da pessoa")
	public void findByPessoaTest() {
		// Cenário
		Long id = 1l;
		
		Pessoa foundPessoa = new Pessoa(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));	
		Endereco foundEndereco = new Endereco(id, "Rua Belém", "88160-396", "646", "Biguaçu", false, foundPessoa);
		
		List<Endereco> listEnderecos = List.of(foundEndereco);
		
		Mockito.when(pessoaService.findById(id)).thenReturn(foundPessoa);
		
		Mockito.when(enderecoRepository.findByPessoa(Mockito.any(Pessoa.class))).thenReturn(listEnderecos);
		
		// Execução
		List<EnderecoDTO> foundEnderecos = enderecoService.findByPessoa(id);
				
		// Verificação
		Assertions.assertThat(foundEnderecos).hasSize(1);
		Assertions.assertThat(foundEnderecos.get(0).getLogradouro()).isEqualTo("Rua Belém");
		Assertions.assertThat(foundEnderecos.get(0).getCep()).isEqualTo("88160-396");
		Assertions.assertThat(foundEnderecos.get(0).getNumero()).isEqualTo("646");
		Assertions.assertThat(foundEnderecos.get(0).getCidade()).isEqualTo("Biguaçu");
		Assertions.assertThat(foundEnderecos.get(0).getEnderecoPrincipal()).isEqualTo(false);
	}
	
	@Test
	@DisplayName("Deve atualizar o campo enderecoPrincipal de um endereço")
	public void updateEnderecoPrincipalByIdTest() {
		// Cenário
		Long id = 1l;
		
		EnderecoPrincipalUpdateDTO enderecoPrincipalUpdateDTO = new EnderecoPrincipalUpdateDTO(id);
		Pessoa foundPessoa = new Pessoa(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		Endereco foundEndereco = new Endereco(id, "Rua Belém", "88160-396", "646", "Biguaçu", false, foundPessoa);
		Endereco savedEndereco = new Endereco(id, "Rua Belém", "88160-396", "646", "Biguaçu", true, foundPessoa);
		
		Mockito.when(pessoaService.findById(id)).thenReturn(foundPessoa);
		Mockito.when(enderecoRepository.findByIdAndPessoa(Mockito.anyLong(), Mockito.any(Pessoa.class))).thenReturn(Optional.of(foundEndereco));
		Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(savedEndereco);
		
		// Execução
		EnderecoDTO savedEnderecoDto = org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> enderecoService.updateEnderecoPrincipalById(id, enderecoPrincipalUpdateDTO));
				
		// Verificação
		Assertions.assertThat(savedEnderecoDto.getId()).isEqualTo(id);
		Assertions.assertThat(savedEnderecoDto.getLogradouro()).isEqualTo("Rua Belém");
		Assertions.assertThat(savedEnderecoDto.getCep()).isEqualTo("88160-396");
		Assertions.assertThat(savedEnderecoDto.getNumero()).isEqualTo("646");
		Assertions.assertThat(savedEnderecoDto.getCidade()).isEqualTo("Biguaçu");
		Assertions.assertThat(savedEnderecoDto.getEnderecoPrincipal()).isEqualTo(true);
		Mockito.verify(enderecoRepository, Mockito.times(1)).updateEnderecoPrincipalByPessoaExceptId(false, foundPessoa, id);
	}
	
	@Test
	@DisplayName("Deve atualizar o enderecoPrincipal de todos os endereços de uma pessoa, exceto o endereço com o id específico")
	public void updateEnderecoPrincipalByPessoaExceptIdTest() {
		// Cenário
		Long id = 1l;
		
		Pessoa pessoa = new Pessoa(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));	
		
		// Execução
		org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> enderecoService.updateEnderecoPrincipalByPessoaExceptId(false, pessoa, id));
		
		// Verificação
		Mockito.verify(enderecoRepository, Mockito.times(1)).updateEnderecoPrincipalByPessoaExceptId(false, pessoa, id);
	}
	
}
