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
import game.ATDApp;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ExitGameButton extends UIButton {
	protected ATDApp app; 
	protected Score score;
	public ExitGameButton(Vec2d position, Vec2d size, ATDApp app, Score s) {
		super(position, size, "Exit Game", new Font("Segoe Script", 16), Color.WHITE, Color.BLACK);
		this.app = app;
		this.score = s;
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
			scores.add(this.score);
			// Sort the scores with our score in here
			Collections.sort(scores);
			hs.writeScores(scores, true);
		} catch(IOException err) {
			err.printStackTrace();
		}
		this.app.shutdown();
		
	}
}
