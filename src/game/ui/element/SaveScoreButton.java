package game.ui.element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import application.Vec2d;
import engine.scores.HighScores;
import engine.scores.ModifyException;
import engine.scores.Score;
import engine.ui.UIButton;
import engine.ui.UITextLabel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;


/**
 * Button for saving a score. Note that this will simply write new scores,
 * so pressing it multiple times will just add multiple new scores.
 * This is only for testing score functionality.
 */
public class SaveScoreButton extends engine.ui.UIButton {

	private Score s;

	public SaveScoreButton(Vec2d position, Vec2d size, Score s) {
		super(position, size, "Save score to file.", new Font("Segoe Script", 16), Color.WHITE, Color.BLACK);
		this.s = s;
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		super.onMouseClicked(e);
		// Read in high scores
		HighScores hs = new HighScores("scores.txt");
		ArrayList<Score> scores = new ArrayList<Score>();
		try {
			try {
				hs.getScores(scores, true);
			} catch(ModifyException err) {
				err.printStackTrace();
				scores.clear();
			}
			// Add our new score
			scores.add(this.s);
			// Sort the scores with our score in here
			Collections.sort(scores);
			hs.writeScores(scores, true);
		} catch(IOException err) {
			err.printStackTrace();
		}
	}

}
