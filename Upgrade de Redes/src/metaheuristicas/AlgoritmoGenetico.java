package metaheuristicas;

public class AlgoritmoGenetico {
	
	private Integer tamanho_populacao;
	private Individuo populacao [] = new Individuo[tamanho_populacao];
	private Individuo solucoes_elite [] = new Individuo[tamanho_populacao/2]; // quantas soluções elite manteremos para o path relinking?
	private Double prob_cruzamento;
	private Double prob_mutacao;
	private Integer max_filhos;
}
