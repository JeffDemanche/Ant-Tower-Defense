package game.ui.element;

import application.Vec2d;
import engine.ui.UIButton;
import game.ATDApp;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ATDButton extends UIButton {

	private ATDApp app;
	
	public ATDButton(ATDApp app, Vec2d position, Vec2d size, String text) {
		super(position, size, text, new Font("Arial", 16), Color.WHITE, Color.BLACK);
		this.app = app;
		this.setArcRadius(15D);
	}
	
	public ATDApp getApp() {
		return app;
	}

}
