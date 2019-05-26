public class PercolationImplementation implements Percolation {
	public static final boolean OPEN = true;
	public static final boolean BLOCKED = false;

	private UF uf;
	private boolean[][] grids;
	private boolean percolate;
	private int source;
	private int destination;
	private int totalOpenGrid;

	public PercolationImplementation(int N) {
		if (N <= 0) throw new IllegalArgumentException();

		uf = new UFWeightedQuickUnion(N * N + 2);
		grids = new boolean[N][N];

		source = N*N;
		destination = N*N+1;

		//connect all the grids in the first row to the last
		//girds in the union-find.

		//sorce for the water flow: grid N*N
		//all the grids at bottom level are connected to grid N*N+1.
		for (int i = 0; i < N; i++) {
			uf.union(i, source);
		}

		for (int i = (N - 1) * N; i < source; i++) {
			uf.union(i, destination);
		}

		totalOpenGrid = 0;

	}

	private boolean valid(int i, int j) {
		return (i >= 0 && i < grids.length) &&
			   (j >= 0 && j < grids.length);
	}

	private int indexInUF(int i, int j) {
		if (!valid(i, j)) 
			throw new IllegalArgumentException();
		return i*grids.length + j;
	}

	public double ratio() {
		return ((double)totalOpenGrid) /
		(grids.length*grids.length);
	}

	public boolean isOpen(int i, int j) {
		if (!valid(i, j)) {
			throw new IllegalArgumentException();
		}

		return grids[i][j] == OPEN;
	}

	public void open(int i, int j) {
		if (!valid(i, j))
			throw new IllegalArgumentException();

		if (grids[i][j] == BLOCKED) {

			grids[i][j] = OPEN;

			if (valid(i-1, j) && isOpen(i-1, j))
				uf.union(indexInUF(i, j), indexInUF(i-1, j));

			if (valid(i+1, j) && isOpen(i+1, j))
				uf.union(indexInUF(i, j), indexInUF(i+1, j));

			if (valid(i, j+1) && isOpen(i, j+1))
				uf.union(indexInUF(i, j), indexInUF(i, j+1));

			if (valid(i, j-1) && isOpen(i, j-1))
				uf.union(indexInUF(i, j), indexInUF(i, j-1));

			totalOpenGrid++;
		}
	}

	public boolean isFull(int i, int j) {
		if (!valid(i, j))
			throw new IllegalArgumentException();

		if (!isOpen(i, j)) {
			return false;
		} else {
			return uf.connected(source, indexInUF(i, j));
		}
	}

	public boolean percolates() {
		return uf.connected(source, destination);
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException();
		}

		Percolation p = new PercolationImplementation(Integer.parseInt(args[0]));

		while (!p.percolates()) {
			int i = StdRandom.uniform(Integer.parseInt(args[0]));
			int j = StdRandom.uniform(Integer.parseInt(args[0]));

			if (!p.isOpen(i, j)) {
				p.open(i, j);
			}
		}

		System.out.println(p.ratio());
		

	}
}