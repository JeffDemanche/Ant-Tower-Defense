package game.ui.element;



import application.Vec2d;
import engine.scores.Score;
import engine.ui.UIButton;
import engine.ui.UITextLabel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EditNameButton extends UIButton {
	private Score s;
	private UITextLabel text;
	private boolean listening;
	public EditNameButton(Vec2d position, Vec2d size, Score s, UITextLabel text) {
		super(position, size, "Edit Your Name", new Font("Segoe Script", 16), Color.WHITE, Color.BLACK);
		this.s = s;
		this.text = text;
		this.listening = false;
	}
	
	@Override
	public void onKeyTyped(KeyEvent e) {
		
		if (this.listening) {
			
			if (this.s.getName().length() > 12 || e.getCharacter().equals("\r")) {
				this.listening = false;
				System.out.println("Entered name.");
				
			} else {
				this.s.setName(this.s.getName() + e.getCharacter());
				this.text.setText("Name: " + this.s.getName());
			}
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		super.onMouseClicked(e);
		this.listening = true;
		this.s.setName("");
		this.text.setText("Name: " + this.s.getName());
		
	}
}
