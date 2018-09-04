package metaheuristicas;

/**
 * Universidade Federal do Rio de Janeiro - COPPE - PESC
 * Metaheuristicas em Otimização Combinatória 2018.2
 * Alunos: Cláudio Diego Souza, Caroline Lima, Waldomiro Sinico
 * GANU: um Algoritmo Genético para o problema de Upgrade de Redes
 * */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import grafos.Grafo;

public class GANU extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	JFrame janela = new JFrame("GANU: a Genetic Algorithm for the Network Upgrade problem");
	JPanel painel = new JPanel();
	JLabel ganu = new JLabel("GANU");
	JLabel ganu_1 = new JLabel("a Genetic Algorithm for the Network Upgrade problem");
	JLabel cabecalho = new JLabel("Universidade Federal do Rio de Janeiro - COPPE - PESC");
	JLabel cabecalho_1 = new JLabel("Metaheurísticas em Otimização Combinatória - Prof.: Laura Bahiense");
	JLabel cabecalho_2 = new JLabel("Alunos: Caroline Lima, Cláudio Diego Souza, José Waldomiro Sinico");
	JLabel cabecalho_3 = new JLabel("O GANU executará até:");
	JLabel cabecalho_4 = new JLabel("I. a solução ótima ser encontrada; ou");
	JLabel cabecalho_5 = new JLabel("II. o número máximo de gerações ser atingido (500); ou");
	JLabel cabecalho_6 = new JLabel("III. o número máximo de gerações sem melhoria ser atingido (50)");
	JLabel label_arquivo = new JLabel("Digite o caminho do arquivo (instância)*: ");
	JLabel label_limite_custo = new JLabel("Defina o limite de custo (budget)*: ");
	JLabel label_rodadas = new JLabel("Defina o número de rodadas do GANU*: ");
	JLabel label_solucao_otima = new JLabel("Digite o delay da solução ótima: ");
	JTextField caminho = new JTextField();
	JTextField solucao_otima = new JTextField();
	JComboBox<String> limite_custo = new JComboBox<String>();
	JComboBox<String> rodadas = new JComboBox<String>();
	JButton executar = new JButton("Executar");
	
	Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension dimensao = toolkit.getScreenSize();
	
	public GANU() {
		
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setSize((int) dimensao.getWidth()/2,(int)dimensao.getHeight()/2);
		janela.setVisible(true);
		janela.setResizable(false);
		
		painel.setSize(janela.getWidth(),150);
		painel.setBackground(Color.decode("#e6eeff"));
		painel.setLayout(null);
		
		ganu.setFont(new Font("TimesRoman", Font.BOLD, 70));
		ganu.setBounds(50, 10, 250, 75);
		
		ganu_1.setFont(new Font("TimesRoman", Font.PLAIN, 13));
		ganu_1.setBounds(10, 80, 300, 20);
		
		cabecalho.setFont(new Font("TimesRoman", Font.PLAIN, 13));
		cabecalho.setBounds(310, 10, 400, 20);
		
		cabecalho_1.setFont(new Font("TimesRoman", Font.PLAIN, 13));
		cabecalho_1.setBounds(310, 31, 400, 20);
		
		cabecalho_2.setFont(new Font("TimesRoman", Font.PLAIN, 13));
		cabecalho_2.setBounds(310, 51, 400, 20);
		
		label_arquivo.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		label_arquivo.setBounds(10, 130, 250, 20);
		
		caminho.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		caminho.setBounds(250, 131, janela.getWidth() - 260, 20);
		
		label_limite_custo.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		label_limite_custo.setBounds(10, 160, 200, 20);
		
		limite_custo.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		limite_custo.setBackground(Color.WHITE);
		limite_custo.setBounds(250, 161, 150, 20);
		String limites_custo[] = {"10% do custo total","20% do custo total","30% do custo total",
				"40% do custo total","50% do custo total","60% do custo total",
				"70% do custo total","80% do custo total","90% do custo total"};
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>(limites_custo);
		limite_custo.setModel(modelo);
		
		label_rodadas.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		label_rodadas.setBounds(10, 190, 250, 20);
		
		rodadas.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		rodadas.setBackground(Color.WHITE);
		rodadas.setBounds(250, 191, 150, 20);
		String num_rodadas[] = {"10 rodadas","30 rodadas","50 rodadas"};
		DefaultComboBoxModel<String> modelo_1 = new DefaultComboBoxModel<String>(num_rodadas);
		rodadas.setModel(modelo_1);
		
		label_solucao_otima.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		label_solucao_otima.setBounds(10, 220, 250, 20);
		
		solucao_otima.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		solucao_otima.setBounds(250, 221, janela.getWidth() - 260, 20);
		
		cabecalho_3.setFont(new Font("TimesRoman", Font.PLAIN, 13));
		cabecalho_3.setBounds(10, 250, 450, 20);
		
		cabecalho_4.setFont(new Font("TimesRoman", Font.PLAIN, 13));
		cabecalho_4.setBounds(10, 270, 450, 20);
		
		cabecalho_5.setFont(new Font("TimesRoman", Font.PLAIN, 13));
		cabecalho_5.setBounds(10, 290, 450, 20);
		
		cabecalho_6.setFont(new Font("TimesRoman", Font.PLAIN, 13));
		cabecalho_6.setBounds(10, 310, 450, 20);
		
		executar.setFont(new Font("TimesRoman", Font.BOLD, 20));
		executar.setBounds(janela.getWidth() - 220, 270, 200, 50);
		
		painel.add(ganu);
		painel.add(ganu_1);
		painel.add(cabecalho);
		painel.add(cabecalho_1);
		painel.add(cabecalho_2);
		painel.add(label_arquivo);
		painel.add(caminho);
		painel.add(label_limite_custo);
		painel.add(limite_custo);
		painel.add(label_rodadas);
		painel.add(rodadas);
		painel.add(label_solucao_otima);
		painel.add(solucao_otima);
		painel.add(cabecalho_3);
		painel.add(cabecalho_4);
		painel.add(cabecalho_5);
		painel.add(cabecalho_6);
		painel.add(executar);
		janela.add(painel);
		
		executar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object objeto = e.getSource();
		if(objeto==executar){
			executar.setEnabled(false);
			if(!caminho.getText().equals("")) {
				Grafo g = new Grafo(caminho.getText().replace("\\","/"));
				
				Double percentual_custo;
				Integer execucoes, contador = 0;
				Double otimo = Double.parseDouble(solucao_otima.getText());
				switch(limite_custo.getSelectedIndex()){
					case 0: {
						percentual_custo = 0.1;
						break;
					}
					case 1: {
						percentual_custo = 0.2;
						break;
					}
					case 2: {
						percentual_custo = 0.3;
						break;
					}
					case 3: {
						percentual_custo = 0.4;
						break;
					}
					case 4: {
						percentual_custo = 0.5;
						break;
					}
					case 5: {
						percentual_custo = 0.6;
						break;
					}
					case 6: {
						percentual_custo = 0.7;
						break;
					}
					case 7: {
						percentual_custo = 0.8;
						break;
					}
					default: {
						percentual_custo = 0.9;
						break;
					}
				}
				
				switch(rodadas.getSelectedIndex()){
					case 0: {
						execucoes = 10;
						break;
					}
					case 1: {
						execucoes = 30;
						break;
					}
					default: {
						execucoes = 50;
						break;
					}
				}
				
				if(!otimo.equals("")) {
					while(contador < execucoes) {
						AlgoritmoGenetico ag = new AlgoritmoGenetico(g, percentual_custo, otimo);
						contador++;
						ag.toString();
					}
				}
				else {
					while(contador < execucoes) {
						AlgoritmoGenetico ag = new AlgoritmoGenetico(g, percentual_custo);
						contador++;
						ag.toString();
					}
				}
				JOptionPane.showMessageDialog(null, "Fim do processamento!\nArquivos de saída gerados no mesmo diretório da instância", 
						"Sucesso!", JOptionPane.PLAIN_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "Um caminho válido precisa ser informado", "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			FuncoesAuxiliares.setRodada(0);
			executar.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		new GANU();
	}

}
