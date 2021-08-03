package planejarGastos.operacaoDeDados;

import java.util.Iterator;

import planejarGastos.Categoria;
import planejarGastos.Planejamento;

import java.util.ArrayList;


//Assim como OperadorDeArquivos, apenas seus métodos interessam, por isso é abstrata,
//e é aqui que vai todos os métodos responsáveis por gerar e interpretar os dados, como somas, porcentagens(ainda não implementadas)
//e outras funcionalidades que seriam interessantes implementar
public abstract class Operacoes {

	//Ainda não utilizada em nenhuma parte do código
	public static double somarTotalGastosMes(Planejamento planejamento, int mes, int ano) {
		double totalGastosMes = 0;
		Categoria c;
		Dados d;
		ArrayList<Categoria> categoria = planejamento.getCategorias();
		ArrayList<Dados> dados;
		Iterator<Categoria> iteratorCategoria = categoria.iterator();
		Iterator<Dados> iteratorDados;

		
		while(iteratorCategoria.hasNext()) {
			c = iteratorCategoria.next();
			dados = c.getDados();
			iteratorDados = dados.iterator();
			while(iteratorDados.hasNext()) {
				d = iteratorDados.next();
				if (d.mes == mes && d.ano == ano) {
					totalGastosMes += d.gastoTotal;
				}

			}
		}
		return totalGastosMes;
	}
	
	//utilizada na tabela quando se abre um novo Planejamento
	public static double totalCategoria(Categoria categoria) {
		double totalCategoria = 0;
		
		for(Dados d : categoria.getDados()) {
			totalCategoria += d.getGastoTotal();
		}
		return totalCategoria;
	}

}
