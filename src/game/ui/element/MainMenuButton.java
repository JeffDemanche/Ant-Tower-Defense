package game.ui.element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import application.Vec2d;
import engine.scores.HighScores;
import engine.scores.ModifyException;
import engine.scores.Score;
import game.ATDApp;
import game.ui.ATDMenuScreen;
import game.ui.ATDWorldScreen;
import javafx.scene.input.MouseEvent;

public class MainMenuButton extends ATDButton {
	protected Score score;
	public MainMenuButton(ATDApp app, Vec2d position, Vec2d size, Score s) {
		super(app, position, size, "Main Menu");
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
		Vec2d currentScreenSize = this.getApp().getScreen().getCurrentSize();
		ATDMenuScreen screen = new ATDMenuScreen(currentScreenSize, this.getApp());
		this.getApp().setScreen(screen);
	}
}
