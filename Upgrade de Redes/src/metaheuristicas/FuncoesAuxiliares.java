package metaheuristicas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Universidade Federal do Rio de Janeiro - COPPE - PESC
 * Metaheuristicas em Otimização Combinatória 2018.2
 * Alunos: Cláudio Diego Souza, Caroline Lima, Waldomiro Sinico
 * GANU: um Algoritmo Genético para o problema de Upgrade de Redes
 * */


public class FuncoesAuxiliares {
	
	private static FileWriter fw;
	private static BufferedWriter bw;
	private static String conteudo;
	private static Integer rodada = 0;
	
	public static void init(String caminho) {
		rodada++;
		String arquivo = caminho.replace(".in", " - solucao "+rodada+".txt");
		try{
			fw = new FileWriter(arquivo);
			bw = new BufferedWriter(fw);
		}
		catch(IOException e) {
			System.out.println("Erro na abertura do arquivo de saída");
		}
		conteudo = "geracao cruzamentos mutacoes populacao populacao_n solucoes_elite percentual_custo melhoria melhor_delay melhor_custo tempo\n";
	}

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
			if(auxiliar[esquerda].getDelay() < auxiliar[direita].getDelay()){
				populacao[atual] = auxiliar[esquerda];
				esquerda++;
				
			}
			else if(auxiliar[direita].getDelay() < auxiliar[esquerda].getDelay()){
				populacao[atual] = auxiliar[direita];
				direita++;
			}
			else { // se os delays forem iguais, ordena por menor custo
				if(auxiliar[esquerda].getCusto() < auxiliar[direita].getCusto()) {
					populacao[atual] = auxiliar[esquerda];
					esquerda++;
				}
				else {
					populacao[atual] = auxiliar[direita];
					direita++;
				}
			}
			atual++;		
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
			if(populacao[auxiliar[esquerda]].getDelay() < populacao[auxiliar[direita]].getDelay()){
				solucoes_elite[atual] = auxiliar[esquerda];
				esquerda++;
				
			}
			else if(populacao[auxiliar[direita]].getDelay() < populacao[auxiliar[esquerda]].getDelay()){
				solucoes_elite[atual] = auxiliar[direita];
				direita++;
			}
			else { // se os delays forem iguais, ordena por menor custo
				if(populacao[auxiliar[esquerda]].getCusto() < populacao[auxiliar[direita]].getCusto()) {
					solucoes_elite[atual] = auxiliar[esquerda];
					esquerda++;
				}
				else {
					solucoes_elite[atual] = auxiliar[direita];
					direita++;
				}
			}
			atual++;		
		}
		
		int restante = meio - esquerda;
		for (int i = 0; i <= restante; i++) {
			solucoes_elite[atual+i] = auxiliar[esquerda+ i];
		}
	}
	
	public static void conteudo_saida(Integer geracao, Integer cruzamentos, Integer mutacoes, Integer populacao, Integer populacao_n, Integer solucoes_elite, 
			Double percentual_custo, String melhor_solucao, Double melhor_delay, Double melhor_custo, Double tempo){
		conteudo = conteudo + geracao +" "+cruzamentos+" "+mutacoes+" "+populacao+" "+populacao_n+"	"+solucoes_elite+" "
			+percentual_custo+" "+melhor_solucao+" "+melhor_delay+" "+melhor_custo+" "+tempo +"\n"; 		
	}
	
	public static void escrever_saida() {
		try {
			bw.write(conteudo);
			bw.flush();
		} catch (IOException e) {
			System.out.println("Erro na escrita do arquivo de saída");
		}
	}
}
