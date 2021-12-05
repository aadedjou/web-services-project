package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.user.IUser;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IOnlineShop extends Remote {
	void createAdvertising(IUser user, Product product, int quantity, double price, String desc) throws RemoteException;

    boolean buyProduct(IUser user, IAdvertising ad, int quantity) throws RemoteException;

    List<Advertising> getAdvertisings() throws RemoteException;

    void addRating(IAdvertising ad, IUser sessionUser, double grade) throws RemoteException;

    void removeAd(IAdvertising ad) throws RemoteException;
    
    
    // USED BY IFSERVICE
    IAdvertising[] getAdvertisingsWhereProductWasBought() throws RemoteException;
    
    Advertising getAdvertisingByProductNameAndSeller(String productName, String sellerName) throws RemoteException;
}