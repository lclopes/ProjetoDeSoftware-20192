package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")

public class Usuario {

	private Long id;
	private String conta;
	private String senha;

	private List<Perfil> perfis = new ArrayList<Perfil>();

	// Um usuário possui vários perfis

	// private List<> equipes = new ArrayList<Equipe>();

	public Usuario() {
	}

	public Usuario(String conta, String senha) {
		this.conta = conta;
		this.senha = senha;
	}

	// ********* Métodos do Tipo Get *********

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	@Column(name = "CONTA")
	public String getConta() {
		return conta;
	}

	@Column(name = "SENHA")
	public String getSenha() {
		return senha;
	}

	// ********* Métodos do Tipo Set *********

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	// ********* Métodos para Associações *********

	@OneToMany(mappedBy = "usuario")
	@OrderBy
	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

}