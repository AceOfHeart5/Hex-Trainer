/*
AUTHOR: EVAN CONWAY

This program is designed to hep the user learn hex and binary. It presents the user with a number in hex, binary, or
decimal and the user must type in the hex, binary, or decimal equivalent. Settings are configurable through simple
commands.
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class main {
    private static Scanner keyboard = new Scanner(System.in);
    private static String input;
    private static Random rnd = new Random(System.currentTimeMillis());

    private static final String hexChar = "H";
    private static final String binaryChar = "B";
    private static final String decimalChar = "D";
    private static final String signedChar = "S";
    private static final String unsignedChar = "U";
    private static final String randomSignChar = "R";

    private static final String commandChar = "#";
    private static final String commandHelp = "HELP";
    private static final String commandSetBit = "BIT";
    private static final String commandSetShow = "SHOW";
    private static final String commandSetAnswer = "ANSWER";
    private static final String commandSetSign = "SIGN";
    private static final String commandQuit = "QUIT";

    private static final String commandsList =
        "\nCommands: \n" +
        commandChar + commandHelp + " : List commandsList.\n" +
        commandChar + commandSetBit + " X : Set Bit system to X. X must be an integer divisible by 4.\n" +
        commandChar + commandSetShow + " X : Set show to X, where X is H, B, or D for hex, binary, and decimal.\n" +
        commandChar + commandSetAnswer + " X : Set answer to X, where X is H, B, or D for hex, binary, and decimal.\n" +
        commandChar + commandSetSign + " X : Set sign to X, where X is S, U, or R for signed, unsigned, and random.\n" +
        commandChar + commandQuit + " : Quit the program.\n";

    private static int numOfBits = 4;// must be divisible by 4
    private static int[] currentNumber = new int[numOfBits/4];
    private static String show = hexChar;
    private static String answer = decimalChar;
    private static String sign = unsignedChar;
    private static String numberSign = sign;
    private final static String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "A", "B", "C", "D", "E", "F"};
    private final static String[] binary = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
            "1000", "1001", "1010", "1011", "1100", "1101", "1111"};
    private final static int[] decimal = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static String response;
    private static String numberString;
    private static boolean generateNewNumber = true;
    private static boolean running = true;

    public static void main(String[] args) {

        System.out.println("\nHex Trainer\n" + commandsList);

        while (running) {
            switch (show) {
                case hexChar:
                    System.out.print("Hex: ");
                    break;
                case binaryChar:
                    System.out.print("Binary: ");
                    break;
                case decimalChar:
                    System.out.print("Decimal: ");
                    break;
            }
            if (generateNewNumber) System.out.println(generateNumber());
            else System.out.println(numberString);
            switch (answer) {
                case hexChar:
                    System.out.println("Hex: ");
                    break;
                case binaryChar:
                    System.out.println("Binary: ");
                    break;
                case decimalChar:
                    System.out.println("Decimal: ");
                    break;
            }
            input = keyboard.nextLine();
            input = removeTrailingSpaces(input);
            if (input.charAt(0) == '#') response = command(input);
            else response = answer(input);
            System.out.println(response);
        }
    }

    private static String command(String input) {
        String result;
        String[] argsArray = commandArgs(input);
        if (argsArray.length > 2) result = "Invalid command, too many arguments.";
        else {
            switch (argsArray[0].toUpperCase()) {
                case commandHelp:
                    result = commandsList;
                    break;
                case commandSetBit:
                    int possBit;
                    try {
                        possBit = Integer.parseInt(argsArray[1]);
                        if (possBit%4 == 0) {
                            numOfBits = possBit;
                            result = "Bit system set to " + possBit;
                        } else result = "Number must be integer divisible by 4.";
                    } catch (Exception e) {
                        result = "Number must be integer divisible by 4.";
                    }
                    break;
                case commandSetShow:
                    result = "Must enter H, B, or D after command.";
                    if (argsArray[1].equalsIgnoreCase(hexChar)) {
                        show = hexChar;
                        result = "Show Hex";
                    }
                    if (argsArray[1].equalsIgnoreCase(binaryChar)) {
                        show = binaryChar;
                        result = "Show Binary";
                    }
                    if (argsArray[1].equalsIgnoreCase(decimalChar)) {
                        show = decimalChar;
                        result = "Show Decimal";
                    }
                    break;
                case commandSetAnswer:
                    result = "Must enter H, B, or D after command.";
                    if (argsArray[1].equalsIgnoreCase(hexChar)) {
                        answer = hexChar;
                        result = "Answer in Hex";
                    }
                    if (argsArray[1].equalsIgnoreCase(binaryChar)) {
                        answer = binaryChar;
                        result = "Answer in Binary";
                    }
                    if (argsArray[1].equalsIgnoreCase(decimalChar)) {
                        answer = decimalChar;
                        result = "Answer in Decimal";
                    }
                    break;
                case commandSetSign:
                    result = "Must enter S, U, or R after command";
                    if (argsArray[1].equalsIgnoreCase(signedChar)) {
                        sign = signedChar;
                        numberSign = signedChar;
                        result = "Sign set to signed";
                    }
                    if (argsArray[1].equalsIgnoreCase(unsignedChar)) {
                        sign = unsignedChar;
                        numberSign = unsignedChar;
                        result = "Sign set to unsigned";
                    }
                    if (argsArray[1].equalsIgnoreCase(randomSignChar)) {
                        sign = randomSignChar;
                        numberSign = randomSignChar;
                        result = "Sign set to random";
                    }
                    break;
                case commandQuit:
                    running = false;
                    result = "\nGoodbye\n";
                    break;
                default:
                    result = "Unknown command.";
                    break;
            }
        }
        return result;
    }

    private static String answer(String input) {
        String result;
        input = input.replaceAll("\\s+","");
        String check = numberString.replaceAll("\\s+","");
        if (input.equalsIgnoreCase(check)) {
            result = "correct";
            generateNewNumber = true;
        } else result = "incorrect";
        return result;
    }

    private static String removeTrailingSpaces(String input) {
        while (input.charAt(input.length() - 1) == ' ') input = input.substring(0, input.length() - 2);
        return input;
    }

    private static String[] commandArgs(String input) {
        ArrayList<String> cmdArgs = new ArrayList();
        String word = "";
        // start at 1 to skip over # symbol
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) != ' ') word += input.charAt(i);
            else {
                if (!word.isEmpty()) cmdArgs.add(word);
                word = "";
            }
        }
        return (String[]) cmdArgs.toArray();
    }

    private static String generateNumber() {
        generateNewNumber = false;
        numberString = "";
        int numberDecimal = 0;
        if (sign.equals(randomSignChar)) {
            if (rnd.nextInt(1) == 1) numberSign = signedChar;
            else numberSign = unsignedChar;
        }
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = rnd.nextInt(hex.length);
            numberDecimal += decimal[currentNumber[i]] * Math.pow(16, i);
            switch (show) {
                case hexChar:
                    numberString += hex[currentNumber[i]];
                    break;
                case binaryChar:
                    numberString += binary[currentNumber[i]];
                    if (i != currentNumber.length - 1) numberString += ' ';
                    break;
                case decimalChar:
                    if (i == currentNumber.length - 1) {
                        if (numberSign.equals(signedChar)) numberDecimal -= Math.pow(2, numOfBits);
                        numberString = Integer.toString(numberDecimal);
                    }
                    break;
            }
        }
        switch (sign) {
            case signedChar:
                numberSign = signedChar;
                numberString += " (signed)";
                break;
            case unsignedChar:
                numberSign = unsignedChar;
                numberString += " (unsigned)";
                break;
            case randomSignChar:
                if (numberSign.equals(signedChar)) numberString += " (signed)";
                else numberString += " (unsigned)";
                break;
        }
        return numberString;
    }
}
