import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // TestCases
        System.out.println("/***** TestCase *****/");
        test("babbd");
        test("abc");
        test("zaabbccbbaaz");

        // Test your own inputs
        System.out.println("Enter your own inputs:");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        sc.close();

        PalindromeString.printLongestPalindromeSubstring(str);
    }

    private static void test(String str) {
        System.out.println("---------- Input -----------");
        System.out.println(str);
        System.out.println("---------- Output ----------");
        PalindromeString.printLongestPalindromeSubstring(str);
        System.out.println("----------------------------");
    }
}
