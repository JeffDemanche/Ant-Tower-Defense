package game.world.gameobject.gui;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.ComponentSolidColorSprite;
import engine.world.gameobject.GameObject;
import engine.world.gameobject.gui.ComponentGUIDrawable;
import engine.world.gameobject.gui.ComponentGUITextRenderer;
import game.world.system.SystemGUI;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GUIWorldInfo extends GameObject {

	private SystemGUI gui;

	private int currentWave;
	private int totalWaves;

	private int sugarRemaining;
	private int cash;

	private ComponentGUIDrawable bound;
	private ComponentSolidColorSprite background;
	private ComponentGUITextRenderer text;

	public GUIWorldInfo(SystemGUI system, Vec2d initialScreenSize) {
		super(system, "GUITowersPanel");

		this.gui = system;

		bound = new ComponentGUIDrawable(this,
				new Vec2d(initialScreenSize.x - SystemGUI.WORLD_INFO_WIDTH,
						SystemGUI.WORLD_INFO_TOP),
				new Vec2d(SystemGUI.WORLD_INFO_WIDTH,
						SystemGUI.WORLD_INFO_HEIGHT));
		background = new ComponentSolidColorSprite(this,
				new Color(0, 0, 0, 0.5), bound);
		text = new ComponentGUITextRenderer(this, "",
				new Vec2d(initialScreenSize.x - SystemGUI.WORLD_INFO_WIDTH,
						SystemGUI.WORLD_INFO_TOP + 26),
				new Font("Arial", 18));

		this.addComponent(bound);
		this.addComponent(background);
		this.addComponent(text);
	}

	public void setInfo(int currentWave, int totalWaves, int sugarRemaining,
			int cash) {
		// Accounts for off-by-one.
		this.currentWave = currentWave + 1;
		this.totalWaves = totalWaves;
		this.sugarRemaining = sugarRemaining;
		this.cash = cash;
	}

	private String createText() {
		StringBuilder builder = new StringBuilder();
		builder.append("Wave: " + currentWave + " / " + totalWaves + "\n\n");
		builder.append("Sugar Remaining: " + sugarRemaining + "\n");
		builder.append("Cash: $" + cash + "\n");
		return builder.toString();
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);

		this.text.setText(createText());
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		super.onMousePressed(e);

		if (bound.insideBB(new Vec2d(e.getSceneX(), e.getSceneY()))) {
			e.consume();
		}
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		super.onMouseReleased(e);

		if (bound.insideBB(new Vec2d(e.getSceneX(), e.getSceneY()))) {
			e.consume();
		}
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		super.onMouseDragged(e);

		if (bound.insideBB(new Vec2d(e.getSceneX(), e.getSceneY()))) {
			e.consume();
		}
	}

	@Override
	public void onResize(Vec2d newSize) {
		super.onResize(newSize);

		bound.setPosition(new Vec2d(newSize.x - SystemGUI.WORLD_INFO_WIDTH,
				SystemGUI.WORLD_INFO_TOP));
		bound.setSize(new Vec2d(SystemGUI.WORLD_INFO_WIDTH,
				SystemGUI.WORLD_INFO_HEIGHT));
		text.setScreenPos(new Vec2d(newSize.x - SystemGUI.WORLD_INFO_WIDTH,
				SystemGUI.WORLD_INFO_TOP + 26));
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

}
