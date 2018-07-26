package metaheuristicas;

public class Individuo {
	
	private String id;
	private Double custo;
	private Boolean solucao_elite = false;
	
	public Individuo(String id, Double custo) {
		this.id = id;
		this.custo = custo;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Double getCusto() {
		return custo;
	}
	
	public void setCusto(Double custo) {
		this.custo = custo;
	}
	
	public Boolean getSolucao_elite() {
		return solucao_elite;
	}
	
	public void setSolucao_elite(Boolean solucao_elite) {
		this.solucao_elite = solucao_elite;
	}
	
	
}
