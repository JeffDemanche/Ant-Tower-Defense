package game.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.Vec2d;
import engine.Screen;
import engine.scores.HighScores;
import engine.scores.ModifyException;
import engine.scores.Score;
import engine.ui.UIElement;
import engine.ui.UIImage;
import engine.ui.UISpline;
import engine.ui.UITextLabel;
import engine.ui.UITextLabel.HorizontalAlign;
import engine.ui.UITextLabel.VerticalAlign;
import game.ATDApp;
import game.ui.element.LoadGameButton;
import game.ui.element.NewGameButton;
import game.ui.element.SettingsButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ATDMenuScreen extends Screen {

	private final double LOGO_TOP = 70;
	private final double LOGO_WIDTH = 256;
	private final double LOGO_HEIGHT = 128;

	private final double BUTTON_WIDTH = 140;
	private final double BUTTON_HEIGHT = 60;

	private final double NEW_GAME_TOP = 210;
	private final double LOAD_GAME_TOP = 300;
	private final double SETTINGS_TOP = 390;
	private final double HIGH_SCORES_TOP = 460;
	
	private UIImage logo;

	private NewGameButton newGameButton;
	private LoadGameButton loadGameButton;
	private SettingsButton settingsButton;
	private UISpline spline;
	private ArrayList<UITextLabel> scoreLabels;
	
	public ATDMenuScreen(Vec2d initialSize, ATDApp app) {
		super(Color.WHITE, initialSize);

		this.logo = new UIImage(
				new Vec2d((initialSize.x - LOGO_WIDTH) / 2, LOGO_TOP),
				new Vec2d(LOGO_WIDTH, LOGO_HEIGHT), "/img/logo.png", 8);
		this.newGameButton = new NewGameButton(app,
				new Vec2d((initialSize.x - BUTTON_WIDTH) / 2, NEW_GAME_TOP),
				new Vec2d(BUTTON_WIDTH, BUTTON_HEIGHT));
		this.loadGameButton = new LoadGameButton(app,
				new Vec2d((initialSize.x - BUTTON_WIDTH) / 2, LOAD_GAME_TOP),
				new Vec2d(BUTTON_WIDTH, BUTTON_HEIGHT));
		this.settingsButton = new SettingsButton(app,
				new Vec2d((initialSize.x - BUTTON_WIDTH) / 2, SETTINGS_TOP),
				new Vec2d(BUTTON_WIDTH, BUTTON_HEIGHT));
		this.scoreLabels = new ArrayList<UITextLabel>();
		this.scoreLabels.add(new UITextLabel(
				new Vec2d((initialSize.x - BUTTON_WIDTH) / 2, HIGH_SCORES_TOP), 
				new Vec2d(BUTTON_WIDTH, BUTTON_HEIGHT), HorizontalAlign.CENTER,
				VerticalAlign.TOP, 
				"High Scores:", 
				new Font("Segoe Script", 16), 
				Color.BLACK));
		
		// Read in high scores
		HighScores hs = new HighScores("scores.txt");
		// ^ Try modifying the scores/names in the file above, and you will be prompted with
		// a message calling you a cheater. Note that this is only basic security, so some modifications
		// may go undetected (or even throw a noncheating error)
		ArrayList<Score> scores = new ArrayList<Score>();
		try {
			try {
				// Change the second argument to false if you want to not handle cheat detection
				// (useful for making your own scores file)
				hs.getScores(scores, true);
			} catch(ModifyException e) {
				e.printStackTrace();
				this.scoreLabels.get(0).setText("Cheater detected.");
				scores.clear();
			}
			// Uncomment this line if you want to rewrite to the scores file.
			//hs.writeScores(scores, true);
			
			// In the event that scores are completely lost, please copy scores_backup.txt into scores.txt
			
		} catch(IOException e) {
			e.printStackTrace();
			this.scoreLabels.get(0).setText("Error getting high scores.");
		}
		
		// Technically should not be necessary (unless user directly modifies the score file)
		Collections.sort(scores);
		
		// Adding all the labels for the scores
		double top = HIGH_SCORES_TOP;
		int ind = 1;
		for (Score s : scores) {
			this.scoreLabels.add(new UITextLabel(
					new Vec2d((initialSize.x - BUTTON_WIDTH) / 2, HIGH_SCORES_TOP), 
					new Vec2d(BUTTON_WIDTH, BUTTON_HEIGHT), HorizontalAlign.CENTER,
					VerticalAlign.TOP, 
					Integer.toString(ind) + ". " + s.toString(), 
					new Font("Segoe Script", 16), 
					Color.BLACK));
			ind++;
			top+=20;
		}
		
		
		
		
		List<Vec2d> controlPoints = new ArrayList<Vec2d>();
		controlPoints.add(new Vec2d(0,50));
		controlPoints.add(new Vec2d(85,85));
		controlPoints.add(new Vec2d(103,45));
		controlPoints.add(new Vec2d(70,25));
		
		this.spline  =  new UISpline(controlPoints);
		this.add(logo);

		this.add(newGameButton);
		this.add(loadGameButton);
		this.add(settingsButton);
		for (UITextLabel l : this.scoreLabels)
			this.add(l);
		
		for(UIElement controlPoint:this.spline.getControlPoints())
		{
			this.add(controlPoint);	
		}
		this.add(spline);
		
	}

	@Override
	public void onResize(Vec2d newSize) {
		super.onResize(newSize);

		this.logo
				.setPosition(new Vec2d((newSize.x - LOGO_WIDTH) / 2, LOGO_TOP));
		this.newGameButton.setPosition(
				new Vec2d((newSize.x - BUTTON_WIDTH) / 2, NEW_GAME_TOP));
		this.loadGameButton.setPosition(
				new Vec2d((newSize.x - BUTTON_WIDTH) / 2, LOAD_GAME_TOP));
		this.settingsButton.setPosition(
				new Vec2d((newSize.x - BUTTON_WIDTH) / 2, SETTINGS_TOP));
		// Updating the position for all the high score labels
		double top = HIGH_SCORES_TOP;
		for (UITextLabel l : this.scoreLabels) {
			l.setPosition(
				new Vec2d((newSize.x - BUTTON_WIDTH) / 2, top));
			top+=20;
		}
	}

}
