package fr.uge.ifshare.rmi;

import java.util.ArrayList;
import java.util.List;

public class Product {
	private final State state;
	private final List<Grade> grades = new ArrayList<>();
	private final String name;
	private float price;
	
	public Product(String name, State state) {
		this.state = state;
		this.name = name;
	}
}
