package fr.uge.ifshare.rmi.common;

import java.util.List;
import java.util.Optional;

import fr.uge.ifshare.rmi.common.product.Product;

public interface IUser {
	
	public String getFullName();
    public String getShortenFullName();
    public String getPassword();
    public String getPseudo();

}
