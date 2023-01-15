public class DrawingFigure {
    public static void drawFigure(int n) {
        String blank = " ";
        String ast = "*";
        System.out.println(blank.repeat(n-1) + ast);

        for (int i=0; i<n-2; i++) {

            System.out.println(blank.repeat(n-2 -i) + ast + blank.repeat(i*2 + 1) + ast);
        }
        if (n>1) System.out.println(ast.repeat(2*n - 1));
    }
}
