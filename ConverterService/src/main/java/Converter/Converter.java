package Converter;

import java.util.HashMap;

public class Converter {
	HashMap<String, Float> currencies = new HashMap<>();
	
	public Converter() {
		currencies.put("eur", 1f);
		currencies.put("usd", 1.128f);
		currencies.put("gbp", 0.846f);
		currencies.put("jpy", 128.04f);
		currencies.put("cad", 1.439f);
		currencies.put("tnd", 3.260f);
		currencies.put("cny", 7.204f);
		currencies.put("rub", 84.885f);
	}
	

	public float convert(String currentCurrency, String targetCurrency, float amount) {
		return (amount/currencies.get(currentCurrency))*currencies.get(targetCurrency);
	}
	
}
