package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import dao.ContaDAO;
import excecao.ObjetoNaoEncontradoException;

@NamedQueries({ 
	@NamedQuery(name = "Transferencia.recuperaListaDeTransferencias", query = "select distinct t from Transferencia t join t.contaOrigem co join"
																		+ " t.contaDestino cd"),
	@NamedQuery(name = "Transferencia.recuperaTransferencia", query = "select t from Transferencia t where t.id = ?1")
})

@Entity
@Table(name = "transferencia")
public class Transferencia {
	//Variáveis de entidade
	private Long id; 
	private double valor;
	
	//Variáveis de relacionamento: uma agência possui muitas contas
	private Conta contaOrigem;
	private Conta contaDestino;
	private ContaDAO contaDAO;
	
	//Construtores
	public Transferencia() {
	}
	
	public Transferencia(double valor, Conta contaOrigem, Conta contaDestino) {
		this.valor = valor;
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
	}

	//Getters e setters
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "VALOR")
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	//Associações
	
	@ManyToOne
    @JoinColumn(name = "CONTAORIGEM")
	public Conta getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}
	
	public void setContaOrigemById(Long contaId) throws ObjetoNaoEncontradoException {
		this.contaOrigem = contaDAO.getPorId(contaId);
	}
	
	@ManyToOne
    @JoinColumn(name = "CONTADESTINO")
	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}
	
	public void setContaDestinoById(Long contaId) throws ObjetoNaoEncontradoException {
		this.contaDestino = contaDAO.getPorId(contaId);
	}
		
}