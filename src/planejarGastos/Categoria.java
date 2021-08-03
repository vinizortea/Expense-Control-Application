package planejarGastos;
import planejarGastos.operacaoDeDados.*;

import java.awt.EventQueue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;

import armazenamento.Conta;
import operationGUI.JanelaCriarDado;

//Classe que abstrai uma Categoria, como por exemplo, "mercado", "farmacia",
//sendo que cada Categoria contém seus próprios Dados(ou Gastos para o usuário)
public class Categoria implements Serializable {
   
   private String nome;   
   private ArrayList<Dados> dados = new ArrayList<Dados>();
   
   public Categoria(String nome) {
	   this.nome = nome;
   }
	
   public void criarDado(Conta conta) {
		JanelaCriarDado frame = new JanelaCriarDado(this, conta);
   }	
   
   public ArrayList<Dados> getDados(){
	   return this.dados;
   }
   
   public String getNome() {
	   return nome;
   }
}