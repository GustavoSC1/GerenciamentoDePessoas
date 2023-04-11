package com.gustavo.gerenciamentoDePessoas.dtos;

import java.io.Serializable;

public class EnderecoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String logradouro;
	private String cep;
	private String numero;
	private String cidade;
	private Boolean principal;
		
	public EnderecoNewDTO() {
		
	}

	public EnderecoNewDTO(String logradouro, String cep, String numero, String cidade, Boolean principal) {
		this.logradouro = logradouro;
		this.cep = cep;
		this.numero = numero;
		this.cidade = cidade;
		this.principal = principal;
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

	public Boolean getPrincipal() {
		return principal;
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

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

}
