package grafos;

import metaheuristicas.AlgoritmoGenetico;
import metaheuristicas.PathRelinking;

public class teste {
	
	public static void main(String[] args) {
	
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2� per�odo/Metaheur�sticas em otimiza��o combinat�ria (MHOC)/Trabalho Final - GANU/Inst�ncias/50 v�rtices/D50226.in");
		AlgoritmoGenetico ag = new AlgoritmoGenetico(g, 0.5, "11111101011111110101000001011011000011101011101101");
		
	}

}
