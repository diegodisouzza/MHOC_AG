package metaheuristicas;

import grafos.Grafo;

public class Individuo {
	
	private String id;
	private Double custo;
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

	public void setCusto(Grafo grafo) {
		
		Grafo grafo_teste = Grafo.replica(grafo);
		
		String nos [] = id.split("");
		
		for(int i = 0; i < nos.length; i++) {
			
			if(nos[i].equals("1")){
				grafo_teste.upgrade(String.valueOf(i));
				
			}
		}
		
		this.custo = grafo_teste.getCusto();
	}
	
	public void setCusto(Double custo) {
		this.custo = custo;
	}
	
	
}
