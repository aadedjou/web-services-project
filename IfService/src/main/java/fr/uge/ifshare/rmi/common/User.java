package fr.uge.ifshare.rmi.common;

import java.util.ArrayList;
import java.util.List;

public class User {
	private final String lastName;
	private final String firstName;
	private final List<Product> products = new ArrayList<>();
	
	public User(String first, String last) {
		this.firstName = first;
		this.lastName = last;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public List<Product> getProducts() {
		return products;
	}
}
