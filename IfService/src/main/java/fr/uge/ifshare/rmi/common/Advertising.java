package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.product.Rating;
import fr.uge.ifshare.rmi.common.user.IUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class Advertising implements Serializable, IAdvertising {
    //private final Category category;
    private static class Lock implements Serializable {
    }

    private final Product product;
    private final Date date; // transformer en vrai objet date
    private final String sellerPseudo;
    private int quantity;
    private float price;
    private final List<Rating> ratings = new ArrayList<>();
    private final String desc;
    private ArrayList<AdvertisingObserver> observers = new ArrayList<>();
    private final Lock lock = new Lock();
    private AdvertisingNotifier advNotifier;

    public Advertising(Product product, String sellerPseudo, int quantity, float price, String desc) {
        if (quantity < 0) {
            throw new IllegalStateException("You can't put a negative quantity");
        }
        if (price < 0) {
            throw new IllegalStateException("You can't put a negative price");
        }
        this.date = new Date(System.currentTimeMillis());
        this.product = Objects.requireNonNull(product);
        this.sellerPseudo = Objects.requireNonNull(sellerPseudo);
        this.desc = Objects.requireNonNull(desc);
        this.price = price;
        this.quantity = quantity;
        // this.category = category;
        this.advNotifier = new AdvertisingNotifier();
    }
    
    
    private class AdvertisingNotifier {
    	LinkedHashMap<IUser, Integer> registeredUsers = new LinkedHashMap<>();
    	
    	public void addUserToNotify(IUser user, int quantity) {
    		registeredUsers.merge(user, quantity, Integer::sum);
    	}
    	
    	public void sendMessageToFirstUser() {
    		registeredUsers.keySet().iterator().next().receiveMessage("The product " + product.getName() + " is available !");
    	}
    }

    public ArrayList<AdvertisingObserver> getObservers() {
        return observers;
    }



    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return "[" + formatter.format(date) + "] " + sellerPseudo + new String(new char[20 - sellerPseudo.length()]).replace("\0", " ") +
                 product +  new String(new char[20 - product.toString().length()]).replace("\0", " ") +
                 "~  " + price + "€ " +
                 "(quantity : " + quantity + ")     " +
                 "  :  '" + desc + "'";
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Advertising other = (Advertising) obj;
        return product.equals(other.product) && sellerPseudo.equals(other.sellerPseudo)
                 && this.price == other.price && this.desc.equals(other.desc);
    }


    public Product getProduct() {
        return product;
    }

    public float getPrice() {
        return price;
    }

    public String getSellerPseudo() {
        return sellerPseudo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateAdQuantity(int quantity) {
        synchronized (lock) {
            if (this.quantity == 0 && quantity > 0) {
                this.advNotifier.sendMessageToFirstUser();
            }
            this.quantity += quantity;
        }
    }

    public boolean hasSufficientQuantity(int quantity) {
        return this.quantity - quantity >= 0;
    }

    
    public void register(AdvertisingObserver obs) {
        this.observers.add(obs);
    }

    public void unregister(AdvertisingObserver obs) {
    	if (this.observers.contains(obs)) {
    		throw new IllegalArgumentException("Unknown observer");
    	}
        this.observers.add(obs);
    }
    
    
    public void addUserToNotify(IUser user, int quantity) {
    	this.advNotifier.addUserToNotify(user, quantity);
    }

}
