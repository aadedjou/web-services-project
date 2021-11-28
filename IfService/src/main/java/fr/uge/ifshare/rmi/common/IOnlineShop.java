package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.IUser;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IOnlineShop extends Remote {
	
	void createAdvertising(IUser user, Product product, int quantity, float price, String desc) throws RemoteException;
	
    IUser registerUser(String firstName, String lastName, String password) throws RemoteException;

    IUser getUserById(String pseudo) throws RemoteException;
    
    void buyProduct(IUser user, Advertising ad, int quantity) throws RemoteException;
}
