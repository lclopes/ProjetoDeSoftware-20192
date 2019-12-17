package excecao;

import anotacao.ConstraintViolada;

@ConstraintViolada(nome="CONTA_AGENCIA_FK", 
                   msg="Esta agência possui contas. Remoção inválida.")
public class AgenciaComContasException extends RuntimeException {
	private final static long serialVersionUID = 1;

	public AgenciaComContasException() {
		super();
	}

	public AgenciaComContasException(String msg) {
		super(msg);
	}
}
