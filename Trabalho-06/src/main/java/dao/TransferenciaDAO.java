package dao;

import java.util.List;

import anotacao.RecuperaLista;
import modelo.Transferencia;
import excecao.ObjetoNaoEncontradoException;

public interface TransferenciaDAO extends DaoGenerico<Transferencia, Long> {
    /* ****** M�todos Gen�ricos ******* */
	
    Transferencia recuperaTransferencia(long numero) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Transferencia> recuperaListaDeTransferencias();

    @RecuperaLista
    List<Transferencia> recuperaListaDeTransferenciasDeUmDestino();
    
    /*
    List<Agencia> recuperaListaDeAgenciasEContas();

    Agencia recuperaPrimeiraAgencia() throws ObjetoNaoEncontradoException;


    Set<Agencia> recuperaConjuntoDeAgenciasEContas();


    /* ****** M�todos n�o Gen�ricos ******* */

    // Um m�todo definido aqui, que n�o seja anotado, dever� ser
    // implementado como final em ProdutoDAOImpl.
}
