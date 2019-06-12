import java.util.Random;

public class MinPQ<Key extends Comparable<Key>> {
    private Key[] pq;	//array-representation for the heap;
    private int N; 		//the size of the current heap;
    private int size;

    @SuppressWarnings("unchecked")
    public MinPQ(int size) {
        if (size <= 0) throw new IllegalArgumentException();
        pq = (Key[]) new Comparable[size+1];
        N = 0;
        this.size = size;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key k) {
        if (k == null) throw new NullPointerException();
        ensureSpace();
        pq[++N] = k;
        swim(N);
    }

    public Key delMin() {
        //System.out.println(N);
        if (isEmpty()) throw new IllegalStateException("Pq is empty");

        Key min = pq[1];
        exch(1, N--);
        pq[N+1] = null;
        sink(1);
        return min;
    }

    public Key min() {
        return pq[1];
    }

    public int size() {
        return N;
    }

    @SuppressWarnings("unchecked")
    private void ensureSpace() {
        if (N == size) {
            Key[] newPq = (Key[]) new Comparable[2*size+2];
            for (int i = 0; i < pq.length; i++) {
                newPq[i] = pq[i];
            }
            size = size*2;
            pq = newPq;
        }
    }

    private void swim(int k) {
        while(k > 1 && greater(pq[k/2], pq[k])) {
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k) {
        while(2*k <= N) {
            int j = 2*k;
            if (j < N && greater(pq[j], pq[j+1])) j++;
            if (!greater(pq[k], pq[j])) break;
            exch(k, j);
            k = j;
        }
    }

    @SuppressWarnings("unchecked")
    private boolean greater(Comparable a, Comparable b) {
        return a.compareTo(b) > 0;
    }

    private void exch(int i, int j) {
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

//    public static void main(String[] args) {
//        MinPQ<Integer> pq = new MinPQ<>(3);
//        Random generator = new Random();
//        for (int i = 0; i < 10; i++) {
//            int current = generator.nextInt(20);
//            pq.insert(current);
//            System.out.print(current + ", ");
//        }
//        System.out.println();
//        while(!pq.isEmpty()) {
//            System.out.print(pq.delMin() + ", ");
//        }
//        System.out.println();
//    }
}
