import java.util.*;
public class PalindromeString {
    public static void printLongestPalindromeSubstring(String s) {
        String palyndrome = new String();
        for (int i=0; i<s.length(); i++){
            for (int j=0; j<s.length()-i; j++)  {
                String extract = s.substring(j, j+i+1);
                char[] comparator = new char[i+1];
                for (int index=0; index< i+1; index++) {
                    comparator[index] = extract.charAt(i - index);
                }
                String test = new String(comparator);
                if (test.equals(extract)) {
                    palyndrome = extract;
                }
            }
        }
        if (palyndrome.length() > 1) {
            System.out.println(palyndrome);
        } else {
            System.out.println(s.charAt(0));
        }

    }

}
