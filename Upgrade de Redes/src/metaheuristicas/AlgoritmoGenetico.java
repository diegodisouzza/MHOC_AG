package metaheuristicas;

import java.util.Random;

import grafos.Grafo;

public class AlgoritmoGenetico {
	
	private Grafo grafo;
	private Integer p; //tamanho da população
	private Integer n; //número de individuos gerados por cruzamento+mutação
	private Integer e; //tamanho do vetor de soluções elite
	private Double limite_custo; //B
	private Individuo populacao [];
	private Individuo populacao_n []; // quantos novos individuos serão gerados?
	private Integer solucoes_elite []; // quantas soluções elite manteremos para o path relinking?
	private Double prob_cruzamento = 0.7;
	private Double prob_mutacao = 0.2;
	private Integer geracoes_sem_melhoria = 0;
	private Individuo melhor_solucao_elite = null;
	private Individuo pior_solucao_elite = null;
	
	public AlgoritmoGenetico(Grafo grafo, Double limite_custo) {
		
		init(grafo, limite_custo);
		
		gerar_populacao_inicial(); // podemos fazer um método guloso
		
		while(geracoes_sem_melhoria < 10) {
			gerar_novos_individuos();
		}
	
	}
	
	public AlgoritmoGenetico(Grafo grafo, Double limite_custo, String id_individuo_otimo) {
		
		init(grafo, limite_custo);
		
		gerar_populacao_inicial(); // podemos fazer um método guloso
		
		while(!melhor_solucao_elite.getId().equals(id_individuo_otimo)) {
			gerar_novos_individuos();
		}
		
	}
	
	private void init(Grafo grafo, Double limite_custo) {
		this.grafo = grafo;
		
		if(this.grafo.getTotal_vertices() >= 10) { // limita o tamanho da populacao a 100 caso o numero de vertices seja maior que 10
			this.p = 100;
		}
		else { // calcula o tamanho da populacao em funcao do numero de vertices
			Double num_vertices = Double.parseDouble(String.valueOf(this.grafo.getTotal_vertices()));
			this.p = (int) Math.pow(2.0, num_vertices) / this.grafo.getTotal_vertices(); // p = (2^total_vertices)/total_vertices
		}
		
		this.n = this.p / 2;
		
		if(this.p < 20) { // garante que e seja pelo menos igual a 2
			this.e = 2;
		}
		else { // calcula o tamanho do conj de solucoes elite em funcao do tamanho da populacao
			this.e = (int) (this.p * 0.1);
		}
		
		this.limite_custo = limite_custo;
		this.populacao = new Individuo[this.p];
		this.populacao_n = new Individuo[n];
		this.solucoes_elite = new Integer[e];
		this.melhor_solucao_elite = new Individuo("", grafo);
		this.pior_solucao_elite = new Individuo("", grafo);
		
		melhor_solucao_elite.setCusto(Double.MAX_VALUE);
		pior_solucao_elite.setCusto(Double.MAX_VALUE);
	}
	

	private void gerar_populacao_inicial() {
		Integer individuos_gerados = 0;
		while(individuos_gerados < p) {
			Individuo novo_individuo = novo_individuo();
			if(checar_viabilidade(novo_individuo)) {
				populacao[individuos_gerados++] = novo_individuo;
			}
		}
		
		primeiro_conj_elite();
	}
	
	private Individuo novo_individuo() {
		
		String id = new String();
		
		for (int i = 0; i < this.grafo.getTotal_vertices(); i++) {
			
			id = id + new Random().nextInt(2);
			
		}
		
		Individuo individuo = new Individuo(id, grafo);
		
		return individuo;
	}
	
	private void gerar_novos_individuos() {
		
		Integer novos_individuos = 0;
			
		while(novos_individuos < this.n) {
				
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
			
		atualizar_populacao_p();
			
		selecionar_solucoes_elite();
	}
		
	private Integer cruzamento(Integer novos_individuos) {
		
		Integer rand_corte = new Random().nextInt(grafo.getTotal_vertices());
		Integer rand_pai = new Random().nextInt(p);
		Integer rand_mae = new Random().nextInt(p);
		
		while (rand_pai == rand_mae) {
			rand_pai = new Random().nextInt(p);
		}
		
		Individuo pai = populacao[rand_pai];
		Individuo mae = populacao[rand_mae];
		
		String id_filho_1 = pai.getId().substring(0, rand_corte) + mae.getId().substring(rand_corte+1, grafo.getTotal_vertices());
		String id_filho_2 = mae.getId().substring(0, rand_corte) + pai.getId().substring(rand_corte+1, grafo.getTotal_vertices());
		
		Individuo filho_1 = new Individuo(id_filho_1, grafo);
		Individuo filho_2 = new Individuo(id_filho_2, grafo);
		
		if(checar_viabilidade(filho_1)) {
			
			populacao_n[novos_individuos++] = filho_1;
		
		}
		
		if(checar_viabilidade(filho_2)) {
			
			populacao_n[novos_individuos++] = filho_2;
			
		}
		
		return novos_individuos;
	}
	
	private Integer mutacao(Integer novos_individuos) {
		
		Integer rand_mutacao = new Random().nextInt(grafo.getTotal_vertices());
		Integer rand_individuo = new Random().nextInt(p);
		
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
			
			populacao_n[novos_individuos++] = mutante;
			
		}
		
		return novos_individuos;
	}
	
	private Boolean checar_viabilidade(Individuo individuo) { // é preciso avaliar as demais restrições do problema
		if(individuo.getCusto() <= this.limite_custo) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void atualizar_populacao_p() {
		
		Integer tamanho_roleta = this.p + this.n + 1 - this.e;
		
		Integer roleta [][] = new Integer[2][tamanho_roleta];		
		Individuo populacao_acrescida [] = new Individuo [p+n-e]; // todo mundo menos solucoes_elite (populacao + populacao_n - solucoes_elite)
		Individuo nova_geracao [] = new Individuo[p];
				
		Integer individuos_populacao = 0;
		Integer individuos_nova_geracao = 0;
		Double custo_total = 0.0;
		
		//insercao dos individuos da solucao_elite na nova_geracao
		for (int i = 0; i < solucoes_elite.length; i++) {
			nova_geracao[i] = populacao[solucoes_elite[i]];
			individuos_nova_geracao++;
		}
		
		// insercao dos individuos da populacao em populacao_acrescida
		for (int i = 0; i < populacao.length; i++) {
			if(!populacao[i].getSolucao_elite()) {
				populacao_acrescida[individuos_populacao] = populacao[i];
				populacao_acrescida[individuos_populacao].setSelecionado(false);
				
				custo_total = custo_total + populacao[i].getCusto();
				individuos_populacao++;
			}
		}	
		
		//insercao dos individuos da populacao_n em populacao_acrescida
		for (int i = 0; i < populacao_n.length; i++) {
			populacao_acrescida[individuos_populacao + i] = populacao_n[i];
			populacao_acrescida[individuos_populacao + i].setSelecionado(false);

			custo_total = custo_total + populacao_n[i].getCusto();
		}
		
		roleta[0][0] = 0;
		roleta[1][0] = null;
		
		// construcao da roleta
		for (int i = 0; i < populacao_acrescida.length; i++) {
			roleta[0][i+1] = 100 * (int) (roleta[0][i] + (1 - (populacao_acrescida[i].getCusto() / custo_total)));
			roleta[1][i+1] = i;
		}
		
		while(individuos_nova_geracao < nova_geracao.length) {
			Integer rand = new Random().nextInt(100) + 1;
			
			for (int i = 0; i < roleta.length-1; i++) {
				if(roleta[0][i] < rand && rand <= roleta[0][i+1]) {
					Integer indice_individuo_selecionado = roleta[1][i+1];
					if(!populacao_acrescida[indice_individuo_selecionado].getSelecionado()) {
						nova_geracao[individuos_nova_geracao] = populacao_acrescida[indice_individuo_selecionado];
						populacao_acrescida[indice_individuo_selecionado].setSelecionado(true);
						continue;
					}
				}
			}
		}
		
		this.populacao = nova_geracao;
	}
	
	private void primeiro_conj_elite() {
		
		ordena_populacao(populacao,0,this.p-1);
		
		for (int i = 0; i < this.e; i++) {
			solucoes_elite[i] = i; // solucoes_elite armazena as primeiras posicoes de populacao
			populacao[i].setSolucao_elite(true);
		}
		
		this.melhor_solucao_elite = populacao[this.solucoes_elite[0]];
		this.pior_solucao_elite = populacao[this.solucoes_elite[e-1]];
	}
	
	private void selecionar_solucoes_elite() {
		
		for (int i = 0; i < populacao.length; i++) {
			if(!populacao[i].getSolucao_elite() && populacao[i].getCusto() < this.melhor_solucao_elite.getCusto()) {
				populacao[solucoes_elite[e-1]].setSolucao_elite(false);
				solucoes_elite[e-1] = i;
				populacao[i].setSolucao_elite(true);
				ordena_solucoes_elite(solucoes_elite, 0, e-1);
			}
			else if(!populacao[i].getSolucao_elite() && populacao[i].getCusto() < this.pior_solucao_elite.getCusto()) {
				if(checar_diferenca(populacao[i])) {
					populacao[solucoes_elite[e-1]].setSolucao_elite(false);
					solucoes_elite[e-1] = i;
					populacao[i].setSolucao_elite(true);
					ordena_solucoes_elite(solucoes_elite, 0, e-1);
				}
			}
		}
	}
	
	private void ordena_populacao(Individuo populacao[], Integer primeira_posicao, Integer ultima_posicao) {
	
		if(primeira_posicao != ultima_posicao) {
			Integer posicao_media = primeira_posicao + ultima_posicao / 2;
			ordena_populacao(populacao, primeira_posicao, posicao_media);
			ordena_populacao(populacao,posicao_media+1,ultima_posicao);
			
			Integer tam_conj_aux_1 = posicao_media - primeira_posicao + 1;
			Integer tam_conj_aux_2 = ultima_posicao - posicao_media;
			
			Individuo conj_aux_1[] = new Individuo[tam_conj_aux_1];
			Individuo conj_aux_2[] = new Individuo[tam_conj_aux_2];
			
			for (int i = 0; i < conj_aux_1.length; i++) {
				conj_aux_1[i] = populacao[primeira_posicao + i];
			}
			for (int i = 0; i < conj_aux_2.length; i++) {
				conj_aux_2[i] = populacao[posicao_media + i + 1];
			}
			
			Integer i = 0;
			Integer j = 0;
			
			for (int k = primeira_posicao; k < ultima_posicao; k++) {
				if(conj_aux_1[i].getCusto() <= conj_aux_2[j].getCusto()) {
					populacao[k] = conj_aux_1[i];
					i++;
				}
				else {
					populacao[k] = conj_aux_2[j];
					j++;
				}
			}
		}
	}
		
	private void ordena_solucoes_elite(Integer solucoes_elite[], Integer primeira_posicao, Integer ultima_posicao) {
		if(primeira_posicao != ultima_posicao) {
			Integer posicao_media = primeira_posicao + ultima_posicao / 2;
			ordena_solucoes_elite(solucoes_elite, primeira_posicao, posicao_media);
			ordena_solucoes_elite(solucoes_elite,posicao_media+1,ultima_posicao);
				
			Integer tam_conj_aux_1 = posicao_media - primeira_posicao + 1;
			Integer tam_conj_aux_2 = ultima_posicao - posicao_media;
				
			Integer conj_aux_1[] = new Integer[tam_conj_aux_1];
			Integer conj_aux_2[] = new Integer[tam_conj_aux_2];
			
			for (int i = 0; i < conj_aux_1.length; i++) {
				conj_aux_1[i] = solucoes_elite[primeira_posicao + i];
			}
			for (int i = 0; i < conj_aux_2.length; i++) {
				conj_aux_2[i] = solucoes_elite[posicao_media + i + 1];
			}
			
			Integer i = 0;
			Integer j = 0;
				
			for (int l = primeira_posicao; l < ultima_posicao; l++) {
				if(populacao[conj_aux_1[i]].getCusto() <= populacao[conj_aux_2[j]].getCusto()) {
					solucoes_elite[l] = conj_aux_1[i];
					i++;
				}
				else {
					solucoes_elite[l] = conj_aux_2[j];
					j++;
				}
			}
		}
	}
	
	private Boolean checar_diferenca(Individuo individuo) {
		Integer diferenca = 0;
		String[] id_aux= individuo.getId().split("");
		Integer comparacao[] = new Integer[grafo.getTotal_vertices()];
		
		for (int i = 0; i < solucoes_elite.length; i++) {
			for (int j = 0; j < grafo.getTotal_vertices(); j++) {
				comparacao[j] = comparacao[j] + Integer.parseInt(populacao[solucoes_elite[i]].getId().split("")[j]);
			}
		}
		
		for (int i = 0; i < comparacao.length; i++) {
			
			if(comparacao[i] <= this.e/3) {
				comparacao[i] = 0;
			}
			else if(comparacao[i] >= 2*this.e/3) {
				comparacao[i] = 1;
			}
			else
				comparacao[i] = Integer.MAX_VALUE;
		}
		
		for (int i = 0; i < comparacao.length; i++) {			
			if(comparacao[i] != Integer.MAX_VALUE && !id_aux.equals(String.valueOf(comparacao[i]))) {
				diferenca++;
			}
		}
		
		if(diferenca > grafo.getTotal_vertices()/2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
}
