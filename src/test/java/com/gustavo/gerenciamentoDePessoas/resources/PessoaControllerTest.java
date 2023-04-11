package com.gustavo.gerenciamentoDePessoas.resources;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.PessoaDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.PessoaNewDTO;
import com.gustavo.gerenciamentoDePessoas.services.EnderecoService;
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
	
	@MockBean
	EnderecoService enderecoService;
	
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
	@DisplayName("Deve obter uma pessoa por id")
	public void findPessoaTest() throws Exception {
		// Cenário
		Long id = 2l;
		
		PessoaDTO pessoa = new PessoaDTO(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		BDDMockito.given(pessoaService.find(id)).willReturn(pessoa);
		
		// Execução
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.get(PESSOA_API.concat("/"+id))
													.accept(MediaType.APPLICATION_JSON);
		
		// Verificação
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
			.andExpect(MockMvcResultMatchers.jsonPath("nome").value("Gustavo Silva Cruz"))
			.andExpect(MockMvcResultMatchers.jsonPath("dataDeNascimento").value("1996-10-17"));
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
	@DisplayName("Deve lançar erro de validação quando não há dados suficientes para atualização da pessoa")
	public void updateInvalidPessoaTest() throws Exception {
		// Cenário
		Long id = 2l;
		
		PessoaDTO user = new PessoaDTO();
		
		String json = mapper.writeValueAsString(user);
				
		// Execução
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.put(PESSOA_API.concat("/"+id))
													.contentType(MediaType.APPLICATION_JSON)
													.accept(MediaType.APPLICATION_JSON)
													.content(json);		
		// Verificação
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
			.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(2)));
	}
	
	@Test
	@DisplayName("Deve obter uma lista das pessoas")
	public void findAllTest() throws Exception {
		// Cenário
		Long id = 2l;
		
		PessoaDTO pessoa = new PessoaDTO(id, "Gustavo Silva Cruz", LocalDate.of(1996, 10, 17));
		
		List<PessoaDTO> list = Arrays.asList(pessoa);
		
		PageRequest pageRequest = PageRequest.of(0, 24);
		
		Page<PessoaDTO> page = new PageImpl<PessoaDTO>(list, pageRequest, list.size());
		
		BDDMockito.given(pessoaService.findAll(0, 24, "nome", "ASC")).willReturn(page);
		
		String queryString = String.format("?page=0&linesPerPage=24&orderBy=%s&direction=%s", "nome", "ASC");
				
		// Execução
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
														.get(PESSOA_API.concat(queryString))
														.accept(MediaType.APPLICATION_JSON);
		
		// Verificação
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("content", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("totalElements").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("pageable.pageSize").value(24))
			.andExpect(MockMvcResultMatchers.jsonPath("pageable.pageNumber").value(0));
	}
	
	@Test
	@DisplayName("Deve obter a lista de endereços da pessoa")
	public void findEnderecoByPessoa() throws Exception {
		// Cenário
		Long id = 2l;
		
		EnderecoDTO enderecoDto = new EnderecoDTO(id, "Rua Belém", "88160-396", "646", "Biguaçu", false);
		
		List<EnderecoDTO> list = Arrays.asList(enderecoDto);
		
		BDDMockito.given(enderecoService.findByPessoa(id)).willReturn(list);
		
		// Execução
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
															.get(PESSOA_API.concat("/"+id+"/enderecos"))
															.accept(MediaType.APPLICATION_JSON);		
				
		// Verificação
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("[0].id").value(id))
			.andExpect(MockMvcResultMatchers.jsonPath("[0].logradouro").value("Rua Belém"))
			.andExpect(MockMvcResultMatchers.jsonPath("[0].cep").value("88160-396"))
			.andExpect(MockMvcResultMatchers.jsonPath("[0].numero").value("646"))
			.andExpect(MockMvcResultMatchers.jsonPath("[0].cidade").value("Biguaçu"))
			.andExpect(MockMvcResultMatchers.jsonPath("[0].principal").value(false));
	}

}
