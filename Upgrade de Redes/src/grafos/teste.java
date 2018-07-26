package grafos;

import java.util.Random;

public class teste {
	
	public static void main(String[] args) {
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2� per�odo/Metaheur�sticas em otimiza��o combinat�ria (MHOC)/"
				+ "Trabalho final/Inst�ncias do trabalho pr�tico/D69.in");
		
		System.out.println("Vertices");
		for (Vertice v : g.getVertices()) {
			System.out.println(v);
		}
		
		System.out.println("Arestas");
		for (Aresta a : g.getArestas()) {
			System.out.println(a.getVertice_1() + " " + a.getVertice_2() + " peso: " + a.getPeso());
		}
		g.upgrade("2");
		g.upgrade("3");
		
		System.out.println("Arestas p�s upgrade");
		for (Aresta a : g.getArestas()) {
			System.out.println(a.getVertice_1() + " " + a.getVertice_2() + " peso: " + a.getPeso());
		}
		System.out.println("Custo: " + g.getCusto());

	}

}
