package planejarGastos.operacaoDeDados;

import java.io.Serializable;


//Classe que para o usuário é interpretada com o nome de Gasto, 
//porém é geralmente usada durante o código como os dados que o usuário quer 
//adicionar às suas categorias
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