package fr.uge.ifshare.rmi.common.ad;

import fr.uge.ifshare.rmi.common.product.Product;

public class Advertising {
	
	private Product product;
	private String sellerPseudo;


	
	public Advertising(Product p, String sellerPseudo) {
		this.product = p;
		this.sellerPseudo = sellerPseudo;
	}
}
