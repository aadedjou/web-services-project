package fr.uge.ifshare.rmi.common.user;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Objects;

import fr.uge.ifshare.rmi.common.AdvertisingObserver;
import fr.uge.ifshare.rmi.common.IAdvertising;
import fr.uge.ifshare.rmi.common.user.User.UserObserver;

public class User implements Serializable, IUser {
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String pseudo;
    private final UserObserver userobserver;
    
    public User(String first, String last, String password) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(last);
        this.password = Objects.requireNonNull(password);
        this.firstName = first.substring(0, 1).toUpperCase() + first.substring(1).toLowerCase();
        this.lastName = last.substring(0, 1).toUpperCase() + last.substring(1).toLowerCase();
        this.pseudo = first.toLowerCase().charAt(0) +
                        Normalizer.normalize(last, Normalizer.Form.NFD).toLowerCase()
                          .replaceAll("\\p{M}", "")
                          .replace(" ", "");
        this.userobserver = new UserObserver();
    }
    
    /*
    public class UserObservers {
    	private final ArrayList<UserObserver> userobservers = new ArrayList<UserObserver>();
    	
    	
    	public UserObserver createUserObserver() {
    		return new UserObserver();
    	}
    	
    	public void addNewUserObserver() {
    		userobservers.add(new UserObserver());
    	}
    	
    	
    	public void removeUserObserver(UserObserver userobs) {
    		if (!userobservers.contains(userobs)) {
    			throw new IllegalArgumentException("User Observer is wrong");
    		}
    		userobservers.remove(Objects.requireNonNull(userobs));
    	}
    }
    */
    
    public class UserObserver implements AdvertisingObserver {
    	
    	@Override
    	public void onAdvertisingUpdate(IAdvertising adv) {
    		System.out.println("The product from  " + adv + " is now available !");
    	}
    	
        
    }

    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getShortenFullName() {
        return firstName + " " + lastName.charAt(0) + ".";
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPseudo() {
        return pseudo;
    }

    @Override
    public String toString() {
        return getPseudo();
    }

	@Override
	public void receiveMessage(String string) {
		System.out.println(string);
	}

    /*
	@Override
	public UserObservers getUserObservers() {
		return userobservers;
	}

	*/
	
    
    

}