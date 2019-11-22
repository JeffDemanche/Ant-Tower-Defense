package game.world.gameobject.gui;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.ComponentSolidColorSprite;
import engine.world.gameobject.GameObject;
import engine.world.gameobject.gui.ComponentGUIDrawable;
import game.world.system.SystemGUI;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class GUITowersPanel extends GameObject {

	private static final double TOWERS_PANEL_HEIGHT = 150;

	private SystemGUI gui;

	private ComponentGUIDrawable bound;
	private ComponentSolidColorSprite color;

	public GUITowersPanel(SystemGUI system, Vec2d initialScreenSize) {
		super(system, "GUITowersPanel");

		this.gui = system;

		bound = new ComponentGUIDrawable(this,
				new Vec2d(0, initialScreenSize.y - TOWERS_PANEL_HEIGHT),
				new Vec2d(initialScreenSize.x, initialScreenSize.y));
		color = new ComponentSolidColorSprite(this, new Color(0, 0, 0, 0.4), bound);

		this.addComponent(bound);
		this.addComponent(color);
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

		bound.setPosition(new Vec2d(0, newSize.y - TOWERS_PANEL_HEIGHT));
		bound.setSize(new Vec2d(newSize.x, newSize.y));
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

}
