package fr.uge.ifshare.rmi.common.user;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.function.IntConsumer;

public interface IUserDatabase extends Remote {
    IUser registerUser(String firstName, String lastName, String password) throws RemoteException;

    IUser getUserById(String pseudo) throws RemoteException;

    List<IUser> userList() throws RemoteException;

    IUser getRandomUser() throws RemoteException;
    
}
