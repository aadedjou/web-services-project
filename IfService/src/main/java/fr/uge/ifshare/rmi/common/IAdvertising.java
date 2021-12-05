package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.user.IUser;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Optional;

public interface IAdvertising extends Remote {

    String getSellerPseudo() throws RemoteException;

    int getQuantity() throws RemoteException;

    Product getProduct() throws RemoteException;


    Optional<Map.Entry<IUser, Integer>> firstOrderInWaitingList() throws RemoteException;

    void addUserToWaitingList(IUser sessionUser, int quantity) throws RemoteException;

    void addRating(IUser sessionUser, double rating) throws RemoteException;

    void desistFirstUserFromWaitingList() throws RemoteException;

    void updateQuantity(int newQty) throws RemoteException;
    void addQuantity(int addedQty) throws RemoteException;
    
    
    // USED BY IFSERVICE
    public double getPrice() throws RemoteException;
    public boolean getProductWasBought() throws RemoteException;
    public String toString();
}
