package dao;

import java.util.List;
import java.util.Set;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import modelo.Cliente;
import excecao.ObjetoNaoEncontradoException;

public interface ClienteDAO extends DaoGenerico<Cliente, Long> {
    /* ****** M�todos Gen�ricos ******* */
	
	@RecuperaObjeto
    Cliente recuperaCliente(long numero) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Cliente> recuperaListaDeClientes();

    List<Cliente> recuperaListaDeClientesEContas();

    Cliente recuperaPrimeiroCliente() throws ObjetoNaoEncontradoException;


    Set<Cliente> recuperaConjuntoDeClientesEContas();


    /* ****** M�todos n�o Gen�ricos ******* */

    // Um m�todo definido aqui, que n�o seja anotado, dever� ser
    // implementado como final em ProdutoDAOImpl.
}
