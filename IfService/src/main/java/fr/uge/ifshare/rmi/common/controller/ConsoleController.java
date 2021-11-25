package fr.uge.ifshare.rmi.common.controller;

import java.util.Scanner;
import java.util.function.Predicate;

public class ConsoleController {
    private final static String BOLD_FONT = "\033[0;1m";
    private final static String HINT_COLOR = "\033[38;2;175;150;250m";
    private final static String ERROR_COLOR = "\033[38;2;250;50;100m";
    private final static String DEFAULT_COLOR = "\u001B[0m";
    private final static Scanner sc = new Scanner(System.in);

    private static String errorMessage(String sequence) {
        return ERROR_COLOR + sequence + DEFAULT_COLOR;
    }

    private static String bold(String sequence) {
        return BOLD_FONT + sequence + DEFAULT_COLOR;
    }

    private static String hintedLabelOf(Choice choice) {
        return HINT_COLOR + choice.getHint() + DEFAULT_COLOR + choice.getLabelWithoutHint();
    }

    public static void displayMenu(String message, Choice ... choices) {
        Choice selectedChoice = null;
        ChoiceSet choiceSet = ChoiceSet.of(choices);

        System.out.println("\n\n" + bold(message) + "\n");
        choiceSet.forEach(c -> System.out.println("   ➔ " + hintedLabelOf(c)));
        System.out.println();

        do {
            System.out.print("> ");
            String selection = sc.nextLine();

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

    public static String inputString(String message) {
        return inputString(message, (s -> true), "Invalid information");
    }

    public static String inputString(String message, Predicate<String> condition, String errorMessage) {
        System.out.println("\n   " + message);

        System.out.print("> ");
        String input = sc.nextLine();

        while (!condition.test(input) || input.equals("")) {
            if (!condition.test(input)) System.out.println(errorMessage(errorMessage));
            System.out.print("> ");
            input = sc.nextLine();
        }
        return input;
    }
}

