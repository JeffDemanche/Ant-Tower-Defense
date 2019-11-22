package game.world.gameobject.gui;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.ComponentSolidColorSprite;
import engine.world.gameobject.GameObject;
import engine.world.gameobject.gui.ComponentGUIDrawable;
import engine.world.gameobject.gui.ComponentGUIImageRenderer;
import game.world.gameobject.tower.TowerInfo;
import game.world.system.SystemGUI;
import javafx.scene.paint.Color;

public class GUITowerIcon extends GameObject {

	private static final double TOWER_ICON_HEIGHT = 65;
	private static final double TOWER_ICON_WIDTH = 35;

	private SystemGUI gui;
	private TowerInfo info;

	private ComponentGUIDrawable parentGUI;
	private Vec2d pos;

	private ComponentGUIDrawable bound;
	private ComponentGUIImageRenderer image;

	public GUITowerIcon(SystemGUI system, ComponentGUIDrawable parentGUI,
			Vec2d pos, Vec2d initialScreenSize, TowerInfo info) {
		super(system, "GUITowersPanel");

		this.gui = system;
		this.info = info;

		this.parentGUI = parentGUI;
		this.pos = pos;

		bound = new ComponentGUIDrawable(this,
				parentGUI.getScreenPosition().plus(pos),
				new Vec2d(TOWER_ICON_WIDTH, TOWER_ICON_HEIGHT));
		image = new ComponentGUIImageRenderer(this,
				parentGUI.getScreenPosition().plus(pos), new Vec2d(32), 6,
				"file:src/img/tower/cinnamon.png");

		this.addComponent(bound);
		this.addComponent(image);
	}

	public TowerInfo getTowerInfo() {
		return this.info;
	}

	public ComponentGUIDrawable getBound() {
		return this.bound;
	}
	
	@Override
	public void onResize(Vec2d newSize) {
		super.onResize(newSize);

		bound.setPosition(parentGUI.getScreenPosition().plus(pos));
		image.setScreenPosition(parentGUI.getScreenPosition().plus(pos));
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

}
