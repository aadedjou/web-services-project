package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Grade;
import fr.uge.ifshare.rmi.common.product.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Advertising {
    private final Product product;
    private final Date date; // transformer en vrai objet date
    private final String sellerPseudo;
    private int quantity;
    private float price;
    private final List<Grade> grades = new ArrayList<>();
	private final String desc;
	private ArrayList<User> usersWaitingForProduct = new ArrayList<User>();
	
    public Advertising(Product product, String sellerPseudo, int quantity, float price, String desc) {
    	if (quantity < 0) {
    		throw new IllegalStateException("You can't put a negative quantity");
    	}
        this.date = new Date(System.currentTimeMillis());
        this.product = Objects.requireNonNull(product);
        this.sellerPseudo = Objects.requireNonNull(sellerPseudo);
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
    }

    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date) + " - " + product + " - Desc : " + desc.substring(0, 5) + " - " + price + " - Quantity : " + quantity + " - " + sellerPseudo;
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
    	this.quantity += quantity;
    }
    
    
}
