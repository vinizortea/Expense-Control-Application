package operationGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import armazenamento.Conta;
import armazenamento.OperadorDeArquivo;
import planejarGastos.Categoria;
import planejarGastos.Planejamento;
import planejarGastos.operacaoDeDados.Dados;


//Janela responsável pela parte de criação dos dados(gastos) que o usuário
//quer adicionar no sistema
public class JanelaCriarDado extends JFrame {

	private Categoria categoria;
	private JComboBox comboBoxDia, comboBoxMeses, comboBoxAno;
	private JPanel contentPane;
	private JTextField textFieldValorGasto, textFieldLocal;
	private JLabel labelLocal, labelDia, labelMes, labelAno, labelValorGasto;
	private JButton botaoOk, botaoCancelar;
	private ArrayList<Integer> anos;

	
	public JanelaCriarDado(Categoria categoria, Conta conta) {
		
		this.categoria = categoria;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Integer[] dias = new Integer[31];
		for(int i = 0; i<31;i++) {
			dias[i] = i+1;
		}
		Integer[] meses = new Integer[12];
		for(int i = 0; i<12; i++) {
			meses[i] = i+1;
		}
		
		anos = new ArrayList<>();
		for(int i = 2000; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
			anos.add(i);
		}
		
		labelLocal = new JLabel("Local:");
		labelLocal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelLocal.setBounds(42, 11, 50, 41);
		contentPane.add(labelLocal);
		
		labelDia = new JLabel("Dia:");
		labelDia.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelDia.setBounds(47, 86, 26, 22);
		contentPane.add(labelDia);
		
		comboBoxDia = new JComboBox();
		comboBoxDia.setBounds(83, 88, 44, 22);
		comboBoxDia.setModel(new DefaultComboBoxModel(dias));
		contentPane.add(comboBoxDia);
		
		labelMes = new JLabel("Mes:");
		labelMes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelMes.setBounds(160, 92, 32, 14);
		contentPane.add(labelMes);
		
		comboBoxMeses = new JComboBox();
		comboBoxMeses.setModel(new DefaultComboBoxModel(meses));
		comboBoxMeses.setBounds(197, 88, 82, 22);
		contentPane.add(comboBoxMeses);
		
		labelAno = new JLabel("Ano:");
		labelAno.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelAno.setBounds(304, 92, 32, 14);
		contentPane.add(labelAno);
		
		comboBoxAno = new JComboBox();
		comboBoxAno.setModel(new DefaultComboBoxModel(anos.toArray()));
		comboBoxAno.setBounds(346, 88, 60, 22);
		contentPane.add(comboBoxAno);
	
		labelValorGasto = new JLabel("Valor Total Gasto No Local:");
		labelValorGasto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelValorGasto.setBounds(42, 142, 187, 14);
		contentPane.add(labelValorGasto);
		
		textFieldValorGasto = new JTextField();
		textFieldValorGasto.setBounds(239, 141, 96, 20);
		contentPane.add(textFieldValorGasto);
		textFieldValorGasto.setColumns(10);
		
		textFieldLocal = new JTextField();
		textFieldLocal.setBounds(96, 23, 96, 20);
		contentPane.add(textFieldLocal);
		textFieldLocal.setColumns(10);
		
		botaoOk = new JButton("ok");
		
		//caso OK seja apertado, todos os dados nas comboBox e textFields são salvos e o gasto é adicionado a categoria
		botaoOk.addActionListener(new ActionListener() {
			Integer diaSelected, mesSelected, anoSelected;
			String categoriaSelected, localSelected;
			double gastoTotalNoLocal;
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					 if(e.getActionCommand().equals("ok")) {			    	
					    	localSelected = textFieldLocal.getText();
					    	gastoTotalNoLocal = Double.parseDouble(textFieldValorGasto.getText());
							diaSelected = (Integer) comboBoxDia.getSelectedItem();
							mesSelected = (Integer) comboBoxMeses.getSelectedItem();
							anoSelected = (Integer) comboBoxAno.getSelectedItem();
			                categoria.getDados().add(new Dados(localSelected, diaSelected, mesSelected, anoSelected, gastoTotalNoLocal));
			                OperadorDeArquivo.salvarConta(conta);
							fecharJanela();
						}
				}catch(NumberFormatException nrfmt) {
					
				}
				
			}
		});
		botaoOk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoOk.setBounds(262, 227, 47, 23);
		contentPane.add(botaoOk);
		
		botaoCancelar = new JButton("cancelar");
		botaoCancelar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				fecharJanela();
				
			}
		});
		botaoCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoCancelar.setBounds(319, 227, 105, 22);
		contentPane.add(botaoCancelar);
		
		setVisible(true);
	}

	
	public void fecharJanela() {
		this.dispose();
	}
	

}
