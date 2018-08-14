package grafos;

import metaheuristicas.AlgoritmoGenetico;
import metaheuristicas.Individuo;

public class teste {
	
	public static void main(String[] args) {
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2º período/Metaheurísticas em otimização combinatória (MHOC)/Trabalho final/Instâncias do trabalho prático/D57.in");
	
		Integer contador = 0;
		while(contador < 10) {
			AlgoritmoGenetico ag = new AlgoritmoGenetico(g, 25.0,"11001");
			contador++;
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
