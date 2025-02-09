package game.world.gameobject.projectile;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.world.ComponentRegisteredSprite;
import engine.world.gameobject.ComponentCollidable;
import engine.world.gameobject.GameObject;
import engine.world.gameobject.ComponentCollidable.CollisionHandler;
import game.world.ATDWorld;
import game.world.gameobject.SpriteRegistry;
import game.world.gameobject.WaterParticlesEmitter;
import game.world.gameobject.ant.Ant;
import game.world.gameobject.tower.TowerInfo;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class WaterProjectile extends Projectile {

	private ComponentCollidable collidable;

	public WaterProjectile(SystemTowers towerSystem, HexCoordinates hex,
			ProjectileInfo projectileInfo) {
		super(towerSystem, hex, projectileInfo);
		// TODO Auto-generated constructor stub
		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.WATER_PROJECTILE, bound);

		this.collidable = new ComponentCollidable(this, this.bound,
				new CollisionHandler() {
					@Override
					public void onCollide(GameObject obj) {
						if (obj instanceof Ant) {
							((Ant) obj).kill(null);
						}
					}
				}, ((ATDWorld) this.getSystem().getWorld()).getAntsSystem());

		this.addComponent(sprite);
		this.addComponent(collidable);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {

		if (this.hit) {

			// remove projectile
			this.remove();

			GameObject emptyGameObject = new WaterParticlesEmitter(
					((SystemTowers) this.getSystem()).getLevel(), "emittergo1",
					this.target);

			((SystemTowers) this.getSystem()).getLevel().addGameObject(
					SystemTowers.PROJECTILE_Z + 1, emptyGameObject);
		}

		super.onTick(nanosSincePreviousTick);
	}
}
