package fr.uge.ifservice;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import fr.uge.ifservice.bank.Bank;
import fr.uge.ifservice.bank.BankServiceLocator;
import fr.uge.ifservice.converter.Converter;
import fr.uge.ifservice.converter.ConverterServiceLocator;
import fr.uge.ifshare.rmi.common.IAdvertising;
import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.user.IUser;
import fr.uge.ifshare.rmi.common.user.IUserDatabase;

public class IfService {

	private Bank bankService;
	private Converter converterService;
	private IOnlineShop shop;
	private IUserDatabase usersDB;
	
	public IfService() throws MalformedURLException, RemoteException, NotBoundException, ServiceException {
		bankService = new BankServiceLocator().getBank();
		converterService = new ConverterServiceLocator().getConverter();
        shop = (IOnlineShop) Naming.lookup("onlineshop");
        usersDB = (IUserDatabase) Naming.lookup("userdata");
	}
	
	
	
	public Object[] getSoldProductsList()  throws RemoteException{
		return shop.getAdvertisingsWhereProductWasBought();
	}
	
	
	/* Quand le produit choisi est en question,
	 * On va communiquer avec le service web Banque
	 * Savoir si le client (qui a un compte) a assez d'argent ou non
	 * Si oui, il achete le produit, et on fait un débit, + on change l'etat du produit/annonce
	 * Si non, il peut rien acheter
	 */
	public String buyProduct(IAdvertising ad, String clientName) throws RemoteException {
		
		if (bankService.clientCanBuy(clientName, ad.getPrice())) {
			bankService.debit(clientName, ad.getPrice());
			shop.buyProduct(usersDB.getUserById(clientName), ad, 1);
			bankService.credit(usersDB.getUserById(ad.getSellerPseudo()).getPseudo(), ad.getPrice());
		
			return "You bought a(n) " + ad.getProduct().getName() + " for " + ad.getPrice();
		}
		return "You can't buy a(n) " + ad.getProduct().getName() + ". Not enough money.";
	}
	
	
	/*public IAdvertising[] toObjects(List<IAdvertising> ads) {
		IAdvertising[] adsToObjects = new IAdvertising[ads.size()];
		for (int i = 0; i < ads.size(); i++) {
			adsToObjects[i] = ads.get(i);
		}
		return adsToObjects;
	}*/
	
}
