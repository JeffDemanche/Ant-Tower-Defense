package game.ui.element;

import application.Vec2d;
import engine.scores.Score;
import engine.ui.UIButton;
import engine.ui.UITextLabel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Button for adding to a score, only displayed to test score functionality.
 */
public class AddScoreButton extends UIButton {

	private int amt;
	private Score s;
	private UITextLabel text;
	
	public AddScoreButton(Vec2d position, Vec2d size, int amt, Score s, UITextLabel text) {
		super(position, size, "Add " + Integer.toString(amt) + " to score.", new Font("Segoe Script", 16), Color.WHITE, Color.BLACK);
		this.amt = amt;
		this.s = s;
		this.text = text;
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		super.onMouseClicked(e);
		this.s.addScore(this.amt);
		this.text.setText("Score: " + Integer.toString(this.s.getScore()));
	}

}
