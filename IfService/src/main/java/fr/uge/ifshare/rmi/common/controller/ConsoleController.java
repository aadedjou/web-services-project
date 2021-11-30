package fr.uge.ifshare.rmi.common.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class ConsoleController {
    private final static Scanner sc = new Scanner(System.in);

    public static ConsoleController standard() {
        return new ConsoleController();
    }

    public static ConsoleController colored() {
        return new ColoredConsoleController();
    }

    private String nextString() {
        String input;
        do {
            System.out.print("> ");
            input = sc.nextLine();
        } while (input.equals(""));
        return input;
    }

    private int nextInt() {
        String input;
        Matcher matcher;
        do {
            System.out.print("> ");
            input = sc.nextLine();
            matcher = Pattern.compile("-?[0-9]+").matcher(input);
        } while (!matcher.matches());
        return Integer.parseInt(input);
    }

    private double nextDouble() {
        String input;
        Matcher matcher;
        do {
            System.out.print("> ");
            input = sc.nextLine();
            matcher = Pattern.compile("-?[0-9]+([.,][0-9]+)?").matcher(input);
        } while (!matcher.matches());
        return Double.parseDouble(input);
    }

    String errorMessage(String sequence) {
        return sequence;
    }

    String bold(String sequence) {
        return sequence;
    }

    void printChoice(int index, Object obj) {
        System.out.println("     " + (index < 10 ? " " : "") + index + " > " + obj);
    }

    void printChoice(Choice choice) {
        System.out.println(
          new String(new char[5 - choice.getHint().length()]).replace("\0", " ") +
            "(" + choice.getHint().toLowerCase() + ") > " + choice.getLabel()
        );
    }

    public void printMessage(String msg) {
        System.out.println("\n     " + msg + new String(new char[25]).replace("\0", "-") + "\n");
    }

    @SafeVarargs
    public final <T> T displayMenu(String message, T... elems) {
        return displayMenu(message, Arrays.asList(elems));
    }

    public <T> T displayMenu(String message, List<T> list) {
        if (list.isEmpty()) {
            printMessage("There is nothing to show for the moment.");
            return null;
        }
        System.out.println("\n\n" + bold(message) + "\n");
        IntStream.range(0, list.size()).forEach(n -> printChoice(n, list.get(n)));
        T selectedChoice = null;

        do {
            int selection = nextInt();
            if (0 <= selection && selection < list.size())
                selectedChoice = list.get(selection);
        } while (selectedChoice == null);

        return selectedChoice;
    }

    public void displayMenu(String message, Choice... choices) {
        ChoiceSet choiceSet = ChoiceSet.of(choices);
        Choice selectedChoice = null;

        System.out.println("\n\n" + bold(message) + "\n");
        choiceSet.forEach(this::printChoice);
        System.out.println();

        do {
            String selection = nextString();

            for (Choice choice : choices) {
                if (choice.acceptedMatches().contains(selection)) {
                    selectedChoice = choice;
                    break;
                }
            }
        } while (selectedChoice == null);

        System.out.println();
        selectedChoice.processAction();
    }

    private <T> T inputValue(Supplier<T> supplier, String message, Predicate<T> condition, String errorMessage) {
        System.out.println("\n   " + message);
        T input = supplier.get();

        while (!condition.test(input)) {
            System.out.println(errorMessage(errorMessage));
            input = supplier.get();
        }
        return input;
    }

    public int inputPositiveInt(String message) {
        return inputInt(message, (n) -> 0 <= n, "Value must be a positive integer.");
    }

    public String inputString(String message) {
        return inputString(message, (s -> true), "Invalid information");
    }

    public int inputInt(String message, Predicate<Integer> condition, String errorMessage) {
        return inputValue(this::nextInt, message, condition, errorMessage);
    }

    public Double inputDouble(String message, Predicate<Double> condition, String errorMessage) {
        return inputValue(this::nextDouble, message, condition, errorMessage);
    }

    public String inputString(String message, Predicate<String> condition, String errorMessage) {
        return inputValue(this::nextString, message, condition, errorMessage);
    }
}

