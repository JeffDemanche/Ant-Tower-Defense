package game.world.gameobject.tower;

import java.util.List;

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

public class TowerOrchid extends Tower {

	private ComponentRegisteredSprite sprite;

	private SystemTowers towers;
	private SystemAnts ants;

	private List<Ant> lockedAnts;

	public TowerOrchid(SystemTowers system, HexCoordinates hex) {
		super(system, TowerInfo.ORCHID, system.nextTowerId(), hex);

		this.towers = system;
		this.ants = ((ATDWorld) system.getWorld()).getAntsSystem();

		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.ORCHID, bound);

		this.setDrawLineOfSight(false);

		this.addComponent(sprite);

		this.setEnabled(true);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);

		// Find ant.
		if (lockedAnts == null) {
			this.lockedAnts = ants.findClosestTargets(this.hex,
					TowerInfo.SEED_THROWER.range, 2);
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
		if (lockedAnts != null) {
			for (Ant a : lockedAnts) {
				if (a.isAlive()) {
					Bullet b = new Bullet(this.getSystem(),
							this.bound.getGamePosition(),
							a.getBound().getPosition());
					getSystem().addGameObject(4, b);
					a.damage(TowerInfo.SEED_THROWER.damage, null);
				}
			}
			lockedAnts = null;
		}
	}

	@Override
	public boolean traversable() {
		return false;
	}

}
