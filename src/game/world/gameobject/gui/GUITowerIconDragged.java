package game.world.gameobject.gui;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.GameObject;
import engine.world.gameobject.gui.ComponentGUIDrawable;
import engine.world.gameobject.gui.ComponentGUIImageRenderer;
import game.world.gameobject.tower.TowerInfo;

public class GUITowerIconDragged extends GameObject {

	private ComponentGUIDrawable bound;
	private ComponentGUIImageRenderer image;

	public GUITowerIconDragged(GameSystem system, TowerInfo tower,
			Vec2d initialScreenPos, Vec2d initialScreenSize) {
		super(system, "Dragged Tower Icon");

		this.bound = new ComponentGUIDrawable(this, initialScreenPos,
				initialScreenSize);
		this.image = new ComponentGUIImageRenderer(this, bound, 6,
				tower.spritePath);

		this.addComponent(bound);
		this.addComponent(image);
	}

	public void setPosition(Vec2d screenPos) {
		this.bound.setPosition(screenPos);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

}
