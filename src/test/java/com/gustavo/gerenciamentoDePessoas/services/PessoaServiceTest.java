package com.gustavo.gerenciamentoDePessoas.services;

import java.time.LocalDate;

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

}
