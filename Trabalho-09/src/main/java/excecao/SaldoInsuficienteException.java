package excecao;

public class SaldoInsuficienteException extends Exception {
    private final static long serialVersionUID = 1;

    public SaldoInsuficienteException(String msg) {
	super(msg);
    }
}