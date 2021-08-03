package planejarGastos;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//Classe que abstrai um Planejamento para o usu�rio, por exemplo, "f�rias", "Ano 2021" e afins,
//sendo que cada planejamento cont�m suas pr�prias categorias
public class Planejamento implements Serializable {
   
	private String nome;
	private String descricao = null;
	private ArrayList<Categoria> categorias = new ArrayList<Categoria>();

    public Planejamento(String nome, String descricao) {
    	this.nome = nome;
    	this.descricao = descricao;
    }
    
    public void getDescricao() {
    	System.out.println("Insira a descri��o do seu planejamento:");
    	Scanner scanner = new Scanner(System.in);
        this.descricao = scanner.next();
        scanner.close();
    
    }
    
    public void criarCategoria(String nome) {
    	Categoria categoria = new Categoria(nome);
    	categorias.add(categoria);
    }
    
    public ArrayList<Categoria> getCategorias(){
		return categorias;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	
}
