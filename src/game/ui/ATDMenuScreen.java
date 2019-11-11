package game.ui;

import java.util.ArrayList;
import java.util.List;

import application.Vec2d;
import engine.Screen;
import engine.ui.UIElement;
import engine.ui.UIImage;
import engine.ui.UISpline;
import game.ATDApp;
import game.ui.element.LoadGameButton;
import game.ui.element.NewGameButton;
import game.ui.element.SettingsButton;
import javafx.scene.paint.Color;

public class ATDMenuScreen extends Screen {

	private final double LOGO_TOP = 70;
	private final double LOGO_WIDTH = 256;
	private final double LOGO_HEIGHT = 128;

	private final double BUTTON_WIDTH = 140;
	private final double BUTTON_HEIGHT = 60;

	private final double NEW_GAME_TOP = 210;
	private final double LOAD_GAME_TOP = 300;
	private final double SETTINGS_TOP = 390;

	private UIImage logo;

	private NewGameButton newGameButton;
	private LoadGameButton loadGameButton;
	private SettingsButton settingsButton;
	private UISpline spline;

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
	}

}
