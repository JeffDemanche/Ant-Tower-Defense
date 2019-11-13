package engine.scores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Wrapper class for interacting with high scores
 * 
 * NOTE: currently I am just using hashcodes for basic security. 
 * This is technically not very secure, since hashcodes are NOT the
 * same as cryptographic hashes. For next week, I am planning on actually
 * implementing a cryptographic hashing algorithm, but for now I am using
 * hashcodes (the goal for this week was to just get high scores working,
 * which seems to be fine). The hashcode implementation should give an idea
 * of how I plan to make sure the scores file does not change. 
 * 
 * I am actually implementing something slightly similar to a blockchain. Basically, 
 * the next score's hash is the hash for the concatenation of the current score's content (i.e. name
 * and score amount) concatenated with the previous hash. For the first one, 
 * I use an initial "salt" to hash with. Using the concatenation is somewhat more secure,
 * since it means that all future hashes depend on the previous ones, which makes it more
 * difficult to modify a single score and get away with it. 
 * 
 * Please note that while hashcodes are not necessarily deterministic for objects in general,
 * they ARE deterministic for Strings.
 * 
 * Besides using a better hash, the next steps I plan on taking for security would probably be 
 * encrypting the whole file so that it would be difficult for users to edit it outside of the program
 * (not sure if this level of security is necessary though).
 */
public class HighScores {

	// Holds the path to the high score file
	private String filepath;
	// Contains the string that we initially "salt" our hash with
	private String salt;
	
	/**
	 * Creates a HighScores object with the path to the file for scores
	 * and a default value for the salt used for hashing
	 * @param filepath - the file containing the high score data
	 */
	public HighScores(String filepath) {
		this.filepath = filepath;
		this.salt = "salt";
	}
	
	/**
	 * Creates a HighScores object with the path to the file for scores 
	 * and the salt used for hashing
	 * @param filepath - the file containing the high score data
	 * @param salt - the String of the salt to use
	 */
	public HighScores(String filepath, String salt) {
		this.filepath = filepath;
		this.salt = salt;
	}
	
	/**
	 * Gets the scores by adding them to the specified List.
	 * @param target - a list to add the scores to (will be cleared beforehand)
	 * @param useHashes - a boolean indicating whether or not to check hashes
	 * @throws IOException
	 */
	public void getScores(List<Score> target, boolean useHashes) throws IOException, ModifyException {
		target.clear();
		FileReader fr;
		BufferedReader br;
		fr = new FileReader(this.filepath);
		br = new BufferedReader(fr);
		String line = null;
		line = br.readLine();
		String salt = this.salt;
		if (useHashes)
			while(line != null) {
				String[] data = line.trim().split("\\s+");
				// TODO: Rather than skipping, should add exception handling here
				if (data.length >= 3) {
					Score s = new Score(data[0], Integer.parseInt(data[1]));
					target.add(s);
					// Please read what I wrote at the top of this file for more details
					salt = Integer.toString((s.toString() + salt).hashCode());
					if (!salt.equals(data[2])) {
						br.close();
						fr.close();
						throw new ModifyException();
					}
				}
				line = br.readLine();
			}
		else
			while(line != null) {
				String[] data = line.trim().split("\\s+");
				// TODO: Rather than skipping, should add exception handling here
				if (data.length  >= 2) {
					Score s = new Score(data[0], Integer.parseInt(data[1]));
					target.add(s);
				}
				line = br.readLine();
			}
		br.close();
		fr.close();
		
	}
	
	/**
	 * Method for writing a list of high scores to a file
	 * @param scores - List of the scores to write to the file
	 * @param useHashing - a boolean indicating whether or not to add hashes
	 * @throws IOException
	 */
	public void writeScores(List<Score> scores, boolean useHashing) throws IOException {
		// TODO: Might be better to just have the list be a SortedList
		// in the first place
		Collections.sort(scores);
		FileWriter fw = new FileWriter(this.filepath);
		BufferedWriter bw = new BufferedWriter(fw);
		if (useHashing) {
			String salt = this.salt;
			for (Score s : scores) {
				// Please read what I wrote at the top of this file for more details
				salt = Integer.toString((s.toString() + salt).hashCode());	
				bw.write(s.toString() + " " + salt  + "\n"); 
			}
		} else {
			for (Score s : scores) {
				bw.write(s.toString() + "\n"); 
			}
		}
		bw.close();
		fw.close();
	}
	
	
}
