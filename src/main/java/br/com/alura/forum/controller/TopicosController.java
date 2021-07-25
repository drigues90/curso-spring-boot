package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.request.TopicoRequest;
import br.com.alura.forum.response.TopicoResponse;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public List<TopicoResponse> lista(String cursoNome){
		List<Topico> topicos = null;
		
		topicos = cursoNome != null  ? topicoRepository.findByCursoNome(cursoNome) : topicoRepository.findAll();
		return TopicoResponse.converter(topicos);
	}
	
	@PostMapping
	public ResponseEntity<TopicoResponse> cadastrar(@RequestBody  @Valid TopicoRequest request, UriComponentsBuilder builder) {
		Topico topico = request.conveter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = builder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoResponse(topico));
	}
}
