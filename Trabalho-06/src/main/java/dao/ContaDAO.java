package dao;

import java.util.List;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import modelo.Conta;
import excecao.ObjetoNaoEncontradoException;

public interface ContaDAO extends DaoGenerico<Conta, Long> {
    /* ****** Métodos Genéricos ******* */
	
	@RecuperaObjeto
    Conta recuperaConta(long numero) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Conta> recuperaListaDeContas();
    
	Conta verificaCliente(long numero);

    /*
    List<Agencia> recuperaListaDeAgenciasEContas();

    Agencia recuperaPrimeiraAgencia() throws ObjetoNaoEncontradoException;


    Set<Agencia> recuperaConjuntoDeAgenciasEContas();*/


    /* ****** Métodos não Genéricos ******* */

    // Um método definido aqui, que não seja anotado, deverá ser
    // implementado como final em ProdutoDAOImpl.
}
