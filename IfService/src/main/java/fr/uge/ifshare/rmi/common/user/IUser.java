package fr.uge.ifshare.rmi.common.user;


import fr.uge.ifshare.rmi.common.IAdvertising;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IUser extends Serializable, Remote {
	String getFullName() throws RemoteException;
    String getShortenFullName() throws RemoteException;
    String getPassword() throws RemoteException;
    String getPseudo() throws RemoteException;
    // UserObservers getUserObservers();
	void receiveMessage(String string) throws RemoteException;

    void addToHistory(IAdvertising ad) throws RemoteException;
    List<IAdvertising> getHistory() throws RemoteException;
}
