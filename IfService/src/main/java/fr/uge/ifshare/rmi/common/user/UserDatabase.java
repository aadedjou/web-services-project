package fr.uge.ifshare.rmi.common.user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDatabase extends UnicastRemoteObject implements IUserDatabase {
    private final List<IUser> users = new ArrayList<>();

    public UserDatabase() throws RemoteException {
		registerUser("Sami", "Ben Chakal", "dev");
		registerUser("Sebastien", "Petanque", "dev");
    }

    @Override
    public IUser registerUser(String firstName, String lastName, String password) throws RemoteException {
        User user = new User(firstName, lastName, password);
        users.add(user);
        return user;
    }

    @Override
    public IUser getUserById(String pseudo) throws RemoteException {
        return users.stream()
          .filter(u -> u.getPseudo()
            .equals(Objects.requireNonNull(pseudo))).findFirst()
          .orElse(null);
    }


}
