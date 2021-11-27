package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnlineShop extends UnicastRemoteObject implements IOnlineShop {
	private final List<IUser> users = new ArrayList<>();
	private final List<Advertising> advertisings = new ArrayList<>();

	public OnlineShop() throws RemoteException {
		registerUser("Sami", "Ben Chakal", "dev");
		registerUser("Sebastien", "Petanque", "dev");
	}

	@Override
	public void createAd(IUser user, Product product) throws RemoteException {

	}

	@Override
	public IUser registerUser(String firstName, String lastName, String password) throws RemoteException {
		IUser user = new User(firstName, lastName, password);
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

	public List<Advertising> getAdvertising() {
		return advertisings;
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
	
	public void sellProduct() {
		
	}
	
}
