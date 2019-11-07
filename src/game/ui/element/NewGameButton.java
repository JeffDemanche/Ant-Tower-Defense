package game.ui.element;

import application.Vec2d;
import game.ATDApp;
import game.ui.ATDWorldScreen;
import javafx.scene.input.MouseEvent;

public class NewGameButton extends ATDButton {

	public NewGameButton(ATDApp app, Vec2d position, Vec2d size) {
		super(app, position, size, "New Game");
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		super.onMouseClicked(e);

		Vec2d currentScreenSize = this.getApp().getScreen().getCurrentSize();
		ATDWorldScreen screen = new ATDWorldScreen(currentScreenSize);
		this.getApp().setScreen(screen);
	}

}
