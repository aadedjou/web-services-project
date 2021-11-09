package fr.uge.ifshare.rmi;

public class Grade {
	private float value;
	
	public Grade(float value) {
		if (value < 0 || 5 < value) {
			throw new IllegalArgumentException("Grade value must be between 0/5 and 5/5. ('" + value + "' is invalid.)");
		}
		this.value = value;
	}
}
