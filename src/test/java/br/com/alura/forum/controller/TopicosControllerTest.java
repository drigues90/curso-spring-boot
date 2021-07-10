package br.com.alura.forum.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TopicosControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void deveriaRetornar200AoAcessarAcessarTopicos() throws Exception  {
		URI uri = new URI("/topicos");
		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(status().isOk());
	}

}
