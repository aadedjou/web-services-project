package Bank;

import java.util.HashMap;
import java.util.Objects;

public class Bank {
	HashMap<String, Float> accounts = new HashMap<>();
	
	public Bank() {
		accounts.put("sbenlakhal", 45.68f);
		accounts.put("spostansque", 22.09f);
	}
	
	public boolean clientCanBuy(String clientName , float amount) {
		Objects.requireNonNull(clientName);
		if (!accounts.containsKey(clientName)) {
			throw new IllegalArgumentException(clientName + " doesn't have account");
		}
		return this.accounts.get(clientName) > amount;
	}
	
	public void debit(String clientName, float amount) {
		Objects.requireNonNull(clientName);
		if (!accounts.containsKey(clientName)) {
			throw new IllegalArgumentException(clientName + " doesn't have account");
		}
		
		this.accounts.replace(clientName, this.accounts.get(clientName)-amount);
	}
	
	public String getClientAccountInformation(String clientName) {
		return clientName + " has " + accounts.get(clientName) + "€";
	}
	
}