package engine.scores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Wrapper class for interacting with high scores
 */
public class HighScores {

	// Holds the path to the high score file
	private String filepath;
	
	/**
	 * Creates a HighScores object with the path to the file for scores
	 * @param filepath - the file containing the high score data
	 */
	public HighScores(String filepath) {
		this.filepath = filepath;
	}
	
	/**
	 * Gets the scores by adding them to the specified List.
	 * @param target - a list to add the scores to (will be cleared beforehand)
	 * @throws IOException
	 */
	public void getScores(List<Score> target) throws IOException {
		// TODO: Need to add custom exception for this method
		target.clear();
		FileReader fr;
		BufferedReader br;
		fr = new FileReader(this.filepath);
		br = new BufferedReader(fr);
		String line = null;
		line = br.readLine();
		while(line != null) {
			String[] data = line.trim().split("\\s+");
			// TODO: Rather than skipping, should add exception handling here
			if (data.length  >= 2) {
				// TODO: Add security check maybe?
				target.add(new Score(data[0], Integer.parseInt(data[1])));
			}
			line = br.readLine();
		}
		br.close();
		fr.close();
	}
	
	/**
	 * Method for writing a list of high scores to a file
	 * @param scores - List of the scores to write to the file
	 * @throws IOException
	 */
	public void writeScores(List<Score> scores) throws IOException {
		// TODO: Might be better to just have the list be a SortedList
		// in the first place
		Collections.sort(scores);
		FileWriter fw = new FileWriter(this.filepath);
		BufferedWriter bw = new BufferedWriter(fw);
		for (Score s : scores) {
			// TODO: Add hash as third item in line for security
			bw.write(s.getName() + " " + Integer.toString(s.getScore()) + "\n"); 
		}
		bw.close();
		fw.close();
	}
	
	
}
