package grafos;

/**
 * Universidade Federal do Rio de Janeiro - COPPE - PESC
 * Metaheuristicas em Otimização Combinatória 2018.2
 * Alunos: Cláudio Diego Souza, Caroline Lima, Waldomiro Sinico
 * GANU: um Algoritmo Genético para o problema de Upgrade de Redes
 * */

import java.util.ArrayDeque;
import java.util.Queue;

public class Aresta {
	
	private Vertice vertice_1;
	private Vertice vertice_2;
	private Queue<Double> pesos = new ArrayDeque<Double>(3);
	
	public Aresta(Vertice vertice_1, Vertice vertice_2, Double peso_1, Double peso_2, Double peso_3) {
		this.vertice_1 = vertice_1;
		this.vertice_2 = vertice_2;
		
		this.pesos.add(peso_1);
		this.pesos.add(peso_2);
		this.pesos.add(peso_3);
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
	
	public Double getPeso() {
		this.checkUpgrade();
		return pesos.peek();
	}
	
	public void upgrade(String vertice) {
		if(this.vertice_1.toString().equals(vertice)) {
			this.vertice_1.setUpgrade(true);
			if((!this.vertice_2.getUpgrade() && this.pesos.size() == 3) 
					|| (this.vertice_2.getUpgrade() && this.pesos.size() == 2)) {
				pesos.poll();
			}
		} else if(this.vertice_2.toString().equals(vertice)) {
			this.vertice_2.setUpgrade(true);
			if((!this.vertice_1.getUpgrade() && this.pesos.size() == 3) 
					|| (this.vertice_1.getUpgrade() && this.pesos.size() == 2)) {
				pesos.poll();
			}
		}
	}
	
	public void checkUpgrade() {
		if(this.vertice_1.getUpgrade() && !this.getVertice_2().getUpgrade() && this.pesos.size() == 3)
			pesos.poll();
		else if(!this.vertice_1.getUpgrade() && this.getVertice_2().getUpgrade() && this.pesos.size() == 3)
			pesos.poll();
		else if(this.vertice_1.getUpgrade() && this.getVertice_2().getUpgrade() && this.pesos.size() == 2)
			pesos.poll();
	}	
}
