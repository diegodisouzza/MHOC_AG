package grafos;

import java.util.Random;

import metaheuristicas.AlgoritmoGenetico;
import metaheuristicas.Individuo;

public class teste {
	
	public static void main(String[] args) {
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2� per�odo/Metaheur�sticas em otimiza��o combinat�ria (MHOC)/Trabalho final/Inst�ncias do trabalho pr�tico/D57.in");
	
		AlgoritmoGenetico ag = new AlgoritmoGenetico(g, 35.0, "01011");
				
	}

}
