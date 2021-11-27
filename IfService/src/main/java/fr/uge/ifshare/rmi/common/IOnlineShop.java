package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IOnlineShop extends Remote {
    void createAd(IUser user, Product product) throws RemoteException; // date !!

    IUser registerUser(String firstName, String lastName, String password) throws RemoteException;

    IUser getUserById(String pseudo) throws RemoteException;
    
}
