package fr.uge.ifshare.rmi.common.user;

import fr.uge.ifshare.rmi.common.IAdvertising;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements IUser {

    private final String firstName;
    private final String lastName;
    private final String password;
    private final String pseudo;
    private final List<IAdvertising> history = new ArrayList<IAdvertising>();

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
    }

    public User() {
        lastName = "";
        firstName = "";
        password = "";
        pseudo = "";
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


    @Override
    public void addToHistory(IAdvertising ad) {
        history.add(ad);
    }

    @Override
    public List<IAdvertising> getHistory() {
        return history;
    }
}
