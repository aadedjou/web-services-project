package fr.uge.ifshare.rmi.common.user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class UserDatabase extends UnicastRemoteObject implements IUserDatabase {
    private final List<IUser> users = new ArrayList<>();

    public UserDatabase() throws RemoteException {
		registerUser("Sami", "Ben Chakal", "dev");
		registerUser("Sebastien", "Petanque", "dev");
        registerUser("Killian", "Blancheur", "user");
        registerUser("Melissa", "Ammoche", "user");
        registerUser("Myriam", "Poussin", "user");
        registerUser("Yohan", "Chameau", "user");
        registerUser("Ruben", "Charo", "user");
    }

    @Override
    public IUser registerUser(String firstName, String lastName, String password) {
        User user = new User(firstName, lastName, password);
        users.add(user);
        return user;
    }

    @Override
    public IUser getUserById(String pseudo) {
        return users.stream()
          .filter(u -> u.getPseudo()
            .equals(Objects.requireNonNull(pseudo))).findFirst()
          .orElse(null);
    }

    @Override
    public List<IUser> userList() {
        return users;
    }

    @Override
    public IUser getRandomUser() {
        return users.get(new Random().nextInt(users.size()));
    }
}
