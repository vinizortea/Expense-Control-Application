package operationGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import armazenamento.Conta;
import armazenamento.OperadorDeArquivo;
import excessoes.NaoExisteNenhumaContaException;
import excessoes.SenhaIncorretaException;
import excessoes.UsuarioInexistenteException;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;

//Classe responsável por realizar o login do usuário
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUsuario;

	public Login() {
		super("Login");
		setTitle("                                            Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 354, 265);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel labelUsuario = new JLabel("Usuario:");
		labelUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelUsuario.setBounds(90, 50, 61, 22);
		panel.add(labelUsuario);
		
		textFieldUsuario = new JTextField(10);
		textFieldUsuario.setBounds(161, 53, 96, 20);
		panel.add(textFieldUsuario);
		
		JLabel labelSenha = new JLabel("Senha:");
		labelSenha.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelSenha.setBounds(90, 104, 48, 14);
		panel.add(labelSenha);
		
		JPasswordField fieldSenha = new JPasswordField(10);
		fieldSenha.setBounds(161, 104, 96, 20);
		panel.add(fieldSenha);
		
		JLabel labelUsuarioInexistente = new JLabel("Nome de usu\u00E1rio n\u00E3o existe");
		labelUsuarioInexistente.setForeground(Color.RED);
		labelUsuarioInexistente.setBounds(90, 163, 193, 14);
		labelUsuarioInexistente.setVisible(false);
		panel.add(labelUsuarioInexistente);
		
		JLabel labelSenhaIncorreta = new JLabel("Senha Incorreta");
		labelSenhaIncorreta.setForeground(Color.RED);
		labelSenhaIncorreta.setBounds(90, 163, 113, 14);
		labelSenhaIncorreta.setVisible(false);
		panel.add(labelSenhaIncorreta);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		//Botao que confirma o desejo de se conectar na respectiva conta,
		//que tem o mesmo nome do nome de usuário
		JButton botaoOk = new JButton("Ok");
		botaoOk.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				labelUsuarioInexistente.setVisible(false);
				labelSenhaIncorreta.setVisible(false);
				try {
					  String senha = new String(fieldSenha.getPassword());
					  OperadorDeArquivo.verificarLogin(textFieldUsuario.getText(), senha); 
					  Conta conta = OperadorDeArquivo.lerConta(textFieldUsuario.getText());
					  JanelaPrincipal jprincipal = new JanelaPrincipal(conta);  
					  fecharJanela();
				   
				}catch(UsuarioInexistenteException uie) {
					labelUsuarioInexistente.setVisible(true);
				}catch(SenhaIncorretaException sie) {
					labelSenhaIncorreta.setVisible(true);
				}catch(NaoExisteNenhumaContaException nence) {
				}
			   
				
			}
		});
		panel_1.add(botaoOk);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_1);
		
		//Caso ainda não tenha contas registradas ou 
		//queiram fazer uma nova conta, este botão abre
		//outra JFrame com a responsabilidade de realizar este cadastro
		JButton botaoCriarConta = new JButton("Nova conta");
		botaoCriarConta.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				JanelaNovaConta novaConta = new JanelaNovaConta();
			}
		});
		panel_1.add(botaoCriarConta);
		
		setVisible(true);
	}
	
	public void fecharJanela() {
         this.dispose();
	}
}
