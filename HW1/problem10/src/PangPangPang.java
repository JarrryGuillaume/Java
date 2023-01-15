public class PangPangPang {
    public static void afterPang(int[][] board) {
        int N = board.length;
        int M = board[0].length;
        int[][] newboard = board;
        int l=0;

        while (true) {
            newboard = board_check(board, newboard);
            newboard = move(board, newboard);
            if (board.equals(newboard)) l ++;
            if (l>1) break;
            board = newboard;
        }
        write(newboard);
    }

    private static void write(int[][] newboard) {
        for (int i=0; i<newboard.length; i++) {
            for (int j=0; j<newboard[0].length - 1; j++) {
                System.out.print(newboard[i][j] + " ");
            }
            System.out.print(newboard[i][newboard[0].length-1]);
            System.out.println();
        }
    }


    private static int[][] board_check(int[][] board, int[][] newboard) {
        int N = board.length;
        int M = board[0].length;
        for (int i=0; i < N; i++) {
            for (int j=0; j < M; j++) {
                newboard = vertical_check(i, j, board, newboard);
                newboard = horizontal_check(i, j, board, newboard);
            }
        }
        return newboard;
    }

    private static int[][] move(int[][] board, int[][] newboard) {
        int N = board.length;
        int M = board[0].length;
        for (int i=N-1; i>-1; i--) {
            for (int j=M-1; j>-1; j--) {
                int k = i;
                if (newboard[i][j] == 0) {
                    while (k>0 && newboard[k][j] == 0) {
                        k--;
                    }
                    board[i][j] = newboard[k][j];
                    newboard[k][j] = 0;
                } else {
                    board[i][j] = board[i][j];
                }
            }
        }
        return board;
    }

    private static int[][] vertical_check(int i, int j, int[][] board, int[][] newboard) {
        int sum = 0;
        int num = board[i][j];
        if (i < 3) {
            if ((board[i][j] == board[i+1][j] && board[i+1][j] == board[i+2][j])) {
                newboard[i][j] = 0;
                newboard[i+1][j] = 0;
                newboard[i+2][j] = 0;
            }
        }
        return newboard;
    }

    private static int[][] horizontal_check(int i, int j, int[][] board, int[][] newboard) {
        int sum = 0;
        int num = board[i][j];
        if (j < 3) {
            if (board[i][j] == board[i][j+1] && board[i][j+1] == board[i][j+2]) {
                newboard[i][j] = 0;
                newboard[i][j+1] = 0;
                newboard[i][j+2] = 0;
            }
        }
        return newboard;
    }
}

