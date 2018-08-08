package metaheuristicas;

public class FuncoesAuxiliares {

	public static void mergeSort_populacao(Individuo populacao[], int inicio, int fim){
		if(inicio < fim){
			Integer meio = (inicio + fim) / 2;
			mergeSort_populacao(populacao, inicio, meio);
			mergeSort_populacao(populacao, meio+1, fim);
			merge_populacao(populacao, inicio, meio, fim);
		}	
	}
	
	private static void merge_populacao(Individuo populacao[], Integer inicio, Integer meio, Integer fim){
		Individuo[] auxiliar = new Individuo[populacao.length];
		for (int i = inicio; i <= fim; i++) {
			auxiliar[i] = populacao[i];
		}
		
		int esquerda = inicio;
		int direita = meio+1;
		int atual = inicio;
		
		while (esquerda <= meio && direita <=fim) {
			if(auxiliar[esquerda].getCusto() <= auxiliar[direita].getCusto()){
				populacao[atual] = auxiliar[esquerda];
				esquerda++;
				
			}else{
				populacao[atual] = auxiliar[direita];
				direita++;
			}
			atual ++;		
		}
		
		int restante = meio - esquerda;
		for (int i = 0; i <= restante; i++) {
			populacao[atual+i] = auxiliar[esquerda+ i];
		}
	}
	
	public static void mergeSort_solucoes_elite(Integer solucoes_elite[], Individuo populacao[], int inicio, int fim){
		if(inicio < fim){
			Integer meio = (inicio + fim) / 2;
			mergeSort_solucoes_elite(solucoes_elite, populacao, inicio, meio);
			mergeSort_solucoes_elite(solucoes_elite, populacao, meio+1, fim);
			merge_solucoes_elite(solucoes_elite, populacao, inicio, meio, fim);
		}
	}
	
	private static void merge_solucoes_elite(Integer solucoes_elite[], Individuo populacao[], Integer inicio, Integer meio, Integer fim){
		Integer[] auxiliar = new Integer[solucoes_elite.length];
		for (int i = inicio; i <= fim; i++) {
			auxiliar[i] = solucoes_elite[i];
		}
		
		int esquerda = inicio;
		int direita = meio+1;
		int atual = inicio;
		
		while (esquerda <= meio && direita <=fim) {
			if(populacao[auxiliar[esquerda]].getCusto() <= populacao[auxiliar[direita]].getCusto()){
				solucoes_elite[atual] = auxiliar[esquerda];
				esquerda++;
				
			}else{
				solucoes_elite[atual] = auxiliar[direita];
				direita++;
			}
			atual ++;		
		}
		
		int restante = meio - esquerda;
		for (int i = 0; i <= restante; i++) {
			solucoes_elite[atual+i] = auxiliar[esquerda+ i];
		}
	}
}
