package grafos;

public class teste {
	
	public static void main(String[] args) {
		Grafo g = new Grafo("C:/Users/Diego Souza/Documents/UFRJ/2º período/Metaheurísticas em otimização combinatória (MHOC)/"
				+ "Trabalho final/Instâncias do trabalho prático/D69.in");
		
		System.out.println("Vertices");
		for (Vertice v : g.getVertices()) {
			System.out.println(v);
		}
		
		System.out.println("Arestas");
		for (Aresta a : g.getArestas()) {
			System.out.println(a.getVertice_1() + " " + a.getVertice_2() + " peso: " + a.getPeso());
		}
		g.upgrade("2");
		g.upgrade("3");
		
		System.out.println("Arestas pós upgrade");
		for (Aresta a : g.getArestas()) {
			System.out.println(a.getVertice_1() + " " + a.getVertice_2() + " peso: " + a.getPeso());
		}
		System.out.println("Custo: " + g.getCusto());
	}

}
