package engine.scores;


/**
 * Class for keeping track of in-game score
 *
 */
public class Score implements Comparable<Score>{
	
	// Currently keeping track of this as an int
	private int amt;
	private String name;
	
	
	/**
	 * Creates a new object to keep track of score (setting it to a score of
	 * 0 by default, and a default name).
	 * @param name - the name of the player
	 */
	public Score() {
		this.amt = 0;
		// TODO: Change this to the defualt name to use for players
		this.name = "DEFAULT";
	}
	
	/**
	 * Creates a new object to keep track of score (setting it to a score of
	 * 0 by default).
	 * @param name - the name of the player
	 */
	public Score(String name) {
		this.amt = 0;
		this.name = name;
	}
	
	/**
	 * Creates a new object to keep track of score (setting it to the value
	 * provided).
	 * @param amt - the amount of points the player has
	 * @param name - the name of the player
	 */
	public Score(String name, int amt) {
		this.amt = amt;
		this.name = name;
	}
	
	public int getScore() {
		return this.amt;
	}
	
	public void setScore(int amt) {
		this.amt = amt;
	}
	
	/**
	 * Add this amount of points to the current score
	 * @param amt - the amount of points to add
	 */
	public void addScore(int amt) {
		this.amt = this.amt + amt;
	}

	@Override
	public int compareTo(Score arg0) {
		if (this.amt == arg0.getScore())
			return 0;
		else if (this.amt > arg0.getScore())
			return -1;
		else
			return 1;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name + " " + Integer.toString(this.getScore());
	}
	
	
	
}
