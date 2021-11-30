package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.product.Rating;
import fr.uge.ifshare.rmi.common.user.IUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Advertising implements Serializable, IAdvertising {
    private static class Lock implements Serializable {
    }

    private final Product product;
    private final String sellerPseudo;
    private final String desc;
    private final Date date;
    private final double price;
    private int quantity;
    private final HashMap<IUser, Rating> ratings = new HashMap<>();
    private final ArrayList<AdvertisingObserver> observers = new ArrayList<>();
    private final LinkedHashMap<IUser, Integer> waitingList = new LinkedHashMap<>();
    private final Lock lock = new Lock();

    public Advertising(Product product, String sellerPseudo, int quantity, double price, String desc) {
        if (quantity < 0) {
            throw new IllegalArgumentException("You can't put a negative quantity (" + quantity + ")");
        }
        if (price < 0) {
            throw new IllegalArgumentException("You can't put a negative price (" + price + ")");
        }
        this.date = new Date(System.currentTimeMillis());
        this.product = Objects.requireNonNull(product);
        this.sellerPseudo = Objects.requireNonNull(sellerPseudo);
        this.desc = Objects.requireNonNull(desc);
        this.price = price;
        this.quantity = quantity;
        register(new WaitingListNotifier(this));
    }

    private class WaitingListNotifier implements AdvertisingObserver {
        private final Advertising advertising;

        private WaitingListNotifier(Advertising ad) {
            this.advertising = ad;
        }

        private void notifyFirstUser() {
            waitingList.keySet().stream()
              .findFirst()
              .ifPresent(u -> u.receiveMessage(
                "The product '" + product.getName() + "' by " + sellerPseudo + " is now available !")
              );
        }

        @Override
        public void onAdvertisingUpdate() {
            waitingList.values().stream().findFirst().ifPresent(qty -> {
                if (qty < advertising.getQuantity()) {
                    notifyFirstUser();
                }
            });
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return averageRating().toString() + " [" + formatter.format(date) + "] " + sellerPseudo + new String(new char[20 - sellerPseudo.length()]).replace("\0", " ") +
                 product + new String(new char[35 - product.toString().length()]).replace("\0", " ") +
                 " ~  " + price + "€ " +
                 "(quantity : " + quantity + ")     " +
                 "  :  '" + desc + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Advertising other = (Advertising) obj;
        return product.equals(other.product) && sellerPseudo.equals(other.sellerPseudo)
                 && this.price == other.price && this.desc.equals(other.desc);
    }

    public Rating averageRating() {
        return new Rating(ratings.values());
    }

    public Product getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    public String getSellerPseudo() {
        return sellerPseudo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void performTransaction() {
        // si la liste d'attente n'est pas vide
        firstOrderInWaitingList().ifPresent(order -> {
            // on met à jour la quantité
            addQuantity(-order.getValue());
            // on retire le premier client de la liste
            desistFirstUserFromWaitingList();
        });
    }

    public void updateQuantity(int addedQuantity) {
        synchronized (lock) {
            this.quantity = addedQuantity;
            this.observers.forEach(AdvertisingObserver::onAdvertisingUpdate);
        }
    }
    public void addQuantity(int addedQuantity) {
        updateQuantity(quantity + addedQuantity);
    }

    @Override
    public Optional<Map.Entry<IUser, Integer>> firstOrderInWaitingList() {
        return waitingList.entrySet().stream().findFirst();
    }

    public boolean hasSufficientQuantity(int quantity) {
        return this.quantity - quantity >= 0;
    }

    public void register(AdvertisingObserver obs) {
        this.observers.add(obs);
    }

    public void unregister(AdvertisingObserver obs) {
        this.observers.remove(obs);
    }

    public void addUserToWaitingList(IUser user, int quantity) {
        waitingList.merge(user, quantity, Integer::sum);
    }

    @Override
    public void addRating(IUser user, double grade) {
        synchronized (lock) {
            ratings.put(user, new Rating(grade));
        }
    }

    @Override
    public void desistFirstUserFromWaitingList() {
        // s'il y a un premier
        firstOrderInWaitingList().ifPresent(order -> {
            // on l'enlève
            waitingList.remove(order.getKey());
        });
        // si on peut envoyer une notification au nouveau premier, le faire ici
    }
}
