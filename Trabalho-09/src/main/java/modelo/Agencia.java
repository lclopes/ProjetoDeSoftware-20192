package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@NamedQueries({ 
	@NamedQuery(name = "Agencia.recuperaListaDeAgencias", query = "select a from Agencia a order by a.id"),
	@NamedQuery(name = "Agencia.recuperaAgencia", query = "select a from Agencia a where a.id = ?1"),
	@NamedQuery(name = "Agencia.recuperaAgenciaEContas", query = "select a from Agencia a left outer join fetch a.contas where a.id = ?1")
})

@Entity
@Table(name = "agencia")
public class Agencia {
	//Variáveis de entidade
	private Long id; 
	private String nome;
	private int numero;
	private String endereco;
		
	//Uma agência possui n contas
	private List<Conta> contas = new ArrayList<Conta>();
	
	//Construtores
	public Agencia() {
		
	}
	
	public Agencia(String nome, int numero, String endereco) {
		this.nome = nome;
		this.numero = numero;
		this.endereco = endereco;
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

	@Column(name = "NOME")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "NUMERO")
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Column(name = "ENDERECO")
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	// Associações
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "agencia", cascade = CascadeType.ALL)
	@OrderBy
	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}
	
}