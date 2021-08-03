package operationGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import armazenamento.Conta;
import armazenamento.OperadorDeArquivo;
import planejarGastos.Planejamento;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;


//Classe responsável por mostrar o menu principal
//onde se encontra os planejamentos criados pelo usuário
public class JanelaPrincipal extends JFrame {
	
	JPanel contentPanel;
	
	public JanelaPrincipal(Conta conta) {
		super(conta.getNome());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 150);
		setLocationRelativeTo(null);
		contentPanel = new JPanel();
		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		ArrayList<String> planejamentos = new ArrayList<>();
		for(Planejamento p : conta.getPlanejamentos()) {
			planejamentos.add(p.getNome());
		}
		
		JPanel panelNorth = new JPanel();
		contentPanel.add(panelNorth, BorderLayout.NORTH);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panelNorth.add(verticalStrut);
		
		JPanel panelCenter = new JPanel();
		contentPanel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel labelPlanejamento = new JLabel("Planejamentos:");
		labelPlanejamento.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelCenter.add(labelPlanejamento);
		
		JComboBox comboBoxPlanejamentos = new JComboBox();
		comboBoxPlanejamentos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panelCenter.add(comboBoxPlanejamentos);
		DefaultComboBoxModel planejamentosNaComboBox = new DefaultComboBoxModel<>(planejamentos.toArray());
		comboBoxPlanejamentos.setModel(planejamentosNaComboBox);
		
		
		//Botao que seleciona o item atual mostrado pela comboBoxPlanejamentos
		//e abre outra Janela responsável por mostrar a tabela do planejamento selecionado
		JButton botaoSelecionarPlanejamento = new JButton("Selecionar");
		botaoSelecionarPlanejamento.setFont(new Font("Tahoma", Font.PLAIN, 11));
		botaoSelecionarPlanejamento.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				for(Planejamento p : conta.getPlanejamentos()) {
					if(p.getNome().equals(comboBoxPlanejamentos.getSelectedItem())) {
						JanelaPlanejamento jPlanejamento = new JanelaPlanejamento(p,conta);
						break;
					}
				}
			}
		});
		panelCenter.add(botaoSelecionarPlanejamento);
		
		JPanel panelSouth = new JPanel();
		contentPanel.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
		
		//Botão que chama a JanelaCriarPlanejamento
		JButton botaoCriarPlanejamento = new JButton("Criar novo planejamento");
		botaoCriarPlanejamento.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				conta.criarPlanejamento(planejamentosNaComboBox);
				
			}
		});
		panelSouth.add(botaoCriarPlanejamento);
		
		//botao que exclui algum planejamento caso ele não seja mais do interesse do usuário
		JButton botaoExcluirPlanejamento = new JButton("Excluir ");
		botaoExcluirPlanejamento.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int desejaExcluirPlanejamento;;
				for(Planejamento p : conta.getPlanejamentos()) {
					if(p.getNome().equals(comboBoxPlanejamentos.getSelectedItem())) {
						desejaExcluirPlanejamento = JOptionPane.showConfirmDialog(botaoExcluirPlanejamento, "Excluir Planejamento:"+p.getNome(), "Confirme a exclusão", JOptionPane.OK_CANCEL_OPTION);
					    if(desejaExcluirPlanejamento == JOptionPane.OK_OPTION) {
				            conta.getPlanejamentos().remove(p);	
				            OperadorDeArquivo.salvarConta(conta);
				            comboBoxPlanejamentos.removeItem(p.getNome());
				            break;
					    }
					}
				}
				
			}
		});
		panelSouth.add(botaoExcluirPlanejamento);

		setVisible(true);	    
	}
}
