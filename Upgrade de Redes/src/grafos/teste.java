package grafos;

import metaheuristicas.AlgoritmoGenetico;
import metaheuristicas.Individuo;

public class teste {
	
	public static void main(String[] args) {
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2� per�odo/Metaheur�sticas em otimiza��o combinat�ria (MHOC)/Trabalho final/Inst�ncias do trabalho pr�tico/D57.in");
	
		AlgoritmoGenetico ag = new AlgoritmoGenetico(g, 25.0, "11001");

//		Individuo i = new Individuo("01011",g);
//		System.out.println(i.getCusto());
//		System.out.println(i.getDelay());
//		
//		Individuo i2 = new Individuo("11001",g);
//		System.out.println(i2.getCusto());
//		System.out.println(i2.getDelay());
		
	}

}
