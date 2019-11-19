package game.ui;

import application.Vec2d;
import engine.Screen;
import engine.scores.Score;
import engine.ui.UITextLabel;
import engine.ui.UIViewport;
import engine.ui.UITextLabel.HorizontalAlign;
import engine.ui.UITextLabel.VerticalAlign;
import game.ui.element.AddScoreButton;
import game.ui.element.SaveScoreButton;
import game.viewport.ATDViewport;
import game.world.ATDWorld;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ATDWorldScreen extends Screen {
	
	private UIViewport viewport;

	private final double SCORE_WIDTH = 150;
	private final double SCORE_HEIGHT = 30;
	private final double SCORE_PAD = 10;
	private final double SCORE_TOP = 5;
	private Score s;
	private boolean debug;
	
	public ATDWorldScreen(Vec2d initialSize) {
		super(Color.BLACK, initialSize);
		this.s = new Score();
		// TODO: set this to false to not distract from the game.
		this.debug = true;
		ATDWorld world = new ATDWorld(new ATDViewport(initialSize), "New World");
		
		this.viewport = new UIViewport(new Vec2d(0), initialSize, world);
		this.viewport.setDrawGrid(false);
		
		this.add(viewport);
		UITextLabel scoreText = new UITextLabel(
				new Vec2d (initialSize.x - SCORE_WIDTH - SCORE_PAD, SCORE_TOP), 
				new Vec2d(SCORE_WIDTH, SCORE_HEIGHT), 
				HorizontalAlign.RIGHT, 
				VerticalAlign.TOP, 
				"Score: " + this.s.getScore(), 
				new Font("Segoe Script", 16),
				Color.WHITE);
		// Adding score display
		this.add(scoreText);
		// This is just to demonstrate score functionality
		if (debug) {
			this.add(new AddScoreButton(
					new Vec2d (initialSize.x - SCORE_WIDTH - SCORE_PAD, SCORE_TOP + SCORE_HEIGHT), 
					new Vec2d(SCORE_WIDTH, SCORE_HEIGHT), 100, this.s, scoreText));
			this.add(new SaveScoreButton(
					new Vec2d (initialSize.x - SCORE_WIDTH - SCORE_PAD, SCORE_TOP + SCORE_HEIGHT + SCORE_HEIGHT), 
					new Vec2d(SCORE_WIDTH, SCORE_HEIGHT), this.s));
		}
		world.onStartup();
	}
	
	@Override
	public void onResize(Vec2d newSize) {
		super.onResize(newSize);
		this.viewport.setSize(newSize);
	}

}
