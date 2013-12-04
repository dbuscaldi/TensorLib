package fr.lipn.tensor.exceptions;

public class WrongSizeException extends Exception {

	public WrongSizeException() {
		super("Columns in Khatri-Rao product must be of the same size");
	}
	
}
