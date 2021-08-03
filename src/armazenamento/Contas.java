package armazenamento;

import java.io.Serializable;
import java.util.HashMap;

//Classe que cont�m apenas o HashMap que � usado para armazenar as informa��es de login,
//usando o nome como Key e as passwords como Values
public class Contas implements Serializable {

	private HashMap<String,String> contas = new HashMap<>();
	
	protected HashMap<String,String> getContas(){
		return this.contas;
	}
}
