package excecao;

import anotacao.ConstraintViolada;

@ConstraintViolada(nome="CONTA_AGENCIA_FK", 
                   msg="Esta ag�ncia possui contas. Remo��o inv�lida.")
public class AgenciaComContasException extends RuntimeException {
	private final static long serialVersionUID = 1;

	public AgenciaComContasException() {
		super();
	}

	public AgenciaComContasException(String msg) {
		super(msg);
	}
}
