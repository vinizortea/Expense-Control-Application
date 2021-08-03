package planejarGastos.operacaoDeDados;

import java.io.Serializable;


//Classe que para o usu�rio � interpretada com o nome de Gasto, 
//por�m � geralmente usada durante o c�digo como os dados que o usu�rio quer 
//adicionar �s suas categorias
public class Dados implements Serializable{

	private String local;
	protected int dia, mes, ano;
	protected double gastoTotal;
	   
	   public Dados(String local, int dia, int mes, int ano, double gastoTotal) {
		   this.local = local;
		   this.dia = dia; this.mes = mes; this.ano = ano;
		   this.gastoTotal = gastoTotal;
	   }
	   
	   public String getLocal() {
		   return this.local;
	   }
	   
	   public double getGastoTotal() {
		   return this.gastoTotal;
	   }
	
}