package excessoes;

import javax.swing.JOptionPane;

//Caso o usuário tente criar uma conta com um nome que já existe, o que não é permitido
//esta exception é lançada
public class UsuarioExistenteException extends Exception {

	public UsuarioExistenteException() {
	}
	
}
