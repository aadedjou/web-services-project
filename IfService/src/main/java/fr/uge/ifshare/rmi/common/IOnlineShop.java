package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.IUser;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IOnlineShop extends Remote {
    void createAd(IUser user, Product product) throws RemoteException; // date !!

    IUser registerUser(String firstName, String lastName, String password) throws RemoteException;
	/*
    void sellProduct(User user, Product product) throws RemoteException; // date !!
	 */
	
	void createAdvertising(User user, Product product, int quantity, float price, String desc) throws RemoteException;
	
    IUser getUserById(String pseudo) throws RemoteException;
    
    void buyProduct(User user, Product product) throws RemoteException;
}
