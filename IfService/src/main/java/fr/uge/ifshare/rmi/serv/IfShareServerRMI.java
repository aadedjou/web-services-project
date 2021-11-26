package fr.uge.ifshare.rmi.serv;

import fr.uge.ifshare.rmi.common.OnlineShop;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class IfShareServerRMI {
	private final OnlineShop shop = new OnlineShop();

	public IfShareServerRMI() throws RemoteException {
    }

    public static void main(String[] args) throws AlreadyBoundException, RemoteException, MalformedURLException {
        IfShareServerRMI serv = new IfShareServerRMI();

        LocateRegistry.createRegistry(1099);
        Naming.bind("onlineshop", serv.shop);
        System.out.println("RMI Server is running...");
    }
}
