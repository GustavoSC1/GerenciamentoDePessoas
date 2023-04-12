package com.gustavo.gerenciamentoDePessoas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.gustavo.gerenciamentoDePessoas.dtos.PessoaDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.PessoaNewDTO;
import com.gustavo.gerenciamentoDePessoas.services.EnderecoService;
import com.gustavo.gerenciamentoDePessoas.services.PessoaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@PostMapping
	public ResponseEntity<PessoaDTO> save(@Valid @RequestBody PessoaNewDTO pessoaNewDto) {
		PessoaDTO pessoaDto = pessoaService.save(pessoaNewDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				 .buildAndExpand(pessoaDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(pessoaDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PessoaDTO> update(@PathVariable Long id, @Valid @RequestBody PessoaNewDTO pessoaNewDto) {
		PessoaDTO pessoaDto = pessoaService.update(id, pessoaNewDto);
		
		return ResponseEntity.ok().body(pessoaDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PessoaDTO> find(@PathVariable Long id) {
		PessoaDTO pessoaDto = pessoaService.find(id);
		
		return ResponseEntity.ok().body(pessoaDto);
	}
	
	@GetMapping
	public ResponseEntity<Page<PessoaDTO>> findAll(
												@RequestParam(value="page", defaultValue="0") Integer page,
												@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
												@RequestParam(value="orderBy", defaultValue="date") String orderBy,
												@RequestParam(value="direction", defaultValue="DESC") String direction) {
		
		Page<PessoaDTO> pessoaDto = pessoaService.findAll(page, linesPerPage, orderBy, direction);
		
		return ResponseEntity.ok().body(pessoaDto);
	}
	
	@PostMapping("/{idPessoa}/enderecos")
	public ResponseEntity<EnderecoDTO> saveEndereco(@PathVariable Long idPessoa, @RequestBody EnderecoNewDTO enderecoNewDto) {
		EnderecoDTO enderecoDto = enderecoService.save(idPessoa, enderecoNewDto);
		
		return ResponseEntity.ok().body(enderecoDto);
				
	}
	
	@GetMapping("/{idPessoa}/enderecos")
	// Resolvi utilizar List ao invés de Page porque geralmente uma pessoa não tem muitos endereços
	public ResponseEntity<List<EnderecoDTO>> findEnderecoByPessoa(@PathVariable Long idPessoa) {
		List<EnderecoDTO> list = enderecoService.findByPessoa(idPessoa);
		
		return ResponseEntity.ok().body(list);
	}

}
