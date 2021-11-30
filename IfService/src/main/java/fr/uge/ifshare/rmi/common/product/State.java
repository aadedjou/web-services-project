package fr.uge.ifshare.rmi.common.product;

import java.util.Arrays;
import java.util.Random;

public enum State {
	NEW, USED, DETERIORATED;

	@Override
	public String toString() {
		return super.toString().charAt(0) + super.toString().toLowerCase().substring(1);
	}

	public static State randomState() {
		return Arrays.asList(values()).get(new Random().nextInt(values().length));
	}
}
