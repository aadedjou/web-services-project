package fr.uge.ifshare.rmi.common.product;

import java.util.Objects;

public class Product {
	private final State state;
	private final String name;
	
	public Product(String name, State state) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(state);
		this.state = state;
		this.name = name;
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
	
	
	@Override
	public String toString() {
		return "Product : " + name+ " - State : " + state;
	}
	
}
