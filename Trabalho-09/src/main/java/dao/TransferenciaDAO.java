package dao;

import java.util.List;

import anotacao.RecuperaLista;
import modelo.Transferencia;
import excecao.ObjetoNaoEncontradoException;

public interface TransferenciaDAO extends DaoGenerico<Transferencia, Long> {
    /* ****** Métodos Genéricos ******* */
	
    Transferencia recuperaTransferencia(long numero) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Transferencia> recuperaListaDeTransferencias();

    @RecuperaLista
    List<Transferencia> recuperaListaDeTransferenciasDeUmDestino();
    
    /*
    List<Agencia> recuperaListaDeAgenciasEContas();

    Agencia recuperaPrimeiraAgencia() throws ObjetoNaoEncontradoException;


    Set<Agencia> recuperaConjuntoDeAgenciasEContas();


    /* ****** Métodos não Genéricos ******* */

    // Um método definido aqui, que não seja anotado, deverá ser
    // implementado como final em ProdutoDAOImpl.
}
