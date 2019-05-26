public class UFWeightedQuickUnion implements UF {
	private int[] parent; //parent[i] = parent of i
	private int[] sz; //size[i] = number of sites in subtree rooted at i
	private int count;//number of components

	public UFWeightedQuickUnion(int number) {
		this.count = number;
		parent = new int[number];
		sz = new int[number];

		for (int i = 0; i < number; i++) {
			parent[i] = i;
			sz[i] = 1;
		}

	}

	public int find(int p) {
		if (p < 0 || p > parent.length) {
			throw new IllegalArgumentException();
		}
		while(parent[p] != p) p = parent[p];
		return p;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	//worst case: still O(n^2)
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);

		if (pRoot == qRoot) return;

		if (sz[pRoot] < sz[qRoot]) {
			parent[pRoot] = qRoot;
			sz[qRoot] += sz[pRoot];
		} else {
			parent[qRoot] = pRoot;
			sz[pRoot] += sz[qRoot];
		}

		count--;
	}

	public int count() {
		return count;
	}
}