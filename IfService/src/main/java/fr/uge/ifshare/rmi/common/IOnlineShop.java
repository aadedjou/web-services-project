package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IOnlineShop extends Remote {
	/*
    void sellProduct(User user, Product product) throws RemoteException; // date !!
	 */
	
	void createAdvertising(User user, Product product, int quantity, float price, String desc) throws RemoteException;
	
    User registerUser(String firstName, String lastName, String password) throws RemoteException;

    User getUserById(String pseudo) throws RemoteException;
    
    void buyProduct(User user, Product product) throws RemoteException;
}
