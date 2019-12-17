package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class AgenciaNaoEncontradaException extends Exception {
    private final static long serialVersionUID = 1;

    public AgenciaNaoEncontradaException(String msg) {
	super(msg);
    }
}