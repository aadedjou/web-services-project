package fr.uge.ifshare.rmi.common.controller;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class ConsoleController {
    private final static Scanner sc = new Scanner(System.in);

    public static ConsoleController standard() {
        return new ConsoleController();
    }

    public static ConsoleController colored() {
        return new ColoredConsoleController();
    }

    String errorMessage(String sequence) {
        return sequence;
    }

    String bold(String sequence) {
        return sequence;
    }

    void printChoice(Choice choice) {
        System.out.println(new String(new char[5 - choice.getHint().length()]).replace("\0", " ") +
                              "(" + choice.getHint().toLowerCase() + ") > " + choice.getLabel());
    }

    public <T> T displayMenu(String message, List<T> list) {
        System.out.println("\n\n" + bold(message) + "\n");
        IntStream.range(0, list.size()).forEach(n -> System.out.println("     " + n + " > " + list.get(n)));
        T selectedChoice = null;

        do {
            System.out.print("> ");
            int selection = sc.nextInt();

            if (0 <= selection && selection < list.size()) selectedChoice = list.get(selection);
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

    public int inputInt(String message, Predicate<Integer> condition, String errorMessage) {
        System.out.println("\n   " + message);

        System.out.print("> ");
        int input = sc.nextInt();

        while (!condition.test(input)) {
            if (!condition.test(input)) System.out.println(errorMessage(errorMessage));
            System.out.print("> ");
            input = sc.nextInt();
        }
        return input;
    }

    public String inputString(String message) {
        return inputString(message, (s -> true), "Invalid information");
    }

    public String inputString(String message, Predicate<String> condition, String errorMessage) {
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

