package excecao;

import anotacao.ConstraintViolada;

@ConstraintViolada(nome="NUMERO_UNIQUE", 
                   msg="N�mero de ag�ncia j� existe! Tente novamente")
public class AgenciaComNumeroRepetidoException extends RuntimeException {
	private final static long serialVersionUID = 1;

	public AgenciaComNumeroRepetidoException() {
		super();
	}

	public AgenciaComNumeroRepetidoException(String msg) {
		super(msg);
	}
}
