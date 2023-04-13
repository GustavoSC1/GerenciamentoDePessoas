package com.gustavo.gerenciamentoDePessoas.dtos;

import java.io.Serializable;

public class EnderecoPrincipalUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idEndereco;
	
	public EnderecoPrincipalUpdateDTO() {
		
	}

	public EnderecoPrincipalUpdateDTO(Long idEndereco) {
		this.idEndereco = idEndereco;
	}

	public Long getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}

}
