package game.world.gameobject.projectile;

import javax.sound.midi.SysexMessage;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.ComponentRegisteredSprite;
import engine.world.gameobject.GameObject;
import game.world.gameobject.HoneyParticlesEmitter;
import game.world.gameobject.tile.Tile;
import game.world.gameobject.tile.TileHoney;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;
import game.world.gameobject.SpriteRegistry;

public class HoneyProjectile extends Projectile{

	public HoneyProjectile(SystemTowers towerSystem, HexCoordinates hex, ProjectileInfo info) {
		super(towerSystem, hex, info);
		// TODO Auto-generated constructor stub
		this.sprite = new ComponentRegisteredSprite(this,
				SpriteRegistry.HONEY_PROJECTILE, bound);
		this.addComponent(sprite);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onTick(long nanosSincePreviousTick) {
		
		if (this.hit) {
			HexCoordinates hexTarget = HexCoordinates.fromGameSpace(this.target);
			Tile targetTile = (Tile) ((SystemTowers) this.getSystem()).getLevel().getTileAt(hexTarget.getX(),
					hexTarget.getY());

			if (targetTile.getType() != Tile.Type.Honey) {
				
				//save hit tile
				
				((SystemTowers) this.getSystem()).getLevel().saveTile(hexTarget, targetTile);
				
				targetTile.remove();

				((SystemTowers) this.getSystem()).getLevel().setTile((hexTarget),
						new TileHoney(((SystemTowers) this.getSystem()).getLevel(), hexTarget));
				

			}

			//remove projectile
			this.remove();
			
			GameObject emptyGameObject = new HoneyParticlesEmitter(((SystemTowers) this.getSystem())
					.getLevel(),"emittergo1", this.target);
			
			((SystemTowers) this.getSystem()).getLevel().
			    addGameObject(SystemTowers.PROJECTILE_Z+1, emptyGameObject);
		}

		super.onTick(nanosSincePreviousTick);
	}

}
