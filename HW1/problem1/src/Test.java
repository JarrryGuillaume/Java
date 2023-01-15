import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // TestCases
        System.out.println("/***** TestCase *****/");
        test(25, 49);

        // Test your own inputs
        System.out.println("Enter your own inputs:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.close();

        SquareTable.printSquareTable(n, m);
    }

    private static void test(int n, int m) {
        System.out.println("---------- Input -----------");
        System.out.println(n + " " + m);
        System.out.println("---------- Output ----------");
        SquareTable.printSquareTable(n, m);
        System.out.println("----------------------------");
    }
}
