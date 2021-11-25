package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OnlineShop extends UnicastRemoteObject implements IOnlineShop {
	private final List<User> users = new ArrayList<>();

	public OnlineShop() throws RemoteException {
		registerUser("Sami", "Ben Chakal", "dev");
		registerUser("Sébastien", "Pétanque", "dev");
	}

	public List<Product> getEveryProduct() {
		return users.stream()
				.flatMap(user -> user.getProducts().stream())
				.collect(Collectors.toList());
	}

	@Override
	public void sellProduct(User user, Product product) {
	}

	@Override
	public User registerUser(String firstName, String lastName, String password) throws RemoteException {
		var user = new User(firstName, lastName, password);
		users.add(user);
		return user;
	}

	@Override
	public User getUserById(String pseudo) throws RemoteException {
		return users.stream()
		.filter(u -> u.getPseudo()
		.equals(Objects.requireNonNull(pseudo))).findFirst()
		.orElse(null);
	}

	@Override
	public String toString() {
		return users.toString();
	}
}
