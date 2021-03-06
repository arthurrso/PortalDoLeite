package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity(name = "Usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;
	private String email;
	private String senha;
	
	@OneToMany
	private List<Disciplina> disciplinasCadastradas;
	
	public Usuario(){
	}
	public Usuario (String nome, String email, String senha){
		
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		disciplinasCadastradas = new ArrayList<Disciplina>();
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setDisciplinasCadastradas(List<Disciplina> disciplinasCadastradas) {
		this.disciplinasCadastradas = disciplinasCadastradas;
	}
	public List<Disciplina> getDisciplinasCadastradas() {
		return disciplinasCadastradas;
	}
	
	public void cadastraDisciplina(Disciplina disciplina){
		disciplinasCadastradas.add(disciplina);
	}
}
