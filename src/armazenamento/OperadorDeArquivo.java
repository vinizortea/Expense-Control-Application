package armazenamento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import excessoes.NaoExisteNenhumaContaException;
import excessoes.SenhaIncorretaException;
import excessoes.UsuarioExistenteException;
import excessoes.UsuarioInexistenteException;
import planejarGastos.Planejamento;


//Classe abstrata, pois o que interessa s�o apenas os m�todos que s�o respons�veis por toda a parte
//de Abrir, Salvar e Fechar Streams para os arquivos.
//A primeira parte � para o arquivo que cont�m informa��es para o Login, do HashMap da classe Contas.
//A segunda parte � para os arquivos que realmente cont�m dados de Planejamento, Categoria e Dados para serem utilizados
//nas tabelas
public abstract class OperadorDeArquivo {

	private static File diretorioContas = new File("/contas/");
	private static File arquivoConta;
	private static File diretorioInfoLogin = new File("/infoLogin/");
	private static File arquivoInfoLogin = new File(diretorioInfoLogin,"InfoLogin");

	// 1� - Opera��es com arquivos para a classe Contassss.

	public static void salvarInfoLogin(Conta conta) throws UsuarioExistenteException {
		try {
			if (diretorioInfoLogin.exists()) {
				Contas contas = abrirInfoLogin();
				if(contas.getContas().containsKey(conta.getNome())) {
					throw new UsuarioExistenteException();
				}
				contas.getContas().put(conta.getNome(), conta.getSenha());
				FileOutputStream streamDeEscrita = new FileOutputStream(arquivoInfoLogin);
				ObjectOutputStream escrita = new ObjectOutputStream(streamDeEscrita);
				escrita.writeObject(contas);
				escrita.close();
				streamDeEscrita.close();
			} else if (!diretorioInfoLogin.exists()) {
				diretorioInfoLogin.mkdir();
				File caminhoAbsolutoInfoLogin = new File(diretorioInfoLogin,"InfoLogin");
				caminhoAbsolutoInfoLogin.createNewFile();
				Contas contas = new Contas();
				contas.getContas().put(conta.getNome(), conta.getSenha());
				FileOutputStream streamDeEscrita = new FileOutputStream(arquivoInfoLogin);
				ObjectOutputStream escrita = new ObjectOutputStream(streamDeEscrita);
				escrita.writeObject(contas);
				escrita.close();
				streamDeEscrita.close();
			}
		} catch (IOException e) {
			System.err.println("O arquivo " + conta.getNome() + " n�o pode ser criado");
		} catch (NaoExisteNenhumaContaException nence) {
		}

	}

	public static Contas abrirInfoLogin() throws NaoExisteNenhumaContaException{
		try {
			Contas contas;
			ObjectInputStream leitura = new ObjectInputStream(new FileInputStream(arquivoInfoLogin));
			contas = (Contas)leitura.readObject();
			leitura.close();
			return contas;
		} catch (FileNotFoundException fnfe) {
			System.err.println("O arquivo "+arquivoInfoLogin+" n�o foi encontrado");
			throw new NaoExisteNenhumaContaException();
		} catch (IOException ioe) {
			System.err.println("N�o foi poss�vel ler do arquivo "+diretorioInfoLogin+"InfoLogin");
			return null;
		} catch (ClassNotFoundException cnfe) {
			System.err.println("A classe do objeto n�o foi encontrada");
			return null;
		}
	}
	
	public static void verificarLogin(String nome,String senha)throws UsuarioInexistenteException, SenhaIncorretaException, NaoExisteNenhumaContaException {
	   try {
		HashMap contas = abrirInfoLogin().getContas();
		if(!contas.containsKey(nome)) {
			throw new UsuarioInexistenteException();
		}else if(!contas.get(nome).equals(senha)) {
			throw new SenhaIncorretaException();
		}
	   }catch (NaoExisteNenhumaContaException nence) {
		  throw nence;   
	   }
 
	}

	// 2� - Opera��es com arquivos para classe Conta


	public static Conta lerConta(String nome) {
		try {
			arquivoConta = new File(diretorioContas,nome);
			Conta conta;
			ObjectInputStream leitura = new ObjectInputStream(new FileInputStream(arquivoConta));
			conta = (Conta) leitura.readObject();
			leitura.close();
			return conta;

		} catch (FileNotFoundException fnfe) {
			System.err.println("O arquivo "+diretorioContas + nome + " n�o foi encontrado");
			return null;
		} catch (IOException ioe) {
			System.err.println("N�o foi poss�vel ler do arquivo "+diretorioContas + " "+nome);
			return null;
		} catch (ClassNotFoundException cnfe) {
			System.err.println("A classe do objeto n�o foi encontrada");
			return null;
		}

	}

	public static void salvarConta(Conta conta) {
		try {
			arquivoConta = new File(diretorioContas,conta.getNome());
			if(diretorioContas.exists()) {
				FileOutputStream streamDeEscrita = new FileOutputStream(arquivoConta);
				ObjectOutputStream escrita = new ObjectOutputStream(streamDeEscrita);
			    escrita.writeObject(conta);
			    escrita.close();
			    streamDeEscrita.close();
			}else if(!diretorioContas.exists()) {
				diretorioContas.mkdir();
				File caminhoAbsolutoConta = new File(diretorioContas,conta.getNome());
				FileOutputStream streamDeEscrita = new FileOutputStream(arquivoConta);
				ObjectOutputStream escrita = new ObjectOutputStream(streamDeEscrita);
				escrita.writeObject(conta);
				escrita.close();
			    streamDeEscrita.close();
				
			}
			
		} catch (FileNotFoundException fnfe) {
			System.err.println("O arquivo " + conta.getNome() + " n�o foi encontrado");
		} catch (IOException ioe) {
			System.err.println("N�o foi poss�vel escrever no arquivo " + conta.getNome());
		}

	}

}
