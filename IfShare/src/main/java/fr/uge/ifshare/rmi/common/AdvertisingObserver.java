package fr.uge.ifshare.rmi.common;

import java.io.Serializable;

public interface AdvertisingObserver extends Serializable {
	void onAdvertisingUpdate();
}
