package fr.uge.ifshare.rmi.serv;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.OnlineShop;

public class RMIServer {
	private final OnlineShop shop = new OnlineShop();

	public RMIServer() throws RemoteException { }

    public static void main(String[] args) throws AlreadyBoundException {
    	try {
            LocateRegistry.createRegistry(1099); 
            IOnlineShop shop = new OnlineShop();    
            Naming.rebind("shop", shop);
        } catch (RemoteException e) {
            System.out.println("Problem : " + e);
        } catch (MalformedURLException e) {
            System.out.println("Problem : " + e);
        }
    }
}
