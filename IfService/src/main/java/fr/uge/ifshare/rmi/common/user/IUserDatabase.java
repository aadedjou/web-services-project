package fr.uge.ifshare.rmi.common.user;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUserDatabase extends Remote {
    IUser registerUser(String firstName, String lastName, String password) throws RemoteException;

    IUser getUserById(String pseudo) throws RemoteException;
}
