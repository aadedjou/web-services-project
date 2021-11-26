package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IOnlineShop extends Remote {
    void createAd(User user, Product product) throws RemoteException; // date !!

    User registerUser(String firstName, String lastName, String password) throws RemoteException;

    User getUserById(String pseudo) throws RemoteException;
    
}
