package game.world.gameobject.gui;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.ComponentSolidColorSprite;
import engine.world.gameobject.GameObject;
import engine.world.gameobject.gui.ComponentGUIDrawable;
import engine.world.gameobject.gui.ComponentGUIImageRenderer;
import engine.world.gameobject.gui.ComponentGUITextRenderer;
import game.world.gameobject.SpriteRegistry;
import game.world.gameobject.tower.TowerInfo;
import game.world.system.SystemGUI;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GUITowerInfoPanel extends GameObject {

	private static final double ICON_HEIGHT = 24;

	private SystemGUI gui;

	private TowerInfo currentTower;

	private ComponentGUIDrawable bound;
	private ComponentGUIDrawable imageBound;
	private ComponentSolidColorSprite background;
	private ComponentGUIImageRenderer image;
	private ComponentGUITextRenderer text;

	public GUITowerInfoPanel(SystemGUI system) {
		super(system, "Tower Info Panel");

		this.gui = system;

		Vec2d screenSize = system.getWorld().getViewport().getScreenSize();
		Vec2d position = new Vec2d(screenSize.x - SystemGUI.INFO_PANEL_WIDTH,
				screenSize.y - SystemGUI.TOWERS_PANEL_HEIGHT);
		Vec2d size = new Vec2d(SystemGUI.INFO_PANEL_WIDTH,
				SystemGUI.TOWERS_PANEL_HEIGHT);

		this.bound = new ComponentGUIDrawable(this, position, size);
		this.imageBound = new ComponentGUIDrawable(this, position,
				new Vec2d(ICON_HEIGHT));

		this.background = new ComponentSolidColorSprite(this,
				new Color(0, 0, 0, 0.5), bound);
		this.image = new ComponentGUIImageRenderer(this, imageBound,
				SpriteRegistry.EMPTY);
		this.text = new ComponentGUITextRenderer(this, "",
				position.plus(new Vec2d(ICON_HEIGHT, 20)),
				new Font("Arial", 14));

		this.addComponent(bound);
		this.addComponent(background);
		this.addComponent(image);
		this.addComponent(text);
	}

	private void updateInfo() {
		if (currentTower != null) {
			this.image.setImage(currentTower.guiSprite);
			StringBuilder content = new StringBuilder();
			content.append(currentTower.name + "\n");
			content.append(currentTower.description + "\n");
			this.text.setText(SystemGUI.wordWrap(content.toString(), 35));
		} else {
			this.text.setText(this.currentTower.name);
		}
	}

	@Override
	public void onResize(Vec2d newSize) {
		super.onResize(newSize);

		Vec2d position = new Vec2d(newSize.x - SystemGUI.INFO_PANEL_WIDTH,
				newSize.y - SystemGUI.TOWERS_PANEL_HEIGHT);
		Vec2d size = new Vec2d(SystemGUI.INFO_PANEL_WIDTH,
				SystemGUI.TOWERS_PANEL_HEIGHT);

		this.bound.setPosition(position);
		this.imageBound.setPosition(position);
		this.bound.setSize(size);
		this.text.setScreenPos(position.plus(new Vec2d(ICON_HEIGHT, 20)));
	}

	public void setTower(TowerInfo info) {
		this.currentTower = info;
		updateInfo();
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

}
