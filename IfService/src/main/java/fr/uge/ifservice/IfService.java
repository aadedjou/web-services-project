package fr.uge.ifservice;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import fr.uge.ifservice.bank.Bank;
import fr.uge.ifservice.bank.BankServiceLocator;
import fr.uge.ifservice.bank.BankSoapBindingStub;
import fr.uge.ifservice.converter.Converter;
import fr.uge.ifservice.converter.ConverterServiceLocator;
import fr.uge.ifservice.converter.ConverterSoapBindingStub;
import fr.uge.ifshare.rmi.common.IAdvertising;
import fr.uge.ifshare.rmi.common.IOnlineShop;
import fr.uge.ifshare.rmi.common.user.IUserDatabase;

public class IfService {

	private Bank bankService;
	private Converter converterService;
	private IOnlineShop shop;
	private IUserDatabase usersDB;
	
	public IfService() throws MalformedURLException, RemoteException, NotBoundException, ServiceException {
		bankService = new BankServiceLocator().getBank();
		converterService = new ConverterServiceLocator().getConverter();
		((BankSoapBindingStub) bankService).setMaintainSession(true); 
		((ConverterSoapBindingStub) converterService).setMaintainSession(true); 
        shop = (IOnlineShop) Naming.lookup("onlineshop");
        usersDB = (IUserDatabase) Naming.lookup("userdata");
	}
	
	
	
	public String[] getSoldProductsList()  throws RemoteException{
		IAdvertising[] ads = shop.getAdvertisingsWhereProductWasBought();
		String[] adsToString = new String[ads.length];
		for (int i = 0; i < ads.length; i++) {
			adsToString[i] = ads[i].toString();
		}
		return adsToString;
	}
	
	
	/* Quand le produit choisi est en question,
	 * On va communiquer avec le service web Banque
	 * Savoir si le client (qui a un compte) a assez d'argent ou non
	 * Si oui, il achete le produit, et on fait un débit, + on change l'etat du produit/annonce
	 * Si non, il peut rien acheter
	 */
	public String buyProduct(String productName, String sellerName, String clientName) throws RemoteException {
		if(sellerName.equals(clientName)) {
			return "You can't buy you proper products !!";
		}
		if (bankService.clientCanBuy(clientName, shop.getAdvertisingByProductNameAndSeller(productName, sellerName).getPrice())) {
			IAdvertising ad = shop.getAdvertisingByProductNameAndSeller(productName, sellerName);
			
			bankService.debit(clientName, ad.getPrice());
			shop.buyProduct(usersDB.getUserById(clientName), ad, 1);
			bankService.credit(usersDB.getUserById(ad.getSellerPseudo()).getPseudo(), ad.getPrice());
			
			return "You bought a(n) " + ad.getProduct().getName() + " for " + ad.getPrice();
		}
		return "You can't buy this product. Not enough money.";
	}
	
	
	public String createUser(String firstName, String lastName, String password, double cash) throws RemoteException {
		return bankService.createAccount(usersDB.registerUser(firstName, lastName, password).getPseudo(), cash);
	}
	
	public String getBankAccountInformation(String pseudo) throws RemoteException {
		return bankService.getClientAccountInformation(pseudo);
	}
	
}
