package metaheuristicas;

import java.util.HashMap;
import java.util.TreeMap;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class Individuo {
	
	private String id;
	private Double custo;
	private Double delay;
	private Boolean solucao_elite = false;
	private Boolean selecionado = false;
	
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
		
		String nos [] = id.split("");
		
		for(int i = 0; i < nos.length; i++) {
			
			if(nos[i].equals("1")){
				grafo_teste.upgrade(String.valueOf(i));
				
			}
		}
		
		this.custo = grafo_teste.getCusto();
		kruskal(grafo_teste);
	}
	
	public void setCusto(Double custo) {
		this.custo = custo;
	}
	
	public void kruskal(Grafo grafo) {
		
		Double delay = 0.0;
		
		Aresta arvore[] = new Aresta[grafo.getTotal_vertices()-1];
		HashMap<String, String> conjunto = new HashMap<String, String>();
		
		for (int i = 0; i < grafo.getTotal_vertices(); i++) {
			conjunto.put(grafo.getVertices()[i].toString(), grafo.getVertices()[i].toString());
		}
		
		TreeMap<Double, Aresta> fila_arestas = new TreeMap<Double,Aresta>();
		
		for (Aresta aresta : grafo.getArestas()) {
			Double prioridade = aresta.getPeso();
			while(fila_arestas.containsKey(prioridade)) {
				prioridade = prioridade+0.1;
			}			
			fila_arestas.put(prioridade, aresta);
		}
		
		Integer num_arestas = 0;
		
		for (Aresta aresta : fila_arestas.values()) {
			
			Vertice vertice_1 = aresta.getVertice_1();
			Vertice vertice_2 = aresta.getVertice_2();
			
			if(!conjunto.get(vertice_1.toString()).contains(vertice_2.toString())) {
				arvore[num_arestas] = aresta;
				
				String uniao = conjunto.get(vertice_1.toString()) + conjunto.get(vertice_2.toString());
				
				for(String chave : uniao.split("")) {
					conjunto.put(chave,uniao);
				}
				
				delay = delay + aresta.getPeso();
			}
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
