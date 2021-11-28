package fr.uge.ifshare.rmi.common;

import java.rmi.Remote;

public interface AdvertisingObserver extends Remote {

	public void onAdvertisingUpdate(IAdvertising ad);
}
