package com.app.escola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.escola.model.Aluno;
import com.app.escola.model.Responsavel;
import com.app.escola.repository.AlunoRepository;
import com.app.escola.repository.ResponsavelRepository;


@Controller
public class AlunoController {
	
	@Autowired
	AlunoRepository ar;
	@Autowired
	ResponsavelRepository rr;
	

	@GetMapping("/cadastrar")
	public String formulario() {
		return "aluno/cadastro";
	}
	
	@PostMapping("/cadastrar")
	public String salvar(Aluno aluno) {
		ar.save(aluno);
		return "redirect:/listar";
	}
	
	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mav = new ModelAndView("aluno/lista");
		Iterable<Aluno> alunos = ar.findAll();
		mav.addObject("alunos", alunos);
		return mav;
	}
	
	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable(value="id") long id) {
		Aluno aluno = ar.findAlunoById(id);
		ar.delete(aluno);
		return "redirect:/listar";
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable(value="id") long id) {
		ModelAndView mav = new ModelAndView("aluno/editar");
		Aluno aluno = ar.findAlunoById(id);
		mav.addObject("aluno", aluno);
		return mav;
	}
	
	@PostMapping("/editar/{id}")
	public String editar(Aluno aluno) {
		ar.save(aluno);
		return "redirect:/listar";
	}
	
	@GetMapping("/detalhe/{id}")
	public ModelAndView detalhes(@PathVariable(value="id") long id) {
		ModelAndView mav = new ModelAndView("aluno/detalhe");
		Aluno aluno = ar.findAlunoById(id);
		mav.addObject("aluno" , aluno);
		
		Iterable<Responsavel> responsaveis = rr.findByAluno(aluno);
		mav.addObject("responsaveis", responsaveis);
		
		return mav;	
	}
	
	@PostMapping("/detalhe/{id}")
	public String salvarResponsavel(@PathVariable(value="id") long id, Responsavel responsavel) {
		Aluno aluno = ar.findAlunoById(id);
		responsavel.setAluno(aluno);
		rr.save(responsavel);
		return "redirect:/detalhe/{id}";	
	}
	
	@GetMapping("/deletarResponsavel/{id}")
	public String deletarResponsavel(@PathVariable(value="id") long id) {
		Responsavel responsavel = rr.findById(id);
		rr.delete(responsavel);
		
		Aluno aluno = responsavel.getAluno();
		long codLong = aluno.getId();
		String cod = "" + codLong;
		return "redirect:/detalhe/" + cod;	
	}
	
	@PostMapping(value="**/pesquisar")
	public ModelAndView pesquisar(@RequestParam("pesquisanome") String pesquisanome) {
		ModelAndView mav = new ModelAndView("aluno/lista");
		mav.addObject("alunos", ar.findAlunoByNome(pesquisanome));
		mav.addObject("alunosobj", new Aluno());
		return mav;
	}
	
	
}
