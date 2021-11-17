package fr.uge.ifshare.rmi.common;

import java.rmi.Remote;

public interface IOnlineShop extends Remote {
	void sellProduct(User user, Product product); // date !!
}
