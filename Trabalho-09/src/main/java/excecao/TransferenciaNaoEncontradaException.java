package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class TransferenciaNaoEncontradaException extends Exception {
    private final static long serialVersionUID = 1;

    public TransferenciaNaoEncontradaException(String msg) {
	super(msg);
    }
}