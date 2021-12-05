package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.user.IUser;
import fr.uge.ifshare.rmi.common.user.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public void createAdvertising(IUser user, Product product, int quantity, double price, String desc) throws RemoteException {
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

    @Override
    public void updateQuantity(IAdvertising ad, int newQty) {
        Advertising adv = advertisings.stream()
          .filter(a -> a.equals(ad))
          .findFirst()
          .orElseThrow(() ->
                         new IllegalArgumentException("Can't buy product from " + ad + " since it doesn't " + "exist")
          );
        adv.updateQuantity(newQty);
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

    public boolean buyProduct(IUser user, IAdvertising ad, int quantity) throws RemoteException {
        Objects.requireNonNull(user);
        Advertising adv = advertisings.stream()
          .filter(a -> a.equals(ad))
          .findFirst()
          .orElseThrow(() ->
                         new IllegalArgumentException("Can't buy product from " + ad + " since it doesn't " + "exist")
          );

        IUser firstUser = adv.firstOrderInWaitingList().stream()
          .findFirst()
          .map(Map.Entry::getKey)
          .orElse(new User());

        if (!firstUser.getPseudo().equals(user.getPseudo()))
            adv.addUserToWaitingList(user, quantity);
        if (!adv.hasSufficientQuantity(quantity)) return false;
        adv.performTransaction();
        adv.setProductWasBought();
        return true;
    }


    //USED BY IFSERVICE

    @Override
    public IAdvertising[] getAdvertisingsWhereProductWasBought() {
        IAdvertising[] adsToObjects = new IAdvertising[advertisings.size()];
        for (int i = 0; i < advertisings.size(); i++) {
            if (advertisings.get(i).getProductWasBought()) {
                adsToObjects[i] = advertisings.get(i);
            }
        }

        return adsToObjects;
    }

}

