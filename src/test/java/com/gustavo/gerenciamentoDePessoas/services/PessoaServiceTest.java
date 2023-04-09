package com.gustavo.gerenciamentoDePessoas.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
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

import com.gustavo.gerenciamentoDePessoas.dtos.PessoaDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.PessoaNewDTO;
import com.gustavo.gerenciamentoDePessoas.entities.Pessoa;
import com.gustavo.gerenciamentoDePessoas.repositories.PessoaRepository;
import com.gustavo.gerenciamentoDePessoas.services.exceptions.ObjectNotFoundException;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PessoaServiceTest {
	
	PessoaService pessoaService;
	
	@MockBean
	PessoaRepository pessoaRepository;
	
	@BeforeEach
	public void setUp() {
		this.pessoaService= Mockito.spy(new PessoaService(pessoaRepository));
	}
	
	@Test
	@DisplayName("Deve salvar uma nova Pessoa")
	public void savePessoaTest() {
		// Cenário
		Long id = 2l;
		
		PessoaNewDTO newPessoa = new PessoaNewDTO("Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		Pessoa savedPessoa = new Pessoa(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		Mockito.when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(savedPessoa);
		
		// Execução
		PessoaDTO savedPessoaDto = pessoaService.save(newPessoa);
		
		// Verificação
		Assertions.assertThat(savedPessoaDto.getId()).isEqualTo(id);
		Assertions.assertThat(savedPessoaDto.getNome()).isEqualTo("Gustavo Silva Cruz");
		Assertions.assertThat(savedPessoaDto.getDataDeNascimento()).isEqualTo(LocalDate.of(1996, 10, 17));
	}
	
	@Test
	@DisplayName("Deve obter uma pessoa por id")
	public void findByIdTest() {		
		// Cenário
		Long id = 2l;
		
		Pessoa pessoa = new Pessoa(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
			
		Mockito.when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));
		
		// Execução
		Pessoa foundPessoa = pessoaService.findById(id);
		
		// Verificação
		Assertions.assertThat(foundPessoa.getId()).isEqualTo(id);
		Assertions.assertThat(foundPessoa.getNome()).isEqualTo("Gustavo Silva Cruz");
		Assertions.assertThat(foundPessoa.getDataDeNascimento()).isEqualTo(LocalDate.of(1996, 10, 17));		
	}
	
	@Test
	@DisplayName("Deve retornar erro ao tentar obter um pessoa inexistente")
	public void userNotFoundByIdTest() {
		// Cenário
		Long id = 1l;
				
		Mockito.when(pessoaRepository.findById(id)).thenReturn(Optional.empty());
		
		// Execução e Verificação
		Exception exception = assertThrows(ObjectNotFoundException.class, () -> {pessoaService.findById(id);});
		
		String expectedMessage = "Objeto não encontrado! Id: " + id + ", Type: " + Pessoa.class.getName();
		String actualMessage = exception.getMessage();
		
		Assertions.assertThat(actualMessage).isEqualTo(expectedMessage);			
	}
	
	@Test
	@DisplayName("Deve chamar o método findById e retornar a Pessoa ao controlador")
	public void findTest() {
		// Cenário
		long id = 2l;
		
		Pessoa pessoa = new Pessoa(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		Mockito.doReturn(pessoa).when(pessoaService).findById(id);
		
		// Execução
		PessoaDTO foundPessoa = pessoaService.find(id);
		
		// Verificação
		Assertions.assertThat(foundPessoa.getId()).isEqualTo(id);
		Assertions.assertThat(foundPessoa.getNome()).isEqualTo("Gustavo Silva Cruz");
		Assertions.assertThat(foundPessoa.getDataDeNascimento()).isEqualTo(LocalDate.of(1996, 10, 17));
	}
	
	@Test
	@DisplayName("Deve atualizar uma Pessoa")
	public void updatePessoaTest() {
		// Cenário
		Long id = 2l;
		
		PessoaNewDTO pessoaDto = new PessoaNewDTO("Fulano Cauê Calebe Jesus", LocalDate.of(1997, 11, 14));
		Pessoa foundPessoa = new Pessoa(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		Pessoa updatedPessoa = new Pessoa(id, "Fulano Cauê Calebe Jesus", LocalDate.of(1997, 11, 14));
		
		Mockito.when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(updatedPessoa);
		Mockito.doReturn(foundPessoa).when(pessoaService).findById(id);
		
		// Execução
		PessoaDTO updatedPessoaDto = pessoaService.update(id, pessoaDto);
		
		// Verificação
		Assertions.assertThat(updatedPessoaDto.getId()).isEqualTo(id);
		Assertions.assertThat(updatedPessoaDto.getNome()).isEqualTo("Fulano Cauê Calebe Jesus");
		Assertions.assertThat(updatedPessoaDto.getDataDeNascimento()).isEqualTo(LocalDate.of(1997, 11, 14));	
	}

}
