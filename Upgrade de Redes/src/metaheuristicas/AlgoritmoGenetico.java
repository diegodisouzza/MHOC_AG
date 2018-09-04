package metaheuristicas;

/**
 * Universidade Federal do Rio de Janeiro - COPPE - PESC
 * Metaheuristicas em Otimiza��o Combinat�ria 2018.2
 * Alunos: Cl�udio Diego Souza, Caroline Lima, Waldomiro Sinico
 * GANU: um Algoritmo Gen�tico para o problema de Upgrade de Redes
 * */

import java.util.Random;

import grafos.Grafo;

public class AlgoritmoGenetico {
	
	private static Grafo grafo;
	private Integer p; //tamanho da popula��o
	private Integer n; //n�mero de individuos gerados por cruzamento+muta��o
	private Integer e; //tamanho do vetor de solu��es elite
	private static Double budget; //B
	private Individuo populacao [];
	private Individuo populacao_n []; // quantos novos individuos ser�o gerados?
	private Integer solucoes_elite []; // quantas solu��es elite manteremos para o path relinking?
	private Double prob_cruzamento = 0.7;
	private Double prob_mutacao = 0.2;
	private Integer geracoes_sem_melhoria = 0;
	private final Integer NUM_MAX_GERACOES = 500;
	private final Integer GERACOES_SEM_MELHORIA = 50;
	private Individuo melhor_solucao_elite = null;
	private Individuo pior_solucao_elite = null;
	private Integer num_cruzamentos = 0, num_mutacoes = 0;
	
	public AlgoritmoGenetico(Grafo grafo, Double percentual) {
		
		System.out.println("Executando GANU... \nCrit�rio de parada: n� de itera��es em melhoria \n");
		
		init(grafo, percentual);
		FuncoesAuxiliares.init(grafo.getArquivo());
		
		Integer num_geracoes = 0;
		Double tempo_anterior = (double) System.currentTimeMillis();
				
		gerar_populacao_inicial(percentual);
		
		Double tempo_atual = (double) System.currentTimeMillis();
		Double intervalo = tempo_atual-tempo_anterior;
		tempo_anterior = tempo_atual;
		
		FuncoesAuxiliares.conteudo_saida_ag(num_geracoes, num_cruzamentos, num_mutacoes, p, n, e, percentual, melhor_solucao_elite.getId(), melhor_solucao_elite.getDelay(), melhor_solucao_elite.getCusto(), intervalo);
		
		while(geracoes_sem_melhoria < GERACOES_SEM_MELHORIA) {
			
			System.out.println("\nGera��o corrente: " + ++num_geracoes + "\n");
			
			gerar_novos_individuos(num_geracoes);
				
			atualizar_populacao_p();
							
			selecionar_solucoes_elite();
			
			tempo_atual = (double) System.currentTimeMillis();
			intervalo = tempo_atual-tempo_anterior;
			tempo_anterior = tempo_atual;
			
			FuncoesAuxiliares.conteudo_saida_ag(num_geracoes, num_cruzamentos, num_mutacoes, p, n, e, percentual, melhor_solucao_elite.getId(), melhor_solucao_elite.getDelay(), melhor_solucao_elite.getCusto(), intervalo);
			
		}
		
		FuncoesAuxiliares.escrever_saida_ag();
		
		PathRelinking.executar(populacao, solucoes_elite, grafo, percentual);
		
		System.out.println("N�mero de gera��es: "+num_geracoes+", melhor solu��o encontrada "+melhor_solucao_elite.getId() 
			+ " delay: "+melhor_solucao_elite.getDelay()+" custo: "+melhor_solucao_elite.getCusto());
	
	}
	
	public AlgoritmoGenetico(Grafo grafo, Double percentual, Double delay_otimo) {
		
		System.out.println("Executando GANU... \nCrit�rio de parada: �timo conhecido ("+delay_otimo+") \n");
		
		init(grafo, percentual);
		FuncoesAuxiliares.init(grafo.getArquivo());
		
		Integer num_geracoes = 0;
		Double tempo_anterior = (double) System.currentTimeMillis();
		
		gerar_populacao_inicial(percentual);
		
		Double tempo_atual = (double) System.currentTimeMillis();
		Double intervalo = tempo_atual-tempo_anterior;
		tempo_anterior = tempo_atual;
		
		FuncoesAuxiliares.conteudo_saida_ag(num_geracoes, num_cruzamentos, num_mutacoes, p, n, e, percentual, melhor_solucao_elite.getId(), melhor_solucao_elite.getDelay(), melhor_solucao_elite.getCusto(), intervalo);
		
		while(melhor_solucao_elite.getDelay() > delay_otimo && num_geracoes < NUM_MAX_GERACOES) {
			
			System.out.println("\nGera��o corrente: " + ++num_geracoes + "\n");
			
			gerar_novos_individuos(num_geracoes);
				
			atualizar_populacao_p();
							
			selecionar_solucoes_elite();
			
			tempo_atual = (double) System.currentTimeMillis();
			intervalo = tempo_atual-tempo_anterior;
			tempo_anterior = tempo_atual;
			
			FuncoesAuxiliares.conteudo_saida_ag(num_geracoes, num_cruzamentos, num_mutacoes, p, n, e, percentual, melhor_solucao_elite.getId(), melhor_solucao_elite.getDelay(), melhor_solucao_elite.getCusto(), intervalo);
		}
		
		FuncoesAuxiliares.escrever_saida_ag();
		
		PathRelinking.executar(populacao, solucoes_elite, grafo, percentual);
		
		System.out.println("N�mero de gera��es: "+num_geracoes+", melhor solu��o encontrada "+melhor_solucao_elite.getId() 
		+ " delay: "+melhor_solucao_elite.getDelay()+" custo: "+melhor_solucao_elite.getCusto());
		
	}
	
	private void init(Grafo g, Double percentual) {
		grafo = g;
		
		if(grafo.getTotal_vertices() >= 10) { // limita o tamanho da populacao a 100 caso o numero de vertices seja maior que 10
			this.p = 100;
		}
		else { // calcula o tamanho da populacao em funcao do numero de vertices
			Double num_vertices = Double.parseDouble(String.valueOf(grafo.getTotal_vertices()));
			this.p = (int) Math.pow(2.0, num_vertices) / grafo.getTotal_vertices(); // p = (2^total_vertices)/total_vertices
		}
		
		System.out.println("[Tamanho] da popula��o: "+this.p);
		
		this.n = this.p / 2;
		
		System.out.println("[Tamanho] da popula��o_n: "+this.n);
		
		if(this.p < 20) { // garante que e seja pelo menos igual a 2
			this.e = 2;
		}
		else { // calcula o tamanho do conj de solucoes elite em funcao do tamanho da populacao
			this.e = (int) (this.p * 0.1);
		}
		
		System.out.println("[Tamanho] das solu��es elite: "+this.e);
		
		budget = percentual * g.soma_custos();
		System.out.println("[Custos total] ("+g.soma_custos()+") * [Percentual] ("
				+percentual+") = [Budget] ("+budget+")");
		this.populacao = new Individuo[this.p];
		this.populacao_n = new Individuo[this.n];
		this.solucoes_elite = new Integer[this.e];
		melhor_solucao_elite = new Individuo();
		pior_solucao_elite = new Individuo();
		
	}
	
	private void gerar_populacao_inicial(Double percentual_upgrade) {
				
		Integer individuos_gerados = 0;
		
		System.out.println("\nGerando popula��o inicial...");
		
		while(individuos_gerados < this.p) {
			
			Individuo novo_individuo = novo_individuo(percentual_upgrade);
			
			populacao[individuos_gerados++] = individuo_viavel(novo_individuo);
			
		}
		
		System.out.println("Popula��o inicial: "+imprime_populacao_generica(populacao));
		
		primeiro_conj_elite();
	}

	private Individuo novo_individuo(Double percentual_upgrade) {
		
		Integer num_upgrades = (int) (percentual_upgrade * grafo.getTotal_vertices());
		
		String id = new String();
		
		Integer contador_upgrades = 0;
		
		for (int i = 0; i < grafo.getTotal_vertices(); i++) {
			id = id + "0";
		}
		
		while(contador_upgrades <= num_upgrades) {
			Integer posicao = new Random().nextInt(grafo.getTotal_vertices());
			if(id.charAt(posicao) == '0'){
				id = id.substring(0, posicao) + "1" + id.substring(posicao+1, id.length());
				contador_upgrades++;
			}
		}
		
		Individuo individuo = new Individuo(id, grafo);
		return individuo;
	}
	
	private void primeiro_conj_elite() {
		
		FuncoesAuxiliares.mergeSort_populacao(populacao,0,this.p-1);
		
		System.out.println("Popula��o inicial ordenada por custo: "+imprime_populacao_generica(populacao));
		
		for (int i = 0; i < this.e; i++) {
			solucoes_elite[i] = i; // solucoes_elite armazena as primeiras posicoes de populacao
			populacao[i].setSolucao_elite(true);
		}
		
		melhor_solucao_elite = populacao[this.solucoes_elite[0]];
		pior_solucao_elite = populacao[this.solucoes_elite[e-1]];
	}
		
	private void gerar_novos_individuos(Integer num_geracoes) {
		
		Integer novos_individuos = 0;
		
		num_cruzamentos = 0;
		num_mutacoes = 0;
		
		System.out.println("[Novos individuos] a serem gerados (populacao_n): "+this.n);
			
		while(novos_individuos < this.n) {
				
			Double rand_cruzamentos = Math.random();
			Double rand_mutacao = Math.random();
				
			if(rand_cruzamentos <= prob_cruzamento) {
				
				novos_individuos = cruzamento(novos_individuos);
				num_cruzamentos++;

			}
				
			if(rand_mutacao <= prob_mutacao) {
				
				novos_individuos = mutacao(novos_individuos);
				num_mutacoes++;
				
			}
		}
		
		System.out.println("[Gera��o "+num_geracoes+"] total de "+num_cruzamentos+" cruzamentos e "+num_mutacoes+" muta��es \n");
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
		
		System.out.println("[Cruzamento] entre os individuos ["+pai.getId()+"] e ["+mae.getId()+"]");
		
		String id_filho_1 = pai.getId().substring(0, rand_corte) + mae.getId().substring(rand_corte, grafo.getTotal_vertices());
		String id_filho_2 = mae.getId().substring(0, rand_corte) + pai.getId().substring(rand_corte, grafo.getTotal_vertices());
		
		Individuo filho_1 = new Individuo(id_filho_1, grafo);
		Individuo filho_2 = new Individuo(id_filho_2, grafo);
		
		System.out.println("Filhos: ["+filho_1.getId()+"] e ["+filho_2.getId()+"]");
		
		try{
			
			populacao_n[novos_individuos++] = individuo_viavel(filho_1);
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Inser��o do individuo ["+filho_1.getId()+"] gerado por cruzamento excede limite");
		}
		
		try{
			
			populacao_n[novos_individuos++] = individuo_viavel(filho_2);
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Inser��o do individuo ["+filho_2.getId()+"] gerado por cruzamento excede limite");
		}
		
		return novos_individuos;
	}
	
	private Integer mutacao(Integer novos_individuos) {
		
		Integer rand_individuo = new Random().nextInt(p);
		Individuo individuo = populacao[rand_individuo];
		
		System.out.println("[Muta��o] no individuo ["+individuo.getId()+"]");
		
		Integer rand_mutacao = new Random().nextInt(grafo.getTotal_vertices());
		Character alelo_x = individuo.getId().charAt(rand_mutacao);
		
		while(!alelo_x.equals('0')) {
			rand_mutacao = new Random().nextInt(grafo.getTotal_vertices());
			alelo_x = individuo.getId().charAt(rand_mutacao);
		}
	
		String id_mutante = individuo.getId().substring(0, rand_mutacao) + "1"
				+ individuo.getId().substring(rand_mutacao+1, grafo.getTotal_vertices());
		
		Individuo mutante = new Individuo(id_mutante, grafo);
		
		System.out.println("Mutante: ["+mutante.getId()+"]");
		
		try{
				
			populacao_n[novos_individuos++] = individuo_viavel(mutante);
				
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Inser��o do individuo ["+mutante.getId()+"] gerado por muta��o excede limite");
		}
		
		
		return novos_individuos;
	}
	
	private Individuo individuo_viavel(Individuo individuo) {
		
		while(individuo.getCusto() > budget) {
			String id_viavel = individuo.getId().replaceFirst("1", "0");
			individuo.setCusto(id_viavel, grafo);
		}
		
		System.out.println("[Indiv�duo vi�vel] "+individuo.getId());
		
		return individuo;
	}
	
	private void atualizar_populacao_p() {
		
		System.out.println("Atualizando popula��o...");
		System.out.println("Popula��o: "+imprime_populacao_generica(populacao)+" + Popula��o N: "+imprime_populacao_generica(populacao_n));
		
		Individuo populacao_acrescida [] = new Individuo [p+n];
		Individuo nova_geracao [] = new Individuo[p];
				
		Integer individuos_populacao = 0;
		Integer individuos_nova_geracao = 0;
		
		//insercao dos individuos da solucao_elite na nova_geracao
		for (int i = 0; i < solucoes_elite.length; i++) {
			nova_geracao[i] = populacao[solucoes_elite[i]];
			individuos_nova_geracao++;
		}
		
		// insercao dos individuos da populacao em populacao_acrescida
		for (int i = 0; i < populacao.length; i++) {
			populacao_acrescida[individuos_populacao] = populacao[i];
			populacao_acrescida[individuos_populacao].setSelecionado(false);
				
			individuos_populacao++;
		}	
		
		//insercao dos individuos da populacao_n em populacao_acrescida
		for (int i = 0; i < populacao_n.length; i++) {
			populacao_acrescida[individuos_populacao] = populacao_n[i];
			populacao_acrescida[individuos_populacao].setSelecionado(false);

			individuos_populacao++;
		}

		System.out.println("Iniciando sorteio de indiv�duos...");
		System.out.println("Candidatos: "+imprime_populacao_generica(populacao_acrescida));
		
		//selecao dos individuos de populacao_acrescida por meio de sorteio
		while(individuos_nova_geracao < nova_geracao.length) {
			Integer rand_1 = new Random().nextInt(populacao_acrescida.length);
			Integer rand_2 = new Random().nextInt(populacao_acrescida.length);
			
			while(populacao_acrescida[rand_1].getId() == populacao_acrescida[rand_2].getId()){
				rand_1 = new Random().nextInt(populacao_acrescida.length);
			}
			
			if(populacao_acrescida[rand_1].getDelay() < populacao_acrescida[rand_2].getDelay()) {
				if(!populacao_acrescida[rand_1].getSolucao_elite() && !populacao_acrescida[rand_1].getSelecionado()) {
					nova_geracao[individuos_nova_geracao] = populacao_acrescida[rand_1];
					populacao_acrescida[rand_1].setSelecionado(true);
					System.out.println("[Torneio] individuo ["+populacao_acrescida[rand_1].getId()+"] selecionado");
					individuos_nova_geracao++;
				}
			}
			else {
				if(!populacao_acrescida[rand_2].getSolucao_elite() && !populacao_acrescida[rand_2].getSelecionado()) {
					nova_geracao[individuos_nova_geracao] = populacao_acrescida[rand_2];
					populacao_acrescida[rand_2].setSelecionado(true);
					System.out.println("[Torneio] individuo ["+populacao_acrescida[rand_2].getId()+"] selecionado");
					individuos_nova_geracao++;
				}
			}
		}
		
		this.populacao = nova_geracao;
		System.out.println("Nova popula��o: "+imprime_populacao_generica(populacao));
	}
		
	private void selecionar_solucoes_elite() { 
		
		System.out.println("Atualizando solu��es elite...");
		System.out.println("Conjunto de solu��es elite atual: "+imprime_solucoes_elite());
		
		Boolean melhoria = false;
		
		atualiza_melhor_pior_solucao_elite();
				
		for (int i = 0; i < populacao.length; i++) {
			
			if(!populacao[i].getSolucao_elite() && !populacao[i].getId().equals(melhor_solucao_elite.getId())
				&& !populacao[i].getId().equals(pior_solucao_elite.getId())) {
				
				// se delay do individuo for menor que da melhor solucao ou se forem iguais, mas apresentar menor custo
				if(populacao[i].melhor_que(melhor_solucao_elite)) {
					
					System.out.println("[Melhor que melhor] "+populacao[i].getId()+" melhor que "+melhor_solucao_elite.getId()
					+" | removida pior solu��o elite: "+populacao[solucoes_elite[e-1]].getId());
					
					substituir_solucao_elite(i);
					atualiza_melhor_pior_solucao_elite();
					
					geracoes_sem_melhoria = 0;
					
					melhoria = true;
				}
				// se delay do individuo for menor que da pior solucao ou se forem iguais, mas apresentar menor custo
				else if(populacao[i].melhor_que(pior_solucao_elite)) {
					
					if(checar_diferenca(populacao[i])) {
						
						System.out.println("[Melhor que pior] "+populacao[i].getId()+" melhor que "+pior_solucao_elite.getId()
						+" | removida pior solu��o elite: "+populacao[solucoes_elite[e-1]].getId());
						
						substituir_solucao_elite(i);
						atualiza_melhor_pior_solucao_elite();
						
						geracoes_sem_melhoria = 0;
						
						melhoria = true;
					}
				}
			}
		}
		
		if(melhoria) {
			System.out.println("Novo conjunto de solu��es elite: "+imprime_solucoes_elite());
		}
		else {
			geracoes_sem_melhoria++;
		}
	}
	
	private void atualiza_melhor_pior_solucao_elite() {
		FuncoesAuxiliares.mergeSort_solucoes_elite(solucoes_elite, populacao, 0, e-1);
		melhor_solucao_elite = populacao[solucoes_elite[0]];
		pior_solucao_elite = populacao[solucoes_elite[e-1]];
	}
	
	private void substituir_solucao_elite(Integer i) {
		populacao[solucoes_elite[e-1]].setSolucao_elite(false);
		solucoes_elite[e-1] = i;
		populacao[i].setSolucao_elite(true);
	}
	
	private Boolean checar_diferenca(Individuo individuo) {
		Integer diferenca = 0;
		String[] id_aux= individuo.getId().split("");
		int comparacao[] = new int[grafo.getTotal_vertices()]; // usei int para o array come�ar com zeros (Integer instancia com nulls)
		
		for (int i = 0; i < solucoes_elite.length; i++) {
			for (int j = 0; j < grafo.getTotal_vertices(); j++) {
				comparacao[j] = comparacao[j] + Integer.parseInt(populacao[solucoes_elite[i]].getId().split("")[j]);
			}
		}
		
		for (int i = 0; i < comparacao.length; i++) {
			
			if(comparacao[i] < this.e/2) {
				comparacao[i] = 0;
			}
			else {
				comparacao[i] = 1;
			}
		}
		
		for (int i = 0; i < comparacao.length; i++) {
			if(!id_aux[i].equals(String.valueOf(comparacao[i]))) {
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
	
	private String imprime_solucoes_elite() {
		String ids = "";
		for (int i = 0; i < solucoes_elite.length; i++) {
			ids = ids + populacao[solucoes_elite[i]].getId() + " ";
		}
		return ids;
	}

	private String imprime_populacao_generica(Individuo populacao_generica[]) {
		String ids = "";
		for (int i = 0; i < populacao_generica.length; i++) {
			ids = ids + populacao_generica[i].getId() + " ";
		}
		return ids;
	}
	
	public static Double getBudget() {
		return budget;
	}
	
	public static Grafo getGrafo() {
		return grafo;
	}
	
}
