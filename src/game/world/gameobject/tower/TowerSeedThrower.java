package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.ComponentRegisteredSprite;
import game.world.ATDWorld;
import game.world.gameobject.SpriteRegistry;
import game.world.gameobject.ant.Ant;
import game.world.system.HexCoordinates;
import game.world.system.SystemAnts;
import game.world.system.SystemTowers;

public class TowerSeedThrower extends Tower {

	private ComponentRegisteredSprite sprite;

	private SystemTowers towers;
	private SystemAnts ants;

	private Ant lockedAnt;

	public TowerSeedThrower(SystemTowers system, HexCoordinates hex) {
		super(system, TowerInfo.SEED_THROWER, system.nextTowerId(), hex);

		this.towers = system;
		this.ants = ((ATDWorld) system.getWorld()).getAntsSystem();

		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.SEED_THROWER, bound);

		this.setDrawLineOfSight(false);

		this.addComponent(sprite);

		this.setEnabled(true);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);

		// Find ant.
		if (lockedAnt == null) {
			this.lockedAnt = ants.findClosestTarget(this.hex,
					TowerInfo.SEED_THROWER.range);
		}
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

	@Override
	public int getCost() {
		return TowerInfo.SEED_THROWER.cost;
	}

	@Override
	protected void shot() {
		if (lockedAnt != null) {
			if (lockedAnt.isAlive()) {
				Bullet b = new Bullet(this.getSystem(),
						this.bound.getGamePosition(),
						lockedAnt.getBound().getPosition());
				getSystem().addGameObject(4, b);
				System.out.println("here");
				this.lockedAnt.damage(TowerInfo.SEED_THROWER.damage, null);
			}
			lockedAnt = null;
		}
	}

	@Override
	public boolean traversable() {
		return false;
	}

}
