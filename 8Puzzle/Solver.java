import edu.princeton.cs.algs4.MinPQ;

import java.util.Iterator;
import java.util.Stack;

public class Solver {
    private static class State implements Comparable<State> {
        private Board board;
        private int moves;
        private int manhattan;
        //private State next;
        private State previous;

        State(Board board, State previous, int moves) {
            if (board == null) throw new NullPointerException();
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            manhattan = board.manhattan();
        }

        public int compareTo(State that) {
            if (that == null) throw new NullPointerException();
            return getPriority() - that.getPriority();
        }

        private int getPriority() {
            return moves + manhattan;
        }

        public int getMoves() {
            return moves;
        }

        public Board getBoard() {
            return board;
        }

        public State getPrevious() {
            return previous;
        }

        public String toString() {
            return board.toString();
        }

        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) return false;
            State that = (State)o;
            return board.equals(that.board);
        }

        boolean isGoal() {
            return board.isGoal();
        }

    }

    private boolean solvable;
    private int moves;
    private Iterable<Board> solution;


    public Solver(Board initial) {
        //initialize 2 pq for the original board and it's twin board.
        MinPQ<State> pq1 = new MinPQ<>(); //initial board
        MinPQ<State> pq2 = new MinPQ<>(); //twin board

        pq1.insert(new State(initial, null, 0));
        pq2.insert(new State(initial.twin(), null, 0));

        while(!pq1.isEmpty() && !pq2.isEmpty()) {
            State s1 = pq1.delMin(), s2 = pq2.delMin();

            if (s1.isGoal()) {
                solvable = true;
                solution = solution(s1);
                moves = s1.getMoves();
                break;
            }
            if (s2.isGoal()) {
                solvable = false;
                break;
            }

            enqueue(s1, pq1); enqueue(s2, pq2);
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public int moves() {
        return moves;
    }

    private Iterable<Board> solution(State s) {
        if (!isSolvable()) return null;

        Stack<Board> p = new Stack<>();
        while(s != null) {
            p.push(s.getBoard());
            s = s.previous;
        }

        Stack<Board> q = new Stack<>();
        while(! p.isEmpty()) {
            q.push(p.pop());
        }

        p = null;
        return q;
    }

    private void enqueue(State s, MinPQ<State> pq) {
        Iterator<Board> iterator = s.board.neighbors().iterator();
        while(iterator.hasNext()) {
            Board current = iterator.next();
            if (s.getPrevious() == null || !current.equals(s.getPrevious().getBoard()))
                pq.insert(new State(current, s, s.getMoves() + 1));
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}


