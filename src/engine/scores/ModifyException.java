package engine.scores;


/**
 * Exception for when the scores file is modified externally
 */
public class ModifyException extends Exception {
	public ModifyException() {
		super("Cheat detected: scores modified outside of program");
	}
}
