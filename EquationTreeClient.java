import java.util.*;

public class EquationTreeClient {
    /**
     * Main method for testing purposes
     * 
     * @param args
     */
    public static void main(String[] args) {   
        test("1+5^2/3-6");        
    }

    public static void test(String testCase) {
        EquationTree tree = new EquationTree();
        System.out.print(testCase + "\t->\t");
        tree.makeTree(testCase);
        System.out.println(tree);
    }
}