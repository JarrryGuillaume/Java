public class FibonacciNumbers {
    public static void printFibonacciNumbers(int n) {
        long F_i = 0;
        long F_j = 1;

        if (n>1) {
            System.out.print(F_i);
            System.out.print(" " + F_j);
        } else if (n <= 1) {
            System.out.println(F_i);
            System.out.println("sum = 0");
        }
        long F_k = 0;
        long s = 1;
        for (int i=2; i<n; i++) {
            F_k = F_i + F_j;
            s += F_k;
            System.out.print(" " + F_k);
            F_i = F_j;
            F_j = F_k;
        }
        String res = Long.toString(s);
        System.out.println();
        int len = res.length();
        if (len > 5) {
            res = res.substring(len-5, len);
            System.out.println("sum = " + res);
        } else if (n > 1) {
            System.out.print("sum = " + s);
        }
    }
}
