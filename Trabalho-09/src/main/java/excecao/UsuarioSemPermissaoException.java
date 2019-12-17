package excecao;

public class UsuarioSemPermissaoException extends Exception {
    private final static long serialVersionUID = 1;

    public UsuarioSemPermissaoException() {
	super();
    }

    public UsuarioSemPermissaoException(String msg) {
	super(msg);
    }
}