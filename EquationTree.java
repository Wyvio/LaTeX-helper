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
        } else if (isBaseCase("int_", ")", input)) {
            EquationNode root = new EquationNode("int");
            root.right = makeTreeHelper(input.substring(input.indexOf(" (") + 2, input.length() - 1));
            root.left = new EquationNode(input.substring(4, input.indexOf("^")));
            root.left.right = new EquationNode(input.substring(input.indexOf("^") + 1, input.indexOf(" (")));
            return root;
        } else {
            EquationNode root = new EquationNode("");
            boolean isSplit = false;
            int numParentheses = 0;
            int numSqrts = 0;
            int numCBrackets = 0;
            int numBrackets = 0;
            int numAbs = 0;
            int numInt = 0;
            for (String operator : operators) {
                if (input.contains(operator)) {
                    for (int i = 0; i < input.length(); i++) {
                        String c = "" + input.charAt(i);
                        if (input.substring(i).indexOf("sqrt(") == 0) {
                            numSqrts++;
                            numParentheses++;
                            i += 4; // Skip to end of "sqrt(" so it is inside next
                        } else if (input.substring(i).indexOf("int_") == 0) {
                            numInt++;
                            numParentheses++;
                            i += input.indexOf("("); // Skip to end of "int_...^... (" so it is inside next
                        } else if (c.equals("|")) {
                            numAbs++;
                        } else if (c.equals("\\|")) {
                            numAbs--;
                        } else if (c.equals("[")) {
                            numBrackets++;
                        } else if (c.equals("]")) {
                            numBrackets--;
                        } else if (c.equals("{")) {
                            numCBrackets++;
                        } else if (c.equals("}")) {
                            numCBrackets--;
                        } else if (c.equals("(")) {
                            numParentheses++;
                        } else if (c.equals(")")) {
                            numParentheses--;
                            if (numSqrts > 0) {
                                numSqrts--;
                            }
                            if (numInt > 0) {
                                numInt--;
                            }
                        } else if (c.equals(operator) && numParentheses == 0 && numCBrackets == 0 && numBrackets == 0 && numAbs == 0) {
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
        if (input.indexOf(symbol) == 0 && input.lastIndexOf(endSymbol) == (input.length() - endSymbol.length())) {
            int numParentheses = 1;

            int i = symbol.length();
            while (i < input.length() && numParentheses > 0) {
                String c = "" + input.charAt(i);
                if (c.equals("(")) {
                    numParentheses++;
                } else if (c.equals(")")) {
                    numParentheses--;
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
     *      starts with "int_"
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
            } else if (root.value.equals("int")) { //int_0^2(dfasdfsd)dx       \int_{2\pi}^{\pi} sodifsidjf \,dx
                result.add("\\int_{");
                result.add(root.left.value);
                result.add("}^{");
                result.add(root.left.right.value);
                result.add("} ");
                toStringHelper(result, root.right);
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