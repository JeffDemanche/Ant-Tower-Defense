package game.world.gameobject.tower;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.ComponentRegisteredSprite;
import engine.world.GameSystem;
import engine.world.gameobject.ComponentCircle;
import engine.world.gameobject.ComponentCollidable;
import engine.world.gameobject.GameObject;
import engine.world.gameobject.ComponentCollidable.CollisionHandler;
import engine.world.gameobject.ComponentNavigable;
import game.world.ATDWorld;
import game.world.gameobject.SpriteRegistry;
import game.world.gameobject.ant.Ant;
import game.world.gameobject.ant.AntCarpenter;
import game.world.gameobject.projectile.Projectile;
import game.world.gameobject.projectile.ProjectileConstants;
import game.world.gameobject.projectile.ProjectileFactory;
import game.world.gameobject.projectile.ProjectileInfo;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class SpiderWebTower extends Tower {

	private ComponentCollidable collidable;
	private boolean caugthAnt = false;
	private ComponentCircle spiderWebBound;
	private long spiderTimer;
	private int spiderTime = 2;
	private boolean spiderSpwaned = false;
	private Ant caughtAnt;

	public SpiderWebTower(SystemTowers system, HexCoordinates hexCoordinates) {
		super(system, TowerInfo.SPIDERWEB, system.nextTowerId(),
				hexCoordinates);
		// TODO Auto-generated constructor stub

		this.projectileSpeed = 0.02;

		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.SPIDERWEB, bound);

		spiderWebBound = this.bound;
		this.collidable = new ComponentCollidable(this, this.bound,
				new CollisionHandler() {
					@Override
					public void onCollide(GameObject obj) {
						if (!caugthAnt && obj instanceof Ant) {
							// myPos = myPos.smult(3);
							// spiderWebBound.adjustPosition(new Vec2d(3,0));
							caugthAnt = true;
							caughtAnt = (AntCarpenter) obj;
							((AntCarpenter) obj).setCaught(true);
							((AntCarpenter) obj).getDrawable()
									.setPosition(spiderWebBound.getPosition());
						}
					}
				}, ((ATDWorld) this.getSystem().getWorld()).getAntsSystem());

		this.addComponent(sprite);
		this.addComponent(collidable);

		this.setEnabled(true);
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

	@Override
	protected void shot() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		if (this.caugthAnt) {
			spiderTimer += nanosSincePreviousTick;
			if (spiderTimer > 1000000000) {
				spiderTime--;
				spiderTimer = 0;
				if (spiderTime < 0 && !spiderSpwaned) {
					Vec2d direction = new Vec2d(0, -1);
					Vec2d target = hex.toGameSpaceCentered()
							.plus(direction.smult(3));

					ProjectileInfo pjInfo = new ProjectileInfo(getName(),
							ProjectileConstants.SPIDER, this.direction,
							this.projectileSpeed, target);

					Projectile spiderProjectile = ProjectileFactory
							.getInstance().createProjectile(
									(SystemTowers) getSystem(), hex, pjInfo);

					getSystem().addGameObject(SystemTowers.PROJECTILE_Z,
							spiderProjectile);
					spiderSpwaned = true;
					this.remove();
					caughtAnt.kill(null);

				}
			}
		}
		super.onTick(nanosSincePreviousTick);
	}
}
