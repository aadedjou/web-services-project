package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.IUser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnlineShop extends UnicastRemoteObject implements IOnlineShop {
	private final List<IUser> users = new ArrayList<>();
	private List<Advertising> advertisings = new ArrayList<Advertising>();
	
	public OnlineShop() throws RemoteException {
		registerUser("Sami", "Ben Chakal", "dev");
		registerUser("Sebastien", "Petanque", "dev");
	}

	/*
	public List<Product> getEveryProduct() {
		return users.stream()
				.flatMap(user -> user.getProducts().stream())
				.collect(Collectors.toList());
	}*/
	
	public List<Advertising> getAdvertisings() {
		return advertisings;
	}

	/*
	 * Etape 1 : Voir si le produit existe déjà
	 * Etape 2a : Si il existe déjà, on incrémente la quantité
	 * Etape 2b : Si il n'existe pas, on ajoute une nouvelle Map.Entry du produit avec 1 en valeur (quantité)
	 */
	@Override
	public void createAdvertising(IUser user, Product product, int quantity, float price, String desc) {
		Advertising ad = new Advertising(product, user.getPseudo(), quantity, price, desc);
		if (adCanBeCreated(ad)) {
			advertisings.add(ad);
		} else {
			System.out.println("Ad already exist");
		}
		
	}

	private boolean adCanBeCreated(Advertising ad) {
		for (Advertising adv : advertisings) {
			if (adv.equals(ad)) {
				adv.updateAdQuantity(ad.getQuantity());
				return false;
			}
		}
		return true;
	}

	@Override
	public IUser registerUser(String firstName, String lastName, String password) throws RemoteException {
		User user = new User(firstName, lastName, password);
		users.add(user);
		return user;
	}

	@Override
	public IUser getUserById(String pseudo) throws RemoteException {
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
	
	@Override
	public void buyProduct(IUser user, Product product) {
		/*if (getEveryProduct().contains(product)) {
			users.stream()
					.filter(user -> user.hasProduct(product))
					.collect(Collectors.toList());
			user.buyProduct(product);
			
		}*/
	}

	
}