package fr.uge.ifshare.rmi.cli;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import fr.uge.ifshare.rmi.common.IOnlineShop;

public class RMIClient {
	private final IOnlineShop shop;

    public RMIClient(IOnlineShop shop) {
        this.shop = shop;
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
    	var clientShop = new RMIClient((IOnlineShop) Naming.lookup("shop"));
    }     
}
