package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import game.world.system.SystemTowers;

public class TowerCinnamon extends Tower {

	private SystemTowers towers;

	public TowerCinnamon(SystemTowers system) {
		super(system, TowerInfo.CINNAMON.name, system.nextTowerId());

		this.towers = system;

	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

	@Override
	public int getCost() {
		return TowerInfo.CINNAMON.cost;
	}

}
