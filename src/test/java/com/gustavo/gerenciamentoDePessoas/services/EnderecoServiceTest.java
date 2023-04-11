package com.gustavo.gerenciamentoDePessoas.services;

import java.time.LocalDate;
import java.util.List;

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
		
		EnderecoNewDTO newEndereco = new EnderecoNewDTO("Rua Belém", "88160-396", "646", "Biguaçu", false, id);		
		Pessoa foundPessoa = new Pessoa(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));		
		Endereco savedEndereco = new Endereco(id, "Rua Belém", "88160-396", "646", "Biguaçu", false, foundPessoa);
		
		Mockito.when(pessoaService.findById(id)).thenReturn(foundPessoa);
		Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(savedEndereco);
		
		// Execução
		EnderecoDTO savedEnderecoDto = enderecoService.save(newEndereco);
		
		// Verificação
		Assertions.assertThat(savedEnderecoDto.getId()).isEqualTo(id);
		Assertions.assertThat(savedEnderecoDto.getLogradouro()).isEqualTo("Rua Belém");
		Assertions.assertThat(savedEnderecoDto.getCep()).isEqualTo("88160-396");
		Assertions.assertThat(savedEnderecoDto.getNumero()).isEqualTo("646");
		Assertions.assertThat(savedEnderecoDto.getCidade()).isEqualTo("Biguaçu");
		Assertions.assertThat(savedEnderecoDto.getPrincipal()).isEqualTo(false);
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
		Assertions.assertThat(foundEnderecos.get(0).getPrincipal()).isEqualTo(false);
	}

}
