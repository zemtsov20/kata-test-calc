package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String... args) throws Exception {
        System.out.print("Enter the equation: ");
        System.out.println("Result: " + calc(new Scanner(System.in).nextLine()));
    }

    public static String calc(String input) throws Exception {
        int lOperand, rOperand, result;
        boolean arabic = isArabicAndCorrect(input);
        String[] allContent = input.split(" ");

        if (arabic) {
            lOperand = Integer.parseInt(allContent[0]);
            rOperand = Integer.parseInt(allContent[2]);
        }
        else {
            lOperand = fromRoman(allContent[0]);
            rOperand = fromRoman(allContent[2]);
        }
        if (lOperand > 10 || rOperand > 10)
            throw new Exception("Too large numbers!");
        result = switch (allContent[1]) {
            case "+" -> lOperand + rOperand;
            case "-" -> lOperand - rOperand;
            case "/" -> lOperand / rOperand;
            case "*" -> lOperand * rOperand;
            default -> throw new Exception("Wrong operation!");
        };
        if (arabic)
            return Integer.toString(result);
        else
            return toRoman(result);
    }

    private static int fromRoman(String romanNumber) throws Exception {
        List<String> romanNums = new ArrayList<>(List.of("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"));
        int result = romanNums.indexOf(romanNumber) + 1;
        if (result == 0)
            throw new Exception("Wrong Roman number!");

        return result;
    }
    private static String toRoman(int arabicNumber) throws Exception {
        if (arabicNumber <= 0)
            throw new Exception("No such numerals in Roman system!");

        StringBuilder result = new StringBuilder();
        int[] romanNumbers = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanChars = {"C", "XC", "L", "XL", "X","IX", "V", "IV", "I"};

        for (int i = 0; i < romanNumbers.length; i++) {
            while (arabicNumber - romanNumbers[i] >= 0) {
                result.append(romanChars[i]);
                arabicNumber = arabicNumber - romanNumbers[i];
            }
        }

        return result.toString();
    }

    private static boolean isArabicAndCorrect(String input) throws Exception {
        if (!Pattern.matches("\\d{1,2} [+\\-/*] \\d{1,2}", input)) {
            if (!Pattern.matches("[IVX]{1,4} [+\\-/*] [IVX]{1,4}", input))
                throw new Exception("Wrong equation!");
            else
                return false;
        }

        return true;
    }
}
