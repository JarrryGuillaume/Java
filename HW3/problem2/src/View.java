import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.List;

public class View {
    Scanner userinput = new Scanner(System.in);
    boolean isTest;
    PrintStream printStream;
    OutputStream os;

    View(){
        userinput = new Scanner(System.in);
        isTest = false;
        printStream = System.out;
    }

    View(String inputFile){
        File file = new File(inputFile);
        try {
            userinput = new Scanner(file);
            isTest = true;
        } catch (FileNotFoundException e) {
        }
        os = new ByteArrayOutputStream();
        printStream = new PrintStream(os, true, StandardCharsets.UTF_8);
    }

    /* Input a string line from the console */
    String getUserInput(String prompt) {
        print(prompt);
        try {
            String nextLine = userinput.nextLine();
            if (isTest) {
                println(nextLine);
            }
            return nextLine;
        } catch (final NoSuchElementException ex) {
            if (isTest) {
                println("");
            }
            return "exit";
        }
    }
    
    String getOutput() {
        return os.toString();
    }

    /* An alias of System.out.println. */
    void println(Object object) {
        printStream.println(object);
    }

    void print(Object object) {
        printStream.print(object);
    }
}
class PostView extends View {
    PostView() {
        super();
    }

    PostView(String input) {
        super(input);
    }

    List getPost(String prompt) {
        String title;
        String advertising;
        String content;
        String entireContent = "";

        List ret = new ArrayList();
        println("-----------------------------------");
        println(prompt);
        print("* Title : ");
        title = userinput.nextLine();
        if(isTest) {
            println(title);
        }
        print("* Advertising (yes/no) : ");
        advertising = userinput.nextLine();
        if(isTest) {
            println(advertising);
        }
        println("* Content : ");
        print("> ");
        content = userinput.nextLine();
        if(isTest) {
            println(content);
        }
        entireContent += content + "\n";
        while(!content.isEmpty()){
            print("> ");
            try {
                content = userinput.nextLine();
            } catch (final NoSuchElementException ex) {
                content = "";
            }
            if(isTest) {
                println(content);
            }
            entireContent += content + "\n";
        }
        println("-----------------------------------");
        ret.add(title);
        ret.add(advertising);
        ret.add(entireContent);

        return ret;
    }
}
class AuthView extends View{
    AuthView() {
        super();
    }

    AuthView(String input) {
        super(input);
    }

    @Override
    String getUserInput(String prompt) {
        String id, passwd;
        print(prompt);
        print("id : ");
        try {
            id = userinput.nextLine();
        } catch (final NoSuchElementException ex) {
            id = "";
        }
        if (isTest)
            println(id);
        print("passwd : ");
        try {
            passwd = userinput.nextLine();
        } catch (final NoSuchElementException ex) {
            passwd = "";
        }
        if (isTest)
            println(passwd);
        return id + "\n" + passwd;
    }
}
class Pair<K,V>{
    K key;
    V value;
    Pair(K key, V value){
        this.key = key;
        this.value = value;
    }
}
