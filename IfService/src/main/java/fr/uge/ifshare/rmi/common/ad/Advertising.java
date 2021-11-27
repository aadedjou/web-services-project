package fr.uge.ifshare.rmi.common.ad;

import fr.uge.ifshare.rmi.common.User;
import fr.uge.ifshare.rmi.common.product.Category;
import fr.uge.ifshare.rmi.common.product.Product;

public class Advertising {
    private final Category category;
    private final Product product;
    private final User user;
    private int quantity;

    public Advertising(Category category, Product product, User user, int quantity) {
        this.category = category;
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }
}
