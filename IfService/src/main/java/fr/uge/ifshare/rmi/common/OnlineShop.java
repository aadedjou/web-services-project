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
	
	public List<Advertising> getAdvertisings() {
		return advertisings;
	}

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
	
	
	@Override
	public void buyProduct(IUser user, Advertising ad, int quantity) {
		Advertising adv = advertisings.stream()
					.filter(a -> a.equals(ad))
					.findFirst()
					.get();
		if (adv.hasSufficientQuantity(quantity)) {
			adv.updateAdQuantity(quantity);
		}
		else {
			adv.addUserToWaitForAvailability(user);
		}
	}

	
}