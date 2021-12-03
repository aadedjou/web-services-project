package fr.uge.ifshare.rmi.serv;

import fr.uge.ifshare.rmi.common.user.UserDatabase;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class DatabaseServerRMI {
	private final UserDatabase userDatabase = new UserDatabase();

	public DatabaseServerRMI() throws RemoteException {
    }

    public static void main(String[] args) throws AlreadyBoundException, RemoteException, MalformedURLException {
        DatabaseServerRMI serv = new DatabaseServerRMI();

        LocateRegistry.createRegistry(1098);
        Naming.bind("userdata", serv.userDatabase);
        System.out.println("UserDatabase RMI Server is running...");
    }
}
