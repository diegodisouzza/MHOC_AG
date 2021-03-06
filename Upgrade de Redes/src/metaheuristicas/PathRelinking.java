package metaheuristicas;

import grafos.Grafo;

public class PathRelinking {
	
	public static void executar(Individuo populacao[], Integer solucoes_elite[], Grafo grafo, Double limite_custo) {

		Individuo novas_solucoes[] = new Individuo[populacao.length];
		Integer num_novas_solucoes = 0;
		Boolean melhoria = false;

		Double tempo_anterior = (double) System.currentTimeMillis();
		
		for (int i = 0; i < solucoes_elite.length - 1; i++) {
			String inicial = populacao[solucoes_elite[i]].getId();
						
			for (int j = 1; j < solucoes_elite.length; j++) {
				String guia = populacao[solucoes_elite[j]].getId();
							
				String inicial2guia = inicial;
				String guia2inicial = guia;
				
				for (int k = 0; k < inicial.length(); k++) {
					if(inicial.charAt(k) != guia.charAt(k)) {
						inicial2guia = inicial2guia.substring(0, k) + guia.charAt(k) 
							+ inicial2guia.substring(k+1,inicial2guia.length());
						
						guia2inicial = guia2inicial.substring(0, k) + inicial.charAt(k) 
							+ guia2inicial.substring(k+1,guia2inicial.length());
						
						if(!inicial2guia.equals(guia) && !inicial2guia.equals(inicial)) {
							Individuo novo = new Individuo(inicial2guia, grafo);
							if(novo.getCusto() <= AlgoritmoGenetico.getBudget() && novo.melhor_que(populacao[solucoes_elite[0]])) {
								novas_solucoes[num_novas_solucoes] = novo;
								num_novas_solucoes++;
								melhoria = true;
							}
						}
						
						if(!guia2inicial.equals(inicial) && !guia2inicial.equals(guia)) {
							Individuo novo_1 = new Individuo(guia2inicial, grafo);
							if(novo_1.getCusto() <= AlgoritmoGenetico.getBudget() && novo_1.melhor_que(populacao[solucoes_elite[0]])) {
								novas_solucoes[num_novas_solucoes] = novo_1;
								num_novas_solucoes++;
								melhoria = true;
							}
						}
					}
				}
			}
		}
		
		Double tempo_atual = (double) System.currentTimeMillis();
		Double intervalo = tempo_atual-tempo_anterior;
		tempo_anterior = tempo_atual;
		
		if(melhoria) {
			FuncoesAuxiliares.escrever_saida_pr(novas_solucoes, intervalo);
		}
	}
		
}
