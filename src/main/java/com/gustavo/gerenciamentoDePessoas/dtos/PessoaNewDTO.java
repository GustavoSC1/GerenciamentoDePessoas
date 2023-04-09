package com.gustavo.gerenciamentoDePessoas.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class PessoaNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="O campo nome é obrigatório")
	@Length(min=8, max=120, message="O comprimento deve estar entre 8 e 120 caracteres")
	private String nome;
	
	@NotNull(message="O campo data de nascimento é obrigatório")
	@Past(message="Data inválida")
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
