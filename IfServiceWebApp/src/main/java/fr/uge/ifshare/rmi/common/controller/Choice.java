package fr.uge.ifshare.rmi.common.controller;

import java.util.*;
import java.util.stream.Collectors;

public class Choice {
    private int hintSize = 1;
    private final String label;
    private final Runnable action;

    public Choice(String label, Runnable action) {
        this.label = Objects.requireNonNull(label);
        this.action = Objects.requireNonNull(action);
    }

    public Choice(String label) {
        this(label, () -> System.out.println("'" + label + "' has been chosen."));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Choice)) return false;
        Choice choice = (Choice) o;
        return Objects.equals(label, choice.label);
    }

    public String getHint() {
        return label.substring(0, hintSize);
    }

    public String getLabel() {
        return label;
    }

    public String getLabelWithoutHint() {
        return label.substring(hintSize);
    }

    public void increaseHintSize() {
        hintSize++;
    }

    public ArrayList<String> acceptedMatches() {
        return new ArrayList<>(Arrays.asList(
          label, label.toLowerCase(), label.toUpperCase(),
          getHint(), getHint().toLowerCase(), getHint().toUpperCase()
        ));
    }

    public void processAction() {
        action.run();
    }
}

class ChoiceSet implements Iterable<Choice> {
    private final LinkedHashSet<Choice> choices;

    private ChoiceSet(Choice... choices) {
        this.choices = new LinkedHashSet<>();
        this.choices.addAll(Arrays.asList(choices));
        this.choices.forEach(Objects::requireNonNull);
        ensureNoDuplicateHints();
    }

    public static ChoiceSet of(Choice... choices) {
        return new ChoiceSet(choices);
    }

    boolean hasDuplicateHints() {
        Set<String> hintSet = choices.stream()
          .map(Choice::getHint)
          .collect(Collectors.toSet());
        return choices.size() > hintSet.size();
    }

    void ensureNoDuplicateHints() {
        while (hasDuplicateHints()) {
            choices.forEach(c -> choices.forEach(c2 -> {
                if (c.equals(c2)) return;
                if (c.getHint().equals(c2.getHint())) {
                    c.increaseHintSize();
                    c2.increaseHintSize();
                }
            }));
        }
    }

    @Override
    public Iterator<Choice> iterator() {
        return choices.iterator();
    }
}