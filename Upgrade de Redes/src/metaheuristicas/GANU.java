package metaheuristicas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class GANU {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		JFrame janela = new JFrame("GANU: a Genetic Algorithm for the Network Upgrade problem");
		JPanel painel = new JPanel();
		JLabel ganu = new JLabel("GANU");
		JLabel ganu_1 = new JLabel("a Genetic Algorithm for the Network Upgrade problem");
		JLabel cabecalho = new JLabel("Universidade Federal do Rio de Janeiro - COPPE - PESC");
		JLabel cabecalho_1 = new JLabel("Metaheurísticas em Otimização Combinatória - Prof.: Laura Bahiense");
		JLabel cabecalho_2 = new JLabel("Alunos: Cláudio Diego Souza, Caroline Lima, José Waldomiro Sinico");
		JLabel label_arquivo = new JLabel("Digite o caminho do arquivo (instância): ");
		JLabel label_limite_custo = new JLabel("Defina o limite de custo (budget): ");
		JLabel label_rodadas = new JLabel("Defina o número de rodadas do GANU: ");
		JLabel label_solucao_otima = new JLabel("Digite o identificador da solução ótima: ");
		JTextField caminho = new JTextField();
		JTextField solucao_otima = new JTextField();
		JComboBox limite_custo = new JComboBox(new Object[]{"10% do custo total","50% do custo total","90% do custo total"});
		JComboBox rodadas = new JComboBox(new Object[]{"10 rodadas","30 rodadas","50 rodadas"});
		JButton executar = new JButton("Executar");
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimensao = toolkit.getScreenSize();
		
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
		caminho.setBounds(240, 131, janela.getWidth() - 260, 20);
		
		label_limite_custo.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		label_limite_custo.setBounds(10, 160, 200, 20);
		
		limite_custo.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		limite_custo.setBackground(Color.WHITE);
		limite_custo.setBounds(240, 161, 150, 20);
		
		label_rodadas.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		label_rodadas.setBounds(10, 190, 250, 20);
		
		rodadas.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		rodadas.setBackground(Color.WHITE);
		rodadas.setBounds(240, 191, 150, 20);
		
		label_solucao_otima.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		label_solucao_otima.setBounds(10, 220, 250, 20);
		
		solucao_otima.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		solucao_otima.setBounds(240, 221, janela.getWidth() - 260, 20);
		
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
		painel.add(executar);
		janela.add(painel);
		
	}

}
