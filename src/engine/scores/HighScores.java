package engine.scores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * Wrapper class for interacting with high scores
 * 
 * I have updated this to use SHA-512 hashing. 
 * 
 * I am actually implementing something slightly similar to a blockchain. Basically, 
 * the next score's hash is the hash for the concatenation of the current score's content (i.e. name
 * and score amount) concatenated with the previous hash. For the first one, 
 * I use an initial "salt" to hash with. Using the concatenation is somewhat more secure,
 * since it means that all future hashes depend on the previous ones, which makes it more
 * difficult to modify a single score and get away with it. 
 * 
 * I also added a footer that is the hash of the whole contents of the file. I realized
 * I needed this because one could remove scores starting from the bottom and going up,
 * since the hashing is done in one direction. I might add hashing in the other direction
 * in addition, which would also allow for better identification of which score is the one
 * giving the error.
 * 
 * Besides using a better hash, another thing I might do is encrypt the entire
 * scores file if we are still concerned with security; but I think that it is
 * nice to be able to view the scores outside the program so I am not sure if
 * we will do this. 
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
	 * Static method for hashing. 
	 * @param toHash - the String to be hashed.
	 * @return a Base64 encoded string of the hash (this is so that non-ASCII
	 * 		   characters won't be printed).
	 */
	private static String makeHash(String toHash) {
		MessageDigest md;
		try {
			// Can change this to a different hashing algorithm.
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
		byte[] bytes;
		try {
			// Apply the selected hashing algorithm to bytes of the string
			bytes = md.digest(toHash.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		// Encode the bytes as a String and return it
		return Base64.getEncoder().encodeToString(bytes);
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
		String total = "";
		if (useHashes)
			while(line != null) {
				String[] data = line.trim().split("\\s+");
				// Handles the footer of the file (which is just the hash of the previous contents)
				if (data.length == 1) {
					if (!HighScores.makeHash(total).equals(data[0])) {
						System.out.println(HighScores.makeHash(total));
						System.out.println(data[0]);
						System.out.print(total);
						br.close();
						fr.close();
						throw new ModifyException();
					}
				} else if (data.length == 3) {
					Score s = new Score(data[0], Integer.parseInt(data[1]));
					target.add(s);
					salt = HighScores.makeHash(s.toString() + salt);
					if (!salt.equals(data[2])) {
						br.close();
						fr.close();
						throw new ModifyException();
					}
				} else {
					br.close();
					fr.close();
					throw new ModifyException();
				}
				total = total + line + "\n";
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
			String total = "";
			String tmp;
			for (Score s : scores) {
				salt = HighScores.makeHash(s.toString() + salt);
				tmp = s.toString() + " " + salt  + "\n";
				total = total + tmp;
				bw.write(tmp); 
			}
			// Added a footer, so that the scores cannot be removed
			bw.write(HighScores.makeHash(total));
		} else {
			for (Score s : scores) {
				bw.write(s.toString() + "\n"); 
			}
		}
		bw.close();
		fw.close();
	}
	
	
}
