public class PentominoSum {
    public static void printMaxSum(int[][] board) {
        int N = board.length;
        int M = board[0].length;
        int[][] t_board = new int[N][M];
        int result = 0;

        for (int i=0; i < N; i++) {
            for (int j=0; j < M; j++) {
                int rect = check_rect(i, j, board);
                int cross = check_cross(i, j, board);
                int angle = check_angle(i, j, board);
                t_board[i][j] = max(rect, max(cross, angle));
            }
            result = max(max_array(t_board[i]), result);
        }
        System.out.println(result);
    }

    private static int max_array(int[] board) {
        int N = board.length;
        int max = board[0];
        for (int i=0; i<N; i++) {
            max = max(max, board[i]);
        }
        return max;
    }
    private static int max(int a, int b) {
        if (b<a) return a;
        else return b;
    }

    private static int check_angle(int i, int j, int[][] board) {
        int N = board.length;
        int M = board[0].length;
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        int sum4 = 0;

        if (i < N-2 && j < M-2) {
            sum1 = board[i][j];
            for (int k=1; k<3; k++) {
                sum1 += board[i+k][j] + board[i][j+k];
            }
        }

        if (i > 1 && j > 1) {
            sum2 = board[i][j];
            for (int k=1; k<3; k++) {
                sum2 += board[i-k][j] + board[i][j-k];
            }
        }

        if (i > 1 && j < M-2) {
            sum3 = board[i][j];
            for (int k=1; k<3; k++) {
                sum3 += board[i-k][j] + board[i][j+k];
            }
        }

        if (i < N-2 && j > 1) {
            sum4 = board[i][j];
            for (int k=1; k<3; k++) {
                sum4 += board[i+k][j] + board[i][j-k];
            }
        }

        return max(max(sum1, sum2), max(sum3, sum4));
    }

    private static int check_cross(int i, int j, int[][] board) {
        int N = board.length;
        int M = board[0].length;
        int sum = 0;
        if ((i > 0 && i < N-1) && (j > 0 && j < M-1)) {
            sum = board[i][j];
            for (int k=-1; k < 2; k+=2) {
                sum += board[i+k][j] + board[i][j+k];
            }
        }
        return sum;
    }

    private static int check_rect(int i, int j, int[][] board) {
        int N = board.length;
        int M = board[0].length;
        int h_sum = 0;
        int v_sum = 0;

        if (j < M-5) {
            for (int k=0; k < 5; k++) {
                h_sum += board[i][j+k];
            }
        }
        if (i < N-5) {
            for (int k=0; k < 5; k++) {
                v_sum += board[i+k][j];
            }
        }
        return max(v_sum, h_sum);
    }


}

