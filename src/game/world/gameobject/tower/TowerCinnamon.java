package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.GameSystem;
import game.world.system.SystemTowers;

public class TowerCinnamon extends Tower {
	
	private SystemTowers towers;
	
	public TowerCinnamon(SystemTowers system) {
		super(system, "Cinnamon", system.nextTowerId());
		
		this.towers = system;
		
		
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
