package excessoes;

import javax.swing.JOptionPane;

//Caso o usu�rio tente criar uma conta com um nome que j� existe, o que n�o � permitido
//esta exception � lan�ada
public class UsuarioExistenteException extends Exception {

	public UsuarioExistenteException() {
	}
	
}
