package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Advertising {
    private final Product product;
    private final Date date; // transformer en vrai objet date

    public Advertising(Product product) {
        this.date = new Date(System.currentTimeMillis());
        this.product = product;
    }

    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
