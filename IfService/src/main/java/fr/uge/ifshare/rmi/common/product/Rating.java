package fr.uge.ifshare.rmi.common.product;

import java.util.Objects;

public class Rating {
	private final float value;
	public Rating(float value) {
		if (value < 0 || 5 < value) {
			throw new IllegalArgumentException("Grade value must be between 0/5 and 5/5. ('" + value + "' is invalid.)");
		}
		this.value = value;
	}
}
