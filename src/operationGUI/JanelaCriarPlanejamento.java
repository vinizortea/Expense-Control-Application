package operationGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import armazenamento.Conta;
import armazenamento.OperadorDeArquivo;
import planejarGastos.Planejamento;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;


//Cria os planejamentos do usuário
public class JanelaCriarPlanejamento extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNomePlanejamento;

	public JanelaCriarPlanejamento(Conta conta, DefaultComboBoxModel planejamentosNaComboBox) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel labelNomePlanejamento = new JLabel("Nome do planejamento:");
		panel.add(labelNomePlanejamento);
		
		textFieldNomePlanejamento = new JTextField();
		panel.add(textFieldNomePlanejamento);
		textFieldNomePlanejamento.setColumns(20);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		JLabel labelDescricao = new JLabel("Descri\u00E7\u00E3o:");
		panel_2.add(labelDescricao);
		
		JTextArea textAreaDescricao = new JTextArea();
		panel_1.add(textAreaDescricao, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JComboBox comboBoxCategorias = new JComboBox();
		comboBoxCategorias.setEditable(true);
		panel_4.add(comboBoxCategorias);
		
		//Botao que criaa categoria escrita pelo usuário na comboBox,
		//visto que para criar um planejamento tem que ter ao menos 1 Categoria
		//criada no momento de criar este planejamento
		JButton botaoCriarCategoria = new JButton("Criar categoria");
		botaoCriarCategoria.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!comboBoxCategorias.getSelectedItem().equals("")) {
				   int iterador;
				   boolean existeCategoria = false;
				   for(iterador = 0; iterador < comboBoxCategorias.getItemCount(); iterador++ ) {
					   if(comboBoxCategorias.getItemAt(iterador).equals(comboBoxCategorias.getSelectedItem())) {
						   existeCategoria = true;
					   }
				   }
				   if(!existeCategoria) {	
					comboBoxCategorias.addItem(comboBoxCategorias.getSelectedItem());
				   }
				}
				
			}
		});
		panel_4.add(botaoCriarCategoria);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 5));
		
		//Responsável por pegar todas as informações( nome do planejamento, descrição e categorias) e salvá-las na conta do usuário
		JButton botaoCriarPlanejamento = new JButton("Salvar");
	    botaoCriarPlanejamento.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
			   if(planejamentosNaComboBox.getIndexOf(textFieldNomePlanejamento.getText()) == -1 && comboBoxCategorias.getItemCount() > 0) {
				int iterador;
				Planejamento planejamento = new Planejamento(textFieldNomePlanejamento.getText(),textAreaDescricao.getText());
				
				for(iterador = 0; iterador < comboBoxCategorias.getItemCount(); iterador++) {
					planejamento.criarCategoria((String)comboBoxCategorias.getItemAt(iterador));
				}
				
				conta.getPlanejamentos().add(planejamento);
				OperadorDeArquivo.salvarConta(conta);
				planejamentosNaComboBox.addElement(textFieldNomePlanejamento.getText());
				fecharJanela();
			   }else if(planejamentosNaComboBox.getIndexOf(textFieldNomePlanejamento.getText()) != -1) {
				   JOptionPane.showMessageDialog(botaoCriarPlanejamento, "Não é possível repetir nome de planejamentos", "Planejamento existente", JOptionPane.INFORMATION_MESSAGE);
			   }else if(comboBoxCategorias.getItemCount() == 0) {
				   JOptionPane.showMessageDialog(botaoCriarPlanejamento, "É necessário criar ao menos uma Categoria", "Adicione Categoria", JOptionPane.INFORMATION_MESSAGE);
			   }
			}
		});
		panel_3.add(botaoCriarPlanejamento);
		
		JButton botaoCancelar = new JButton("Cancelar");
		botaoCancelar.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
				fecharJanela();
				
			}
		});
		panel_3.add(botaoCancelar);
		
		setVisible(true);
	}
	
	public void fecharJanela() {
		this.dispose();
	}
}
