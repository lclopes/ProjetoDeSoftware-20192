package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class ContaNaoEncontradaException extends Exception {
    private final static long serialVersionUID = 1;

    public ContaNaoEncontradaException(String msg) {
	super(msg);
    }
}