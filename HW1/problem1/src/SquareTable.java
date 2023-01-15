public class SquareTable {
    public static void printSquareTable(int n, int m) {
        for (int i=0; i<31; i++) {
            if (i*i >= n && i*i <= m) {
                System.out.println(i + " times " + i + " = " + i*i);
            }
        }
    }

    private static void printOneSquare(int a, int b) {
        System.out.printf("%d times %d = %d\n", a, a, b);
    }
}
