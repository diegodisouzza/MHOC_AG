package grafos;

/**
 * Universidade Federal do Rio de Janeiro - COPPE - PESC
 * Metaheuristicas em Otimização Combinatória 2018.2
 * Alunos: Cláudio Diego Souza, Caroline Lima, Waldomiro Sinico
 * GANU: um Algoritmo Genético para o problema de Upgrade de Redes
 * */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Grafo {

	private FileReader fr;
	private BufferedReader br;
	
	private Integer total_vertices;
	private Integer total_arestas;
	
	private Vertice vertices [];
	private List<Aresta> arestas;
	
	private String arquivo = new String();
	
	private Double custo = 0.0;
	
	public Grafo(String arquivo) {
		this.arquivo = arquivo;
		try {
			this.fr = new FileReader(this.arquivo);
			this.br = new BufferedReader(this.fr);
			
			String linha = br.readLine();
			
			this.total_vertices = Integer.parseInt(linha.split(" ")[0]);
			this.total_arestas = Integer.parseInt(linha.split(" ")[1]);
			
			vertices =  new Vertice[this.total_vertices];
			arestas = new ArrayList<Aresta>(this.total_arestas);
			
			linha = br.readLine();
			Integer contador_custos = 0;;

			while(linha != null) {
				String info [] = linha.split(" ");
				if(info.length > 1) {
					this.instanciarGrafo(info);
				}
				else {
					this.instanciarCustos(info, contador_custos++);
				}
				linha = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Erro de leitura do arquivo de instância do grafo");
			JOptionPane.showMessageDialog(null, "Erro de leitura do arquivo de instância do grafo\n"
					+ "Verifique se o caminho está correto", "Erro!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void instanciarGrafo(String info[]) {
		Integer v_1 = Integer.parseInt(info[0]), v_2 = Integer.parseInt(info[1]);
		Vertice vertice_1, vertice_2;
		
		if(this.vertices[v_1] != null) {
			vertice_1 = this.vertices[v_1];
		}
		else {
			vertice_1 = new Vertice(info[0]);
			this.vertices[vertice_1.getId()] = vertice_1;
		}
		
		if(this.vertices[v_2] != null) {
			vertice_2 = this.vertices[v_2];
		}
		else {
			vertice_2 = new Vertice(info[1]);
			this.vertices[vertice_2.getId()] = vertice_2;
		}
		
		Double peso_1 = Double.parseDouble(info[2]);
		Double peso_2 = Double.parseDouble(info[3]);
		Double peso_3 = Double.parseDouble(info[4]);
		
		Aresta aresta = new Aresta(vertice_1, vertice_2, peso_1, peso_2, peso_3);
		arestas.add(aresta);
	}
	
	private void instanciarCustos(String info[], Integer contador) {
		if(contador < total_vertices)
			vertices[contador].setCusto(Double.parseDouble(info[0]));
	}
	
	public void upgrade(Integer vertice) {
		for (Aresta aresta: arestas) {
			if(aresta.getVertice_1().getId() == vertice) {
				aresta.upgrade(vertice);
			}
			else if(aresta.getVertice_2().getId() == vertice) {
				aresta.upgrade(vertice);
			}
		}
		setCusto(vertice);
	}
	
	public void upgrade(String representacao) {
		String nos [] = representacao.split("");
		
		for(int i = 0; i < nos.length; i++) {
			
			if(nos[i].equals("1")){
				this.upgrade(i);
			}
		}
	}
	
	public Integer getTotal_vertices() {
		return total_vertices;
	}

	public Integer getTotal_arestas() {
		return total_arestas;
	}

	public Vertice[] getVertices() {
		return vertices;
	}

	public List<Aresta> getArestas() {
		return arestas;
	}

	public Double getCusto() {
		return custo;
	}
	
	public String getArquivo() {
		return arquivo;
	}

	private void setCusto(Integer vertice) {
		this.custo = this.custo + vertices[vertice].getCusto();
	}
	
	public static Grafo replica(Grafo grafo) {
		return new Grafo(grafo.getArquivo());
	}
	
	public Double soma_custos() {
		Double soma = 0.0;
		for(Vertice vertice : vertices) {
			soma = soma + vertice.getCusto();			
		}
		return soma;
	}
	
	public Double soma_delays() {
		Double soma = 0.0;
		for(Aresta aresta : arestas) {
			soma = soma + aresta.getDelay();			
		}
		return soma;
	}
	
}
