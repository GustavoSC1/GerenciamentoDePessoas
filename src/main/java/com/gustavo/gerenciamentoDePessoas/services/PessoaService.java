package com.gustavo.gerenciamentoDePessoas.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.gustavo.gerenciamentoDePessoas.services.exceptions.ObjectNotFoundException;
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
	
	public PessoaDTO find(Long id) {		
		Pessoa user = findById(id);
		
		return new PessoaDTO(user);
	}
	
	public PessoaDTO update(Long id, PessoaNewDTO pessoaDto) {
		Pessoa pessoa = findById(id);
		
		pessoa.setNome(pessoaDto.getNome());
		pessoa.setDataDeNascimento(pessoa.getDataDeNascimento());
		
		pessoa = pessoaRepository.save(pessoa);
		
		return new PessoaDTO(pessoa);
	}
		
	public Pessoa findById(Long id) {
		
		Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
		Pessoa pessoa = pessoaOptional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Type: " + Pessoa.class.getName()));
	
		return pessoa;
	}

}
