package fr.uge.ifshare.rmi.common;

import fr.uge.ifshare.rmi.common.product.Product;
import fr.uge.ifshare.rmi.common.user.IUser;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IOnlineShop extends Remote {
	void createAdvertising(IUser user, Product product, int quantity, float price, String desc) throws RemoteException;

    boolean buyProduct(IUser user, Advertising ad, int quantity) throws RemoteException;

    List<Advertising> getAdvertisings() throws RemoteException;
}
