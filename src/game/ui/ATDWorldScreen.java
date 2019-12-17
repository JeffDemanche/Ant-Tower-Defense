package game.ui;

import application.Vec2d;
import engine.Screen;
import engine.scores.Score;
import engine.ui.UITextLabel;
import engine.ui.UIViewport;
import engine.ui.UITextLabel.HorizontalAlign;
import engine.ui.UITextLabel.VerticalAlign;
import game.ATDApp;
import game.ui.element.AddScoreButton;
import game.ui.element.EditNameButton;
import game.ui.element.ExitGameButton;
import game.ui.element.MainMenuButton;
import game.ui.element.NewGameButton;
import game.ui.element.SaveScoreButton;
import game.viewport.ATDViewport;
import game.world.ATDWorld;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ATDWorldScreen extends Screen {
	
	private UIViewport viewport;

	private final double SCORE_WIDTH = 150;
	private final double SCORE_HEIGHT = 35;
	private final double SCORE_PAD = 10;
	private final double SCORE_TOP = 5;
	private Score s;
	private boolean debug;
	private boolean paused;
	private ExitGameButton exit;
	private UITextLabel pausedText;
	private NewGameButton newGame;
	private UITextLabel nameText;
	private EditNameButton editName;
	
	public ATDWorldScreen(Vec2d initialSize, ATDApp app) {
		super(Color.BLACK, initialSize);
		this.s = new Score();
		// TODO: set this to false to not distract from the game.
		this.debug = false;
		this.paused = false;
		
		UITextLabel scoreText = new UITextLabel(
				new Vec2d (initialSize.x - SCORE_WIDTH - SCORE_PAD, SCORE_TOP), 
				new Vec2d(SCORE_WIDTH, SCORE_HEIGHT), 
				HorizontalAlign.RIGHT, 
				VerticalAlign.TOP, 
				"Score: " + this.s.getScore(), 
				new Font("Arial", 16),
				Color.WHITE);
		
		ATDWorld world = new ATDWorld(new ATDViewport(initialSize), "New World", this, this.s, scoreText);
		
		this.viewport = new UIViewport(new Vec2d(0), initialSize, world);
		this.viewport.setDrawGrid(false);
		this.exit = new ExitGameButton(
				new Vec2d (initialSize.x/2 - SCORE_WIDTH, SCORE_TOP + SCORE_HEIGHT), 
				new Vec2d(SCORE_WIDTH, SCORE_HEIGHT),
				app,
				this.s);
		
		this.pausedText = new UITextLabel(
				new Vec2d (initialSize.x/2 - SCORE_WIDTH, SCORE_TOP), 
				new Vec2d(SCORE_WIDTH, SCORE_HEIGHT), 
				HorizontalAlign.CENTER, 
				VerticalAlign.TOP, 
				"Game Paused", 
				new Font("Arial", 16),
				Color.WHITE);
		
		this.newGame = new NewGameButton(app, 
				new Vec2d (initialSize.x/2 - SCORE_WIDTH, SCORE_TOP + SCORE_HEIGHT + SCORE_HEIGHT + SCORE_TOP), 
				new Vec2d(SCORE_WIDTH, SCORE_HEIGHT),
				this.s);
		
		
		
		this.nameText = new UITextLabel(
				new Vec2d (initialSize.x/2 - SCORE_WIDTH, 6 * SCORE_TOP + 6 * SCORE_HEIGHT), 
				new Vec2d(SCORE_WIDTH, SCORE_HEIGHT), 
				HorizontalAlign.CENTER, 
				VerticalAlign.TOP, 
				"Name: " + this.s.getName(), 
				new Font("Arial", 16),
				Color.WHITE);
		
		this.editName = new EditNameButton(
				new Vec2d (initialSize.x/2 - SCORE_WIDTH, 5 * SCORE_TOP + 5 * SCORE_HEIGHT), 
				new Vec2d(SCORE_WIDTH, SCORE_HEIGHT), 
				this.s, 
				this.nameText);
		
		this.add(viewport);
		
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
	
	public void togglePaused() {
		this.paused = !this.paused;
		if (this.paused) {
			this.add(this.exit);
			this.add(this.pausedText);
			this.add(this.newGame);
			this.add(this.nameText);
			this.add(this.editName);
		} else {
			this.remove(this.exit);
			this.remove(this.pausedText);
			this.remove(this.newGame);
			this.remove(this.nameText);
			this.remove(this.editName);
		}
	}
	
	@Override
	public void onResize(Vec2d newSize) {
		super.onResize(newSize);
		this.viewport.setSize(newSize);
	}

}
