public class Main {

    //Testing the standard library.
    public static void main(String[] args) {
	    // write your code here
        int[] a = new int[10];
        for (int i = 0; i < 10; i++) {
            a[i] = i;
        }
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + ", ");
        }
        System.out.println();
        StdRandom.shuffle(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + ", ");
        }
        System.out.println();

    }
}