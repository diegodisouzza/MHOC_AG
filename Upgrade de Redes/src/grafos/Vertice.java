package grafos;

/**
 * Universidade Federal do Rio de Janeiro - COPPE - PESC
 * Metaheuristicas em Otimiza��o Combinat�ria 2018.2
 * Alunos: Cl�udio Diego Souza, Caroline Lima, Waldomiro Sinico
 * GANU: um Algoritmo Gen�tico para o problema de Upgrade de Redes
 * */

public class Vertice {

	private String id;
	private Double custo;
	private Boolean upgrade = false;
	
	public Vertice(String id) {
		this.id = id;
	}
	
	public Integer getId() {
		return Integer.parseInt(id);
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
	
	public Boolean getUpgrade() {
		return upgrade;
	}
	
	public void setUpgrade(Boolean upgrade) {
		this.upgrade = upgrade;
	}
	
	@Override
	public String toString() {
		return id;
		
	}
}
