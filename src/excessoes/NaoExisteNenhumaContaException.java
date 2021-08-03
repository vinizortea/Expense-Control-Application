package excessoes;

import javax.swing.JButton;
import javax.swing.JOptionPane;

//Utilizado caso não exista nenhuma conta ainda salva nos arquivos
public class NaoExisteNenhumaContaException extends Exception{

	public NaoExisteNenhumaContaException() {
		JOptionPane.showMessageDialog(null, "Não há contas criadas", "", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
