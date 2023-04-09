package com.gustavo.gerenciamentoDePessoas.dtos;

import java.io.Serializable;
import java.time.LocalDate;

public class PessoaNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private LocalDate dataDeNascimento;
		
	public PessoaNewDTO() {
		
	}

	public PessoaNewDTO(String nome, LocalDate dataDeNascimento) {
		this.nome = nome;
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getNome() {
		return nome;
	}
	
	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setDataDeNascimento(LocalDate dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
	
}
