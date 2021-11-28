package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.user.IUser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnlineShop extends UnicastRemoteObject implements IOnlineShop {
	private final List<Advertising> advertisings = new ArrayList<>();
	
	public OnlineShop() throws RemoteException {
	}

	@Override
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

	public boolean buyProduct(IUser user, Advertising ad, int quantity) {
		Objects.requireNonNull(user);
		Advertising adv = advertisings.stream()
					.filter(a -> a.equals(ad))
					.findFirst()
					.orElseThrow(() ->
								 new IllegalArgumentException("Can't buy product from " + ad + " since it doesn't " + "exist")
					);

		if (!adv.hasSufficientQuantity(quantity)) {
			adv.addUserToWaitForAvailability(user);
			return false;
		}
		adv.updateAdQuantity(-quantity);
		return true;
	}
}