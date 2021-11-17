package fr.uge.ifshare.rmi.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OnlineShop implements IOnlineShop {
	private final List<User> users = new ArrayList<>();
	
	public OnlineShop() {
		users.add(new User("Sami", "Ben Chakal"));
		users.add(new User("Sébastien", "Pétanque"));
	}
	
	public List<Product> getProducts() {
		return users.stream()
				.flatMap(user -> user.getProducts().stream())
				.collect(Collectors.toList());
	}

	@Override
	public void sellProduct(User user, Product product) {
		// TODO Auto-generated method stub
	}
}
