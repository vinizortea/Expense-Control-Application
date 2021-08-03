package operationGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import armazenamento.Conta;
import armazenamento.OperadorDeArquivo;
import planejarGastos.Categoria;
import planejarGastos.Planejamento;
import planejarGastos.operacaoDeDados.Dados;
import planejarGastos.operacaoDeDados.Operacoes;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.Component;
import javax.swing.Box;

//Classe que mostra tanto a tabela com todas as categorias do planejamento
//como também, se clicar em alguma categoria especifica, ela mostra alguns detalhes a mais
//daquela categoria
public class JanelaPlanejamento extends JFrame {

	private Planejamento planejamento;
	private Categoria categoriaSelecionada = null;
	private String nomeColunasCategoria[] = null;
	private JPanel contentPanel;
	private JTable table;
	public Object[][] dados, dadosGasto;
	public JScrollPane scrollPane;
	private AbstractTableModel modeloPlanejamento, modeloCategoria;
	
	//estes dois atributos são responsáveis por mudar a função de "nova categoria" e "fechar" para
	//"novo gasto" e "voltar", respectivamente, quando é selecionado alguma categoria do planejamento atual 
	private boolean virarBotaoVoltar = false;
	private boolean virarBotaoNovoDado = false;

	public JanelaPlanejamento(Planejamento planejamento, Conta conta) {
		super(planejamento.getNome());
		this.planejamento = planejamento;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 200);
		setLocationRelativeTo(null);
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPanel);

		Object[] nomeColunasPlanejamento = { "Categorias", "Valor Total" };
		dados = arrayListToObjectMatrix(planejamento.getCategorias(), nomeColunasPlanejamento);

		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setHgap(30);
		contentPanel.add(panel_1, BorderLayout.SOUTH);

		//Modelo utilizado pela JTable quando deseja mostrar o formato do planejamento inteiro
		modeloPlanejamento = new AbstractTableModel() {

			public Object getValueAt(int rowIndex, int columnIndex) {
				return dados[rowIndex][columnIndex];
			}

			public String getColumnName(int col) {
				return (String) nomeColunasPlanejamento[col];
			}

			public int getRowCount() {
				return dados.length;
			}

			public int getColumnCount() {
				return nomeColunasPlanejamento.length;
			}

			public boolean isCellEditable(int row, int col) {
				if (col < nomeColunasPlanejamento.length) {
					return false;
				} else {
					return true;
				}
			}

		};
		modeloPlanejamento.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
					table.setModel(modeloPlanejamento);
			}
		});

		
		//Botão que adiciona uma nova categoria ao Planejamento
		JButton botaoNovaCategoria = new JButton("Nova Categoria");
		botaoNovaCategoria.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			   if(!virarBotaoNovoDado) {	
				String novaCategoria = JOptionPane.showInputDialog(botaoNovaCategoria, "Adicione a nova Categoria:",
						"Nova Categoria de " + planejamento.getNome(), JOptionPane.INFORMATION_MESSAGE);
				boolean existeCategoria = false;
				for (int iterador = 0; iterador < planejamento.getCategorias().size(); iterador++) {
					if (planejamento.getCategorias().get(iterador).getNome().equals(novaCategoria)) {
						existeCategoria = true;
						break;
					}
				}
				if (!existeCategoria) {
					planejamento.criarCategoria(novaCategoria);
					OperadorDeArquivo.salvarConta(conta);
					dados = arrayListToObjectMatrix(planejamento.getCategorias(), nomeColunasPlanejamento);
					modeloPlanejamento.fireTableRowsInserted(0, planejamento.getCategorias().size());
				}
			   }else if(virarBotaoNovoDado) {
				   categoriaSelecionada.criarDado(conta);
			   }
			}
		});

		Component horizontalStrut = Box.createHorizontalStrut(50);
		panel_1.add(horizontalStrut);
		panel_1.add(botaoNovaCategoria);

		JButton botaoFechar = new JButton("Fechar");
		botaoFechar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(!virarBotaoVoltar) {
				fecharJanela();
				}else if(virarBotaoVoltar) {
					botaoFechar.setText("Fechar");
					virarBotaoVoltar = false;
					botaoNovaCategoria.setText("Nova Categoria");
					virarBotaoNovoDado = false;
					modeloPlanejamento.fireTableStructureChanged();
					
				}
			}
		});
		panel_1.add(botaoFechar);

		table = new JTable(modeloPlanejamento);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setDragEnabled(false);

		//Listener responsável por escutar o mouse e mudar da tabela do planejamento para a tabela que
		//mostra outros detalhes da categoria selecionada com o mouse
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				String nomeCategoriaSelecionada = (String) dados[table.getSelectedRow()][table.getSelectedColumn()];
				for (Categoria c : planejamento.getCategorias()) {
					if (c.getNome().equals(nomeCategoriaSelecionada)) {
						if (c.getDados().size() != 0) {
							categoriaSelecionada = c;
							nomeColunasCategoria = new String[c.getDados().size() + 1];
							nomeColunasCategoria[0] = "";
							for (int i = 0; i < c.getDados().size(); i++) {
								nomeColunasCategoria[i + 1] = c.getDados().get(i).getLocal();
							}
							Object[][] dadosGasto = arrayListToObjectMatrix(categoriaSelecionada.getDados(),
									nomeColunasCategoria, categoriaSelecionada);
							
							//Modelo utilizado pela JTable quando deseja mostrar o formato da categoria selecionada
							modeloCategoria = new AbstractTableModel() {

								@Override
								public Object getValueAt(int rowIndex, int columnIndex) {
									return dadosGasto[rowIndex][columnIndex];
								}

								@Override
								public int getRowCount() {
									return dadosGasto.length;
								}

								@Override
								public int getColumnCount() {
									return nomeColunasCategoria.length;
								}

								public boolean isCellEditable(int row, int col) {
									if (col < nomeColunasCategoria.length) {
										return false;
									} else {
										return true;
									}
								}

								public String getColumnName(int col) {
									return (String) nomeColunasCategoria[col];
								}
							};
							//fireTableStructureChanged() aciona o listener de modeloCategoria avisando que mudou 
							//a estrutura da Table, e coloca o modeloCategoria como modelo da Table,
							//mudando também a função dos botões da janela,
							//assim como foi mencionado nos atributos
							modeloCategoria.addTableModelListener(new TableModelListener() {

								@Override
								public void tableChanged(TableModelEvent e) {
									table.setModel(modeloCategoria);
									botaoNovaCategoria.setText("Novo Gasto");
									virarBotaoNovoDado = true;
									botaoFechar.setText("Voltar");
									virarBotaoVoltar = true;
									retornaEssaJanela().validate();
									

								}
							});

							modeloCategoria.fireTableStructureChanged();

						} else if (c.getDados().size() == 0) {
							c.criarDado(conta);
						}
						break;
					}
				}
			}
		});

		scrollPane = new JScrollPane(table);
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	//Ambas as funções abaixo são utilizadas no contrutor para transformarem ArrayList em Object[][] devido 
	//aos construtores das AbstractTableModel
	
	private Object[][] arrayListToObjectMatrix(ArrayList<Categoria> categorias, Object[] nomeColunas) {
		Object[][] dados = new Object[categorias.size()][nomeColunas.length];
		for (int i = 0; i < categorias.size(); i++) {
			dados[i][0] = categorias.get(i).getNome();
			dados[i][1] = Operacoes.totalCategoria(categorias.get(i));
		}
		return dados;
	}

	private Object[][] arrayListToObjectMatrix(ArrayList<Dados> arrayDados, Object[] nomeColunas,
			Categoria categoriaSelecionada) {
		Object[][] dados = new Object[1][nomeColunas.length];
		dados[0][0] = categoriaSelecionada.getNome();
		for (int j = 0; j < arrayDados.size(); j++) {
			dados[0][j + 1] = arrayDados.get(j).getGastoTotal();
		}
		return dados;
	}

	
	private void fecharJanela() {
		this.dispose();
	}
	
	//Utilizado para se referir a JanelaPlanejamento dentro de outros construtores
	private JanelaPlanejamento retornaEssaJanela() {
		return this;
	}

}
