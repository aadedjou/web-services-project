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
    public void createAdvertising(IUser user, Product product, int quantity, double price, String desc) {
        Advertising ad = new Advertising(product, user.getPseudo(), quantity, price, desc);
        if (adCanBeCreated(ad)) {
            advertisings.add(ad);
        } else {
            System.out.println("Ad already exist");
        }
    }

    @Override
    public void addRating(IAdvertising ad, IUser user, double grade) {
        Advertising adv = advertisings.stream()
          .filter(a -> a.equals(ad))
          .findFirst()
          .orElseThrow(() ->
                         new IllegalArgumentException("Can't buy product from " + ad + " since it doesn't " + "exist")
          );
        adv.addRating(user, grade);
    }

    @Override
    public void removeAd(IAdvertising ad) {
        Advertising adv = advertisings.stream()
          .filter(a -> a.equals(ad))
          .findFirst()
          .orElseThrow(() ->
                         new IllegalArgumentException("Can't buy product from " + ad + " since it doesn't " + "exist")
          );
        synchronized (advertisings) {
            advertisings.remove(adv);
        }
    }

    private boolean adCanBeCreated(Advertising ad) {
        for (Advertising adv : advertisings) {
            if (adv.equals(ad)) {
                adv.addQuantity(ad.getQuantity());
                return false;
            }
        }
        return true;
    }

    public boolean buyProduct(IUser user, IAdvertising ad, int quantity) {
        Objects.requireNonNull(user);
        Advertising adv = advertisings.stream()
          .filter(a -> a.equals(ad))
          .findFirst()
          .orElseThrow(() ->
                         new IllegalArgumentException("Can't buy product from " + ad + " since it doesn't " + "exist")
          );

        /// ajoute l'user à la liste d'attente
        adv.addUserToWaitingList(user, quantity);
        // s'il n'y a pas assez de produits, on renvoie false
        if (!adv.hasSufficientQuantity(quantity)) return false;
        // sinon on effectue une transaction
        adv.performTransaction();
        return true;
    }
}