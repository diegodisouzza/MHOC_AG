package grafos;

import metaheuristicas.AlgoritmoGenetico;
import metaheuristicas.Individuo;

public class teste {
	
	public static void main(String[] args) {
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2º período/Metaheurísticas em otimização combinatória (MHOC)/Trabalho final/Instâncias do trabalho prático/D57.in");
	
		Integer rodadas = 0;
		while(rodadas < 10) {
			System.out.println("\n--------------Rodada: "+rodadas+1+" -----------------\n");
			AlgoritmoGenetico ag = new AlgoritmoGenetico(g, 25.0,"11001");
			rodadas++;
		}

//		Individuo i = new Individuo("01011",g);
//		System.out.println(i.getCusto());
//		System.out.println(i.getDelay());
//		
//		Individuo i2 = new Individuo("11001",g);
//		System.out.println(i2.getCusto());
//		System.out.println(i2.getDelay());
		
	}

}
