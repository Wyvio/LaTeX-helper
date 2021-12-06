public class EquationTree {
    private EquationNode overallRoot;
    private List<String> operators;
    
    public static void main(String[] args) {
        // Find + or - first
        EquationTree tree = new EquationTree();
        tree.makeTree("2+3");

    }

    public EquationTree() {
        this(new EquationNode(""));
    }
    
    public EquationTree(EquationNode overallRoot) {
        this.overallRoot = overallRoot;
        operators = new LinkedList<>();
        operators.add("+");
        operators.add("-");
    }

    public void makeTree(String input) {
        this.overallRoot = makeTreeHelper(input);
    }
    
    private EquationNode makeTreeHelper(String input) {
        if (input.isEmpty()) {
            return null;
        } else {
            EquationNode root;
            if (input.contains("+")) {
                int rootIndex = input.indexOf("+");
                root = new EquationNode("+");
                root.left = makeTreeHelper(input.substring(0, rootIndex));
                root.right = makeTreeHelper(input.substring(rootIndex + 1));
            } else {
                root = new EquationNode(input);
            }
            
            return root;
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
