package game;

import application.Vec2d;
import game.ui.ATDMenuScreen;

public class AntTowerDefense {

	public static void main(String[] args) {
		Vec2d initialSize = new Vec2d(800, 600);
		ATDApp app = new ATDApp(initialSize);
		app.setScreen(new ATDMenuScreen(initialSize, app));
		app.start();
	}

}
