package com.gustavo.gerenciamentoDePessoas.services;

import org.springframework.stereotype.Service;

import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.EnderecoNewDTO;
import com.gustavo.gerenciamentoDePessoas.entities.Endereco;
import com.gustavo.gerenciamentoDePessoas.entities.Pessoa;
import com.gustavo.gerenciamentoDePessoas.repositories.EnderecoRepository;

@Service
public class EnderecoService {
	
	private EnderecoRepository enderecoRepository;
	
	private PessoaService pessoaService;
		
	public EnderecoService(EnderecoRepository enderecoRepository, PessoaService pessoaService) {
		this.enderecoRepository = enderecoRepository;
		this.pessoaService = pessoaService;
	}

	public EnderecoDTO save(EnderecoNewDTO enderecoDto) {
		
		Pessoa pessoa = pessoaService.findById(enderecoDto.getId_pessoa());
		
		Endereco endereco = new Endereco(null, enderecoDto.getLogradouro(), enderecoDto.getCep(), enderecoDto.getNumero(), 
				enderecoDto.getCidade(), false, pessoa);
		
		 endereco = enderecoRepository.save(endereco);
		
		return new EnderecoDTO(endereco);
	}
	
}
