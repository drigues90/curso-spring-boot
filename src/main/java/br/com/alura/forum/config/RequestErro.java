package br.com.alura.forum.config;

public class RequestErro {

	private String campo;
	private String messagem;

	public RequestErro(String campo, String messagem) {
		this.campo = campo;
		this.messagem = messagem;
	}

	public String getCampo() {
		return campo;
	}

	public String getMessagem() {
		return messagem;
	}

}
