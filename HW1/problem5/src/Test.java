import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // TestCases
        System.out.println("/***** TestCase *****/");
        test("101023");

        // Test your own inputs
        System.out.println("Enter your own inputs:");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        sc.close();

        IPAddress.countValidAddress(str);
    }

    private static void test(String str) {
        System.out.println("---------- Input -----------");
        System.out.println(str);
        System.out.println("---------- Output ----------");
        IPAddress.countValidAddress(str);
        System.out.println("----------------------------");
    }
}
