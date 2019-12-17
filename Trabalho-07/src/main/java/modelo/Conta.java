package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries({ 
	@NamedQuery(name = "Conta.recuperaListaDeContas", query = "select distinct c from Conta c join c.cliente cl join"
																		+ " c.agencia ag"),
	@NamedQuery(name = "Conta.recuperaConta", query = "select c from Conta c where c.id = ?1"),
	@NamedQuery(name = "Conta.verificaCliente", query = "select c from Conta c join c.cliente cl where c.id = cl.id")
})

@Entity
@Table(name = "conta")
public class Conta {
	//Variáveis de entidade
	private Long id; 
	private int numero;
	private double saldo;
		
	//Variáveis de relacionamento: uma conta se refere a uma agência e a um único cliente
	private Agencia agencia;
	private Cliente cliente;
	
	//Construtores
	public Conta() {
		
	}
	
	public Conta(int numero, double saldo) {
		this.numero = numero;
		this.saldo = saldo;
	}

	public Conta(int numero, double saldo, Agencia agencia, Cliente cliente) {
		this.numero = numero;
		this.saldo = saldo;
		this.agencia = agencia;
		this.cliente = cliente;
	}
	
	//Getters e setters
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NUMERO")
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	@Column(name = "SALDO")
	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	@ManyToOne
	@JoinColumn(name = "AGENCIA_ID")
	public Agencia getAgencia() {
		return agencia;
	}
	
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	
	@OneToOne
	@JoinColumn(name = "CLIENTE_ID")
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}