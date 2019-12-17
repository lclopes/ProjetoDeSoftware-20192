package dao;

import java.util.List;
import java.util.Set;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import modelo.Cliente;
import excecao.ObjetoNaoEncontradoException;

public interface ClienteDAO extends DaoGenerico<Cliente, Long> {
    /* ****** Métodos Genéricos ******* */
	
	@RecuperaObjeto
    Cliente recuperaCliente(long numero) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Cliente> recuperaListaDeClientes();

    List<Cliente> recuperaListaDeClientesEContas();

    Cliente recuperaPrimeiroCliente() throws ObjetoNaoEncontradoException;


    Set<Cliente> recuperaConjuntoDeClientesEContas();


    /* ****** Métodos não Genéricos ******* */

    // Um método definido aqui, que não seja anotado, deverá ser
    // implementado como final em ProdutoDAOImpl.
}
