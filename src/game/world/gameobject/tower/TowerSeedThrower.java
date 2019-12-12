package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.ComponentRegisteredSprite;
import game.world.gameobject.SpriteRegistry;
import game.world.gameobject.ant.Ant;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class TowerSeedThrower extends Tower {

	private ComponentRegisteredSprite sprite;
	
	private Ant lockedAnt;

	public TowerSeedThrower(SystemTowers system, HexCoordinates hex) {
		super(system, TowerInfo.SEED_THROWER, system.nextTowerId(), hex);
		
		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.SEED_THROWER, bound);
		
		this.addComponent(sprite);
	}
	
	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCost() {
		return TowerInfo.SEED_THROWER.cost;
	}

	@Override
	protected void shot() {
		// TODO Auto-generated method stub

	}

}
