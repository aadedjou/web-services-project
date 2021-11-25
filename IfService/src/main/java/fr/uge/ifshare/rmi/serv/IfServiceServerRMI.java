package fr.uge.ifshare.rmi.serv;

import fr.uge.ifshare.rmi.common.OnlineShop;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class IfServiceServerRMI {
	private final OnlineShop shop = new OnlineShop();

	public IfServiceServerRMI() throws RemoteException {
    }

    public static void main(String[] args) throws AlreadyBoundException, RemoteException, MalformedURLException {
        IfServiceServerRMI serv = new IfServiceServerRMI();

        LocateRegistry.createRegistry(1099);
        Naming.bind("onlineshop", serv.shop);
        System.out.println("RMI Server is running...");
    }
}
