package fr.uge.ifshare.rmi.common.product;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
	private final State state;
	private final String name;
	
	public Product(String name, State state) {
		this.name = Objects.requireNonNull(name);
		this.state = Objects.requireNonNull(state);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return  name.equals(other.name) && state == other.state;
	}

	public String getName() {
		return name;
	}

	public State getState() {
		return state;
	}

	@Override
	public String toString() {
		return  name + " (" + state + ")";
	}
	
}
