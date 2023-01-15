import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // TestCases
        System.out.println("/***** TestCase *****/");
        test(12);

        // Test your own inputs
        System.out.println("Enter your own inputs:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.close();

        PrimeFactorization.factorize(n);
    }

    private static void test(int n) {
        System.out.println("---------- Input -----------");
        System.out.println(n);
        System.out.println("---------- Output ----------");
        PrimeFactorization.factorize(n);
        System.out.println("----------------------------");
    }
}
