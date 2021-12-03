package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.user.IUser;

import java.util.Map;
import java.util.Optional;

public interface IAdvertising {

    String getSellerPseudo();

    int getQuantity();

    Product getProduct();


    Optional<Map.Entry<IUser, Integer>> firstOrderInWaitingList();

    void addUserToWaitingList(IUser sessionUser, int quantity);

    void addRating(IUser sessionUser, double rating);

    void desistFirstUserFromWaitingList();

    void updateQuantity(int newQty);
    void addQuantity(int addedQty);
}
