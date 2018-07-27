package metaheuristicas;

import java.util.Random;

import grafos.Grafo;

public class AlgoritmoGenetico {
	
	private Grafo grafo;
	private Integer tamanho_populacao;
	private Integer tamanho_nova_geracao;
	private Integer tamanho_solucoes_elite;
	private Double limite_custo;
	private Individuo populacao [];
	private Individuo nova_geracao []; // quantos novos individuos serão gerados?
	private Individuo solucoes_elite []; // quantas soluções elite manteremos para o path relinking?
	private Double prob_cruzamento = 0.7;
	private Double prob_mutacao = 0.2;
	private Integer geracoes_sem_melhoria = 0;
	
	public AlgoritmoGenetico(Grafo grafo, Integer tamanho_populacao, Double limite_custo) {
		
		init(grafo, tamanho_populacao, limite_custo);
		
		gerar_populacao_inicial();
		
		gerar_novos_individuos();
	}
	
	public void init(Grafo grafo, Integer tamanho_populacao, Double limite_custo) {
		this.grafo = grafo;
		this.tamanho_populacao = tamanho_populacao;
		this.tamanho_nova_geracao = tamanho_populacao / 2;
		this.tamanho_solucoes_elite = tamanho_populacao / 2;
		this.limite_custo = limite_custo;
		this.populacao = new Individuo[tamanho_populacao];
		this.nova_geracao = new Individuo[tamanho_nova_geracao];
		this.solucoes_elite = new Individuo[tamanho_solucoes_elite];
	}
	

	public void gerar_populacao_inicial() {
		Integer individuos_gerados = 0;
		while(individuos_gerados < tamanho_populacao) {
			Individuo novo_individuo = novo_individuo();
			if(checar_viabilidade(novo_individuo)) {
				populacao[individuos_gerados++] = novo_individuo;
			}
		}
	}
	
	public Individuo novo_individuo() {
		
		String id = new String();
		
		for (int i = 0; i < this.grafo.getTotal_vertices(); i++) {
			
			id = id + new Random().nextInt(2);
			
		}
		
		Individuo individuo = new Individuo(id, grafo);
		
		return individuo;
	}
	
	public void gerar_novos_individuos() {
		
		while(geracoes_sem_melhoria <= 10) {
			
			Integer novos_individuos = 0;
			
			while(novos_individuos < this.tamanho_populacao/2) {
				
				Double rand_cruzamentos = Math.random();
				Double rand_mutacao = Math.random();
				
				if(rand_cruzamentos <= prob_cruzamento) {
					
					try {
						
						novos_individuos = cruzamento(novos_individuos);
						
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Quantidade de indivivuos gerados por cruzamento excede limite");
					}
					
				}
				
				if(rand_mutacao <= prob_mutacao) {
					
					try {
						
						novos_individuos = mutacao(novos_individuos);
						
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Quantidade de indivivuos gerados por mutação excede limite");
					}
					
				}
			}
		}
	}
		
	public Integer cruzamento(Integer novos_individuos) {
		
		Integer rand_corte = new Random().nextInt(grafo.getTotal_vertices());
		Integer rand_pai = new Random().nextInt(tamanho_populacao);
		Integer rand_mae = new Random().nextInt(tamanho_populacao);
		
		while (rand_pai == rand_mae) {
			rand_pai = new Random().nextInt(tamanho_populacao);
			rand_mae = new Random().nextInt(tamanho_populacao);
		}
		
		Individuo pai = populacao[rand_pai];
		Individuo mae = populacao[rand_mae];
		
		String id_filho_1 = pai.getId().substring(0, rand_corte) + mae.getId().substring(rand_corte+1, grafo.getTotal_vertices());
		String id_filho_2 = mae.getId().substring(0, rand_corte) + pai.getId().substring(rand_corte+1, grafo.getTotal_vertices());
		
		Individuo filho_1 = new Individuo(id_filho_1, grafo);
		Individuo filho_2 = new Individuo(id_filho_2, grafo);
		
		if(checar_viabilidade(filho_1)) {
			
			nova_geracao[novos_individuos++] = filho_1;
		
		}
		
		if(checar_viabilidade(filho_2)) {
			
			nova_geracao[novos_individuos++] = filho_2;
			
		}
		
		return novos_individuos;
	}
	
	public Integer mutacao(Integer novos_individuos) {
		
		Integer rand_mutacao = new Random().nextInt(grafo.getTotal_vertices());
		Integer rand_individuo = new Random().nextInt(tamanho_populacao);
		
		Individuo individuo = populacao[rand_individuo];
		
		Character alelo_x = individuo.getId().charAt(rand_mutacao);
		Character alelo_y = null;
		
		if(alelo_x.equals('0')) {
			alelo_y = '1';
		}
		else {
			alelo_y = '0';
		}
		
		String id_mutante = individuo.getId().substring(0, rand_mutacao-1) + alelo_y
				+ individuo.getId().substring(rand_mutacao+1, grafo.getTotal_vertices());
		
		Individuo mutante = new Individuo(id_mutante, grafo);
		
		if(checar_viabilidade(mutante)) {
			
			nova_geracao[novos_individuos++] = mutante;
			
		}
		
		return novos_individuos;
	}
	
	private Boolean checar_viabilidade(Individuo individuo) { // é preciso avaliar as demais restrições do problema
		if(individuo.getCusto(grafo) <= this.limite_custo) {
			return true;
		}
		else {
			return false;
		}
	}
}
