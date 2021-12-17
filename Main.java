// very old
// do not use
import java.util.*;

public class Main {
    
    static Map<String, String> symbols = new TreeMap<>();
    public static void main(String[] args) {
        symbols.put("(", "\\left(");
        symbols.put(")", "\\right)");

        Scanner console = new Scanner(System.in);
        System.out.print("Write a fraction here: ");
        String input = console.nextLine(); // Take in the input
        input = input.trim();

        String result = input;     // Make another string so we can do random stuff with it  
        result = replaceSymbols(result); // Substitutes in the latex for symbols


        // Look for parens
        if (result.contains("(") && result.contains(")")) {
            String insideParens = result.substring(result.indexOf("(") + 1, result.indexOf("\\right)"));
            if (insideParens.contains("/")) {
                result = result.substring(0, result.indexOf("(") + 1) + convertToFraction(insideParens) + result.substring(result.indexOf("\\right)"));
            }
        } 
        if (result.contains("/")) {
            result = convertToFraction(result);
        }
        
        System.out.println(result);
        
    }

    public static void trimStringArray(String[] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = pieces[i].trim();
        }
    }

    /**
     * Converts string to LaTeX function
     * @param fractionPart
     * @return
     */
    public static String convertToFraction(String fractionPart) {
        String[] pieces = fractionPart.split("\\/"); // Put into array to make fraction
        trimStringArray(pieces);
        return "\\frac{" + pieces[0] + "}{" + pieces[1] + "}";
    }

    /**
     * Replaces all symbols in input with LaTeX if they exist
     * @param input 
     * @return input with LaTeX symbols inserted
     *          Example:
     *          input:  ( 2 )
     *          output: \left( 2 \right)
     */
    public static String replaceSymbols(String input) {
        String result = input;
        for (String symbol : symbols.keySet()) {
            int index = result.indexOf(symbol);
            
            if (index >= 0) {
                result = result.substring(0, index) + symbols.get(symbol) + result.substring(index + 1);
            }
        }

        return result;
    }

}