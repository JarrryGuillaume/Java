import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // TestCases
        System.out.println("/***** TestCase *****/");

        int[][] A = {
                {1, 2},
                {3, 4},
                {5, 6}};

        int[][] B = {
                {1, 1, 2},
                {1, 0, 0}};
        test(A, B);

        // Test your own inputs
        System.out.println("Enter your own inputs:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        int[][] A1 = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                A1[i][j] = sc.nextInt();
            }
        }

        n = sc.nextInt();
        m = sc.nextInt();
        int[][] B1 = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                B1[i][j] = sc.nextInt();
            }
        }

        MatrixMultiplication.multiply(A1, B1);
    }

    private static void test(int[][] A, int[][] B) {
        System.out.println("---------- Input -----------");
        System.out.println("Matrix A");
        for (int[] row: A) {
            for (int i: row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        System.out.println("Matrix B");
        for (int[] row: B) {
            for (int i: row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        System.out.println("---------- Output ----------");
        MatrixMultiplication.multiply(A, B);
        System.out.println("----------------------------");
    }
}
