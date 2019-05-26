public interface UF {
	//add connection between p and q
	void union(int p, int q);

	//return the component identifier for p (0 to N-1)
	int find(int p);

	//return true if p and q are in the same component
	boolean connected(int p, int q);

	//return the number of component
	int count();
}