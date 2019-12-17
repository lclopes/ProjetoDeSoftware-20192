package modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({ 
	@NamedQuery(name = "Cliente.recuperaListaDeClientes", query = "select c from Cliente c order by c.id"),
	@NamedQuery(name = "Cliente.recuperaCliente", query = "select c from Cliente c where c.id = ?1")
})

@Entity
@Table(name = "cliente")
public class Cliente {
	// Variáveis de entidade
	private Long id;
	private String nome;
	private String endereco;
	private Date dataNasc;
	private String cpf;
	private String rg;
	private Date dataCadastro;

	// Variáveis de relacionamento: uma agência possui muitas contas

	// Construtores
	public Cliente() {

	}

	public Cliente(String nome, String endereco, Date dataNasc, String cpf, String rg, Date dataCadastro) {
		this.nome = nome;
		this.endereco = endereco;
		this.dataNasc = dataNasc;
		this.cpf = cpf;
		this.rg = rg;
		this.dataCadastro = dataCadastro;
	}

	// Getters e setters
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

	@Column(name = "NOME")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "ENDERECO")
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Column(name = "DATANASC")
	@Temporal(TemporalType.DATE)
	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	@Column(name = "CPF")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "RG")
	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Column(name = "DATACADASTRO")
	@Temporal(TemporalType.DATE)
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/*
	 * //Associações
	 * 
	 * @OneToMany(mappedBy="agencia")
	 * 
	 * @OrderBy
	 * 
	 * public List<Conta> getContas() { return contas; }
	 * 
	 * public void setLances(List<Conta> contas) { this.contas = contas; }
	 */
}