package metaheuristicas;

public class UnionFind {
	private int[] pais;
	private int[] rank;

	public UnionFind(int num_elementos) {

		pais = new int[num_elementos];
		rank = new int[num_elementos];

		for (int i = 0; i < num_elementos; i++) {
			pais[i] = i;
		}
	}

	public int encontra(int i) {

		int p = pais[i];
		if (i == p) {
			return i;
		}
		return pais[i] = encontra(p);
	}

	public void uniao(int i, int j) {

		int raiz1 = encontra(i);
		int raiz2 = encontra(j);

		if (raiz2 == raiz1)
			return;

		if (rank[raiz1] > rank[raiz2]) {
			pais[raiz2] = raiz1;
		} else if (rank[raiz2] > rank[raiz1]) {
			pais[raiz1] = raiz2;
		} else {
			pais[raiz2] = raiz1;
			rank[raiz1]++;
		}
	}
}
