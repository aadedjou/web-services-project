package fr.uge.ifshare.rmi.common;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import fr.uge.ifshare.rmi.common.product.Product;

public interface IUser extends Serializable {
	
	public String getFullName();
    public String getShortenFullName();
    public String getPassword();
    public String getPseudo();
    void receiveMessage(String string);

    void addToHistory(IAdvertising ad);
    List<IAdvertising> getHistory();

}
