package br.com.alura.forum.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.Topico;

public class TopicoResponse {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;

	public TopicoResponse(Long id, String titulo, String mensagem, LocalDateTime dataCriacao) {
		super();
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.dataCriacao = dataCriacao;
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	
	public static List<TopicoResponse> converter(List<Topico> topicos){
		return 
		 topicos.stream().map(topico -> new TopicoResponse(topico.getId(), topico.getTitulo(), topico.getMensagem(),topico.getDataCriacao()))
		 	.collect(Collectors.toList());
	}

}
