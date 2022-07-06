package br.com.snba.exceptions;

public class ErroInternoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ErroInternoException(String mensagem) {
		super(mensagem);
	}
	
	public ErroInternoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
