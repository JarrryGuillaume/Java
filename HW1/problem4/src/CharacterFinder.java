public class CharacterFinder {
    public static void findCharacter(String str) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char[] repertory = str.toCharArray();


        for (int i=0; i < alphabet.length(); i++) {
            int appearance = 0;
            int first = 0;
            int last = 0;
            for (int j=0; j < repertory.length; j++) {
                if (repertory[j] == alphabet.charAt(i)) {
                    if (appearance == 0) {
                        last = j;
                        appearance++;
                        first = j;
                    }
                    last = j;
                }
            }
            if (!(appearance == 0)) System.out.printf("%c: %d %d\n", alphabet.charAt(i), first, last);
        }
    }

    private static void printIdx(char character, int startIdx, int endIdx) {
        System.out.printf("%c: %d %d\n", character, startIdx, endIdx);
    }
}
