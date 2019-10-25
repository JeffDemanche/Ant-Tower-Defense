package game;

import application.Vec2d;
import game.ui.ATDWorldScreen;

public class AntTowerDefense {
 
	public static void main(String[] args) {
		Vec2d initialSize = new Vec2d(800, 600);
		ATDApp wiz = new ATDApp(initialSize);
		wiz.setScreen(new ATDWorldScreen(initialSize));
		wiz.start();
	}
	
}
