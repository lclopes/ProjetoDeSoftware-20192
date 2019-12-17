package dao;

import java.util.List;
import java.util.Set;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import modelo.Agencia;
import modelo.Conta;
import excecao.ObjetoNaoEncontradoException;

public interface AgenciaDAO extends DaoGenerico<Agencia, Long> {
    /* ****** Métodos Genéricos ******* */
	
	@RecuperaObjeto
	Agencia recuperaAgencia(long numero) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Agencia> recuperaListaDeAgencias();

    @RecuperaLista
    List<Conta> recuperaAgenciaEContas();
    
    
    List<Agencia> recuperaListaDeAgenciasEContas();

    Agencia recuperaPrimeiraAgencia() throws ObjetoNaoEncontradoException;


    Set<Agencia> recuperaConjuntoDeAgenciasEContas();


    /* ****** Métodos não Genéricos ******* */

    // Um método definido aqui, que não seja anotado, deverá ser
    // implementado como final em ProdutoDAOImpl.
}
