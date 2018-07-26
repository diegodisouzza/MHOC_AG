package metaheuristicas;

import java.util.Random;

import grafos.Grafo;

public class AlgoritmoGenetico {
	
	private Grafo grafo;
	private Integer tamanho_populacao;
	private Integer tamanho_nova_geracao;
	private Integer tamanho_solucoes_elite;
	private Individuo populacao [];
	private Individuo nova_geracao [];
	private Individuo solucoes_elite []; // quantas soluções elite manteremos para o path relinking?
	private Double prob_cruzamento = 0.7;
	private Double prob_mutacao = 0.2;
	private Integer max_filhos;
	private Integer geracoes_sem_melhoria = 0;
	
	public AlgoritmoGenetico(Grafo grafo, Integer tamanho_populacao) {
		
		init(grafo, tamanho_populacao);
		
		gerar_populacao_inicial();
		
		gerar_novos_individuos();
	}
	
	public void init(Grafo grafo, Integer tamanho_populacao) {
		this.grafo = grafo;
		this.tamanho_populacao = tamanho_populacao;
		this.tamanho_nova_geracao = tamanho_populacao / 2;
		this.tamanho_solucoes_elite = tamanho_populacao / 2;
		this.populacao = new Individuo[tamanho_populacao];
		this.nova_geracao = new Individuo[tamanho_populacao/2];
		this.solucoes_elite = new Individuo[tamanho_populacao/2];
	}
	

	public void gerar_populacao_inicial() {
		for (int i = 0; i < this.tamanho_populacao; i++) {
			populacao[i] = novo_individuo();
		}
	}
	
	public Individuo novo_individuo() {
		String id = new String();
		for (int i = 0; i < this.grafo.getTotal_vertices(); i++) {
			id = id + new Random().nextInt(2);
		}
		Individuo individuo = new Individuo(id, custo_individuo(id));
		return individuo;
	}
	
	public Double custo_individuo(String id) {
		Grafo grafo_teste = Grafo.replica(grafo);
		String nos [] = id.split("");
		for(int i = 0; i < nos.length; i++) {
			if(nos[i] == "1"){
				grafo_teste.upgrade(String.valueOf(i));
			}
		}
		return grafo_teste.getCusto();
	}
	
	
	public void gerar_novos_individuos() {
		
		while(geracoes_sem_melhoria <= 10) {
			
			Integer novos_individuos = 0;
			
			while(novos_individuos < this.tamanho_populacao/2) {
				
				Double rand_cruzamentos = Math.random();
				Double rand_mutacao = Math.random();
				if(rand_cruzamentos <= prob_cruzamento) {
					cruzamento();
				}
				if(rand_mutacao <= prob_mutacao) {
					mutacao();
				}
			}
		}
	}
		
	public void cruzamento() {
		
	}
	
	public void mutacao() {
		
	}
}
