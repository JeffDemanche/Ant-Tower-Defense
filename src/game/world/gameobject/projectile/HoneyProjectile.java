package game.world.gameobject.projectile;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class HoneyProjectile extends Projectile{

	public HoneyProjectile(SystemTowers towerSystem, HexCoordinates hex, ProjectileInfo info) {
		super(towerSystem, hex, info);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onTick(long nanosSincePreviousTick) {
		
		
		
		super.onTick(nanosSincePreviousTick);
	}

}
