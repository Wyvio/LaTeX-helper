import java.util.*;

public class EquationTreeClient {
    /**
     * Main method for testing purposes
     * 
     * @param args
     */
    public static void main(String[] args) {   
        Scanner console = new Scanner(System.in);
      
        System.out.print("What do you want to convert to LaTeX code (q to quit) : ");
        String input = console.nextLine();
        while (!input.equals("q")) {
            test(input); 
            System.out.print("What do you want to convert to LaTeX code (q to quit) : ");    
            input = console.nextLine();         
        }      
    }

    public static void test(String testCase) {
        EquationTree tree = new EquationTree();
        System.out.print(testCase + "\t->\t");
        tree.makeTree(testCase);
        System.out.println(tree);
    }
}