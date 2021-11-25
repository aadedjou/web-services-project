package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private final List<Product> products = new ArrayList<>();
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String pseudo;

    public User(String first, String last, String password) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(last);
        this.password = Objects.requireNonNull(password);
        this.firstName = first.substring(0, 1).toUpperCase() + first.substring(1).toLowerCase();
        this.lastName = last.substring(0, 1).toUpperCase() + last.substring(1).toLowerCase();
        this.pseudo = first.toLowerCase().charAt(0) +
                        Normalizer.normalize(last, Normalizer.Form.NFD).toLowerCase()
                          .replaceAll("\\p{M}", "")
                          .replace(" ", "");
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getShortenFullName() {
        return firstName + " " + lastName.charAt(0) + ".";
    }

    public String getPassword() {
        return password;
    }

    public String getPseudo() {
        return pseudo;
    }

    public List<Product> getProducts() {
        return products;
    }
    
	public boolean hasProduct(Product p) {
		return this.products.contains(p);
	}

    @Override
    public String toString() {
        return getPseudo();
    }
}
