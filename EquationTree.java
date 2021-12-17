import java.util.*;

public class EquationTree {
    private EquationNode overallRoot;
    private List<String> operators;
    
    /**
     * Main method for testing purposes
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Find + or - first
        EquationTree tree = new EquationTree();
        tree.makeTree("1+5^2/3-6");
        System.out.println(tree);
    }

    /**
     * Makes an empty tree
     */
    public EquationTree() {
        this(new EquationNode(""));
    }
    
    /**
     * Makes a tree with a specified root
     * 
     * @param overallRoot
     */
    public EquationTree(EquationNode overallRoot) {
        this.overallRoot = overallRoot;
        operators = new LinkedList<>();
        operators.add("+"); // checked first, higher in tree
        operators.add("-");
        operators.add("*");
        operators.add("/");
        operators.add("^");
    }

    public void makeTree(String input) {
        this.overallRoot = makeTreeHelper(input);
    }
    
    private EquationNode makeTreeHelper(String input) {
        if (input.isEmpty()) {
            return null;
        } else {
            EquationNode root = new EquationNode("");
            for (String operator : operators) {
                if (input.contains(operator)) {                    
                    int rootIndex = input.indexOf(operator);
                    root = new EquationNode(operator);
                    root.left = makeTreeHelper(input.substring(0, rootIndex));
                    root.right = makeTreeHelper(input.substring(rootIndex + 1));                    
                    break;
                } else {
                    root = new EquationNode(input);
                }
            }
            
            return root;
        }
    }
    
    public String toString() {
        List<String> result = new ArrayList<>();
        toStringHelper(result, overallRoot);
        
        String finalResult = "";
        for (String s : result) {
            finalResult += s;
        }
        return finalResult;
    }

    private void toStringHelper(List<String> result, EquationNode root) {
        if (root != null) {
            if (root.value.equals("/")) {
                result.add("\\frac{");
                toStringHelper(result, root.left);
                result.add("}{");
                toStringHelper(result, root.right);
                result.add("}");
            // } else if  (root.value.equals("int")) { //int_0^2(dfasdfsd)dx       \int_{2\pi}^{\pi} sodifsidjf \,dx
            //     result.add("\\int{");
            //     toStringHelper(result, root.left);
            //     result.add("}{");
            //     toStringHelper(result, root.right);
            //     result.add("}");
            } else {
                toStringHelper(result, root.left);
                result.add(root.value); 
                toStringHelper(result, root.right);
            }
        }
    }

    private static class EquationNode {
        public String value;
        public EquationNode left;
        public EquationNode right;

        public EquationNode(String value) {
            this.value = value;
        }
    }
}
