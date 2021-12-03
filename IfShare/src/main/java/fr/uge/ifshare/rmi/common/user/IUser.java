package fr.uge.ifshare.rmi.common.user;


import fr.uge.ifshare.rmi.common.IAdvertising;

import java.io.Serializable;
import java.util.List;

public interface IUser extends Serializable {
	String getFullName();
    String getShortenFullName();
    String getPassword();
    String getPseudo();
    // UserObservers getUserObservers();
	void receiveMessage(String string);

    void addToHistory(IAdvertising ad);
    List<IAdvertising> getHistory();
}
