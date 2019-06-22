package com.app.escola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.escola.model.Aluno;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long> {

	Aluno findAlunoById(long id);
	
	
	@Query("select a from Aluno a where a.nomeCompleto like %?1%")
	List<Aluno> findAlunoByNome(String nome);
	
	
}
