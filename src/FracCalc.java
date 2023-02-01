/** Does basic calculator functions with two operands and one operator.
 * @author s-jwon
 */

import java.util.*;

public class FracCalc {
    public static void main(String[] args) {
        // TODO: Read the input from the user and call produceAnswer with an equation
        Scanner console = new Scanner(System.in);
        System.out.print("Input expression: (quit to exit)");
        String expression = console.nextLine();
        while (!expression.equals("quit")) {
            String answer = produceAnswer(expression);
            System.out.println(answer);
            System.out.print("Input expression: (quit to exit)");
            expression = console.nextLine();
        }
    }

    /** Reads input from user.
     *
     * @param input - Input that user types.
     * @return Final answer.
     */
    public static String produceAnswer(String input) {
        // TODO: Implement this function to produce the solution to the input
        Scanner read = new Scanner(input);
        String operand1 = read.next();
        String operator = read.next();
        String operand2 = read.next();
        int[] operand1Array = parseFraction(operand1);
        int[] operand2Array = parseFraction(operand2);
        int[] result = improperSimplify(operation(mixedToImproper(operand1Array), mixedToImproper(operand2Array), operator));
        return changeToNumber(result);
    }

    /** Converts operand in string to array.
     *
     * @param in - The inputed operand as a string.
     * @return The operand in array form.
     */
    public static int[] parseFraction(String in){
        //create integers for whole, numerator, denominator
        int whole, numerator, denominator;

        //find the index of "/" and "_"
        int posS = in.indexOf("/");
        int posU = in.indexOf("_");

        if (posS == -1){
            //whole number case
            whole = Integer.parseInt(in);
            numerator = 0;
            denominator = 1;
        } else if (posU == -1) {
            //fraction only case
            whole = 0;
            numerator = Integer.parseInt(in.substring(0, posS));
            denominator = Integer.parseInt(in.substring(posS + 1));
        } else {
            //mixed number case
            whole = Integer.parseInt(in.substring(0,posU));
            numerator = Integer.parseInt(in.substring(posU + 1, posS));
            denominator = Integer.parseInt(in.substring(posS+1));

        }
        if (whole < 0) {
            numerator = numerator * -1;
        }

        int[] out = {whole, numerator, denominator};

        //return the new array
        return out;
    }

    /** Changes mixed fraction to improper fraction.
     *
     * @param fraction - Mixed function as an array.
     * @return Improper fraction.
     */
    public static int[] mixedToImproper(int[] fraction) {
        fraction[1] = fraction[0] * fraction[2] + fraction[1];
        fraction[0] = 0;
        return fraction;
    }

    /** Changes improper fraction to mixed fraction.
     *
     * @param fraction - Improper fraction as an array.
     * @return Mixed fraction.
     */
    public static int[] improperSimplify(int[] fraction) {
        for (int i = Math.min(Math.abs(fraction[1]), Math.abs(fraction[2])); i > 0; i--) {
            if (fraction[1] % i == 0 && fraction[2] % i == 0) {
                fraction[1] /= i;
                fraction[2] /= i;
            }
        }
        if (Math.abs(fraction[1]) >= Math.abs(fraction[2])) {
            int remainder = fraction[1] % fraction[2];
            int whole = fraction[1] / fraction[2];
            fraction[0] += whole;
            fraction[1] = remainder;
        }
        if (fraction[1] == 0) fraction[2] = 0;
        if (fraction[1] < 0 && fraction[2] < 0) {
            fraction[1] = Math.abs(fraction[1]);
            fraction[2] = Math.abs(fraction[2]);
        }
        if (fraction[0] == 0 && fraction[1] == 0 && fraction[2] == 0) {
            fraction[0] = 0;
            fraction[1] = 0;
            fraction[2] = 0;
        }
        if (fraction[0] < 0) {
            fraction[1] = Math.abs(fraction[1]);
            fraction[2] = Math.abs(fraction[2]);
        }
        if (fraction[1] > 0 && fraction[2] < 0) {
            fraction[1] = fraction[1] * -1;
            fraction[2] = Math.abs(fraction[2]);
        }

        return fraction;
    }

    /** Changes array to string.
     *
     * @param fraction - Fraction as array.
     * @return Fraction as string.
     */
    public static String changeToNumber(int[] fraction) {
        if (fraction[0] == 0 && fraction[1] == 0) return "0";
        if (fraction[0] == 0) return fraction[1] + "/" + fraction[2];
        if (fraction[1] == 0) return "" + fraction[0];
        return fraction[0] + "_" + fraction[1] + "/" + fraction[2];
    }

    /** Solves math expression.
     *
     * @param first - First fraction
     * @param second - Second fraction
     * @param operator - Operator
     * @return answer as array.
     */
    public static int[] operation(int[] first, int[] second, String operator) {
        int[] result = new int[3];
        if (operator.equals("+")) {
            result[1] = first[1] * second[2] + first[2] * second[1];
            result[2] = first[2] * second[2];
        } else if (operator.equals("-")) {
            result[1] = first[1] * second[2] - first[2] * second[1];
            result[2] = first[2] * second[2];
        } else if (operator.equals("*")) {
            result[1] = first[1] * second[1];
            result[2] = first[2] * second[2];
        } else if (operator.equals("/")) {
            result[1] = first[1] * second[2];
            result[2] = first[2] * second[1];
        } else {
            result[0] = -1;
            result[1] = -1;
            result[2] = -1;
        }
        return result;
    }
}