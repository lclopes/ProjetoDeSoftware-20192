package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class NumeroDeAgenciaInvalidoException extends Exception {
    private final static long serialVersionUID = 1;

    public NumeroDeAgenciaInvalidoException(String msg) {
	super(msg);
    }
}