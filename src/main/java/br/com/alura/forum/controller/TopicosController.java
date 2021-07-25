package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@Cacheable(value = "listarTopicos")
	public Page<TopicoResponse> lista(String cursoNome,@PageableDefault(sort = "id", direction = Direction.ASC, size = 3) Pageable pageable) {
		Page<Topico> topicos = null;

		topicos = cursoNome != null ? topicoRepository.findByCursoNome(cursoNome,pageable) : topicoRepository.findAll(pageable);
		return TopicoResponse.converter(topicos);
	}
	
	@GetMapping(path = "{id}")
	public ResponseEntity<TopicoResponse> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			return ResponseEntity.ok(new TopicoResponse(topico.get()));
		}else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@CacheEvict(value = "listarTopicos",allEntries = true)
	public ResponseEntity<TopicoResponse> cadastrar(@RequestBody @Valid TopicoRequest request,
			UriComponentsBuilder builder) {
		Topico topico = request.conveter(cursoRepository);
		topicoRepository.save(topico);

		URI uri = builder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoResponse(topico));
	}

	@Transactional
	@PutMapping(path = "{id}")
	@CacheEvict(value = "listarTopicos",allEntries = true)
	public ResponseEntity<TopicoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoRequest request) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			topico.get().atualizar(request);
			return ResponseEntity.ok(new TopicoResponse(topico.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping(path = "{id}")
	@CacheEvict(value = "listarTopicos",allEntries = true)
	public ResponseEntity<TopicoResponse> deletar(@PathVariable Long id) {
		try {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
