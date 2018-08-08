package grafos;

import java.util.Random;

import metaheuristicas.AlgoritmoGenetico;
import metaheuristicas.Individuo;

public class teste {
	
	public static void main(String[] args) {
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2º período/Metaheurísticas em otimização combinatória (MHOC)/Trabalho final/Instâncias do trabalho prático/D57.in");
	
		AlgoritmoGenetico ag = new AlgoritmoGenetico(g, 35.0, "01011");
		
		
	}

}
