package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.ComponentRegisteredSprite;
import engine.world.gameobject.ComponentCollidable;
import engine.world.gameobject.ComponentCollidable.CollisionHandler;
import engine.world.gameobject.GameObject;
import game.world.ATDWorld;
import game.world.gameobject.SpriteRegistry;
import game.world.gameobject.ant.Ant;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class TowerCinnamon extends Tower {

	private SystemTowers towers;

	private ComponentRegisteredSprite sprite;
	private ComponentCollidable collidable;

	private boolean fireThisTick;

	public TowerCinnamon(SystemTowers system, HexCoordinates hex) {
		super(system, TowerInfo.CINNAMON, system.nextTowerId(), hex, 0);

		this.fireThisTick = false;

		this.towers = system;

		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.CINNAMON, bound);
		this.collidable = new ComponentCollidable(this, this.bound,
				new CollisionHandler() {
					@Override
					public void onCollide(GameObject obj) {
						if (obj instanceof Ant && fireThisTick) {
							((Ant) obj).damage(TowerInfo.CINNAMON.damage);
							fireThisTick = false;
						}
					}
				}, ((ATDWorld) this.getSystem().getWorld()).getAntsSystem());

		this.addComponent(sprite);
		this.addComponent(collidable);
		
		this.setEnabled(true);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

	@Override
	public int getCost() {
		return TowerInfo.CINNAMON.cost;
	}

	/**
	 * Called once whenever attack cooldown is completed.
	 */
	@Override
	protected void shot() {
		this.fireThisTick = true;
	}

}
