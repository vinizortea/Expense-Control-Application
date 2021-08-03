package armazenamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import operationGUI.JanelaCriarPlanejamento;
import planejarGastos.Planejamento;


//Classe que cont�m os atributos para uma conta, seus planejamentos, nome e senha
//assim como o m�todo que permite criar uma nova inst�ncia de Planejamento em planejametos
public class Conta implements Serializable {

	private ArrayList<Planejamento> planejamentos = new ArrayList<Planejamento>();
	private String nome;
	private String senha;
	
	public Conta(String nome, String senha) {
		this.nome = nome;
		this.senha = senha; 
	}
	
	public ArrayList<Planejamento> getPlanejamentos(){
		return this.planejamentos;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	protected String getSenha() {
	    return this.senha;	
		
	}
	
	public void criarPlanejamento(DefaultComboBoxModel planejamentosNaComboBox) {
		JanelaCriarPlanejamento frame = new JanelaCriarPlanejamento(this, planejamentosNaComboBox);
	}
	
}
