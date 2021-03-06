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
		registerUser("Sami", "Ben Lakhal", "dev");
		registerUser("Sebastien", "Postansque", "dev");
        registerUser("Killian", "Blanchard", "user");
        registerUser("Melissa", "Ammiche", "user");
        registerUser("Myriam", "Pouchain", "user");
        registerUser("Yohan", "Rameau", "user");
        registerUser("Ruben", "Sero", "user");
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
    	          .filter(u -> {
    	              try {
    	                  return u.getPseudo().equals(Objects.requireNonNull(pseudo));
    	              } catch (RemoteException e) {
    	                  e.printStackTrace();
    	              }
    	              return false;
    	          })
    	          .findFirst()
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
