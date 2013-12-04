package fr.lipn.tensor.exceptions;

public class UnsupportedModeException extends Exception {
	public UnsupportedModeException() {
		super("Invalid mode: only modes 1-3");
	}
}
