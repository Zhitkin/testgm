import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String inputString;
        do {
            inputString = in.nextLine();
            String inputResult = calc(inputString);
            System.out.println(inputResult);
        } while(!inputString.equals(""));
    }
    public static String calc(String input) throws IOException {
        String mathString = input.replaceAll(" ","");
        validateQuantityOperands(mathString);
        validateQuantityOperators(mathString);
        validateInputSymbols(mathString);
        String[] operands = mathString.split("[+\\-/*]");
        checkOnlyOneSystem(operands[0],operands[1]);
        boolean isRoman = checkRoman(operands[0]);
        String operator = getOperator(mathString);
        if (isRoman) {
            int a = romanToInt(operands[0]);
            int b = romanToInt(operands[1]);
            int sum = calculate(a, b, operator);
            if(sum<1){
                throw new IOException("Ошибка, в римской системе исчесления нет отрицательных чисел");
            }
            int t = Math.round(sum);
            String resultRoman = intToRoman(t);
            return  resultRoman;
        } else {
            int k = parseInt(operands[0]);
            int p = parseInt(operands[1]);
            int sum1 = calculate(k,p,operator);
            String resultArabic = String.valueOf(sum1);
            return resultArabic;
        }
    }
    public static String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        int times = 0;
        String[] romans = new String[] { "I", "IV", "V", "IX", "X", "XL", "L",
                "XC", "C", "CD", "D", "CM", "M" };
        int[] ints = new int[] { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500,
                900, 1000 };
        for (int i = ints.length - 1; i >= 0; i--) {
            times = num / ints[i];
            num %= ints[i];
            while (times > 0) {
                sb.append(romans[i]);
                times--;
            }
        }
        return sb.toString();
    }
    public static void validateInputSymbols (String input) throws IOException {
        Pattern patternSymbols = Pattern.compile("^[0-9ivxIVX+*/\\- ]+$");
        Matcher symbols = patternSymbols.matcher(input);
        if (!symbols.matches()) throw new IOException("Ошибка, введены некорректрные символы");
    }
    public static boolean checkRoman (String input) throws IOException {
        Pattern patternSymbols = Pattern.compile("^[ivxIVX]+$");
        Matcher matcher = patternSymbols.matcher(input);
        boolean roman = matcher.matches();
        return roman;
    }
    public static boolean checkArabic (String input) throws IOException {
        Pattern patternSymbols = Pattern.compile("^[0-9]+$");
        Matcher matcher = patternSymbols.matcher(input);
        boolean arabic = matcher.matches();
        return arabic;
    }
    public static void checkOnlyOneSystem (String first, String second) throws IOException {
        if(checkRoman(first) && checkArabic(second)){
            throw new IOException("Ошибка,операнды принадлежат к разным числовым системам");
        }
        if(checkArabic(first) && checkRoman(second)){
            throw new IOException("Ошибка,операнды принадлежат к разным числовым системам");
        }
    }
    public static void validateQuantityOperands (String input) throws IOException {
        String stringWithoutSpace = input.trim().replaceAll("[+\\-/*]", " ").replaceAll(" +", " ");
        String[] operandsWithoutSpace = stringWithoutSpace.split(" ");
        if (Objects.equals(operandsWithoutSpace[0], "")) throw new IOException("Ошибка, неверный формат примера. Не математическая операция");
        if (operandsWithoutSpace.length != 2) throw new IOException("Ошибка, неверный формат примера. " +
                "Введенное количество операндов - " + operandsWithoutSpace.length + ". Правильное количество операндов - 2");
    }
    public static void validateQuantityOperators (String input) throws IOException {
        Pattern patternOperators = Pattern.compile("[+\\-*/]");
        Matcher countOperandsMather = patternOperators.matcher(input);
        int countOperators = 0;
        while (countOperandsMather.find()) {
            countOperators++;
        }
        if (countOperators != 1) throw new IOException("Ошибка, неверный формат примера. " +
                "Введенное количество операторов - " + countOperators + ". Правильное количество операторов - 1");
    }


    public static int romanToInt(String romanOperator) {
        int ans = 0, num = 0;
        for (int i = romanOperator.length()-1; i >= 0; i--) {
            switch (romanOperator.charAt(i)) {
                case 'I', 'i' -> num = 1;
                case 'V', 'v' -> num = 5;
                case 'X', 'x' -> num = 10;
                case 'L', 'l' -> num = 50;
                case 'C', 'c' -> num = 100;
                case 'D', 'd' -> num = 500;
                case 'M', 'm' -> num = 1000;
            }
            if (4 * num < ans) ans -= num;
            else ans += num;
        }
        return ans;
    }
    public static String getOperator (String input) throws IOException {
        Pattern patternOperator = Pattern.compile("[+\\-*/]");
        Matcher matcherOperator = patternOperator.matcher(input);
        if (matcherOperator.find()) {
            return matcherOperator.group();
        }
        throw new IOException("Ошибка, неверный формат примера. Операторы не найдены");
    }
    public static int calculate(int a,int b,String operator){
        int result = 0;
        switch (operator){
            case "+":
                result = a+b;
                break;
            case "-":
                result = a-b;
                break;
            case "*":
                result = a*b;
                break;
            case "/":
                result = a/b;
                break;

        }
        return result;
    }
}

