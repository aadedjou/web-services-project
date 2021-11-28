package fr.uge.ifshare.rmi.common.product;

public enum State {
	NEW, USED, DETERIORATED;

	@Override
	public String toString() {
		return super.toString().charAt(0) + super.toString().toLowerCase().substring(1);
	}
}
