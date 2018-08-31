package grafos;

import metaheuristicas.AlgoritmoGenetico;


public class teste {
	
	public static void main(String[] args) {
	
		Grafo g = new Grafo("C:/Users/Diego Souza/Desktop/testes/c01/c01.in");
		AlgoritmoGenetico ag = new AlgoritmoGenetico(g, 0.3);
	}

}
