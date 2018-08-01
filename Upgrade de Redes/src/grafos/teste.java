package grafos;

import java.util.Random;

import metaheuristicas.Individuo;

public class teste {
	
	public static void main(String[] args) {
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2� per�odo/Metaheur�sticas em otimiza��o combinat�ria (MHOC)/"
				+ "Trabalho final/Inst�ncias do trabalho pr�tico/D57.in");
		
		System.out.println("Vertices");
		for (Vertice v : g.getVertices()) {
			System.out.println(v);
		}
		
		System.out.println("Arestas");
		for (Aresta a : g.getArestas()) {
			System.out.println(a.getVertice_1() + " " + a.getVertice_2() + " peso: " + a.getPeso());
		}
		
		Individuo i = new Individuo("01011", g);
		System.out.println("custo : "+ i.getCusto());

	}

}
