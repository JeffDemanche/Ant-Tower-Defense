package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentLine;
import engine.world.gameobject.GameObject;
import javafx.scene.paint.Color;

public class Bullet extends GameObject {

	private ComponentLine line;
	private double timer;

	public Bullet(GameSystem system, Vec2d start, Vec2d end) {
		super(system, "Bullet");
		this.line = new ComponentLine(this, start, end, Color.CHARTREUSE, 3);
		this.timer = 2;
		addComponent(line);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);
		
		timer -= nanosSincePreviousTick * 10E-9;

		if (timer <= 0) {
			getSystem().removeGameObject(this);
		} else {
			double percent = timer / 2D;
			line.setColor(line.getColor().interpolate(new Color(0, 0, 0, 0),
					percent));
		}
	}
	
	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

}
