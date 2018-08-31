package grafos;

/**
 * Universidade Federal do Rio de Janeiro - COPPE - PESC
 * Metaheuristicas em Otimização Combinatória 2018.2
 * Alunos: Cláudio Diego Souza, Caroline Lima, Waldomiro Sinico
 * GANU: um Algoritmo Genético para o problema de Upgrade de Redes
 * */

import java.util.ArrayDeque;
import java.util.Queue;

public class Aresta implements Comparable<Aresta>{
	
	private Vertice vertice_1;
	private Vertice vertice_2;
	private Queue<Double> delays = new ArrayDeque<Double>(3);
	
	public Aresta(Vertice vertice_1, Vertice vertice_2, Double peso_1, Double peso_2, Double peso_3) {
		this.vertice_1 = vertice_1;
		this.vertice_2 = vertice_2;
		
		this.delays.add(peso_1);
		this.delays.add(peso_2);
		this.delays.add(peso_3);
	}
	
	public Vertice getVertice_1() {
		return vertice_1;
	}
	
	public void setVertice_1(Vertice vertice_1) {
		this.vertice_1 = vertice_1;
	}
	
	public Vertice getVertice_2() {
		return vertice_2;
	}
	
	public void setVertice_2(Vertice vertice_2) {
		this.vertice_2 = vertice_2;
	}
	
	public Double getDelay() {
		this.checkUpgrade();
		return delays.peek();
	}
	
	public void upgrade(Integer vertice) {
		if(this.vertice_1.getId() == vertice) {
			this.vertice_1.setUpgrade(true);
			if((!this.vertice_2.getUpgrade() && this.delays.size() == 3) 
					|| (this.vertice_2.getUpgrade() && this.delays.size() == 2)) {
				delays.poll();
			}
		} else if(this.vertice_2.getId() == vertice) {
			this.vertice_2.setUpgrade(true);
			if((!this.vertice_1.getUpgrade() && this.delays.size() == 3) 
					|| (this.vertice_1.getUpgrade() && this.delays.size() == 2)) {
				delays.poll();
			}
		}
	}
	
	public void checkUpgrade() {
		if(this.vertice_1.getUpgrade() && !this.getVertice_2().getUpgrade() && this.delays.size() == 3)
			delays.poll();
		else if(!this.vertice_1.getUpgrade() && this.getVertice_2().getUpgrade() && this.delays.size() == 3)
			delays.poll();
		else if(this.vertice_1.getUpgrade() && this.getVertice_2().getUpgrade() && this.delays.size() == 2)
			delays.poll();
	}	
	
	@Override
	public String toString() {
		return vertice_1 + " - " + vertice_2;
	}

	@Override
	public int compareTo(Aresta aresta) {
		return (int) (this.getDelay() - aresta.getDelay());
	}
}
