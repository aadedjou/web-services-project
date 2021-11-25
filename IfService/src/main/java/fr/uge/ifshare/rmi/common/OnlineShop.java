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
		registerUser("SÃ©bastien", "PÃ©tanque", "dev");
	}

	public List<Product> getEveryProduct() {
		return users.stream()
				.flatMap(user -> user.getProducts().stream())
				.collect(Collectors.toList());
	}

	/*
	 * Etape 1 : Voir si le produit existe déjà
	 * Etape 2a : Si il existe déjà, on incrémente la quantité
	 * Etape 2b : Si il n'existe pas, on ajoute une nouvelle Map.Entry du produit avec 1 en valeur (quantité)
	 */
	@Override
	public void sellProduct(User user, Product product) {
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
	
	
	/*
	 * Etape 1 : Voir si le produit existe déjà
	 * Etape 2a : Si il existe déjà, on cherche le User qui l'a, et on lui supprime le produit, et on ajoute à l'acheteur
	 * Etape 2b : Si il n'existe pas, exception ? ou autre
	 */
	
	public void buyProduct(User user, Product product) {
		/*if (getEveryProduct().contains(product)) {
			users.stream()
					.filter(user -> user.hasProduct(product))
					.collect(Collectors.toList());
			user.buyProduct(product);
			
		}*/
	}
	
}
