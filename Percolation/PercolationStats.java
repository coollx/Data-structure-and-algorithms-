public class PercolationStats {
	private double[] stats;
	private double mean;
	private double stddev;

	private double experiment(int N) {
		Percolation p = new PercolationImplementation(N);

		while (!p.percolates()) {
			int i = StdRandom.uniform(N);
			int j = StdRandom.uniform(N);

			if (!p.isOpen(i, j)) {
				p.open(i, j);
			}
		}

		double ratio = p.ratio();
		p = null;

		return ratio;
	}

	// perform T independent experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw  new IllegalArgumentException();
		}

		stats = new double[T];
		double cnt = 0.0;

		for (int i = 0; i < T; i++) {
			stats[i] = experiment(N);
			cnt += stats[i];
		}

		mean = cnt / T;

		cnt = 0.0;

   		for (int i = 0; i < stats.length; i++) {
   			cnt += (stats[i] - mean) * (stats[i] - mean);
   		}

   		stddev = Math.sqrt(cnt / (T - 1));
	}

	// sample mean of percolation threshold
	public double mean() {
		return mean;
	}

	// sample standard deviation of percolation threshold                    
   	public double stddev() {
   		return stddev;
   	}             

   	// low  endpoint of 95% confidence interval
   	public double confidenceLow() {
   		return mean - 1.96 * stddev / Math.sqrt(stats.length);
   	}

   	// high endpoint of 95% confidence interval
   	public double confidenceHigh() {
   		return mean + 1.96 * stddev / Math.sqrt(stats.length);
   	}


   	public static void main(String[] args) {
   		PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
   												   Integer.parseInt(args[1]));
   		System.out.println(ps.mean());
   		System.out.println(ps.stddev());
   		System.out.println(ps.confidenceLow());
   		System.out.println(ps.confidenceHigh());
   	}




}