package fr.uge.ifshare.rmi.common.controller;

class ColoredConsoleController extends ConsoleController {
    private final static String BOLD_FONT = "\033[0;1m";
    private final static String HINT_COLOR = "\033[38;2;175;150;250m";
    private final static String INFO_COLOR = "\033[38;2;225;250;175m";
    private final static String ERROR_COLOR = "\033[38;2;250;50;100m";
    private final static String DEFAULT_COLOR = "\u001B[0m";

    @Override
    String errorMessage(String sequence) {
        return ERROR_COLOR + sequence + DEFAULT_COLOR;
    }

    @Override
    String bold(String sequence) {
        return BOLD_FONT + sequence + DEFAULT_COLOR;
    }

    @Override
    void printChoice(int index, Object obj) {
        System.out.println("     " + (index < 10 ? " " : "") + HINT_COLOR + index + DEFAULT_COLOR + " > " + obj);
    }

    @Override
    void printChoice(Choice choice) {
        System.out.println(("     > " + HINT_COLOR + choice.getHint() + DEFAULT_COLOR + choice.getLabelWithoutHint()));
    }

    @Override
    public void printMessage(String msg) {
        System.out.println("\n     " + INFO_COLOR + msg + DEFAULT_COLOR + "\n");
    }
}
