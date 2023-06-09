package com.gustavo.gerenciamentoDePessoas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoNewDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoPrincipalUpdateDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.PessoaDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.PessoaNewDTO;
import com.gustavo.gerenciamentoDePessoas.services.EnderecoService;
import com.gustavo.gerenciamentoDePessoas.services.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@PostMapping
	@Operation(description = "Criar uma pessoa", responses = {
			@ApiResponse(responseCode = "201", description = "Pessoa salva com sucesso"),
			@ApiResponse(responseCode = "422", description = "Erro de validação dos dados")
	})
	public ResponseEntity<Void> save(@Valid @RequestBody PessoaNewDTO pessoaNewDto) {
		PessoaDTO pessoaDto = pessoaService.save(pessoaNewDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				 .buildAndExpand(pessoaDto.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	@Operation(description = "Editar uma pessoa", responses = {
			@ApiResponse(responseCode = "200", description = "Pessoa editada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Não foi possível encontrar a pessoa solicitada"),
			@ApiResponse(responseCode = "422", description = "Erro de validação ddos dados")
	})
	public ResponseEntity<PessoaDTO> update(@PathVariable Long id, @Valid @RequestBody PessoaNewDTO pessoaNewDto) {
		PessoaDTO pessoaDto = pessoaService.update(id, pessoaNewDto);
		
		return ResponseEntity.ok().body(pessoaDto);
	}
	
	@GetMapping("/{id}")
	@Operation(description = "Consultar uma pessoa", responses = {
			@ApiResponse(responseCode = "200", description = "Pessoa obtida com sucesso"),
			@ApiResponse(responseCode = "404", description = "Não foi possível encontrar a pessoa solicitada")
	})
	public ResponseEntity<PessoaDTO> find(@PathVariable Long id) {
		PessoaDTO pessoaDto = pessoaService.find(id);
		
		return ResponseEntity.ok().body(pessoaDto);
	}
	
	@GetMapping
	@Operation(description = "Listar as pessoas", responses = {
			@ApiResponse(responseCode = "200", description = "Pessoas encontradas com sucesso")
	})
	public ResponseEntity<Page<PessoaDTO>> findAll(
												@RequestParam(value="page", defaultValue="0") Integer page,
												@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
												@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
												@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		Page<PessoaDTO> pessoaDto = pessoaService.findAll(page, linesPerPage, orderBy, direction);
		
		return ResponseEntity.ok().body(pessoaDto);
	}
	
	@PostMapping("/{idPessoa}/enderecos")
	@Operation(description = "Criar endereço para pessoa", responses = {
			@ApiResponse(responseCode = "200", description = "Endereco salvo com sucesso"),
			@ApiResponse(responseCode = "404", description = "Não foi possível encontrar a pessoa solicitada"),
			@ApiResponse(responseCode = "422", description = "Erro de validação de dados")
	})
	public ResponseEntity<EnderecoDTO> saveEndereco(@PathVariable Long idPessoa, @Valid @RequestBody EnderecoNewDTO enderecoNewDto) {
		EnderecoDTO enderecoDto = enderecoService.save(idPessoa, enderecoNewDto);
		
		return ResponseEntity.ok().body(enderecoDto);				
	}
	
	@GetMapping("/{idPessoa}/enderecos")
	@Operation(description = "Listar endereços da pessoa", responses = {
			@ApiResponse(responseCode = "200", description = "Endereços encontrados com sucesso"),			
			@ApiResponse(responseCode = "404", description = "Não foi possível encontrar a pessoa solicitada")
	})
	// Resolvi utilizar List ao invés de Page porque geralmente uma pessoa não tem muitos endereços
	public ResponseEntity<List<EnderecoDTO>> findEnderecoByPessoa(@PathVariable Long idPessoa) {
		List<EnderecoDTO> list = enderecoService.findByPessoa(idPessoa);
		
		return ResponseEntity.ok().body(list);
	}
	
	@PatchMapping("/{idPessoa}/enderecos")
	@Operation(description = "Informar qual endereço é o principal da pessoa", responses = {
			@ApiResponse(responseCode = "200", description = "Endereço principal alterado com sucesso"),			
			@ApiResponse(responseCode = "404", description = "Não foi possível encontrar a pessoa ou o endereço solicitado"),
	})
	public ResponseEntity<EnderecoDTO> updateEnderecoPrincipalById(@PathVariable Long idPessoa, @Valid @RequestBody EnderecoPrincipalUpdateDTO enderecoPrincipalUpdateDTO) {
		EnderecoDTO enderecoDto = enderecoService.updateEnderecoPrincipalById(idPessoa, enderecoPrincipalUpdateDTO);
		
		return ResponseEntity.ok().body(enderecoDto);				
	}

}
