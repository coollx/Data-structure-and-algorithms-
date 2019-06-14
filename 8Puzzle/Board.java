import java.util.Iterator;
import java.util.Stack;

public class Board {
    private int[][] tiles;
    private int n;

    public Board(int[][] tiles) {
        this.tiles = tiles;
        n = tiles.length;
    }

    // return number of blocks out of place
    public int hamming() {
        int cnt = 0;
        int scale = n * n;

        for (int i = 0; i < scale; i++) {
            if (tiles[i / n][i % n] == 0) continue;
            if (tiles[i / n][i % n] != (i + 1) % scale) cnt++;
        }

        return cnt;
    }

    // return sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int cnt = 0;

        for (int i = 0; i < n * n; i++) {
            if (tiles[i / n][i % n] == 0) continue;
            cnt += mhtDistance(tiles[i / n][i % n], i / n, i % n);
        }

        return cnt;
    }

    @Override
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) return false;
        Board other = (Board) y;

        if (other.n != n) return false;
        for (int i = 0; i < n * n; i++) {
            if (tiles[i / other.n][i % other.n] != other.tiles[i / other.n][i % other.n]) return false;
        }

        return true;
    }

    // return an Iterable of all neighboring board positions
    public Iterable<Board> neighbors() {
        Stack<Board> s = new Stack<>();

        //locate the position of ZERO, the empty tile.
        int i = -1, j = -1;

        boolean found = false;
        for (i = 0; i < n ; i++) {
            for (j = 0; j< n; j++) {
                if (tiles[i][j] == 0) {
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        //exchange the empty tile with the surrounding tiles.
        if (isValid(i-1, j)) {
            exch(i, j, i-1, j);
            s.push(new Board(copy(tiles)));
            //exchange back to original position.
            exch(i, j, i-1, j);
        }

        if (isValid(i+1, j)) {
            exch(i, j, i+1, j);
            //System.out.println(this);
            s.push(new Board(copy(tiles)));
            //exchange back to original position.
            exch(i, j, i+1, j);
        }

        if (isValid(i, j-1)) {
            exch(i, j, i, j-1);
            s.push(new Board(copy(tiles)));
            //exchange back to original position.
            exch(i, j, i, j-1);
        }

        if (isValid(i, j+1)) {
            exch(i, j, i, j+1);
            s.push(new Board(copy(tiles)));
            //exchange back to original position.
            exch(i, j, i, j+1);
        }

        return s;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int mhtDistance(int tile, int i, int j) {
        return Math.abs(((tile-1)/n - i))
                + Math.abs(((tile-1) % n - j));
    }

    private boolean isValid(int i, int j) {
        return i >= 0 && i < n && j >= 0 && j < n;
    }

    //exchange the tile[i][j] with tile[m][n], where tile[i][j] == 0,
    //so i, j is necessarily valid, however we need to check if m, n is valid or not.
    private void exch(int i, int j, int m, int n) {
        int tmp = tiles[i][j];
        tiles[i][j] = tiles[m][n];
        tiles[m][n] = tmp;
    }

    private static int[][] copy(int[][] source) {
        int[][] newArray = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            newArray[i] = new int[source[i].length];
            for (int j = 0; j < newArray.length; j++) {
                newArray[i][j] = source[i][j];
            }
        }

        return newArray;
    }

    public boolean isGoal() {
        int scale = n * n;
        for (int i = 0; i < scale; i++) {
            if (tiles[i / n][i % n] != (i + 1) % scale) return false;
        }
        return true;
    }

    public Board twin() {
        int i = 0,j = 1;

        if (tiles[0][0] == 0) {
            i++;
            j++;
        }
        if (tiles[0][1] == 0) j++;

        exch(i / n, i % n, j / n, j % n);
        Board result = new Board(copy(tiles));
        exch(i / n, i % n, j / n, j % n);

        return result;
    }

}
