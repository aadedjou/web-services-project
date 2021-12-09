package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.user.IUser;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public interface IAdvertising extends Remote {

    Date getDate();

    String getSellerPseudo() throws RemoteException;
    String getDescription() throws RemoteException;

    int getQuantity() throws RemoteException;

    Product getProduct() throws RemoteException;


    Optional<Map.Entry<IUser, Integer>> firstOrderInWaitingList() throws RemoteException;

    void addUserToWaitingList(IUser sessionUser, int quantity) throws RemoteException;

    void addRating(IUser sessionUser, double rating) throws RemoteException;

    void desistFirstUserFromWaitingList() throws RemoteException;

    void updateQuantity(int newQty) throws RemoteException;
    void addQuantity(int addedQty) throws RemoteException;
    
    
    // USED BY IFSERVICE
    double getPrice() throws RemoteException;
    boolean getProductWasBought() throws RemoteException;
    String toString();
}
