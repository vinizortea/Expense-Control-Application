package operationGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import armazenamento.Conta;
import armazenamento.Contas;
import armazenamento.OperadorDeArquivo;
import excessoes.UsuarioExistenteException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;


//Classe responsável pela criação de uma nova conta
public class JanelaNovaConta extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNovoUsuario;
	private JTextField textFieldNovaSenha;
	private JButton botaoCriarConta;
	private JButton botaoCancelarCriarConta;


	public JanelaNovaConta() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 150);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelNovoUsuario = new JLabel("Usuario:");
		labelNovoUsuario.setForeground(Color.RED);
		labelNovoUsuario.setBounds(48, 11, 73, 14);
		contentPane.add(labelNovoUsuario);
		
		textFieldNovoUsuario = new JTextField(10);
		textFieldNovoUsuario.setBounds(131, 8, 96, 20);
		contentPane.add(textFieldNovoUsuario);
		
		JLabel labelNovaSenha = new JLabel("Senha:");
		labelNovaSenha.setForeground(Color.RED);
		labelNovaSenha.setBounds(48, 44, 48, 14);
		contentPane.add(labelNovaSenha);
		
		textFieldNovaSenha = new JTextField(10);
		textFieldNovaSenha.setBounds(131, 41, 96, 20);
		contentPane.add(textFieldNovaSenha);
		
		botaoCriarConta = new JButton("Criar Conta");
		botaoCriarConta.setBounds(29, 77, 107, 23);
		
		//Botao que confirma a criação da nova conta nos arquivos utilizados pela aplicação
		botaoCriarConta.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				try {
				Conta conta = new Conta(textFieldNovoUsuario.getText(), textFieldNovaSenha.getText());
				OperadorDeArquivo.salvarInfoLogin(conta);
				OperadorDeArquivo.salvarConta(conta);
				fecharJanela();
				}catch(UsuarioExistenteException uee) {
					JOptionPane.showMessageDialog(contentPane, "Nome de usuário já existente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		contentPane.add(botaoCriarConta);
		
		botaoCancelarCriarConta = new JButton("Cancelar");
		botaoCancelarCriarConta.setBounds(162, 77, 89, 23);
		botaoCancelarCriarConta.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				fecharJanela();
			}
		});
		contentPane.add(botaoCancelarCriarConta);
		
		setVisible(true);
	}
	
	private void fecharJanela() {
		this.dispose();
	}

}
