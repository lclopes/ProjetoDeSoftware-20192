package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import anotacao.Perfil;
import dao.ClienteDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.ClienteNaoEncontradoException;
import modelo.Cliente;

public class ClienteAppService {
	
	private ClienteDAO clienteDAO;
	
	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
	
	@Perfil(nomes={"admin", "user"})
	@Transactional
	public long inclui(Cliente cliente) {
		clienteDAO.inclui(cliente);
//		Produto outroProduto = new Produto();
//		outroProduto.setNome(umProduto.getNome());
//		outroProduto.setDescricao(umProduto.getDescricao());
//		outroProduto.setLanceMinimo(umProduto.getLanceMinimo());
//		outroProduto.setDataCadastro(umProduto.getDataCadastro());
//		Console.readLine("Digite algo: ");
//		return produtoDAO.inclui(outroProduto).getId();
		return cliente.getId();
	}

	@Perfil(nomes={"admin"})
	@Transactional
	public void altera(Cliente cliente) throws ClienteNaoEncontradoException {
		try {
			clienteDAO.getPorIdComLock(cliente.getId());
			clienteDAO.altera(cliente);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ClienteNaoEncontradoException("Cliente não encontrado");
		}
	}

	@Perfil(nomes={"admin"})
	@Transactional
	public void exclui(Cliente cl) throws ClienteNaoEncontradoException {
		try {
			Cliente cliente = clienteDAO.recuperaCliente(cl.getId());

//			if(produto.getLances().size() > 0)
//			{	throw new ProdutoNaoEncontradoException("Este produto possui lances e não pode ser removido");
//			}

			clienteDAO.exclui(cliente);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ClienteNaoEncontradoException("Cliente não encontrado");
		}
	}

	@Perfil(nomes={"admin"})
	public Cliente recuperaUmCliente(long numero) throws ClienteNaoEncontradoException {
		try {
			return clienteDAO.getPorId(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ClienteNaoEncontradoException("Cliente não encontrado");
		}
	}

	@Perfil(nomes={"admin"})
	public Cliente recuperaCliente(long numero) throws ClienteNaoEncontradoException {
		try {
			return clienteDAO.recuperaCliente(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ClienteNaoEncontradoException("Cliente não encontrado");
		}
	}

	@Perfil(nomes={"admin"})
	public List<Cliente> recuperaListaDeClientes() {
		return clienteDAO.recuperaListaDeClientes();
	}
}