package metaheuristicas;

import java.util.ArrayList;
import java.util.Collections;

import grafos.Aresta;
import grafos.Grafo;

public class Individuo {
	
	private String id;
	private Double custo;
	private Double delay;
	private Boolean solucao_elite = false;
	private Boolean selecionado = false;
	
	public Individuo() {
		setCusto(Double.MAX_VALUE);
	}
	
	public Individuo(String id, Grafo grafo) {
		this.id = id;
		setCusto(grafo);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Double getCusto() {
		return this.custo;
	}
	
	public void setCusto(Grafo grafo) {
		
		Grafo grafo_teste = Grafo.replica(grafo);
		
		grafo_teste.upgrade(id);
		
		custo = grafo_teste.getCusto();
		if(custo <= AlgoritmoGenetico.getBudget()) {
			kruskal(grafo_teste);
		}
		else {
			delay = Double.MAX_VALUE;
		}
	}
	
	public void setCusto(String representacao, Grafo grafo) {
		
		this.id = representacao;
		
		Grafo grafo_teste = Grafo.replica(grafo);
		
		grafo_teste.upgrade(representacao);
		
		custo = grafo_teste.getCusto();
		
		if(custo <= AlgoritmoGenetico.getBudget()) {
			kruskal(grafo_teste);
		}
		else {
			delay = Double.MAX_VALUE;
		}
	}
	
	public void setCusto(Double custo) {
		this.custo = custo;
	}
	
	public void kruskal(Grafo grafo) {
		Collections.sort(grafo.getArestas());
		
		ArrayList<Aresta> arvore = new ArrayList<Aresta>();
		
		UnionFind nodeSet = new UnionFind(grafo.getTotal_vertices()+1);
		
		Integer contador = 0;
		while(arvore.size() < grafo.getTotal_vertices()-1) {
			Aresta aresta_atual = grafo.getArestas().get(contador++);
			Integer conj_1 = nodeSet.encontra(aresta_atual.getVertice_1().getId());
			Integer conj_2 = nodeSet.encontra(aresta_atual.getVertice_2().getId());
			
			if(conj_1 != conj_2) {
				arvore.add(aresta_atual);
				nodeSet.uniao(conj_1, conj_2);
			}
		}
		
		Double delay=0.0;
		for(Aresta aresta: arvore){
			delay += aresta.getDelay();
		}
		
		this.delay = delay;
	}
	
	public Double getDelay() {
		return delay;
	}
	
	public Boolean getSolucao_elite() {
		return solucao_elite;
	}
	
	public void setSolucao_elite(Boolean solucao_elite) {
		this.solucao_elite = solucao_elite;
	}
		
	public Boolean getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Boolean selecionado) {
		this.selecionado = selecionado;
	}

	@Override
	public String toString() {
		return id;
		
	}

	public boolean melhor_que(Individuo individuo) {
		if(this.getDelay() < individuo.getDelay() || (this.getDelay() == individuo.getDelay() && this.getCusto() < individuo.getCusto())) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
	