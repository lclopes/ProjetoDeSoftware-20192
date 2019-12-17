package dao;

import java.util.List;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import modelo.Conta;
import excecao.ObjetoNaoEncontradoException;

public interface ContaDAO extends DaoGenerico<Conta, Long> {
    /* ****** M�todos Gen�ricos ******* */
	
	@RecuperaObjeto
    Conta recuperaConta(long numero) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Conta> recuperaListaDeContas();
    
	Conta verificaCliente(long numero);

    /*
    List<Agencia> recuperaListaDeAgenciasEContas();

    Agencia recuperaPrimeiraAgencia() throws ObjetoNaoEncontradoException;


    Set<Agencia> recuperaConjuntoDeAgenciasEContas();*/


    /* ****** M�todos n�o Gen�ricos ******* */

    // Um m�todo definido aqui, que n�o seja anotado, dever� ser
    // implementado como final em ProdutoDAOImpl.
}
