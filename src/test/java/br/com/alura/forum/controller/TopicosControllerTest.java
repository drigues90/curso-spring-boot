package br.com.alura.forum.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.forum.request.TopicoRequest;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TopicosControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	URI uri;
	
	@Before
	public void before() throws URISyntaxException {
		uri = new URI("/topicos");
	}
	
	@Test
	public void deveriaRetornar200AoAcessarAcessarTopicos() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(status().isOk());
	}
	
	@Test
	public void deveRetornar200AoBuscarCursoExistente() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uri).param("cursoNome", "Spring Boot"))
		.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());	
	}
	
	@Test
	public void deveRetornar201AoCadastrarTopicoValido() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(new TopicoRequest("Testando","mensagem teste","Spring Boot")))
				).andExpect(status().isCreated());
	}

}
