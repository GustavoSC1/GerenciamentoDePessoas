package com.gustavo.gerenciamentoDePessoas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoNewDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoPrincipalUpdateDTO;
import com.gustavo.gerenciamentoDePessoas.entities.Endereco;
import com.gustavo.gerenciamentoDePessoas.entities.Pessoa;
import com.gustavo.gerenciamentoDePessoas.repositories.EnderecoRepository;
import com.gustavo.gerenciamentoDePessoas.services.exceptions.ObjectNotFoundException;

@Service
public class EnderecoService {
	
	private EnderecoRepository enderecoRepository;
	
	private PessoaService pessoaService;
		
	public EnderecoService(EnderecoRepository enderecoRepository, PessoaService pessoaService) {
		this.enderecoRepository = enderecoRepository;
		this.pessoaService = pessoaService;
	}

	public EnderecoDTO save(Long idPessoa, EnderecoNewDTO enderecoDto) {
		
		Pessoa pessoa = pessoaService.findById(idPessoa);
		
		Endereco endereco = new Endereco(null, enderecoDto.getLogradouro(), enderecoDto.getCep(), enderecoDto.getNumero(), 
				enderecoDto.getCidade(), enderecoDto.getEnderecoPrincipal(), pessoa);
		
		 endereco = enderecoRepository.save(endereco);
		 
		 if(endereco.getEnderecoPrincipal() == true) {
			 enderecoRepository.updateEnderecoPrincipalByPessoaExceptId(false, pessoa, endereco.getId());
		 }
		
		return new EnderecoDTO(endereco);
	}
	
	public List<EnderecoDTO> findByPessoa(Long idPessoa) {
		Pessoa pessoa = pessoaService.findById(idPessoa);
		
		return enderecoRepository.findByPessoa(pessoa).stream().map(obj -> new EnderecoDTO(obj)).collect(Collectors.toList());
	}
	
	public EnderecoDTO updateEnderecoPrincipalById(Long idPessoa, EnderecoPrincipalUpdateDTO enderecoPrincipalUpdateDTO) {
		Pessoa pessoa = pessoaService.findById(idPessoa);
		
		Optional<Endereco> optionalEndereco = enderecoRepository.findByIdAndPessoa(enderecoPrincipalUpdateDTO.getIdEndereco(), pessoa);
		
		Endereco endereco = optionalEndereco.orElseThrow(() -> new ObjectNotFoundException("A pessoa não possui o endereço informado! Id: " + enderecoPrincipalUpdateDTO.getIdEndereco() + ", Type: " + Endereco.class.getName()));
		
		endereco.setEnderecoPrincipal(true);
		
		updateEnderecoPrincipalByPessoaExceptId(false, pessoa, endereco.getId());
		
		endereco = enderecoRepository.save(endereco);
		
		return new EnderecoDTO(endereco);
	}
	
	public void updateEnderecoPrincipalByPessoaExceptId(Boolean enderecoPrincipal, Pessoa pessoa, Long id) {
		
		enderecoRepository.updateEnderecoPrincipalByPessoaExceptId(enderecoPrincipal, pessoa, id);
		
	}
	
}
