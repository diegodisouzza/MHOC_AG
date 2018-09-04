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

	public Integer encontra(int index) {

		int p = pais[index];
		if (index == p) {
			return index;
		}
		return pais[index] = encontra(p);
	}

	public void uniao(int index_1, int index_2) {

		int raiz1 = encontra(index_1);
		int raiz2 = encontra(index_2);

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
