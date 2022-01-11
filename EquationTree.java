import java.util.*;

public class EquationTree {
    private EquationNode overallRoot;
    private List<String> operators;

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
        operators.add("_");
    }

    /**
     * Makes an equation tree from a string input
     *
     * @param input
     */
    public void makeTree(String input) {
        this.overallRoot = makeTreeHelper(input);
    }

    /**
     * makes tree :))
     *
     * @param input
     * @return
     */
    private EquationNode makeTreeHelper(String input) {
        input = input.trim();
        if (input.isEmpty()) {
            return null;
        } else if (isBaseCase("|", "\\|", input)) {
            EquationNode root = new EquationNode("|");
            root.right = new EquationNode("\\|");
            root.left = makeTreeHelper(input.substring(1, input.length() - 2));
            return root;
        } else if (isBaseCase("[", "]", input)) {
            EquationNode root = new EquationNode("[");
            root.right = new EquationNode("]");
            root.left = makeTreeHelper(input.substring(1, input.length() - 1));
            return root;
        } else if (isBaseCase("{", "}", input)) {
            EquationNode root = new EquationNode("{");
            root.right = new EquationNode("}");
            root.left = makeTreeHelper(input.substring(1, input.length() - 1));
            return root;
        } else if (isBaseCase("(", ")", input)) {
            EquationNode root = new EquationNode("(");
            root.right = new EquationNode(")");
            root.left = makeTreeHelper(input.substring(1, input.length() - 1));
            return root;
        } else if (isBaseCase("sqrt(", ")", input)) {
            EquationNode root = new EquationNode("sqrt(");
            root.right = new EquationNode(")");
            root.left = makeTreeHelper(input.substring(5, input.length() - 1));
            return root;
        } else {
            EquationNode root = new EquationNode("");
            boolean isSplit = false;
            int numRightParentheses = 0;
            int numRightSqrts = 0;
            int numRightCBrackets = 0;
            int numRightBrackets = 0;
            int numRightAbs = 0;
            for (String operator : operators) {
                if (input.contains(operator)) {
                    for (int i = 0; i < input.length(); i++) {
                        String c = "" + input.charAt(i);
                        if (input.substring(i).indexOf("sqrt(") == 0) {
                            numRightSqrts++;
                            numRightParentheses++;
                            i += 4; // Skip to end of "sqrt(" so it is inside next
                        } else if (c.equals("|")) {
                            numRightAbs++;
                        } else if (c.equals("\\|")) {
                            numRightAbs--;
                        } else if (c.equals("[")) {
                            numRightBrackets++;
                        } else if (c.equals("]")) {
                            numRightBrackets--;
                        } else if (c.equals("{")) {
                            numRightCBrackets++;
                        } else if (c.equals("}")) {
                            numRightCBrackets--;
                        } else if (c.equals("(")) {
                            numRightParentheses++;
                        } else if (c.equals(")")) {
                            numRightParentheses--;
                            if (numRightSqrts > 0) {
                                numRightSqrts--;
                            }
                        } else if (c.equals(operator) && numRightParentheses == 0 && numRightCBrackets == 0 && numRightBrackets == 0 && numRightAbs == 0) {
                            root = new EquationNode(operator);
                            root.left = makeTreeHelper(input.substring(0, i));
                            root.right = makeTreeHelper(input.substring(i + 1));
                            isSplit = true;
                            break;
                        }                     
                    }
                } else {
                    root = new EquationNode(input);
                }
                if (isSplit) {
                    break;
                }
            }
            return root;
        }
    }

    /**
     * 
     * @param symbol opening symbol e.g. sqrt(
     *               assumes that ) will be closing symbol
     * @param input input text to be parsed 
     * @return true when input has function symbols at the start and end e.g. sqrt(...), false otherwise
     */
    private boolean isBaseCase(String symbol, String endSymbol, String input) {
        if (input.indexOf(symbol) == 0 && input.indexOf(endSymbol) == (input.length() - endSymbol.length())) {
            int numRightParentheses = 1;

            int i = symbol.length();
            while (i < input.length() && numRightParentheses > 0) {
                String c = "" + input.charAt(i);
                if (c.equals("(")) {
                    numRightParentheses++;
                } else if (c.equals(")")) {
                    numRightParentheses--;
                }

                i++;
            }

            return i == input.length();

        } else {
            return false;
        }
    }

    /**
     * Helper method
     * Check if this is an integral base case
     * If so, return true (in makeTree, add the nodes)
     */

    /**
     * Prints out LaTeX form without spaces in between
     */
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
            } else if (root.value.equals("|")) {
                    result.add("\\left|");
                    toStringHelper(result, root.left);
                    result.add("\\right|");
            } else if (root.value.equals("[")) {
                    result.add("\\left[");
                    toStringHelper(result, root.left);
                    result.add("\\right]");
            } else if (root.value.equals("{")) {
                    result.add("{");
                    toStringHelper(result, root.left);
                    result.add("}");
            } else if (root.value.equals("(")) {
                    result.add("\\left(");
                    toStringHelper(result, root.left);
                    result.add("\\right)");
            } else if (root.value.equals("sqrt(")) {
                    result.add("\\sqrt{");
                    toStringHelper(result, root.left);
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