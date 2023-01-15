
public class IPAddress {
    public static void countValidAddress(String str) {
        int ValidNum = 0;
        String[] adresses = generateIPs(str);
        for (String adress: adresses) {
            ValidNum += checkAdress(adress);
        }
        System.out.println(ValidNum);
    }

    private static String[] generateIPs(String str) {
        int n = str.length();
        int dim = count(n);
        String[] list = new String[dim];
        int counter = 0;
        for (int i=1; i<4; i++) {
            for (int j=1; j<4; j++) {
                for (int l=1; l<4; l++) {
                    if (l+j+i < n) {
                        list[counter] = str.substring(0, i) + "." + str.substring(i, i+j) + "." + str.substring(i+j, i+j+l) + "." + str.substring(i+j+l);
                        counter++;
                    }
                }
            }
        }
        return list;
    }

    private static int count(int n) {
        int num=0;
        for (int i=1; i<4; i++) {
            for (int j = 1; j < 4; j++) {
                for (int l = 1; l < 4; l++) {
                    if (l + j + i < n) {
                        num += 1;
                    }
                }
            }
        }
        return num;
    }


    private static int checkAdress(String str) {
        for (int i=0; i<10; i++ ) {
            if (str.contains(".0" + i) || str.contains("00")) {
                return 0;
            }
        }
        str += '.';
        String sint = "";
        for (int i=0; i<str.length(); i++) {
            if (str.charAt(i) == '.') {
                if (Long.parseLong(sint) > 255) {
                    return 0;
                }
                sint = "";
            } else {
                sint += str.charAt(i);
            }
        }
        return 1;
    }
}
