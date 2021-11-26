package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnlineShop extends UnicastRemoteObject implements IOnlineShop {
	private final List<User> users = new ArrayList<>();
	private final List<Advertising> advertisings = new ArrayList<>();

	public OnlineShop() throws RemoteException {
		registerUser("Sami", "Ben Chakal", "dev");
		registerUser("Sebastien", "Petanque", "dev");
	}

	@Override
	public void createAd(User user, Product product) throws RemoteException {

	}

	@Override
	public User registerUser(String firstName, String lastName, String password) throws RemoteException {
		User user = new User(firstName, lastName, password);
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
	
	public void buyProduct(User user, Product product) {
		/*if (getEveryProduct().contains(product)) {
			users.stream()
					.filter(user -> user.hasProduct(product))
					.collect(Collectors.toList());
			user.buyProduct(product);
			
		}*/
	}
	
}
