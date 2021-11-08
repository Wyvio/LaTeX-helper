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
        result = checkForSymbols(result); // Substitutes in the latex for symbols

        // Finds just the part we need to convert into a fraction
        String fractionPart = result.substring(result.indexOf("(") + 1, result.indexOf("\\right)"));
        String[] pieces = fractionPart.split("\\/"); // Put into array to make fraction
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = pieces[i].trim();
        }
        result = result.substring(0, result.indexOf("(") + 1) + "\\frac{" + pieces[0] + "}{" + pieces[1] + "}" + result.substring(result.indexOf("\\right)"));
        
        System.out.println(result);
        
    }

    public static String checkForSymbols(String input) {
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