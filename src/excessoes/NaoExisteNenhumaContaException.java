package excessoes;

import javax.swing.JButton;
import javax.swing.JOptionPane;

//Utilizado caso n�o exista nenhuma conta ainda salva nos arquivos
public class NaoExisteNenhumaContaException extends Exception{

	public NaoExisteNenhumaContaException() {
		JOptionPane.showMessageDialog(null, "N�o h� contas criadas", "", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
