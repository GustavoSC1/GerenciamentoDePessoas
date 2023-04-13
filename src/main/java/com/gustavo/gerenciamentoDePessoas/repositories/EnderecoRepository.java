package com.gustavo.gerenciamentoDePessoas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gustavo.gerenciamentoDePessoas.entities.Endereco;
import com.gustavo.gerenciamentoDePessoas.entities.Pessoa;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
	List<Endereco> findByPessoa(Pessoa pessoa);
	
	Optional<Endereco> findByIdAndPessoa(Long id, Pessoa pessoa);
	
	@Transactional
    @Modifying()
    @Query("update Endereco obj set obj.enderecoPrincipal = :enderecoPrincipal where obj.id = :id")
	void updateEnderecoPrincipalById(@Param("enderecoPrincipal") Boolean enderecoPrincipal, @Param("id") Long id);
	
	@Transactional
    @Modifying
    @Query("update Endereco obj set obj.enderecoPrincipal = :enderecoPrincipal where obj.pessoa = :pessoa and obj.id != :id")
    void updateEnderecoPrincipalByPessoaExceptId(@Param("enderecoPrincipal") Boolean enderecoPrincipal, @Param("pessoa") Pessoa pessoa, @Param("id") Long id);
	
}
