package dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import anotacao.RecuperaConjunto;
import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import modelo.Agencia;
import modelo.Conta;
import excecao.ObjetoNaoEncontradoException;

@Repository
public interface AgenciaDAO extends DaoGenerico<Agencia, Long> {
    /* ****** M�todos Gen�ricos ******* */
	
	@RecuperaObjeto
	Agencia recuperaAgencia(long numero) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Agencia> recuperaListaDeAgencias();

    @RecuperaLista
    List<Conta> recuperaAgenciaEContas();
    
    @RecuperaLista
    List<Agencia> recuperaListaDeAgenciasEContas();

    @RecuperaObjeto
    Agencia recuperaPrimeiraAgencia() throws ObjetoNaoEncontradoException;

    @RecuperaConjunto
    Set<Agencia> recuperaConjuntoDeAgenciasEContas();


    /* ****** M�todos n�o Gen�ricos ******* */

    // Um m�todo definido aqui, que n�o seja anotado, dever� ser
    // implementado como final em ProdutoDAOImpl.
}
