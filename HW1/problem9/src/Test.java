import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // TestCases
        System.out.println("/***** TestCase *****/");
        int[][] board = {
                {1,1,1,1,1},
                {1,4,4,5,1},
                {1,2,1,4,1},
                {1,2,1,4,1},
                {1,1,1,1,1},
        };
        test(board);

        // Test your own inputs
        System.out.println("Enter your own inputs:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        int[][] board1 = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board1[i][j] = sc.nextInt();
            }
        }

        PentominoSum.printMaxSum(board1);
    }

    private static void test(int[][] board) {
        System.out.println("---------- Input -----------");
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[r][c]);
                if (c != board[0].length-1) System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("---------- Output ----------");
        PentominoSum.printMaxSum(board);
        System.out.println("----------------------------");
    }
}
