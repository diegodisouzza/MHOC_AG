package grafos;

import metaheuristicas.AlgoritmoGenetico;
import metaheuristicas.PathRelinking;

public class teste {
	
	public static void main(String[] args) {
	
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2º período/Metaheurísticas em otimização combinatória (MHOC)/Trabalho Final - GANU/Instâncias/50 vértices/D50226.in");
		AlgoritmoGenetico ag = new AlgoritmoGenetico(g, 0.5, "11111101011111110101000001011011000011101011101101");
		
	}

}
