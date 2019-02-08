/*
AUTHOR: EVAN CONWAY

This program is designed to hep the user learn HEX and BINARY. It presents the user with a number in HEX, BINARY, or
decimal and the user must type in the HEX, BINARY, or decimal equivalent. Settings are configurable through simple
commands.
 */

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
    private static final String commandSetNibbles = "NIBBLES";
    private static final String commandSetShow = "SHOW";
    private static final String commandSetAnswer = "ANSWER";
    private static final String commandSetSign = "SIGN";
    private static final String commandGiveAnswer = "GIVEANSWER";
    private static final String commandQuit = "QUIT";

    private static final String commandsList =
            "\nCommands: \n" +
                    commandChar + commandHelp + " : List commandsList.\n" +
                    commandChar + commandSetNibbles + " X : Set Nibbles to X. 1 Nibble is 4 bits.\n" +
                    commandChar + commandSetShow + " X : Set show to X, where X is H, B, or D for hex, binary, and decimal.\n" +
                    commandChar + commandSetAnswer + " X : Set answer to X, where X is H, B, or D for hex, binary, and decimal.\n" +
                    commandChar + commandSetSign + " X : Set sign to X, where X is S, U, or R for signed, unsigned, and random.\n" +
                    commandChar + commandGiveAnswer + " : Display the answer.\n" +
                    commandChar + commandQuit + " : Quit the program.\n";

    private static int nibbles = 4;// must be divisible by 4
    private static String show = hexChar;
    private static String answer = decimalChar;
    private static String sign = signedChar;
    private static String response;
    private static boolean answeredCorrect = true;
    private static boolean running = true;

    private static HexNumber number;

    public static void main(String[] args) {

        System.out.println("\nHex Trainer\n" + commandsList);

        while (running) {
            if (answeredCorrect) number = generateNumber();
            switch (show) {
                case hexChar:
                    System.out.print("Hex: " + number.getHexString());
                    break;
                case binaryChar:
                    System.out.print("Binary: " + number.getBinaryString());
                    break;
                case decimalChar:
                    System.out.print("Decimal: " + number.getDecimalString());
                    break;
            }
            if (number.getSign()) System.out.println(" (signed)");
            else System.out.println(" (unsigned)");
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
        String result = new String();
        String[] argsArray = commandArgs(input);
        if (argsArray.length > 2) result = "Invalid command, too many arguments.";
        else {
            switch (argsArray[0].toUpperCase()) {
                case commandHelp:
                    result = commandsList;
                    break;
                case commandSetNibbles:
                    int possBit;
                    try {
                        possBit = Integer.parseInt(argsArray[1]);
                        if (possBit > 0) {
                            nibbles = possBit;
                            result = "Nibbles set to " + possBit;
                            number = generateNumber();
                        } else result = "Number must be integer greater than 0.";
                    } catch (Exception e) {
                        result = "Number must be integer greater than 0.";
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
                    result = "Argument not recognized. Must enter S, U, or R after command";
                    if (argsArray[1].equalsIgnoreCase(signedChar)) {
                        sign = signedChar;
                        result = "Sign set to signed";
                        number = generateNumber();
                    }
                    if (argsArray[1].equalsIgnoreCase(unsignedChar)) {
                        sign = unsignedChar;
                        result = "Sign set to unsigned";
                        number = generateNumber();
                    }
                    if (argsArray[1].equalsIgnoreCase(randomSignChar)) {
                        sign = randomSignChar;
                        result = "Sign set to random";
                        number = generateNumber();
                    }
                    break;
                case commandGiveAnswer:
                    switch(answer) {
                        case hexChar:
                            result = number.getHexString();
                            break;
                        case binaryChar:
                            result = number.getBinaryString();
                            break;
                        case decimalChar:
                            result = number.getDecimalString();
                            break;
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
        input = input.replaceAll("\\s+", "");
        String check = new String();
        switch (answer) {
            case hexChar:
                check = number.getHexString().replaceAll("\\s+", "");
                break;
            case binaryChar:
                check = number.getBinaryString().replaceAll("\\s+", "");
                break;
            case decimalChar:
                check = number.getDecimalString().replaceAll("\\s+", "");
                break;
        }
        if (input.equalsIgnoreCase(check)) {
            result = "\t\tYES";
            answeredCorrect = true;
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
        if (!word.isEmpty()) cmdArgs.add(word);
        String[] result = new String[cmdArgs.size()];
        for (int i = 0; i < result.length; i++) result[i] = cmdArgs.get(i);
        return result;
    }

    private static HexNumber generateNumber() {
        answeredCorrect = false;
        HexNumber result;
        int[] temp = new int[nibbles];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = rnd.nextInt(HexNumber.BASE);
        }
        if (sign.equalsIgnoreCase(randomSignChar)) {
            result = new HexNumber(temp, rnd.nextBoolean());
        } else {
            if (sign.equalsIgnoreCase(signedChar)) result = new HexNumber(temp, true);
            else result = new HexNumber(temp, false);
        }
        return result;
    }
}
