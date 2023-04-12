package com.gustavo.gerenciamentoDePessoas.dtos;

import java.io.Serializable;

import com.gustavo.gerenciamentoDePessoas.entities.Endereco;

public class EnderecoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String logradouro;
	private String cep;
	private String numero;
	private String cidade;
	private Boolean enderecoPrincipal;
		
	public EnderecoDTO() {
		
	}
	
	public EnderecoDTO(Endereco endereco) {
		this.id = endereco.getId();
		this.logradouro = endereco.getLogradouro();
		this.cep = endereco.getCep();
		this.numero = endereco.getNumero();
		this.cidade = endereco.getCidade();
		this.enderecoPrincipal = endereco.getEnderecoPrincipal();
	}

	public EnderecoDTO(Long id, String logradouro, String cep, String numero, String cidade, Boolean enderecoPrincipal) {
		this.id = id;
		this.logradouro = logradouro;
		this.cep = cep;
		this.numero = numero;
		this.cidade = cidade;
		this.enderecoPrincipal = enderecoPrincipal;
	}

	public Long getId() {
		return id;
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

	public void setId(Long id) {
		this.id = id;
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
