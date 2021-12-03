package fr.uge.ifshare.rmi.common.product;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rating implements Serializable {
    private final double value;

    public Rating(double value) {
        if (value < 0 || 5 < value) {
            throw new IllegalArgumentException("Grade value must be between 0/5 and 5/5. ('" + value + "' is invalid.)");
        }
        this.value = value;
    }

    public Rating(Collection<Rating> ratings) {
        value = ratings.stream()
          .mapToDouble(Rating::getValue)
          .average()
          .orElse(0);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return IntStream.range(0, 5)
                 .mapToObj(n -> n < value && value != 0 ? "*" : "-")
                 .collect(Collectors.joining("", "(", ")"));
    }
}
