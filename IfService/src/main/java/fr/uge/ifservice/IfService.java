package fr.uge.ifservice;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import fr.uge.ifshare.rmi.common.IAdvertising;
import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.OnlineShop;
import fr.uge.ifshare.rmi.common.user.IUser;
import fr.uge.ifshare.rmi.common.user.IUserDatabase;

public class IfService {

	//private Bank bankService;
	//private Converter converterService;
	private IOnlineShop shop;
	private IUserDatabase usersDB;
	
	public IfService() throws MalformedURLException, RemoteException, NotBoundException {
		//bankService = new BankServiceLocator().getBank();
		//converterService = new ConverterServiceLocator().getConverter();
        shop = (IOnlineShop) Naming.lookup("onlineshop");
        usersDB = (IUserDatabase) Naming.lookup("userdata");
	}
	
	
	
	public List<IAdvertising> getSoldProductsList() {
		return shop.getAdvertisingsWhereProductWasBought();
	}
	
	
	/* Quand le produit choisi est en question,
	 * On va communiquer avec le service web Banque
	 * Savoir si le client (qui a un compte) a assez d'argent ou non
	 * Si oui, il achete le produit, et on fait un débit, + on change l'etat du produit/annonce
	 * Si non, il peut rien acheter
	 */
	public String buyProduct(IAdvertising ad, IUser client) {
		
		if (bank.clientCanBuy(client.getPseudo(), ad.getPrice()) {
			bank.debit(client.getPseudo(), ad.getPrice());
			shop.buyProduct(client, ad, 1);
			bank.cashIn(usersDB.getUserById(ad.getSellerPseudo()), ad.getPrice());
		
			return "You bought a(n) " + ad.getProduct().getName() + " for " + ad.getPrice();
		}
		return "You can't buy a(n) " + ad.getProduct().getName() + ". Not enough money.";
	}
	
}
