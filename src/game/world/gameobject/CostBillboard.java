package game.world.gameobject;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.GameObject;

public class CostBillboard extends GameObject{

	public CostBillboard(GameSystem system, String name, int cost, Vec2d position) {
		super(system, name);
		// TODO Auto-generated constructor stub
		CostBillboardComponent costBillboard =
				new CostBillboardComponent("costbillboard", this,cost, position );
		this.addComponent(costBillboard);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

}
