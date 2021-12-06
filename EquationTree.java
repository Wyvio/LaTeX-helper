import java.util.*;

public class EquationTree {
    private EquationNode overallRoot;
    private List<String> operators;
    
    public static void main(String[] args) {
        // Find + or - first
        EquationTree tree = new EquationTree();
        tree.makeTree("1+5^2*3-6");
        System.out.println(tree);

    }

    public EquationTree() {
        this(new EquationNode(""));
    }
    
    public EquationTree(EquationNode overallRoot) {
        this.overallRoot = overallRoot;
        operators = new LinkedList<>();
        operators.add("+");
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
        toStringHelper(result, overallRoot, 0);
        
        String finalResult = "";
        for (String s : result) {
            finalResult += s;
        }
        return finalResult;
    }

    private void toStringHelper(List<String> result, EquationNode root, int index) {
        if (root != null) {
            result.add(index, root.value);
            toStringHelper(result, root.left, result.indexOf(root.value));
            toStringHelper(result, root.right, result.indexOf(root.value) + 1);
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
