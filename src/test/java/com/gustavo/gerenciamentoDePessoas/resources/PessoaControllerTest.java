package com.gustavo.gerenciamentoDePessoas.resources;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gustavo.gerenciamentoDePessoas.dtos.PessoaDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.PessoaNewDTO;
import com.gustavo.gerenciamentoDePessoas.services.PessoaService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = PessoaController.class)
@AutoConfigureMockMvc
public class PessoaControllerTest {
	
	static String PESSOA_API = "/pessoas";
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	PessoaService pessoaService;
	
	ObjectMapper mapper;
	
	@BeforeEach
	public void setUp() {
		// Adicionando módulo para o jackson suportar o LocalDate
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}
	
	@Test
	@DisplayName("Deve salvar uma nova Pessoa")
	public void savePessoaTest() throws Exception {
		// Cenário		
		long id = 2l;
		
		PessoaNewDTO newPessoa = new PessoaNewDTO("Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		PessoaDTO savedPessoa = new PessoaDTO(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		BDDMockito.given(pessoaService.save(Mockito.any(PessoaNewDTO.class))).willReturn(savedPessoa);
				
		String json = mapper.writeValueAsString(newPessoa);
		
		// Execução
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(PESSOA_API)
													.contentType(MediaType.APPLICATION_JSON)
													.accept(MediaType.APPLICATION_JSON)
													.content(json);		
		
		// Verificação
		mvc
		.perform(request)
		.andExpect( MockMvcResultMatchers.status().isCreated() )
		.andExpect( MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, Matchers.containsString("/pessoas/"+id)));
	}
	
	@Test	
	@DisplayName("Deve lançar um erro de validação quando não há dados suficientes para a criação da pessoa")
	public void saveInvalidPessoaTest() throws Exception {
		// Cenário	
		PessoaNewDTO newPessoa = new PessoaNewDTO();
						
		String json = mapper.writeValueAsString(newPessoa);
		
		// Execução
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(PESSOA_API)
													.contentType(MediaType.APPLICATION_JSON)
													.accept(MediaType.APPLICATION_JSON)
													.content(json);		
		
		// Verificação
		mvc.perform(request)
					.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
					.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(2)));
	}
	
	@Test
	@DisplayName("Deve atualizar uma pessoa")
	public void updatePessoaTest() throws Exception {
		// Cenário
		Long id = 2l;
		
		PessoaNewDTO pessoaUpdateDTO = new PessoaNewDTO("Fulano Cauê Calebe Jesus", LocalDate.of(1997, 11, 14));
				
		PessoaDTO updatedPessoa = new PessoaDTO(id, "Fulano Cauê Calebe Jesus", LocalDate.of(1997, 11, 14));
				
		BDDMockito.given(pessoaService.update(Mockito.anyLong(), Mockito.any(PessoaNewDTO.class))).willReturn(updatedPessoa);
		
		String json = mapper.writeValueAsString(pessoaUpdateDTO);
		
		// Execução
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.put(PESSOA_API.concat("/"+id))
													.contentType(MediaType.APPLICATION_JSON)
													.accept(MediaType.APPLICATION_JSON)
													.content(json);
		// Verificação
		mvc.perform(request)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
		.andExpect(MockMvcResultMatchers.jsonPath("nome").value("Fulano Cauê Calebe Jesus"))
		.andExpect(MockMvcResultMatchers.jsonPath("dataDeNascimento").value("1997-11-14"));
	}
	
	@Test
	@DisplayName("Should throw validation error when there is not enough data for user updating")
	public void updateInvalidPessoaTest() throws Exception {
		// Scenario
		Long id = 2l;
		
		PessoaDTO user = new PessoaDTO();
		
		String json = mapper.writeValueAsString(user);
				
		// Execution
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.put(PESSOA_API.concat("/"+id))
													.contentType(MediaType.APPLICATION_JSON)
													.accept(MediaType.APPLICATION_JSON)
													.content(json);		
		// Verification
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
			.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(2)));
	}

}
