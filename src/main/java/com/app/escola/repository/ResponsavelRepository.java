package com.app.escola.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.escola.model.Aluno;
import com.app.escola.model.Responsavel;

@Repository
@Transactional
public interface ResponsavelRepository extends CrudRepository<Responsavel,Long> {

	Responsavel findById(long id);
	
	Iterable<Responsavel> findByAluno(Aluno aluno);
	
}
