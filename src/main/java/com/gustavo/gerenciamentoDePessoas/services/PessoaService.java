package com.gustavo.gerenciamentoDePessoas.services;

import org.springframework.stereotype.Service;

import com.gustavo.gerenciamentoDePessoas.dtos.PessoaDTO;
import com.gustavo.gerenciamentoDePessoas.dtos.PessoaNewDTO;
import com.gustavo.gerenciamentoDePessoas.entities.Pessoa;
import com.gustavo.gerenciamentoDePessoas.repositories.PessoaRepository;

@Service
public class PessoaService {
	
	private PessoaRepository pessoaRepository;

	public PessoaService(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}
	
	public PessoaDTO save(PessoaNewDTO pessoaDto) {
		Pessoa pessoa = new Pessoa(null, pessoaDto.getNome(), pessoaDto.getDataDeNascimento());
		pessoa = pessoaRepository.save(pessoa);
		return new PessoaDTO(pessoa);		
	}

}
