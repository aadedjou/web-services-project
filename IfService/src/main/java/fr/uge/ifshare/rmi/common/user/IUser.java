package fr.uge.ifshare.rmi.common.user;


public interface IUser {
	public String getFullName();
    public String getShortenFullName();
    public String getPassword();
    public String getPseudo();
    //public UserObservers getUserObservers();
	public void receiveMessage(String string);
}
