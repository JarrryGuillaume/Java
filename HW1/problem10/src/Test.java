import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // TestCases
        int[][] board = {
                {5, 2, 3, 4, 1},
                {3, 2, 2, 3, 3},
                {4, 2, 1, 2, 4},
                {2, 2, 2, 1, 4},
                {4, 4, 3, 3, 4}
        };
        test(board);

        // Test your own inputs
        System.out.println("Enter your own inputs:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        int[][] myBoard = new int[n][m];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                myBoard[r][c] = sc.nextInt();
            }
        }
        sc.close();

        PangPangPang.afterPang(myBoard);
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
        PangPangPang.afterPang(board);
        System.out.println("----------------------------");
    }
}
