public interface Percolation {
	void open(int i, int j);          // open site (row i, column j) if it is not open already
	boolean isOpen(int i, int j);     // is site (row i, column j) open?
	boolean isFull(int i, int j);     // is site (row i, column j) full?
	boolean percolates();             // does the system percolate?
	double ratio();					  // total opened girds / total grids
}