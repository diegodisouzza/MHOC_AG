package grafos;

import java.util.Random;

import metaheuristicas.AlgoritmoGenetico;
import metaheuristicas.Individuo;

public class teste {
	
	public static void main(String[] args) {
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2º período/Metaheurísticas em otimização combinatória (MHOC)/Trabalho final/Instâncias do trabalho prático/D57.in");
//		
//		System.out.println("Vertices");
//		for (Vertice v : g.getVertices()) {
//			System.out.println(v);
//		}
//		
//		System.out.println("Arestas");
//		for (Aresta a : g.getArestas()) {
//			System.out.println(a.getVertice_1() + " " + a.getVertice_2() + " peso: " + a.getPeso());
//		}
//		
//		Individuo i = new Individuo("01011", g);
//		System.out.println("custo : "+ i.getCusto());
//		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(g, 35.0, "01011");

	}

}
