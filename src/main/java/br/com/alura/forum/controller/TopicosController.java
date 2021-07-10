package br.com.alura.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.response.TopicoResponse;

@RestController
public class TopicosController {

	@RequestMapping("/topicos")
	public List<TopicoResponse> lista(){
		Topico topico = new Topico("Duvida", "duvida com spring", new Curso("Spring", "Programação"));
		
		return TopicoResponse.converter(Arrays.asList(topico,topico));
	}
}
