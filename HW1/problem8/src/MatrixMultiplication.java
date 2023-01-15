public class MatrixMultiplication {
    public static void multiply(int[][] A, int[][] B) {
        int[][] C = new int[A.length][B[0].length];
        for (int i=0; i< A.length; i++) {
            for (int j=0; j < B[0].length; j++){
                int sum = 0;
                for (int k=0; k< A[0].length; k++ ){
                    sum += (long) A[i][k] * B[k][j];
                }
                C[i][j] = sum;
            }
        }
        for (int row=0; row<C.length; row++) {
            for (int col=0; col<C[0].length-1; col++) {
                System.out.print(C[row][col] + " ");
            }
            System.out.println(C[row][C[0].length-1]);
        }
    }
}
