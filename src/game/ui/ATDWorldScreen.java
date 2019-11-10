package game.ui;

import application.Vec2d;
import engine.Screen;
import engine.ui.UIViewport;
import game.viewport.ATDViewport;
import game.world.ATDWorld;
import javafx.scene.paint.Color;

public class ATDWorldScreen extends Screen {
	
	private UIViewport viewport;

	public ATDWorldScreen(Vec2d initialSize) {
		super(Color.BLACK, initialSize);
		
		ATDWorld world = new ATDWorld(new ATDViewport(initialSize), "New World");
		
		this.viewport = new UIViewport(new Vec2d(0), initialSize, world);
		this.viewport.setDrawGrid(false);
		
		this.add(viewport);
		
		world.onStartup();
	}
	
	@Override
	public void onResize(Vec2d newSize) {
		super.onResize(newSize);
		this.viewport.setSize(newSize);
	}

}
