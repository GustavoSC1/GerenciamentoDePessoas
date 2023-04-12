package com.gustavo.gerenciamentoDePessoas.dtos;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EnderecoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="O campo logradouro é obrigatório")
	private String logradouro;
	
	@NotEmpty(message="O campo cep é obrigatório")
	private String cep;
	
	@NotEmpty(message="O campo numero é obrigatório")
	private String numero;
	
	@NotEmpty(message="O campo cidade é obrigatório")
	private String cidade;
	
	@NotNull(message="O campo data de nascimento é obrigatório")
	private Boolean enderecoPrincipal;
		
	public EnderecoNewDTO() {
		
	}

	public EnderecoNewDTO(String logradouro, String cep, String numero, String cidade, Boolean enderecoPrincipal) {
		this.logradouro = logradouro;
		this.cep = cep;
		this.numero = numero;
		this.cidade = cidade;
		this.enderecoPrincipal = enderecoPrincipal;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getCep() {
		return cep;
	}

	public String getNumero() {
		return numero;
	}

	public String getCidade() {
		return cidade;
	}

	public Boolean getEnderecoPrincipal() {
		return enderecoPrincipal;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setEnderecoPrincipal(Boolean enderecoPrincipal) {
		this.enderecoPrincipal = enderecoPrincipal;
	}

}
